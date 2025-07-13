package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import com.raquo.airstream.state.Var
import com.tewe.nrftw.EnchantmentData.Enchantment
import org.scalajs.dom.HTMLDivElement

object EnchantmentsBuilder {

  @JSImport("@find/**/EnchantmentsBuilder.css", JSImport.Namespace)
  @js.native
  private object Stylesheet extends js.Object

  val _ = Stylesheet // Use import to prevent DCE

  def renderEnchant(
    enchant: Enchantment,
    isSelected: Boolean,
    mods: Modifier[HtmlElement]*
  ): HtmlElement = {
    div(enchant.htmlDisplay(), mods)
  }

  def enchantmentsCompact(
    enchantments: List[String],
    enchantDownsides: List[String],
  ) = {
    div(
      cls("enchantments-container-compact"),
      enchantments.map(enchant => {
        div(cls(s"enchantment-item-compact enchant-text"), span(enchant))
      }),
      enchantDownsides.map(downside =>
        div(cls(s"enchantment-item-compact downside-text"), span(downside))
      ),
    )
  }

  def enchantmentsFull(
    enchantments: List[String],
    enchantDownsides: List[String],
  ) = {
    div(
      cls("enchantments-container"),
      div(
        enchantments.map(enchant => {
          div(cls("enchantment-item enchant-text"), span(enchant))
        })
      ),
      div(
        enchantDownsides.map(downside =>
          div(cls(s"enchantment-item downside-text"), span(downside))
        )
      ),
    )
  }

  def enchantSplitSelectComponent(
    index: Int,
    config: ItemBuilderConfig,
    sortedEnchants: List[Enchantment],
    sortedMagicEnchants: List[Enchantment],
    itemRaritySignal: Signal[ItemRarity],
    stateVar: Var[ItemState],
    enchantVar: Var[String],
    enchantsErrorSignal: Signal[List[Boolean]],
  ) = {
    Log.debug(
      s"Rendering enchantSplitSelectComponent for ${config
          .itemSlot} and index: $index errors: ${stateVar
          .now()
          .enchantsError
          .mkString(",")}"
    )

    val optionsSignal = itemRaritySignal.map { itemRarity =>
      if (itemRarity == ItemRarity.Plagued)
        sortedEnchants
      else
        sortedMagicEnchants
    }

    val singleEnchantErrorSignal = enchantsErrorSignal.map { errors =>
      if (errors.size - 1 >= index)
        errors(index)
      else
        false
    }

    customSelectComponent[Enchantment](
      enchantVar,
      _.id,
      enchant =>
        renderEnchant(
          enchant,
          isSelected = false,
          cls := s"enchant-group-${enchant.group}",
        ),
      optionsSignal,
      (enchant, selected) => renderEnchant(enchant, isSelected = selected),
      cls("x-hasError") <-- singleEnchantErrorSignal,
      cls("enchant-text"),
      enchantVar.signal.changes.mapTo(stateVar.now()) -->
        Errors.validator(config, stateVar),
    )
  }

  def customSelectComponent[A](
    valueVar: Var[String],
    valueFn: A => String,
    valueDisplayFn: A => HtmlElement,
    optionsSignal: Signal[List[A]],
    optionDisplayFn: (a: A, selected: Boolean) => HtmlElement,
    mods: Modifier[HtmlElement]*
  ) = {
    val isDropdownVisible = Var(false)

    val customSelectContainer = div(
      cls := "custom-select-container",
      div(
        cls := "custom-select-display",
        mods,
        onClick --> { _ =>
          isDropdownVisible.update(!_)
        },
        child <--
          valueVar
            .signal
            .combineWith(optionsSignal)
            .map { (selectedId, options) =>
              options
                .find(valueFn(_) == selectedId)
                .map(optionDisplayFn(_, selected = true))
                .getOrElse(span("Select..."))
            },
      ),
      div(
        cls := "custom-select-dropdown",
        inContext { thisNode =>
          styleAttr <--
            isDropdownVisible
              .signal
              .combineWith(optionsSignal)
              .map { (visible, options) =>
                if (visible) {
                  val dropdown = thisNode
                    .ref
                    .parentNode
                    .asInstanceOf[HTMLDivElement]
                  val dropdownRect = dropdown.getBoundingClientRect()
                  val spaceBelow =
                    org.scalajs.dom.window.innerHeight - dropdownRect.bottom
                  val spaceAbove = dropdownRect.top
                  println(dropdown.offsetHeight)
                  val top = {
                    if (spaceBelow < 300 && spaceAbove > spaceBelow) {
                      dropdownRect.top - 300
                    } else {
                      dropdownRect.bottom
                    }
                  }

                  val rect = thisNode
                    .ref
                    .parentNode
                    .asInstanceOf[HTMLDivElement]
                    .getBoundingClientRect()
                  s"top: ${top}px; left: ${rect.left}px; width: ${rect
                      .width}px; display: block;"
                } else {
                  "display: none;"
                }
              }
        },
        children <--
          optionsSignal.map(
            _.map { value =>
              div(
                cls := "custom-option-wrapper",
                onClick --> { _ =>
                  valueVar.set(valueFn(value))
                  isDropdownVisible.set(false)
                },
                valueDisplayFn(value),
              )
            }
          ),
      ),
    )

    // Close dropdown when clicking outside
    windowEvents(_.onClick)
      .filter(_ => isDropdownVisible.now())
      .filterNot(ev =>
        customSelectContainer.ref.contains(ev.target.asInstanceOf[dom.Node])
      )
      .foreach(_ => isDropdownVisible.set(false))(unsafeWindowOwner)

    // Close dropdown on scroll
    windowEvents(_.onScroll)
      .filter(_ => isDropdownVisible.now())
      .foreach(_ => isDropdownVisible.set(false))(unsafeWindowOwner)

    customSelectContainer
  }

  def enchantmentsSelect(
    config: ItemBuilderConfig,
    stateVar: Var[ItemState],
  ) = {
    Log.debug(s"Rendering enchantmentsSelect for ${config.itemSlot}")
    val sortedEnchants = config.enchants.values.toList.sortBy(_.id)
    val sortedMagicEnchants = config.magicEnchants.values.toList.sortBy(_.id)
    val sortedEnchantDownsides = config
      .enchantDownsides
      .values
      .toList
      .sortBy(_.id)

    val itemRaritySignal = stateVar.signal.map(_.itemRarity)
    // val itemRaritySignal = {
    //   stateVar
    //     .zoomLazy(_.itemRarity)((state, itemRarity) =>
    //       state.copy(itemRarity = itemRarity)
    //     )
    //     .signal
    // }
    val enchantsVar = {
      stateVar.zoomLazy(_.enchants)((state, enchants) => {
        Log.debug(s"Updating ${config.itemSlot} enchantsVar ${enchants.size}")
        state.copy(enchants = enchants)
      })
    }
    val downsideVar = {
      stateVar.zoomLazy(_.downside.getOrElse(""))((state, downside) => {
        state.copy(downside = Option.when(downside.nonEmpty)(downside))
      })
    }
    val enchantsErrorSignal = stateVar.signal.map(_.enchantsError)

    // div(
    // child <--
    // itemRaritySignal.map { itemRarity =>
    // Log.debug(s"Rendering enchantmentsSelect for $itemRarity")
    div(
      child <--
        itemRaritySignal
          .debugWithName(s"${config.itemSlot}.itemRaritySignal")
          .debugLog(Config.debugLogWhenFunction)
          .map(itemRarity => {
            div()
          }),
      cls := "enchantments-container",
      children <--
        enchantsVar.splitByIndex { (index, value, valueVar) =>
          enchantSplitSelectComponent(
            index,
            config,
            sortedEnchants,
            sortedMagicEnchants,
            itemRaritySignal,
            stateVar,
            valueVar,
            enchantsErrorSignal,
          )
        },
      child <--
        itemRaritySignal.map { itemRarity =>
          if (itemRarity == ItemRarity.Plagued) {
            customSelectComponent[Enchantment](
              downsideVar,
              _.id,
              enchant => renderEnchant(enchant, isSelected = false),
              Signal.fromValue(sortedEnchantDownsides),
              (enchant, selected) =>
                renderEnchant(enchant, isSelected = selected),
              cls("downside-text"),
            )
            // select(
            //   cls := "downside-text",
            //   value <-- downsideVar,
            //   onChange.mapToValue --> downsideVar,
            //   sortedEnchantDownsides.map(enchant => {
            //     option(
            //       value := enchant.id,
            //       cls := s"enchant-group-${enchant.group}",
            //       enchant.value,
            //     )
            //   }),
            // )
          } else {
            div()
          }
        },
    )
    // }
    // )
  }
}

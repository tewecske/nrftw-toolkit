package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import com.raquo.airstream.state.Var

object EnchantmentsBuilder {

  @JSImport("@find/**/EnchantmentsBuilder.css", JSImport.Namespace)
  @js.native
  private object Stylesheet extends js.Object

  val _ = Stylesheet // Use import to prevent DCE

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
    sortedEnchants: List[Enchant],
    sortedMagicEnchants: List[Enchant],
    itemRaritySignal: Signal[ItemRarity],
    stateVar: Var[ItemState],
    enchantVar: Var[String],
    enchantsErrorSignal: Signal[List[Boolean]],
  ) = {
    println(
      s"Rendering enchantSplitSelectComponent for ${config
          .itemSlot} and index: $index errors: ${stateVar
          .now()
          .enchantsError
          .mkString(",")}"
    )
    select(
      cls := "enchant-text",
      cls("x-hasError") <--
        enchantsErrorSignal.map { errors =>
          if (errors.size - 1 >= index)
            errors.apply(index)
          else
            false
        },
      value <--
        enchantVar
          .signal
          .debugWithName(s"${config.itemSlot} enchantVar $index")
          .debugLog(),
      onChange.mapToValue --> enchantVar,
      onChange.mapTo(stateVar.now()) --> Errors.validator(config, stateVar),
      children <--
        itemRaritySignal.map(itemRarity => {
          println(
            s"Rendering ${config.itemSlot} $index options for $itemRarity"
          )
          if (itemRarity == ItemRarity.Plagued) {
            sortedEnchants.map(enchant => {
              option(
                value := enchant.id,
                selected <-- enchantVar.signal.map(_ == enchant.id),
                cls := s"enchant-group-${enchant.group}",
                enchant.value,
              )
            })
          } else {
            sortedMagicEnchants.map(enchant => {
              option(
                value := enchant.id,
                selected <-- enchantVar.signal.map(_ == enchant.id),
                cls := s"enchant-group-${enchant.group}",
                enchant.value,
              )
            })
          }
        }),
    )
  }
  def enchantmentsSelect(
    config: ItemBuilderConfig,
    stateVar: Var[ItemState],
  ) = {
    println(s"Rendering enchantmentsSelect for ${config.itemSlot}")
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
    // println(s"Rendering enchantmentsSelect for $itemRarity")
    div(
      child <--
        itemRaritySignal
          .debugWithName(s"${config.itemSlot}.itemRaritySignal")
          .debugLog()
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
            select(
              cls := "downside-text",
              value <-- downsideVar,
              onChange.mapToValue --> downsideVar,
              sortedEnchantDownsides.map(enchant => {
                option(
                  value := enchant.id,
                  cls := s"enchant-group-${enchant.group}",
                  enchant.value,
                )
              }),
            )
          } else {
            div()
          }
        },
    )
    // }
    // )
  }
}

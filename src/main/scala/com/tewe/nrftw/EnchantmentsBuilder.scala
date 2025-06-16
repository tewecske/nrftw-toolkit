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
    stateVar: Var[ItemState],
    enchantVar: Var[String],
    enchantsErrorSignal: Signal[List[Boolean]],
  ) = {
    select(
      cls := "enchant-text",
      cls("x-hasError") <-- enchantsErrorSignal.map(_.apply(index)),
      value <-- enchantVar,
      onChange.mapToValue --> enchantVar,
      onChange.mapTo(stateVar.now()) --> Errors.validator(config, stateVar),
      sortedEnchants.map(enchant => {
        option(
          value := enchant.id,
          cls := s"enchant-group-${enchant.group}",
          enchant.value,
        )
      }),
    )
  }
  def enchantmentsSelect(
    config: ItemBuilderConfig,
    stateVar: Var[ItemState],
  ) = {
    val sortedEnchants = config.enchants.values.toList.sortBy(_.id)
    val sortedEnchantDownsides = config
      .enchantDownsides
      .values
      .toList
      .sortBy(_.id)

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

    div(
      cls := "enchantments-container",
      children <--
        enchantsVar.splitByIndex { (index, value, valueVar) =>
          enchantSplitSelectComponent(
            index,
            config,
            sortedEnchants,
            stateVar,
            valueVar,
            enchantsErrorSignal,
          )
        },
      if (config.itemRarity == ItemRarity.Plagued) {
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
      },
    )
  }
}

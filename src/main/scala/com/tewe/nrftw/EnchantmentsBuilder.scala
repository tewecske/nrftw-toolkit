package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object EnchantmentsBuilder {

  @JSImport("@find/**/EnchantmentsBuilder.css", JSImport.Namespace)
  @js.native private object Stylesheet extends js.Object

  val _ = Stylesheet // Use import to prevent DCE

  def enchantmentsCompact(itemRarity: ItemRarity, enchantments: List[String], enchantDownsides: List[String]) = {
      div(
        cls("enchantments-container-compact"),
        enchantments.map(enchant =>
          div(
            cls(s"enchantment-item-compact item-type-${itemRarity}"),
            span(enchant)
          )
        ),
        enchantDownsides.map(downside =>
          div(
            cls(s"enchantment-item-compact downside-text"),
            span(downside)
          )
        ),
      )
  }

  def enchantmentsFull(itemRarity: ItemRarity, enchantments: List[String], enchantDownsides: List[String]) = {
      div(
        cls("enchantments-container"),
        div(
          enchantments.map(enchant =>
            div(
              cls("enchantment-item"),
              cls(s"item-type-${itemRarity}"),
              span(enchant)
            )
          )
        ),
        div(
          enchantDownsides.map(downside =>
            div(
              cls(s"enchantment-item downside-text"),
              span(downside)
            )
          )
        )
      )
  }

  def enchantmentsSelect(config: ItemBuilderConfig, stateVar: Var[ItemState]) = {
    val sortedEnchants = config.enchants.values.toList.sortBy(_.id)
    val sortedEnchantDownsides = config.enchantDownsides.values.toList.sortBy(_.id)

    val enchant1Var = stateVar.zoomLazy(_.enchant1)((state, enchant) => state.copy(enchant1 = enchant))
    val enchant2Var = stateVar.zoomLazy(_.enchant2)((state, enchant) => state.copy(enchant2 = enchant))
    val enchant3Var = stateVar.zoomLazy(_.enchant3)((state, enchant) => state.copy(enchant3 = enchant))
    val enchant4Var = stateVar.zoomLazy(_.enchant4)((state, enchant) => state.copy(enchant4 = enchant))
    val downsideVar = stateVar.zoomLazy(_.downside)((state, enchant) => state.copy(downside = enchant))

    val enchant1ErrorSignal = stateVar.signal.map(_.enchant1Error)
    val enchant2ErrorSignal = stateVar.signal.map(_.enchant2Error)
    val enchant3ErrorSignal = stateVar.signal.map(_.enchant3Error)
    val enchant4ErrorSignal = stateVar.signal.map(_.enchant4Error)

        div(
          cls := "enchantments-container",
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant1ErrorSignal,
            value <-- enchant1Var,
            onChange.mapToValue --> enchant1Var,
            onChange.mapTo(stateVar.now()) --> Errors.validator(config, stateVar),
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant2ErrorSignal,
            value <-- enchant2Var,
            onChange.mapToValue --> enchant2Var,
            onChange.mapTo(stateVar.now()) --> Errors.validator(config, stateVar),
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant3ErrorSignal,
            value <-- enchant3Var,
            onChange.mapToValue --> enchant3Var,
            onChange.mapTo(stateVar.now()) --> Errors.validator(config, stateVar),
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant4ErrorSignal,
            value <-- enchant4Var,
            onChange.mapToValue --> enchant4Var,
            onChange.mapTo(stateVar.now()) --> Errors.validator(config, stateVar),
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "downside-text",
            value <-- downsideVar,
            onChange.mapToValue --> downsideVar,
            sortedEnchantDownsides.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          )
        )
  }
}


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

  def enchantSelectComponent(config: ItemBuilderConfig, sortedEnchants: List[Enchant], stateVar: Var[ItemState], enchantVar: Var[String], enchantErrorSignal: Signal[Boolean]) = {
    select(
      cls := "enchant-text",
      cls("x-hasError") <-- enchantErrorSignal,
      value <-- enchantVar,
      onChange.mapToValue --> enchantVar,
      onChange.mapTo(stateVar.now()) --> Errors.validator(config, stateVar),
      sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
    )
  }
  def enchantmentsSelect(config: ItemBuilderConfig, stateVar: Var[ItemState]) = {
    val sortedEnchants = config.enchants.values.toList.sortBy(_.id)
    val sortedEnchantDownsides = config.enchantDownsides.values.toList.sortBy(_.id)

    val enchant1Var = stateVar.zoomLazy(state => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.enchant1
    })((state, enchant) => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.copy(enchant1 = enchant)
    })
    val enchant2Var = stateVar.zoomLazy(state => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.enchant2
    })((state, enchant) => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.copy(enchant2 = enchant)
    })
    val enchant3Var = stateVar.zoomLazy(state => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.enchant3
    })((state, enchant) => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.copy(enchant3 = enchant)
    })
    val enchant4Var = stateVar.zoomLazy(state => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.enchant4
    })((state, enchant) => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.copy(enchant4 = enchant)
    })
    val downsideVar = stateVar.zoomLazy(state => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.downside
    })((state, enchant) => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.copy(downside = enchant)
    })

    val enchant1ErrorSignal = stateVar.signal.map(state => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.enchant1Error
    })
    val enchant2ErrorSignal = stateVar.signal.map(state => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.enchant2Error
    })
    val enchant3ErrorSignal = stateVar.signal.map(state => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.enchant3Error
    })
    val enchant4ErrorSignal = stateVar.signal.map(state => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.enchant4Error
    })

        div(
          cls := "enchantments-container",
          enchantSelectComponent(config, sortedEnchants, stateVar, enchant1Var, enchant1ErrorSignal),
          enchantSelectComponent(config, sortedEnchants, stateVar, enchant2Var, enchant2ErrorSignal),
          enchantSelectComponent(config, sortedEnchants, stateVar, enchant3Var, enchant3ErrorSignal),
          enchantSelectComponent(config, sortedEnchants, stateVar, enchant4Var, enchant4ErrorSignal),
          select(
            cls := "downside-text",
            value <-- downsideVar,
            onChange.mapToValue --> downsideVar,
            sortedEnchantDownsides.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          )
        )
  }
}


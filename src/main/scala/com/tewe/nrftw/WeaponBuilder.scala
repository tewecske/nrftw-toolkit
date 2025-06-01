package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object WeaponBuilder {

  val _ = Stylesheet // Use import to prevent DCE

  def createState(config: WeaponBuilderConfig, initState: Option[String]): WeaponState = {
    val itemState = ItemBuilder.createState(config.itemConfig, initState)
    WeaponState(itemState = itemState, config.weaponTypes.head)
  }

  def apply(config: WeaponBuilderConfig, stateVar: Var[WeaponState]): HtmlElement = {

    val itemStateVar = stateVar.zoomLazy(_.itemState)((state, itemState) => state.copy(itemState = itemState))
    val itemGemStateVar = stateVar.zoomLazy(_.itemState.gemOption)((state, gem) => state.copy(itemState = state.itemState.copy(gemOption = gem)))
    val itemGemShowModalVar = Var(false)
    val itemGemModal = Modal.gemsModal(config.itemConfig.itemSlot, itemGemShowModalVar, gems, gem => itemGemStateVar.update(_ => Option(gem)))
    val slot = config.itemConfig.itemSlot.name
      div(
        cls := "item-card",
        itemGemModal,
        div(
          cls := "item-header",
          h1(cls := "item-name", slot),
          div(cls := "item-level", span("16")),
          // div(
          //   cls := "item-type-select",
          //   select(
          //     option(selected := true, s"Mesh $slot"),
          //     option(s"Plate $slot"),
          //     option(s"Leather $slot"),
          //     option(s"Cloth $slot")
          //   )
          // )
        ),

        GemsBuilder(config.itemConfig, itemStateVar, itemGemShowModalVar),
        EnchantmentsBuilder.enchantmentsSelect(config.itemConfig, itemStateVar)
      )
  }
}

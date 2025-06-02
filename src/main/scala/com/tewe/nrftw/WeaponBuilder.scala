package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object WeaponBuilder {

  val _ = Stylesheet // Use import to prevent DCE

  def createState(config: WeaponBuilderConfig, initState: Option[String]): WeaponState = {
    initState.filter(!_.isBlank).fold {
      WeaponState(
        itemState = ItemBuilder.createState(config.itemConfig, initState),
        weaponTypeId = config.weaponTypes.head.id
      )
    } { stringState =>
      println(s"STATE: $stringState")
      stringState match {
        case s"W$w-$itemStateString" => 
          WeaponState(
            itemState = ItemBuilder.createState(config.itemConfig, Option(itemStateString)),
            weaponTypeId = w
          )
        case _ => 
          WeaponState(
            itemState = ItemBuilder.createState(config.itemConfig, initState),
            weaponTypeId = config.weaponTypes.head.id
          )
      }
    }
  }

  def apply(config: WeaponBuilderConfig, stateVar: Var[WeaponState]): HtmlElement = {

    val itemStateVar = stateVar.zoomLazy(_.itemState)((state, itemState) => state.copy(itemState = itemState))
    val weaponTypeIdVar = stateVar.zoomLazy(_.weaponTypeId)((state, weaponTypeId) => state.copy(weaponTypeId = weaponTypeId))
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
          div(
            cls := "item-type-select",
            config.weaponTypes match {
              case head :: Nil => div(head.name)
              case weapons =>
                select(
                  value <-- weaponTypeIdVar,
                  onChange.mapToValue --> weaponTypeIdVar,
                  weapons.map(weaponType =>
                      option(value := weaponType.id, weaponType.name))
                )
            }
          )
        ),

        GemsBuilder(config.itemConfig, itemStateVar, itemGemShowModalVar),
        EnchantmentsBuilder.enchantmentsSelect(config.itemConfig, itemStateVar)
      )
  }
}

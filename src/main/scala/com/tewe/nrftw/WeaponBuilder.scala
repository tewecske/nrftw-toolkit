package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import com.tewe.nrftw.WeaponType.allWeapons
import com.tewe.nrftw.Errors

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
        case s"W$w-$itemStateString-R-$runeState" => 
          val runesList = runeState.split("-").toSeq.flatMap(id => runes.find(_.id == id))
          WeaponState(
            itemState = ItemBuilder.createState(config.itemConfig, Option(itemStateString)),
            weaponTypeId = w,
            rune1Option = runesList.lift(0),
            rune2Option = runesList.lift(1),
            rune3Option = runesList.lift(2),
            rune4Option = runesList.lift(3)
          )
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
    // TODO try to remove
    val weaponTypeSignal = weaponTypeIdVar.signal.distinct.map(weaponId => allWeapons.find(_.id == weaponId).head)
    val itemGemStateVar = stateVar.zoomLazy(_.itemState.gemOption)((state, gem) => state.copy(itemState = state.itemState.copy(gemOption = gem)))
    val itemGemShowModalVar = Var(false)
    val itemGemModal = Modal.gemsModal(config.itemConfig.itemSlot, itemGemShowModalVar, gems, gem => itemGemStateVar.update(_ => Option(gem)))

    val rune1StateVar = stateVar.zoomLazy(_.rune1Option)((state, rune1Option) => state.copy(rune1Option = rune1Option))
    val rune2StateVar = stateVar.zoomLazy(_.rune2Option)((state, rune2Option) => state.copy(rune2Option = rune2Option))
    val rune3StateVar = stateVar.zoomLazy(_.rune3Option)((state, rune3Option) => state.copy(rune3Option = rune3Option))
    val rune4StateVar = stateVar.zoomLazy(_.rune4Option)((state, rune4Option) => state.copy(rune4Option = rune4Option))
    val rune1ErrorSignal = stateVar.signal.map(_.rune1Error)
    val rune2ErrorSignal = stateVar.signal.map(_.rune2Error)
    val rune3ErrorSignal = stateVar.signal.map(_.rune3Error)
    val rune4ErrorSignal = stateVar.signal.map(_.rune4Error)
    val runesShowModalVar = Var(false)
    val runesModalCallbackVar = Var((rune: Rune) => rune1StateVar.update(_ => Option(rune)))
    val runesModal = Modal.runesModal(weaponTypeIdVar, runesShowModalVar, runes, runesModalCallbackVar)

    val slot = config.itemConfig.itemSlot.name
      div(
        cls := "item-card",
        itemGemModal,
        runesModal,
        div(
          cls := "item-header",
          h1(cls := "item-name", slot),
          div(cls := "item-level", span("16")),
          div(
            cls := "item-type-select",
            config.weaponTypes.toList match {
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

      child <-- weaponTypeSignal.map(weaponType =>
          println(s"weaponType: $weaponType")
          if (weaponType == WeaponType.Shield) {
            div()
          } else {
            div(
              cls := "runes-container",
              RunesBuilder(config, rune1StateVar, rune1ErrorSignal, runesShowModalVar, runesModalCallbackVar, (rune: Rune) => {
                rune1StateVar.update(_ => Option(rune))
                stateVar.update(state => Errors.errors(config, state))
              }),
              RunesBuilder(config, rune2StateVar, rune2ErrorSignal, runesShowModalVar, runesModalCallbackVar, (rune: Rune) => {
                rune2StateVar.update(_ => Option(rune))
                stateVar.update(state => Errors.errors(config, state))
              }),
              RunesBuilder(config, rune3StateVar, rune3ErrorSignal, runesShowModalVar, runesModalCallbackVar, (rune: Rune) => {
                rune3StateVar.update(_ => Option(rune))
                stateVar.update(state => Errors.errors(config, state))
              }),
              RunesBuilder(config, rune4StateVar, rune4ErrorSignal, runesShowModalVar, runesModalCallbackVar, (rune: Rune) => {
                rune4StateVar.update(_ => Option(rune))
                stateVar.update(state => Errors.errors(config, state))
              }),
            )
          }),
        GemsBuilder(config.itemConfig, itemStateVar, itemGemShowModalVar),
        EnchantmentsBuilder.enchantmentsSelect(config.itemConfig, itemStateVar)
      )
  }
}

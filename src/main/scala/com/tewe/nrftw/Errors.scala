package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Errors {

  def errors(fullState: FullState): FullState = {
    val rings = fullState.ring1StateOption ++ fullState.ring2StateOption ++ fullState.ring3StateOption
    val ring1Error = rings.count(ring => fullState.ring1StateOption.exists(_.id == ring.id)) > 1
    val ring2Error = rings.count(ring => fullState.ring2StateOption.exists(_.id == ring.id)) > 1
    val ring3Error = rings.count(ring => fullState.ring3StateOption.exists(_.id == ring.id)) > 1
    if (ring1Error || ring2Error || ring3Error) {
      fullState.copy(
        ring1Error = ring1Error,
        ring2Error = ring2Error,
        ring3Error = ring2Error,
      )
    } else {
      fullState
    }
  }

  def errors(config: WeaponBuilderConfig, weaponState: WeaponState): WeaponState = {
    // println(s"WeaponState errors called for ${config.itemConfig.itemSlot}")
    val itemStateChecked = errors(config.itemConfig, weaponState.itemState)
    val weaponStateWithItemStateUpdated = if (itemStateChecked != weaponState.itemState) {
      weaponState.copy(itemState = errors(config.itemConfig, weaponState.itemState))
    } else {
      weaponState
    }
    val runes = weaponState.rune1Option ++ weaponState.rune2Option ++ weaponState.rune3Option ++ weaponState.rune4Option
    val rune1Error = runes.count(rune => weaponState.rune1Option.exists(_.id == rune.id)) > 1
    val rune2Error = runes.count(rune => weaponState.rune2Option.exists(_.id == rune.id)) > 1
    val rune3Error = runes.count(rune => weaponState.rune3Option.exists(_.id == rune.id)) > 1
    val rune4Error = runes.count(rune => weaponState.rune4Option.exists(_.id == rune.id)) > 1
    if (rune1Error || rune2Error || rune3Error || rune4Error) {
      // println(s"WeaponState has errors for ${config.itemConfig.itemSlot}")
      weaponStateWithItemStateUpdated.copy(
        rune1Error = rune1Error,
        rune2Error = rune2Error,
        rune3Error = rune3Error,
        rune4Error = rune4Error,
      )

    } else {
      weaponStateWithItemStateUpdated
    }
  }

  def errors(config: ItemBuilderConfig, itemState: ItemState): ItemState = {
    val groups = List(
      config.enchants(itemState.enchant1).group,
      config.enchants(itemState.enchant2).group,
      config.enchants(itemState.enchant3).group,
      config.enchants(itemState.enchant4).group,
    )
    val enchant1Error = groups.count(_ == groups(0)) > 1
    val enchant2Error = groups.count(_ == groups(1)) > 1
    val enchant3Error = groups.count(_ == groups(2)) > 1
    val enchant4Error = groups.count(_ == groups(3)) > 1
    if (enchant1Error || enchant2Error || enchant3Error || enchant4Error) {
      itemState.copy(
        enchant1Error = enchant1Error,
        enchant2Error = enchant2Error,
        enchant3Error = enchant3Error,
        enchant4Error = enchant4Error,
      )
    } else {
      itemState
    }
  }

  val validator = (config: ItemBuilderConfig, stateVar: Var[ItemState]) => Observer[ItemState] { state =>
    println(s"Validator ${config.itemSlot}")
    stateVar.update(_ => Errors.errors(config, state))
  }

  val weaponValidator = (config: WeaponBuilderConfig, stateVar: Var[WeaponState]) => Observer[WeaponState] { state =>
    println(s"Weapon Validator ${config.itemConfig.itemSlot}")
    stateVar.update(_ => Errors.errors(config, state))
  }

}

package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Errors {

  def errors(fullState: FullState): FullState = {
    println(s"FullState errors called")
    val rings = {
      fullState.ring1StateOption ++ fullState.ring2StateOption ++
        fullState.ring3StateOption
    }
    val ring1Error = {
      rings.count(ring => fullState.ring1StateOption.exists(_.id == ring.id)) >
        1
    }
    val ring2Error = {
      rings.count(ring => fullState.ring2StateOption.exists(_.id == ring.id)) >
        1
    }
    val ring3Error = {
      rings.count(ring => fullState.ring3StateOption.exists(_.id == ring.id)) >
        1
    }
    if (
      ring1Error != fullState.ring1Error ||
      ring2Error != fullState.ring2Error || ring3Error != fullState.ring3Error
    ) {
      println(s"FullState errors has changed")
      fullState.copy(
        ring1Error = ring1Error,
        ring2Error = ring2Error,
        ring3Error = ring3Error,
      )
    } else {
      fullState
    }
  }

  def errors(
    config: WeaponBuilderConfig,
    weaponState: WeaponState,
  ): WeaponState = {
    println(s"WeaponState errors called for ${config.itemConfig.itemSlot}")
    val itemStateChecked = errors(config.itemConfig, weaponState.itemState)
    val weaponStateWithItemStateUpdated = {
      if (itemStateChecked != weaponState.itemState) {
        weaponState.copy(itemState =
          errors(config.itemConfig, weaponState.itemState)
        )
      } else {
        weaponState
      }
    }
    val runes = {
      weaponState.rune1Option ++ weaponState.rune2Option ++
        weaponState.rune3Option ++ weaponState.rune4Option
    }
    val rune1Error =
      runes.count(rune => weaponState.rune1Option.exists(_.id == rune.id)) > 1
    val rune2Error =
      runes.count(rune => weaponState.rune2Option.exists(_.id == rune.id)) > 1
    val rune3Error =
      runes.count(rune => weaponState.rune3Option.exists(_.id == rune.id)) > 1
    val rune4Error =
      runes.count(rune => weaponState.rune4Option.exists(_.id == rune.id)) > 1
    if (
      rune1Error != weaponState.rune1Error ||
      rune2Error != weaponState.rune2Error ||
      rune3Error != weaponState.rune3Error ||
      rune4Error != weaponState.rune4Error
    ) {
      println(
        s"WeaponState errors has changed for ${config.itemConfig.itemSlot}"
      )
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
    println(
      s"ItemState errors called for ${config.itemSlot} and rarity ${itemState
          .itemRarity}"
    )
    val actualEnchants = {
      itemState.itemRarity match {
        case ItemRarity.Plagued =>
          config.enchants
        case ItemRarity.Magic =>
          config.magicEnchants
        case _ =>
          config.enchants
      }
    }
    val groups = itemState.enchants.map(actualEnchants(_).group)
    val errors = groups.map(group => groups.count(_ == group) > 1)
    if (itemState.enchantsError != errors) {
      itemState.copy(enchantsError = errors)
    } else {
      itemState
    }
  }

  val validator = { (config: ItemBuilderConfig, stateVar: Var[ItemState]) =>
    {
      Observer[ItemState] { state =>
        println(s"Validator ${config.itemSlot}")
        stateVar.update(_ => Errors.errors(config, state))
      }
    }
  }

  val weaponValidator = {
    (config: WeaponBuilderConfig, stateVar: Var[WeaponState]) =>
      {
        Observer[WeaponState] { state =>
          println(s"Weapon Validator ${config.itemConfig.itemSlot}")
          stateVar.update(_ => Errors.errors(config, state))
        }
      }
  }

}

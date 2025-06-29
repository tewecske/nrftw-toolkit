package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Errors {

  def errors(fullState: FullState): FullState = {
    Log.debug(s"FullState errors called")
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
      Log.debug(s"FullState errors has changed")
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
    Log.debug(s"WeaponState errors called for ${config.itemConfig.itemSlot}")
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
    val weaponStateRunesChecked = weaponStateWithItemStateUpdated.copy(runes = {
      weaponStateWithItemStateUpdated
        .runes
        .map(
          _.filter(rune => {
            rune
              .weaponTypes
              .exists(_.id == weaponStateWithItemStateUpdated.weaponTypeId)
          })
        )
    }
    )
    val runeCounts = weaponStateRunesChecked
      .runes
      .map(runeOption => {
        weaponStateRunesChecked
          .runes
          .count(_.exists(rune => runeOption.exists(_.id == rune.id)))
      })
    val runesErrors = runeCounts.map(_ > 1)
    Log.info(
      s"itemSlot = ${config
          .itemConfig
          .itemSlot}, runes = ${weaponStateRunesChecked
          .runes
          .flatMap(_.map(_.id))}, runesErrors = $runesErrors"
    )
    if (weaponStateRunesChecked.runesError != runesErrors) {
      weaponStateRunesChecked.copy(runesError = runesErrors)
    } else {
      weaponStateRunesChecked
    }

  }

  def errors(config: ItemBuilderConfig, itemState: ItemState): ItemState = {
    Log.debug(
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
        Log.debug(s"Validator ${config.itemSlot}")
        stateVar.update { _ =>
          Log.debug(s"Update ${config.itemSlot} ItemState by validator")
          Errors.errors(config, state)
        }
      }
    }
  }

  val weaponValidator = {
    (config: WeaponBuilderConfig, stateVar: Var[WeaponState]) =>
      {
        Observer[WeaponState] { state =>
          Log.debug(s"Weapon Validator ${config.itemConfig.itemSlot}")
          stateVar.update { _ =>
            Log.debug(
              s"Update ${config
                  .itemConfig
                  .itemSlot} WeaponState by weaponValidator"
            )
            Errors.errors(config, state)
          }
        }
      }
  }

}

package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Errors {

  def errors(config: WeaponBuilderConfig, weaponState: WeaponState): WeaponState = {
    weaponState.copy(itemState = errors(config.itemConfig, weaponState.itemState))
  }

  def errors(config: ItemBuilderConfig, itemState: ItemState): ItemState = {
    val groups = List(
      config.enchants(itemState.enchant1).group,
      config.enchants(itemState.enchant2).group,
      config.enchants(itemState.enchant3).group,
      config.enchants(itemState.enchant4).group,
    )
    itemState.copy(
      enchant1Error = groups.count(_ == groups(0)) > 1,
      enchant2Error = groups.count(_ == groups(1)) > 1,
      enchant3Error = groups.count(_ == groups(2)) > 1,
      enchant4Error = groups.count(_ == groups(3)) > 1,
    )
  }

  val validator = (config: ItemBuilderConfig, stateVar: Var[ItemState]) => Observer[ItemState] { state =>
    println(s"Validator ${config.itemSlot}")
    stateVar.update(_ => Errors.errors(config, state))
  }

}

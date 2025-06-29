package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import com.tewe.nrftw.WeaponType.allWeapons
import com.tewe.nrftw.items.*
import com.tewe.nrftw.Errors
import com.tewe.nrftw.RunesBuilder.runesSelect

object UtilityBuilder {

  val _ = Stylesheet // Use import to prevent DCE

  def apply(
    config: WeaponBuilderConfig,
    stateVar: Var[WeaponState],
  ): HtmlElement = {

    val itemStateVar = {
      stateVar.zoomLazy(_.itemState)((state, itemState) => {
        Log.debug(
          s"Update ${config
              .itemConfig
              .itemSlot} itemStateVar with stateVar.zoomLazy to ${itemState
              .shortState()}"
        )
        state.copy(itemState = itemState)
      })
    }
    val weaponTypeIdVar = Var(config.weaponTypes.head.id)
    val runesShowModalVar = Var(false)
    val runesModalCallbackVar = Var((rune: Rune) => {})
    val runesModal = Modal.runesModal(
      weaponTypeIdVar,
      runesShowModalVar,
      runes,
      runesModalCallbackVar,
    )

    val runesValidator = {
      (config: WeaponBuilderConfig, stateVar: Var[WeaponState]) =>
        {
          Observer[WeaponState] { state =>
            Log.debug(s"Runes validator ${config.itemConfig.itemSlot}")
            stateVar.update(_ => {
              Log.debug(s"Update WeaponState errors in runesValidator")
              Errors.errors(config, state)
            })
          }
        }
    }

    val slot = config.itemConfig.itemSlot.name
    div(
      cls := "utility-card",
      runesModal,
      div(
        cls := "utility-header",
        div(cls := "item-header", h1(cls := "item-name", slot)),
        runesSelect(config, stateVar, runesShowModalVar, runesModalCallbackVar),
      ),
    )
  }
}

package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import com.tewe.nrftw.CompactComponent.compactComponent

object RunesBuilder {

  @JSImport("@find/**/RunesBuilder.css", JSImport.Namespace)
  @js.native
  private object Stylesheet extends js.Object

  val _ = Stylesheet // Use import to prevent DCE

  def enchantmentsSelect(
    config: ItemBuilderConfig,
    stateVar: Var[WeaponState],
  ) = {
    val weaponTypeSignal = stateVar.signal.map(_.weaponType)
    val runesVar = {
      stateVar.zoomLazy(_.runes)((state, runes) => {
        Log.debug(s"Updating ${config.itemSlot} runesVar ${runes.size}")
        state.copy(runes = runes)
      })
    }
    val runesErrorSignal = stateVar.signal.map(_.runesError)
  }

  def runesSplitComponent(
    index: Int,
    config: WeaponBuilderConfig,
    weaponTypeSignal: Signal[WeaponType],
    runeVar: Var[Rune],
    runesErrorSignal: Signal[List[Boolean]],
  ) = {}

  def apply(
    config: WeaponBuilderConfig,
    stateVar: Var[Option[Rune]],
    errorSignal: Signal[Boolean],
    showModalVar: Var[Boolean],
    modalCallbackVar: Var[Rune => Unit],
    modalCallback: Rune => Unit,
  ): HtmlElement = {
    div(
      cls := "rune-container",
      onClick --> { _ =>
        modalCallbackVar.set(modalCallback)
        showModalVar.set(true)
      },
      child <--
        stateVar
          .signal
          .map(
            _.fold {
              div(cls := "rune-item", div(cls("rune-text"), "Select a Rune"))
            } { rune =>
              div(
                cls := "rune-item",
                cls("x-hasError") <-- errorSignal,
                img(cls("rune-icon"), src(rune.imageSrc)),
                div(
                  cls("rune-text"),
                  // Rune effect like fire/frost/plague/lightning/support
                  // rune.filter(_.extra).fold(cls("magic-text"))(_ => cls("plagued-text")),
                  rune.name,
                ),
              )
            }
          ),
    )

  }

  def runeComponentCompact(rune: Rune, onSelect: Rune => Unit): Element = {
    compactComponent(
      "rune",
      onClick --> { _ =>
        onSelect(rune)
      },
      rune.imageSrc,
      p(
        // Rune type fire/frost/plague/lightning/support
        // cls(if (runeEffect.extra) "plagued-text" else "magic-text"),
        rune.name
      ),
      rune.cost match {
        case FocusCost(value) =>
          p(cls("focus-cost"), s"Focus cost: $value")
        case StaminaCost(value) =>
          p(cls("stamina-cost"), s"Stamina cost: $value")
        case HealthCost(value) =>
          p(cls("health-cost"), s"Health cost: $value")
      },
    )

  }

}

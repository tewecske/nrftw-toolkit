package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object GemsBuilder {

  @JSImport("@find/**/GemsBuilder.css", JSImport.Namespace)
  @js.native private object Stylesheet extends js.Object

  val _ = Stylesheet // Use import to prevent DCE

  def apply(
    config: ItemBuilderConfig,
    stateVar: Var[ItemState],
    showModalVar: Var[Boolean]
  ): HtmlElement = {
    val gemOptionVar = stateVar.zoomLazy(_.gemOption)((state, gem) => state.copy(gemOption = gem))
    // val gemOptionVar: Var[Option[Gem]] = Var(None)
    div(
      cls := "gem-container",
      onClick --> { _ =>
        println("TEST")
        showModalVar.set(true)
      },
      child <-- gemOptionVar.signal.map(_.fold {
      div(cls := "gem-item",
        div(
          cls("gem-text"),
          "Select a Gem"
        )
      )
        } { gem =>
      div(cls := "gem-item",
        img(
          cls("gem-icon"),
          src(gem.imageSrc),
        ),
        div(
          cls("gem-text"),
          gem.value
        )
      )
        })
    )
  }

  def gemComponentCompact(
      itemSlot: ItemSlot,
      gem: Gem,
      onSelect: Gem => Unit
  ): Element = {
    div(
      cls("gem-container-compact"),
      onClick --> { _ => onSelect(gem) },
      div(
        cls("compact-gem-item"),
        img(
          cls("compact-gem-icon"),
          src(gem.imageSrc),
        ),
        div(
          cls("compact-gem-text"),
          gem.gemEffects.map(gemEffect =>
            div(
              h3(gemEffect.itemSlot.toString),
              p(gemEffect.value)
            )
          )

        )
      ),
    )
  }
}



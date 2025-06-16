package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import com.tewe.nrftw.CompactComponent.compactComponent

object GemsBuilder {

  @JSImport("@find/**/GemsBuilder.css", JSImport.Namespace)
  @js.native
  private object Stylesheet extends js.Object

  val _ = Stylesheet // Use import to prevent DCE

  def apply(
    config: ItemBuilderConfig,
    gemStateVar: Var[Option[Gem]],
    showModalVar: Var[Boolean],
  ): HtmlElement = {
    div(
      cls := "gem-container",
      onClick --> { _ =>
        println("TEST")
        showModalVar.set(true)
      },
      child <--
        gemStateVar
          .signal
          .map(
            _.fold {
              div(cls := "gem-item", div(cls("gem-text"), "Select a Gem"))
            } { gem =>
              val gemEffect = gem.gemEffects.find(_.itemSlot == config.itemSlot)
              div(
                cls := "gem-item",
                gemEffect
                  .filter(_.extra)
                  .fold(cls("rarity-magic"))(_ => cls("rarity-plagued")),
                img(cls("gem-icon"), src(gem.imageSrc)),
                div(cls("gem-text"), gemEffect.fold("")(_.value)),
              )
            }
          ),
    )
  }

  def gemComponentCompact(
    itemSlot: ItemSlot,
    gem: Gem,
    onSelect: Gem => Unit,
  ): Element = {
    compactComponent(
      "gem",
      onClick --> { _ =>
        onSelect(gem)
      },
      gem.imageSrc,
      gem
        .gemEffects
        .find(_.itemSlot == itemSlot)
        .map(gemEffect => {
          div(
            cls(
              if (gemEffect.extra)
                "rarity-plagued"
              else
                "rarity-magic"
            ),
            h3(cls("compact-gem-item-slot"), gemEffect.itemSlot.name),
            p(cls("compact-gem-effect"), gemEffect.value),
          )
        }),
    )

  }
}

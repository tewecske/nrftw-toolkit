package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object RingBuilder {

  val _ = Stylesheet // Use import to prevent DCE

  def ringComponentCompact(
      ringData: RingData,
      onSelect: RingData => Unit
  ): Element = {
    div(
      cls("ring-container-compact"),
      onClick --> { _ => onSelect(ringData) },
      div(
        cls("compact-ring-header"),
        img(
          cls("compact-ring-image"),
          src(ringData.imageSrc),
          alt("Ring Compact")
        ),
        div(
          cls("compact-ring-titles"),
          h3(cls("compact-ring-name"), ringData.name),
          p(cls("compact-ring-type"), "Ring")
        )
      ),
      div(
        cls("enchantments-container-compact"),
        ringData.enchantments.map(enchant =>
          div(
            cls("enchantment-item-compact item-type-magic"),
            span(enchant)
          )
        ),
      )
    )
  }

  def ringComponentFull(
      ringDataVar: Var[RingData],
      showModalVar: Var[Boolean]
  ): Element = {

    div(
      cls("ring-big-container"),
      onClick --> { _ =>
        println(s"ring-big-container clicked")
        showModalVar.set(true)
      }, // Clickable area for the entire component to open modal
      div(
        cls("ring-header"),
        img(
          cls("ring-image"),
          src <-- ringDataVar.signal.map(_.imageSrc),
          alt("Plagued Ring")
        ),
        div(
          cls("ring-titles"),
          h2(cls("ring-name", "item-type-magic"), text <-- ringDataVar.signal.map(_.name)),
          p(cls("ring-type"), "Ring")
        )
      ),
      div(
        cls("resistances-container"),
        div(
          cls("resistance"),
          div(
            cls("resistance-type"),
            span("⚡"),
            s"Electric Resistance",
          ),
          div(cls("resistance-value"), s"+10%")
        ),
        div(
          cls("resistance"),
          div(
            cls("resistance-type"),
            span("☣️"),
            s"Plague Resistance",
          ),
          div(cls("resistance-value"), s"+11%")
        )
      ),
      div(
        cls("enchantments-container"),
        div(cls("enchantment-header"), "Enchantments 2/2"),
        children <-- ringDataVar.signal.map(_.enchantments.map(enchant =>
          div(
            // cls(s"enchantment-item item-type-${enchant.enchantType}"),
            cls(s"enchantment-item item-type-magic"),
            span(enchant)
          )
        ))
      ),
      div(cls("item-quote"), text <-- ringDataVar.signal.map(_.description)),
      div(
        cls := "required-level",
        "Required Level: ",
        span(cls := "level-value", text <-- ringDataVar.signal.map(r => s"${r.requiredLevel}"))
      ),
      div(cls("bottom-stats"),
        div(cls("durability"),
          span(cls("durability-icon"), "🗡"),
          span(cls("durability-value"), text <-- ringDataVar.signal.map(r => s"${r.durability}/${r.durability}"))
        ),
        div(cls("weigth"),
          span(cls("weight-icon"), "⚖"),
          span(cls("weight-value"), text <-- ringDataVar.signal.map(r => s"${r.weight}"))
        )
      ),
    )
  }


  case class RingState(id: String)

  def ringNameComponent(name: String, imagePath: String): Div = {
    div(
      cls := "ring-name-component",
      select(
        cls := "ring-select",
        option(
          selected := true,
          div(
            cls := "ring-option-content",
            img(
              cls := "ring-image",
              src := imagePath,
              alt := name
            ),
            span(cls := "ring-name", name)
          )
        ),
      )
    )
  }

}

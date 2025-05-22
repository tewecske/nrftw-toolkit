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
      cls("ring-compact-container"),
      onClick --> { _ => onSelect(ringData) },
      div(
        cls("ring-compact-header"),
        img(
          cls("compact-ring-image"),
          src(ringData.imageSrc), // Use imageSrc from ringData
          alt("Ring Compact")
        ),
        div(
          cls("compact-ring-titles"),
          h3(cls("compact-ring-name"), ringData.name),
          p(cls("compact-ring-type"), "Ring")
        )
      ),
      div(
        cls("compact-enchantments"),
        p(cls("compact-enchantments-title"), "Enchantments 2/2"),
        ringData.enchantments.map(enchant =>
          div(
            cls("compact-enchantment-item"),
            // div(cls("compact-enchantment-icon lifesteal")),
            span(cls("compact-enchantment-text"), enchant)
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
          h2(cls("ring-name"), text <-- ringDataVar.signal.map(_.name)),
          p(cls("ring-type"), "Ring")
        )
      ),
      div(
        cls("resistances"),
        div(
          cls("resistance-item"),
          div(cls("resistance-icon electric")),
          span(cls("resistance-text"), s"ELECTRIC RESISTANCE"),
          span(cls("resistance-value"), s"+10%")
        ),
        div(
          cls("resistance-item"),
          div(cls("resistance-icon plague")),
          span(cls("resistance-text"), s"PLAGUE RESISTANCE"),
          span(cls("resistance-value"), s"+11%")
        )
      ),
      div(
        cls("enchantments"),
        p(cls("enchantments-title"), "Enchantments 2/2"),
        children <-- ringDataVar.signal.map(_.enchantments.map(enchant =>
          div(
            cls("enchantment-item"),
            div(cls("enchantment-icon")),
            span(cls("enchantment-text"), enchant)
          )
        ))
      ),
      p(cls("flavor-text"), text <-- ringDataVar.signal.map(_.description)),
      p(cls("required-level"), text <-- ringDataVar.signal.map(r => s"REQUIRED LEVEL: ${r.requiredLevel}")),
      div(cls("bottom-stats"),
        div(cls("stat-item"),
          span(cls("stat-icon durability")),
          span(cls("stat-value"), text <-- ringDataVar.signal.map(r => s"${r.durability}/${r.durability}"))
        ),
        div(cls("stat-item"),
          span(cls("stat-icon weight")),
          span(cls("stat-value"), text <-- ringDataVar.signal.map(r => s"${r.weight}"))
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

  def apply(): HtmlElement = {

      div(
        cls := "ring-card",
        div(
          cls := "ring-header",
          ringNameComponent("Plagued Ring", "/images/ring-plaguedRing.png"),
          div(cls := "item-type", "Ring")
        ),

        div(
          cls := "resistances-container",
          div(
            cls := "resistance",
            div(cls := "resistance-type", span("⚡"), " Electric Resistance"),
            div(cls := "resistance-value", "+18%")
          ),
          div(
            cls := "resistance",
            div(cls := "resistance-type", span("☣"), " Plague Resistance"),
            div(cls := "resistance-value", "+21%")
          )
        ),

        div(
          cls := "section",
          div(
            cls := "section-header",
            div(cls := "section-title", "Enchantments"),
            div(cls := "section-count", "2/2")
          ),
          div(
            cls := "enchantment lifesteal-text",
            "Lifesteal increased by 16% at Low Health"
          ),
          div(
            cls := "enchantment negative-text",
            "Max Health decreased by 21%"
          )
        ),

        div(
          cls := "item-quote",
          "\"Feel unwell. Feel nothing. Feel this blessing of decay.\" – Prayer of Sickness"
        ),

        div(
          cls := "required-level",
          "Required Level: ",
          span(cls := "level-value", "11")
        ),

        div(
          cls := "item-status",
          div(
            cls := "durability",
            span(cls := "durability-icon", "🗡"),
            span(cls := "durability-value", "94/100")
          ),
          div(
            cls := "weight",
            span(cls := "weight-icon", "⚖"),
            span(cls := "weight-value", "0.0")
          )
        )
      )
  }
}

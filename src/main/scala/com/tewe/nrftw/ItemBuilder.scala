package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object ItemBuilder {

  val _ = Stylesheet // Use import to prevent DCE

  def createState(config: ItemBuilderConfig, initState: Option[String]): ItemState = {
    val sortedEnchants = config.enchants.values.toList.sortBy(_.id)
    val sortedEnchantDownsides = config.enchantDownsides.values.toList.sortBy(_.id)

    initState.filter(!_.isBlank).fold {
      ItemState(
        enchant1 = sortedEnchants.head.id,
        enchant2 = sortedEnchants.head.id,
        enchant3 = sortedEnchants.head.id,
        enchant4 = sortedEnchants.head.id,
        downside = sortedEnchantDownsides.head.id
      )
      } { stringState =>
        println(s"STATE: $stringState")
        stringState match {
          case s"$e1-$e2-$e3-$e4-$d" => ItemState(e1, e2, e3, e4, d)
          case _ => 
            ItemState(
              enchant1 = sortedEnchants.head.id,
              enchant2 = sortedEnchants.head.id,
              enchant3 = sortedEnchants.head.id,
              enchant4 = sortedEnchants.head.id,
              downside = sortedEnchantDownsides.head.id
            )
        }
      }
  }
  def apply(config: ItemBuilderConfig, stateVar: Var[ItemState]): HtmlElement = {
    val sortedEnchants = config.enchants.values.toList.sortBy(_.id)
    val sortedEnchantDownsides = config.enchantDownsides.values.toList.sortBy(_.id)

    val enchant1Var = stateVar.zoomLazy(_.enchant1)((state, enchant) => state.copy(enchant1 = enchant))
    val enchant2Var = stateVar.zoomLazy(_.enchant2)((state, enchant) => state.copy(enchant2 = enchant))
    val enchant3Var = stateVar.zoomLazy(_.enchant3)((state, enchant) => state.copy(enchant3 = enchant))
    val enchant4Var = stateVar.zoomLazy(_.enchant4)((state, enchant) => state.copy(enchant4 = enchant))
    val downsideVar = stateVar.zoomLazy(_.downside)((state, enchant) => state.copy(downside = enchant))

    val enchant1ErrorSignal = stateVar.signal.map(_.enchant1Error)
    val enchant2ErrorSignal = stateVar.signal.map(_.enchant2Error)
    val enchant3ErrorSignal = stateVar.signal.map(_.enchant3Error)
    val enchant4ErrorSignal = stateVar.signal.map(_.enchant4Error)

    val slot = config.itemSlot.toString
      div(
        cls := "item-card",
        div(
          cls := "item-header",
          h1(cls := "item-name", slot),
          div(cls := "item-level", span("16")),
          // div(
          //   cls := "item-type-select",
          //   select(
          //     option(selected := true, s"Mesh $slot"),
          //     option(s"Plate $slot"),
          //     option(s"Leather $slot"),
          //     option(s"Cloth $slot")
          //   )
          // )
        ),

        /*
        div(
          cls := "stats-container",
          div(
            cls := "stat",
            div(cls := "stat-icon base-stat-icon", "👕"),
            div(cls := "stat-value", "135")
          ),
          div(
            cls := "stat",
            div(cls := "stat-icon base-stat-icon", "🛡️"),
            div(cls := "stat-value", "4")
          ),
          div(
            cls := "stat",
            div(cls := "stat-icon resistance-icon", "🔥"),
            div(cls := "stat-name", "Heat Resistance"),
            div(cls := "stat-value", "118")
          ),
          div(
            cls := "stat",
            div(cls := "stat-icon resistance-icon", "❄️"),
            div(cls := "stat-name", "Cold Resistance"),
            div(cls := "stat-value", "133")
          ),
          div(
            cls := "stat",
            div(cls := "stat-icon resistance-icon", "⚡"),
            div(cls := "stat-name", "Electric Resistance"),
            div(cls := "stat-value", "121")
          ),
          div(
            cls := "stat",
            div(cls := "stat-icon resistance-icon", "☣️"),
            div(cls := "stat-name", "Plague Resistance"),
            div(cls := "stat-value", "116")
          )
        ),

        div(
          cls := "section",
          div(
            cls := "section-header",
            div(cls := "section-title", "Gems"),
            div(cls := "section-count", "1/1")
          ),
          select(
            config.gems.map(gem => option(value := gem.value, gem.value))
          )
        ),
      */

        // Enchantments Section
        div(
          cls := "section",
          div(
            cls := "section-header",
            div(cls := "section-title", "Enchantments"),
            div(cls := "section-count", "5/5")
          ),
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant1ErrorSignal,
            value <-- enchant1Var,
            onChange.mapToValue --> enchant1Var,
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant2ErrorSignal,
            value <-- enchant2Var,
            onChange.mapToValue --> enchant2Var,
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant3ErrorSignal,
            value <-- enchant3Var,
            onChange.mapToValue --> enchant3Var,
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant4ErrorSignal,
            value <-- enchant4Var,
            onChange.mapToValue --> enchant4Var,
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "downside-text",
            value <-- downsideVar,
            onChange.mapToValue --> downsideVar,
            sortedEnchantDownsides.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          )
        )
      )
  }
}


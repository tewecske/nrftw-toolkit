package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object StatsBuilder {
  def apply() = {
    div(
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
         cls := "stats-container",
         div(
           cls := "stat",
           div(cls := "stat-icon", "⚔️"),
           div(cls := "stat-value", "319")
         ),
         div(
           cls := "stat",
           div(cls := "stat-icon", "👊"),
           div(cls := "stat-value", "30")
         )
       ),

       div(
         cls := "scales-with",
         div(cls := "scales-text", "Scales with:"),
         select(
           cls := "scales-select",
           option(selected := true, "💎 Strength"),
           option("🔮 Intelligence"),
           option("🏹 Dexterity"),
           option("✨ Faith")
         ),
         select(
           cls := "scales-select",
           option(selected := true, ""),
           option("🔮 Intelligence"),
           option("💎 Strength"),
           option("🏹 Dexterity"),
           option("✨ Faith")
         )
       ),

       div(
         cls := "rune-slots",
         div(
           cls := "rune-slot",
           div(cls := "slot-number", "1"),
           select(
             cls := "slot-select",
             option(selected := true, "Crushing Slam"),
             option("Heavy Strike"),
             option("Ground Pound"),
             option("Whirlwind")
           )
         ),
         div(
           cls := "rune-slot",
           div(cls := "slot-number", "2"),
           select(
             cls := "slot-select",
             option(selected := true, "Empty"),
             option("Overhead Smash"),
             option("Spinning Attack"),
             option("Earth Shatter")
           )
         ),
         div(
           cls := "rune-slot",
           div(cls := "slot-number", "3"),
           select(
             cls := "slot-select",
             option(selected := true, "Empty"),
             option("Charge Attack"),
             option("Defensive Stance"),
             option("Berserk Rage")
           )
         ),
         div(
           cls := "rune-slot",
           div(cls := "slot-number", "4"),
           select(
             cls := "slot-select",
             option(selected := true, "Empty"),
             option("Mighty Cleave"),
             option("Ground Tremor"),
             option("Battle Roar")
           )
         )
       ),

       div(
         cls := "resource-bar",
         div(
           cls := "cost",
           div(cls := "resource-icon cost-icon", "💰"),
           div(cls := "resource-label", "Cost"),
           div(cls := "resource-value", "20")
         ),
         div(
           cls := "gain",
           div(cls := "resource-icon gain-icon", "💎"),
           div(cls := "resource-label", "Gain"),
           div(cls := "resource-value", "6")
         )
       ),
    )
  }
}

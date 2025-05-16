package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object WeaponBuilder {

  val _ = Stylesheet // Use import to prevent DCE

  case class ItemState(
    enchant1: String,
    enchant2: String,
    enchant3: String,
    enchant4: String,
    downside: String, 
    enchant1Error: Boolean = false,
    enchant2Error: Boolean = false,
    enchant3Error: Boolean = false,
    enchant4Error: Boolean = false,
  )

  // def enchants(enchants: List[Enchant], downsides: List[Enchant])
  def apply(config: ItemBuilderConfig): HtmlElement = {
    def errors(itemState: ItemState): ItemState = {
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

    val sortedEnchants = config.enchants.values.toList.sortBy(_.id)
    val sortedEnchantDownsides = config.enchantDownsides.values.toList.sortBy(_.id)
    val stateVar = Var(ItemState(
      enchant1 = sortedEnchants.head.id,
      enchant2 = sortedEnchants.head.id,
      enchant3 = sortedEnchants.head.id,
      enchant4 = sortedEnchants.head.id,
      downside = sortedEnchantDownsides.head.id
    ))

    stateVar.update(state => errors(state))

    val enchant1Var = stateVar.zoomLazy(_.enchant1)((state, enchant) => state.copy(enchant1 = enchant))
    val enchant2Var = stateVar.zoomLazy(_.enchant2)((state, enchant) => state.copy(enchant2 = enchant))
    val enchant3Var = stateVar.zoomLazy(_.enchant3)((state, enchant) => state.copy(enchant3 = enchant))
    val enchant4Var = stateVar.zoomLazy(_.enchant4)((state, enchant) => state.copy(enchant4 = enchant))
    val downsideVar = stateVar.zoomLazy(_.downside)((state, enchant) => state.copy(downside = enchant))

    val enchant1ErrorSignal = stateVar.signal.map(_.enchant1Error)
    val enchant2ErrorSignal = stateVar.signal.map(_.enchant2Error)
    val enchant3ErrorSignal = stateVar.signal.map(_.enchant3Error)
    val enchant4ErrorSignal = stateVar.signal.map(_.enchant4Error)

    val validator = Observer[ItemState] { state =>
      stateVar.update(_ => errors(state))
    }


     div(
       cls := "weapon-card",
       div(
         cls := "weapon-header",
         h1(cls := "weapon-name", "Brutish Cudgel"),
         div(cls := "weapon-level", span("13")),
         div(
           cls := "weapon-type-select",
           select(
             option(selected := true, "Two-Handed Great Club"),
             option("Two-Handed Battle Hammer"),
             option("Two-Handed War Axe"),
             option("Two-Handed Greatsword")
           )
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

     /*
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
            onChange.mapTo(stateVar.now()) --> validator,
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant2ErrorSignal,
            value <-- enchant2Var,
            onChange.mapToValue --> enchant2Var,
            onChange.mapTo(stateVar.now()) --> validator,
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant3ErrorSignal,
            value <-- enchant3Var,
            onChange.mapToValue --> enchant3Var,
            onChange.mapTo(stateVar.now()) --> validator,
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "enchant-text",
            cls("x-hasError") <-- enchant4ErrorSignal,
            value <-- enchant4Var,
            onChange.mapToValue --> enchant4Var,
            onChange.mapTo(stateVar.now()) --> validator,
            sortedEnchants.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          ),
          select(
            cls := "downside-text",
            value <-- downsideVar,
            onChange.mapToValue --> downsideVar,
            onChange.mapTo(stateVar.now()) --> validator,
            sortedEnchantDownsides.map(enchant => option(value := enchant.id, cls := s"enchant-group-${enchant.group}", enchant.value))
          )
       )
     )

  }
}

package com.tewe.nrftw

import ItemSlot.*
import EnchantGroup.*
import scala.collection.mutable.LinkedHashMap

case class Gem(value: String)
enum EnchantGroup {
  case Attack, Defense, Durability, Focus, Healing, Indestructible, Movement, Other, Resistance, Stamina, Weight, Downside
}
case class Enchant(id: String, group: EnchantGroup, value: String)
enum ItemSlot {
  case Helmet, Armor, Pants, Gloves, Weapon, Shield, Bow, Ring
}
case class ItemBuilderConfig(
  itemSlot: ItemSlot,
  gems: List[Gem],
  enchants: Map[String, Enchant],
  enchantDownsides: Map[String, Enchant],
)

val helmetGems = List(Gem("Max Health increased by 10%"))

val helmetEnchants = List(
  Enchant("o:bnc", Other, "Bomb not consumed with 10%-20% chance"),
  
  Enchant("a:hdi", Attack, "Heat Damage increased by 7%-25%"),
  Enchant("a:cdi", Attack, "Cold Damage increased by 7%-25%"),
  Enchant("a:ldi", Attack, "Lightning Damage increased by 7%-25%"),
  Enchant("a:pdi", Attack, "Plague Damage increased by 7%-25%"),
  
  Enchant("f:gb", Focus, "Gain 8-15 Focus on any Buildup"),
  Enchant("f:fcslh", Focus, "Focus Cost decreased by 15%-30% at Low Health"),
  
  Enchant("s:sri", Stamina, "Stamina Recovery increased by up to 7%-25% based on Durability."),
  
  Enchant("i:i", Indestructible, "Indestructible"),
  
  Enchant("h:hid", Healing, "Healing increased by up to 6%-20% based on Durability"),
  Enchant("h:ghra", Healing, "Gain 3%-6% Health once per Rune Attack."),
  Enchant("h:hilf", Healing, "Healing increased by 9%-30% at Low Focus"),
  
  Enchant("w:iwd", Weight, "Item Weight decreased by 7%-15%."),
  
  Enchant("d:dtdlf", Defense, "Damage Taken decreased by 7%-15% at Low Focus"),
  Enchant("d:dtdd", Defense, "Damage Taken decreased by up to 5%-10% based on Durability")
) 

val helmetEnchantDownsides = List(
  Enchant("down:ad", Downside, "Armor Decreased by 20-30%"),
  Enchant("down:dtiadt", Downside, "Damage Taken Increased by 21-30% for 6 seconds After Damage Taken"),
  Enchant("down:u", Downside, "Unrepairable"),
  Enchant("down:dtiff", Downside, "Damage Taken Increased by 26%-40% If Not at Full Focus"),
  Enchant("down:eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down:mfd", Downside, "Max focus decreased by 18-25%"),
  Enchant("down:dfc", Downside, "Drain Focus in Combat"),
  Enchant("down:fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down:lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down:tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down:fgd", Downside, "Focus gain decreased by 32%-50%"),
  Enchant("down:lhc", Downside, "Lose Health in Combat periodically"),
  Enchant("down:mhd", Downside, "Max Health decreased by 14%-20%"),
  Enchant("down:msd", Downside, "Max Stamina decreased by 14%-20%"),
  Enchant("down:sci", Downside, "Stamina Cost increased by 13%-20%"),
  Enchant("down:srd", Downside, "Stagger Resistance decreased by 26%-40%"),
)
  
val helmetPlagued = ItemBuilderConfig(
  itemSlot = Helmet,
  gems = helmetGems,
  enchants = helmetEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = helmetEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val armorGems = List(Gem("Max Health increased by 10%"))

val armorEnchants = List(
    Enchant("o:pdic", Other, "Poise Defense increased by 5-10 while Charging"),
  
  Enchant("a:fea", Attack, "Frozen Enemy Armor decreased by 25%-50%"),
  Enchant("a:ddt", Attack, "Deal 4%-15% Damage on Damage Taken"),
  Enchant("a:dis", Attack, "Damage increased by 15%-30% for 6 seconds after Stagger."),
  Enchant("a:bdi", Attack, "Burn Damage increased by 25%-50%"),
  Enchant("a:iesrd", Attack, "Infected Enemy Stagger Resistance decreased by 15%-30%"),
  
  Enchant("f:gi", Focus, "Focus Gain increased by up to 10%-25% based on Durability"),
  Enchant("f:cd", Focus, "Focus Cost decreased by 15%-30% for 5s after Stagger."),
  
  Enchant("s:sri", Stamina, "Stamina Recovery increased by up to 7%-25% based on Durability."),
  
  Enchant("i:i", Indestructible, "Indestructible"),
  
  Enchant("h:mhi", Healing, "Max Health increased by 5%-10% of Max Focus"),
  Enchant("h:rh", Healing, "Regenerate Health in Combat Periodically"),
  Enchant("h:hl", Healing, "Hunger Limit increased by 1."),
  
  Enchant("m:os", Movement, "Overall Speed increased by 7%-15% for 6 seconds after Shock Buildup"),
  
  Enchant("w:iw", Weight, "Item Weight decreased by 7%-15%."),
  
  Enchant("d:dtdb", Defense, "Damage Taken decreased by 25%-50% for 6 seconds after any buildup taken."),
  Enchant("d:dtdlh", Defense, "Damage Taken decreased by 12%-25% at Low Health"),
  Enchant("d:dtdff", Defense, "Damage Taken decreased by 12%-25% at Full Focus"),
  Enchant("d:dtdc", Defense, "Damage Taken decreased by 10%-20% while Charging")
  )

val armorEnchantDownsides = List(
  Enchant("down:ad", Downside, "Armor Decreased by 20-30%"),
  Enchant("down:meld", Downside, "Max Equip Load Decreased"),
  Enchant("down:dtiadt", Downside, "Damage Taken Increased by 21-30% for 6 seconds After Damage Taken"),
  Enchant("down:u", Downside, "Unrepairable"),
  Enchant("down:dtiff", Downside, "Damage Taken Increased by 26%-40% If Not at Full Focus"),
  Enchant("down:eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down:mfd", Downside, "Max focus decreased by 18-25%"),
  Enchant("down:dfc", Downside, "Drain Focus in Combat"),
  Enchant("down:fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down:lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down:tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down:hd", Downside, "Healing decreased by 20%-30%"),
  Enchant("down:lhc", Downside, "Lose Health in Combat periodically"),
  Enchant("down:mhd", Downside, "Max Health decreased by 14%-20%"),
  Enchant("down:msd", Downside, "Max Stamina decreased by 14%-20%"),
  Enchant("down:sci", Downside, "Stamina Cost increased by 13%-20%"),
  Enchant("down:srd", Downside, "Stagger Resistance decreased by 26%-40%"),
  )

val armorPlagued = ItemBuilderConfig(
  itemSlot = Armor,
  gems = armorGems,
  enchants = armorEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = armorEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val pantsGems = List(Gem("Max Health increased by 10%"))

val pantsEnchants = List(
    Enchant("o:pdic", Other, "Poise Defense increased by 5-10 while Charging"),
  
  Enchant("a:dddd", Attack, "Deal 4%-15% Damage on Damage Dodged"),
  Enchant("a:dis", Attack, "Damage increased by 5%-10% after Sprinting for 2 seconds."),
  Enchant("a:didd", Attack, "Damage increased by 15%-30% for 6 seconds after Damage Dodged."),
  
  Enchant("f:fgid", Focus, "Focus Gain increased by up to 10%-25% based on Durability"),
  
  Enchant("s:scdlh", Stamina, "Stamina Cost decreased by 15%-30% at Low Health"),
  Enchant("s:dscdlh", Stamina, "Dodge Stamina Cost decreased by 18%-35% at Low Health"),
  Enchant("s:scdlf", Stamina, "Stamina Cost decreased by 10%-20% at Low Focus"),
  
  Enchant("i:i", Indestructible, "Indestructible"),
  
  Enchant("h:hid", Healing, "Healing increased by up to 6%-20% based on Durability"),
  Enchant("h:ghdd", Healing, "Gain 3%-5% Health on Damage Dodged"),
  Enchant("h:msimf", Healing, "Max Stamina increased by 3%-5% of Max Focus"),
  
  Enchant("m:msica", Movement, "Movement Speed increased by 7%-15% for 6s after Charged Attack."),
  
  Enchant("w:iwd", Weight, "Item Weight decreased by 7%-15%."),
  
  Enchant("d:dtdd", Defense, "Damage Taken decreased by up to 5%-10% based on Durability")
)

val pantsEnchantDownsides = List(
  Enchant("down:ad", Downside, "Armor Decreased by 20-30%"),
  Enchant("down:dtiadt", Downside, "Damage Taken Increased by 21-30% for 6 seconds After Damage Taken"),
  Enchant("down:u", Downside, "Unrepairable"),
  Enchant("down:dtiff", Downside, "Damage Taken Increased by 26%-40% If Not at Full Focus"),
  Enchant("down:eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down:mfd", Downside, "Max focus decreased by 18-25%"),
  Enchant("down:dfc", Downside, "Drain Focus in Combat"),
  Enchant("down:fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down:lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down:tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down:lhc", Downside, "Lose Health in Combat periodically"),
  Enchant("down:mhd", Downside, "Max Health decreased by 14%-20%"),
  Enchant("down:msd", Downside, "Max Stamina decreased by 14%-20%"),
  Enchant("down:sci", Downside, "Stamina Cost increased by 13%-20%"),
  Enchant("down:dsci", Downside, "Dodge Stamina Cost increased by 26%-40%"),
  Enchant("down:msd", Downside, "Movement Speed decreased by 7%–25%"),
  Enchant("down:hr", Downside, "Heavy Roll only."),
  Enchant("down:srd", Downside, "Stagger Resistance decreased by 26%-40%"),
  )

val pantsPlagued = ItemBuilderConfig(
  itemSlot = Pants,
  gems = pantsGems,
  enchants = pantsEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = pantsEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val glovesGems = List(Gem("Max Health increased by 10%"))

val glovesEnchants = List(
  Enchant("o:bdi", Other, "Bomb Damage increased by 15%-30%"),
  
  Enchant("a:ddp", Attack, "Deal 15%-30% Damage on Parry"),
  Enchant("a:dip", Attack, "Damage increased by 15%-30% for 6 seconds after Parry."),
  Enchant("a:bbi", Attack, "Burn Buildup increased by 15%-30%."),
  Enchant("a:sbi", Attack, "Shock Buildup increased by 15%-30%."),
  Enchant("a:fbi", Attack, "Freeze Buildup increased by 15%-30%."),
  Enchant("a:ibi", Attack, "Infect Buildup increased by 15%-30%."),
  Enchant("a:pdi", Attack, "Physical Damage increased by 7%-25%"),
  
  Enchant("f:fgid", Focus, "Focus Gain increased by up to 10%-25% based on Durability"),
  
  Enchant("s:srid", Stamina, "Stamina Recovery increased by up to 7%-25% based on Durability."),
  Enchant("s:irss", Stamina, "Instantly restore 25–40 Stamina on Stagger."),
  
  Enchant("i:i", Indestructible, "Indestructible"),
  
  Enchant("h:ghp", Healing, "Gain 2%-6% Health on Parry."),
  Enchant("h:hid", Healing, "Healing increased by up to 6%-20% based on Durability"),
  Enchant("h:ghk", Healing, "Gain 5%-10% Health on Kill."),
  Enchant("h:ghse", Healing, "Gain 5%-10% Health on Staggering an Enemy"),
  
  Enchant("w:iwd", Weight, "Item Weight decreased by 7%-15%."),
  
  Enchant("d:dtdd", Defense, "Damage Taken decreased by up to 5%-10% based on Durability")
)

val glovesEnchantDownsides = List(
  Enchant("down:ad", Downside, "Armor Decreased by 20-30%"),
  Enchant("down:pd", Downside, "Parry disabled"),
  Enchant("down:dtiadt", Downside, "Damage Taken Increased by 21-30% for 6 seconds After Damage Taken"),
  Enchant("down:u", Downside, "Unrepairable"),
  Enchant("down:dtiff", Downside, "Damage Taken Increased by 26%-40% If Not at Full Focus"),
  Enchant("down:eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down:mfd", Downside, "Max focus decreased by 18-25%"),
  Enchant("down:dfc", Downside, "Drain Focus in Combat"),
  Enchant("down:fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down:lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down:tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down:lhc", Downside, "Lose Health in Combat periodically"),
  Enchant("down:mhd", Downside, "Max Health decreased by 14%-20%"),
  Enchant("down:msd", Downside, "Max Stamina decreased by 14%-20%"),
  Enchant("down:sci", Downside, "Stamina Cost increased by 13%-20%"),
  Enchant("down:asci", Downside, "Attack Stamina Cost increased by 20%-30%"),
  Enchant("down:srd", Downside, "Stagger Resistance decreased by 26%-40%"),
  )

val glovesPlagued = ItemBuilderConfig(
  itemSlot = Gloves,
  gems = glovesGems,
  enchants = glovesEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = glovesEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)


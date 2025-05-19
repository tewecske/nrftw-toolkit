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
  Enchant("o_bnc", Other, "Bomb not consumed with 10%-20% chance"),
  
  Enchant("a_hdi", Attack, "Heat Damage increased by 7%-25%"),
  Enchant("a_cdi", Attack, "Cold Damage increased by 7%-25%"),
  Enchant("a_ldi", Attack, "Lightning Damage increased by 7%-25%"),
  Enchant("a_pdi", Attack, "Plague Damage increased by 7%-25%"),
  
  Enchant("f_gb", Focus, "Gain 8-15 Focus on any Buildup"),
  Enchant("f_fcslh", Focus, "Focus Cost decreased by 15%-30% at Low Health"),
  
  Enchant("s_sri", Stamina, "Stamina Recovery increased by up to 7%-25% based on Durability."),
  
  Enchant("i_i", Indestructible, "Indestructible"),
  
  Enchant("h_hid", Healing, "Healing increased by up to 6%-20% based on Durability"),
  Enchant("h_ghra", Healing, "Gain 3%-6% Health once per Rune Attack."),
  Enchant("h_hilf", Healing, "Healing increased by 9%-30% at Low Focus"),
  
  Enchant("w_iwd", Weight, "Item Weight decreased by 7%-15%."),
  
  Enchant("d_dtdlf", Defense, "Damage Taken decreased by 7%-15% at Low Focus"),
  Enchant("d_dtdd", Defense, "Damage Taken decreased by up to 5%-10% based on Durability")
) 

val helmetEnchantDownsides = List(
  Enchant("down_ad", Downside, "Armor Decreased by 20-30%"),
  Enchant("down_dtiadt", Downside, "Damage Taken Increased by 21-30% for 6 seconds After Damage Taken"),
  Enchant("down_u", Downside, "Unrepairable"),
  Enchant("down_dtiff", Downside, "Damage Taken Increased by 26%-40% If Not at Full Focus"),
  Enchant("down_eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down_mfd", Downside, "Max focus decreased by 18-25%"),
  Enchant("down_dfc", Downside, "Drain Focus in Combat"),
  Enchant("down_fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down_lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down_tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down_fgd", Downside, "Focus gain decreased by 32%-50%"),
  Enchant("down_lhc", Downside, "Lose Health in Combat periodically"),
  Enchant("down_mhd", Downside, "Max Health decreased by 14%-20%"),
  Enchant("down_msd", Downside, "Max Stamina decreased by 14%-20%"),
  Enchant("down_sci", Downside, "Stamina Cost increased by 13%-20%"),
  Enchant("down_srd", Downside, "Stagger Resistance decreased by 26%-40%"),
)
  
val helmetPlagued = ItemBuilderConfig(
  itemSlot = Helmet,
  gems = helmetGems,
  enchants = helmetEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = helmetEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val armorGems = List(Gem("Max Health increased by 10%"))

val armorEnchants = List(
    Enchant("o_pdic", Other, "Poise Defense increased by 5-10 while Charging"),
  
  Enchant("a_fea", Attack, "Frozen Enemy Armor decreased by 25%-50%"),
  Enchant("a_ddt", Attack, "Deal 4%-15% Damage on Damage Taken"),
  Enchant("a_dis", Attack, "Damage increased by 15%-30% for 6 seconds after Stagger."),
  Enchant("a_bdi", Attack, "Burn Damage increased by 25%-50%"),
  Enchant("a_iesrd", Attack, "Infected Enemy Stagger Resistance decreased by 15%-30%"),
  
  Enchant("f_gi", Focus, "Focus Gain increased by up to 10%-25% based on Durability"),
  Enchant("f_cd", Focus, "Focus Cost decreased by 15%-30% for 5s after Stagger."),
  
  Enchant("s_sri", Stamina, "Stamina Recovery increased by up to 7%-25% based on Durability."),
  
  Enchant("i_i", Indestructible, "Indestructible"),
  
  Enchant("h_mhi", Healing, "Max Health increased by 5%-10% of Max Focus"),
  Enchant("h_rh", Healing, "Regenerate Health in Combat Periodically"),
  Enchant("h_hl", Healing, "Hunger Limit increased by 1."),
  
  Enchant("m_os", Movement, "Overall Speed increased by 7%-15% for 6 seconds after Shock Buildup"),
  
  Enchant("w_iw", Weight, "Item Weight decreased by 7%-15%."),
  
  Enchant("d_dtdb", Defense, "Damage Taken decreased by 25%-50% for 6 seconds after any buildup taken."),
  Enchant("d_dtdlh", Defense, "Damage Taken decreased by 12%-25% at Low Health"),
  Enchant("d_dtdff", Defense, "Damage Taken decreased by 12%-25% at Full Focus"),
  Enchant("d_dtdc", Defense, "Damage Taken decreased by 10%-20% while Charging"),
  Enchant("d_dtdd", Defense, "Damage Taken decreased by up to 5%-10% based on Durability")
  )

val armorEnchantDownsides = List(
  Enchant("down_ad", Downside, "Armor Decreased by 20-30%"),
  Enchant("down_meld", Downside, "Max Equip Load Decreased"),
  Enchant("down_dtiadt", Downside, "Damage Taken Increased by 21-30% for 6 seconds After Damage Taken"),
  Enchant("down_u", Downside, "Unrepairable"),
  Enchant("down_dtiff", Downside, "Damage Taken Increased by 26%-40% If Not at Full Focus"),
  Enchant("down_eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down_mfd", Downside, "Max focus decreased by 18-25%"),
  Enchant("down_dfc", Downside, "Drain Focus in Combat"),
  Enchant("down_fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down_lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down_tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down_hd", Downside, "Healing decreased by 20%-30%"),
  Enchant("down_lhc", Downside, "Lose Health in Combat periodically"),
  Enchant("down_mhd", Downside, "Max Health decreased by 14%-20%"),
  Enchant("down_msd", Downside, "Max Stamina decreased by 14%-20%"),
  Enchant("down_sci", Downside, "Stamina Cost increased by 13%-20%"),
  Enchant("down_srd", Downside, "Stagger Resistance decreased by 26%-40%"),
  )

val armorPlagued = ItemBuilderConfig(
  itemSlot = Armor,
  gems = armorGems,
  enchants = armorEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = armorEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val pantsGems = List(Gem("Max Health increased by 10%"))

val pantsEnchants = List(
    Enchant("o_pdic", Other, "Poise Defense increased by 5-10 while Charging"),
  
  Enchant("a_dddd", Attack, "Deal 4%-15% Damage on Damage Dodged"),
  Enchant("a_dis", Attack, "Damage increased by 5%-10% after Sprinting for 2 seconds."),
  Enchant("a_didd", Attack, "Damage increased by 15%-30% for 6 seconds after Damage Dodged."),
  
  Enchant("f_fgid", Focus, "Focus Gain increased by up to 10%-25% based on Durability"),
  
  Enchant("s_scdlh", Stamina, "Stamina Cost decreased by 15%-30% at Low Health"),
  Enchant("s_dscdlh", Stamina, "Dodge Stamina Cost decreased by 18%-35% at Low Health"),
  Enchant("s_scdlf", Stamina, "Stamina Cost decreased by 10%-20% at Low Focus"),
  
  Enchant("i_i", Indestructible, "Indestructible"),
  
  Enchant("h_hid", Healing, "Healing increased by up to 6%-20% based on Durability"),
  Enchant("h_ghdd", Healing, "Gain 3%-5% Health on Damage Dodged"),
  Enchant("h_msimf", Healing, "Max Stamina increased by 3%-5% of Max Focus"),
  
  Enchant("m_msica", Movement, "Movement Speed increased by 7%-15% for 6s after Charged Attack."),
  
  Enchant("w_iwd", Weight, "Item Weight decreased by 7%-15%."),
  
  Enchant("d_dtdd", Defense, "Damage Taken decreased by up to 5%-10% based on Durability")
)

val pantsEnchantDownsides = List(
  Enchant("down_ad", Downside, "Armor Decreased by 20-30%"),
  Enchant("down_dtiadt", Downside, "Damage Taken Increased by 21-30% for 6 seconds After Damage Taken"),
  Enchant("down_u", Downside, "Unrepairable"),
  Enchant("down_dtiff", Downside, "Damage Taken Increased by 26%-40% If Not at Full Focus"),
  Enchant("down_eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down_mfd", Downside, "Max focus decreased by 18-25%"),
  Enchant("down_dfc", Downside, "Drain Focus in Combat"),
  Enchant("down_fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down_lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down_tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down_lhc", Downside, "Lose Health in Combat periodically"),
  Enchant("down_mhd", Downside, "Max Health decreased by 14%-20%"),
  Enchant("down_msd", Downside, "Max Stamina decreased by 14%-20%"),
  Enchant("down_sci", Downside, "Stamina Cost increased by 13%-20%"),
  Enchant("down_dsci", Downside, "Dodge Stamina Cost increased by 26%-40%"),
  Enchant("down_msd", Downside, "Movement Speed decreased by 7%–25%"),
  Enchant("down_hr", Downside, "Heavy Roll only."),
  Enchant("down_srd", Downside, "Stagger Resistance decreased by 26%-40%"),
  )

val pantsPlagued = ItemBuilderConfig(
  itemSlot = Pants,
  gems = pantsGems,
  enchants = pantsEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = pantsEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val glovesGems = List(Gem("Max Health increased by 10%"))

val glovesEnchants = List(
  Enchant("o_bdi", Other, "Bomb Damage increased by 15%-30%"),
  
  Enchant("a_ddp", Attack, "Deal 15%-30% Damage on Parry"),
  Enchant("a_dip", Attack, "Damage increased by 15%-30% for 6 seconds after Parry."),
  Enchant("a_bbi", Attack, "Burn Buildup increased by 15%-30%."),
  Enchant("a_sbi", Attack, "Shock Buildup increased by 15%-30%."),
  Enchant("a_fbi", Attack, "Freeze Buildup increased by 15%-30%."),
  Enchant("a_ibi", Attack, "Infect Buildup increased by 15%-30%."),
  Enchant("a_pdi", Attack, "Physical Damage increased by 7%-25%"),
  
  Enchant("f_fgid", Focus, "Focus Gain increased by up to 10%-25% based on Durability"),
  
  Enchant("s_srid", Stamina, "Stamina Recovery increased by up to 7%-25% based on Durability."),
  Enchant("s_irss", Stamina, "Instantly restore 25–40 Stamina on Stagger."),
  
  Enchant("i_i", Indestructible, "Indestructible"),
  
  Enchant("h_ghp", Healing, "Gain 2%-6% Health on Parry."),
  Enchant("h_hid", Healing, "Healing increased by up to 6%-20% based on Durability"),
  Enchant("h_ghk", Healing, "Gain 5%-10% Health on Kill."),
  Enchant("h_ghse", Healing, "Gain 5%-10% Health on Staggering an Enemy"),
  
  Enchant("w_iwd", Weight, "Item Weight decreased by 7%-15%."),
  
  Enchant("d_dtdd", Defense, "Damage Taken decreased by up to 5%-10% based on Durability")
)

val glovesEnchantDownsides = List(
  Enchant("down_ad", Downside, "Armor Decreased by 20-30%"),
  Enchant("down_pd", Downside, "Parry disabled"),
  Enchant("down_dtiadt", Downside, "Damage Taken Increased by 21-30% for 6 seconds After Damage Taken"),
  Enchant("down_u", Downside, "Unrepairable"),
  Enchant("down_dtiff", Downside, "Damage Taken Increased by 26%-40% If Not at Full Focus"),
  Enchant("down_eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down_mfd", Downside, "Max focus decreased by 18-25%"),
  Enchant("down_dfc", Downside, "Drain Focus in Combat"),
  Enchant("down_fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down_lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down_tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down_lhc", Downside, "Lose Health in Combat periodically"),
  Enchant("down_mhd", Downside, "Max Health decreased by 14%-20%"),
  Enchant("down_msd", Downside, "Max Stamina decreased by 14%-20%"),
  Enchant("down_sci", Downside, "Stamina Cost increased by 13%-20%"),
  Enchant("down_asci", Downside, "Attack Stamina Cost increased by 20%-30%"),
  Enchant("down_srd", Downside, "Stagger Resistance decreased by 26%-40%"),
  )

val glovesPlagued = ItemBuilderConfig(
  itemSlot = Gloves,
  gems = glovesGems,
  enchants = glovesEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = glovesEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val weaponGems = List(Gem("Max Health increased by 10%"))

val weaponEnchants = List(
  Enchant("o_gfob", Other, "Gain 5-10 Focus on backstab"),
  Enchant("o_ghob", Other, "Gain 3%-5% Health on Backstab."),
  
  Enchant("a_hi", Attack, "Heat Infusion"),
  Enchant("a_ci", Attack, "Cold Infusion"),
  Enchant("a_li", Attack, "Lightning Infusion"),
  Enchant("a_pi", Attack, "Plague Infusion"),
  Enchant("a_asid", Attack, "Attack Speed increased by 7%-15% based on Durability"),
  Enchant("a_rdi", Attack, "Rune Damage increased by 10%-20%"),
  Enchant("a_diff", Attack, "Damage increased by 15%-30% at Full Focus"),
  Enchant("a_dilh", Attack, "Damage increased by 15%-30% while at Low Health"),
  Enchant("a_difh", Attack, "Damage increased by 15%-30% while at Full Health"),
  Enchant("a_dlei", Attack, "Damage vs Large Enemies increased by 15%-30%"),
  Enchant("a_dif", Attack, "Damage increased by 20%-40% for 20s after Fatality."),
  Enchant("a_dib", Attack, "Damage increased by 15%-30% for 30s after Backstab."),
  Enchant("a_cadi", Attack, "Charged Attack Damage increased by 10%-20%"),
  Enchant("a_nadica", Attack, "Normal attack damage increased by 12%-25% for 5s after Charged Attack."),
  Enchant("a_adira", Attack, "Attack damage increased by 15%-30% for 6s after Rune Attack."),
  Enchant("a_ddse", Attack, "Deal 15%-30% Damage on Staggering an Enemy"),
  Enchant("a_did", Attack, "Damage increased by up to 7%-15% based on Durability"),
  Enchant("a_sdilh", Attack, "Stagger Damage increased by 12%-25% at Low Health"),
  Enchant("a_sdiaf", Attack, "Stagger Damage increased by 20%-40% for 20s after Fatality."),
  
  Enchant("f_gff", Focus, "Gain 12–25 Focus on Fatality."),
  Enchant("f_gfk", Focus, "Gain 8-15 Focus on Kill."),
  
  Enchant("s_srid", Stamina, "Stamina Recovery increased by up to 7%-25% based on Durability."),
  Enchant("s_ascdp", Stamina, "Attack Stamina Cost decreased by 15%-30% for 6 seconds after parry"),
  Enchant("s_scdf", Stamina, "Stamina Cost decreased by 15%-30% for 20s after Fatality."),
  
  Enchant("i_i", Indestructible, "Indestructible"),
  
  Enchant("h_hid", Healing, "Healing increased by up to 6%-20% based on Durability"),
  Enchant("h_ghk", Healing, "Gain 5%-10% Health on Kill."),
  Enchant("h_ghse", Healing, "Gain 5%-10% Health on Staggering an Enemy"),
  Enchant("h_ghca", Healing, "Gain 2%-4% Health on Charged Attack."),
  Enchant("h_ghf", Healing, "Gain 5%-10% Health on Fatality."),
  
  Enchant("m_msira", Movement, "Movement Speed increased by 7%-15% for 5s after Rune Attack."),
  
  Enchant("w_iwd", Weight, "Item Weight decreased by 7%-15%.")
)

val weaponEnchantDownsides = List(
  Enchant("down_u", Downside, "Unrepairable"),
  Enchant("down_eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down_dfc", Downside, "Drain Focus in Combat"),
  Enchant("down_fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down_lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down_tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down_asci", Downside, "Healing decreased by 9%-14% for each Nearby Enemy"),
  Enchant("down_lhc", Downside, "Lose Health in Combat periodically"),
  Enchant("down_asci", Downside, "Attack Stamina Cost increased by 20%-30%"),
  )

val weaponPlagued = ItemBuilderConfig(
  itemSlot = Weapon,
  gems = weaponGems,
  enchants = weaponEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = weaponEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val shieldGems = List(Gem("Max Health increased by 10%"))

val shieldEnchants = List(
  Enchant("o_pdib", Other, "Poise Defense increased by 5-10 while Blocking"),
  
  Enchant("a_did", Attack, "Damage increased by up to 7%-15% based on Durability"),
  Enchant("a_dib", Attack, "Damage increased by 10%-20% for 6 seconds after Block"),
  Enchant("a_dip", Attack, "Damage increased by 15%-30% for 6 seconds after Parry."),
  Enchant("a_ddb", Attack, "Deal 10%-20% Damage on Block"),
  Enchant("a_ddp", Attack, "Deal 15%-30% Damage on Parry"),
  
  Enchant("s_srid", Stamina, "Stamina Recovery increased by up to 7%-25% based on Durability."),
  
  Enchant("i_i", Indestructible, "Indestructible"),
  
  Enchant("h_ghp", Healing, "Gain 2%-6% Health on Parry."),
  Enchant("h_hid", Healing, "Healing increased by up to 6%-20% based on Durability"),
  
  Enchant("w_iwd", Weight, "Item Weight decreased by 7%-15%."),
  
  Enchant("d_dtdb", Defense, "Damage Taken decreased by 7%-15% while Blocking"))

val shieldEnchantDownsides = List(
  Enchant("down_bd", Downside, "Block disabled"),
  Enchant("down_u", Downside, "Unrepairable"),
  Enchant("down_eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down_dfc", Downside, "Drain Focus in Combat"),
  Enchant("down_fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down_lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down_tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down_lhc", Downside, "Lose Health in Combat periodically"),
  )

val shieldPlagued = ItemBuilderConfig(
  itemSlot = Shield,
  gems = shieldGems,
  enchants = shieldEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = shieldEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val bowGems = List(Gem("Max Health increased by 10%"))

val bowEnchants = List(
  Enchant("a_did", Attack, "Damage increased by up to 7%-15% based on Durability"),
  Enchant("a_dinen", Attack, "Damage increased by 10%-20% if no Enemies nearby"),
  Enchant("a_hi", Attack, "Heat Infusion"),
  Enchant("a_ci", Attack, "Cold Infusion"),
  Enchant("a_li", Attack, "Lightning Infusiion"),
  Enchant("a_pi", Attack, "Plague Infusion"),
  Enchant("a_dddd", Attack, "Deal 4%-15% Damage on Damage Dodged"),
  Enchant("a_didd", Attack, "Damage increased by 15%-30% for 6 seconds after Damage Dodged."),
  
  Enchant("s_srid", Stamina, "Stamina Recovery increased by up to 7%-25% based on Durability."),
  
  Enchant("i_i", Indestructible, "Indestructible"),
  
  Enchant("h_hid", Healing, "Healing increased by up to 6%-20% based on Durability"),
  Enchant("h_ghdd", Healing, "Gain 3%-5% Health on Damage Dodged"),
  
  Enchant("w_iwd", Weight, "Item Weight decreased by 7%-15%.")
)

val bowEnchantDownsides = List(
  Enchant("down_u", Downside, "Unrepairable"),
  Enchant("down_eld", Downside, "Equip Load Decreased by 20%-30%"),
  Enchant("down_dfc", Downside, "Drain Focus in Combat"),
  Enchant("down_fci", Downside, "Focus Cost increased by 26%-40%"),
  Enchant("down_lftd", Downside, "Lose 10-15 Focus each time you take Damage"),
  Enchant("down_tdfu", Downside, "Take 20%-30% Damage on Focus Use."),
  Enchant("down_lhc", Downside, "Lose Health in Combat periodically"),
  Enchant("down_dsci", Downside, "Dodge Stamina Cost increased by 26%-40%"),
  )

val bowPlagued = ItemBuilderConfig(
  itemSlot = Bow,
  gems = bowGems,
  enchants = bowEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = bowEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)


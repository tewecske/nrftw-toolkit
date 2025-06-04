package com.tewe.nrftw

import ItemSlot.*
import EnchantGroup.*
import scala.collection.mutable.LinkedHashMap
import com.tewe.nrftw.WeaponType.*

enum WeaponType(val id: String, val name: String) {
  case AnyWeapon extends WeaponType("w", "Weapon")
  case DualWield extends WeaponType("dw", "Dual Wield")
  case DoubleDaggers extends WeaponType("dd", "Double Daggers")
  case Gauntlets extends WeaponType("g", "Gauntlets")
  case OneHanded extends WeaponType("1h", "One Handed")
  case Wand extends WeaponType("wd", "Wand")
  case TwoHanded extends WeaponType("2h", "Two Handed")
  case Staff extends WeaponType("s", "Staff")

  case Shield extends WeaponType("sh", "Shield")

  case Bow extends WeaponType("b", "Bow")
}

object WeaponType {
  val weaponTypesSelect = Set(DoubleDaggers, Gauntlets, OneHanded, TwoHanded, Wand, Staff)
  val allWeapons = WeaponType.values.toSet

  val shields = Set(Shield)

  val bows = Set(Bow)

  val oneHandedWeapons = Set(AnyWeapon, OneHanded)
  val twoHandedWeapons = Set(AnyWeapon, TwoHanded)
  val wandWeapons = Set(AnyWeapon, OneHanded, Wand)
  val staffWeapons = Set(AnyWeapon, OneHanded, Staff)
  val gauntletWeapons = Set(AnyWeapon, DualWield, Gauntlets)
  val doubleDaggerWeapons = Set(AnyWeapon, DualWield, DoubleDaggers)
  val bowWeapons = Set(AnyWeapon, Bow)

}

case class GemEffect(itemSlot: ItemSlot, value: String, extra: Boolean = false)
case class Gem(id: String, value: String, imageSrc: String, gemEffects: List[GemEffect])

sealed trait RuneCost
case class FocusCost(value: Int) extends RuneCost
case class StaminaCost(value: Int) extends RuneCost
case class HealthCost(value: Int) extends RuneCost

case class Rune(id: String, name: String, imageSrc: String, cost: RuneCost, weaponTypes: Set[WeaponType])

enum WeaponSource {
  case Vendor, Drop, Crafted
}
enum Stat {
  case Strength, Dexterity, Intelligence, Faith
}
sealed trait WeaponScale
case class SingleScale(stat: Stat, minValue: Int)
case class DoubleScale(stat1: Stat, minValue1: Int, stat2: Stat, minValue2: Int)

case class Weapon(
  id: String,
  name: String,
  weaponType: WeaponType,
  desc: String,
  source: WeaponSource,
  scales: WeaponScale,
  tier: String,
  runes: List[Rune],
  poise: Int,
  staminaCost: Int,
  focusGain: Int,
  weight: Int
)

enum EnchantGroup {
  case Attack, Defense, Durability, Focus, Healing, Indestructible, Movement, Other, Resistance, Stamina, Weight, Downside
}

enum ItemRarity(value: String) {
  case Common extends ItemRarity("common")
  case Magic extends ItemRarity("magic")
  case Plagued extends ItemRarity("plagued")
  case Legendary extends ItemRarity("legendary")
  override def toString(): String = value
}

case class Enchant(id: String, group: EnchantGroup, value: String)

enum ItemSlot(val name: String) {
  case HelmetSlot extends ItemSlot("Helmet")
  case ArmorSlot extends ItemSlot("Armor")
  case PantsSlot extends ItemSlot("Pants")
  case GlovesSlot extends ItemSlot("Gloves")
  case WeaponSlot extends ItemSlot("Weapon")
  case ShieldSlot extends ItemSlot("Shield")
  case BowSlot extends ItemSlot("Bow")
  case RingSlot extends ItemSlot("Ring")
}

case class ItemBuilderConfig(
  itemSlot: ItemSlot,
  itemRarity: ItemRarity,
  gems: List[Gem],
  enchants: Map[String, Enchant],
  enchantDownsides: Map[String, Enchant],
)

case class WeaponBuilderConfig(
  itemConfig: ItemBuilderConfig,
  weaponTypes: Set[WeaponType]
)

case class RingData(
  id: String,
  name: String,
  itemRarity: ItemRarity,
  description: String,
  requiredLevel: Int,
  durability: Int,
  weight: Double,
  imageSrc: String,
  enchantments: List[String],
  enchantDownsides: List[String] = List.empty
)

val gems = List(
  Gem("ca", "Chipped Amethyst", "/images/gem-chippedAmethyst.png", gemEffects = List(
    GemEffect(WeaponSlot, "Plague Damage Infusion", extra = true),
    GemEffect(ShieldSlot, "Deal 5%-15% Plague Damage on Block"),
    GemEffect(BowSlot, "Plague Damage Infusion", extra = true),
    GemEffect(ArmorSlot, "Plague Resistance increased by 6%-20%"),
    GemEffect(HelmetSlot, "Infection Buildup increased by 4%-15%"),
    GemEffect(PantsSlot, "Plague Resistance increased by 6%-20%"),
    GemEffect(GlovesSlot, "Infection Buildup increased by 4%-15%"),
  )),
  Gem("cbp", "Chipped Black Pearl", "/images/gem-chippedBlackPearl.png", gemEffects = List(
    GemEffect(WeaponSlot, "Healing increased by 4%-15%", extra = true),
    GemEffect(ShieldSlot, "Healing increased by 3%-10%"),
    GemEffect(BowSlot, "Healing increased by 3%-10%"),
    GemEffect(ArmorSlot, "Healing increased by 4%-15%", extra = true),
    GemEffect(HelmetSlot, "Healing increased by 3%-10%"),
    GemEffect(PantsSlot, "Healing increased by 3%-10%"),
    GemEffect(GlovesSlot, "Healing increased by 3%-10%"),
  )),
  Gem("cbs", "Chipped Blood Stone", "/images/gem-chippedBloodStone.png", gemEffects = List(
    GemEffect(WeaponSlot, "Lifesteal increased by 3%-10%", extra = true),
    GemEffect(ShieldSlot, "Lifesteal increased by 2%-5%"),
    GemEffect(BowSlot, "Lifesteal increased by 2%-5%"),
    GemEffect(ArmorSlot, "Regainable Health increased by 5%-15%"),
    GemEffect(HelmetSlot, "Regainable Health increased by 5%-15%"),
    GemEffect(PantsSlot, "Regainable Health increased by 5%-15%"),
    GemEffect(GlovesSlot, "Regainable Health increased by 10%-25%", extra = true),
  )),
  Gem("cd", "Chipped Diamond", "/images/gem-chippedDiamond.png", gemEffects = List(
    GemEffect(ArmorSlot, "Experience Gain increased by 3%-10%"),
    GemEffect(HelmetSlot, "Experience Gain increased by 3%-10%"),
    GemEffect(PantsSlot, "Experience Gain increased by 3%-10%"),
    GemEffect(GlovesSlot, "Experience Gain increased by 3%-10%"),
  )),
  Gem("cdn", "Chipped Dianite", "/images/gem-chippedDianite.png", gemEffects = List(
    GemEffect(ArmorSlot, "Focus Cost decreased by 2%-5%"),
    GemEffect(HelmetSlot, "Focus Cost decreased by 2%-5%"),
    GemEffect(PantsSlot, "Focus Cost decreased by 2%-5%"),
    GemEffect(GlovesSlot, "Focus Cost decreased by 3%-10%", extra = true),
  )),
  Gem("ce", "Chipped Emerald", "/images/gem-chippedEmerald.png", gemEffects = List(
   GemEffect(WeaponSlot, "Max Stamina increased by 4%-15%", extra = true),
   GemEffect(ShieldSlot, "Max Stamina increased by 3%-10%"),
   GemEffect(BowSlot, "Max Stamina increased by 3%-10%"),
   GemEffect(ArmorSlot, "Max Stamina increased by 3%-10%"),
   GemEffect(HelmetSlot, "Max Stamina increased by 3%-10%"),
   GemEffect(PantsSlot, "Max Stamina increased by 4%-15%", extra = true),
   GemEffect(GlovesSlot, "Max Stamina increased by 3%-10%"),
  )),
  Gem("tf", "Tiny Fang", "/images/gem-tinyFang.png", gemEffects = List(
   GemEffect(WeaponSlot, "Attack Stamina Cost decreased by 7%-15%", extra = true),
   GemEffect(ShieldSlot, "Parry Stamina Cost decreased by 9%-30%", extra = true),
   GemEffect(BowSlot, "Dodge Stamina Cost decreased by 7%-25%", extra = true),
   GemEffect(ArmorSlot, "Stamina Cost decreased by 2%-6%"),
   GemEffect(HelmetSlot, "Stamina Cost decreased by 2%-6%"),
   GemEffect(PantsSlot, "Stamina Cost decreased by 3%-10%", extra = true),
   GemEffect(GlovesSlot, "Stamina Cost decreased by 2%-6%"),
  )),
  Gem("tft", "Tiny Feather", "/images/gem-tinyFeather.png", gemEffects = List(
    GemEffect(WeaponSlot, "Item Weight decreased by 4%-15%"),
    GemEffect(ShieldSlot, "Item Weight decreased by 4%-15%"),
    GemEffect(BowSlot, "Item Weight decreased by 4%-15%"),
    GemEffect(ArmorSlot, "Item Weight decreased by 4%-15%"),
    GemEffect(HelmetSlot, "Item Weight decreased by 4%-15%"),
    GemEffect(PantsSlot, "Item Weight decreased by 4%-15%"),
    GemEffect(GlovesSlot, "Item Weight decreased by 4%-15%"),
  )),
  Gem("cg", "Chipped Granite", "/images/gem-chippedGranite.png", gemEffects = List(
    GemEffect(ArmorSlot, "Poise Defense increased by 1-3"),
    GemEffect(HelmetSlot, "Poise Defense increased by 1-3"),
    GemEffect(PantsSlot, "Poise Defense increased by 1-3"),
    GemEffect(GlovesSlot, "Poise Defense increased by 1-3"),
  )),
  Gem("cj", "Chipped Jade", "/images/gem-chippedJade.png", gemEffects = List(
    GemEffect(ArmorSlot, "Stamina Recovery increased by 4%-12%"),
    GemEffect(HelmetSlot, "Stamina Recovery increased by 4%-12%"),
    GemEffect(PantsSlot, "Stamina Recovery increased by 4%-12%"),
    GemEffect(GlovesSlot, "Stamina Recovery increased by 6%-20%", extra = true)
  )),
  Gem("cjw", "Chipped Jewel", "/images/gem-chippedJewel.png", gemEffects = List(
    GemEffect(WeaponSlot, "Max Focus increased by 4%-15%", extra = true),
    GemEffect(ShieldSlot, "Max Focus increased by 3%-10%"),
    GemEffect(BowSlot, "Max Focus increased by 3%-10%"),
    GemEffect(ArmorSlot, "Max Focus increased by 3%-10%"),
    GemEffect(HelmetSlot, "Max Focus increased by 4%-15%", extra = true),
    GemEffect(PantsSlot, "Max Focus increased by 3%-10%"),
    GemEffect(GlovesSlot, "Max Focus increased by 3%-10%"),
  )),
  Gem("cm", "Chipped Marble", "/images/gem-chippedMarble.png", gemEffects = List(
    GemEffect(WeaponSlot, "Max Health increased by 4%-15%", extra = true),
    GemEffect(ShieldSlot, "Max Health increased by 3%-10%"),
    GemEffect(BowSlot, "Max Health increased by 3%-10%"),
    GemEffect(ArmorSlot, "Max Health increased by 4%-15%", extra = true),
    GemEffect(HelmetSlot, "Max Health increased by 3%-10%"),
    GemEffect(PantsSlot, "Max Health increased by 3%-10%"),
    GemEffect(GlovesSlot, "Max Health increased by 3%-10%"),
  )),
  Gem("cmr", "Chipped Meteorite", "/images/gem-chippedMeteorite.png", gemEffects = List(
    GemEffect(ArmorSlot, "Elemental Damage Taken decreased by 2%-5%"),
    GemEffect(HelmetSlot, "Elemental Damage Taken decreased by 2%-5%"),
    GemEffect(PantsSlot, "Elemental Damage Taken decreased by 2%-5%"),
    GemEffect(GlovesSlot, "Elemental Damage Taken decreased by 2%-5%"),
  )),
  Gem("cms", "Chipped Moon Stone", "/images/gem-chippedMoonStone.png", gemEffects = List(
    GemEffect(ArmorSlot, "Regenerate Focus in Combat"),
    GemEffect(HelmetSlot, "Regenerate Focus in Combat"),
    GemEffect(PantsSlot, "Regenerate Focus in Combat"),
    GemEffect(GlovesSlot, "Regenerate Focus in Combat"),
  )),
  Gem("cq", "Chipped Quartz", "/images/gem-chippedQuartz.png", gemEffects = List(
    GemEffect(WeaponSlot, "Electric Damage Infusion", extra = true),
    GemEffect(ShieldSlot, "Deal 5%-15% Electric Damage on Block"),
    GemEffect(BowSlot, "Electric Damage Infusion", extra = true),
    GemEffect(ArmorSlot, "Electric Resistance increased by 10%-20%"),
    GemEffect(HelmetSlot, "Shock Buildup increased by 4%-15%"),
    GemEffect(PantsSlot, "Electric Resistance increased by 10%-20%"),
    GemEffect(GlovesSlot, "Shock Buildup increased by 4%-15%")
  )),
  Gem("cr", "Chipped Ruby", "/images/gem-chippedRuby.png", gemEffects = List(
    GemEffect(WeaponSlot, "Heat Damage Infusion", extra = true),
    GemEffect(ShieldSlot, "Deal 5%-15% Heat Damage on Block"),
    GemEffect(BowSlot, "Heat Damage Infusion", extra = true),
    GemEffect(ArmorSlot, "Heat Resistance increased by 10%-20%"),
    GemEffect(HelmetSlot, "Burn Buildup increased by 4%-15%"),
    GemEffect(PantsSlot, "Heat Resistance increased by 10%-20%"),
    GemEffect(GlovesSlot, "Burn Buildup increased by 4%-15%"),
  )),
  Gem("cs", "Chipped Sapphire", "/images/gem-chippedSapphire.png", gemEffects = List(
    GemEffect(WeaponSlot, "Cold Damage Infusion", extra = true),
    GemEffect(ShieldSlot, "Deal 5%-15% Cold Damage on Block"),
    GemEffect(BowSlot, "Cold Damage Infusion", extra = true),
    GemEffect(ArmorSlot, "Cold Resistance increased by 10%-20%"),
    GemEffect(HelmetSlot, "Frost Buildup increased by 4%-15%"),
    GemEffect(PantsSlot, "Cold Resistance increased by 10%-20%"),
    GemEffect(GlovesSlot, "Frost Buildup increased by 4%-15%"),
  )),
  Gem("cse", "Chipped Shield Emblem", "/images/gem-chippedShieldEmblem.png", gemEffects = List(
    GemEffect(ArmorSlot, "Physical Damage Taken decreased by 2%-5%"),
    GemEffect(HelmetSlot, "Physical Damage Taken decreased by 2%-5%"),
    GemEffect(PantsSlot, "Physical Damage Taken decreased by 2%-5%"),
    GemEffect(GlovesSlot, "Physical Damage Taken decreased by 2%-5%")
  )),
  Gem("ss", "Small Skull", "/images/gem-smallSkull.png", gemEffects = List(
    GemEffect(ArmorSlot, "Regenerate Health in Combat"),
    GemEffect(HelmetSlot, "Regenerate Health in Combat"),
    GemEffect(PantsSlot, "Regenerate Health in Combat"),
    GemEffect(GlovesSlot, "Regenerate Health in Combat")
  )),
  Gem("csk", "Chipped Spike", "/images/gem-chippedSpike.png", gemEffects = List(
    GemEffect(WeaponSlot, "Physical Damage increased by 3%-10%", extra = true),
    GemEffect(ShieldSlot, "Physical Damage increased by 2%-5%"),
    GemEffect(BowSlot, "Physical Damage increased by 2%-5%"),
    GemEffect(ArmorSlot, "Armor increased by 4%-12%"),
    GemEffect(HelmetSlot, "Buildup Resistance increased by 4%-15%"),
    GemEffect(PantsSlot, "Armor increased by 4%-12%"),
    GemEffect(GlovesSlot, "Buildup Resistance increased by 4%-15%")
  )),
  Gem("ct", "Chipped Topaz", "/images/gem-chippedTopaz.png", gemEffects = List(
    GemEffect(ArmorSlot, "Focus Gain increased by 2%-6%"),
    GemEffect(HelmetSlot, "Focus Gain increased by 3%-10%", extra = true),
    GemEffect(PantsSlot, "Focus Gain increased by 2%-6%"),
    GemEffect(GlovesSlot, "Focus Gain increased by 2%-6%")
  )),
)

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
  itemSlot = HelmetSlot,
  itemRarity = ItemRarity.Plagued,
  gems = gems,
  enchants = helmetEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = helmetEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val armorEnchants = List(
    Enchant("o_pdic", Other, "Poise Defense increased by 5-10 while Charging"),
  
  Enchant("a_fea", Attack, "Frozen Enemy Armor decreased by 25%-50%"),
  Enchant("a_ddt", Attack, "Deal 4%-15% Damage on Damage Taken"),
  Enchant("a_dis", Attack, "Damage increased by 15%-30% for 6 seconds after Stagger."),
  Enchant("a_bdi", Attack, "Burn Damage increased by 25%-50%"),
  Enchant("a_iesrd", Attack, "Infected Enemy Stagger Resistance decreased by 15%-30%"),
  
  Enchant("f_gi", Focus, "Focus Gain increased by up to 10%-20% based on Durability"),
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
  itemSlot = ArmorSlot,
  itemRarity = ItemRarity.Plagued,
  gems = gems,
  enchants = armorEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = armorEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val pantsEnchants = List(
    Enchant("o_pdic", Other, "Poise Defense increased by 5-10 while Charging"),
  
  Enchant("a_dddd", Attack, "Deal 4%-15% Damage on Damage Dodged"),
  Enchant("a_dis", Attack, "Damage increased by 5%-10% after Sprinting for 2 seconds."),
  Enchant("a_didd", Attack, "Damage increased by 15%-30% for 6 seconds after Damage Dodged."),
  
  Enchant("f_fgid", Focus, "Focus Gain increased by up to 10%-20% based on Durability"),
  
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
  itemSlot = PantsSlot,
  itemRarity = ItemRarity.Plagued,
  gems = gems,
  enchants = pantsEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = pantsEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

val glovesEnchants = List(
  Enchant("o_bdi", Other, "Bomb Damage increased by 15%-30%"),
  
  Enchant("a_ddp", Attack, "Deal 15%-30% Damage on Parry"),
  Enchant("a_dip", Attack, "Damage increased by 15%-30% for 6 seconds after Parry."),
  Enchant("a_bbi", Attack, "Burn Buildup increased by 15%-30%."),
  Enchant("a_sbi", Attack, "Shock Buildup increased by 15%-30%."),
  Enchant("a_fbi", Attack, "Freeze Buildup increased by 15%-30%."),
  Enchant("a_ibi", Attack, "Infect Buildup increased by 15%-30%."),
  Enchant("a_pdi", Attack, "Physical Damage increased by 7%-25%"),
  
  Enchant("f_fgid", Focus, "Focus Gain increased by up to 10%-20% based on Durability"),
  
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
  itemSlot = GlovesSlot,
  itemRarity = ItemRarity.Plagued,
  gems = gems,
  enchants = glovesEnchants.map(enchant => (enchant.id, enchant)).toMap,
  enchantDownsides = glovesEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
)

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

val weaponPlagued = WeaponBuilderConfig(
  itemConfig = ItemBuilderConfig(
    itemSlot = WeaponSlot,
    itemRarity = ItemRarity.Plagued,
    gems = gems,
    enchants = weaponEnchants.map(enchant => (enchant.id, enchant)).toMap,
    enchantDownsides = weaponEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
  ),
  weaponTypes = weaponTypesSelect
)

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

val shieldPlagued = WeaponBuilderConfig(
  itemConfig = ItemBuilderConfig(
    itemSlot = ShieldSlot,
    itemRarity = ItemRarity.Plagued,
    gems = gems,
    enchants = shieldEnchants.map(enchant => (enchant.id, enchant)).toMap,
    enchantDownsides = shieldEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
  ),
  weaponTypes = shields
)

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

val bowPlagued = WeaponBuilderConfig(
  itemConfig = ItemBuilderConfig(
    itemSlot = BowSlot,
    itemRarity = ItemRarity.Plagued,
    gems = gems,
    enchants = bowEnchants.map(enchant => (enchant.id, enchant)).toMap,
    enchantDownsides = bowEnchantDownsides.map(enchant => (enchant.id, enchant)).toMap,
  ),
  weaponTypes = bows
)

val rings = List(
  RingData("r_cr", "Crow Ring", ItemRarity.Plagued, "Description of Crow Ring", 1, 100, 0.0, "/images/ring-crowRing.png",
    enchantments = List(
    "Max Stamina Increased by 30-50%",
    ),
    enchantDownsides = List(
    "Drain Health in combat"
  )),
  RingData("r_fr", "Feather Ring", ItemRarity.Magic, "Description Feather Ring", 1, 100, 0.0, "/images/ring-featherRing.png", List(
    "Equip Load Increased by 10-20%"
  )),
  RingData("r_rr", "Rune Ring", ItemRarity.Plagued, "Description of Rune Ring", 1, 100, 0.0, "/images/ring-runeRing.png", List(
    "Rune Damage increased by 10%-30%",
    ),
  enchantDownsides = List(
    "Attack Damage decreased by 10%-20%"
  )),
  RingData("r_sr", "Solace Ring", ItemRarity.Magic, "Description of Solace Ring", 1, 100, 0.0, "/images/ring-solaceRing.png", List(
    "Regainable Health Increased by 10%-30%"
  )),
  RingData("r_str", "Stone Tusk Ring", ItemRarity.Magic, "Description of Stone Tusk Ring", 1, 100, 0.0, "/images/ring-stoneTuskRing.png", List(
    "Armor Increased by 25%"
  )),
  RingData("r_wr", "Woven Ring", ItemRarity.Magic, "Description of Woven Ring", 1, 100, 0.0, "/images/ring-wovenRing.png", List(
    "Endless Climbing"
  )),
  RingData("r_ar", "Agility Ring", ItemRarity.Magic, "Description of Agility Ring", 1, 100, 0.0, "/images/ring-agilityRing.png", List(
    "Attack Stamina Cost Decreased by 20%",
    "Movement Speed Increased by 10%",
    "Sprint Stamina Cost Decreased by 25%"
  )),
  RingData("r_boc", "Band of Calmness", ItemRarity.Magic, "Description of Band of Calmness", 11, 100, 0.0, "/images/ring-bandOfCalmness.png", List(
    "Focus Gain Increased by 25%",
    "Max Focus Increased by 50%"
  )),
  RingData("r_gb", "Golden Band", ItemRarity.Magic, "Description of Golden Band", 11, 100, 0.0, "/images/ring-goldenBand.png", List(
    "Increased Experience by 5-10%"
  )),
  RingData("r_jr", "Jade Ring", ItemRarity.Magic, "Description of Jade Ring", 11, 100, 0.0, "/images/ring-jadeRing.png", List(
    "Gain 3%-6% Stamina on Damage Dealt"
  )),
  RingData("r_pr", "Plagued Ring", ItemRarity.Plagued, "Description of Plagued Ring", 11, 100, 0.0, "/images/ring-plaguedRing.png", List(
    "Lifesteal Increased by 20% at Low Health",
    ),
  enchantDownsides = List(
    "Max Health decreased by 20%"
  )),
  RingData("r_robr", "Ring of Broken Promises", ItemRarity.Plagued, "Description of Ring of Broken Promises", 11, 100, 0.0, "/images/ring-ringOfBrokenPromises.png", List(
    "Damage Dealt Increased by 10-20%",
    ),
  enchantDownsides = List(
    "Damage Taken Increased by 10-20%"
  )),
  RingData("r_fir", "Fierce Ring", ItemRarity.Magic, "Description of Fierce Ring", 21, 100, 0.0, "/images/ring-fierceRing.png", List(
    "Damage Increased by 8%-20% for 10 seconds after Damage Taken"
  )),
  RingData("r_sir", "Silver Ring", ItemRarity.Magic, "Description of Silver Ring", 21, 100, 0.0, "/images/ring-silverRing.png", List(
    "Deals 35% Damage on Parry"
  )),
  RingData("r_snr", "Snake Ring", ItemRarity.Plagued, "Description of Snake Ring", 21, 100, 0.0, "/images/ring-snakeRing.png", List(
    "Deals 25% Damage on Block",
    ),
  enchantDownsides = List(
    "Lose 6 Stamina on Block"
  )),
  RingData("r_tr", "Thistle Ring", ItemRarity.Plagued, "Description of Thistle Ring", 21, 100, 0.0, "/images/ring-thistleRing.png", List(
    "Deal 21%-25% Damage on Damage Taken",
    ),
  enchantDownsides = List(
    "Lose 8-6 Stamina on Damage Taken"
  )),
  RingData("r_wcr", "Willow Cap Ring", ItemRarity.Magic, "Description of Willow Cap Ring", 21, 100, 0.0, "/images/ring-willowCapRing.png", List(
    "Overall Speed increased by 15%"
  )),
  RingData("r_br", "Battlecry Ring", ItemRarity.Legendary, "Description of Battlecry Ring", 0, 100, 0.0, "/images/ring-battlecryRing.png", List(
    "Spend Health instead of Focus",
    ),
  enchantDownsides = List(
    "Cannot Lifesteal"
  )),
  RingData("r_rod", "Ring of Determination", ItemRarity.Legendary, "Description of Ring of Determination", 0, 100, 0.0, "/images/ring-ringOfDetermination.png", List(
    "Spend Health if there is not enough Focus available",
    "Focus Cost decreased by up to 60% based on Missing Health"
  )),
  RingData("r_scr", "Scarlet Ring", ItemRarity.Legendary, "Description of Scarlet Ring", 0, 100, 0.0, "/images/ring-scarletRing.png", List(
    "Swap Max Health and Max Focus"
  )),
  RingData("r_ser", "Serendipity Ring", ItemRarity.Legendary, "Description of Serendipity Ring", 0, 100, 0.0, "/images/ring-serendipityRing.png", List(
    "Food Healing is applied over time",
    "Healing increased by 18%-30%"
  ))
)

val runes = List(
    Rune(id = "u_adfl", name = "Advancing Flurry", imageSrc = "/images/rune-advancingFlurry.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_aido", name = "Air Dodge", imageSrc = "/images/rune-airDodge.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_arma", name = "Armageddon", imageSrc = "/images/rune-armageddon.png", cost = FocusCost(150), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_ar", name = "Arrow", imageSrc = "/images/rune-arrow.png", cost = FocusCost(10), weaponTypes = WeaponType.bowWeapons),
    Rune(id = "u_bash", name = "Balance Smash", imageSrc = "/images/rune-balanceSmash.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_bakn", name = "Bare Knuckle", imageSrc = "/images/rune-bareKnuckle.png", cost = FocusCost(100), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_befl", name = "Berserk Flurry", imageSrc = "/images/rune-berserkFlurry.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_besl", name = "Berserker Slam", imageSrc = "/images/rune-berserkerSlam.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_best", name = "Berserker Strike", imageSrc = "/images/rune-berserkerStrike.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_blru", name = "Blazing Rupture", imageSrc = "/images/rune-blazingRupture.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_bl", name = "Blink", imageSrc = "/images/rune-blink.png", cost = FocusCost(25), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_brth", name = "Breaker Thrust", imageSrc = "/images/rune-breakerThrust.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_br", name = "Breakrush", imageSrc = "/images/rune-breakrush.png", cost = FocusCost(133), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_brre", name = "Brutal Reprise", imageSrc = "/images/rune-brutalReprise.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_buri", name = "Buster Rift", imageSrc = "/images/rune-busterRift.png", cost = FocusCost(125), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_chli", name = "Chain Lightning", imageSrc = "/images/rune-chainLightning.png", cost = FocusCost(50), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_ch", name = "Channel", imageSrc = "/images/rune-channel.png", cost = HealthCost(25), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_chst", name = "Charge Strike", imageSrc = "/images/rune-chargeStrike.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_chbo", name = "Charged Bolt", imageSrc = "/images/rune-chargedBolt.png", cost = FocusCost(50), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_ci", name = "Circular", imageSrc = "/images/rune-circular.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_coen", name = "Cold Enchantment", imageSrc = "/images/rune-coldEnchantment.png", cost = FocusCost(50), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_cosl", name = "Cold Sling", imageSrc = "/images/rune-coldSling.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_cosho", name = "Cone Shot", imageSrc = "/images/rune-coneShot.png", cost = FocusCost(100), weaponTypes = WeaponType.bowWeapons),
    Rune(id = "u_cofl", name = "Converging Flame", imageSrc = "/images/rune-convergingFlame.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_cr", name = "Crush", imageSrc = "/images/rune-crush.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_crdash", name = "Crushing Dash", imageSrc = "/images/rune-crushingDash.png", cost = FocusCost(0), weaponTypes = Set.empty[WeaponType]),
    Rune(id = "u_crdo", name = "Crushing Dodge", imageSrc = "/images/rune-crushingDodge.png", cost = FocusCost(0), weaponTypes = Set.empty[WeaponType]),
    Rune(id = "u_crfl", name = "Crushing Flurry", imageSrc = "/images/rune-crushingFlurry.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_crqu", name = "Crushing Quad", imageSrc = "/images/rune-crushingQuad.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_crsl", name = "Crushing Slam", imageSrc = "/images/rune-crushingSlam.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_crtri", name = "Crushing Triple", imageSrc = "/images/rune-crushingTriple.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_crupp", name = "Crushing Uppercut", imageSrc = "/images/rune-crushingUppercut.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_cyki", name = "Cyclone Kick", imageSrc = "/images/rune-cycloneKick.png", cost = FocusCost(50), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_cysw", name = "Cyclone Swipe", imageSrc = "/images/rune-cycloneSwipe.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_dasu", name = "Damage Surge", imageSrc = "/images/rune-damageSurge.png", cost = FocusCost(100), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_dast", name = "Dashing Stab", imageSrc = "/images/rune-dashingStab.png", cost = FocusCost(100), weaponTypes = WeaponType.doubleDaggerWeapons),
    Rune(id = "u_dibr", name = "Drive Break", imageSrc = "/images/rune-driveBreak.png", cost = FocusCost(50), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_dosl", name = "Dodge Slamdown", imageSrc = "/images/rune-dodgeSlamdown.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_docru", name = "Double Crush", imageSrc = "/images/rune-doubleCrush.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_dosp", name = "Double Spin", imageSrc = "/images/rune-doubleSpin.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_dota", name = "Double Take", imageSrc = "/images/rune-doubleTake.png", cost = FocusCost(0), weaponTypes = WeaponType.doubleDaggerWeapons),
    Rune(id = "u_drki", name = "Dragonfly Kick", imageSrc = "/images/rune-dragonflyKick.png", cost = FocusCost(50), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_drtra", name = "Drone Trap", imageSrc = "/images/rune-droneTrap.png", cost = FocusCost(50), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_dr", name = "Dropkick", imageSrc = "/images/rune-dropkick.png", cost = StaminaCost(44), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_dubre", name = "Dual Breaker", imageSrc = "/images/rune-dualBreaker.png", cost = FocusCost(100), weaponTypes = WeaponType.doubleDaggerWeapons),
    Rune(id = "u_dufl", name = "Dual Flurry", imageSrc = "/images/rune-dualFlurry.png", cost = FocusCost(50), weaponTypes = WeaponType.doubleDaggerWeapons),
    Rune(id = "u_dusa", name = "Dual Slash", imageSrc = "/images/rune-dualSlash.png", cost = FocusCost(100), weaponTypes = WeaponType.doubleDaggerWeapons),
    Rune(id = "u_elen", name = "Electric Enchantment", imageSrc = "/images/rune-electricEnchantment.png", cost = FocusCost(50), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_elre", name = "Electric Resistance", imageSrc = "/images/rune-electricResistance.png", cost = FocusCost(0), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_er", name = "Eruption", imageSrc = "/images/rune-eruption.png", cost = FocusCost(150), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_evco", name = "Evasive Combo", imageSrc = "/images/rune-evasiveCombo.png", cost = FocusCost(0), weaponTypes = Set.empty[WeaponType]),
    Rune(id = "u_evpi", name = "Evasive Pierce", imageSrc = "/images/rune-evasivePierce.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_evsho", name = "Evasive Shot", imageSrc = "/images/rune-evasiveShot.png", cost = FocusCost(50), weaponTypes = WeaponType.bowWeapons),
    Rune(id = "u_evst", name = "Evasive Strike", imageSrc = "/images/rune-evasiveStrike.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_evsw", name = "Evasive Swing", imageSrc = "/images/rune-evasiveSwing.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_fcds", name = "Fierce Dash", imageSrc = "/images/rune-fierceDash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_fiar", name = "Fire Arrow", imageSrc = "/images/rune-fireArrow.png", cost = FocusCost(20), weaponTypes = WeaponType.bowWeapons),
    Rune(id = "u_fibl", name = "Fire Blast", imageSrc = "/images/rune-fireBlast.png", cost = FocusCost(50), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_fibu", name = "Fire Burst", imageSrc = "/images/rune-fireBurst.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_fidr", name = "Fire Dart", imageSrc = "/images/rune-fireDart.png", cost = FocusCost(50), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_fida", name = "Fire Dash", imageSrc = "/images/rune-fireDash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_fifl", name = "Fire Flurry", imageSrc = "/images/rune-fireFlurry.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_fino", name = "Fire Nova", imageSrc = "/images/rune-fireNova.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_fisl", name = "Fire Slam", imageSrc = "/images/rune-fireSlam.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_fisw", name = "Fire Swipe", imageSrc = "/images/rune-fireSwipe.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_fith", name = "Fire Throw", imageSrc = "/images/rune-fireThrow.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_fiwa", name = "Fire Wall", imageSrc = "/images/rune-fireWall.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_fiwk", name = "Fire Walk", imageSrc = "/images/rune-fireWalk.png", cost = FocusCost(150), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_fiwv", name = "Fire Wave", imageSrc = "/images/rune-fireWave.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_fiwh", name = "Fire Whirl", imageSrc = "/images/rune-fireWhirl.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_fiww", name = "Fire Whirlwind", imageSrc = "/images/rune-fireWhirlwind.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_fi", name = "Fireball", imageSrc = "/images/rune-fireball.png", cost = FocusCost(50), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_flsw", name = "Flame Sweep", imageSrc = "/images/rune-flameSweep.png", cost = FocusCost(50), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_foha", name = "Focus Halo", imageSrc = "/images/rune-focusHalo.png", cost = FocusCost(100), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_frcl", name = "Frenzied Cleave", imageSrc = "/images/rune-frenziedCleave.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_frar", name = "Frigid Arc", imageSrc = "/images/rune-frigidArc.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_frki", name = "Frontflip Kick", imageSrc = "/images/rune-frontflipKick.png", cost = StaminaCost(42), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_frda", name = "Frost Dash", imageSrc = "/images/rune-frostDash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_frne", name = "Frost Needles", imageSrc = "/images/rune-frostNeedles.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_frno", name = "Frost Nova", imageSrc = "/images/rune-frostNova.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_frst", name = "Frost Step", imageSrc = "/images/rune-frostStep.png", cost = FocusCost(50), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_frss", name = "Frost Stream", imageSrc = "/images/rune-frostStream.png", cost = FocusCost(25), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_frsk", name = "Frost Strike", imageSrc = "/images/rune-frostStrike.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_glsp", name = "Glacial Spike", imageSrc = "/images/rune-glacialSpike.png", cost = FocusCost(150), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_grsl", name = "Ground Slam", imageSrc = "/images/rune-groundSlam.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_gysl", name = "Gyro Slash", imageSrc = "/images/rune-gyroSlash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_ha", name = "Hail", imageSrc = "/images/rune-hail.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_heau", name = "Heal Aura", imageSrc = "/images/rune-healAura.png", cost = FocusCost(25), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_heaf", name = "Heat Affliction", imageSrc = "/images/rune-heatAffliction.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_heen", name = "Heat Enchantment", imageSrc = "/images/rune-heatEnchantment.png", cost = FocusCost(50), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_he", name = "Hellfire", imageSrc = "/images/rune-hellfire.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_hofro", name = "Homing Frost", imageSrc = "/images/rune-homingFrost.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_icar", name = "Ice Arrow", imageSrc = "/images/rune-iceArrow.png", cost = FocusCost(20), weaponTypes = WeaponType.bowWeapons),
    Rune(id = "u_ic", name = "Icebolt", imageSrc = "/images/rune-icebolt.png", cost = FocusCost(50), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_icda", name = "Ice Dart", imageSrc = "/images/rune-iceDart.png", cost = FocusCost(50), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_icra", name = "Ice Ram", imageSrc = "/images/rune-iceRam.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_icsw", name = "Ice Sweep", imageSrc = "/images/rune-iceSweep.png", cost = FocusCost(25), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_icth", name = "Ice Throw", imageSrc = "/images/rune-iceThrow.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_icwh", name = "Ice Whirl", imageSrc = "/images/rune-iceWhirl.png", cost = FocusCost(150), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_il", name = "Illuminate", imageSrc = "/images/rune-illuminate.png", cost = FocusCost(25), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_in", name = "Inferno", imageSrc = "/images/rune-inferno.png", cost = FocusCost(25), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_just", name = "Juggle Strike", imageSrc = "/images/rune-juggleStrike.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_jucu", name = "Jump Cut", imageSrc = "/images/rune-jumpCut.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_kisl", name = "Kick Slash", imageSrc = "/images/rune-kickSlash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_knsw", name = "Knockdown Swirl", imageSrc = "/images/rune-knockdownSwirl.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_knsho", name = "Knockout Shot", imageSrc = "/images/rune-knockoutShot.png", cost = FocusCost(100), weaponTypes = WeaponType.bowWeapons),
    Rune(id = "u_le", name = "Leap", imageSrc = "/images/rune-leap.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_liar", name = "Lightning Arrow", imageSrc = "/images/rune-lightningArrow.png", cost = FocusCost(20), weaponTypes = WeaponType.bowWeapons),
    Rune(id = "u_lias", name = "Lightning Assault", imageSrc = "/images/rune-lightningAssault.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_liba", name = "Lightning Barrage", imageSrc = "/images/rune-lightningBarrage.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_libl", name = "Lightning Blast", imageSrc = "/images/rune-lightningBlast.png", cost = FocusCost(50), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_libo", name = "Lightning Bolt", imageSrc = "/images/rune-lightningBolt.png", cost = FocusCost(150), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_licl", name = "Lightning Claw", imageSrc = "/images/rune-lightningClaw.png", cost = FocusCost(50), weaponTypes = WeaponType.doubleDaggerWeapons),
    Rune(id = "u_lida", name = "Lightning Dart", imageSrc = "/images/rune-lightningDart.png", cost = FocusCost(50), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_lids", name = "Lightning Dash", imageSrc = "/images/rune-lightningDash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_lifl", name = "Lightning Flash", imageSrc = "/images/rune-lightningFlash.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_lifr", name = "Lightning Flurry", imageSrc = "/images/rune-lightningFlurry.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_lile", name = "Lightning Leap", imageSrc = "/images/rune-lightningLeap.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_lisl", name = "Lightning Slam", imageSrc = "/images/rune-lightningSlam.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_lisp", name = "Lightning Spin", imageSrc = "/images/rune-lightningSpin.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_list", name = "Lightning Storm", imageSrc = "/images/rune-lightningStorm.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_lisw", name = "Lightning Sweep", imageSrc = "/images/rune-lightningSweep.png", cost = FocusCost(25), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_lith", name = "Lightning Throw", imageSrc = "/images/rune-lightningThrow.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_liwh", name = "Lightning Whirl", imageSrc = "/images/rune-lightningWhirl.png", cost = FocusCost(150), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_libr", name = "Limit Break", imageSrc = "/images/rune-limitBreak.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_mush", name = "Multi Shot", imageSrc = "/images/rune-multiShot.png", cost = FocusCost(100), weaponTypes = WeaponType.bowWeapons),
    Rune(id = "u_musp", name = "Multi Spin", imageSrc = "/images/rune-multiSpin.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_ov", name = "Overrun", imageSrc = "/images/rune-overrun.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_pe", name = "Pestilence", imageSrc = "/images/rune-pestilence.png", cost = FocusCost(150), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_pere", name = "Pestilent Recoil", imageSrc = "/images/rune-pestilentRecoil.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_pida", name = "Piercing Dash", imageSrc = "/images/rune-piercingDash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_pido", name = "Piercing Dodge", imageSrc = "/images/rune-piercingDodge.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_pifl", name = "Piercing Flurry", imageSrc = "/images/rune-piercingFlurry.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_pisp", name = "Piercing Spin", imageSrc = "/images/rune-piercingSpin.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_plar", name = "Plague Arrow", imageSrc = "/images/rune-plagueArrow.png", cost = FocusCost(20), weaponTypes = WeaponType.bowWeapons),
    Rune(id = "u_plba", name = "Plague Barrage", imageSrc = "/images/rune-plagueBarrage.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_plbr", name = "Plague Breath", imageSrc = "/images/rune-plagueBreath.png", cost = FocusCost(50), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_plbu", name = "Plague Burst", imageSrc = "/images/rune-plagueBurst.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_plco", name = "Plague Column", imageSrc = "/images/rune-plagueColumn.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_plcr", name = "Plague Crush", imageSrc = "/images/rune-plagueCrush.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_plda", name = "Plague Dart", imageSrc = "/images/rune-plagueDart.png", cost = FocusCost(50), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_plds", name = "Plague Dash", imageSrc = "/images/rune-plagueDash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_plen", name = "Plague Enchantment", imageSrc = "/images/rune-plagueEnchantment.png", cost = FocusCost(50), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_plex", name = "Plague Explosion", imageSrc = "/images/rune-plagueExplosion.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_plla", name = "Plague Launch", imageSrc = "/images/rune-plagueLaunch.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_plno", name = "Plague Nova", imageSrc = "/images/rune-plagueNova.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_plre", name = "Plague Retch", imageSrc = "/images/rune-plagueRetch.png", cost = FocusCost(50), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_plsm", name = "Plague Smite", imageSrc = "/images/rune-plagueSmite.png", cost = FocusCost(50), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_plsp", name = "Plague Splatter", imageSrc = "/images/rune-plagueSplatter.png", cost = FocusCost(150), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_plst", name = "Plague Strike", imageSrc = "/images/rune-plagueStrike.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_plsw", name = "Plague Sweep", imageSrc = "/images/rune-plagueSweep.png", cost = FocusCost(25), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_plsl", name = "Plague Swirl", imageSrc = "/images/rune-plagueSwirl.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_plth", name = "Plague Throw", imageSrc = "/images/rune-plagueThrow.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_plwa", name = "Plague Wave", imageSrc = "/images/rune-plagueWave.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_plwh", name = "Plague Whirl", imageSrc = "/images/rune-plagueWhirl.png", cost = FocusCost(150), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_posh", name = "Poise Shield", imageSrc = "/images/rune-poiseShield.png", cost = FocusCost(100), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_po", name = "Pole Flurry", imageSrc = "/images/rune-poleFlurry.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_puhe", name = "Pulse of Health", imageSrc = "/images/rune-pulseOfHealth.png", cost = FocusCost(10), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_raspi", name = "Raging Spiral", imageSrc = "/images/rune-ragingSpiral.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_reap", name = "Reap", imageSrc = "/images/rune-reap.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_rest", name = "Recoil Strike", imageSrc = "/images/rune-recoilStrike.png", cost = FocusCost(50), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_regu", name = "Regurgitate", imageSrc = "/images/rune-regurgitate.png", cost = FocusCost(100), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_recl", name = "Relentless Cleave", imageSrc = "/images/rune-relentlessCleave.png", cost = FocusCost(138), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_repa", name = "Repair", imageSrc = "/images/rune-repair.png", cost = FocusCost(50), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_retu", name = "Return", imageSrc = "/images/rune-return.png", cost = FocusCost(50), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_rita", name = "Rip and Tear", imageSrc = "/images/rune-ripAndTear.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_rifa", name = "Rising Fang", imageSrc = "/images/rune-risingFang.png", cost = FocusCost(50), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_ro", name = "Rotwheel", imageSrc = "/images/rune-rotwheel.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_sc", name = "Scream", imageSrc = "/images/rune-scream.png", cost = FocusCost(50), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_secr", name = "Seismic Crush", imageSrc = "/images/rune-seismicCrush.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_shno", name = "Shock Nova", imageSrc = "/images/rune-shockNova.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_shst", name = "Shock Strike", imageSrc = "/images/rune-shockStrike.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_sk", name = "Skyfall Smash", imageSrc = "/images/rune-skyfallSmash.png", cost = FocusCost(125), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_sn", name = "Slamdown", imageSrc = "/images/rune-slamdown.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_sldh", name = "Slashing Dash", imageSrc = "/images/rune-slashingDash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_slde", name = "Slashing Dodge", imageSrc = "/images/rune-slashingDodge.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_slfl", name = "Slashing Flurry", imageSrc = "/images/rune-slashingFlurry.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_slpi", name = "Slashing Pirouette", imageSrc = "/images/rune-slashingPirouette.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_slsp", name = "Slashing Spin", imageSrc = "/images/rune-slashingSpin.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_sp", name = "Spark", imageSrc = "/images/rune-spark.png", cost = FocusCost(100), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_spin", name = "Spin", imageSrc = "/images/rune-spin.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_spcu", name = "Spin Crush", imageSrc = "/images/rune-spinCrush.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_spst", name = "Spin Strike", imageSrc = "/images/rune-spinStrike.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_spcr", name = "Spiral Crash", imageSrc = "/images/rune-spiralCrash.png", cost = FocusCost(50), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_spbu", name = "Spirit Burst", imageSrc = "/images/rune-spiritBurst.png", cost = FocusCost(50), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_stwa", name = "Stamina Wellspring", imageSrc = "/images/rune-staminaWellspring.png", cost = FocusCost(100), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_stat", name = "Static", imageSrc = "/images/rune-static.png", cost = FocusCost(100), weaponTypes = WeaponType.wandWeapons),
    Rune(id = "u_stor", name = "Stormpiercer", imageSrc = "/images/rune-stormpiercer.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_sush", name = "Surge Slash", imageSrc = "/images/rune-surgeSlash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_swki", name = "Swipe Kick", imageSrc = "/images/rune-swipeKick.png", cost = StaminaCost(40), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_tast", name = "Taunt Strike", imageSrc = "/images/rune-tauntStrike.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_teki", name = "Tempest Kick", imageSrc = "/images/rune-tempestKick.png", cost = FocusCost(50), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_th", name = "Thorns", imageSrc = "/images/rune-thorns.png", cost = FocusCost(50), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_thrw", name = "Throw", imageSrc = "/images/rune-throw.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_thax", name = "Throw Axe", imageSrc = "/images/rune-throwAxe.png", cost = FocusCost(20), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_thkn", name = "Throw Knife", imageSrc = "/images/rune-throwKnife.png", cost = FocusCost(25), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_thru", name = "Thrust", imageSrc = "/images/rune-thrust.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_thst", name = "Thunderstrike", imageSrc = "/images/rune-thunderstrike.png", cost = FocusCost(150), weaponTypes = WeaponType.staffWeapons),
    Rune(id = "u_tosp", name = "Tornado Spin", imageSrc = "/images/rune-tornadoSpin.png", cost = FocusCost(100), weaponTypes = WeaponType.doubleDaggerWeapons),
    Rune(id = "u_tost", name = "Tornado Strike", imageSrc = "/images/rune-tornadoStrike.png", cost = FocusCost(50), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_trsl", name = "Tremor Slam", imageSrc = "/images/rune-tremorSlam.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_trwa", name = "Tremor Wave", imageSrc = "/images/rune-tremorWave.png", cost = FocusCost(50), weaponTypes = WeaponType.gauntletWeapons),
    Rune(id = "u_trba", name = "Trinity Barrage", imageSrc = "/images/rune-trinityBarrage.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_trsw", name = "Triple Swipe", imageSrc = "/images/rune-tripleSwipe.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_tuki", name = "Turnback Kick", imageSrc = "/images/rune-turnbackKick.png", cost = StaminaCost(30), weaponTypes = WeaponType.allWeapons),
    Rune(id = "u_twda", name = "Twirl Dash", imageSrc = "/images/rune-twirlDash.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_twsp", name = "Twirl Spin", imageSrc = "/images/rune-twirlSpin.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_wa", name = "Wallbreaker", imageSrc = "/images/rune-wallbreaker.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_wh", name = "Whirl", imageSrc = "/images/rune-whirl.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_whst", name = "Whirlstep Strike", imageSrc = "/images/rune-whirlstepStrike.png", cost = FocusCost(100), weaponTypes = WeaponType.oneHandedWeapons),
    Rune(id = "u_ww", name = "Whirlwind", imageSrc = "/images/rune-whirlwind.png", cost = FocusCost(100), weaponTypes = WeaponType.twoHandedWeapons),
    Rune(id = "u_wiru", name = "Wild Rush", imageSrc = "/images/rune-wildRush.png", cost = FocusCost(100), weaponTypes = WeaponType.doubleDaggerWeapons)
)




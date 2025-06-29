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

  case Utility extends WeaponType("u", "Utility")
}

object WeaponType {
  val weaponTypesSelect = Set(
    DoubleDaggers,
    Gauntlets,
    OneHanded,
    TwoHanded,
    Wand,
    Staff,
  )
  val allWeapons = WeaponType.values.toSet

  val shields = Set(Shield)

  val bows = Set(Bow)

  val oneHandedWeapons = Set(AnyWeapon, OneHanded)
  val twoHandedWeapons = Set(AnyWeapon, TwoHanded)
  val wandWeapons = Set(AnyWeapon, OneHanded, Wand, Staff)
  val staffWeapons = Set(AnyWeapon, OneHanded, Wand, Staff)
  val gauntletWeapons = Set(AnyWeapon, DualWield, Gauntlets)
  val doubleDaggerWeapons = Set(AnyWeapon, DualWield, DoubleDaggers)
  val bowWeapons = Set(AnyWeapon, Bow)

  val utilities = Set(Utility)

}

case class GemEffect(itemSlot: ItemSlot, value: String, extra: Boolean = false)
case class Gem(
  id: String,
  value: String,
  imageSrc: String,
  gemEffects: List[GemEffect],
)

sealed trait RuneCost
case class FocusCost(value: Int) extends RuneCost
case class StaminaCost(value: Int) extends RuneCost
case class HealthCost(value: Int) extends RuneCost

case class Rune(
  id: String,
  name: String,
  imageSrc: String,
  cost: RuneCost,
  weaponTypes: Set[WeaponType],
)

enum WeaponSource {
  case Vendor,
    Drop,
    Crafted
}
enum Stat {
  case Strength,
    Dexterity,
    Intelligence,
    Faith
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
  weight: Int,
)

enum EnchantGroup {
  case Attack, // a
    Defense, // d
    Durability, // u
    Focus, // f
    Healing, // h
    Indestructible, // i
    Movement, // m
    Other, // o
    Resistance, // r
    Stamina, // s
    Weight, // w
    Downside // down
}

enum ItemRarity(val id: String, val value: String) {
  case Common extends ItemRarity("c", "common")
  case Magic extends ItemRarity("m", "magic")
  case Plagued extends ItemRarity("p", "plagued")
  case Legendary extends ItemRarity("l", "legendary")
  override def toString(): String = value
}
object ItemRarity {
  def findById(id: String): ItemRarity = ItemRarity
    .values
    .find(_.id == id)
    .getOrElse(ItemRarity.Plagued)
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
  magicEnchants: Map[String, Enchant],
  enchants: Map[String, Enchant],
  enchantDownsides: Map[String, Enchant],
)

case class WeaponBuilderConfig(
  itemConfig: ItemBuilderConfig,
  weaponTypes: Set[WeaponType],
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
  enchantDownsides: List[String] = List.empty,
)

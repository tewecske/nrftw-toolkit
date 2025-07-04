package com.tewe.nrftw.items

import com.tewe.nrftw.Enchant
import com.tewe.nrftw.EnchantGroup.*
import com.tewe.nrftw.ItemBuilderConfig
import com.tewe.nrftw.ItemRarity
import com.tewe.nrftw.ItemSlot.*
import com.tewe.nrftw.WeaponBuilderConfig
import com.tewe.nrftw.WeaponType.*

// format: off

sealed trait ByValue
case object ByValueNone extends ByValue
case class ByValuePercent(value: Int) extends ByValue
case class ByValueFlat(value: Int) extends ByValue
case class ByValueRange(min: Int, max: Int) extends ByValue
case class ByValuePercentRange(min: Int, max: Int) extends ByValue
case class ByValuePercentRangeOf(min: Int, max: Int, stat: StatEffect) extends ByValue

sealed trait Operation
case object OperationNone extends Operation
case class OperationAdd(byValue: ByValue = ByValueNone) extends Operation
case class OperationSubtract(byValue: ByValue = ByValueNone) extends Operation
case class OperationMultiply(byValue: ByValue = ByValueNone) extends Operation
case class OperationDivide(byValue: ByValue = ByValueNone) extends Operation
case class OperationPercent(byValue: ByValue = ByValueNone) extends Operation
case class OperationIgnoredChance(byValue: ByValue = ByValueNone) extends Operation


sealed trait Duration
case object DurationNone extends Duration
case class DurationSeconds(seconds: Int) extends Duration

sealed trait Condition
case object ConditionNone extends Condition
case class ConditionOr(conditions: Condition*) extends Condition
case class ConditionAnd(conditions: Condition*) extends Condition
case class ConditionChance(byValue: ByValue) extends Condition
case object ConditionBlocking extends Condition
case object ConditionCharging extends Condition
case object ConditionColdDamageDealt extends Condition
case object ConditionEnemyFrozen extends Condition
case object ConditionEnemyStaggered extends Condition
case object ConditionEnemyLowHealth extends Condition
case object ConditionEnemyLarge extends Condition
case object ConditionEnemyInfected extends Condition
case object ConditionEnemyBurning extends Condition
case object ConditionEnemyPlagued extends Condition
case object ConditionEnemyLightning extends Condition
case object ConditionEnemyCold extends Condition
case object ConditionEnemyNearby extends Condition
case object ConditionEnemyNotNearby extends Condition
case object ConditionLowHealth extends Condition
case object ConditionFullFocus extends Condition
case object ConditionNotFullFocus extends Condition
case object ConditionLowFocus extends Condition
case object ConditionHighFocus extends Condition
case object ConditionFocusUse extends Condition
case object ConditionFullStamina extends Condition
case object ConditionLowStamina extends Condition
case object ConditionFullHealth extends Condition
case object ConditionBuildupTaken extends Condition
case object ConditionInflictingAnyStatus extends Condition
case object ConditionRuneAttack extends Condition
case object ConditionParry extends Condition
case object ConditionConsumeFood extends Condition
case object ConditionDamageDodged extends Condition
case object ConditionDamageDealt extends Condition
case object ConditionFireDamageDealt extends Condition
case object ConditionBlock extends Condition
case object ConditionSprint extends Condition
case object ConditionBackstab extends Condition
case object ConditionDamageTaken extends Condition
case object ConditionFatality extends Condition
case object ConditionStagger extends Condition
case object ConditionChargedAttack extends Condition
case object ConditionKill extends Condition



sealed trait StatEffect
case class StatArmor(operation: Operation = OperationNone, duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatArmorLowHealth(operation: Operation = OperationNone, duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatAttackSpeed(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatBlock(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatBombDamage(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatBombConsume(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatClimbing(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatColdArmor(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatColdDamage(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatDamage(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatDamageTaken(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatDurability(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatDurabilityLoss(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatElementalDamageTaken(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatEquipLoad(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatExperience(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatExplosion(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatFireArmor(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatFireDamage(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatFocus(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatFocusCost(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatFocusGain(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatFocusRegen(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case object StatFoodHealingOverTime extends StatEffect
case class StatHealthGain(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case object StatHealth extends StatEffect
case object StatHealthMissing extends StatEffect
case class StatHealthRegen(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatHealing(operation: Operation = OperationNone, duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatHealthRally(operation: Operation = OperationNone, duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatHunger(operation: Operation = OperationNone, duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatInfectionBuildup(operation: Operation = OperationNone, duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatKnockbackResistance(operation: Operation = OperationNone, duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatLifesteal(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatLightningArmor(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatLightningDamage(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatLightningDamageOverride(duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatMaxFocus(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatMaxHealth(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatMaxStamina(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatMovementSpeed(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatNormalAttackDamage(operation: Operation = OperationNone, duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatOverallSpeed(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatParryStaminaCost(operation: Operation = OperationNone, duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatPhysicalDamage(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatPhysicalDamageTaken(operation: Operation = OperationNone, duration: Duration = DurationNone, condition: Condition = ConditionNone) extends StatEffect
case class StatPlagueArmor(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatPlagueDamage(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case object StatPlagueDamageOverride extends StatEffect
case class StatPoiseDefense(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatStamina(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatStaminaCost(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatStaminaRegen(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatStaggerResistance(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatDodgeStaminaCost(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatWeight(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatBurnBuildup(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatExplosionOnRuneAttack(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatChargedAttackDamage(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatBuildUpResistance(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect
case class StatEnemyFrozenArmor(operation: Operation = OperationNone, duration: Duration = DurationNone, condition:Condition = ConditionNone) extends StatEffect


case object StatHeavyRollOnly extends StatEffect
case object StatParryDisabled extends StatEffect
case object StatUnrepairable extends StatEffect

//Frozen enemy armor?
//executeAgainstInfectedLowHealth


case class Enchantment(
  id: String,
  name: String,
  textCode: String,
  description: String,
  statEffect: StatEffect,
)


object Enchantments {
  // enchantments.armorDecreased.Description=[StatArmor] <b>Armor</b> decreased by {0}
  val armorDecreased = Enchantment(
    id = "armorDecreased",
    name = "Armor Decreased",
    textCode = "enchantments.armorDecreased",
    description = "[StatArmor] <b>Armor</b> decreased by {0}",
    statEffect = StatArmor(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.armorIncreased.Description=[StatArmor] <b>Armor</b> increased by {0}
  val armorIncreased = Enchantment(
    id = "armorIncreased",
    name = "Armor Increased",
    textCode = "enchantments.armorIncreased",
    description = "[StatArmor] <b>Armor</b> increased by {0}",
    statEffect = StatArmor(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.attackDamageDecreased.Description=[StatDamage] <b>Attack Damage</b> decreased by {0}
  val attackDamageDecreased = Enchantment(
    id = "attackDamageDecreased",
    name = "Attack Damage Decreased",
    textCode = "enchantments.attackDamageDecreased",
    description = "[StatDamage] <b>Attack Damage</b> decreased by {0}",
    statEffect = StatDamage(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.attackDamageIncreased.Description=[StatDamage] <b>Attack Damage</b> increased by {0}
  val attackDamageIncreased = Enchantment(
    id = "attackDamageIncreased",
    name = "Attack Damage Increased",
    textCode = "enchantments.attackDamageIncreased",
    description = "[StatDamage] <b>Attack Damage</b> increased by {0}",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.attackDamageIncreasedAfterRuneAttack.Description=[StatDamage] <b>Attack Damage</b> increased by {0} for {1} seconds after Rune Attack
  val attackDamageIncreasedAfterRuneAttack = Enchantment(
    id = "attackDamageIncreasedAfterRuneAttack",
    name = "Attack Damage Increased After Rune Attack",
    textCode = "enchantments.attackDamageIncreasedAfterRuneAttack",
    description = "[StatDamage] <b>Attack Damage</b> increased by {0} for {1} seconds after Rune Attack",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionRuneAttack),
  )
  // enchantments.attackSpeedIncreased.Description=[StatAttackSpeed] <b>Attack Speed</b> increased by {0}
  val attackSpeedIncreased = Enchantment(
    id = "attackSpeedIncreased",
    name = "Attack Speed Increased",
    textCode = "enchantments.attackSpeedIncreased",
    description = "[StatAttackSpeed] <b>Attack Speed</b> increased by {0}",
    statEffect = StatAttackSpeed(OperationAdd(ByValuePercentRange(5, 15))),
  )
  // enchantments.attackSpeedIncreasedAfterKill.Description=[StatAttackSpeed] <b>Attack Speed</b> increased by {1} for <b>{0} Seconds</b> after Kill
  val attackSpeedIncreasedAfterKill = Enchantment(
    id = "attackSpeedIncreasedAfterKill",
    name = "Attack Speed Increased After Kill",
    textCode = "enchantments.attackSpeedIncreasedAfterKill",
    description = "[StatAttackSpeed] <b>Attack Speed</b> increased by {1} for <b>{0} Seconds</b> after Kill",
    statEffect = StatAttackSpeed(OperationAdd(ByValuePercentRange(5, 15)), DurationSeconds(10), ConditionKill),
  )
  // enchantments.attackStaminaCostDecreased.Description=[StatStaminaCost] <b>Attack Stamina Cost</b> decreased by {0}
  val attackStaminaCostDecreased = Enchantment(
    id = "attackStaminaCostDecreased",
    name = "Attack Stamina Cost Decreased",
    textCode = "enchantments.attackStaminaCostDecreased",
    description = "[StatStaminaCost] <b>Attack Stamina Cost</b> decreased by {0}",
    statEffect = StatStaminaCost(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.attackStaminaCostDecreasedAfterParry.Description=[StatStaminaCost] <b>Attack Stamina Cost</b> decreased by {0} for {1} seconds after Parry
  val attackStaminaCostDecreasedAfterParry = Enchantment(
    id = "attackStaminaCostDecreasedAfterParry",
    name = "Attack Stamina Cost Decreased After Parry",
    textCode = "enchantments.attackStaminaCostDecreasedAfterParry",
    description = "[StatStaminaCost] <b>Attack Stamina Cost</b> decreased by {0} for {1} seconds after Parry",
    statEffect = StatStaminaCost(OperationSubtract(ByValuePercentRange(20, 40)), DurationSeconds(5), ConditionParry),
  )
  // enchantments.attackStaminaCostIncreased.Description=[StatStaminaCost] <b>Attack Stamina Cost</b> increased by {0}
  val attackStaminaCostIncreased = Enchantment(
    id = "attackStaminaCostIncreased",
    name = "Attack Stamina Cost Increased",
    textCode = "enchantments.attackStaminaCostIncreased",
    description = "[StatStaminaCost] <b>Attack Stamina Cost</b> increased by {0}",
    statEffect = StatStaminaCost(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.blockingDisabled.Description=<b>Block</b> disabled
  val blockingDisabled = Enchantment(
    id = "blockingDisabled",
    name = "Blocking Disabled",
    textCode = "enchantments.blockingDisabled",
    description = "<b>Block</b> disabled",
    statEffect = StatBlock(OperationSubtract(ByValuePercent(100))),
  )
  // enchantments.bombDamageIncreased.Description=<b>Bomb Damage</b> increased by {0}
  val bombDamageIncreased = Enchantment(
    id = "bombDamageIncreased",
    name = "Bomb Damage Increased",
    textCode = "enchantments.bombDamageIncreased",
    description = "<b>Bomb Damage</b> increased by {0}",
    statEffect = StatBombDamage(OperationAdd(ByValuePercentRange(10, 25))),
  )
  // enchantments.buildupResistance.Description=[StatElementalDamageTaken] <b>Buildup Resistance</b> increased by {0}
  val buildupResistance = Enchantment(
    id = "buildupResistance",
    name = "Buildup Resistance",
    textCode = "enchantments.buildupResistance",
    description = "[StatElementalDamageTaken] <b>Buildup Resistance</b> increased by {0}",
    statEffect = StatBuildUpResistance(OperationAdd(ByValuePercentRange(10, 25))),
  )
  // enchantments.burnBuildupIncreased.Description=[StatFireDamage] [!sFire]Burn Buildup[/s] increased by {0}
  val burnBuildupIncreased = Enchantment(
    id = "burnBuildupIncreased",
    name = "Burn Buildup Increased",
    textCode = "enchantments.burnBuildupIncreased",
    description = "[StatFireDamage] [!sFire]Burn Buildup[/s] increased by {0}",
    statEffect = StatBurnBuildup(OperationAdd(ByValuePercentRange(10, 25))),
  )
  // enchantments.burnDamageIncreased.Description=[StatFireDamage] [!sFire]Burn Damage[/s] increased by {0}
  val burnDamageIncreased = Enchantment(
    id = "burnDamageIncreased",
    name = "Burn Damage Increased",
    textCode = "enchantments.burnDamageIncreased",
    description = "[StatFireDamage] [!sFire]Burn Damage[/s] increased by {0}",
    statEffect = StatFireDamage(OperationAdd(ByValuePercentRange(10, 25))),
  )
  // enchantments.chanceToIngoreDurabilityLoss.Description=[StatDurability] <b>Durability Loss</b> ignored with {0} chance
  val chanceToIngoreDurabilityLoss = Enchantment(
    id = "chanceToIngoreDurabilityLoss",
    name = "Chance To Ingore Durability Loss",
    textCode = "enchantments.chanceToIngoreDurabilityLoss",
    description = "[StatDurability] <b>Durability Loss</b> ignored with {0} chance",
    statEffect = StatDurabilityLoss(OperationIgnoredChance(ByValuePercentRange(10, 25))),
  )
  // enchantments.chanceToNotConsumeBomb.Description=<b>Bomb not consumed</b> with {0} chance
  val chanceToNotConsumeBomb = Enchantment(
    id = "chanceToNotConsumeBomb",
    name = "Chance To Not Consume Bomb",
    textCode = "enchantments.chanceToNotConsumeBomb",
    description = "<b>Bomb not consumed</b> with {0} chance",
    statEffect = StatBombConsume(OperationIgnoredChance(ByValuePercentRange(10, 25))),
  )
  // enchantments.chanceToSpawnExplosionOnRuneAttack.Description=Deal {0} Explosion [StatFireDamage] [!sFire]Damage[/s] on Rune Attack with {1} chance
  val chanceToSpawnExplosionOnRuneAttack = Enchantment(
    id = "chanceToSpawnExplosionOnRuneAttack",
    name = "Chance To Spawn Explosion On Rune Attack",
    textCode = "enchantments.chanceToSpawnExplosionOnRuneAttack",
    description = "Deal {0} Explosion [StatFireDamage] [!sFire]Damage[/s] on Rune Attack with {1} chance",
    statEffect = StatExplosion(OperationAdd(ByValueRange(15, 30)), condition = ConditionAnd(ConditionRuneAttack, ConditionChance(ByValuePercentRange(10, 25)))),
  )
  // enchantments.chargedDamageIncreased.Description=[StatDamage] <b>Charged Attack Damage</b> increased by {0}
  val chargedDamageIncreased = Enchantment(
    id = "chargedDamageIncreased",
    name = "Charged Damage Increased",
    textCode = "enchantments.chargedDamageIncreased",
    description = "[StatDamage] <b>Charged Attack Damage</b> increased by {0}",
    statEffect = StatChargedAttackDamage(OperationAdd(ByValuePercentRange(15, 30))),
  )
  // enchantments.coldDamageIncreasedAfterHeatDamageDealt.Descritpion=[StatColdDamage] [!sIce]Ice Damage[/s] increased by {0} for {1} seconds after [!sFire]Fire Damage[/s] Dealt
  val coldDamageIncreasedAfterHeatDamageDealt = Enchantment(
    id = "coldDamageIncreasedAfterHeatDamageDealt",
    name = "Cold Damage Increased After Heat Damage Dealt",
    textCode = "enchantments.coldDamageIncreasedAfterHeatDamageDealt",
    description = "[StatColdDamage] [!sIce]Ice Damage[/s] increased by {0} for {1} seconds after [!sFire]Fire Damage[/s] Dealt",
    statEffect = StatColdDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionFireDamageDealt),
  )
  // enchantments.coldResistanceIncreased.Description=[StatColdArmor] <b>Ice Resistance</b> increased by {0}
  val coldResistanceIncreased = Enchantment(
    id = "coldResistanceIncreased",
    name = "Cold Resistance Increased",
    textCode = "enchantments.coldResistanceIncreased",
    description = "[StatColdArmor] <b>Ice Resistance</b> increased by {0}",
    statEffect = StatColdArmor(OperationAdd(ByValuePercentRange(10, 25))),
  )
  // enchantments.damageDecreasedAfterParryOrDodged.Description=[StatDamage] <b>Damage</b> decreased by {0} for {1} seconds after Parry or Damage Dodged
  val damageDecreasedAfterParryOrDodged = Enchantment(
    id = "damageDecreasedAfterParryOrDodged",
    name = "Damage Decreased After Parry Or Dodged",
    textCode = "enchantments.damageDecreasedAfterParryOrDodged",
    description = "[StatDamage] <b>Damage</b> decreased by {0} for {1} seconds after Parry or Damage Dodged",
    statEffect = StatDamage(OperationSubtract(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionOr(ConditionParry, ConditionDamageDodged)),
  )
  // enchantments.damageIncreased.Description=[StatDamage] <b>Damage</b> increased by {0}
  val damageIncreased = Enchantment(
    id = "damageIncreased",
    name = "Damage Increased",
    textCode = "enchantments.damageIncreased",
    description = "[StatDamage] <b>Damage</b> increased by {0}",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.damageIncreasedAfterBackstab.Description=[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Backstab
  val damageIncreasedAfterBackstab = Enchantment(
    id = "damageIncreasedAfterBackstab",
    name = "Damage Increased After Backstab",
    textCode = "enchantments.damageIncreasedAfterBackstab",
    description = "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Backstab",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionBackstab),
  )
  // enchantments.damageIncreasedAfterBlock.Description=[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Block
  val damageIncreasedAfterBlock = Enchantment(
    id = "damageIncreasedAfterBlock",
    name = "Damage Increased After Block",
    textCode = "enchantments.damageIncreasedAfterBlock",
    description = "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Block",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionBlock),
  )
  // enchantments.damageIncreasedAfterDamageTaken.Description=[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Damage Taken
  val damageIncreasedAfterDamageTaken = Enchantment(
    id = "damageIncreasedAfterDamageTaken",
    name = "Damage Increased After Damage Taken",
    textCode = "enchantments.damageIncreasedAfterDamageTaken",
    description = "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Damage Taken",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionDamageTaken),
  )
  // enchantments.damageIncreasedAfterDodged.Description=[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Damage Dodged
  val damageIncreasedAfterDodged = Enchantment(
    id = "damageIncreasedAfterDodged",
    name = "Damage Increased After Dodged",
    textCode = "enchantments.damageIncreasedAfterDodged",
    description = "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Damage Dodged",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionDamageDodged),
  )
  // enchantments.damageIncreasedAfterFatality.Description=[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Fatality
  val damageIncreasedAfterFatality = Enchantment(
    id = "damageIncreasedAfterFatality",
    name = "Damage Increased After Fatality",
    textCode = "enchantments.damageIncreasedAfterFatality",
    description = "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Fatality",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionFatality),
  )
  // enchantments.damageIncreasedAfterParry.Description=[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Parry
  val damageIncreasedAfterParry = Enchantment(
    id = "damageIncreasedAfterParry",
    name = "Damage Increased After Parry",
    textCode = "enchantments.damageIncreasedAfterParry",
    description = "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Parry",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionParry),
  )
  // enchantments.damageIncreasedAfterSprint.Description=[StatDamage] <b>Damage</b> increased by {0} after Sprinting for {1} seconds
  val damageIncreasedAfterSprint = Enchantment(
    id = "damageIncreasedAfterSprint",
    name = "Damage Increased After Sprint",
    textCode = "enchantments.damageIncreasedAfterSprint",
    description = "[StatDamage] <b>Damage</b> increased by {0} after Sprinting for {1} seconds",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionSprint),
  )
  // enchantments.damageIncreasedAfterStagger.Description=[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Staggering an Enemy
  val damageIncreasedAfterStagger = Enchantment(
    id = "damageIncreasedAfterStagger",
    name = "Damage Increased After Stagger",
    textCode = "enchantments.damageIncreasedAfterStagger",
    description = "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Staggering an Enemy",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionEnemyStaggered),
  )
  // enchantments.damageIncreasedAgainstLargeEnemies.Description=[StatDamage] <b>Damage</b> increased by {0} against Large Enemies
  val damageIncreasedAgainstLargeEnemies = Enchantment(
    id = "damageIncreasedAgainstLargeEnemies",
    name = "Damage Increased Against Large Enemies",
    textCode = "enchantments.damageIncreasedAgainstLargeEnemies",
    description = "[StatDamage] <b>Damage</b> increased by {0} against Large Enemies",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), condition = ConditionEnemyLarge),
  )
  // enchantments.damageIncreasedAgainstLowHealth.Description=[StatDamage] <b>Damage</b> increased by {0} against Low Health Enemies
  val damageIncreasedAgainstLowHealth = Enchantment(
    id = "damageIncreasedAgainstLowHealth",
    name = "Damage Increased Against Low Health Enemies",
    textCode = "enchantments.damageIncreasedAgainstLowHealth",
    description = "[StatDamage] <b>Damage</b> increased by {0} against Low Health Enemies",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), condition = ConditionEnemyLowHealth),
  )
  // enchantments.damageIncreasedAtFullFocus.Description=[StatDamage] <b>Damage</b> increased by {0} at Full Focus
  val damageIncreasedAtFullFocus = Enchantment(
    id = "damageIncreasedAtFullFocus",
    name = "Damage Increased At Full Focus",
    textCode = "enchantments.damageIncreasedAtFullFocus",
    description = "[StatDamage] <b>Damage</b> increased by {0} at Full Focus",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), condition = ConditionFullFocus),
  )
  // enchantments.damageIncreasedAtHighFocus.Description=[StatDamage] <b>Damage</b> increased by {0} at High Focus
  val damageIncreasedAtHighFocus = Enchantment(
    id = "damageIncreasedAtHighFocus",
    name = "Damage Increased At High Focus",
    textCode = "enchantments.damageIncreasedAtHighFocus",
    description = "[StatDamage] <b>Damage</b> increased by {0} at High Focus",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), condition = ConditionHighFocus),
  )
  // enchantments.damageIncreasedAtLowHealth.Description=[StatDamage] <b>Damage</b> increased by {0} at Low Health
  val damageIncreasedAtLowHealth = Enchantment(
    id = "damageIncreasedAtLowHealth",
    name = "Damage Increased At Low Health",
    textCode = "enchantments.damageIncreasedAtLowHealth",
    description = "[StatDamage] <b>Damage</b> increased by {0} at Low Health",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), condition = ConditionLowHealth),
  )
  // enchantments.damageIncreasedPerNearbyEnemy.Description=[StatDamage] <b>Damage</b> increased by {0} for each Nearby Enemy
  val damageIncreasedPerNearbyEnemy = Enchantment(
    id = "damageIncreasedPerNearbyEnemy",
    name = "Damage Increased Per Nearby Enemy",
    textCode = "enchantments.damageIncreasedPerNearbyEnemy",
    description = "[StatDamage] <b>Damage</b> increased by {0} for each Nearby Enemy",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(3, 7)), condition = ConditionEnemyNearby),
  )
  // enchantments.damageIncreasedWhenNoNearbyEnemy.Description=[StatDamage] <b>Damage</b> increased by {0} if no Enemies nearby
  val damageIncreasedWhenNoNearbyEnemy = Enchantment(
    id = "damageIncreasedWhenNoNearbyEnemy",
    name = "Damage Increased When No Nearby Enemy",
    textCode = "enchantments.damageIncreasedWhenNoNearbyEnemy",
    description = "[StatDamage] <b>Damage</b> increased by {0} if no Enemies nearby",
    statEffect = StatDamage(OperationAdd(ByValuePercentRange(10, 20)), condition = ConditionEnemyNotNearby),
  )
  // enchantments.damageScalesWithCurrentFocus.Description=[StatDamage] <b>Damage</b> increased by {0} of [StatFocus] Current Focus
  val damageScalesWithCurrentFocus = Enchantment(
    id = "damageScalesWithCurrentFocus",
    name = "Damage Scales With Current Focus",
    textCode = "enchantments.damageScalesWithCurrentFocus",
    description = "[StatDamage] <b>Damage</b> increased by {0} of [StatFocus] Current Focus",
    statEffect = StatDamage(OperationAdd(ByValuePercentRangeOf(10, 20, StatFocus()))),
  )
  // enchantments.damageScalesWithDurability.Description=[StatDamage] <b>Damage</b> increased by up to {0} based on Durability
  val damageScalesWithDurability = Enchantment(
    id = "damageScalesWithDurability",
    name = "Damage Scales With Durability",
    textCode = "enchantments.damageScalesWithDurability",
    description = "[StatDamage] <b>Damage</b> increased by up to {0} based on Durability",
    statEffect = StatDamage(OperationAdd(ByValuePercentRangeOf(10, 20, StatDurability()))),
  )
  // enchantments.damageScalesWithMissingHealth.Description=[StatDamage] <b>Damage</b> increased by up to {0} based on Missing Health
  val damageScalesWithMissingHealth = Enchantment(
    id = "damageScalesWithMissingHealth",
    name = "Damage Scales With Missing Health",
    textCode = "enchantments.damageScalesWithMissingHealth",
    description = "[StatDamage] <b>Damage</b> increased by up to {0} based on Missing Health",
    statEffect = StatDamage(OperationAdd(ByValuePercentRangeOf(10, 20, StatHealthMissing))),
  )
  // enchantments.damageTakenDecreasedAfterBuildupTaken.Description=[StatDamageTaken] <b>Damage Taken</b> decreased by {0} for {1} seconds after being afflicted by Status
  val damageTakenDecreasedAfterBuildupTaken = Enchantment(
    id = "damageTakenDecreasedAfterBuildupTaken",
    name = "Damage Taken Decreased After Buildup Taken",
    textCode = "enchantments.damageTakenDecreasedAfterBuildupTaken",
    description = "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} for {1} seconds after being afflicted by Status",
    statEffect = StatDamageTaken(OperationSubtract(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionBuildupTaken),
  )
  // enchantments.damageTakenDecreasedAtFullStamina.Description=[StatDamageTaken] <b>Damage Taken</b> decreased by {0} at Full Stamina
  val damageTakenDecreasedAtFullStamina = Enchantment(
    id = "damageTakenDecreasedAtFullStamina",
    name = "Damage Taken Decreased At Full Stamina",
    textCode = "enchantments.damageTakenDecreasedAtFullStamina",
    description = "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} at Full Stamina",
    statEffect = StatDamageTaken(OperationSubtract(ByValuePercentRange(10, 20)), condition = ConditionFullStamina),
  )
  // enchantments.damageTakenDecreasedPerNearbyEnemy.Description=[StatDamageTaken] <b>Damage Taken</b> decreased by {0} for each Nearby Enemy
  val damageTakenDecreasedPerNearbyEnemy = Enchantment(
    id = "damageTakenDecreasedPerNearbyEnemy",
    name = "Damage Taken Decreased Per Nearby Enemy",
    textCode = "enchantments.damageTakenDecreasedPerNearbyEnemy",
    description = "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} for each Nearby Enemy",
    statEffect = StatDamageTaken(OperationSubtract(ByValuePercentRange(3, 7)), condition = ConditionEnemyNearby),
  )
  // enchantments.damageTakenDecreasedWhileBlocking.Description=[StatDamageTaken] <b>Damage Taken</b> decreased by {0} while Blocking
  val damageTakenDecreasedWhileBlocking = Enchantment(
    id = "damageTakenDecreasedWhileBlocking",
    name = "Damage Taken Decreased While Blocking",
    textCode = "enchantments.damageTakenDecreasedWhileBlocking",
    description = "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} while Blocking",
    statEffect = StatDamageTaken(OperationSubtract(ByValuePercentRange(10, 20)), condition = ConditionBlocking),
  )
  // enchantments.damageTakenDecreasedWhileCharging.Description=[StatDamageTaken] <b>Damage Taken</b> decreased by {0} while Charging
  val damageTakenDecreasedWhileCharging = Enchantment(
    id = "damageTakenDecreasedWhileCharging",
    name = "Damage Taken Decreased While Charging",
    textCode = "enchantments.damageTakenDecreasedWhileCharging",
    description = "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} while Charging",
    statEffect = StatDamageTaken(OperationSubtract(ByValuePercentRange(10, 20)), condition = ConditionCharging),
  )
  // enchantments.damageTakenIncreasedAfterConsumeFood.Description=[StatDamageTaken] <b>Damage Taken</b> increased by {0} for {1} seconds after Consuming Food
  val damageTakenIncreasedAfterConsumeFood = Enchantment(
    id = "damageTakenIncreasedAfterConsumeFood",
    name = "Damage Taken Increased After Consume Food",
    textCode = "enchantments.damageTakenIncreasedAfterConsumeFood",
    description = "[StatDamageTaken] <b>Damage Taken</b> increased by {0} for {1} seconds after Consuming Food",
    statEffect = StatDamageTaken(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionConsumeFood),
  )
  // enchantments.damageTakenIncreasedAtLowStamina.Description=[StatDamageTaken] <b>Damage Taken</b> increased by {0} at Low Stamina
  val damageTakenIncreasedAtLowStamina = Enchantment(
    id = "damageTakenIncreasedAtLowStamina",
    name = "Damage Taken Increased At Low Stamina",
    textCode = "enchantments.damageTakenIncreasedAtLowStamina",
    description = "[StatDamageTaken] <b>Damage Taken</b> increased by {0} at Low Stamina",
    statEffect = StatDamageTaken(OperationAdd(ByValuePercentRange(10, 20)), condition = ConditionLowStamina),
  )
  // enchantments.damageTakenIncreasedIfNotFullFocus.Description=[StatDamageTaken] <b>Damage Taken</b> increased by {0} if not at Full Focus
  val damageTakenIncreasedIfNotFullFocus = Enchantment(
    id = "damageTakenIncreasedIfNotFullFocus",
    name = "Damage Taken Increased If Not Full Focus",
    textCode = "enchantments.damageTakenIncreasedIfNotFullFocus",
    description = "[StatDamageTaken] <b>Damage Taken</b> increased by {0} if not at Full Focus",
    statEffect = StatDamageTaken(OperationAdd(ByValuePercentRange(10, 20)), condition = ConditionNotFullFocus),
  )
  // enchantments.damageTakenScalesWithDurability.Description=[StatDamageTaken] <b>Damage Taken</b> decreased by up to {0} based on Durability
  val damageTakenScalesWithDurability = Enchantment(
    id = "damageTakenScalesWithDurability",
    name = "Damage Taken Scales With Durability",
    textCode = "enchantments.damageTakenScalesWithDurability",
    description = "[StatDamageTaken] <b>Damage Taken</b> decreased by up to {0} based on Durability",
    statEffect = StatDamageTaken(OperationSubtract(ByValuePercentRangeOf(10, 20, StatDurability()))),
  )
  // enchantments.dealColdDamageOnBlock.Description=Deal {0} [StatColdDamage] [!sIce]Ice Damage[/s] on Block
  val dealColdDamageOnBlock = Enchantment(
    id = "dealColdDamageOnBlock",
    name = "Deal Cold Damage On Block",
    textCode = "enchantments.dealColdDamageOnBlock",
    description = "Deal {0} [StatColdDamage] [!sIce]Ice Damage[/s] on Block",
    statEffect = StatColdDamage(OperationAdd(ByValueRange(15, 30)), condition = ConditionBlock),
  )
  // enchantments.dealDamageOnBlock.Description=Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Block
  val dealDamageOnBlock = Enchantment(
    id = "dealDamageOnBlock",
    name = "Deal Damage On Block",
    textCode = "enchantments.dealDamageOnBlock",
    description = "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Block",
    statEffect = StatPhysicalDamage(OperationAdd(ByValueRange(15, 30)), condition = ConditionBlock),
  )
  // enchantments.dealDamageOnDamageTaken.Description=Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Damage Taken
  val dealDamageOnDamageTaken = Enchantment(
    id = "dealDamageOnDamageTaken",
    name = "Deal Damage On Damage Taken",
    textCode = "enchantments.dealDamageOnDamageTaken",
    description = "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Damage Taken",
    statEffect = StatPhysicalDamage(OperationAdd(ByValueRange(15, 30)), condition = ConditionDamageTaken),
  )
  // enchantments.dealDamageOnDodged.Description=Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Damage Dodged
  val dealDamageOnDodged = Enchantment(
    id = "dealDamageOnDodged",
    name = "Deal Damage On Dodged",
    textCode = "enchantments.dealDamageOnDodged",
    description = "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Damage Dodged",
    statEffect = StatPhysicalDamage(OperationAdd(ByValueRange(15, 30)), condition = ConditionDamageDodged),
  )
  // enchantments.dealDamageOnParry.Description=Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Parry
  val dealDamageOnParry = Enchantment(
    id = "dealDamageOnParry",
    name = "Deal Damage On Parry",
    textCode = "enchantments.dealDamageOnParry",
    description = "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Parry",
    statEffect = StatPhysicalDamage(OperationAdd(ByValueRange(15, 30)), condition = ConditionParry),
  )
  // enchantments.dealDamageOnStagger.Description=Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Staggering an Enemy
  val dealDamageOnStagger = Enchantment(
    id = "dealDamageOnStagger",
    name = "Deal Damage On Stagger",
    textCode = "enchantments.dealDamageOnStagger",
    description = "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Staggering an Enemy",
    statEffect = StatPhysicalDamage(OperationAdd(ByValueRange(15, 30)), condition = ConditionStagger),
  )
  // enchantments.dealElectricDamageOnBlock.Description=Deal {0} [StatLightningDamage] [!sLightning]Lightning Damage[/s] on Block
  val dealElectricDamageOnBlock = Enchantment(
    id = "dealElectricDamageOnBlock",
    name = "Deal Electric Damage On Block",
    textCode = "enchantments.dealElectricDamageOnBlock",
    description = "Deal {0} [StatLightningDamage] [!sLightning]Lightning Damage[/s] on Block",
    statEffect = StatLightningDamage(OperationAdd(ByValueRange(15, 30)), condition = ConditionBlock),
  )
  // enchantments.dealHeatDamageOnBlock.Description=Deal {0} [StatFireDamage] [!sFire]Fire Damage[/s] on Block
  val dealHeatDamageOnBlock = Enchantment(
    id = "dealHeatDamageOnBlock",
    name = "Deal Heat Damage On Block",
    textCode = "enchantments.dealHeatDamageOnBlock",
    description = "Deal {0} [StatFireDamage] [!sFire]Fire Damage[/s] on Block",
    statEffect = StatFireDamage(OperationAdd(ByValueRange(15, 30)), condition = ConditionBlock),
  )
  // enchantments.dealHeatDamageOnRuneAttack.Description=Deal {0} [StatFireDamage] [!sFire]Fire Damage[/s] on Rune Attack
  val dealHeatDamageOnRuneAttack = Enchantment(
    id = "dealHeatDamageOnRuneAttack",
    name = "Deal Heat Damage On Rune Attack",
    textCode = "enchantments.dealHeatDamageOnRuneAttack",
    description = "Deal {0} [StatFireDamage] [!sFire]Fire Damage[/s] on Rune Attack",
    statEffect = StatFireDamage(OperationAdd(ByValueRange(15, 30)), condition = ConditionRuneAttack),
  )
  // enchantments.delayedHealing.Description=<b>Food Healing</b> is applied over time
  val delayedHealing = Enchantment(
    id = "delayedHealing",
    name = "Delayed Healing",
    textCode = "enchantments.delayedHealing",
    description = "<b>Food Healing</b> is applied over time",
    statEffect = StatFoodHealingOverTime,
  )
  // enchantments.dodgeStaminaCostDecreasedAtLowHealth.Description=[StatStaminaCost] <b>Dodge Stamina Cost</b> decreased by {0} at Low Health
  val dodgeStaminaCostDecreasedAtLowHealth = Enchantment(
    id = "dodgeStaminaCostDecreasedAtLowHealth",
    name = "Dodge Stamina Cost Decreased At Low Health",
    textCode = "enchantments.dodgeStaminaCostDecreasedAtLowHealth",
    description = "[StatStaminaCost] <b>Dodge Stamina Cost</b> decreased by {0} at Low Health",
    statEffect = StatDodgeStaminaCost(OperationSubtract(ByValuePercentRange(10, 20)), condition = ConditionLowHealth),
  )
  // enchantments.dodgeStaminaCostIncreased.Description=[StatStaminaCost] <b>Dodge Stamina Cost</b> increased by {0}
  val dodgeStaminaCostIncreased = Enchantment(
    id = "dodgeStaminaCostIncreased",
    name = "Dodge Stamina Cost Increased",
    textCode = "enchantments.dodgeStaminaCostIncreased",
    description = "[StatStaminaCost] <b>Dodge Stamina Cost</b> increased by {0}",
    statEffect = StatDodgeStaminaCost(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.drainFocusInCombat.Description=[StatFocusRegen] <b>Drain Focus</b> in Combat
  val drainFocusInCombat = Enchantment(
    id = "drainFocusInCombat",
    name = "Drain Focus In Combat",
    textCode = "enchantments.drainFocusInCombat",
    description = "[StatFocusRegen] <b>Drain Focus</b> in Combat",
    statEffect = StatFocusRegen(OperationSubtract(ByValueRange(10, 20))),
  )
  // enchantments.drainHealthInCombat.Description=[StatHealthRegen] <b>Drain Health</b> in Combat
  val drainHealthInCombat = Enchantment(
    id = "drainHealthInCombat",
    name = "Drain Health In Combat",
    textCode = "enchantments.drainHealthInCombat",
    description = "[StatHealthRegen] <b>Drain Health</b> in Combat",
    statEffect = StatHealthRegen(OperationSubtract(ByValueRange(10, 20))),
  )
  // enchantments.durabilityIncreased.Description=Item [StatDurability] <b>Durability</b> increased by {0}
  val durabilityIncreased = Enchantment(
    id = "durabilityIncreased",
    name = "Durability Increased",
    textCode = "enchantments.durabilityIncreased",
    description = "Item [StatDurability] <b>Durability</b> increased by {0}",
    statEffect = StatDurability(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.elementalDamageTakenDecreased.Description=[StatElementalDamageTaken] <b>Elemental Damage Taken</b> decreased by {0}
  val elementalDamageTakenDecreased = Enchantment(
    id = "elementalDamageTakenDecreased",
    name = "Elemental Damage Taken Decreased",
    textCode = "enchantments.elementalDamageTakenDecreased",
    description = "[StatElementalDamageTaken] <b>Elemental Damage Taken</b> decreased by {0}",
    statEffect = StatElementalDamageTaken(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.endlessClimbing.Description=<b>Endless Climbing</b>
  val endlessClimbing = Enchantment(
    id = "endlessClimbing",
    name = "Endless Climbing",
    textCode = "enchantments.endlessClimbing",
    description = "<b>Endless Climbing</b>",
    statEffect = StatClimbing(OperationAdd(ByValueRange(10, 20))),
  )
  // enchantments.enemyArmorDecreasedAfterFrost.Description=[!sIce]Frozen[/s] Enemy [StatArmor] <b>Armor</b> decreased by {0}
  val enemyArmorDecreasedAfterFrost = Enchantment(
    id = "enemyArmorDecreasedAfterFrost",
    name = "Enemy Armor Decreased After Frost",
    textCode = "enchantments.enemyArmorDecreasedAfterFrost",
    description = "[!sIce]Frozen[/s] Enemy [StatArmor] <b>Armor</b> decreased by {0}",
    statEffect = StatEnemyFrozenArmor(OperationSubtract(ByValuePercentRange(10, 20)), condition = ConditionEnemyFrozen),
  )
  // enchantments.enemyStaggerResistanceDecreasedAfterInfection.Description=[!sPlague]Infected[/s] Enemy [StatStaggerResistance] <b>Stagger Resistance</b> decreased by {0}
  val enemyStaggerResistanceDecreasedAfterInfection = Enchantment(
    id = "enemyStaggerResistanceDecreasedAfterInfection",
    name = "Enemy Stagger Resistance Decreased After Infection",
    textCode = "enchantments.enemyStaggerResistanceDecreasedAfterInfection",
    description = "[!sPlague]Infected[/s] Enemy [StatStaggerResistance] <b>Stagger Resistance</b> decreased by {0}",
    statEffect = StatStaggerResistance(OperationSubtract(ByValuePercentRange(10, 20)), condition = ConditionEnemyInfected),
  )
  // enchantments.equipLoadDecreased.Description=[StatEquipLoad] <b>Equip Load</b> decreased by {0}
  val equipLoadDecreased = Enchantment(
    id = "equipLoadDecreased",
    name = "Equip Load Decreased",
    textCode = "enchantments.equipLoadDecreased",
    description = "[StatEquipLoad] <b>Equip Load</b> decreased by {0}",
    statEffect = StatEquipLoad(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.equipLoadIncreased.Description=[StatEquipLoad] <b>Equip Load</b> increased by {0}
  val equipLoadIncreased = Enchantment(
    id = "equipLoadIncreased",
    name = "Equip Load Increased",
    textCode = "enchantments.equipLoadIncreased",
    description = "[StatEquipLoad] <b>Equip Load</b> increased by {0}",
    statEffect = StatEquipLoad(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.executeAgainstInfectedLowHealth.Description=Execute [!sPlague]Infected[/s] Low Health Enemies on Damage Dealt
  val executeAgainstInfectedLowHealth = Enchantment(
    id = "executeAgainstInfectedLowHealth",
    name = "Execute Against Infected Low Health",
    textCode = "enchantments.executeAgainstInfectedLowHealth",
    description = "Execute [!sPlague]Infected[/s] Low Health Enemies on Damage Dealt",
    statEffect = StatDamage(OperationAdd(ByValueRange(10, 20)), condition = ConditionEnemyInfected),
  )
  // enchantments.fireDamageIncreased.Description=[StatFireDamage] [!sFire]Fire Damage[/s] increased by {0}
  val fireDamageIncreased = Enchantment(
    id = "fireDamageIncreased",
    name = "Fire Damage Increased",
    textCode = "enchantments.fireDamageIncreased",
    description = "[StatFireDamage] [!sFire]Fire Damage[/s] increased by {0}",
    statEffect = StatFireDamage(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.fireResistanceIncreased.Description=[StatFireArmor] <b>Fire Resistance</b> increased by {0}
  val fireResistanceIncreased = Enchantment(
    id = "fireResistanceIncreased",
    name = "Fire Resistance Increased",
    textCode = "enchantments.fireResistanceIncreased",
    description = "[StatFireArmor] <b>Fire Resistance</b> increased by {0}",
    statEffect = StatFireArmor(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.focusCostDecreased.Description=[StatFocusGain] <b>Focus Cost</b> decreased by {0}
  val focusCostDecreased = Enchantment(
    id = "focusCostDecreased",
    name = "Focus Cost Decreased",
    textCode = "enchantments.focusCostDecreased",
    description = "[StatFocusGain] <b>Focus Cost</b> decreased by {0}",
    statEffect = StatFocusCost(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.focusCostDecreasedAfterRuneAttack.Description=[StatFocusCost] <b>Focus Cost</b> decreased by {0} for {1} seconds after Rune Attackm
  val focusCostDecreasedAfterRuneAttack = Enchantment(
    id = "focusCostDecreasedAfterRuneAttack",
    name = "Focus Cost Decreased After Rune Attack",
    textCode = "enchantments.focusCostDecreasedAfterRuneAttack",
    description = "[StatFocusCost] <b>Focus Cost</b> decreased by {0} for {1} seconds after Rune Attackm",
    statEffect = StatFocusCost(OperationSubtract(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionRuneAttack),
  )
  // enchantments.focusCostDecreasedAfterStagger.Description=[StatFocusCost] <b>Focus Cost</b> decreased by {0} for {1} seconds after Staggering an Enemy
  val focusCostDecreasedAfterStagger = Enchantment(
    id = "focusCostDecreasedAfterStagger",
    name = "Focus Cost Decreased After Stagger",
    textCode = "enchantments.focusCostDecreasedAfterStagger",
    description = "[StatFocusCost] <b>Focus Cost</b> decreased by {0} for {1} seconds after Staggering an Enemy",
    statEffect = StatFocusCost(OperationSubtract(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionStagger),
  )
  // enchantments.focusCostDecreasedAtLowHealth.Description=[StatFocusGain] <b>Focus Cost</b> decreased by {0} at Low Health
  val focusCostDecreasedAtLowHealth = Enchantment(
    id = "focusCostDecreasedAtLowHealth",
    name = "Focus Cost Decreased At Low Health",
    textCode = "enchantments.focusCostDecreasedAtLowHealth",
    description = "[StatFocusGain] <b>Focus Cost</b> decreased by {0} at Low Health",
    statEffect = StatFocusCost(OperationSubtract(ByValuePercentRange(10, 20)), condition = ConditionLowHealth),
  )
  // enchantments.focusCostIncreased.Description=[StatFocusGain] <b>Focus Cost</b> increased by {0}
  val focusCostIncreased = Enchantment(
    id = "focusCostIncreased",
    name = "Focus Cost Increased",
    textCode = "enchantments.focusCostIncreased",
    description = "[StatFocusGain] <b>Focus Cost</b> increased by {0}",
    statEffect = StatFocusCost(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.focusCostScalesWithMissingHealth.Description=[StatFocusGain] <b>Focus Cost</b> decreased by up to {0} based on Missing Health
  val focusCostScalesWithMissingHealth = Enchantment(
    id = "focusCostScalesWithMissingHealth",
    name = "Focus Cost Scales With Missing Health",
    textCode = "enchantments.focusCostScalesWithMissingHealth",
    description = "[StatFocusGain] <b>Focus Cost</b> decreased by up to {0} based on Missing Health",
    statEffect = StatFocusCost(OperationSubtract(ByValuePercentRangeOf(10, 20, StatHealthMissing))),
  )
  // enchantments.focusGainDecreased.Description=[StatFocusGain] <b>Focus Gain</b> decreased by {0}
  val focusGainDecreased = Enchantment(
    id = "focusGainDecreased",
    name = "Focus Gain Decreased",
    textCode = "enchantments.focusGainDecreased",
    description = "[StatFocusGain] <b>Focus Gain</b> decreased by {0}",
    statEffect = StatFocusGain(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.focusGainIncreased.Description=[StatFocusGain] <b>Focus Gain</b> increased by {0}
  val focusGainIncreased = Enchantment(
    id = "focusGainIncreased",
    name = "Focus Gain Increased",
    textCode = "enchantments.focusGainIncreased",
    description = "[StatFocusGain] <b>Focus Gain</b> increased by {0}",
    statEffect = StatFocusGain(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.focusGainScalesWithDurability.Description=[StatFocusGain] <b>Focus Gain</b> increased by up to {0} based on Durability
  val focusGainScalesWithDurability = Enchantment(
    id = "focusGainScalesWithDurability",
    name = "Focus Gain Scales With Durability",
    textCode = "enchantments.focusGainScalesWithDurability",
    description = "[StatFocusGain] <b>Focus Gain</b> increased by up to {0} based on Durability",
    statEffect = StatFocusGain(OperationAdd(ByValuePercentRangeOf(10, 20, StatDurability()))),
  )
  // enchantments.gainFocusOnBackstab.Description=Gain {0} [StatFocus] <b>Focus</b> on Backstab
  val gainFocusOnBackstab = Enchantment(
    id = "gainFocusOnBackstab",
    name = "Gain Focus On Backstab",
    textCode = "enchantments.gainFocusOnBackstab",
    description = "Gain {0} [StatFocus] <b>Focus</b> on Backstab",
    statEffect = StatFocusGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionBackstab),
  )
  // enchantments.gainFocusOnBuildup.Description=Gain {0} [StatFocus] <b>Focus</b> on inflicting Any Status
  val gainFocusOnBuildup = Enchantment(
    id = "gainFocusOnBuildup",
    name = "Gain Focus On Buildup",
    textCode = "enchantments.gainFocusOnBuildup",
    description = "Gain {0} [StatFocus] <b>Focus</b> on inflicting Any Status",
    statEffect = StatFocusGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionInflictingAnyStatus),
  )
  // enchantments.gainFocusOnDodged.Description=Gain {0} [StatFocus] <b>Focus</b> on Damage Dodged
  val gainFocusOnDodged = Enchantment(
    id = "gainFocusOnDodged",
    name = "Gain Focus On Dodged",
    textCode = "enchantments.gainFocusOnDodged",
    description = "Gain {0} [StatFocus] <b>Focus</b> on Damage Dodged",
    statEffect = StatFocusGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionDamageDodged),
  )
  // enchantments.gainFocusOnFatality.Description=Gain {0} [StatFocus] <b>Focus</b> on Fatality
  val gainFocusOnFatality = Enchantment(
    id = "gainFocusOnFatality",
    name = "Gain Focus On Fatality",
    textCode = "enchantments.gainFocusOnFatality",
    description = "Gain {0} [StatFocus] <b>Focus</b> on Fatality",
    statEffect = StatFocusGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionFatality),
  )
  // enchantments.gainFocusOnKill.Description=Gain {0} [StatFocus] <b>Focus</b> on Kill
  val gainFocusOnKill = Enchantment(
    id = "gainFocusOnKill",
    name = "Gain Focus On Kill",
    textCode = "enchantments.gainFocusOnKill",
    description = "Gain {0} [StatFocus] <b>Focus</b> on Kill",
    statEffect = StatFocusGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionKill),
  )
  // enchantments.gainFocusOnParry.Description=Gain {0} [StatFocus] <b>Focus</b> on Parry
  val gainFocusOnParry = Enchantment(
    id = "gainFocusOnParry",
    name = "Gain Focus On Parry",
    textCode = "enchantments.gainFocusOnParry",
    description = "Gain {0} [StatFocus] <b>Focus</b> on Parry",
    statEffect = StatFocusGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionParry),
  )
  // enchantments.gainFocusOnStagger.Description=Gain {0} [StatFocus] <b>Focus</b> on Staggering an Enemy
  val gainFocusOnStagger = Enchantment(
    id = "gainFocusOnStagger",
    name = "Gain Focus On Stagger",
    textCode = "enchantments.gainFocusOnStagger",
    description = "Gain {0} [StatFocus] <b>Focus</b> on Staggering an Enemy",
    statEffect = StatFocusGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionEnemyStaggered),
  )
  // enchantments.gainHealthForFocusSpent.Description=Gain [StatHealth] <b>Health</b> equal to {0} of Focus Spent
  val gainHealthForFocusSpent = Enchantment(
    id = "gainHealthForFocusSpent",
    name = "Gain Health For Focus Spent",
    textCode = "enchantments.gainHealthForFocusSpent",
    description = "Gain [StatHealth] <b>Health</b> equal to {0} of Focus Spent",
    statEffect = StatHealthGain(OperationAdd(ByValuePercentRangeOf(10, 20, StatFocusCost()))),
  )
  // enchantments.gainHealthOnBackstab.Description=Gain {0} [StatHealth] <b>Health</b> on Backstab
  val gainHealthOnBackstab = Enchantment(
    id = "gainHealthOnBackstab",
    name = "Gain Health On Backstab",
    textCode = "enchantments.gainHealthOnBackstab",
    description = "Gain {0} [StatHealth] <b>Health</b> on Backstab",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionBackstab),
  )
  // enchantments.gainHealthOnChargedAttack.Description=Gain {0} [StatHealth] <b>Health</b> on Charged Attack
  val gainHealthOnChargedAttack = Enchantment(
    id = "gainHealthOnChargedAttack",
    name = "Gain Health On Charged Attack",
    textCode = "enchantments.gainHealthOnChargedAttack",
    description = "Gain {0} [StatHealth] <b>Health</b> on Charged Attack",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionChargedAttack),
  )
  // enchantments.gainHealthOnDamageDealtAgainstFrozen.Description=Gain {0} [StatHealth] <b>Health</b> on Damage Dealt against [!sIce]Frozen[/s] Enemies
  val gainHealthOnDamageDealtAgainstFrozen = Enchantment(
    id = "gainHealthOnDamageDealtAgainstFrozen",
    name = "Gain Health On Damage Dealt Against Frozen",
    textCode = "enchantments.gainHealthOnDamageDealtAgainstFrozen",
    description = "Gain {0} [StatHealth] <b>Health</b> on Damage Dealt against [!sIce]Frozen[/s] Enemies",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionAnd(ConditionDamageDealt, ConditionEnemyFrozen)),
  )
  // enchantments.gainHealthOnDamageDealtAtLowHealth.Description=Gain {0} [StatHealth] <b>Health</b> on Damage Dealt at Low Health
  val gainHealthOnDamageDealtAtLowHealth = Enchantment(
    id = "gainHealthOnDamageDealtAtLowHealth",
    name = "Gain Health On Damage Dealt At Low Health",
    textCode = "enchantments.gainHealthOnDamageDealtAtLowHealth",
    description = "Gain {0} [StatHealth] <b>Health</b> on Damage Dealt at Low Health",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionAnd(ConditionDamageDealt, ConditionLowHealth)),
  )
  // enchantments.gainHealthOnDodged.Description=Gain {0} [StatHealth] <b>Health</b> on Damage Dodged
  val gainHealthOnDodged = Enchantment(
    id = "gainHealthOnDodged",
    name = "Gain Health On Dodged",
    textCode = "enchantments.gainHealthOnDodged",
    description = "Gain {0} [StatHealth] <b>Health</b> on Damage Dodged",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionDamageDodged),
  )
  // enchantments.gainHealthOnFatality.Description=Gain {0} [StatHealth] <b>Health</b> on Fatality
  val gainHealthOnFatality = Enchantment(
    id = "gainHealthOnFatality",
    name = "Gain Health On Fatality",
    textCode = "enchantments.gainHealthOnFatality",
    description = "Gain {0} [StatHealth] <b>Health</b> on Fatality",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionFatality),
  )
  // enchantments.gainHealthOnKill.Description=Gain {0} [StatHealth] <b>Health</b> on Kill
  val gainHealthOnKill = Enchantment(
    id = "gainHealthOnKill",
    name = "Gain Health On Kill",
    textCode = "enchantments.gainHealthOnKill",
    description = "Gain {0} [StatHealth] <b>Health</b> on Kill",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionKill),
  )
  // enchantments.gainHealthOnKillAgainstStaggeredEnemies.Description=Gain {0} [StatHealth] <b>Health</b> on Kill against Staggered Enemies
  val gainHealthOnKillAgainstStaggeredEnemies = Enchantment(
    id = "gainHealthOnKillAgainstStaggeredEnemies",
    name = "Gain Health On Kill Against Staggered Enemies",
    textCode = "enchantments.gainHealthOnKillAgainstStaggeredEnemies",
    description = "Gain {0} [StatHealth] <b>Health</b> on Kill against Staggered Enemies",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionAnd(ConditionKill, ConditionEnemyStaggered)),
  )
  // enchantments.gainHealthOnParry.Description=Gain {0} [StatHealth] <b>Health</b> on Parry
  val gainHealthOnParry = Enchantment(
    id = "gainHealthOnParry",
    name = "Gain Health On Parry",
    textCode = "enchantments.gainHealthOnParry",
    description = "Gain {0} [StatHealth] <b>Health</b> on Parry",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionParry),
  )
  // enchantments.gainHealthOnStagger.Description=Gain {0} [StatHealth] <b>Health</b> on Staggering an Enemy
  val gainHealthOnStagger = Enchantment(
    id = "gainHealthOnStagger",
    name = "Gain Health On Stagger",
    textCode = "enchantments.gainHealthOnStagger",
    description = "Gain {0} [StatHealth] <b>Health</b> on Staggering an Enemy",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionEnemyStaggered),
  )
  // enchantments.gainHealthOncePerRuneAttack.Description=Gain {0} [StatHealth] <b>Health</b> once per Rune Attack
  val gainHealthOncePerRuneAttack = Enchantment(
    id = "gainHealthOncePerRuneAttack",
    name = "Gain Health Once Per Rune Attack",
    textCode = "enchantments.gainHealthOncePerRuneAttack",
    description = "Gain {0} [StatHealth] <b>Health</b> once per Rune Attack",
    statEffect = StatHealthGain(OperationAdd(ByValueRange(10, 20)), condition = ConditionRuneAttack),
  )
  // enchantments.gainStaminaOnBlock.Description=Gain {0} [StatStamina] <b>Stamina</b> on Block
  val gainStaminaOnBlock = Enchantment(
    id = "gainStaminaOnBlock",
    name = "Gain Stamina On Block",
    textCode = "enchantments.gainStaminaOnBlock",
    description = "Gain {0} [StatStamina] <b>Stamina</b> on Block",
    statEffect = StatStamina(OperationAdd(ByValueRange(10, 20)), condition = ConditionBlock),
  )
  // enchantments.gainStaminaOnDamageDealt.Description=Gain {0} [StatStamina] <b>Stamina</b> on Damage Dealt
  val gainStaminaOnDamageDealt = Enchantment(
    id = "gainStaminaOnDamageDealt",
    name = "Gain Stamina On Damage Dealt",
    textCode = "enchantments.gainStaminaOnDamageDealt",
    description = "Gain {0} [StatStamina] <b>Stamina</b> on Damage Dealt",
    statEffect = StatStamina(OperationAdd(ByValueRange(10, 20)), condition = ConditionDamageDealt),
  )
  // enchantments.gainStaminaOnDodged.Description=Gain {0} [StatStamina] <b>Stamina</b> on Damage Dodged
  val gainStaminaOnDodged = Enchantment(
    id = "gainStaminaOnDodged",
    name = "Gain Stamina On Dodged",
    textCode = "enchantments.gainStaminaOnDodged",
    description = "Gain {0} [StatStamina] <b>Stamina</b> on Damage Dodged",
    statEffect = StatStamina(OperationAdd(ByValueRange(10, 20)), condition = ConditionDamageDodged),
  )
  // enchantments.gainStaminaOnFocusUse.Description=Gain {0} [StatStamina] <b>Stamina</b> on Focus Use
  val gainStaminaOnFocusUse = Enchantment(
    id = "gainStaminaOnFocusUse",
    name = "Gain Stamina On Focus Use",
    textCode = "enchantments.gainStaminaOnFocusUse",
    description = "Gain {0} [StatStamina] <b>Stamina</b> on Focus Use",
    statEffect = StatStamina(OperationAdd(ByValueRange(10, 20)), condition = ConditionFocusUse),
  )
  // enchantments.gainStaminaOnParry.Description=Gain {0} [StatStamina] <b>Stamina</b> on Parry
  val gainStaminaOnParry = Enchantment(
    id = "gainStaminaOnParry",
    name = "Gain Stamina On Parry",
    textCode = "enchantments.gainStaminaOnParry",
    description = "Gain {0} [StatStamina] <b>Stamina</b> on Parry",
    statEffect = StatStamina(OperationAdd(ByValueRange(10, 20)), condition = ConditionParry),
  )
  // enchantments.gainStaminaOnStagger.Description=Gain {0} [StatStamina] <b>Stamina</b> on Staggering an Enemy
  val gainStaminaOnStagger = Enchantment(
    id = "gainStaminaOnStagger",
    name = "Gain Stamina On Stagger",
    textCode = "enchantments.gainStaminaOnStagger",
    description = "Gain {0} [StatStamina] <b>Stamina</b> on Staggering an Enemy",
    statEffect = StatStamina(OperationAdd(ByValueRange(10, 20)), condition = ConditionStagger),
  )
  // enchantments.healingDecreased.Description=[StatHealing] <b>Healing</b> decreased by {0}
  val healingDecreased = Enchantment(
    id = "healingDecreased",
    name = "Healing Decreased",
    textCode = "enchantments.healingDecreased",
    description = "[StatHealing] <b>Healing</b> decreased by {0}",
    statEffect = StatHealing(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.healingDecreasedPerNearbyEnemy.Description=[StatHealing] <b>Healing</b> decreased by {0} for each Nearby Enemy
  val healingDecreasedPerNearbyEnemy = Enchantment(
    id = "healingDecreasedPerNearbyEnemy",
    name = "Healing Decreased Per Nearby Enemy",
    textCode = "enchantments.healingDecreasedPerNearbyEnemy",
    description = "[StatHealing] <b>Healing</b> decreased by {0} for each Nearby Enemy",
    statEffect = StatHealing(OperationSubtract(ByValuePercentRange(3, 7)), condition = ConditionEnemyNearby),
  )
  // enchantments.healingIncreased.Description=[StatHealing] <b>Healing</b> increased by {0}
  val healingIncreased = Enchantment(
    id = "healingIncreased",
    name = "Healing Increased",
    textCode = "enchantments.healingIncreased",
    description = "[StatHealing] <b>Healing</b> increased by {0}",
    statEffect = StatHealing(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.healingIncreasedAtLowFocus.Description=[StatHealing] <b>Healing</b> increased by {0} at Low Focus
  val healingIncreasedAtLowFocus = Enchantment(
    id = "healingIncreasedAtLowFocus",
    name = "Healing Increased At Low Focus",
    textCode = "enchantments.healingIncreasedAtLowFocus",
    description = "[StatHealing] <b>Healing</b> increased by {0} at Low Focus",
    statEffect = StatHealing(OperationAdd(ByValuePercentRange(10, 20)), condition = ConditionLowFocus),
  )
  // enchantments.healthRallyIncreased.Description=[StatHealthRally] <b>Regainable Health</b> increased by {0}
  val healthRallyIncreased = Enchantment(
    id = "healthRallyIncreased",
    name = "Health Rally Increased",
    textCode = "enchantments.healthRallyIncreased",
    description = "[StatHealthRally] <b>Regainable Health</b> increased by {0}",
    statEffect = StatHealthRally(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.heatDamageIncreasedAfterColdDamageDealt.Description=[StatFireDamage] [!sFire]Fire Damage[/s] increased by {0} for {1} seconds after [!sIce]Ice Damage[/s] Dealt
  val heatDamageIncreasedAfterColdDamageDealt = Enchantment(
    id = "heatDamageIncreasedAfterColdDamageDealt",
    name = "Heat Damage Increased After Cold Damage Dealt",
    textCode = "enchantments.heatDamageIncreasedAfterColdDamageDealt",
    description = "[StatFireDamage] [!sFire]Fire Damage[/s] increased by {0} for {1} seconds after [!sIce]Ice Damage[/s] Dealt",
    statEffect = StatFireDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionColdDamageDealt),
  )
  // enchantments.hungerIncreased.Description=<b>Hunger Limit</b> increased by {0}
  val hungerIncreased = Enchantment(
    id = "hungerIncreased",
    name = "Hunger Increased",
    textCode = "enchantments.hungerIncreased",
    description = "<b>Hunger Limit</b> increased by {0}",
    statEffect = StatHunger(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.indestructible.Description=<b>Indestructible</b>
  val indestructible = Enchantment(
    id = "indestructible",
    name = "Indestructible",
    textCode = "enchantments.indestructible",
    description = "<b>Indestructible</b>",
    statEffect = StatDurabilityLoss(OperationIgnoredChance(ByValuePercent(100))),
  )
  // enchantments.infectionBuildupIncreased.Description=[StatPlagueDamage] [!sPlague]Infection Buildup[/s] increased by {0}
  val infectionBuildupIncreased = Enchantment(
    id = "infectionBuildupIncreased",
    name = "Infection Buildup Increased",
    textCode = "enchantments.infectionBuildupIncreased",
    description = "[StatPlagueDamage] [!sPlague]Infection Buildup[/s] increased by {0}",
    statEffect = StatInfectionBuildup(OperationAdd(ByValuePercentRange(10, 25))),
  )
  // enchantments.itemWeightDecreased.Description=Item [StatWeight] <b>Weight</b> decreased by {0}
  val itemWeightDecreased = Enchantment(
    id = "itemWeightDecreased",
    name = "Item Weight Decreased",
    textCode = "enchantments.itemWeightDecreased",
    description = "Item [StatWeight] <b>Weight</b> decreased by {0}",
    statEffect = StatWeight(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.itemWeightIncreased.Description=Item [StatWeight] <b>Weight</b> increased by {0}
  val itemWeightIncreased = Enchantment(
    id = "itemWeightIncreased",
    name = "Item Weight Increased",
    textCode = "enchantments.itemWeightIncreased",
    description = "Item [StatWeight] <b>Weight</b> increased by {0}",
    statEffect = StatWeight(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.knockbackResistanceIncreased.Description=<b>Knockback Resistance</b> increased by {0}
  val knockbackResistanceIncreased = Enchantment(
    id = "knockbackResistanceIncreased",
    name = "Knockback Resistance Increased",
    textCode = "enchantments.knockbackResistanceIncreased",
    description = "<b>Knockback Resistance</b> increased by {0}",
    statEffect = StatKnockbackResistance(OperationAdd(ByValuePercentRange(10, 25))),
  )
  // enchantments.lifestealDisabled.Description=Cannot [StatLifesteal] <b>Lifesteal</b>
  val lifestealDisabled = Enchantment(
    id = "lifestealDisabled",
    name = "Lifesteal Disabled",
    textCode = "enchantments.lifestealDisabled",
    description = "Cannot [StatLifesteal] <b>Lifesteal</b>",
    statEffect = StatLifesteal(OperationSubtract(ByValuePercent(100))),
  )
  // enchantments.lifestealIncreased.Description=[StatLifesteal] <b>Lifesteal</b> increased by {0}
  val lifestealIncreased = Enchantment(
    id = "lifestealIncreased",
    name = "Lifesteal Increased",
    textCode = "enchantments.lifestealIncreased",
    description = "[StatLifesteal] <b>Lifesteal</b> increased by {0}",
    statEffect = StatLifesteal(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.lifestealIncreasedAtLowHealth.Description=[StatLifesteal] <b>Lifesteal</b> increased by {0} at Low Health
  val lifestealIncreasedAtLowHealth = Enchantment(
    id = "lifestealIncreasedAtLowHealth",
    name = "Lifesteal Increased At Low Health",
    textCode = "enchantments.lifestealIncreasedAtLowHealth",
    description = "[StatLifesteal] <b>Lifesteal</b> increased by {0} at Low Health",
    statEffect = StatLifesteal(OperationAdd(ByValuePercentRange(10, 20)), condition = ConditionLowHealth),
  )
  // enchantments.lightningDamageIncreased.Description=[StatLightningDamage] [!sLightning]Lightning Damage[/s] increased by {0}
  val lightningDamageIncreased = Enchantment(
    id = "lightningDamageIncreased",
    name = "Lightning Damage Increased",
    textCode = "enchantments.lightningDamageIncreased",
    description = "[StatLightningDamage] [!sLightning]Lightning Damage[/s] increased by {0}",
    statEffect = StatLightningDamage(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.lightningDamageOverride.Description=[StatLightningDamage] [!sLightning]Lightning Damage[/s] Infusion
  val lightningDamageOverride = Enchantment(
    id = "lightningDamageOverride",
    name = "Lightning Damage Override",
    textCode = "enchantments.lightningDamageOverride",
    description = "[StatLightningDamage] [!sLightning]Lightning Damage[/s] Infusion",
    statEffect = StatLightningDamageOverride(),
  )
  // enchantments.lightningDamageOverrideAfterSprint.Description=[StatLightningDamage] [!sLightning]Lightning Damage[/s] Infusion after Sprinting for {0} seconds
  val lightningDamageOverrideAfterSprint = Enchantment(
    id = "lightningDamageOverrideAfterSprint",
    name = "Lightning Damage Override After Sprint",
    textCode = "enchantments.lightningDamageOverrideAfterSprint",
    description = "[StatLightningDamage] [!sLightning]Lightning Damage[/s] Infusion after Sprinting for {0} seconds",
    statEffect = StatLightningDamageOverride(duration = DurationSeconds(5), condition = ConditionSprint),
  )
  // enchantments.lightningResistanceIncreased.Description=[StatLightningArmor] <b>Lightning Resistance</b> increased by {0}
  val lightningResistanceIncreased = Enchantment(
    id = "lightningResistanceIncreased",
    name = "Lightning Resistance Increased",
    textCode = "enchantments.lightningResistanceIncreased",
    description = "[StatLightningArmor] <b>Lightning Resistance</b> increased by {0}",
    statEffect = StatLightningArmor(OperationAdd(ByValuePercentRange(10, 25))),
  )
  // enchantments.loseFocusOnDamageTaken.Description=Lose {0} [StatFocus] <b>Focus</b> on Damage Taken
  val loseFocusOnDamageTaken = Enchantment(
    id = "loseFocusOnDamageTaken",
    name = "Lose Focus On Damage Taken",
    textCode = "enchantments.loseFocusOnDamageTaken",
    description = "Lose {0} [StatFocus] <b>Focus</b> on Damage Taken",
    statEffect = StatFocus(OperationSubtract(ByValueRange(10, 20)), condition = ConditionDamageTaken),
  )
  // enchantments.loseFocusOnFocusUse.Description=Lose {0} [StatFocus] <b>Focus</b> on Focus Use
  val loseFocusOnFocusUse = Enchantment(
    id = "loseFocusOnFocusUse",
    name = "Lose Focus On Focus Use",
    textCode = "enchantments.loseFocusOnFocusUse",
    description = "Lose {0} [StatFocus] <b>Focus</b> on Focus Use",
    statEffect = StatFocus(OperationSubtract(ByValueRange(10, 20)), condition = ConditionFocusUse),
  )
  // enchantments.loseStaminaOnBlock.Description=Lose {0} [StatStamina] <b>Stamina</b> on Block
  val loseStaminaOnBlock = Enchantment(
    id = "loseStaminaOnBlock",
    name = "Lose Stamina On Block",
    textCode = "enchantments.loseStaminaOnBlock",
    description = "Lose {0} [StatStamina] <b>Stamina</b> on Block",
    statEffect = StatStamina(OperationSubtract(ByValueRange(10, 20)), condition = ConditionBlock),
  )
  // enchantments.loseStaminaOnDamageTaken.Description=Lose {0} [StatStamina] <b>Stamina</b> on Damage Taken
  val loseStaminaOnDamageTaken = Enchantment(
    id = "loseStaminaOnDamageTaken",
    name = "Lose Stamina On Damage Taken",
    textCode = "enchantments.loseStaminaOnDamageTaken",
    description = "Lose {0} [StatStamina] <b>Stamina</b> on Damage Taken",
    statEffect = StatStamina(OperationSubtract(ByValueRange(10, 20)), condition = ConditionDamageTaken),
  )
  // enchantments.maxFocusDecreased.Description=[StatMaxFocus] <b>Max Focus</b> decreased by {0}
  val maxFocusDecreased = Enchantment(
    id = "maxFocusDecreased",
    name = "Max Focus Decreased",
    textCode = "enchantments.maxFocusDecreased",
    description = "[StatMaxFocus] <b>Max Focus</b> decreased by {0}",
    statEffect = StatMaxFocus(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.maxFocusIncreased.Description=[StatMaxFocus] <b>Max Focus</b> increased by {0}
  val maxFocusIncreased = Enchantment(
    id = "maxFocusIncreased",
    name = "Max Focus Increased",
    textCode = "enchantments.maxFocusIncreased",
    description = "[StatMaxFocus] <b>Max Focus</b> increased by {0}",
    statEffect = StatMaxFocus(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.maxStaminaDecreased.Description=[StatMaxStamina] <b>Max Stamina</b> decreased by {0}
  val maxStaminaDecreased = Enchantment(
    id = "maxStaminaDecreased",
    name = "Max Stamina Decreased",
    textCode = "enchantments.maxStaminaDecreased",
    description = "[StatMaxStamina] <b>Max Stamina</b> decreased by {0}",
    statEffect = StatMaxStamina(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.maxStaminaScalesWithMaxFocus.Description=[StatMaxStamina] <b>Max Stamina</b> increased by {0}% of Max Focus
  val maxStaminaScalesWithMaxFocus = Enchantment(
    id = "maxStaminaScalesWithMaxFocus",
    name = "Max Stamina Scales With Max Focus",
    textCode = "enchantments.maxStaminaScalesWithMaxFocus",
    description = "[StatMaxStamina] <b>Max Stamina</b> increased by {0}% of Max Focus",
    statEffect = StatMaxStamina(OperationAdd(ByValuePercentRangeOf(10, 20, StatMaxFocus()))),
  )
  // enchantments.movementSpeedDecreased.Description=[StatMovementSpeed] <b>Movement Speed</b> decreased by {0}
  val movementSpeedDecreased = Enchantment(
    id = "movementSpeedDecreased",
    name = "Movement Speed Decreased",
    textCode = "enchantments.movementSpeedDecreased",
    description = "[StatMovementSpeed] <b>Movement Speed</b> decreased by {0}",
    statEffect = StatMovementSpeed(OperationSubtract(ByValuePercentRange(5, 10))),
  )
  // enchantments.movementSpeedDecreasedPerNearbyEnemy.Description=[StatMovementSpeed] <b>Movement Speed</b> decreased by {0} per Nearby Enemy
  val movementSpeedDecreasedPerNearbyEnemy = Enchantment(
    id = "movementSpeedDecreasedPerNearbyEnemy",
    name = "Movement Speed Decreased Per Nearby Enemy",
    textCode = "enchantments.movementSpeedDecreasedPerNearbyEnemy",
    description = "[StatMovementSpeed] <b>Movement Speed</b> decreased by {0} per Nearby Enemy",
    statEffect = StatMovementSpeed(OperationSubtract(ByValuePercentRange(2, 5)), condition = ConditionEnemyNearby),
  )
  // enchantments.movementSpeedIncreased.Description=[StatMovementSpeed] <b>Movement Speed</b> increased by {0}
  val movementSpeedIncreased = Enchantment(
    id = "movementSpeedIncreased",
    name = "Movement Speed Increased",
    textCode = "enchantments.movementSpeedIncreased",
    description = "[StatMovementSpeed] <b>Movement Speed</b> increased by {0}",
    statEffect = StatMovementSpeed(OperationAdd(ByValuePercentRange(5, 10))),
  )
  // enchantments.movementSpeedIncreasedAfterChargedAttack.Description=[StatMovementSpeed] <b>Movement Speed</b> increased by {0} for {1} seconds after Charged Attack
  val movementSpeedIncreasedAfterChargedAttack = Enchantment(
    id = "movementSpeedIncreasedAfterChargedAttack",
    name = "Movement Speed Increased After Charged Attack",
    textCode = "enchantments.movementSpeedIncreasedAfterChargedAttack",
    description = "[StatMovementSpeed] <b>Movement Speed</b> increased by {0} for {1} seconds after Charged Attack",
    statEffect = StatMovementSpeed(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionChargedAttack),
  )
  // enchantments.movementSpeedIncreasedAfterKill.Description=[StatMovementSpeed] <b>Movement Speed</b> increased by {0} for {1} seconds after Kill
  val movementSpeedIncreasedAfterKill = Enchantment(
    id = "movementSpeedIncreasedAfterKill",
    name = "Movement Speed Increased After Kill",
    textCode = "enchantments.movementSpeedIncreasedAfterKill",
    description = "[StatMovementSpeed] <b>Movement Speed</b> increased by {0} for {1} seconds after Kill",
    statEffect = StatMovementSpeed(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(10), ConditionKill),
  )
  // enchantments.normalDamageIncreasedAfterChargedAttack.Description=[StatDamage] <b>Normal Attack Damage</b> increased by {0} for {1} seconds after Charged Attack
  val normalDamageIncreasedAfterChargedAttack = Enchantment(
    id = "normalDamageIncreasedAfterChargedAttack",
    name = "Normal Damage Increased After Charged Attack",
    textCode = "enchantments.normalDamageIncreasedAfterChargedAttack",
    description = "[StatDamage] <b>Normal Attack Damage</b> increased by {0} for {1} seconds after Charged Attack",
    statEffect = StatNormalAttackDamage(OperationAdd(ByValuePercentRange(10, 20)), DurationSeconds(5), ConditionChargedAttack),
  )
  // enchantments.onlyHeavyRoll.Description=<b>Heavy Roll</b> only
  val onlyHeavyRoll = Enchantment(
    id = "onlyHeavyRoll",
    name = "Only Heavy Roll",
    textCode = "enchantments.onlyHeavyRoll",
    description = "<b>Heavy Roll</b> only",
    statEffect = StatHeavyRollOnly,
  )
  // enchantments.parryDisabled.Description=<b>Parry</b> disabled
  val parryDisabled = Enchantment(
    id = "parryDisabled",
    name = "Parry Disabled",
    textCode = "enchantments.parryDisabled",
    description = "<b>Parry</b> disabled",
    statEffect = StatParryDisabled,
  )
  // enchantments.parryStaminaCostDecreased.Description=[StatStaminaCost] <b>Parry Stamina Cost</b> decreased by {0}
  val parryStaminaCostDecreased = Enchantment(
    id = "parryStaminaCostDecreased",
    name = "Parry Stamina Cost Decreased",
    textCode = "enchantments.parryStaminaCostDecreased",
    description = "[StatStaminaCost] <b>Parry Stamina Cost</b> decreased by {0}",
    statEffect = StatParryStaminaCost(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.physicalDamageIncreased.Description=[StatPhysicalDamage] [!sPhysical]Physical Damage[/s] increased by {0}
  val physicalDamageIncreased = Enchantment(
    id = "physicalDamageIncreased",
    name = "Physical Damage Increased",
    textCode = "enchantments.physicalDamageIncreased",
    description = "[StatPhysicalDamage] [!sPhysical]Physical Damage[/s] increased by {0}",
    statEffect = StatPhysicalDamage(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.physicalDamageTakenDecreased.Description=[StatDamageTaken] <b>Physical Damage Taken</b> decreased by {0}
  val physicalDamageTakenDecreased = Enchantment(
    id = "physicalDamageTakenDecreased",
    name = "Physical Damage Taken Decreased",
    textCode = "enchantments.physicalDamageTakenDecreased",
    description = "[StatDamageTaken] <b>Physical Damage Taken</b> decreased by {0}",
    statEffect = StatPhysicalDamageTaken(OperationSubtract(ByValuePercentRange(10, 20))),
  )
  // enchantments.plagueDamageIncreased.Description=[StatPlagueDamage] [!sPlague]Plague Damage[/s] increased by {0}
  val plagueDamageIncreased = Enchantment(
    id = "plagueDamageIncreased",
    name = "Plague Damage Increased",
    textCode = "enchantments.plagueDamageIncreased",
    description = "[StatPlagueDamage] [!sPlague]Plague Damage[/s] increased by {0}",
    statEffect = StatPlagueDamage(OperationAdd(ByValuePercentRange(10, 20))),
  )
  // enchantments.plagueDamageOverride.Description=[StatPlagueDamage] [!sPlague]Plague Damage[/s] Infusion
  val plagueDamageOverride = Enchantment(
    id = "plagueDamageOverride",
    name = "Plague Damage Override",
    textCode = "enchantments.plagueDamageOverride",
    description = "[StatPlagueDamage] [!sPlague]Plague Damage[/s] Infusion",
    statEffect = StatPlagueDamageOverride,
  )
  // enchantments.plagueResistanceIncreased.Description=[StatPlagueArmor] <b>Plague Resistance</b> increased by {0}
  val plagueResistanceIncreased = Enchantment(
    id = "plagueResistanceIncreased",
    name = "Plague Resistance Increased",
    textCode = "enchantments.plagueResistanceIncreased",
    description = "[StatPlagueArmor] <b>Plague Resistance</b> increased by {0}",
    statEffect = StatPlagueArmor(OperationAdd(ByValuePercentRange(10, 25))),
  )
}

package com.tewe.nrftw

import com.tewe.nrftw.EnchantGroup

object EnchantmentData {

// format: off

  enum StatType:
    case Armor,
      Damage,
      ChargedAttackDamage,
      AttackSpeed,
      StaminaCost,
      DodgeStaminaCost,
      Focus,
      CurrentFocus,
      Health,
      MissingHealth,
      Stamina,
      MovementSpeed
    case MaxFocus,
      MaxHealth,
      MaxStamina,
      FocusGain,
      FocusCost,
      FocusSpent, // TODO another type?
      HealthRegen,
      StaminaRegen,
      FocusRegen
    case Lifesteal,
      Healing,
      DamageTaken,
      ElementalDamageTaken,
      Durability,
      Weight,
      EquipLoad
    case FireDamage,
      ColdDamage,
      LightningDamage,
      PlagueDamage,
      PhysicalDamage,
      BurnBuildup,
      FrostBuildup,
      InfectionBuildup,
      ShockBuildup
    case FireArmor,
      ColdArmor,
      LightningArmor,
      PlagueArmor
    case StaggerDamage,
      StaggerResistance,
      PoiseDefense,
      ExperienceGain,
      BombDamage
    case HealthRally,
      BuildupResistance
    case EnemyArmor,
      EnemyStaggerResistance,
      HungerLimit,
      Enlightment

  enum ElementType:
    case Fire, Ice, Lightning, Plague, Physical

  enum StatusEffect:
    case Frozen, Infected, Shocked, Burned, Tainted, Enlightened, FirstToll, SecondToll, ThirdToll

  enum EnemyType:
    case Large, LowHealth, Staggered, Beasts, Nearby, Frozen, Infected

  enum ActionType:
    case RuneAttack, Kill, Parry, Backstab, Block, DamageDealt, DamageTaken, Afflicted, Dodged, Fatality
    case Stagger, Sprint, ChargedAttack, FocusUse, ConsumeFood, NormalAttack, DurabilityLoss, BombConsumed, Explosion
    case FireDamage, LightningDamage, IceDamage, Damage, Execute, DamageDodged

  enum ConditionType:
    case FullFocus, HighFocus, LowFocus, FullHealth, LowHealth, FullStamina, LowStamina
    case NotFullFocus, Blocking, Charging, InCombat, Sprinting, NoEnemiesNearby

  enum ValueType:
    case Percentage, Flat, Duration, Chance

  sealed trait Condition
  case object NoCondition extends Condition
  case class ActionCondition(action: ActionType) extends Condition
  case class StateCondition(state: ConditionType, forDuration: Option[EffectValue] = None) extends Condition
  case class EnemyCondition(enemyType: EnemyType) extends Condition
  case class StatusEffectCondition(effect: StatusEffect) extends Condition
  case class ElementCondition(elementType: ElementType) extends Condition
  case class AndCondition(left: Condition, right: Condition) extends Condition
  case class OrCondition(left: Condition, right: Condition) extends Condition

  sealed trait EffectValue
  case class ValueRange(min: Double, max: Double, valueType: ValueType)
      extends EffectValue
  case class ScalingValue(value: ValueRange, scalingFactor: StatType)
      extends EffectValue

  enum ModificationType:
    case Increase,
      Decrease,
      Gain,
      Lose,
      Disable,
      Refill,
      Drain,
      Infusion,
      Swap,
      SpendInstead,
      Ignore

  // TODO chance could be a Condition and use normal ValueRange!!!!
  sealed trait EnchantmentEffect
  case class StatModifier(
    stat: StatType, value: EffectValue, modificationType: ModificationType, condition: Condition = NoCondition, duration: Option[ValueRange] = None) extends EnchantmentEffect
  case class ActionTrigger(
    action: ActionType, value: EffectValue, condition: Condition, chance: Option[ValueRange] = None) extends EnchantmentEffect
  case class ActionModifier(
    action: ActionType, modificationType: ModificationType, chance: ValueRange) extends EnchantmentEffect
  case class ElementalDamage(
    element: ElementType, value: EffectValue, condition: Condition) extends EnchantmentEffect
  case class ElementalInfusion(
    element: ElementType, condition: Condition = NoCondition) extends EnchantmentEffect
  case class StatusInfliction(
    effect: StatusEffect, duration: Option[ValueRange] = None) extends EnchantmentEffect
  case class ResourceGain(
    resource: StatType, value: EffectValue, condition: Condition) extends EnchantmentEffect
  case class ResourceLoss(
    resource: StatType, value: EffectValue, condition: Condition) extends EnchantmentEffect
  case class ChanceEffect(
    chance: ValueRange, effect: EnchantmentEffect) extends EnchantmentEffect
  case class ExecuteEffect(
    condition: Condition) extends EnchantmentEffect
  case class SwapStats(
    stat1: StatType, stat2: StatType) extends EnchantmentEffect

  sealed trait SpecialEffect extends EnchantmentEffect
  case object EndlessClimbing extends SpecialEffect
  case object BlockingDisabled extends SpecialEffect
  case object ParryDisabled extends SpecialEffect
  case object OnlyHeavyRoll extends SpecialEffect
  case object Indestructible extends SpecialEffect
  case object Unrepairable extends SpecialEffect
  case object LifestealDisabled extends SpecialEffect
  case object DelayedHealing extends SpecialEffect
  case object DrainFocusInCombat extends SpecialEffect
  case object DrainHealthInCombat extends SpecialEffect
  case object RegenFocusInCombat extends SpecialEffect
  case object RegenHealthInCombat extends SpecialEffect
  case object SpendHealthIfNoFocus extends SpecialEffect
  case object SpendHealthInsteadOfFocus extends SpecialEffect

  case class Enchantment(
    id: String,
    group: EnchantGroup,
    textCode: String,
    rawText: String,
    effects: List[EnchantmentEffect],
  ) {
    val value = rawText
  }

  val armorDecreased = Enchantment("down_ad", EnchantGroup.Downside, "enchantment.armorDecreased",
    "[StatArmor] <b>Armor</b> decreased by {0}",
    List(StatModifier(StatType.Armor, ValueRange(20, 30, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val armorIncreased = Enchantment("d_ai", EnchantGroup.Defense, "enchantment.armorIncreased",
    "[StatArmor] <b>Armor</b> increased by {0}",
    List(StatModifier(StatType.Armor, ValueRange(10, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val attackDamageDecreased = Enchantment("", EnchantGroup.Other, "enchantment.attackDamageDecreased",
    "[StatDamage] <b>Attack Damage</b> decreased by {0}",
    List(StatModifier(StatType.Damage, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val attackDamageIncreased = Enchantment("", EnchantGroup.Other, "enchantment.attackDamageIncreased",
    "[StatDamage] <b>Attack Damage</b> increased by {0}",
    List(StatModifier(StatType.Damage, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
  )
  
  val attackDamageIncreasedAfterRuneAttack = Enchantment("a_adiara", EnchantGroup.Attack, "enchantment.attackDamageIncreasedAfterRuneAttack",
    "[StatDamage] <b>Attack Damage</b> increased by {0} for {1} seconds after Rune Attack",
    List(StatModifier(StatType.Damage, ValueRange(6, 20, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.RuneAttack), duration = Some(ValueRange(5, 5, ValueType.Duration))))
  )
  
  val attackDamageIncreasedAfterRuneAttackPlagued = Enchantment("a_adira", EnchantGroup.Attack, "enchantment.attackDamageIncreasedAfterRuneAttack",
    "[StatDamage] <b>Attack Damage</b> increased by {0} for {1} seconds after Rune Attack",
    List(StatModifier(StatType.Damage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.RuneAttack), duration = Some(ValueRange(6, 6, ValueType.Duration))))
  )
  
  val attackSpeedIncreased = Enchantment("", EnchantGroup.Other, "enchantment.attackSpeedIncreased",
    "[StatAttackSpeed] <b>Attack Speed</b> increased by {0}",
    List(StatModifier(StatType.AttackSpeed, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
  )
  
  val attackSpeedIncreasedAfterKill = Enchantment("", EnchantGroup.Other, "enchantment.attackSpeedIncreasedAfterKill",
    "[StatAttackSpeed] <b>Attack Speed</b> increased by {1} for <b>{0} Seconds</b> after Kill",
    List(StatModifier(StatType.AttackSpeed, ValueRange(1, 100, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Kill), Some(ValueRange(1, 10, ValueType.Percentage))))
  )
  
  val attackSpeedScalesWithDurability = Enchantment("a_asswd", EnchantGroup.Attack, "enchantment.attackSpeedScalesWithDurability",
    "[StatAttackSpeed] <b>Attack Speed</b> increased by up to {0} based on Durability",
    List(StatModifier(StatType.AttackSpeed, ScalingValue(ValueRange(3, 10, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
  )
  
  val attackSpeedScalesWithDurabilityPlagued = Enchantment("a_asid", EnchantGroup.Attack, "enchantment.attackSpeedScalesWithDurability",
    "[StatAttackSpeed] <b>Attack Speed</b> increased by up to {0} based on Durability",
    List(StatModifier(StatType.AttackSpeed, ScalingValue(ValueRange(7, 15, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
  )
  
  val attackStaminaCostDecreased = Enchantment("a_ascd", EnchantGroup.Attack, "enchantment.attackStaminaCostDecreased",
    "[StatStaminaCost] <b>Attack Stamina Cost</b> decreased by {0}",
    List(StatModifier(StatType.StaminaCost, ValueRange(7, 15, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val attackStaminaCostDecreasedAfterParry = Enchantment("s_ascdp", EnchantGroup.Stamina, "enchantment.attackStaminaCostDecreasedAfterParry",
    "[StatStaminaCost] <b>Attack Stamina Cost</b> decreased by {0} for {1} seconds after Parry",
    List(StatModifier(StatType.StaminaCost, ValueRange(7, 25, ValueType.Percentage), ModificationType.Decrease, ActionCondition(ActionType.Parry), Some(ValueRange(5, 5, ValueType.Duration))))
  )
  
  val attackStaminaCostDecreasedAfterParryPlagued = Enchantment("s_ascdp", EnchantGroup.Stamina, "enchantment.attackStaminaCostDecreasedAfterParry",
    "[StatStaminaCost] <b>Attack Stamina Cost</b> decreased by {0} for {1} seconds after Parry",
    List(StatModifier(StatType.StaminaCost, ValueRange(15, 30, ValueType.Percentage), ModificationType.Decrease, ActionCondition(ActionType.Parry), Some(ValueRange(6, 6, ValueType.Duration))))
  )
  
  val attackStaminaCostIncreased = Enchantment("down_asci", EnchantGroup.Downside, "enchantment.attackStaminaCostIncreased",
    "[StatStaminaCost] <b>Attack Stamina Cost</b> increased by {0}",
    List(StatModifier(StatType.StaminaCost, ValueRange(20, 30, ValueType.Percentage), ModificationType.Increase))
  )
  
  val blockingDisabled = Enchantment("down_bd", EnchantGroup.Downside, "enchantment.blockingDisabled",
    "<b>Block</b> disabled",
    List(BlockingDisabled)
  )
  
  val bombDamageIncreased = Enchantment("o_bdi", EnchantGroup.Other, "enchantment.bombDamageIncreased",
    "<b>Bomb Damage</b> increased by {0}",
    List(StatModifier(StatType.BombDamage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase))
  )
  
  val buildupResistance = Enchantment("", EnchantGroup.Other, "enchantment.buildupResistance",
    "[StatElementalDamageTaken] <b>Buildup Resistance</b> increased by {0}",
    List(StatModifier(StatType.BuildupResistance, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
  )
  
  val burnBuildupIncreased = Enchantment("a_bbi", EnchantGroup.Attack, "enchantment.burnBuildupIncreased",
    "[StatFireDamage] [!sFire]Burn Buildup[/s] increased by {0}",
    List(StatModifier(StatType.BurnBuildup, ValueRange(6, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val burnBuildupIncreasedPlagued = Enchantment("a_bbi", EnchantGroup.Attack, "enchantment.burnBuildupIncreased",
    "[StatFireDamage] [!sFire]Burn Buildup[/s] increased by {0}",
    List(StatModifier(StatType.BurnBuildup, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase))
  )
  
  val burnDamageIncreased = Enchantment("a_bdi", EnchantGroup.Attack, "enchantment.burnDamageIncreased",
    "[StatFireDamage] [!sFire]Burn Damage[/s] increased by {0}",
    List(StatModifier(StatType.FireDamage, ValueRange(25, 50, ValueType.Percentage), ModificationType.Increase))
  )
  
  val chanceToIngoreDurabilityLoss = Enchantment("u_ctidl", EnchantGroup.Durability, "enchantment.chanceToIngoreDurabilityLoss",
    "[StatDurability] <b>Durability Loss</b> ignored with {0} chance",
    List(ActionModifier(ActionType.DurabilityLoss, ModificationType.Ignore, ValueRange(6, 20, ValueType.Percentage)))
  )
  
  val chanceToNotConsumeBomb = Enchantment("o_bnc", EnchantGroup.Other, "enchantment.chanceToNotConsumeBomb",
    "<b>Bomb not consumed</b> with {0} chance",
    List(ActionModifier(ActionType.BombConsumed, ModificationType.Ignore, ValueRange(10, 20, ValueType.Percentage)))
  )
  
  val chanceToSpawnExplosionOnRuneAttack = Enchantment("a_ctseora", EnchantGroup.Attack, "enchantment.chanceToSpawnExplosionOnRuneAttack",
    "Deal {0} Explosion [StatFireDamage] [!sFire]Damage[/s] on Rune Attack with {1} chance",
    List(ActionTrigger(ActionType.Explosion, ValueRange(15, 50, ValueType.Flat), ActionCondition(ActionType.RuneAttack), Some(ValueRange(10, 10, ValueType.Percentage))))
  )
  
  val chargedDamageIncreased = Enchantment("a_cadi", EnchantGroup.Attack, "enchantment.chargedDamageIncreased",
    "[StatDamage] <b>Charged Attack Damage</b> increased by {0}",
    List(StatModifier(StatType.ChargedAttackDamage, ValueRange(10, 20, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ChargedAttack)))
  )
  
  val coldDamageIncreased = Enchantment("a_hme3", EnchantGroup.Attack, "enchantment.coldDamageIncreased",
    "[StatColdDamage] [!sIce]Ice Damage[/s] increased by {0}",
    List(StatModifier(StatType.ColdDamage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase))
  )
  
  val coldDamageIncreasedPlagued = Enchantment("a_cdi", EnchantGroup.Attack, "enchantment.coldDamageIncreased",
    "[StatColdDamage] [!sIce]Ice Damage[/s] increased by {0}",
    List(StatModifier(StatType.ColdDamage, ValueRange(7, 25, ValueType.Percentage), ModificationType.Increase))
  )
  
  val coldDamageIncreasedAfterHeatDamageDealt = Enchantment("", EnchantGroup.Other, "enchantment.coldDamageIncreasedAfterHeatDamageDealt",
    "[StatColdDamage] [!sIce]Ice Damage[/s] increased by {0} for {1} seconds after [!sFire]Fire Damage[/s] Dealt",
    List(StatModifier(StatType.ColdDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, AndCondition(ActionCondition(ActionType.DamageDealt), ElementCondition(ElementType.Fire)), Some(ValueRange(1, 30, ValueType.Duration))))
  )
  
  val coldDamageOverride = Enchantment("n_cdo", EnchantGroup.Infusion, "enchantment.coldDamageOverride",
    "[StatColdDamage] [!sIce]Ice Damage[/s] Infusion",
    List(ElementalInfusion(ElementType.Ice))
  )
  
  val coldResistanceIncreased = Enchantment("r_cri", EnchantGroup.Resistance, "enchantment.coldResistanceIncreased",
    "[StatColdArmor] <b>Ice Resistance</b> increased by {0}",
    List(StatModifier(StatType.ColdArmor, ValueRange(10, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val damageDecreasedAfterParryOrDodged = Enchantment("", EnchantGroup.Other, "enchantment.damageDecreasedAfterParryOrDodged",
    "[StatDamage] <b>Damage</b> decreased by {0} for {1} seconds after Parry or Damage Dodged",
    List(StatModifier(StatType.Damage, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase, OrCondition(ActionCondition(ActionType.Parry), ActionCondition(ActionType.Dodged)), Some(ValueRange(1, 30, ValueType.Duration))))
  )
  
  val damageIncreased = Enchantment("a_di", EnchantGroup.Attack, "enchantment.damageIncreased",
    "[StatDamage] <b>Damage</b> increased by {0}",
    List(StatModifier(StatType.Damage, ValueRange(3, 10, ValueType.Percentage), ModificationType.Increase))
  )
  
  val damageIncreasedAfterBackstab = Enchantment("a_diab", EnchantGroup.Attack, "enchantment.damageIncreasedAfterBackstab",
    "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Backstab",
    List(StatModifier(StatType.Damage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Backstab), Some(ValueRange(30, 30, ValueType.Duration))))
  )
  
  val damageIncreasedAfterBlock = Enchantment("a_diab", EnchantGroup.Attack, "enchantment.damageIncreasedAfterBlock",
    "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Block",
    List(StatModifier(StatType.Damage, ValueRange(5, 10, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Block), Some(ValueRange(5, 5, ValueType.Duration))))
  )
  
  val damageIncreasedAfterBlockPlagued = Enchantment("a_dib", EnchantGroup.Attack, "enchantment.damageIncreasedAfterBlock",
    "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Block",
    List(StatModifier(StatType.Damage, ValueRange(10, 20, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Block), Some(ValueRange(6, 6, ValueType.Duration))))
  )
  
  val damageIncreasedAfterDamageTaken = Enchantment("", EnchantGroup.Other, "enchantment.damageIncreasedAfterDamageTaken",
    "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Damage Taken",
    List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.DamageTaken), Some(ValueRange(1, 30, ValueType.Duration))))
  )
  
  val damageIncreasedAfterDodged = Enchantment("a_diad", EnchantGroup.Attack, "enchantment.damageIncreasedAfterDodged",
    "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Damage Dodged",
    List(StatModifier(StatType.Damage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Dodged), Some(ValueRange(5, 5, ValueType.Duration))))
  )
  
  val damageIncreasedAfterDodgedPlagued = Enchantment("a_didd", EnchantGroup.Attack, "enchantment.damageIncreasedAfterDodged",
    "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Damage Dodged",
    List(StatModifier(StatType.Damage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Dodged), Some(ValueRange(6, 6, ValueType.Duration))))
  )
  
  val damageIncreasedAfterFatality = Enchantment("a_dif", EnchantGroup.Attack, "enchantment.damageIncreasedAfterFatality",
    "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Fatality",
    List(StatModifier(StatType.Damage, ValueRange(20, 40, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Fatality), Some(ValueRange(20, 20, ValueType.Duration))))
  )
  
  val damageIncreasedAfterParry = Enchantment("a_diap", EnchantGroup.Attack, "enchantment.damageIncreasedAfterParry",
    "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Parry",
    List(StatModifier(StatType.Damage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Parry), Some(ValueRange(5, 5, ValueType.Duration))))
  )
  
  val damageIncreasedAfterParryPlagued = Enchantment("a_dip", EnchantGroup.Attack, "enchantment.damageIncreasedAfterParry",
    "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Parry",
    List(StatModifier(StatType.Damage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Parry), Some(ValueRange(6, 6, ValueType.Duration))))
  )
  
  val damageIncreasedAfterSprint = Enchantment("a_dis", EnchantGroup.Other, "enchantment.damageIncreasedAfterSprint",
    "[StatDamage] <b>Damage</b> increased by {0} after Sprinting for {1} seconds",
    List(StatModifier(StatType.Damage, ValueRange(5, 10, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.Sprinting, Some(ValueRange(2, 2, ValueType.Duration)))))
  )
  
  val damageIncreasedAfterStagger = Enchantment("a_dis", EnchantGroup.Attack, "enchantment.damageIncreasedAfterStagger",
    "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Staggering an Enemy",
    List(StatModifier(StatType.Damage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Stagger), Some(ValueRange(6, 6, ValueType.Duration))))
  )
  
  val damageIncreasedAgainstLargeEnemies = Enchantment("a_dlei", EnchantGroup.Attack, "enchantment.damageIncreasedAgainstLargeEnemies",
    "[StatDamage] <b>Damage</b> increased by {0} against Large Enemies",
    List(StatModifier(StatType.Damage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, EnemyCondition(EnemyType.Large)))
  )
  
  val damageIncreasedAgainstLowHealth = Enchantment("", EnchantGroup.Other, "enchantment.damageIncreasedAgainstLowHealth",
    "[StatDamage] <b>Damage</b> increased by {0} against Low Health Enemies",
    List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, EnemyCondition(EnemyType.LowHealth)))
  )
  
  val damageIncreasedAtFullFocus = Enchantment("a_diff", EnchantGroup.Attack, "enchantment.damageIncreasedAtFullFocus",
    "[StatDamage] <b>Damage</b> increased by {0} at Full Focus",
    List(StatModifier(StatType.Damage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.FullFocus)))
  )
  
  val damageIncreasedAtHighFocus = Enchantment("a_diahf", EnchantGroup.Attack, "enchantment.damageIncreasedAtHighFocus",
    "[StatDamage] <b>Damage</b> increased by {0} at High Focus",
    List(StatModifier(StatType.Damage, ValueRange(6, 20, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.HighFocus)))
  )
  
  val damageIncreasedAtFullHealth = Enchantment("a_diff", EnchantGroup.Attack, "enchantment.damageIncreasedAtFullHealth",
    "[StatDamage] <b>Damage</b> increased by {0} at Full Health",
    List(StatModifier(StatType.Damage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.FullHealth)))
  )
  
  val damageIncreasedAtLowHealth = Enchantment("a_dilh", EnchantGroup.Attack, "enchantment.damageIncreasedAtLowHealth",
    "[StatDamage] <b>Damage</b> increased by {0} at Low Health",
    List(StatModifier(StatType.Damage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.LowHealth)))
  )
  
  val damageIncreasedPerNearbyEnemy = Enchantment("a_dipne", EnchantGroup.Attack, "enchantment.damageIncreasedPerNearbyEnemy",
    "[StatDamage] <b>Damage</b> increased by {0} for each Nearby Enemy",
    List(StatModifier(StatType.Damage, ValueRange(2, 5, ValueType.Percentage), ModificationType.Increase, EnemyCondition(EnemyType.Nearby)))
  )
  
  val damageIncreasedWhenNoNearbyEnemy = Enchantment("a_diwnne", EnchantGroup.Attack, "enchantment.damageIncreasedWhenNoNearbyEnemy",
    "[StatDamage] <b>Damage</b> increased by {0} if no Enemies nearby",
    List(StatModifier(StatType.Damage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.NoEnemiesNearby)))
  )
  
  val damageIncreasedWhenNoNearbyEnemyPlagued = Enchantment("a_dinen", EnchantGroup.Attack, "enchantment.damageIncreasedWhenNoNearbyEnemy",
    "[StatDamage] <b>Damage</b> increased by {0} if no Enemies nearby",
    List(StatModifier(StatType.Damage, ValueRange(10, 20, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.NoEnemiesNearby)))
  )
  
  val damageScalesWithCurrentFocus = Enchantment("", EnchantGroup.Other, "enchantment.damageScalesWithCurrentFocus",
    "[StatDamage] <b>Damage</b> increased by {0} of [StatFocus] Current Focus",
    List(StatModifier(StatType.Damage, ScalingValue(ValueRange(0, 200, ValueType.Percentage), StatType.CurrentFocus), ModificationType.Increase))
  )
  
  val damageScalesWithDurability = Enchantment("a_dswd", EnchantGroup.Attack, "enchantment.damageScalesWithDurability",
    "[StatDamage] <b>Damage</b> increased by up to {0} based on Durability",
    List(StatModifier(StatType.Damage, ScalingValue(ValueRange(3, 10, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
  )
  
  val damageScalesWithDurabilityPlagued = Enchantment("a_did", EnchantGroup.Attack, "enchantment.damageScalesWithDurability",
    "[StatDamage] <b>Damage</b> increased by up to {0} based on Durability",
    List(StatModifier(StatType.Damage, ScalingValue(ValueRange(7, 15, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
  )
  
  val damageScalesWithMissingHealth = Enchantment("", EnchantGroup.Other, "enchantment.damageScalesWithMissingHealth",
    "[StatDamage] <b>Damage</b> increased by up to {0} based on Missing Health",
    List(StatModifier(StatType.Damage, ScalingValue(ValueRange(0, 200, ValueType.Percentage), StatType.MissingHealth), ModificationType.Increase))
  )
  
  val damageTakenDecreasedAfterBuildupTaken = Enchantment("d_dtdbas", EnchantGroup.Defense, "enchantment.damageTakenDecreasedAfterBuildupTaken",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} for {1} seconds after being afflicted by Status",
    List(StatModifier(StatType.DamageTaken, ValueRange(25, 50, ValueType.Percentage), ModificationType.Decrease, ActionCondition(ActionType.Afflicted), Some(ValueRange(10, 10, ValueType.Duration))))
  )
  
  val damageTakedDecreasedAtLowFocus = Enchantment("d_dtdlf", EnchantGroup.Defense, "enchantment.damageTakedDecreasedAtLowFocus",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} at Low Focus",
    List(StatModifier(StatType.DamageTaken, ValueRange(5, 10, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowFocus)))
  )
  
  val damageTakenDecreasedAtLowHealth = Enchantment("d_dtdalh", EnchantGroup.Defense, "enchantment.damageTakenDecreasedAtLowHealth",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} at Low Health",
    List(StatModifier(StatType.DamageTaken, ValueRange(6, 20, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowHealth)))
  )
  
  val damageTakenDecreasedAtLowHealthPlagued = Enchantment("d_dtdlh", EnchantGroup.Defense, "enchantment.damageTakenDecreasedAtLowHealth",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} at Low Health",
    List(StatModifier(StatType.DamageTaken, ValueRange(12, 25, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowHealth)))
  )
  
  val damageTakenDecreasedAtFullFocus = Enchantment("d_dtdff", EnchantGroup.Defense, "enchantment.damageTakenDecreasedAtFullFocus",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} at Full Focus",
    List(StatModifier(StatType.DamageTaken, ValueRange(12, 25, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.FullFocus)))
  )
  
  val damageTakenDecreasedAtFullStamina = Enchantment("d_dtdafs", EnchantGroup.Defense, "enchantment.damageTakenDecreasedAtFullStamina",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} at Full Stamina",
    List(StatModifier(StatType.DamageTaken, ValueRange(6, 20, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.FullStamina)))
  )
  
  val damageTakenDecreasedPerNearbyEnemy = Enchantment("d_dtdpne", EnchantGroup.Defense, "enchantment.damageTakenDecreasedPerNearbyEnemy",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} for each Nearby Enemy",
    // TODO fix this
    List(StatModifier(StatType.DamageTaken, ValueRange(2, 5, ValueType.Percentage), ModificationType.Decrease, EnemyCondition(EnemyType.Nearby)))
  )
  
  val damageTakenDecreasedWhileBlocking = Enchantment("d_dtdb", EnchantGroup.Defense, "enchantment.damageTakenDecreasedWhileBlocking",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} while Blocking",
    List(StatModifier(StatType.DamageTaken, ValueRange(7, 15, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.Blocking)))
  )
  
  val damageTakenDecreasedWhileCharging = Enchantment("d_dtdc", EnchantGroup.Defense, "enchantment.damageTakenDecreasedWhileCharging",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} while Charging",
    List(StatModifier(StatType.DamageTaken, ValueRange(10, 20, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.Charging)))
  )
  
  val damageTakenIncreasedAfterConsumeFood = Enchantment("", EnchantGroup.Other, "enchantment.damageTakenIncreasedAfterConsumeFood",
    "[StatDamageTaken] <b>Damage Taken</b> increased by {0} for {1} seconds after Consuming Food",
    List(StatModifier(StatType.DamageTaken, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ConsumeFood), Some(ValueRange(1, 30, ValueType.Duration))))
  )
  
  val damageTakenIncreasedAfterDamageTaken = Enchantment("down_dtiadt", EnchantGroup.Downside, "enchantment.damageTakenIncreasedAfterDamageTaken",
    "[StatDamageTaken] <b>Damage Taken</b> increased by {0} for {1} seconds after Damage Taken",
    List(StatModifier(StatType.DamageTaken, ValueRange(21, 30, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.DamageTaken), Some(ValueRange(6, 6, ValueType.Duration))))
  )
  
  val damageTakenIncreasedAtLowStamina = Enchantment("", EnchantGroup.Other, "enchantment.damageTakenIncreasedAtLowStamina",
    "[StatDamageTaken] <b>Damage Taken</b> increased by {0} at Low Stamina",
    List(StatModifier(StatType.DamageTaken, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.LowStamina)))
  )
  
  val damageTakenIncreasedIfNotFullFocus = Enchantment("down_dtiff", EnchantGroup.Downside, "enchantment.damageTakenIncreasedIfNotFullFocus",
    "[StatDamageTaken] <b>Damage Taken</b> increased by {0} if not at Full Focus",
    List(StatModifier(StatType.DamageTaken, ValueRange(26, 40, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.NotFullFocus)))
  )
  
  val damageTakenScalesWithDurability = Enchantment("d_dtswd", EnchantGroup.Defense, "enchantment.damageTakenScalesWithDurability",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by up to {0} based on Durability",
    List(StatModifier(StatType.DamageTaken, ScalingValue(ValueRange(2, 6, ValueType.Percentage), StatType.Durability), ModificationType.Decrease))
  )
  
  val damageTakenScalesWithDurabilityPlagued = Enchantment("d_dtdd", EnchantGroup.Defense, "enchantment.damageTakenScalesWithDurability",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by up to {0} based on Durability",
    List(StatModifier(StatType.DamageTaken, ScalingValue(ValueRange(5, 10, ValueType.Percentage), StatType.Durability), ModificationType.Decrease))
  )
  
  val dealColdDamageOnBlock = Enchantment("", EnchantGroup.Other, "enchantment.dealColdDamageOnBlock",
    "Deal {0} [StatColdDamage] [!sIce]Ice Damage[/s] on Block",
    List(ActionTrigger(ActionType.IceDamage, ValueRange(0, 0, ValueType.Flat), ActionCondition(ActionType.Block)))
  )
  
  val dealDamageOnBlock = Enchantment("a_ddob", EnchantGroup.Attack, "enchantment.dealDamageOnBlock",
    "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Block",
    List(ActionTrigger(ActionType.Damage, ValueRange(5, 15, ValueType.Percentage), ActionCondition(ActionType.Block)))
  )
  
  val dealDamageOnBlockPlagued = Enchantment("a_ddb", EnchantGroup.Attack, "enchantment.dealDamageOnBlock",
    "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Block",
    List(ActionTrigger(ActionType.Damage, ValueRange(10, 20, ValueType.Percentage), ActionCondition(ActionType.Block)))
  )
  
  val dealDamageOnDamageTaken = Enchantment("a_ddt", EnchantGroup.Attack, "enchantment.dealDamageOnDamageTaken",
    "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Damage Taken",
    List(ActionTrigger(ActionType.Damage, ValueRange(4, 15, ValueType.Percentage), ActionCondition(ActionType.DamageTaken)))
  )
  
  val dealDamageOnDodged = Enchantment("a_dddd", EnchantGroup.Attack, "enchantment.dealDamageOnDodged",
    "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Damage Dodged",
    List(ActionTrigger(ActionType.Damage, ValueRange(4, 15, ValueType.Percentage), ActionCondition(ActionType.DamageDodged)))
  )
  
  val dealDamageOnParry = Enchantment("a_ddop", EnchantGroup.Attack, "enchantment.dealDamageOnParry",
    "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Parry",
    List(ActionTrigger(ActionType.Damage, ValueRange(7, 25, ValueType.Percentage), ActionCondition(ActionType.Parry)))
  )
  
  val dealDamageOnParryPlagued = Enchantment("a_ddp", EnchantGroup.Attack, "enchantment.dealDamageOnParry",
    "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Parry",
    List(ActionTrigger(ActionType.Damage, ValueRange(15, 30, ValueType.Percentage), ActionCondition(ActionType.Parry)))
  )
  
  val dealDamageOnStagger = Enchantment("a_ddos", EnchantGroup.Attack, "enchantment.dealDamageOnStagger",
    "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Staggering an Enemy",
    List(ActionTrigger(ActionType.Damage, ValueRange(7, 25, ValueType.Percentage), ActionCondition(ActionType.Stagger)))
  )
  
  val dealDamageOnStaggerPlagued = Enchantment("a_ddse", EnchantGroup.Attack, "enchantment.dealDamageOnStagger",
    "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Staggering an Enemy",
    List(ActionTrigger(ActionType.Damage, ValueRange(15, 30, ValueType.Percentage), ActionCondition(ActionType.Stagger)))
  )
  
  val dealElectricDamageOnBlock = Enchantment("", EnchantGroup.Other, "enchantment.dealElectricDamageOnBlock",
    "Deal {0} [StatLightningDamage] [!sLightning]Lightning Damage[/s] on Block",
    List(ActionTrigger(ActionType.LightningDamage, ValueRange(0, 0, ValueType.Flat), ActionCondition(ActionType.Block)))
  )
  
  val dealHeatDamageOnBlock = Enchantment("", EnchantGroup.Other, "enchantment.dealHeatDamageOnBlock",
    "Deal {0} [StatFireDamage] [!sFire]Fire Damage[/s] on Block",
    List(ActionTrigger(ActionType.FireDamage, ValueRange(0, 0, ValueType.Flat), ActionCondition(ActionType.Block)))
  )
  
  val dealHeatDamageOnRuneAttack = Enchantment("", EnchantGroup.Other, "enchantment.dealHeatDamageOnRuneAttack",
    "Deal {0} [StatFireDamage] [!sFire]Fire Damage[/s] on Rune Attack",
    List(ActionTrigger(ActionType.FireDamage, ValueRange(0, 0, ValueType.Flat), ActionCondition(ActionType.RuneAttack)))
  )
  
  val delayedHealing = Enchantment("", EnchantGroup.Other, "enchantment.delayedHealing",
    "<b>Food Healing</b> is applied over time",
    List(DelayedHealing)
  )
  
  val dodgeStaminaCostDecreased = Enchantment("s_dscd", EnchantGroup.Stamina, "enchantment.dodgeStaminaCostDecreased",
    "[StatStaminaCost] <b>Dodge Stamina Cost</b> decreased by {0}",
    List(StatModifier(StatType.DodgeStaminaCost, ValueRange(7, 25, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val dodgeStaminaCostDecreasedAtLowHealth = Enchantment("s_dscdlh", EnchantGroup.Stamina, "enchantment.dodgeStaminaCostDecreasedAtLowHealth",
    "[StatStaminaCost] <b>Dodge Stamina Cost</b> decreased by {0} at Low Health",
    List(StatModifier(StatType.DodgeStaminaCost, ValueRange(18, 35, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowHealth)))
  )
  
  val dodgeStaminaCostIncreased = Enchantment("down_dsci", EnchantGroup.Downside, "enchantment.dodgeStaminaCostIncreased",
    "[StatStaminaCost] <b>Dodge Stamina Cost</b> increased by {0}",
    List(StatModifier(StatType.DodgeStaminaCost, ValueRange(26, 40, ValueType.Percentage), ModificationType.Increase))
  )
  
  val drainFocusInCombat = Enchantment("down_dfc", EnchantGroup.Downside, "enchantment.drainFocusInCombat",
    "[StatFocusRegen] <b>Drain Focus</b> in Combat",
    List(DrainFocusInCombat)
  )
  
  val drainHealthInCombat = Enchantment("down_lhc", EnchantGroup.Downside, "enchantment.drainHealthInCombat",
    "[StatHealthRegen] <b>Drain Health</b> in Combat",
    List(DrainHealthInCombat)
  )
  
  val durabilityIncreased = Enchantment("u_di", EnchantGroup.Durability, "enchantment.durabilityIncreased",
    "Item [StatDurability] <b>Durability</b> increased by {0}",
    List(StatModifier(StatType.Durability, ValueRange(30, 100, ValueType.Percentage), ModificationType.Increase))
  )
  
  val elementalDamageTakenDecreased = Enchantment("", EnchantGroup.Other, "enchantment.elementalDamageTakenDecreased",
    "[StatElementalDamageTaken] <b>Elemental Damage Taken</b> decreased by {0}",
    List(StatModifier(StatType.ElementalDamageTaken, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val endlessClimbing = Enchantment("", EnchantGroup.Other, "enchantment.endlessClimbing",
    "<b>Endless Climbing</b>",
    List(EndlessClimbing)
  )
  
  val enemyArmorDecreasedAfterFrost = Enchantment("a_fea", EnchantGroup.Attack, "enchantment.enemyArmorDecreasedAfterFrost",
    "[!sIce]Frozen[/s] Enemy [StatArmor] <b>Armor</b> decreased by {0}",
    // TODO EnemyModifier?
    List(StatModifier(StatType.EnemyArmor, ValueRange(15, 30, ValueType.Percentage), ModificationType.Decrease, EnemyCondition(EnemyType.Frozen)))
  )
  
  val enemyStaggerResistanceDecreasedAfterInfection = Enchantment("a_iesrd", EnchantGroup.Attack, "enchantment.enemyStaggerResistanceDecreasedAfterInfection",
    "[!sPlague]Infected[/s] Enemy [StatStaggerResistance] <b>Stagger Resistance</b> decreased by {0}",
    List(StatModifier(StatType.EnemyStaggerResistance, ValueRange(15, 30, ValueType.Percentage), ModificationType.Decrease, EnemyCondition(EnemyType.Infected)))
  )
  
  val equipLoadDecreased = Enchantment("down_eld", EnchantGroup.Downside, "enchantment.equipLoadDecreased",
    "[StatEquipLoad] <b>Equip Load</b> decreased by {0}",
    List(StatModifier(StatType.EquipLoad, ValueRange(20, 30, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val equipLoadIncreased = Enchantment("", EnchantGroup.Other, "enchantment.equipLoadIncreased",
    "[StatEquipLoad] <b>Equip Load</b> increased by {0}",
    List(StatModifier(StatType.EquipLoad, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
  )
  
  val executeAgainstInfectedLowHealth = Enchantment("a_elhe", EnchantGroup.Attack, "enchantment.executeAgainstInfectedLowHealth",
    "Execute [!sPlague]Infected[/s] Low Health Enemies on Damage Dealt",
    List(ActionTrigger(ActionType.Execute, ValueRange(100, 100, ValueType.Flat), AndCondition(StatusEffectCondition(StatusEffect.Infected), EnemyCondition(EnemyType.LowHealth))))
  )
  
  val experienceGainIncreased = Enchantment("", EnchantGroup.Other, "enchantment.experienceGainIncreased",
    "[StatExperience] <b>Experience Gain</b> increased by {0}",
    List(StatModifier(StatType.ExperienceGain, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
  )
  
  val fireDamageIncreased = Enchantment("a_hme2", EnchantGroup.Attack, "enchantment.fireDamageIncreased",
    "[StatFireDamage] [!sFire]Fire Damage[/s] increased by {0}",
    List(StatModifier(StatType.FireDamage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase))
  )
  
  val fireDamageIncreasedPlagued = Enchantment("a_hdi", EnchantGroup.Attack, "enchantment.fireDamageIncreased",
    "[StatFireDamage] [!sFire]Fire Damage[/s] increased by {0}",
    List(StatModifier(StatType.FireDamage, ValueRange(7, 25, ValueType.Percentage), ModificationType.Increase))
  )
  
  val fireDamageOverride = Enchantment("n_fdo", EnchantGroup.Infusion, "enchantment.fireDamageOverride",
    "[StatFireDamage] [!sFire]Fire Damage[/s] Infusion",
    List(ElementalInfusion(ElementType.Fire))
  )
  
  val fireResistanceIncreased = Enchantment("r_fri", EnchantGroup.Resistance, "enchantment.fireResistanceIncreased",
    "[StatFireArmor] <b>Fire Resistance</b> increased by {0}",
    List(StatModifier(StatType.FireArmor, ValueRange(10, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val focusCostDecreased = Enchantment("", EnchantGroup.Other, "enchantment.focusCostDecreased",
    "[StatFocusGain] <b>Focus Cost</b> decreased by {0}",
    List(StatModifier(StatType.FocusCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val focusCostDecreasedAfterRuneAttack = Enchantment("f_hme11", EnchantGroup.Focus, "enchantment.focusCostDecreasedAfterRuneAttack",
    "[StatFocusCost] <b>Focus Cost</b> decreased by {0} for {1} seconds after Rune Attackm",
    List(StatModifier(StatType.FocusCost, ValueRange(6, 20, ValueType.Percentage), ModificationType.Decrease, ActionCondition(ActionType.RuneAttack), Some(ValueRange(10, 10, ValueType.Duration))))
  )
  
  val focusCostDecreasedAfterStagger = Enchantment("f_cd", EnchantGroup.Focus, "enchantment.focusCostDecreasedAfterStagger",
    "[StatFocusCost] <b>Focus Cost</b> decreased by {0} for {1} seconds after Staggering an Enemy",
    List(StatModifier(StatType.FocusCost, ValueRange(15, 30, ValueType.Percentage), ModificationType.Decrease, ActionCondition(ActionType.Stagger), Some(ValueRange(10, 10, ValueType.Duration))))
  )
  
  val focusCostDecreasedAtFullFocus = Enchantment("f_hme7", EnchantGroup.Focus, "enchantment.focusCostDecreasedAtFullFocus",
    "[StatFocusGain] <b>Focus Cost</b> decreased by {0} at Full Focus",
    List(StatModifier(StatType.FocusCost, ValueRange(7, 25, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.FullFocus)))
  )
  
  val focusCostDecreasedAtLowHealth = Enchantment("f_hme8", EnchantGroup.Focus, "enchantment.focusCostDecreasedAtLowHealth",
    "[StatFocusGain] <b>Focus Cost</b> decreased by {0} at Low Health",
    List(StatModifier(StatType.FocusCost, ValueRange(6, 25, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowHealth)))
  )
  
  val focusCostDecreasedAtLowHealthPlagued = Enchantment("f_fcslh", EnchantGroup.Focus, "enchantment.focusCostDecreasedAtLowHealth",
    "[StatFocusGain] <b>Focus Cost</b> decreased by {0} at Low Health",
    List(StatModifier(StatType.FocusCost, ValueRange(15, 30, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowHealth)))
  )
  
  val focusCostIncreased = Enchantment("down_fci", EnchantGroup.Downside, "enchantment.focusCostIncreased",
    "[StatFocusGain] <b>Focus Cost</b> increased by {0}",
    List(StatModifier(StatType.FocusCost, ValueRange(26, 40, ValueType.Percentage), ModificationType.Increase))
  )
  
  val focusCostScalesWithMissingHealth = Enchantment("", EnchantGroup.Other, "enchantment.focusCostScalesWithMissingHealth",
    "[StatFocusGain] <b>Focus Cost</b> decreased by up to {0} based on Missing Health",
    List(StatModifier(StatType.FocusCost, ScalingValue(ValueRange(0, 100, ValueType.Percentage), StatType.MissingHealth), ModificationType.Decrease))
  )
  
  val focusGainDecreased = Enchantment("down_fgd", EnchantGroup.Downside, "enchantment.focusGainDecreased",
    "[StatFocusGain] <b>Focus Gain</b> decreased by {0}",
    List(StatModifier(StatType.FocusGain, ValueRange(32, 50, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val focusGainIncreased = Enchantment("f_hme6", EnchantGroup.Focus, "enchantment.focusGainIncreased",
    "[StatFocusGain] <b>Focus Gain</b> increased by {0}",
    List(StatModifier(StatType.FocusGain, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase))
  )
  
  val focusGainScalesWithDurability = Enchantment("f_fgswd", EnchantGroup.Focus, "enchantment.focusGainScalesWithDurability",
    "[StatFocusGain] <b>Focus Gain</b> increased by up to {0} based on Durability",
    List(StatModifier(StatType.FocusGain, ScalingValue(ValueRange(4, 15, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
  )
  
  val focusGainScalesWithDurabilityPlagued = Enchantment("f_gi", EnchantGroup.Focus, "enchantment.focusGainScalesWithDurability",
    "[StatFocusGain] <b>Focus Gain</b> increased by up to {0} based on Durability",
    List(StatModifier(StatType.FocusGain, ScalingValue(ValueRange(10, 20, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
  )
  
  val frostBuildupIncreased = Enchantment("a_fbi", EnchantGroup.Attack, "enchantment.frostBuildupIncreased",
    "[StatColdDamage] [!sIce]Frost Buildup[/s] increased by {0}",
    List(StatModifier(StatType.FrostBuildup, ValueRange(6, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val frostBuildupIncreasedPlagued = Enchantment("a_fbi", EnchantGroup.Attack, "enchantment.frostBuildupIncreased",
    "[StatColdDamage] [!sIce]Frost Buildup[/s] increased by {0}",
    List(StatModifier(StatType.FrostBuildup, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase))
  )
  
  val gainFocusOnBackstab = Enchantment("o_gfob", EnchantGroup.Focus, "enchantment.gainFocusOnBackstab",
    "Gain {0} [StatFocus] <b>Focus</b> on Backstab",
    List(ResourceGain(StatType.Focus, ValueRange(5, 10, ValueType.Flat), ActionCondition(ActionType.Backstab)))
  )
  
  val gainFocusOnBlock = Enchantment("f_gfob", EnchantGroup.Focus, "enchantment.gainFocusOnBlock",
    "Gain {0} [StatFocus] <b>Focus</b> on Block",
    List(ResourceGain(StatType.Focus, ValueRange(2, 5, ValueType.Flat), ActionCondition(ActionType.Block)))
  )
  
  val gainFocusOnBuildup = Enchantment("f_hme9", EnchantGroup.Focus, "enchantment.gainFocusOnBuildup",
    "Gain {0} [StatFocus] <b>Focus</b> on inflicting Any Status",
    List(ResourceGain(StatType.Focus, ValueRange(3, 10, ValueType.Flat), ActionCondition(ActionType.DamageDealt)))
  )
  
  val gainFocusOnBuildupPlagued = Enchantment("f_gb", EnchantGroup.Focus, "enchantment.gainFocusOnBuildup",
    "Gain {0} [StatFocus] <b>Focus</b> on inflicting Any Status",
    List(ResourceGain(StatType.Focus, ValueRange(8, 15, ValueType.Flat), ActionCondition(ActionType.DamageDealt)))
  )
  
  val gainFocusOnDodged = Enchantment("f_gfod", EnchantGroup.Focus, "enchantment.gainFocusOnDodged",
    "Gain {0} [StatFocus] <b>Focus</b> on Damage Dodged",
    List(ResourceGain(StatType.Focus, ValueRange(5, 15, ValueType.Flat), ActionCondition(ActionType.Dodged)))
  )
  
  val gainFocusOnFatality = Enchantment("f_gff", EnchantGroup.Focus, "enchantment.gainFocusOnFatality",
    "Gain {0} [StatFocus] <b>Focus</b> on Fatality",
    List(ResourceGain(StatType.Focus, ValueRange(12, 25, ValueType.Flat), ActionCondition(ActionType.Fatality)))
  )
  
  val gainFocusOnKill = Enchantment("f_gfk", EnchantGroup.Focus, "enchantment.gainFocusOnKill",
    "Gain {0} [StatFocus] <b>Focus</b> on Kill",
    List(ResourceGain(StatType.Focus, ValueRange(8, 15, ValueType.Flat), ActionCondition(ActionType.Kill)))
  )
  
  val gainFocusOnParry = Enchantment("f_gfop", EnchantGroup.Focus, "enchantment.gainFocusOnParry",
    "Gain {0} [StatFocus] <b>Focus</b> on Parry",
    List(ResourceGain(StatType.Focus, ValueRange(5, 15, ValueType.Flat), ActionCondition(ActionType.Parry)))
  )
  
  val gainFocusOnStagger = Enchantment("f_gfos", EnchantGroup.Focus, "enchantment.gainFocusOnStagger",
    "Gain {0} [StatFocus] <b>Focus</b> on Staggering an Enemy",
    List(ResourceGain(StatType.Focus, ValueRange(3, 10, ValueType.Flat), ActionCondition(ActionType.Stagger)))
  )
  
  val gainHealthForFocusSpent = Enchantment("h_hfs", EnchantGroup.Healing, "enchantment.gainHealthForFocusSpent",
    "Gain [StatHealth] <b>Health</b> equal to {0} of Focus Spent",
    // TODO check min value
    List(ResourceGain(StatType.Health, ScalingValue(ValueRange(1, 10, ValueType.Percentage), StatType.FocusSpent), ActionCondition(ActionType.FocusUse)))
  )
  
  val gainHealthOnBackstab = Enchantment("o_ghob", EnchantGroup.Other, "enchantment.gainHealthOnBackstab",
    "Gain {0} [StatHealth] <b>Health</b> on Backstab",
    List(ResourceGain(StatType.Health, ValueRange(3, 5, ValueType.Percentage), ActionCondition(ActionType.Backstab)))
  )
  
  val gainHealthOnChargedAttack = Enchantment("h_ghca", EnchantGroup.Healing, "enchantment.gainHealthOnChargedAttack",
    "Gain {0} [StatHealth] <b>Health</b> on Charged Attack",
    List(ResourceGain(StatType.Health, ValueRange(2, 4, ValueType.Percentage), ActionCondition(ActionType.ChargedAttack)))
  )
  
  val gainHealthOnDamageDealtAgainstFrozen = Enchantment("h_ghdaf", EnchantGroup.Healing, "enchantment.gainHealthOnDamageDealtAgainstFrozen",
    "Gain {0} [StatHealth] <b>Health</b> on Damage Dealt against [!sIce]Frozen[/s] Enemies",
    List(ResourceGain(StatType.Health, ValueRange(1, 2, ValueType.Percentage), AndCondition(ActionCondition(ActionType.DamageDealt), EnemyCondition(EnemyType.Frozen))))
  )
  
  val gainHealthOnDamageDealtAtLowHealth = Enchantment("", EnchantGroup.Other, "enchantment.gainHealthOnDamageDealtAtLowHealth",
    "Gain {0} [StatHealth] <b>Health</b> on Damage Dealt at Low Health",
    List(ResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), AndCondition(ActionCondition(ActionType.DamageDealt), StateCondition(ConditionType.LowHealth))))
  )
  
  val gainHealthOnDodged = Enchantment("h_ghdd", EnchantGroup.Healing, "enchantment.gainHealthOnDodged",
    "Gain {0} [StatHealth] <b>Health</b> on Damage Dodged",
    List(ResourceGain(StatType.Health, ValueRange(3, 5, ValueType.Percentage), ActionCondition(ActionType.Dodged)))
  )
  
  val gainHealthOnFatality = Enchantment("h_ghf", EnchantGroup.Healing, "enchantment.gainHealthOnFatality",
    "Gain {0} [StatHealth] <b>Health</b> on Fatality",
    List(ResourceGain(StatType.Health, ValueRange(5, 10, ValueType.Percentage), ActionCondition(ActionType.Fatality)))
  )
  
  val gainHealthOnKill = Enchantment("h_ghok", EnchantGroup.Healing, "enchantment.gainHealthOnKill",
    "Gain {0} [StatHealth] <b>Health</b> on Kill",
    List(ResourceGain(StatType.Health, ValueRange(3, 10, ValueType.Percentage), ActionCondition(ActionType.Kill)))
  )
  
  val gainHealthOnKillPlagued = Enchantment("h_ghk", EnchantGroup.Healing, "enchantment.gainHealthOnKill",
    "Gain {0} [StatHealth] <b>Health</b> on Kill",
    List(ResourceGain(StatType.Health, ValueRange(5, 10, ValueType.Percentage), ActionCondition(ActionType.Kill)))
  )
  
  val gainHealthOnKillAgainstStaggeredEnemies = Enchantment("", EnchantGroup.Other, "enchantment.gainHealthOnKillAgainstStaggeredEnemies",
    "Gain {0} [StatHealth] <b>Health</b> on Kill against Staggered Enemies",
    List(ResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), AndCondition(ActionCondition(ActionType.Kill), EnemyCondition(EnemyType.Staggered))))
  )
  
  val gainHealthOnParry = Enchantment("h_ghp", EnchantGroup.Healing, "enchantment.gainHealthOnParry",
    "Gain {0} [StatHealth] <b>Health</b> on Parry",
    List(ResourceGain(StatType.Health, ValueRange(2, 6, ValueType.Percentage), ActionCondition(ActionType.Parry)))
  )
  
  val gainHealthOnStagger = Enchantment("h_ghse", EnchantGroup.Healing, "enchantment.gainHealthOnStagger",
    "Gain {0} [StatHealth] <b>Health</b> on Staggering an Enemy",
    List(ResourceGain(StatType.Health, ValueRange(5, 10, ValueType.Percentage), ActionCondition(ActionType.Stagger)))
  )
  
  val gainHealthOncePerRuneAttack = Enchantment("", EnchantGroup.Other, "enchantment.gainHealthOncePerRuneAttack",
    "Gain {0} [StatHealth] <b>Health</b> once per Rune Attack",
    List(ResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.RuneAttack)))
  )
  
  val gainStaminaOnBlock = Enchantment("", EnchantGroup.Other, "enchantment.gainStaminaOnBlock",
    "Gain {0} [StatStamina] <b>Stamina</b> on Block",
    List(ResourceGain(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Block)))
  )
  
  val gainStaminaOnDamageDealt = Enchantment("", EnchantGroup.Other, "enchantment.gainStaminaOnDamageDealt",
    "Gain {0} [StatStamina] <b>Stamina</b> on Damage Dealt",
    List(ResourceGain(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.DamageDealt)))
  )
  
  val gainStaminaOnDodged = Enchantment("s_gsod", EnchantGroup.Stamina, "enchantment.gainStaminaOnDodged",
    "Gain {0} [StatStamina] <b>Stamina</b> on Damage Dodged",
    List(ResourceGain(StatType.Stamina, ValueRange(5, 15, ValueType.Flat), ActionCondition(ActionType.Dodged)))
  )
  
  val gainStaminaOnFocusUse = Enchantment("", EnchantGroup.Other, "enchantment.gainStaminaOnFocusUse",
    "Gain {0} [StatStamina] <b>Stamina</b> on Focus Use",
    List(ResourceGain(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.FocusUse)))
  )
  
  val gainStaminaOnParry = Enchantment("s_gsop", EnchantGroup.Stamina, "enchantment.gainStaminaOnParry",
    "Gain {0} [StatStamina] <b>Stamina</b> on Parry",
    List(ResourceGain(StatType.Stamina, ValueRange(9, 30, ValueType.Flat), ActionCondition(ActionType.Parry)))
  )
  
  val gainStaminaOnStagger = Enchantment("", EnchantGroup.Other, "enchantment.gainStaminaOnStagger",
    "Gain {0} [StatStamina] <b>Stamina</b> on Staggering an Enemy",
    List(ResourceGain(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Stagger)))
  )
  
  val healingDecreased = Enchantment("down_hd", EnchantGroup.Downside, "enchantment.healingDecreased",
    "[StatHealing] <b>Healing</b> decreased by {0}",
    List(StatModifier(StatType.Healing, ValueRange(20, 30, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val healingDecreasedPerNearbyEnemy = Enchantment("down_asci", EnchantGroup.Downside, "enchantment.healingDecreasedPerNearbyEnemy",
    "[StatHealing] <b>Healing</b> decreased by {0} for each Nearby Enemy",
    List(StatModifier(StatType.Healing, ValueRange(9, 14, ValueType.Percentage), ModificationType.Decrease, EnemyCondition(EnemyType.Nearby)))
  )
  
  val healingIncreased = Enchantment("h_hi", EnchantGroup.Healing, "enchantment.healingIncreased",
    "[StatHealing] <b>Healing</b> increased by {0}",
    List(StatModifier(StatType.Healing, ValueRange(6, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val healingIncreasedAtLowFocus = Enchantment("h_hilf", EnchantGroup.Healing, "enchantment.healingIncreasedAtLowFocus",
    "[StatHealing] <b>Healing</b> increased by {0} at Low Focus",
    List(StatModifier(StatType.Healing, ValueRange(9, 30, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.LowFocus)))
  )
  
  val healingScalesWithDurability = Enchantment("h_hswd", EnchantGroup.Healing, "enchantment.healingScalesWithDurability",
    "[StatHealing] <b>Healing</b> increased by up to {0} based on Durability",
    List(StatModifier(StatType.Healing, ScalingValue(ValueRange(4, 15, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
  )
  
  val healingScalesWithDurabilityPlagued = Enchantment("h_hid", EnchantGroup.Healing, "enchantment.healingScalesWithDurability",
    "[StatHealing] <b>Healing</b> increased by up to {0} based on Durability",
    List(StatModifier(StatType.Healing, ScalingValue(ValueRange(6, 20, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
  )
  
  val healthRallyIncreased = Enchantment("h_hri", EnchantGroup.Healing, "enchantment.healthRallyIncreased",
    "[StatHealthRally] <b>Regainable Health</b> increased by {0}",
    List(StatModifier(StatType.HealthRally, ValueRange(10, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val heatDamageIncreasedAfterColdDamageDealt = Enchantment("", EnchantGroup.Other, "enchantment.heatDamageIncreasedAfterColdDamageDealt",
    "[StatFireDamage] [!sFire]Fire Damage[/s] increased by {0} for {1} seconds after [!sIce]Ice Damage[/s] Dealt",
    List(StatModifier(StatType.FireDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.DamageDealt), Some(ValueRange(1, 30, ValueType.Duration))))
  )
  
  val hungerIncreased = Enchantment("h_hl", EnchantGroup.Healing, "enchantment.hungerIncreased",
    "<b>Hunger Limit</b> increased by {0}",
    List(StatModifier(StatType.HungerLimit, ValueRange(1, 1, ValueType.Flat), ModificationType.Increase))
  )
  
  val indestructible = Enchantment("i_i", EnchantGroup.Indestructible, "enchantment.indestructible",
    "<b>Indestructible</b>",
    List(Indestructible)
  )
  
  val infectionBuildupIncreased = Enchantment("a_ibi", EnchantGroup.Attack, "enchantment.infectionBuildupIncreased",
    "[StatPlagueDamage] [!sPlague]Infection Buildup[/s] increased by {0}",
    List(StatModifier(StatType.InfectionBuildup, ValueRange(6, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val infectionBuildupIncreasedPlagued = Enchantment("a_ibi", EnchantGroup.Attack, "enchantment.infectionBuildupIncreased",
    "[StatPlagueDamage] [!sPlague]Infection Buildup[/s] increased by {0}",
    List(StatModifier(StatType.InfectionBuildup, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase))
  )
  
  val itemWeightDecreased = Enchantment("w_iwd", EnchantGroup.Weight, "enchantment.itemWeightDecreased",
    "Item [StatWeight] <b>Weight</b> decreased by {0}",
    List(StatModifier(StatType.Weight, ValueRange(3, 10, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val itemWeightDecreasedPlagued = Enchantment("w_iwd", EnchantGroup.Weight, "enchantment.itemWeightDecreased",
    "Item [StatWeight] <b>Weight</b> decreased by {0}",
    List(StatModifier(StatType.Weight, ValueRange(7, 15, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val itemWeightIncreased = Enchantment("", EnchantGroup.Other, "enchantment.itemWeightIncreased",
    "Item [StatWeight] <b>Weight</b> increased by {0}",
    List(StatModifier(StatType.Weight, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
  )
  
  val knockbackResistanceIncreased = Enchantment("", EnchantGroup.Other, "enchantment.knockbackResistanceIncreased",
    "<b>Knockback Resistance</b> increased by {0}",
    List(StatModifier(StatType.Armor, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
  )
  
  val lifestealDisabled = Enchantment("", EnchantGroup.Other, "enchantment.lifestealDisabled",
    "Cannot [StatLifesteal] <b>Lifesteal</b>",
    List(LifestealDisabled)
  )
  
  val lifestealIncreased = Enchantment("", EnchantGroup.Other, "enchantment.lifestealIncreased",
    "[StatLifesteal] <b>Lifesteal</b> increased by {0}",
    List(StatModifier(StatType.Lifesteal, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
  )
  
  val lifestealIncreasedAtLowHealth = Enchantment("", EnchantGroup.Other, "enchantment.lifestealIncreasedAtLowHealth",
    "[StatLifesteal] <b>Lifesteal</b> increased by {0} at Low Health",
    List(StatModifier(StatType.Lifesteal, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.LowHealth)))
  )
  
  val lightningDamageIncreased = Enchantment("a_hme4", EnchantGroup.Attack, "enchantment.lightningDamageIncreased",
    "[StatLightningDamage] [!sLightning]Lightning Damage[/s] increased by {0}",
    List(StatModifier(StatType.LightningDamage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase))
  )
  
  val lightningDamageIncreasedPlagued = Enchantment("a_ldi", EnchantGroup.Attack, "enchantment.lightningDamageIncreased",
    "[StatLightningDamage] [!sLightning]Lightning Damage[/s] increased by {0}",
    List(StatModifier(StatType.LightningDamage, ValueRange(7, 25, ValueType.Percentage), ModificationType.Increase))
  )
  
  val lightningDamageOverride = Enchantment("n_ldo", EnchantGroup.Infusion, "enchantment.lightningDamageOverride",
    "[StatLightningDamage] [!sLightning]Lightning Damage[/s] Infusion",
    List(ElementalInfusion(ElementType.Lightning))
  )
  
  val lightningDamageOverrideAfterSprint = Enchantment("", EnchantGroup.Other, "enchantment.lightningDamageOverrideAfterSprint",
    "[StatLightningDamage] [!sLightning]Lightning Damage[/s] Infusion after Sprinting for {0} seconds",
    List(ElementalInfusion(ElementType.Lightning, StateCondition(ConditionType.Sprinting, Some(ValueRange(1, 10, ValueType.Duration)))))
  )
  
  val lightningResistanceIncreased = Enchantment("r_lri", EnchantGroup.Resistance, "enchantment.lightningResistanceIncreased",
    "[StatLightningArmor] <b>Lightning Resistance</b> increased by {0}",
    List(StatModifier(StatType.LightningArmor, ValueRange(10, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val loseFocusOnDamageTaken = Enchantment("down_lftd", EnchantGroup.Downside, "enchantment.loseFocusOnDamageTaken",
    "Lose {0} [StatFocus] <b>Focus</b> on Damage Taken",
    List(ResourceLoss(StatType.Focus, ValueRange(10, 15, ValueType.Flat), ActionCondition(ActionType.DamageTaken)))
  )
  
  val loseFocusOnFocusUse = Enchantment("", EnchantGroup.Other, "enchantment.loseFocusOnFocusUse",
    "Lose {0} [StatFocus] <b>Focus</b> on Focus Use",
    List(ResourceLoss(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.FocusUse)))
  )
  
  val loseStaminaOnBlock = Enchantment("", EnchantGroup.Other, "enchantment.loseStaminaOnBlock",
    "Lose {0} [StatStamina] <b>Stamina</b> on Block",
    List(ResourceLoss(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Block)))
  )
  
  val loseStaminaOnDamageTaken = Enchantment("", EnchantGroup.Other, "enchantment.loseStaminaOnDamageTaken",
    "Lose {0} [StatStamina] <b>Stamina</b> on Damage Taken",
    List(ResourceLoss(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.DamageTaken)))
  )
  
  val maxFocusDecreased = Enchantment("down_mfd", EnchantGroup.Downside, "enchantment.maxFocusDecreased",
    "[StatMaxFocus] <b>Max Focus</b> decreased by {0}",
    List(StatModifier(StatType.MaxFocus, ValueRange(18, 25, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val maxFocusIncreased = Enchantment("f_hme10", EnchantGroup.Focus, "enchantment.maxFocusIncreased",
    "[StatMaxFocus] <b>Max Focus</b> increased by {0}",
    List(StatModifier(StatType.MaxFocus, ValueRange(7, 25, ValueType.Percentage), ModificationType.Increase))
  )
  
  val maxHealthDecreased = Enchantment("down_mhd", EnchantGroup.Downside, "enchantment.maxHealthDecreased",
    "[StatMaxHealth] <b>Max Health</b> decreased by {0}",
    List(StatModifier(StatType.MaxHealth, ValueRange(14, 20, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val maxHealthIncreased = Enchantment("h_mhi", EnchantGroup.Healing, "enchantment.maxHealthIncreased",
    "[StatMaxHealth] <b>Max Health</b> increased by {0}",
    List(StatModifier(StatType.MaxHealth, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase))
  )
  
  val maxHealthScalesWithMaxFocus = Enchantment("h_mhi", EnchantGroup.Healing, "enchantment.maxHealthScalesWithMaxFocus",
    "[StatMaxHealth] <b>Max Health</b> increased by {0}% of Max Focus",
    List(StatModifier(StatType.MaxHealth, ScalingValue(ValueRange(5, 10, ValueType.Percentage), StatType.MaxFocus), ModificationType.Increase))
  )
  
  val maxStaminaDecreased = Enchantment("down_msd", EnchantGroup.Downside, "enchantment.maxStaminaDecreased",
    "[StatMaxStamina] <b>Max Stamina</b> decreased by {0}",
    List(StatModifier(StatType.MaxStamina, ValueRange(14, 20, ValueType.Percentage), ModificationType.Decrease))
  )
  
  // TODO Healing -> Stamina?
  val maxStaminaScalesWithMaxFocus = Enchantment("h_msimf", EnchantGroup.Healing, "enchantment.maxStaminaScalesWithMaxFocus",
    "[StatMaxStamina] <b>Max Stamina</b> increased by {0}% of Max Focus",
    List(StatModifier(StatType.MaxStamina, ScalingValue(ValueRange(3, 5, ValueType.Percentage), StatType.MaxFocus), ModificationType.Increase))
  )
  
  val movementSpeedDecreased = Enchantment("down_msd", EnchantGroup.Downside, "enchantment.movementSpeedDecreased",
    "[StatMovementSpeed] <b>Movement Speed</b> decreased by {0}",
    List(StatModifier(StatType.MovementSpeed, ValueRange(7, 25, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val movementSpeedDecreasedPerNearbyEnemy = Enchantment("", EnchantGroup.Other, "enchantment.movementSpeedDecreasedPerNearbyEnemy",
    "[StatMovementSpeed] <b>Movement Speed</b> decreased by {0} per Nearby Enemy",
    List(StatModifier(StatType.MovementSpeed, ValueRange(0, 30, ValueType.Percentage), ModificationType.Decrease, EnemyCondition(EnemyType.Nearby)))
  )
  
  val movementSpeedIncreased = Enchantment("m_msi", EnchantGroup.Movement , "enchantment.movementSpeedIncreased",
    "[StatMovementSpeed] <b>Movement Speed</b> increased by {0}",
    List(StatModifier(StatType.MovementSpeed, ValueRange(3, 10, ValueType.Percentage), ModificationType.Increase))
  )
  
  val movementSpeedIncreasedAfterChargedAttack = Enchantment("m_msica", EnchantGroup.Movement, "enchantment.movementSpeedIncreasedAfterChargedAttack",
    "[StatMovementSpeed] <b>Movement Speed</b> increased by {0} for {1} seconds after Charged Attack",
    List(StatModifier(StatType.MovementSpeed, ValueRange(7, 15, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ChargedAttack), Some(ValueRange(10, 10, ValueType.Duration))))
  )
  
  val movementSpeedIncreasedAfterKill = Enchantment("m_msiak", EnchantGroup.Movement, "enchantment.movementSpeedIncreasedAfterKill",
    "[StatMovementSpeed] <b>Movement Speed</b> increased by {0} for {1} seconds after Kill",
    List(StatModifier(StatType.MovementSpeed, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Kill), Some(ValueRange(10, 10, ValueType.Duration))))
  )
  
  val movementSpeedIncreasedAfterRuneAttack = Enchantment("m_msira", EnchantGroup.Movement, "enchantment.movementSpeedIncreasedAfterRuneAttack",
    "[StatMovementSpeed] <b>Movement Speed</b> increased by {0} for {1} seconds after Rune Attack",
    List(StatModifier(StatType.MovementSpeed, ValueRange(7, 15, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.RuneAttack), Some(ValueRange(5, 5, ValueType.Duration))))
  )
  
  val normalDamageIncreasedAfterChargedAttack = Enchantment("a_ndiaca", EnchantGroup.Attack, "enchantment.normalDamageIncreasedAfterChargedAttack",
    "[StatDamage] <b>Normal Attack Damage</b> increased by {0} for {1} seconds after Charged Attack",
    List(StatModifier(StatType.Damage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ChargedAttack), Some(ValueRange(4, 4, ValueType.Duration))))
  )
  
  val normalDamageIncreasedAfterChargedAttackPlagued = Enchantment("a_nadica", EnchantGroup.Attack, "enchantment.normalDamageIncreasedAfterChargedAttack",
    "[StatDamage] <b>Normal Attack Damage</b> increased by {0} for {1} seconds after Charged Attack",
    List(StatModifier(StatType.Damage, ValueRange(15, 25, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ChargedAttack), Some(ValueRange(5, 5, ValueType.Duration))))
  )
  
  val onlyHeavyRoll = Enchantment("", EnchantGroup.Other, "enchantment.onlyHeavyRoll",
    "<b>Heavy Roll</b> only",
    List(OnlyHeavyRoll)
  )
  
  val parryDisabled = Enchantment("", EnchantGroup.Other, "enchantment.parryDisabled",
    "<b>Parry</b> disabled",
    List(ParryDisabled)
  )
  
  val parryStaminaCostDecreased = Enchantment("", EnchantGroup.Other, "enchantment.parryStaminaCostDecreased",
    "[StatStaminaCost] <b>Parry Stamina Cost</b> decreased by {0}",
    List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val physicalDamageIncreased = Enchantment("a_pdi", EnchantGroup.Attack, "enchantment.physicalDamageIncreased",
    "[StatPhysicalDamage] [!sPhysical]Physical Damage[/s] increased by {0}",
    List(StatModifier(StatType.PhysicalDamage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase))
  )
  
  val physicalDamageIncreasedPlagued = Enchantment("a_pdi", EnchantGroup.Attack, "enchantment.physicalDamageIncreased",
    "[StatPhysicalDamage] [!sPhysical]Physical Damage[/s] increased by {0}",
    List(StatModifier(StatType.PhysicalDamage, ValueRange(7, 25, ValueType.Percentage), ModificationType.Increase))
  )
  
  val physicalDamageTakenDecreased = Enchantment("", EnchantGroup.Other, "enchantment.physicalDamageTakenDecreased",
    "[StatDamageTaken] <b>Physical Damage Taken</b> decreased by {0}",
    List(StatModifier(StatType.DamageTaken, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val plagueDamageIncreased = Enchantment("a_hme5", EnchantGroup.Attack, "enchantment.plagueDamageIncreased",
    "[StatPlagueDamage] [!sPlague]Plague Damage[/s] increased by {0}",
    List(StatModifier(StatType.PlagueDamage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase))
  )
  
  val plagueDamageIncreasedPlagued = Enchantment("a_pdi", EnchantGroup.Attack, "enchantment.plagueDamageIncreased",
    "[StatPlagueDamage] [!sPlague]Plague Damage[/s] increased by {0}",
    List(StatModifier(StatType.PlagueDamage, ValueRange(7, 25, ValueType.Percentage), ModificationType.Increase))
  )
  
  val plagueDamageOverride = Enchantment("n_pdo", EnchantGroup.Infusion, "enchantment.plagueDamageOverride",
    "[StatPlagueDamage] [!sPlague]Plague Damage[/s] Infusion",
    List(ElementalInfusion(ElementType.Plague))
  )
  
  val plagueResistanceIncreased = Enchantment("r_pri", EnchantGroup.Resistance, "enchantment.plagueResistanceIncreased",
    "[StatPlagueArmor] <b>Plague Resistance</b> increased by {0}",
    List(StatModifier(StatType.PlagueArmor, ValueRange(10, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val poiseDefenseIncreased = Enchantment("o_pdi", EnchantGroup.Other, "enchantment.poiseDefenseIncreased",
    "[StatPoiseDefense] <b>Poise Defense</b> increased by {0}",
    List(StatModifier(StatType.PoiseDefense, ValueRange(1, 3, ValueType.Flat), ModificationType.Increase))
  )
  
  val poiseDefenseIncreasedWhileBlocking = Enchantment("o_pdiwb", EnchantGroup.Other, "enchantment.poiseDefenseIncreasedWhileBlocking",
    "[StatPoiseDefense] <b>Poise Defense</b> increased by {0} while Blocking",
    List(StatModifier(StatType.PoiseDefense, ValueRange(5, 10, ValueType.Flat), ModificationType.Increase, StateCondition(ConditionType.Blocking)))
  )
  
  val poiseDefenseIncreasedWhileCharging = Enchantment("o_pdic", EnchantGroup.Attack, "enchantment.poiseDefenseIncreasedWhileCharging",
    "[StatPoiseDefense] <b>Poise Defense</b> increased by {0} while Charging",
    List(StatModifier(StatType.PoiseDefense, ValueRange(5, 10, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.Charging)))
  )
  
  val refillFocusOnBackstab = Enchantment("", EnchantGroup.Other, "enchantment.refillFocusOnBackstab",
    "Refill [StatFocus] <b>Focus</b> on Backstab",
    List(ResourceGain(StatType.Focus, ValueRange(100, 100, ValueType.Percentage), ActionCondition(ActionType.Backstab)))
  )
  
  val regenFocusInCombat = Enchantment("f_hme12", EnchantGroup.Focus, "enchantment.regenFocusInCombat",
    "[StatFocusRegen] <b>Regenerate Focus</b> in Combat",
    List(RegenFocusInCombat)
  )
  
  val regenHealthInCombat = Enchantment("h_rh", EnchantGroup.Healing, "enchantment.regenHealthInCombat",
    "[StatHealthRegen] <b>Regenerate Health</b> in Combat",
    List(RegenHealthInCombat)
  )
  
  val regenHealthWhileBlocking = Enchantment("", EnchantGroup.Other, "enchantment.regenHealthWhileBlocking",
    "[StatHealthRegen] <b>Regenerate Health</b> while Blocking",
    List(StatModifier(StatType.HealthRegen, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.Blocking)))
  )
  
  val repairDisabled = Enchantment("down_u", EnchantGroup.Downside, "enchantment.repairDisabled",
    "<b>Unrepairable</b>",
    List(Unrepairable)
  )
  
  val runeDamageIncreased = Enchantment("a_rdi", EnchantGroup.Attack, "enchantment.runeDamageIncreased",
    "[StatDamage] <b>Rune Damage</b> increased by {0}",
    List(StatModifier(StatType.Damage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase))
  )
  
  val runeDamageIncreasedPlagued = Enchantment("a_rdi", EnchantGroup.Attack, "enchantment.runeDamageIncreased",
    "[StatDamage] <b>Rune Damage</b> increased by {0}",
    List(StatModifier(StatType.Damage, ValueRange(10, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val shockBuildupIncreased = Enchantment("a_sbi", EnchantGroup.Attack, "enchantment.shockBuildupIncreased",
    "[StatLightningDamage] [!sLightning]Shock Buildup[/s] increased by {0}",
    List(StatModifier(StatType.LightningDamage, ValueRange(6, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val shockBuildupIncreasedPlagued = Enchantment("a_sbi", EnchantGroup.Attack, "enchantment.shockBuildupIncreased",
    "[StatLightningDamage] [!sLightning]Shock Buildup[/s] increased by {0}",
    List(StatModifier(StatType.LightningDamage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase))
  )
  
  val shockBuildupIncreasedAfterShock = Enchantment("", EnchantGroup.Other, "enchantment.shockBuildupIncreasedAfterShock",
    "[StatLightningDamage] [!sLightning]Shock Buildup[/s] increased by {0} for {1} seconds after inflicting [!sLightning]Shock Status[/s]",
    List(StatModifier(StatType.LightningDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.Shocked), Some(ValueRange(1, 30, ValueType.Duration))))
  )
  
  val spawnExplosionOnKill = Enchantment("a_seok", EnchantGroup.Attack, "enchantment.spawnExplosionOnKill",
    "Deal {0} Explosion [StatFireDamage] [!sFire]Damage[/s] on Kill",
    List(ActionTrigger(ActionType.Explosion, ValueRange(25, 50, ValueType.Percentage), ActionCondition(ActionType.Kill)))
  )
  
  val speedIncreasedAfterShock = Enchantment("m_os", EnchantGroup.Movement, "enchantment.speedIncreasedAfterShock",
    "[StatAttackSpeed] <b>Overall Speed</b> increased by {0} for {1} seconds after inflicting [!sLightning]Shock Status[/s]",
    List(StatModifier(StatType.AttackSpeed, ValueRange(7, 15, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.Shocked), Some(ValueRange(10, 10, ValueType.Duration))))
  )
  
  val spendHealthIfThereIsNoFocus = Enchantment("", EnchantGroup.Other, "enchantment.spendHealthIfThereIsNoFocus",
    "Spend [StatHealth] <b>Health</b> if there is not enough [StatFocus] <b>Focus</b> available",
    List(SpendHealthIfNoFocus)
  )
  
  val spendHealthInsteadFocus = Enchantment("", EnchantGroup.Other, "enchantment.spendHealthInsteadFocus",
    "Spend [StatHealth] <b>Health</b> instead of [StatFocus] <b>Focus</b>",
    List(SpendHealthInsteadOfFocus)
  )
  
  val sprintStaminaCostDecreased = Enchantment("", EnchantGroup.Other, "enchantment.sprintStaminaCostDecreased",
    "[StatStaminaCost] <b>Sprint Stamina Cost</b> decreased by {0}",
    List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val staggerDamageIncreased = Enchantment("a_sdi", EnchantGroup.Attack, "enchantment.staggerDamageIncreased",
    "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0}",
    List(StatModifier(StatType.StaggerDamage, ValueRange(4, 15, ValueType.Percentage), ModificationType.Increase))
  )
  
  val staggerDamageIncreasedAfterBackstab = Enchantment("a_sdiab", EnchantGroup.Attack, "enchantment.staggerDamageIncreasedAfterBackstab",
    "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0} for {1} seconds after Backstab",
    List(StatModifier(StatType.StaggerDamage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Backstab), Some(ValueRange(30, 30, ValueType.Duration))))
  )
  
  val staggerDamageIncreasedAfterChargedAttack = Enchantment("a_sdiaca", EnchantGroup.Attack, "enchantment.staggerDamageIncreasedAfterChargedAttack",
    "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0} for {1} seconds after Charged Attack",
    List(StatModifier(StatType.StaggerDamage, ValueRange(6, 20, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ChargedAttack), Some(ValueRange(4, 4, ValueType.Duration))))
  )
  
  val staggerDamageIncreasedAfterFatality = Enchantment("a_sdiaf", EnchantGroup.Attack, "enchantment.staggerDamageIncreasedAfterFatality",
    "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0} for {1} seconds after Fatality",
    List(StatModifier(StatType.StaggerDamage, ValueRange(20, 40, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Fatality), Some(ValueRange(20, 20, ValueType.Duration))))
  )
  
  val staggerDamageIncreasedAfterKill = Enchantment("a_sdiak", EnchantGroup.Attack, "enchantment.staggerDamageIncreasedAfterKill",
    "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0} for {1} seconds after Kill",
    List(StatModifier(StatType.StaggerDamage, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Kill), Some(ValueRange(20, 20, ValueType.Duration))))
  )
  
  val staggerDamageIncreasedAtLowHealth = Enchantment("a_sdilh", EnchantGroup.Attack, "enchantment.staggerDamageIncreasedAtLowHealth",
    "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0} at Low Health",
    List(StatModifier(StatType.StaggerDamage, ValueRange(12, 25, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.LowHealth)))
  )
  
  val staggerResistanceDecreased = Enchantment("down_srd", EnchantGroup.Downside, "enchantment.staggerResistanceDecreased",
    "[StatStaggerResistance] <b>Stagger Resistance</b> decreased by {0}",
    List(StatModifier(StatType.StaggerResistance, ValueRange(26, 40, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val staggerResistanceIncreased = Enchantment("r_sri", EnchantGroup.Resistance, "enchantment.staggerResistanceIncreased",
    "[StatStaggerResistance] <b>Stagger Resistance</b> increased by {0}",
    List(StatModifier(StatType.StaggerResistance, ValueRange(7, 15, ValueType.Percentage), ModificationType.Increase))
  )
  
  val staminaCostDecreased = Enchantment("", EnchantGroup.Other, "enchantment.staminaCostDecreased",
    "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0}",
    List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
  )
  
  val staminaCostDecreasedAfterFatality = Enchantment("s_scdf", EnchantGroup.Stamina, "enchantment.staminaCostDecreasedAfterFatality",
    "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} for {1} seconds after Fatality",
    List(StatModifier(StatType.StaminaCost, ValueRange(15, 30, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Fatality), Some(ValueRange(20, 20, ValueType.Duration))))
  )
  
  val staminaCostDecreasedAtFullHealth = Enchantment("s_scdafh", EnchantGroup.Stamina , "enchantment.staminaCostDecreasedAtFullHealth",
    "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} at Full Health",
    List(StatModifier(StatType.StaminaCost, ValueRange(7, 25, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.FullHealth)))
  )
  
  val staminaCostDecreasedAtHighFocus = Enchantment("s_scdahf", EnchantGroup.Stamina, "enchantment.staminaCostDecreasedAtHighFocus",
    "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} at High Focus",
    List(StatModifier(StatType.StaminaCost, ValueRange(8, 25, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.HighFocus)))
  )
  
  val staminaCostDecreasedAtLowFocus = Enchantment("s_scdlf", EnchantGroup.Stamina, "enchantment.staminaCostDecreasedAtLowFocus",
    "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} at Low Focus",
    List(StatModifier(StatType.StaminaCost, ValueRange(10, 20, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowFocus)))
  )
  
  val staminaCostDecreasedAtLowHealth = Enchantment("s_scdalh", EnchantGroup.Stamina, "enchantment.staminaCostDecreasedAtLowHealth",
    "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} at Low Health",
    List(StatModifier(StatType.StaminaCost, ValueRange(7, 25, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowHealth)))
  )
  
  val staminaCostDecreasedAtLowHealthPlagued = Enchantment("s_scdalh", EnchantGroup.Stamina, "enchantment.staminaCostDecreasedAtLowHealth",
    "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} at Low Health",
    List(StatModifier(StatType.StaminaCost, ValueRange(15, 30, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowHealth)))
  )
  
  val staminaCostIncreased = Enchantment("down_sci", EnchantGroup.Downside, "enchantment.staminaCostIncreased",
    "[StatStaminaCost] <b>Stamina Cost</b> increased by {0}",
    List(StatModifier(StatType.StaminaCost, ValueRange(13, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val staminaRefillOnParry = Enchantment("", EnchantGroup.Other, "enchantment.staminaRefillOnParry",
    "Refill [StatStamina] <b>Stamina</b> on Parry",
    List(ResourceGain(StatType.Stamina, ValueRange(100, 100, ValueType.Percentage), ActionCondition(ActionType.Parry)))
  )
  
  val staminaRefillOnStagger = Enchantment("", EnchantGroup.Other, "enchantment.staminaRefillOnStagger",
    "Refill [StatStamina] <b>Stamina</b> on Staggering an Enemy",
    List(ResourceGain(StatType.Stamina, ValueRange(100, 100, ValueType.Percentage), ActionCondition(ActionType.Stagger)))
  )
  
  val staminaRegenerationIncreased = Enchantment("s_sri", EnchantGroup.Stamina, "enchantment.staminaRegenerationIncreased",
    "[StatStaminaRegen] <b>Stamina Recovery</b> increased by {0}",
    List(StatModifier(StatType.StaminaRegen, ValueRange(6, 20, ValueType.Percentage), ModificationType.Increase))
  )
  
  val staminaRegenerationScalesWithDurability = Enchantment("s_srswd", EnchantGroup.Stamina, "enchantment.staminaRegenerationScalesWithDurability",
    "[StatStaminaRegen] <b>Stamina Recovery</b> increased by up to {0} based on Durability",
    List(StatModifier(StatType.StaminaRegen, ScalingValue(ValueRange(6, 20, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
  )
  
  val staminaRegenerationScalesWithDurabilityPlagued = Enchantment("s_sri", EnchantGroup.Stamina, "enchantment.staminaRegenerationScalesWithDurability",
    "[StatStaminaRegen] <b>Stamina Recovery</b> increased by up to {0} based on Durability",
    List(StatModifier(StatType.StaminaRegen, ScalingValue(ValueRange(7, 25, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
  )
  
  val swapMaxHealthAndMaxFocus = Enchantment("", EnchantGroup.Other, "enchantment.swapMaxHealthAndMaxFocus",
    "Swap <b>Max Health</b> and <b>Max Focus</b>",
    List(SwapStats(StatType.Health, StatType.MaxFocus))
  )
  
  val takeDamageOnFocusUse = Enchantment("down_tdfu", EnchantGroup.Downside, "enchantment.takeDamageOnFocusUse",
    "Take {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Focus Use",
    List(ResourceLoss(StatType.Health, ValueRange(20, 30, ValueType.Percentage), ActionCondition(ActionType.FocusUse)))
  )
  
  val uniqueBuffSpecialDamageAfterSpecial = Enchantment("", EnchantGroup.Other, "enchantment.unique.buffSpecialDamageAfterSpecial",
    "<b>Rune Attack Damage</b> increases by {1} for {0} <b>Seconds</b> after Rune Attack",
    List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.RuneAttack), Some(ValueRange(1, 30, ValueType.Duration))))
  )
  
  val uniqueCorpseSmearedBlade1 = Enchantment("", EnchantGroup.Other, "enchantment.unique.corpseSmearedBlade1",
    "[StatLifesteal] <b>Lifesteal</b> increased by {0} while <b>Tainted</b>",
    List(StatModifier(StatType.Lifesteal, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.Tainted)))
  )
  
  val uniqueCorpseSmearedBlade2 = Enchantment("", EnchantGroup.Other, "enchantment.unique.corpseSmearedBlade2",
    "[StatMaxStamina] <b>Max Stamina</b> increased by {0} while <b>Tainted</b>",
    List(StatModifier(StatType.MaxStamina, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.Tainted)))
  )
  
  val uniqueCorpseSmearedBlade3 = Enchantment("", EnchantGroup.Other, "enchantment.unique.corpseSmearedBlade3",
    "[StatHealthRegen] <b>Drain Health</b> while <b>Tainted</b>",
    List(StatModifier(StatType.HealthRegen, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StatusEffectCondition(StatusEffect.Tainted)))
  )
  
  val uniqueDamageAgainstBeasts = Enchantment("", EnchantGroup.Other, "enchantment.unique.damageAgainstBeasts",
    "<b>Damage</b> increased by {0} against Beasts",
    List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, EnemyCondition(EnemyType.Beasts)))
  )
  
  val uniqueGainFocusOnDamageTaken = Enchantment("", EnchantGroup.Other, "enchantment.unique.gainFocusOnDamageTaken",
    "Gain <b>{0} Focus</b> on Damage Taken",
    List(ResourceGain(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.DamageTaken)))
  )
  
  val uniqueGlimmeringBulwark0 = Enchantment("", EnchantGroup.Other, "enchantment.unique.glimmeringBulwark0",
    "Gain <b>Enlightment</b> for {0} seconds after Parry",
    List(StatModifier(StatType.Enlightment, ValueRange(1, 1, ValueType.Flat), ModificationType.Increase, ActionCondition(ActionType.Parry), Some(ValueRange(1, 30, ValueType.Duration))))
  )
  
  val uniqueGlimmeringBulwark2 = Enchantment("", EnchantGroup.Other, "enchantment.unique.glimmeringBulwark2",
    "[StatFocusGain] <b>Focus Gain</b> increased by {0} while <b>Enlightened</b>",
    List(StatModifier(StatType.FocusGain, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.Enlightened)))
  )
  
  val uniqueHealthLossOnDamageDealt = Enchantment("", EnchantGroup.Other, "enchantment.unique.healthLossOnDamageDealt",
    "Lose <b>{0} Health</b> on Damage Dealt",
    List(ResourceLoss(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.DamageDealt)))
  )
  
  val uniqueTarnishedWand1 = Enchantment("", EnchantGroup.Other, "enchantment.unique.tarnishedWand1",
    "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} while <b>Enlightened</b>",
    List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StatusEffectCondition(StatusEffect.Enlightened)))
  )
  
  val uniqueTarnishedWand2 = Enchantment("", EnchantGroup.Other, "enchantment.unique.tarnishedWand2",
    "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} while <b>Enlightened</b>",
    List(StatModifier(StatType.DamageTaken, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StatusEffectCondition(StatusEffect.Enlightened)))
  )
  
  val uniqueTheDawnNeverCome2 = Enchantment("", EnchantGroup.Other, "enchantment.unique.theDawnNeverCome2",
    "[StatLifesteal] <b>Lifesteal</b> increased by {0} on <b>Second Toll</b>",
    List(StatModifier(StatType.Lifesteal, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.SecondToll)))
  )
  
  val uniqueTheDawnNeverCome3 = Enchantment("", EnchantGroup.Other, "enchantment.unique.theDawnNeverCome3",
    "[StatDamageTaken] <b>Damage Taken</b> increased by {0} on <b>Third Toll</b>",
    List(StatModifier(StatType.DamageTaken, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.ThirdToll)))
  )
  
  val uniqueTheDawnNeverComes0 = Enchantment("", EnchantGroup.Other, "enchantment.unique.theDawnNeverComes0",
    "Gain One of <b>Three Tolls</b> for {0} seconds after Damage Taken",
    List(StatModifier(StatType.Damage, ValueRange(1, 30, ValueType.Duration), ModificationType.Increase, ActionCondition(ActionType.DamageTaken)))
  )
  
  val uniqueWeaponsBleedersDelightChanceStaggerDamageOnDamageDealt = Enchantment("", EnchantGroup.Other, "enchantment.unique.weapons.bleedersDelight.chanceStaggerDamageOnDamageDealt",
    "Chance to deal Extra <b>Stagger Damage</b> on Damage Dealt",
    List(ChanceEffect(ValueRange(0, 100, ValueType.Chance), StatModifier(StatType.StaggerDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.DamageDealt))))
  )
}

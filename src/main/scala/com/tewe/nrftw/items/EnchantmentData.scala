package com.tewe.nrftw

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
      MaxStamina,
      FocusGain,
      FocusCost,
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
      BurnBuildup
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
      EnemyStaggerResistance

  enum ElementType:
    case Fire, Ice, Lightning, Plague, Physical

  enum StatusEffect:
    case Frozen, Infected, Shocked, Burned, Tainted, Enlightened, FirstToll, SecondToll, ThirdToll

  enum EnemyType:
    case Large, LowHealth, Staggered, Beasts, Nearby, Frozen, Infected

  enum ActionType:
    case RuneAttack, Kill, Parry, Backstab, Block, DamageDealt, DamageTaken, Afflicted, Dodged, Fatality
    case Stagger, Sprint, ChargedAttack, FocusUse, ConsumeFood, NormalAttack, DurabilityLoss, BombConsumed, Explosion
    case FireDamage, LightningDamage, IceDamage, Damage, Execute

  enum ConditionType:
    case FullFocus, HighFocus, LowFocus, FullHealth, LowHealth, FullStamina, LowStamina
    case NotFullFocus, Blocking, Charging, InCombat, Sprinting, NoEnemiesNearby

  enum ValueType:
    case Percentage, Flat, Duration, Chance

  sealed trait Condition
  case object NoCondition extends Condition
  case class ActionCondition(action: ActionType) extends Condition
  case class StateCondition(state: ConditionType) extends Condition
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
    action: ActionType, condition: Condition, chance: Option[ValueRange] = None) extends EnchantmentEffect
  case class ActionModifier(
    action: ActionType, modificationType: ModificationType, chance: ValueRange) extends EnchantmentEffect
  case class ElementalDamage(
    element: ElementType, value: EffectValue, condition: Condition) extends EnchantmentEffect
  case class ElementalInfusion(
    element: ElementType) extends EnchantmentEffect
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
    textCode: String,
    rawText: String,
    effects: List[EnchantmentEffect],
  )

  val allEnchantments = List(
    Enchantment(
      "enchantment.armorDecreased",
      "[StatArmor] <b>Armor</b> decreased by {0}",
      List(StatModifier(StatType.Armor, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.armorIncreased",
      "[StatArmor] <b>Armor</b> increased by {0}",
      List(StatModifier(StatType.Armor, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.attackDamageDecreased",
      "[StatDamage] <b>Attack Damage</b> decreased by {0}",
      List(StatModifier(StatType.Damage, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.attackDamageIncreased",
      "[StatDamage] <b>Attack Damage</b> increased by {0}",
      List(StatModifier(StatType.Damage, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.attackDamageIncreasedAfterRuneAttack",
      "[StatDamage] <b>Attack Damage</b> increased by {0} for {1} seconds after Rune Attack",
      List(StatModifier(StatType.Damage, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.RuneAttack), duration = Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.attackSpeedIncreased",
      "[StatAttackSpeed] <b>Attack Speed</b> increased by {0}",
      List(StatModifier(StatType.AttackSpeed, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase)),
    ),
    
    Enchantment(
      "enchantment.attackSpeedIncreasedAfterKill",
      "[StatAttackSpeed] <b>Attack Speed</b> increased by {1} for <b>{0} Seconds</b> after Kill",
      List(StatModifier(StatType.AttackSpeed, ValueRange(1, 100, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Kill), Some(ValueRange(1, 10, ValueType.Percentage))))
    ),
    
    Enchantment(
      "enchantment.attackStaminaCostDecreased",
      "[StatStaminaCost] <b>Attack Stamina Cost</b> decreased by {0}",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.attackStaminaCostDecreasedAfterParry",
      "[StatStaminaCost] <b>Attack Stamina Cost</b> decreased by {0} for {1} seconds after Parry",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, ActionCondition(ActionType.Parry), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.attackStaminaCostIncreased",
      "[StatStaminaCost] <b>Attack Stamina Cost</b> increased by {0}",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.blockingDisabled",
      "<b>Block</b> disabled",
      List(BlockingDisabled)
    ),
    
    Enchantment(
      "enchantment.bombDamageIncreased",
      "<b>Bomb Damage</b> increased by {0}",
      List(StatModifier(StatType.BombDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.buildupResistance",
      "[StatElementalDamageTaken] <b>Buildup Resistance</b> increased by {0}",
      List(StatModifier(StatType.BuildupResistance, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.burnBuildupIncreased",
      "[StatFireDamage] [!sFire]Burn Buildup[/s] increased by {0}",
      List(StatModifier(StatType.BurnBuildup, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.burnDamageIncreased",
      "[StatFireDamage] [!sFire]Burn Damage[/s] increased by {0}",
      List(StatModifier(StatType.FireDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.chanceToIngoreDurabilityLoss",
      "[StatDurability] <b>Durability Loss</b> ignored with {0} chance",
      List(ActionModifier(ActionType.DurabilityLoss, ModificationType.Ignore, ValueRange(0, 100, ValueType.Percentage)))
    ),
    
    Enchantment(
      "enchantment.chanceToNotConsumeBomb",
      "<b>Bomb not consumed</b> with {0} chance",
      List(ActionModifier(ActionType.BombConsumed, ModificationType.Ignore, ValueRange(0, 100, ValueType.Percentage)))
    ),
    
    Enchantment(
      "enchantment.chanceToSpawnExplosionOnRuneAttack",
      "Deal {0} Explosion [StatFireDamage] [!sFire]Damage[/s] on Rune Attack with {1} chance",
      List(ActionTrigger(ActionType.Explosion, ActionCondition(ActionType.RuneAttack), Some(ValueRange(0, 100, ValueType.Percentage))))
    ),
    
    Enchantment(
      "enchantment.chargedDamageIncreased",
      "[StatDamage] <b>Charged Attack Damage</b> increased by {0}",
      List(StatModifier(StatType.ChargedAttackDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ChargedAttack)))
    ),
    
    Enchantment(
      "enchantment.coldDamageIncreasedAfterHeatDamageDealt",
      "[StatColdDamage] [!sIce]Ice Damage[/s] increased by {0} for {1} seconds after [!sFire]Fire Damage[/s] Dealt",
      List(StatModifier(StatType.ColdDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, AndCondition(ActionCondition(ActionType.DamageDealt), ElementCondition(ElementType.Fire)), Some(ValueRange(1, 30, ValueType.Duration)))),
    ),
    
    Enchantment(
      "enchantment.coldResistanceIncreased",
      "[StatColdArmor] <b>Ice Resistance</b> increased by {0}",
      List(StatModifier(StatType.ColdArmor, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.damageDecreasedAfterParryOrDodged",
      "[StatDamage] <b>Damage</b> decreased by {0} for {1} seconds after Parry or Damage Dodged",
      List(StatModifier(StatType.Damage, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase, OrCondition(ActionCondition(ActionType.Parry), ActionCondition(ActionType.Dodged)), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageIncreased",
      "[StatDamage] <b>Damage</b> increased by {0}",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAfterBackstab",
      "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Backstab",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Backstab), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAfterBlock",
      "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Block",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Block), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAfterDamageTaken",
      "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Damage Taken",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.DamageTaken), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAfterDodged",
      "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Damage Dodged",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Dodged), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAfterFatality",
      "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Fatality",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Fatality), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAfterParry",
      "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Parry",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Parry), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAfterSprint",
      "[StatDamage] <b>Damage</b> increased by {0} after Sprinting for {1} seconds",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.Sprinting), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAfterStagger",
      "[StatDamage] <b>Damage</b> increased by {0} for {1} seconds after Staggering an Enemy",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Stagger), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAgainstLargeEnemies",
      "[StatDamage] <b>Damage</b> increased by {0} against Large Enemies",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, EnemyCondition(EnemyType.Large)))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAgainstLowHealth",
      "[StatDamage] <b>Damage</b> increased by {0} against Low Health Enemies",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, EnemyCondition(EnemyType.LowHealth)))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAtFullFocus",
      "[StatDamage] <b>Damage</b> increased by {0} at Full Focus",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.FullFocus)))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAtHighFocus",
      "[StatDamage] <b>Damage</b> increased by {0} at High Focus",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.HighFocus)))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedAtLowHealth",
      "[StatDamage] <b>Damage</b> increased by {0} at Low Health",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.LowHealth)))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedPerNearbyEnemy",
      "[StatDamage] <b>Damage</b> increased by {0} for each Nearby Enemy",
      List(StatModifier(StatType.Damage, ValueRange(0, 50, ValueType.Percentage), ModificationType.Increase, EnemyCondition(EnemyType.Nearby)))
    ),
    
    Enchantment(
      "enchantment.damageIncreasedWhenNoNearbyEnemy",
      "[StatDamage] <b>Damage</b> increased by {0} if no Enemies nearby",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.NoEnemiesNearby)))
    ),
    
    Enchantment(
      "enchantment.damageScalesWithCurrentFocus",
      "[StatDamage] <b>Damage</b> increased by {0} of [StatFocus] Current Focus",
      List(StatModifier(StatType.Damage, ScalingValue(ValueRange(0, 200, ValueType.Percentage), StatType.CurrentFocus), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.damageScalesWithDurability",
      "[StatDamage] <b>Damage</b> increased by up to {0} based on Durability",
      List(StatModifier(StatType.Damage, ScalingValue(ValueRange(0, 200, ValueType.Percentage), StatType.Durability), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.damageScalesWithMissingHealth",
      "[StatDamage] <b>Damage</b> increased by up to {0} based on Missing Health",
      List(StatModifier(StatType.Damage, ScalingValue(ValueRange(0, 200, ValueType.Percentage), StatType.MissingHealth), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.damageTakenDecreasedAfterBuildupTaken",
      "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} for {1} seconds after being afflicted by Status",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, ActionCondition(ActionType.Afflicted), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageTakenDecreasedAtFullStamina",
      "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} at Full Stamina",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.FullStamina)))
    ),
    
    Enchantment(
      "enchantment.damageTakenDecreasedPerNearbyEnemy",
      "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} for each Nearby Enemy",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 20, ValueType.Percentage), ModificationType.Decrease, EnemyCondition(EnemyType.Nearby)))
    ),
    
    Enchantment(
      "enchantment.damageTakenDecreasedWhileBlocking",
      "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} while Blocking",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.Blocking)))
    ),
    
    Enchantment(
      "enchantment.damageTakenDecreasedWhileCharging",
      "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} while Charging",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.Charging)))
    ),
    
    Enchantment(
      "enchantment.damageTakenIncreasedAfterConsumeFood",
      "[StatDamageTaken] <b>Damage Taken</b> increased by {0} for {1} seconds after Consuming Food",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ConsumeFood), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.damageTakenIncreasedAtLowStamina",
      "[StatDamageTaken] <b>Damage Taken</b> increased by {0} at Low Stamina",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.LowStamina)))
    ),
    
    Enchantment(
      "enchantment.damageTakenIncreasedIfNotFullFocus",
      "[StatDamageTaken] <b>Damage Taken</b> increased by {0} if not at Full Focus",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.NotFullFocus)))
    ),
    
    Enchantment(
      "enchantment.damageTakenScalesWithDurability",
      "[StatDamageTaken] <b>Damage Taken</b> decreased by up to {0} based on Durability",
      List(StatModifier(StatType.DamageTaken, ScalingValue(ValueRange(0, 200, ValueType.Percentage), StatType.Durability), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.dealColdDamageOnBlock",
      "Deal {0} [StatColdDamage] [!sIce]Ice Damage[/s] on Block",
      List(ActionTrigger(ActionType.IceDamage, ActionCondition(ActionType.Block)))
    ),
    
    Enchantment(
      "enchantment.dealDamageOnBlock",
      "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Block",
      List(ActionTrigger(ActionType.Damage, ActionCondition(ActionType.Block)))
    ),
    
    Enchantment(
      "enchantment.dealDamageOnDamageTaken",
      "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Damage Taken",
      List(ActionTrigger(ActionType.Damage, ActionCondition(ActionType.DamageTaken)))
    ),
    
    Enchantment(
      "enchantment.dealDamageOnDodged",
      "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Damage Dodged",
      List(ActionTrigger(ActionType.Damage, ActionCondition(ActionType.DamageDodged)))
    ),
    
    Enchantment(
      "enchantment.dealDamageOnParry",
      "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Parry",
      List(ActionTrigger(ActionType.Damage, ActionCondition(ActionType.Parry)))
    ),
    
    Enchantment(
      "enchantment.dealDamageOnStagger",
      "Deal {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Staggering an Enemy",
      List(ActionTrigger(ActionType.Damage, ActionCondition(ActionType.Stagger)))
    ),
    
    Enchantment(
      "enchantment.dealElectricDamageOnBlock",
      "Deal {0} [StatLightningDamage] [!sLightning]Lightning Damage[/s] on Block",
      List(ActionTrigger(ActionType.LightningDamage, ActionCondition(ActionType.Block)))
    ),
    
    Enchantment(
      "enchantment.dealHeatDamageOnBlock",
      "Deal {0} [StatFireDamage] [!sFire]Fire Damage[/s] on Block",
      List(ActionTrigger(ActionType.FireDamage, ActionCondition(ActionType.Block)))
    ),
    
    Enchantment(
      "enchantment.dealHeatDamageOnRuneAttack",
      "Deal {0} [StatFireDamage] [!sFire]Fire Damage[/s] on Rune Attack",
      List(ActionTrigger(ActionType.FireDamage, ActionCondition(ActionType.RuneAttack)))
    ),
    
    Enchantment(
      "enchantment.delayedHealing",
      "<b>Food Healing</b> is applied over time",
      List(DelayedHealing)
    ),
    
    Enchantment(
      "enchantment.dodgeStaminaCostDecreasedAtLowHealth",
      "[StatStaminaCost] <b>Dodge Stamina Cost</b> decreased by {0} at Low Health",
      List(StatModifier(StatType.DodgeStaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowHealth)))
    ),
    
    Enchantment(
      "enchantment.dodgeStaminaCostIncreased",
      "[StatStaminaCost] <b>Dodge Stamina Cost</b> increased by {0}",
      List(StatModifier(StatType.DodgeStaminaCost, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.drainFocusInCombat",
      "[StatFocusRegen] <b>Drain Focus</b> in Combat",
      List(DrainFocusInCombat)
    ),
    
    Enchantment(
      "enchantment.drainHealthInCombat",
      "[StatHealthRegen] <b>Drain Health</b> in Combat",
      List(DrainHealthInCombat)
    ),
    
    Enchantment(
      "enchantment.durabilityIncreased",
      "Item [StatDurability] <b>Durability</b> increased by {0}",
      List(StatModifier(StatType.Durability, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.elementalDamageTakenDecreased",
      "[StatElementalDamageTaken] <b>Elemental Damage Taken</b> decreased by {0}",
      List(StatModifier(StatType.ElementalDamageTaken, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.endlessClimbing",
      "<b>Endless Climbing</b>",
      List(EndlessClimbing)
    ),
    
    Enchantment(
      "enchantment.enemyArmorDecreasedAfterFrost",
      "[!sIce]Frozen[/s] Enemy [StatArmor] <b>Armor</b> decreased by {0}",
      List(StatModifier(StatType.EnemyArmor, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, EnemyCondition(EnemyType.Frozen))),
    ),
    
    Enchantment(
      "enchantment.enemyStaggerResistanceDecreasedAfterInfection",
      "[!sPlague]Infected[/s] Enemy [StatStaggerResistance] <b>Stagger Resistance</b> decreased by {0}",
      List(StatModifier(StatType.EnemyStaggerResistance, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, EnemyCondition(EnemyType.Infected)))
    ),
    
    Enchantment(
      "enchantment.equipLoadDecreased",
      "[StatEquipLoad] <b>Equip Load</b> decreased by {0}",
      List(StatModifier(StatType.EquipLoad, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.equipLoadIncreased",
      "[StatEquipLoad] <b>Equip Load</b> increased by {0}",
      List(StatModifier(StatType.EquipLoad, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.executeAgainstInfectedLowHealth",
      "Execute [!sPlague]Infected[/s] Low Health Enemies on Damage Dealt",
      List(ActionTrigger(ActionType.Execute, AndCondition(StatusEffectCondition(StatusEffect.Infected), EnemyCondition(EnemyType.LowHealth))))
    ),
    
    Enchantment(
      "enchantment.experienceGainIncreased",
      "[StatExperience] <b>Experience Gain</b> increased by {0}",
      List(StatModifier(StatType.ExperienceGain, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.fireDamageIncreased",
      "[StatFireDamage] [!sFire]Fire Damage[/s] increased by {0}",
      List(StatModifier(StatType.FireDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.fireResistanceIncreased",
      "[StatFireArmor] <b>Fire Resistance</b> increased by {0}",
      List(StatModifier(StatType.FireArmor, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.focusCostDecreased",
      "[StatFocusGain] <b>Focus Cost</b> decreased by {0}",
      List(StatModifier(StatType.FocusCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.focusCostDecreasedAfterRuneAttack",
      "[StatFocusCost] <b>Focus Cost</b> decreased by {0} for {1} seconds after Rune Attackm",
      List(StatModifier(StatType.FocusCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, ActionCondition(ActionType.RuneAttack), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.focusCostDecreasedAfterStagger",
      "[StatFocusCost] <b>Focus Cost</b> decreased by {0} for {1} seconds after Staggering an Enemy",
      List(StatModifier(StatType.FocusCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, ActionCondition(ActionType.Stagger), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.focusCostDecreasedAtLowHealth",
      "[StatFocusGain] <b>Focus Cost</b> decreased by {0} at Low Health",
      List(StatModifier(StatType.FocusCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowHealth)))
    ),
    
    Enchantment(
      "enchantment.focusCostIncreased",
      "[StatFocusGain] <b>Focus Cost</b> increased by {0}",
      List(StatModifier(StatType.FocusCost, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.focusCostScalesWithMissingHealth",
      "[StatFocusGain] <b>Focus Cost</b> decreased by up to {0} based on Missing Health",
      List(ScalingValue(ValueRange(0, 100, ValueType.Percentage), "MissingHealth"))
    ),
    
    Enchantment(
      "enchantment.focusGainDecreased",
      "[StatFocusGain] <b>Focus Gain</b> decreased by {0}",
      List(StatModifier(StatType.FocusGain, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.focusGainIncreased",
      "[StatFocusGain] <b>Focus Gain</b> increased by {0}",
      List(StatModifier(StatType.FocusGain, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.focusGainScalesWithDurability",
      "[StatFocusGain] <b>Focus Gain</b> increased by up to {0} based on Durability",
      List(ScalingValue(ValueRange(0, 200, ValueType.Percentage), "Durability"))
    ),
    
    Enchantment(
      "enchantment.gainFocusOnBackstab",
      "Gain {0} [StatFocus] <b>Focus</b> on Backstab",
      List(ConditionalResourceGain(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Backstab)))
    ),
    
    Enchantment(
      "enchantment.gainFocusOnBuildup",
      "Gain {0} [StatFocus] <b>Focus</b> on inflicting Any Status",
      List(ConditionalResourceGain(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.DamageDealt)))
    ),
    
    Enchantment(
      "enchantment.gainFocusOnDodged",
      "Gain {0} [StatFocus] <b>Focus</b> on Damage Dodged",
      List(ConditionalResourceGain(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Dodged)))
    ),
    
    Enchantment(
      "enchantment.gainFocusOnFatality",
      "Gain {0} [StatFocus] <b>Focus</b> on Fatality",
      List(ConditionalResourceGain(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Fatality)))
    ),
    
    Enchantment(
      "enchantment.gainFocusOnKill",
      "Gain {0} [StatFocus] <b>Focus</b> on Kill",
      List(ConditionalResourceGain(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Kill)))
    ),
    
    Enchantment(
      "enchantment.gainFocusOnParry",
      "Gain {0} [StatFocus] <b>Focus</b> on Parry",
      List(ConditionalResourceGain(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Parry)))
    ),
    
    Enchantment(
      "enchantment.gainFocusOnStagger",
      "Gain {0} [StatFocus] <b>Focus</b> on Staggering an Enemy",
      List(ConditionalResourceGain(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Stagger)))
    ),
    
    Enchantment(
      "enchantment.gainHealthForFocusSpent",
      "Gain [StatHealth] <b>Health</b> equal to {0} of Focus Spent",
      List(ResourceGain(StatType.Health, SimpleValue(ValueRange(0, 100, ValueType.Percentage))))
    ),
    
    Enchantment(
      "enchantment.gainHealthOnBackstab",
      "Gain {0} [StatHealth] <b>Health</b> on Backstab",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Backstab)))
    ),
    
    Enchantment(
      "enchantment.gainHealthOnChargedAttack",
      "Gain {0} [StatHealth] <b>Health</b> on Charged Attack",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.ChargedAttack)))
    ),
    
    Enchantment(
      "enchantment.gainHealthOnDamageDealtAgainstFrozen",
      "Gain {0} [StatHealth] <b>Health</b> on Damage Dealt against [!sIce]Frozen[/s] Enemies",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat)), AndCondition(ActionCondition(ActionType.DamageDealt, StatusEffectCondition(StatusEffect.Frozen))))
    ),
    
    Enchantment(
      "enchantment.gainHealthOnDamageDealtAtLowHealth",
      "Gain {0} [StatHealth] <b>Health</b> on Damage Dealt at Low Health",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat)), AndCondition(ActionCondition(ActionType.DamageDealt, StateCondition(ConditionType.LowHealth))))
    ),
    
    Enchantment(
      "enchantment.gainHealthOnDodged",
      "Gain {0} [StatHealth] <b>Health</b> on Damage Dodged",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Dodged)))
    ),
    
    Enchantment(
      "enchantment.gainHealthOnFatality",
      "Gain {0} [StatHealth] <b>Health</b> on Fatality",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Fatality)))
    ),
    
    Enchantment(
      "enchantment.gainHealthOnKill",
      "Gain {0} [StatHealth] <b>Health</b> on Kill",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Kill)))
    ),
    
    Enchantment(
      "enchantment.gainHealthOnKillAgainstStaggeredEnemies",
      "Gain {0} [StatHealth] <b>Health</b> on Kill against Staggered Enemies",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat)), AndCondition(ActionCondition(ActionType.Kill, EnemyCondition(EnemyType.Staggered))))
    ),
    
    Enchantment(
      "enchantment.gainHealthOnParry",
      "Gain {0} [StatHealth] <b>Health</b> on Parry",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Parry)))
    ),
    
    Enchantment(
      "enchantment.gainHealthOnStagger",
      "Gain {0} [StatHealth] <b>Health</b> on Staggering an Enemy",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Stagger)))
    ),
    
    Enchantment(
      "enchantment.gainHealthOncePerRuneAttack",
      "Gain {0} [StatHealth] <b>Health</b> once per Rune Attack",
      List(ConditionalResourceGain(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.RuneAttack)))
    ),
    
    Enchantment(
      "enchantment.gainStaminaOnBlock",
      "Gain {0} [StatStamina] <b>Stamina</b> on Block",
      List(ConditionalResourceGain(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Block)))
    ),
    
    Enchantment(
      "enchantment.gainStaminaOnDamageDealt",
      "Gain {0} [StatStamina] <b>Stamina</b> on Damage Dealt",
      List(ConditionalResourceGain(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.DamageDealt)))
    ),
    
    Enchantment(
      "enchantment.gainStaminaOnDodged",
      "Gain {0} [StatStamina] <b>Stamina</b> on Damage Dodged",
      List(ConditionalResourceGain(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Dodged)))
    ),
    
    Enchantment(
      "enchantment.gainStaminaOnFocusUse",
      "Gain {0} [StatStamina] <b>Stamina</b> on Focus Use",
      List(ConditionalResourceGain(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.FocusUse)))
    ),
    
    Enchantment(
      "enchantment.gainStaminaOnParry",
      "Gain {0} [StatStamina] <b>Stamina</b> on Parry",
      List(ConditionalResourceGain(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Parry)))
    ),
    
    Enchantment(
      "enchantment.gainStaminaOnStagger",
      "Gain {0} [StatStamina] <b>Stamina</b> on Staggering an Enemy",
      List(ConditionalResourceGain(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Stagger)))
    ),
    
    Enchantment(
      "enchantment.healingDecreased",
      "[StatHealing] <b>Healing</b> decreased by {0}",
      List(StatModifier(StatType.Healing, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.healingDecreasedPerNearbyEnemy",
      "[StatHealing] <b>Healing</b> decreased by {0} for each Nearby Enemy",
      List(StatModifier(StatType.Healing, ValueRange(0, 50, ValueType.Percentage), ModificationType.Decrease, EnemyCondition(EnemyType.Nearby)))
    ),
    
    Enchantment(
      "enchantment.healingIncreased",
      "[StatHealing] <b>Healing</b> increased by {0}",
      List(StatModifier(StatType.Healing, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.healingIncreasedAtLowFocus",
      "[StatHealing] <b>Healing</b> increased by {0} at Low Focus",
      List(StatModifier(StatType.Healing, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.LowFocus)))
    ),
    
    Enchantment(
      "enchantment.healthRallyIncreased",
      "[StatHealthRally] <b>Regainable Health</b> increased by {0}",
      List(StatModifier(StatType.HealthRally, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.heatDamageIncreasedAfterColdDamageDealt",
      "[StatFireDamage] [!sFire]Fire Damage[/s] increased by {0} for {1} seconds after [!sIce]Ice Damage[/s] Dealt",
      List(StatModifier(StatType.FireDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.DamageDealt), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.hungerIncreased",
      "<b>Hunger Limit</b> increased by {0}",
      List(StatModifier(StatType.Health, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.indestructible",
      "<b>Indestructible</b>",
      List(Indestructible)
    ),
    
    Enchantment(
      "enchantment.infectionBuildupIncreased",
      "[StatPlagueDamage] [!sPlague]Infection Buildup[/s] increased by {0}",
      List(StatModifier(StatType.PlagueDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.itemWeightDecreased",
      "Item [StatWeight] <b>Weight</b> decreased by {0}",
      List(StatModifier(StatType.Weight, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.itemWeightIncreased",
      "Item [StatWeight] <b>Weight</b> increased by {0}",
      List(StatModifier(StatType.Weight, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.knockbackResistanceIncreased",
      "<b>Knockback Resistance</b> increased by {0}",
      List(StatModifier(StatType.Armor, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.lifestealDisabled",
      "Cannot [StatLifesteal] <b>Lifesteal</b>",
      List(LifestealDisabled)
    ),
    
    Enchantment(
      "enchantment.lifestealIncreased",
      "[StatLifesteal] <b>Lifesteal</b> increased by {0}",
      List(StatModifier(StatType.Lifesteal, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.lifestealIncreasedAtLowHealth",
      "[StatLifesteal] <b>Lifesteal</b> increased by {0} at Low Health",
      List(StatModifier(StatType.Lifesteal, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.LowHealth)))
    ),
    
    Enchantment(
      "enchantment.lightningDamageIncreased",
      "[StatLightningDamage] [!sLightning]Lightning Damage[/s] increased by {0}",
      List(StatModifier(StatType.LightningDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.lightningDamageOverride",
      "[StatLightningDamage] [!sLightning]Lightning Damage[/s] Infusion",
      List(ElementalInfusion(ElementType.Lightning))
    ),
    
    Enchantment(
      "enchantment.lightningDamageOverrideAfterSprint",
      "[StatLightningDamage] [!sLightning]Lightning Damage[/s] Infusion after Sprinting for {0} seconds",
      List(ConditionalElementalInfusion(ElementType.Lightning, StateCondition(ConditionType.Sprinting)))
    ),
    
    Enchantment(
      "enchantment.lightningResistanceIncreased",
      "[StatLightningArmor] <b>Lightning Resistance</b> increased by {0}",
      List(StatModifier(StatType.LightningArmor, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.loseFocusOnDamageTaken",
      "Lose {0} [StatFocus] <b>Focus</b> on Damage Taken",
      List(ConditionalResourceLoss(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.DamageTaken)))
    ),
    
    Enchantment(
      "enchantment.loseFocusOnFocusUse",
      "Lose {0} [StatFocus] <b>Focus</b> on Focus Use",
      List(ConditionalResourceLoss(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.FocusUse)))
    ),
    
    Enchantment(
      "enchantment.loseStaminaOnBlock",
      "Lose {0} [StatStamina] <b>Stamina</b> on Block",
      List(ConditionalResourceLoss(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.Block)))
    ),
    
    Enchantment(
      "enchantment.loseStaminaOnDamageTaken",
      "Lose {0} [StatStamina] <b>Stamina</b> on Damage Taken",
      List(ConditionalResourceLoss(StatType.Stamina, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.DamageTaken)))
    ),
    
    Enchantment(
      "enchantment.maxFocusDecreased",
      "[StatMaxFocus] <b>Max Focus</b> decreased by {0}",
      List(StatModifier(StatType.MaxFocus, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.maxFocusIncreased",
      "[StatMaxFocus] <b>Max Focus</b> increased by {0}",
      List(StatModifier(StatType.MaxFocus, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.maxStaminaDecreased",
      "[StatMaxStamina] <b>Max Stamina</b> decreased by {0}",
      List(StatModifier(StatType.MaxStamina, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.maxStaminaScalesWithMaxFocus",
      "[StatMaxStamina] <b>Max Stamina</b> increased by {0}% of Max Focus",
      List(ScalingValue(ValueRange(0, 200, ValueType.Percentage), "MaxFocus"))
    ),
    
    Enchantment(
      "enchantment.movementSpeedDecreased",
      "[StatMovementSpeed] <b>Movement Speed</b> decreased by {0}",
      List(StatModifier(StatType.MovementSpeed, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.movementSpeedDecreasedPerNearbyEnemy",
      "[StatMovementSpeed] <b>Movement Speed</b> decreased by {0} per Nearby Enemy",
      List(StatModifier(StatType.MovementSpeed, ValueRange(0, 30, ValueType.Percentage), ModificationType.Decrease, EnemyCondition(EnemyType.Nearby)))
    ),
    
    Enchantment(
      "enchantment.movementSpeedIncreased",
      "[StatMovementSpeed] <b>Movement Speed</b> increased by {0}",
      List(StatModifier(StatType.MovementSpeed, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.movementSpeedIncreasedAfterChargedAttack",
      "[StatMovementSpeed] <b>Movement Speed</b> increased by {0} for {1} seconds after Charged Attack",
      List(StatModifier(StatType.MovementSpeed, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ChargedAttack), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.movementSpeedIncreasedAfterKill",
      "[StatMovementSpeed] <b>Movement Speed</b> increased by {0} for {1} seconds after Kill",
      List(StatModifier(StatType.MovementSpeed, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Kill), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.normalDamageIncreasedAfterChargedAttack",
      "[StatDamage] <b>Normal Attack Damage</b> increased by {0} for {1} seconds after Charged Attack",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ChargedAttack), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.onlyHeavyRoll",
      "<b>Heavy Roll</b> only",
      List(OnlyHeavyRoll)
    ),
    
    Enchantment(
      "enchantment.parryDisabled",
      "<b>Parry</b> disabled",
      List(ParryDisabled)
    ),
    
    Enchantment(
      "enchantment.parryStaminaCostDecreased",
      "[StatStaminaCost] <b>Parry Stamina Cost</b> decreased by {0}",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.physicalDamageIncreased",
      "[StatPhysicalDamage] [!sPhysical]Physical Damage[/s] increased by {0}",
      List(StatModifier(StatType.PhysicalDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.physicalDamageTakenDecreased",
      "[StatDamageTaken] <b>Physical Damage Taken</b> decreased by {0}",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.plagueDamageIncreased",
      "[StatPlagueDamage] [!sPlague]Plague Damage[/s] increased by {0}",
      List(StatModifier(StatType.PlagueDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.plagueDamageOverride",
      "[StatPlagueDamage] [!sPlague]Plague Damage[/s] Infusion",
      List(ElementalInfusion(ElementType.Plague))
    ),
    
    Enchantment(
      "enchantment.plagueResistanceIncreased",
      "[StatPlagueArmor] <b>Plague Resistance</b> increased by {0}",
      List(StatModifier(StatType.PlagueArmor, ValueRange(0, 100, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.poiseDefenseIncreased",
      "[StatPoiseDefense] <b>Poise Defense</b> increased by {0}",
      List(StatModifier(StatType.PoiseDefense, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.poiseDefenseIncreasedWhileBlocking",
      "[StatPoiseDefense] <b>Poise Defense</b> increased by {0} while Blocking",
      List(StatModifier(StatType.PoiseDefense, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.Blocking)))
    ),
    
    Enchantment(
      "enchantment.poiseDefenseIncreasedWhileCharging",
      "[StatPoiseDefense] <b>Poise Defense</b> increased by {0} while Charging",
      List(StatModifier(StatType.PoiseDefense, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.Charging)))
    ),
    
    Enchantment(
      "enchantment.refillFocusOnBackstab",
      "Refill [StatFocus] <b>Focus</b> on Backstab",
      List(ConditionalResourceGain(StatType.Focus, ValueRange(100, 100, ValueType.Percentage), ActionCondition(ActionType.Backstab)))
    ),
    
    Enchantment(
      "enchantment.regenFocusInCombat",
      "[StatFocusRegen] <b>Regenerate Focus</b> in Combat",
      List(RegenFocusInCombat)
    ),
    
    Enchantment(
      "enchantment.regenHealthInCombat",
      "[StatHealthRegen] <b>Regenerate Health</b> in Combat",
      List(RegenHealthInCombat)
    ),
    
    Enchantment(
      "enchantment.regenHealthWhileBlocking",
      "[StatHealthRegen] <b>Regenerate Health</b> while Blocking",
      List(StatModifier(StatType.HealthRegen, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.Blocking)))
    ),
    
    Enchantment(
      "enchantment.repairDisabled",
      "<b>Unrepairable</b>",
      List(Unrepairable)
    ),
    
    Enchantment(
      "enchantment.runeDamageIncreased",
      "[StatDamage] <b>Rune Damage</b> increased by {0}",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.shockBuildupIncreased",
      "[StatLightningDamage] [!sLightning]Shock Buildup[/s] increased by {0}",
      List(StatModifier(StatType.LightningDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.shockBuildupIncreasedAfterShock",
      "[StatLightningDamage] [!sLightning]Shock Buildup[/s] increased by {0} for {1} seconds after inflicting [!sLightning]Shock Status[/s]",
      List(StatModifier(StatType.LightningDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.Shocked), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.speedIncreasedAfterShock",
      "[StatAttackSpeed] <b>Overall Speed</b> increased by {0} for {1} seconds after inflicting [!sLightning]Shock Status[/s]",
      List(StatModifier(StatType.AttackSpeed, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.Shocked), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.spendHealthIfThereIsNoFocus",
      "Spend [StatHealth] <b>Health</b> if there is not enough [StatFocus] <b>Focus</b> available",
      List(SpendHealthIfNoFocus)
    ),
    
    Enchantment(
      "enchantment.spendHealthInsteadFocus",
      "Spend [StatHealth] <b>Health</b> instead of [StatFocus] <b>Focus</b>",
      List(SpendHealthInsteadOfFocus)
    ),
    
    Enchantment(
      "enchantment.sprintStaminaCostDecreased",
      "[StatStaminaCost] <b>Sprint Stamina Cost</b> decreased by {0}",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.staggerDamageIncreased",
      "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0}",
      List(StatModifier(StatType.StaggerDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.staggerDamageIncreasedAfterBackstab",
      "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0} for {1} seconds after Backstab",
      List(StatModifier(StatType.StaggerDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Backstab), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.staggerDamageIncreasedAfterChargedAttack",
      "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0} for {1} seconds after Charged Attack",
      List(StatModifier(StatType.StaggerDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.ChargedAttack), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.staggerDamageIncreasedAfterFatality",
      "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0} for {1} seconds after Fatality",
      List(StatModifier(StatType.StaggerDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Fatality), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.staggerDamageIncreasedAfterKill",
      "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0} for {1} seconds after Kill",
      List(StatModifier(StatType.StaggerDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.Kill), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.staggerDamageIncreasedAtLowHealth",
      "[StatStaggerDamage] <b>Stagger Damage</b> increased by {0} at Low Health",
      List(StatModifier(StatType.StaggerDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StateCondition(ConditionType.LowHealth)))
    ),
    
    Enchantment(
      "enchantment.staggerResistanceDecreased",
      "[StatStaggerResistance] <b>Stagger Resistance</b> decreased by {0}",
      List(StatModifier(StatType.StaggerResistance, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.staggerResistanceIncreased",
      "[StatStaggerResistance] <b>Stagger Resistance</b> increased by {0}",
      List(StatModifier(StatType.StaggerResistance, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.staminaCostDecreased",
      "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0}",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease))
    ),
    
    Enchantment(
      "enchantment.staminaCostDecreasedAtFullHealth",
      "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} at Full Health",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.FullHealth)))
    ),
    
    Enchantment(
      "enchantment.staminaCostDecreasedAtHighFocus",
      "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} at High Focus",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.HighFocus)))
    ),
    
    Enchantment(
      "enchantment.staminaCostDecreasedAtLowFocus",
      "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} at Low Focus",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowFocus)))
    ),
    
    Enchantment(
      "enchantment.staminaCostDecreasedAtLowHealth",
      "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} at Low Health",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StateCondition(ConditionType.LowHealth)))
    ),
    
    Enchantment(
      "enchantment.staminaCostIncreased",
      "[StatStaminaCost] <b>Stamina Cost</b> increased by {0}",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.staminaRefillOnParry",
      "Refill [StatStamina] <b>Stamina</b> on Parry",
      List(ConditionalResourceGain(StatType.Stamina, ValueRange(100, 100, ValueType.Percentage), ActionCondition(ActionType.Parry)))
    ),
    
    Enchantment(
      "enchantment.staminaRefillOnStagger",
      "Refill [StatStamina] <b>Stamina</b> on Staggering an Enemy",
      List(ConditionalResourceGain(StatType.Stamina, ValueRange(100, 100, ValueType.Percentage), ActionCondition(ActionType.Stagger)))
    ),
    
    Enchantment(
      "enchantment.staminaRegenerationIncreased",
      "[StatStaminaRegen] <b>Stamina Recovery</b> increased by {0}",
      List(StatModifier(StatType.StaminaRegen, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase))
    ),
    
    Enchantment(
      "enchantment.staminaRegenerationScalesWithDurability",
      "[StatStaminaRegen] <b>Stamina Recovery</b> increased by up to {0} based on Durability",
      List(ScalingValue(ValueRange(0, 200, ValueType.Percentage), "Durability"))
    ),
    
    Enchantment(
      "enchantment.swapMaxHealthAndMaxFocus",
      "Swap <b>Max Health</b> and <b>Max Focus</b>",
      List(SwapStats(StatType.Health, StatType.MaxFocus))
    ),
    
    Enchantment(
      "enchantment.takeDamageOnFocusUse",
      "Take {0} [StatPhysicalDamage] [!sPhysical]Damage[/s] on Focus Use",
      List(ConditionalResourceLoss(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.FocusUse)))
    ),
    
    Enchantment(
      "enchantment.unique.buffSpecialDamageAfterSpecial",
      "<b>Rune Attack Damage</b> increases by {1} for {0} <b>Seconds</b> after Rune Attack",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.RuneAttack), Some(ValueRange(1, 30, ValueType.Duration))))
    ),
    
    Enchantment(
      "enchantment.unique.corpseSmearedBlade1",
      "[StatLifesteal] <b>Lifesteal</b> increased by {0} while <b>Tainted</b>",
      List(StatModifier(StatType.Lifesteal, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.Tainted)))
    ),
    
    Enchantment(
      "enchantment.unique.corpseSmearedBlade2",
      "[StatMaxStamina] <b>Max Stamina</b> increased by {0} while <b>Tainted</b>",
      List(StatModifier(StatType.MaxStamina, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.Tainted)))
    ),
    
    Enchantment(
      "enchantment.unique.corpseSmearedBlade3",
      "[StatHealthRegen] <b>Drain Health</b> while <b>Tainted</b>",
      List(StatModifier(StatType.HealthRegen, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StatusEffectCondition(StatusEffect.Tainted)))
    ),
    
    Enchantment(
      "enchantment.unique.damageAgainstBeasts",
      "<b>Damage</b> increased by {0} against Beasts",
      List(StatModifier(StatType.Damage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, EnemyCondition(EnemyType.Beasts)))
    ),
    
    Enchantment(
      "enchantment.unique.gainFocusOnDamageTaken",
      "Gain <b>{0} Focus</b> on Damage Taken",
      List(ConditionalResourceGain(StatType.Focus, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.DamageTaken)))
    ),
    
    Enchantment(
      "enchantment.unique.glimmeringBulwark0",
      "Gain <b>Enlightment</b> for {0} seconds after Parry",
      List(StatModifier(StatType.Damage, ValueRange(1, 30, ValueType.Duration, ModificationType.Increase, ActionCondition(ActionType.Parry)))
    ),
    
    Enchantment(
      "enchantment.unique.glimmeringBulwark2",
      "[StatFocusGain] <b>Focus Gain</b> increased by {0} while <b>Enlightened</b>",
      List(StatModifier(StatType.FocusGain, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.Enlightened)))
    ),
    
    Enchantment(
      "enchantment.unique.healthLossOnDamageDealt",
      "Lose <b>{0} Health</b> on Damage Dealt",
      List(ConditionalResourceLoss(StatType.Health, ValueRange(1, 100, ValueType.Flat), ActionCondition(ActionType.DamageDealt)))
    ),
    
    Enchantment(
      "enchantment.unique.tarnishedWand1",
      "[StatStaminaCost] <b>Stamina Cost</b> decreased by {0} while <b>Enlightened</b>",
      List(StatModifier(StatType.StaminaCost, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StatusEffectCondition(StatusEffect.Enlightened)))
    ),
    
    Enchantment(
      "enchantment.unique.tarnishedWand2",
      "[StatDamageTaken] <b>Damage Taken</b> decreased by {0} while <b>Enlightened</b>",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 100, ValueType.Percentage), ModificationType.Decrease, StatusEffectCondition(StatusEffect.Enlightened)))
    ),
    
    Enchantment(
      "enchantment.unique.theDawnNeverCome2",
      "[StatLifesteal] <b>Lifesteal</b> increased by {0} on <b>Second Toll</b>",
      List(StatModifier(StatType.Lifesteal, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.SecondToll)))
    ),
    
    Enchantment(
      "enchantment.unique.theDawnNeverCome3",
      "[StatDamageTaken] <b>Damage Taken</b> increased by {0} on <b>Third Toll</b>",
      List(StatModifier(StatType.DamageTaken, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, StatusEffectCondition(StatusEffect.ThirdToll)))
    ),
    
    Enchantment(
      "enchantment.unique.theDawnNeverComes0",
      "Gain One of <b>Three Tolls</b> for {0} seconds after Damage Taken",
      List(StatModifier(StatType.Damage, ValueRange(1, 30, ValueType.Duration), ModificationType.Increase, ActionCondition(ActionType.DamageTaken)))
    ),
    
    Enchantment(
      "enchantment.unique.weapons.bleedersDelight.chanceStaggerDamageOnDamageDealt",
      "Chance to deal Extra <b>Stagger Damage</b> on Damage Dealt",
      List(ChanceEffect(ValueRange(0, 100, ValueType.Chance), StatModifier(StatType.StaggerDamage, ValueRange(0, 200, ValueType.Percentage), ModificationType.Increase, ActionCondition(ActionType.DamageDealt))))
    )
  )
}

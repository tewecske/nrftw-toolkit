package com.tewe.nrftw.items

import com.tewe.nrftw.WeaponType
import com.tewe.nrftw.{FocusCost, HealthCost, Rune, StaminaCost}

// format: off

val runes = List(
  Rune("u_adfl", "Advancing Flurry", "/images/rune-advancingFlurry.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_aido", "Air Dodge", "/images/rune-airDodge.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_arma", "Armageddon", "/images/rune-armageddon.png", FocusCost(150), WeaponType.wandWeapons),
  Rune("u_ar", "Arrow", "/images/rune-arrow.png", FocusCost(10), WeaponType.bowWeapons),
  Rune("u_bash", "Balance Smash", "/images/rune-balanceSmash.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_bakn", "Bare Knuckle", "/images/rune-bareKnuckle.png", FocusCost(100), WeaponType.gauntletWeapons),
  Rune("u_befl", "Berserk Flurry", "/images/rune-berserkFlurry.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_besl", "Berserker Slam", "/images/rune-berserkerSlam.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_best", "Berserker Strike", "/images/rune-berserkerStrike.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_blru", "Blazing Rupture", "/images/rune-blazingRupture.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_bl", "Blink", "/images/rune-blink.png", FocusCost(25), WeaponType.utilities),
  Rune("u_brth", "Breaker Thrust", "/images/rune-breakerThrust.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_br", "Breakrush", "/images/rune-breakrush.png", FocusCost(133), WeaponType.gauntletWeapons),
  Rune("u_brre", "Brutal Reprise", "/images/rune-brutalReprise.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_buri", "Buster Rift", "/images/rune-busterRift.png", FocusCost(125), WeaponType.gauntletWeapons),
  Rune("u_chli", "Chain Lightning", "/images/rune-chainLightning.png", FocusCost(50), WeaponType.staffWeapons),
  Rune("u_ch", "Channel", "/images/rune-channel.png", HealthCost(25), WeaponType.utilities),
  Rune("u_chst", "Charge Strike", "/images/rune-chargeStrike.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_chbo", "Charged Bolt", "/images/rune-chargedBolt.png", FocusCost(50), WeaponType.wandWeapons),
  Rune("u_ci", "Circular", "/images/rune-circular.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_coen", "Cold Enchantment", "/images/rune-coldEnchantment.png", FocusCost(50), WeaponType.utilities),
  Rune("u_cosl", "Cold Sling", "/images/rune-coldSling.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_cosho", "Cone Shot", "/images/rune-coneShot.png", FocusCost(100), WeaponType.bowWeapons),
  Rune("u_cofl", "Converging Flame", "/images/rune-convergingFlame.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_cr", "Crush", "/images/rune-crush.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_crdash", "Crushing Dash", "/images/rune-crushingDash.png", FocusCost(0), Set.empty[WeaponType]),
  Rune("u_crdo", "Crushing Dodge", "/images/rune-crushingDodge.png", FocusCost(0), Set.empty[WeaponType]),
  Rune("u_crfl", "Crushing Flurry", "/images/rune-crushingFlurry.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_crqu", "Crushing Quad", "/images/rune-crushingQuad.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_crsl", "Crushing Slam", "/images/rune-crushingSlam.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_crtri", "Crushing Triple", "/images/rune-crushingTriple.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_crupp", "Crushing Uppercut", "/images/rune-crushingUppercut.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_cyki", "Cyclone Kick", "/images/rune-cycloneKick.png", FocusCost(50), WeaponType.gauntletWeapons),
  Rune("u_cysw", "Cyclone Swipe", "/images/rune-cycloneSwipe.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_dasu", "Damage Surge", "/images/rune-damageSurge.png", FocusCost(100), WeaponType.allMeleeWeapons),
  Rune("u_dast", "Dashing Stab", "/images/rune-dashingStab.png", FocusCost(100), WeaponType.doubleDaggerWeapons),
  Rune("u_dibr", "Drive Break", "/images/rune-driveBreak.png", FocusCost(50), WeaponType.gauntletWeapons),
  Rune("u_dosl", "Dodge Slamdown", "/images/rune-dodgeSlamdown.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_docru", "Double Crush", "/images/rune-doubleCrush.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_dosp", "Double Spin", "/images/rune-doubleSpin.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_dota", "Double Take", "/images/rune-doubleTake.png", FocusCost(0), WeaponType.doubleDaggerWeapons),
  Rune("u_drki", "Dragonfly Kick", "/images/rune-dragonflyKick.png", FocusCost(50), WeaponType.gauntletWeapons),
  Rune("u_drtra", "Drone Trap", "/images/rune-droneTrap.png", FocusCost(50), WeaponType.allMeleeWeapons),
  Rune("u_dr", "Dropkick", "/images/rune-dropkick.png", StaminaCost(44), WeaponType.allMeleeWeapons),
  Rune("u_dubre", "Dual Breaker", "/images/rune-dualBreaker.png", FocusCost(100), WeaponType.doubleDaggerWeapons),
  Rune("u_dufl", "Dual Flurry", "/images/rune-dualFlurry.png", FocusCost(50), WeaponType.doubleDaggerWeapons),
  Rune("u_dusa", "Dual Slash", "/images/rune-dualSlash.png", FocusCost(100), WeaponType.doubleDaggerWeapons),
  Rune("u_elen", "Electric Enchantment", "/images/rune-electricEnchantment.png", FocusCost(50), WeaponType.utilities),
  Rune("u_elre", "Electric Resistance", "/images/rune-electricResistance.png", FocusCost(0), WeaponType.allMeleeWeapons),
  Rune("u_er", "Eruption", "/images/rune-eruption.png", FocusCost(150), WeaponType.twoHandedWeapons),
  Rune("u_evco", "Evasive Combo", "/images/rune-evasiveCombo.png", FocusCost(0), Set.empty[WeaponType]),
  Rune("u_evpi", "Evasive Pierce", "/images/rune-evasivePierce.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_evsho", "Evasive Shot", "/images/rune-evasiveShot.png", FocusCost(50), WeaponType.bowWeapons),
  Rune("u_evst", "Evasive Strike", "/images/rune-evasiveStrike.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_evsw", "Evasive Swing", "/images/rune-evasiveSwing.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_fcds", "Fierce Dash", "/images/rune-fierceDash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_fiar", "Fire Arrow", "/images/rune-fireArrow.png", FocusCost(20), WeaponType.bowWeapons),
  Rune("u_fibl", "Fire Blast", "/images/rune-fireBlast.png", FocusCost(50), WeaponType.wandWeapons),
  Rune("u_fibu", "Fire Burst", "/images/rune-fireBurst.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_fidr", "Fire Dart", "/images/rune-fireDart.png", FocusCost(50), WeaponType.wandWeapons),
  Rune("u_fida", "Fire Dash", "/images/rune-fireDash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_fifl", "Fire Flurry", "/images/rune-fireFlurry.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_fino", "Fire Nova", "/images/rune-fireNova.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_fisl", "Fire Slam", "/images/rune-fireSlam.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_fisw", "Fire Swipe", "/images/rune-fireSwipe.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_fith", "Fire Throw", "/images/rune-fireThrow.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_fiwa", "Fire Wall", "/images/rune-fireWall.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_fiwk", "Fire Walk", "/images/rune-fireWalk.png", FocusCost(150), WeaponType.wandWeapons),
  Rune("u_fiwv", "Fire Wave", "/images/rune-fireWave.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_fiwh", "Fire Whirl", "/images/rune-fireWhirl.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_fiww", "Fire Whirlwind", "/images/rune-fireWhirlwind.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_fi", "Fireball", "/images/rune-fireball.png", FocusCost(50), WeaponType.staffWeapons),
  Rune("u_flsw", "Flame Sweep", "/images/rune-flameSweep.png", FocusCost(50), WeaponType.staffWeapons),
  Rune("u_foha", "Focus Halo", "/images/rune-focusHalo.png", FocusCost(100), WeaponType.utilities),
  Rune("u_frcl", "Frenzied Cleave", "/images/rune-frenziedCleave.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_frar", "Frigid Arc", "/images/rune-frigidArc.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_frki", "Frontflip Kick", "/images/rune-frontflipKick.png", StaminaCost(42), WeaponType.allMeleeWeapons),
  Rune("u_frda", "Frost Dash", "/images/rune-frostDash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_frne", "Frost Needles", "/images/rune-frostNeedles.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_frno", "Frost Nova", "/images/rune-frostNova.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_frst", "Frost Step", "/images/rune-frostStep.png", FocusCost(50), WeaponType.wandWeapons),
  Rune("u_frss", "Frost Stream", "/images/rune-frostStream.png", FocusCost(25), WeaponType.staffWeapons),
  Rune("u_frsk", "Frost Strike", "/images/rune-frostStrike.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_glsp", "Glacial Spike", "/images/rune-glacialSpike.png", FocusCost(150), WeaponType.staffWeapons),
  Rune("u_grsl", "Ground Slam", "/images/rune-groundSlam.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_gysl", "Gyro Slash", "/images/rune-gyroSlash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_ha", "Hail", "/images/rune-hail.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_heal", "Heal", "/images/rune-healAura.png", FocusCost(50), WeaponType.utilities),
  Rune("u_heau", "Heal Aura", "/images/rune-healAura.png", FocusCost(25), WeaponType.utilities),
  Rune("u_heaf", "Heat Affliction", "/images/rune-heatAffliction.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_heen", "Heat Enchantment", "/images/rune-heatEnchantment.png", FocusCost(50), WeaponType.utilities),
  Rune("u_he", "Hellfire", "/images/rune-hellfire.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_hofro", "Homing Frost", "/images/rune-homingFrost.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_icar", "Ice Arrow", "/images/rune-iceArrow.png", FocusCost(20), WeaponType.bowWeapons),
  Rune("u_ic", "Icebolt", "/images/rune-icebolt.png", FocusCost(50), WeaponType.staffWeapons),
  Rune("u_icda", "Ice Dart", "/images/rune-iceDart.png", FocusCost(50), WeaponType.wandWeapons),
  Rune("u_icra", "Ice Ram", "/images/rune-iceRam.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_icsw", "Ice Sweep", "/images/rune-iceSweep.png", FocusCost(25), WeaponType.staffWeapons),
  Rune("u_icth", "Ice Throw", "/images/rune-iceThrow.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_icwh", "Ice Whirl", "/images/rune-iceWhirl.png", FocusCost(150), WeaponType.oneHandedWeapons),
  Rune("u_il", "Illuminate", "/images/rune-illuminate.png", FocusCost(25), WeaponType.utilities),
  Rune("u_in", "Inferno", "/images/rune-inferno.png", FocusCost(25), WeaponType.staffWeapons),
  Rune("u_just", "Juggle Strike", "/images/rune-juggleStrike.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_jucu", "Jump Cut", "/images/rune-jumpCut.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_kisl", "Kick Slash", "/images/rune-kickSlash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_knsw", "Knockdown Swirl", "/images/rune-knockdownSwirl.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_knsho", "Knockout Shot", "/images/rune-knockoutShot.png", FocusCost(100), WeaponType.bowWeapons),
  Rune("u_le", "Leap", "/images/rune-leap.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_liar", "Lightning Arrow", "/images/rune-lightningArrow.png", FocusCost(20), WeaponType.bowWeapons),
  Rune("u_lias", "Lightning Assault", "/images/rune-lightningAssault.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_liba", "Lightning Barrage", "/images/rune-lightningBarrage.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_libl", "Lightning Blast", "/images/rune-lightningBlast.png", FocusCost(50), WeaponType.wandWeapons),
  Rune("u_libo", "Lightning Bolt", "/images/rune-lightningBolt.png", FocusCost(150), WeaponType.wandWeapons),
  Rune("u_licl", "Lightning Claw", "/images/rune-lightningClaw.png", FocusCost(50), WeaponType.doubleDaggerWeapons),
  Rune("u_lida", "Lightning Dart", "/images/rune-lightningDart.png", FocusCost(50), WeaponType.wandWeapons),
  Rune("u_lids", "Lightning Dash", "/images/rune-lightningDash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_lifl", "Lightning Flash", "/images/rune-lightningFlash.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_lifr", "Lightning Flurry", "/images/rune-lightningFlurry.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_lile", "Lightning Leap", "/images/rune-lightningLeap.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_lisl", "Lightning Slam", "/images/rune-lightningSlam.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_lisp", "Lightning Spin", "/images/rune-lightningSpin.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_list", "Lightning Storm", "/images/rune-lightningStorm.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_lisw", "Lightning Sweep", "/images/rune-lightningSweep.png", FocusCost(25), WeaponType.staffWeapons),
  Rune("u_lith", "Lightning Throw", "/images/rune-lightningThrow.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_liwh", "Lightning Whirl", "/images/rune-lightningWhirl.png", FocusCost(150), WeaponType.oneHandedWeapons),
  Rune("u_libr", "Limit Break", "/images/rune-limitBreak.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_mush", "Multi Shot", "/images/rune-multiShot.png", FocusCost(100), WeaponType.bowWeapons),
  Rune("u_musp", "Multi Spin", "/images/rune-multiSpin.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_ov", "Overrun", "/images/rune-overrun.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_pe", "Pestilence", "/images/rune-pestilence.png", FocusCost(150), WeaponType.staffWeapons),
  Rune("u_pere", "Pestilent Recoil", "/images/rune-pestilentRecoil.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_pida", "Piercing Dash", "/images/rune-piercingDash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_pido", "Piercing Dodge", "/images/rune-piercingDodge.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_pifl", "Piercing Flurry", "/images/rune-piercingFlurry.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_pisp", "Piercing Spin", "/images/rune-piercingSpin.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_plar", "Plague Arrow", "/images/rune-plagueArrow.png", FocusCost(20), WeaponType.bowWeapons),
  Rune("u_plba", "Plague Barrage", "/images/rune-plagueBarrage.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_plbr", "Plague Breath", "/images/rune-plagueBreath.png", FocusCost(50), WeaponType.staffWeapons),
  Rune("u_plbu", "Plague Burst", "/images/rune-plagueBurst.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_plco", "Plague Column", "/images/rune-plagueColumn.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_plcr", "Plague Crush", "/images/rune-plagueCrush.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_plda", "Plague Dart", "/images/rune-plagueDart.png", FocusCost(50), WeaponType.wandWeapons),
  Rune("u_plds", "Plague Dash", "/images/rune-plagueDash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_plen", "Plague Enchantment", "/images/rune-plagueEnchantment.png", FocusCost(50), WeaponType.utilities),
  Rune("u_plex", "Plague Explosion", "/images/rune-plagueExplosion.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_plla", "Plague Launch", "/images/rune-plagueLaunch.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_plno", "Plague Nova", "/images/rune-plagueNova.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_plre", "Plague Retch", "/images/rune-plagueRetch.png", FocusCost(50), WeaponType.gauntletWeapons),
  Rune("u_plsm", "Plague Smite", "/images/rune-plagueSmite.png", FocusCost(50), WeaponType.wandWeapons),
  Rune("u_plsp", "Plague Splatter", "/images/rune-plagueSplatter.png", FocusCost(150), WeaponType.wandWeapons),
  Rune("u_plst", "Plague Strike", "/images/rune-plagueStrike.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_plsw", "Plague Sweep", "/images/rune-plagueSweep.png", FocusCost(25), WeaponType.staffWeapons),
  Rune("u_plsl", "Plague Swirl", "/images/rune-plagueSwirl.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_plth", "Plague Throw", "/images/rune-plagueThrow.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_plwa", "Plague Wave", "/images/rune-plagueWave.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_plwh", "Plague Whirl", "/images/rune-plagueWhirl.png", FocusCost(150), WeaponType.oneHandedWeapons),
  Rune("u_posh", "Poise Shield", "/images/rune-poiseShield.png", FocusCost(100), WeaponType.utilities),
  Rune("u_po", "Pole Flurry", "/images/rune-poleFlurry.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_puhe", "Pulse of Health", "/images/rune-pulseOfHealth.png", FocusCost(10), WeaponType.utilities),
  Rune("u_raspi", "Raging Spiral", "/images/rune-ragingSpiral.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_reap", "Reap", "/images/rune-reap.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_rest", "Recoil Strike", "/images/rune-recoilStrike.png", FocusCost(50), WeaponType.gauntletWeapons),
  Rune("u_regu", "Regurgitate", "/images/rune-regurgitate.png", FocusCost(100), WeaponType.allMeleeWeapons),
  Rune("u_recl", "Relentless Cleave", "/images/rune-relentlessCleave.png", FocusCost(138), WeaponType.twoHandedWeapons),
  Rune("u_repa", "Repair", "/images/rune-repair.png", FocusCost(50), WeaponType.utilities),
  Rune("u_retu", "Return", "/images/rune-return.png", FocusCost(50), WeaponType.utilities),
  Rune("u_rita", "Rip and Tear", "/images/rune-ripAndTear.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_rifa", "Rising Fang", "/images/rune-risingFang.png", FocusCost(50), WeaponType.gauntletWeapons),
  Rune("u_ro", "Rotwheel", "/images/rune-rotwheel.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_sc", "Scream", "/images/rune-scream.png", FocusCost(50), WeaponType.utilities),
  Rune("u_secr", "Seismic Crush", "/images/rune-seismicCrush.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_shno", "Shock Nova", "/images/rune-shockNova.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_shst", "Shock Strike", "/images/rune-shockStrike.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_sk", "Skyfall Smash", "/images/rune-skyfallSmash.png", FocusCost(125), WeaponType.gauntletWeapons),
  Rune("u_sn", "Slamdown", "/images/rune-slamdown.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_sldh", "Slashing Dash", "/images/rune-slashingDash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_slde", "Slashing Dodge", "/images/rune-slashingDodge.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_slfl", "Slashing Flurry", "/images/rune-slashingFlurry.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune(
    "u_slpi",
    "Slashing Pirouette",
    "/images/rune-slashingPirouette.png",
    FocusCost(100),
    WeaponType.oneHandedWeapons,
  ),
  Rune("u_slsp", "Slashing Spin", "/images/rune-slashingSpin.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_sp", "Spark", "/images/rune-spark.png", FocusCost(100), WeaponType.staffWeapons),
  Rune("u_spin", "Spin", "/images/rune-spin.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_spcu", "Spin Crush", "/images/rune-spinCrush.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_spst", "Spin Strike", "/images/rune-spinStrike.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_spcr", "Spiral Crash", "/images/rune-spiralCrash.png", FocusCost(50), WeaponType.twoHandedWeapons),
  Rune("u_spbu", "Spirit Burst", "/images/rune-spiritBurst.png", FocusCost(50), WeaponType.gauntletWeapons),
  Rune("u_stwa", "Stamina Wellspring", "/images/rune-staminaWellspring.png", FocusCost(100), WeaponType.utilities),
  Rune("u_stat", "Static", "/images/rune-static.png", FocusCost(100), WeaponType.wandWeapons),
  Rune("u_stor", "Stormpiercer", "/images/rune-stormpiercer.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_sush", "Surge Slash", "/images/rune-surgeSlash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_swki", "Swipe Kick", "/images/rune-swipeKick.png", StaminaCost(40), WeaponType.allMeleeWeapons),
  Rune("u_tast", "Taunt Strike", "/images/rune-tauntStrike.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_teki", "Tempest Kick", "/images/rune-tempestKick.png", FocusCost(50), WeaponType.gauntletWeapons),
  Rune("u_th", "Thorns", "/images/rune-thorns.png", FocusCost(50), WeaponType.utilities),
  Rune("u_thrw", "Throw", "/images/rune-throw.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_thax", "Throw Axe", "/images/rune-throwAxe.png", FocusCost(20), WeaponType.oneHandedWeapons),
  Rune("u_thkn", "Throw Knife", "/images/rune-throwKnife.png", FocusCost(25), WeaponType.allMeleeWeapons),
  Rune("u_thru", "Thrust", "/images/rune-thrust.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_thst", "Thunderstrike", "/images/rune-thunderstrike.png", FocusCost(150), WeaponType.staffWeapons),
  Rune("u_tosp", "Tornado Spin", "/images/rune-tornadoSpin.png", FocusCost(100), WeaponType.doubleDaggerWeapons),
  Rune("u_tost", "Tornado Strike", "/images/rune-tornadoStrike.png", FocusCost(50), WeaponType.oneHandedWeapons),
  Rune("u_trsl", "Tremor Slam", "/images/rune-tremorSlam.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_trwa", "Tremor Wave", "/images/rune-tremorWave.png", FocusCost(50), WeaponType.gauntletWeapons),
  Rune("u_trba", "Trinity Barrage", "/images/rune-trinityBarrage.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_trsw", "Triple Swipe", "/images/rune-tripleSwipe.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_tuki", "Turnback Kick", "/images/rune-turnbackKick.png", StaminaCost(30), WeaponType.allMeleeWeapons),
  Rune("u_twda", "Twirl Dash", "/images/rune-twirlDash.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_twsp", "Twirl Spin", "/images/rune-twirlSpin.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_wa", "Wallbreaker", "/images/rune-wallbreaker.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_wh", "Whirl", "/images/rune-whirl.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_whst", "Whirlstep Strike", "/images/rune-whirlstepStrike.png", FocusCost(100), WeaponType.oneHandedWeapons),
  Rune("u_ww", "Whirlwind", "/images/rune-whirlwind.png", FocusCost(100), WeaponType.twoHandedWeapons),
  Rune("u_wiru", "Wild Rush", "/images/rune-wildRush.png", FocusCost(100), WeaponType.doubleDaggerWeapons),
)

package com.tewe.nrftw

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
    gemOption: Option[Gem] = None
  ) {
    def shortState(): String = {
      s"${enchant1}-${enchant2}-${enchant3}-${enchant4}-${downside}${gemOption.fold("")(gem => s"-${gem.id}")}"
    }
  }

  case class WeaponState(
    itemState: ItemState,
    weaponTypeId: String,
    rune1Option: Option[Rune] = None,
    rune2Option: Option[Rune] = None,
    rune3Option: Option[Rune] = None,
    rune4Option: Option[Rune] = None
  ) {
    def shortState(): String = {
      s"W$weaponTypeId-${itemState.shortState()}"
    }
  }

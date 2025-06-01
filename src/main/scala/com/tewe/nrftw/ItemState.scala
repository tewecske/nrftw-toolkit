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
    weaponType: WeaponType
  ) {
    def shortState(): String = {
      itemState.shortState() // TODO temporary
    }
  }

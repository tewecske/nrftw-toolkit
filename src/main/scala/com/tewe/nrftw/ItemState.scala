package com.tewe.nrftw

sealed trait ItemState {
  def enchants: List[String]
  def enchantsError: List[Boolean]
  def shortState(): String
}

case class PlaguedItemState(
  enchants: List[String],
  downside: String,
  enchantsError: List[Boolean] = List(false, false, false, false),
  gemOption: Option[Gem] = None,
) extends ItemState {
  def shortState(): String = {
    s"${enchants.mkString("-")}-${downside}${gemOption.fold("")(gem =>
        s"-${gem.id}"
      )}"
  }
}

case class MagicItemState(
  enchants: List[String],
  enchantsError: List[Boolean] = List(false, false, false),
  gemOption: Option[Gem] = None,
) extends ItemState {
  def shortState(): String = {
    s"${enchants.mkString("-")}${gemOption.fold("")(gem => s"-${gem.id}")}"
  }
}

case class WeaponState(
  itemState: ItemState,
  weaponTypeId: String,
  rune1Option: Option[Rune] = None,
  rune2Option: Option[Rune] = None,
  rune3Option: Option[Rune] = None,
  rune4Option: Option[Rune] = None,
  rune1Error: Boolean = false,
  rune2Error: Boolean = false,
  rune3Error: Boolean = false,
  rune4Error: Boolean = false,
) {
  def shortState(): String = {
    val runes = rune1Option ++ rune2Option ++ rune3Option ++ rune4Option
    val runesString = runes.map(_.id).mkString("-")
    val runesState = {
      if (runesString.nonEmpty)
        s"-R-$runesString"
      else
        ""
    }
    s"W$weaponTypeId-${itemState.shortState()}${runesState}"
  }
}

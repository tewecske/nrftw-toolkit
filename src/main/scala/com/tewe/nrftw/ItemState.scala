package com.tewe.nrftw

case class ItemState(
  itemRarity: ItemRarity = ItemRarity.Plagued,
  enchants: List[String],
  downside: Option[String],
  enchantsError: List[Boolean] = List(false, false, false, false),
  gemOption: Option[Gem] = None,
) {
  def shortState(): String = {
    s"RY-${itemRarity.id}-ENH-${enchants.mkString("-")}${downside.fold("")(d =>
        s"-$d"
      )}${gemOption.fold("")(gem => s"-${gem.id}")}"
  }
  def resetEnchants(
    itemRarity: ItemRarity,
    config: ItemBuilderConfig,
  ): ItemState = {
    Log.debug(
      s"resetEnchants called for ${config.itemSlot} and rarity $itemRarity"
    )
    val firstEnchant = config.enchants.values.toList.map(_.id).sorted.head
    val firstMagicEnchant =
      config.magicEnchants.values.toList.map(_.id).sorted.head
    val firstDownside =
      config.enchantDownsides.values.toList.map(_.id).sorted.head
    Errors.errors(
      config,
      this.copy(
        enchants =
          itemRarity match {
            case ItemRarity.Plagued =>
              List(firstEnchant, firstEnchant, firstEnchant, firstEnchant)
            case ItemRarity.Magic =>
              List(firstMagicEnchant, firstMagicEnchant, firstMagicEnchant)
            case _ =>
              List(firstEnchant, firstEnchant, firstEnchant, firstEnchant)
          },
        downside = {
          itemRarity match {
            case ItemRarity.Plagued =>
              Some(firstDownside)
            case _ =>
              None
          }
        },
      ),
    )

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
  runes: List[Rune] = List.empty,
  runesError: List[Boolean] = List(false, false, false, false),
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

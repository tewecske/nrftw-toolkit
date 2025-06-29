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

case class RuneState(
  runes: List[Option[Rune]] = List(None, None, None, None),
  runesError: List[Boolean] = List(false, false, false, false),
) {
  def shortState(): String = {
    val runesString = runes.flatMap(_.map(_.id)).mkString("-")
    if (runesString.nonEmpty)
      s"-R-$runesString"
    else
      ""
  }
}

case class WeaponState(
  itemState: ItemState,
  weaponTypeId: String,
  runeState: RuneState = RuneState(),
) {
  def shortState(): String = {
    s"W$weaponTypeId-${itemState.shortState()}${runeState.shortState()}"
  }
}

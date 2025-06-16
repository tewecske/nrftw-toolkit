package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

object ItemRarityComponent {

  def apply(itemRarityVar: Var[String]): HtmlElement = {
    div(
      cls := "item-rarity-select",
      select(
        value <-- itemRarityVar,
        onChange.mapToValue --> itemRarityVar,
        ItemRarity
          .values
          .filter(ry => ry != ItemRarity.Common && ry != ItemRarity.Legendary)
          .map { itemRarity =>
            option(
              value := itemRarity.id,
              cls := s"item-type-${itemRarity.value}",
              itemRarity.value,
            )
          },
      ),
    )
  }
}


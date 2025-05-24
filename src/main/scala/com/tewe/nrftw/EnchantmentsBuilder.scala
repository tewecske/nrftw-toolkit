package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object EnchantmentsBuilder {

  val _ = Stylesheet // Use import to prevent DCE

  def enchantmentsCompact(itemRarity: ItemRarity, enchantments: List[String], enchantDownsides: List[String]) = {
      div(
        cls("enchantments-container-compact"),
        enchantments.map(enchant =>
          div(
            cls(s"enchantment-item-compact item-type-${itemRarity}"),
            span(enchant)
          )
        ),
        enchantDownsides.map(downside =>
          div(
            cls(s"enchantment-item-compact downside-text"),
            span(downside)
          )
        ),
      )
  }

  def enchantmentsFull(itemRarity: ItemRarity, enchantments: List[String], enchantDownsides: List[String]) = {
      div(
        cls("enchantments-container"),
        div(
          enchantments.map(enchant =>
            div(
              cls("enchantment-item"),
              cls(s"item-type-${itemRarity}"),
              span(enchant)
            )
          )
        ),
        div(
          enchantDownsides.map(downside =>
            div(
              cls(s"enchantment-item downside-text"),
              span(downside)
            )
          )
        )
      )
  }
}


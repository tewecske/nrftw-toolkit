package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import com.tewe.nrftw.items.*

object ItemBuilder {

  @JSImport("@find/**/ItemBuilder.css", JSImport.Namespace)
  @js.native
  private object Stylesheet extends js.Object

  val _ = Stylesheet // Use import to prevent DCE

  def createState(
    config: ItemBuilderConfig,
    initState: Option[String],
  ): ItemState = {
    val sortedEnchants = config.enchants.values.toList.sortBy(_.id)
    val sortedEnchantDownsides = config
      .enchantDownsides
      .values
      .toList
      .sortBy(_.id)

    // TODO List[Option[Enchant]] will fix this
    val firstEnchant = {
      if (config.itemSlot == ItemSlot.UtilitySlot)
        ""
      else
        sortedEnchants.head.id
    }
    val defaultEnchants = {
      if (config.itemSlot == ItemSlot.UtilitySlot)
        List.empty
      else
        List(firstEnchant, firstEnchant, firstEnchant, firstEnchant)
    }

    val firstDownside = sortedEnchantDownsides.headOption.map(_.id)

    def safeEnchant(id: String): String = {
      sortedEnchants.find(_.id == id).map(_.id).getOrElse(firstEnchant)
    }

    def safeDownside(id: String): Option[String] = {
      sortedEnchantDownsides.find(_.id == id).map(_.id).orElse(firstDownside)
    }

    initState
      .filter(!_.isBlank)
      .fold {
        ItemState(
          enchants = defaultEnchants,
          downside = sortedEnchantDownsides.headOption.map(_.id),
        )
      } { stringState =>
        Log.debug(s"STATE: $stringState")
        stringState match {
          case s"RY-$r-ENH-$e1-$e2-$e3-$e4-$d-$g" =>
            ItemState(
              ItemRarity.values.find(_.id == r).getOrElse(ItemRarity.Plagued),
              enchants = List(
                safeEnchant(e1),
                safeEnchant(e2),
                safeEnchant(e3),
                safeEnchant(e4),
              ),
              safeDownside(d),
              gemOption = gems.find(_.id == g),
            )
          case s"RY-$r-ENH-$e1-$e2-$e3-$e4-$d" =>
            ItemState(
              ItemRarity.values.find(_.id == r).getOrElse(ItemRarity.Plagued),
              enchants = List(
                safeEnchant(e1),
                safeEnchant(e2),
                safeEnchant(e3),
                safeEnchant(e4),
              ),
              safeDownside(d),
            )
          case s"RY-$r-ENH-$e1-$e2-$e3-$g" =>
            ItemState(
              ItemRarity.values.find(_.id == r).getOrElse(ItemRarity.Magic),
              enchants = List(
                safeEnchant(e1),
                safeEnchant(e2),
                safeEnchant(e3),
              ),
              downside = None,
              gemOption = gems.find(_.id == g),
            )
          case s"RY-$r-ENH-$e1-$e2-$e3" =>
            ItemState(
              ItemRarity.values.find(_.id == r).getOrElse(ItemRarity.Magic),
              enchants = List(
                safeEnchant(e1),
                safeEnchant(e2),
                safeEnchant(e3),
              ),
              downside = None,
            )
          case s"$e1-$e2-$e3-$e4-$d-$g" =>
            ItemState(
              ItemRarity.Plagued,
              enchants = List(
                safeEnchant(e1),
                safeEnchant(e2),
                safeEnchant(e3),
                safeEnchant(e4),
              ),
              safeDownside(d),
              gemOption = gems.find(_.id == g),
            )
          case s"$e1-$e2-$e3-$e4-$d" =>
            ItemState(
              ItemRarity.Plagued,
              enchants = List(
                safeEnchant(e1),
                safeEnchant(e2),
                safeEnchant(e3),
                safeEnchant(e4),
              ),
              safeDownside(d),
            )
          case _ =>
            ItemState(
              ItemRarity.Plagued,
              enchants = defaultEnchants,
              downside = sortedEnchantDownsides.headOption.map(_.id),
            )
        }
      }
  }
  def apply(
    config: ItemBuilderConfig,
    stateVar: Var[ItemState],
  ): HtmlElement = {

    val itemRarityVar = {
      stateVar.zoomLazy(_.itemRarity.id)((state, id) => {
        Log.debug(s"Item ${config.itemSlot} rarity changed to $id")
        val itemRarity = ItemRarity
          .values
          .find(_.id == id)
          .getOrElse(ItemRarity.Plagued)
        state.copy(itemRarity = itemRarity).resetEnchants(itemRarity, config)
      })
    }
    val itemGemStateVar = {
      stateVar.zoomLazy(_.gemOption)((state, gem) => {
        state.copy(gemOption = gem)
      })
    }
    val itemGemShowModalVar = Var(false)
    val itemGemModal = Modal.gemsModal(
      config.itemSlot,
      itemGemShowModalVar,
      gems,
      gem => {
        itemGemStateVar.update { _ =>
          Log.debug(s"Update ${config.itemSlot} itemGemStateVar to ${gem.id}")
          Option(gem)
        }
      },
    )
    val slot = config.itemSlot.name
    div(
      cls := "item-card",
      cls <--
        itemRarityVar
          .signal
          .map { itemRarity =>
            s"rarity-${ItemRarity.findById(itemRarity)}"
          },
      itemGemModal,
      div(
        cls := "item-header",
        h1(cls := "item-name", slot),
        ItemRarityComponent(itemRarityVar),
        div(cls := "item-level", span("16")),
        // div(
        //   cls := "item-type-select",
        //   select(
        //     option(selected := true, s"Mesh $slot"),
        //     option(s"Plate $slot"),
        //     option(s"Leather $slot"),
        //     option(s"Cloth $slot")
        //   )
        // )
      ),
      GemsBuilder(config, itemGemStateVar, itemGemShowModalVar),
      EnchantmentsBuilder.enchantmentsSelect(config, stateVar),
    )
  }
}

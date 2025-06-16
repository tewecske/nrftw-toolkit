package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

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

    val firstEnchant = sortedEnchants.head.id
    val defaultEnchants = List(
      firstEnchant,
      firstEnchant,
      firstEnchant,
      firstEnchant,
    )
    initState
      .filter(!_.isBlank)
      .fold {
        ItemState(
          enchants = defaultEnchants,
          downside = Option(sortedEnchantDownsides.head.id),
        )
      } { stringState =>
        println(s"STATE: $stringState")
        stringState match {
          case s"R$r-$e1-$e2-$e3-$e4-$d-$g" =>
            ItemState(
              ItemRarity.values.find(_.id == r).getOrElse(ItemRarity.Plagued),
              defaultEnchants,
              Option(d),
              gemOption = gems.find(_.id == g),
            )
          case s"$e1-$e2-$e3-$e4-$d-$g" =>
            ItemState(
              ItemRarity.Plagued,
              defaultEnchants,
              Option(d),
              gemOption = gems.find(_.id == g),
            )
          case s"$e1-$e2-$e3-$e4-$d" =>
            ItemState(ItemRarity.Plagued, defaultEnchants, Option(d))
          case s"$e1-$e2-$e3-$g" =>
            ItemState(
              ItemRarity.Magic,
              defaultEnchants,
              downside = None,
              gemOption = gems.find(_.id == g),
            )
          case s"$e1-$e2-$e3" =>
            ItemState(ItemRarity.Magic, defaultEnchants, downside = None)
          case _ =>
            ItemState(
              ItemRarity.Plagued,
              enchants = defaultEnchants,
              downside = Option(sortedEnchantDownsides.head.id),
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
        state.copy(itemRarity =
          ItemRarity.values.find(_.id == id).getOrElse(ItemRarity.Plagued)
        )
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
      gem => itemGemStateVar.update(_ => Option(gem)),
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
        div(
          cls := "item-rarity-select",
          select(
            value <-- itemRarityVar,
            onChange.mapToValue --> itemRarityVar,
            ItemRarity
              .values
              .map { itemRarity =>
                option(
                  value := itemRarity.id,
                  cls := s"item-type-${itemRarity.value}",
                  itemRarity.value,
                )
              },
          ),
        ),
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

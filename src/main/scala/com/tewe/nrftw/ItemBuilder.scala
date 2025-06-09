package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object ItemBuilder {

  @JSImport("@find/**/ItemBuilder.css", JSImport.Namespace)
  @js.native private object Stylesheet extends js.Object

  val _ = Stylesheet // Use import to prevent DCE

  def createState(config: ItemBuilderConfig, initState: Option[String]): ItemState = {
    val sortedEnchants = config.enchants.values.toList.sortBy(_.id)
    val sortedEnchantDownsides = config.enchantDownsides.values.toList.sortBy(_.id)

    initState.filter(!_.isBlank).fold {
      PlaguedItemState(
        enchant1 = sortedEnchants.head.id,
        enchant2 = sortedEnchants.head.id,
        enchant3 = sortedEnchants.head.id,
        enchant4 = sortedEnchants.head.id,
        downside = sortedEnchantDownsides.head.id
      )
      } { stringState =>
        println(s"STATE: $stringState")
        stringState match {
          case s"$e1-$e2-$e3-$e4-$d-$g" => PlaguedItemState(e1, e2, e3, e4, d, gemOption = gems.find(_.id == g))
          case s"$e1-$e2-$e3-$e4-$d" => PlaguedItemState(e1, e2, e3, e4, d)
          case _ => 
            PlaguedItemState(
              enchant1 = sortedEnchants.head.id,
              enchant2 = sortedEnchants.head.id,
              enchant3 = sortedEnchants.head.id,
              enchant4 = sortedEnchants.head.id,
              downside = sortedEnchantDownsides.head.id
            )
        }
      }
  }
  def apply(config: ItemBuilderConfig, stateVar: Var[ItemState]): HtmlElement = {

    val itemGemStateVar = stateVar.zoomLazy(state => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.gemOption
    })((state, gem) => state match {
      case plaguedState @ PlaguedItemState(_, _, _, _, _, _, _, _, _, _) => plaguedState.copy(gemOption = gem)
    })
    val itemGemShowModalVar = Var(false)
    val itemGemModal = Modal.gemsModal(config.itemSlot, itemGemShowModalVar, gems, gem => itemGemStateVar.update(_ => Option(gem)))
    val slot = config.itemSlot.name
      div(
        cls := "item-card",
        itemGemModal,
        div(
          cls := "item-header",
          h1(cls := "item-name", slot),
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
        EnchantmentsBuilder.enchantmentsSelect(config, stateVar)
      )
  }
}


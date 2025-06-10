package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import com.raquo.airstream.state.Var

object EnchantmentsBuilder {

  @JSImport("@find/**/EnchantmentsBuilder.css", JSImport.Namespace)
  @js.native
  private object Stylesheet extends js.Object

  val _ = Stylesheet // Use import to prevent DCE

  def enchantmentsCompact(
    itemRarity: ItemRarity,
    enchantments: List[String],
    enchantDownsides: List[String],
  ) = {
    div(
      cls("enchantments-container-compact"),
      enchantments.map(enchant => {
        div(
          cls(s"enchantment-item-compact item-type-${itemRarity}"),
          span(enchant),
        )
      }),
      enchantDownsides.map(downside =>
        div(cls(s"enchantment-item-compact downside-text"), span(downside))
      ),
    )
  }

  def enchantmentsFull(
    itemRarity: ItemRarity,
    enchantments: List[String],
    enchantDownsides: List[String],
  ) = {
    div(
      cls("enchantments-container"),
      div(
        enchantments.map(enchant => {
          div(
            cls("enchantment-item"),
            cls(s"item-type-${itemRarity}"),
            span(enchant),
          )
        })
      ),
      div(
        enchantDownsides.map(downside =>
          div(cls(s"enchantment-item downside-text"), span(downside))
        )
      ),
    )
  }

  def updateListElement[A](list: List[A], index: Int, newValue: A): List[A] = {
    if (index < 0 || index >= list.length) {
      throw new IndexOutOfBoundsException(
        s"Index $index is out of bounds for list of length ${list.length}"
      )
    }
    list.take(index) ::: (newValue :: list.drop(index + 1))
  }
  def enchantsSelectComponent(
    index: Int,
    config: ItemBuilderConfig,
    sortedEnchants: List[Enchant],
    stateVar: Var[ItemState],
    enchantsVar: Var[List[String]],
    enchantsErrorSignal: Signal[List[Boolean]],
  ) = {
    val enchantVar = {
      enchantsVar.zoomLazy(_.apply(index))((list, enchant) =>
        updateListElement(list, index, enchant)
      )
    }
    select(
      cls := "enchant-text",
      cls("x-hasError") <-- enchantsErrorSignal.map(_.apply(index)),
      value <-- enchantVar,
      onChange.mapToValue --> enchantVar,
      onChange.mapTo(stateVar.now()) --> Errors.validator(config, stateVar),
      sortedEnchants.map(enchant => {
        option(
          value := enchant.id,
          cls := s"enchant-group-${enchant.group}",
          enchant.value,
        )
      }),
    )
  }
  def enchantmentsSelect(
    config: ItemBuilderConfig,
    stateVar: Var[ItemState],
  ) = {
    val sortedEnchants = config.enchants.values.toList.sortBy(_.id)
    val sortedEnchantDownsides = config
      .enchantDownsides
      .values
      .toList
      .sortBy(_.id)

    val enchantsVar = {
      stateVar.zoomLazy(state => {
        state match {
          case plaguedState @ PlaguedItemState(_, _, _, _) =>
            plaguedState.enchants
        }
      })((state, enchants) => {
        state match {
          case plaguedState @ PlaguedItemState(_, _, _, _) =>
            plaguedState.copy(enchants = enchants)
        }
      })
    }
    val downsideVar = {
      stateVar.zoomLazy(state => {
        state match {
          case plaguedState @ PlaguedItemState(_, _, _, _) =>
            plaguedState.downside
        }
      })((state, enchant) => {
        state match {
          case plaguedState @ PlaguedItemState(_, _, _, _) =>
            plaguedState.copy(downside = enchant)
        }
      })
    }

    val enchantsErrorSignal = stateVar
      .signal
      .map(state => {
        state match {
          case plaguedState @ PlaguedItemState(_, _, _, _) =>
            plaguedState.enchantsError
        }
      })

    div(
      cls := "enchantments-container",
      (0 until 4).map(index => {
        enchantsSelectComponent(
          index,
          config,
          sortedEnchants,
          stateVar,
          enchantsVar,
          enchantsErrorSignal,
        )
      }),
      select(
        cls := "downside-text",
        value <-- downsideVar,
        onChange.mapToValue --> downsideVar,
        sortedEnchantDownsides.map(enchant => {
          option(
            value := enchant.id,
            cls := s"enchant-group-${enchant.group}",
            enchant.value,
          )
        }),
      ),
    )
  }
}

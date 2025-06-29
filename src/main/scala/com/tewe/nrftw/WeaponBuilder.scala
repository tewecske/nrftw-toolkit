package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import com.tewe.nrftw.WeaponType.allWeapons
import com.tewe.nrftw.items.*
import com.tewe.nrftw.Errors
import com.tewe.nrftw.RunesBuilder.runesSelect

object WeaponBuilder {

  val _ = Stylesheet // Use import to prevent DCE

  def createState(
    config: WeaponBuilderConfig,
    initState: Option[String],
  ): WeaponState = {
    initState
      .filter(!_.isBlank)
      .fold {
        WeaponState(
          itemState = ItemBuilder.createState(config.itemConfig, initState),
          weaponTypeId = config.weaponTypes.head.id,
        )
      } { stringState =>
        Log.debug(s"STATE: $stringState")
        stringState match {
          case s"W$w-$itemStateString-R-$runeState" =>
            val runesList = runeState
              .split("-")
              .toSeq
              .flatMap(id => runes.find(_.id == id))
            WeaponState(
              itemState = ItemBuilder.createState(
                config.itemConfig,
                Option(itemStateString),
              ),
              weaponTypeId = w,
              runes = (0 to 3).toList.map(runesList.lift),
            )
          case s"W$w-$itemStateString" =>
            WeaponState(
              itemState = ItemBuilder.createState(
                config.itemConfig,
                Option(itemStateString),
              ),
              weaponTypeId = w,
            )
          case _ =>
            WeaponState(
              itemState = ItemBuilder.createState(config.itemConfig, initState),
              weaponTypeId = config.weaponTypes.head.id,
            )
        }
      }
  }

  def apply(
    config: WeaponBuilderConfig,
    stateVar: Var[WeaponState],
  ): HtmlElement = {

    val itemStateVar = {
      stateVar.zoomLazy(_.itemState)((state, itemState) => {
        Log.debug(
          s"Update ${config
              .itemConfig
              .itemSlot} itemStateVar with stateVar.zoomLazy to ${itemState
              .shortState()}"
        )
        state.copy(itemState = itemState)
      })
    }
    val itemRarityVar = {
      stateVar.zoomLazy(_.itemState.itemRarity.id)((state, id) => {
        Log.debug(s"Item ${config.itemConfig.itemSlot} rarity changed to $id")
        val itemRarity = ItemRarity
          .values
          .find(_.id == id)
          .getOrElse(ItemRarity.Plagued)
        state.copy(itemState = {
          state
            .itemState
            .copy(itemRarity = itemRarity)
            .resetEnchants(itemRarity, config.itemConfig)
        }
        )
      })
    }
    val weaponTypeIdVar = {
      stateVar.zoomLazy(_.weaponTypeId)((state, weaponTypeId) =>
        state.copy(weaponTypeId = weaponTypeId)
      )
    }
    // TODO try to remove
    val weaponTypeSignal = weaponTypeIdVar
      .signal
      .distinct
      .map(weaponId => allWeapons.find(_.id == weaponId).head)
    val itemGemStateVar = {
      stateVar.zoomLazy(_.itemState.gemOption)((weaponState, gem) => {
        Log.debug(
          s"Update ${config
              .itemConfig
              .itemSlot} itemGemStateVar with stateVar.zoomLazy to $gem"
        )
        weaponState.copy(itemState =
          weaponState.itemState.copy(gemOption = gem)
        )
      })
    }
    val itemGemShowModalVar = Var(false)
    val itemGemModal = Modal.gemsModal(
      config.itemConfig.itemSlot,
      itemGemShowModalVar,
      gems,
      gem => {
        itemGemStateVar.update { _ =>
          Log.debug(
            s"Update ${config.itemConfig.itemSlot} itemGemStateVar to ${gem.id}"
          )
          Option(gem)
        }
      },
    )

    val runesShowModalVar = Var(false)
    val runesModalCallbackVar = Var((rune: Rune) => {})
    val runesModal = Modal.runesModal(
      weaponTypeIdVar,
      runesShowModalVar,
      runes,
      runesModalCallbackVar,
    )

    val runesValidator = {
      (config: WeaponBuilderConfig, stateVar: Var[WeaponState]) =>
        {
          Observer[WeaponState] { state =>
            Log.debug(s"Runes validator ${config.itemConfig.itemSlot}")
            stateVar.update(_ => {
              Log.debug(s"Update WeaponState errors in runesValidator")
              Errors.errors(
                config,
                state.copy(runes = {
                  state
                    .runes
                    .map(
                      _.filter(rune =>
                        rune.weaponTypes.exists(_.id == state.weaponTypeId)
                      )
                    )
                }
                ),
              )
            })
          }
        }
    }

    val slot = config.itemConfig.itemSlot.name
    div(
      cls := "item-card",
      cls <--
        itemRarityVar
          .signal
          .map { itemRarity =>
            s"rarity-${ItemRarity.findById(itemRarity)}"
          },
      itemGemModal,
      runesModal,
      div(
        cls := "weapon-header",
        div(
          cls := "item-header",
          h1(cls := "item-name", slot),
          div(cls := "item-level", span("16")),
          ItemRarityComponent(itemRarityVar),
        ),
        div(
          cls := "item-type-select",
          config.weaponTypes.toList match {
            case head :: Nil =>
              div(head.name)
            case weapons =>
              select(
                value <-- weaponTypeIdVar,
                onChange.mapToValue --> weaponTypeIdVar,
                onChange.mapTo(stateVar.now()) -->
                  runesValidator(config, stateVar),
                weapons.map(weaponType =>
                  option(value := weaponType.id, weaponType.name)
                ),
              )
          },
        ),
      ),
      child <--
        weaponTypeSignal.map(weaponType => {
          Log.debug(s"weaponType: $weaponType")
          if (weaponType == WeaponType.Shield) {
            div()
          } else {
            div(
              runesSelect(
                config,
                stateVar,
                runesShowModalVar,
                runesModalCallbackVar,
              )
            )
          }
        }),
      GemsBuilder(config.itemConfig, itemGemStateVar, itemGemShowModalVar),
      EnchantmentsBuilder.enchantmentsSelect(config.itemConfig, itemStateVar),
    )
  }
}

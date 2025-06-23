package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import com.tewe.nrftw.WeaponType.allWeapons
import com.tewe.nrftw.items.*
import com.tewe.nrftw.Errors

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
              rune1Option = runesList.lift(0),
              rune2Option = runesList.lift(1),
              rune3Option = runesList.lift(2),
              rune4Option = runesList.lift(3),
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

    val rune1StateVar = {
      stateVar.zoomLazy(_.rune1Option)((state, rune1Option) =>
        state.copy(rune1Option = rune1Option)
      )
    }
    val rune2StateVar = {
      stateVar.zoomLazy(_.rune2Option)((state, rune2Option) =>
        state.copy(rune2Option = rune2Option)
      )
    }
    val rune3StateVar = {
      stateVar.zoomLazy(_.rune3Option)((state, rune3Option) =>
        state.copy(rune3Option = rune3Option)
      )
    }
    val rune4StateVar = {
      stateVar.zoomLazy(_.rune4Option)((state, rune4Option) =>
        state.copy(rune4Option = rune4Option)
      )
    }
    val rune1ErrorSignal = stateVar.signal.map(_.rune1Error)
    val rune2ErrorSignal = stateVar.signal.map(_.rune2Error)
    val rune3ErrorSignal = stateVar.signal.map(_.rune3Error)
    val rune4ErrorSignal = stateVar.signal.map(_.rune4Error)
    val runesShowModalVar = Var(false)
    val runesModalCallbackVar = Var((rune: Rune) => {
      rune1StateVar.update { _ =>
        Log.debug(s"Update rune1StateVar with $rune")
        Option(rune)
      }
      stateVar.update { state =>
        Log.debug(s"Update WeaponState errors after rune1StateVar $rune update")
        Errors.errors(config, state)
      }
    })
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
              val rune1Update = state
                .rune1Option
                .filter(rune =>
                  rune.weaponTypes.exists(_.id == state.weaponTypeId)
                )
              val rune2Update = state
                .rune2Option
                .filter(rune =>
                  rune.weaponTypes.exists(_.id == state.weaponTypeId)
                )
              val rune3Update = state
                .rune3Option
                .filter(rune =>
                  rune.weaponTypes.exists(_.id == state.weaponTypeId)
                )
              val rune4Update = state
                .rune4Option
                .filter(rune =>
                  rune.weaponTypes.exists(_.id == state.weaponTypeId)
                )
              if (
                rune1Update != state.rune1Option ||
                rune1Update != state.rune1Option ||
                rune1Update != state.rune1Option ||
                rune1Update != state.rune1Option
              ) {
                state.copy(
                  rune1Option = rune1Update,
                  rune2Option = rune3Update,
                  rune3Option = rune3Update,
                  rune4Option = rune4Update,
                )
              } else {
                state
              }
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
              cls := "runes-container",
              RunesBuilder(
                config,
                rune1StateVar,
                rune1ErrorSignal,
                runesShowModalVar,
                runesModalCallbackVar,
                (rune: Rune) => {
                  rune1StateVar.update { _ =>
                    Log.debug(s"Update rune1Var with $rune")
                    Option(rune)
                  }
                  stateVar.update { state =>
                    Log.debug(
                      s"Update WeaponState errors after rune1Var $rune update"
                    )
                    Errors.errors(config, state)
                  }
                },
              ),
              RunesBuilder(
                config,
                rune2StateVar,
                rune2ErrorSignal,
                runesShowModalVar,
                runesModalCallbackVar,
                (rune: Rune) => {
                  rune2StateVar.update { _ =>
                    Log.debug(s"Update rune2Var with $rune")
                    Option(rune)
                  }
                  stateVar.update { state =>
                    Log.debug(
                      s"Update WeaponState errors after rune2Var $rune update"
                    )
                    Errors.errors(config, state)
                  }
                },
              ),
              RunesBuilder(
                config,
                rune3StateVar,
                rune3ErrorSignal,
                runesShowModalVar,
                runesModalCallbackVar,
                (rune: Rune) => {
                  rune3StateVar.update { _ =>
                    Log.debug(s"Update rune3Var with $rune")
                    Option(rune)
                  }
                  stateVar.update { state =>
                    Log.debug(
                      s"Update WeaponState errors after rune3Var $rune update"
                    )
                    Errors.errors(config, state)
                  }
                },
              ),
              RunesBuilder(
                config,
                rune4StateVar,
                rune4ErrorSignal,
                runesShowModalVar,
                runesModalCallbackVar,
                (rune: Rune) => {
                  rune4StateVar.update { _ =>
                    Log.debug(s"Update rune4Var with $rune")
                    Option(rune)
                  }
                  stateVar.update { state =>
                    Log.debug(
                      s"Update WeaponState errors after rune4Var $rune update"
                    )
                    Errors.errors(config, state)
                  }
                },
              ),
            )
          }
        }),
      GemsBuilder(config.itemConfig, itemGemStateVar, itemGemShowModalVar),
      EnchantmentsBuilder.enchantmentsSelect(config.itemConfig, itemStateVar),
    )
  }
}

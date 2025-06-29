package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import org.scalajs.dom.URLSearchParams

import com.tewe.nrftw.items.*

@JSImport("@find/**/HelloWorld.less", JSImport.Namespace)
@js.native
private object Stylesheet extends js.Object

val _ = Stylesheet // Use import to prevent DCE

case class FullState(
  helmetState: ItemState,
  armorState: ItemState,
  pantsState: ItemState,
  glovesState: ItemState,
  weaponState: WeaponState,
  shieldState: WeaponState,
  bowState: WeaponState,
  utilityState: WeaponState,
  ring1StateOption: Option[RingData],
  ring2StateOption: Option[RingData],
  ring3StateOption: Option[RingData],
  ring1Error: Boolean = false,
  ring2Error: Boolean = false,
  ring3Error: Boolean = false,
) {
  def shortState(): String = {
    s"h=${js.URIUtils.encodeURIComponent(helmetState.shortState())}&" +
      s"a=${js.URIUtils.encodeURIComponent(armorState.shortState())}&" +
      s"p=${js.URIUtils.encodeURIComponent(pantsState.shortState())}&" +
      s"g=${js.URIUtils.encodeURIComponent(glovesState.shortState())}&" +
      s"w=${js.URIUtils.encodeURIComponent(weaponState.shortState())}&" +
      s"s=${js.URIUtils.encodeURIComponent(shieldState.shortState())}&" +
      s"b=${js.URIUtils.encodeURIComponent(bowState.shortState())}" +
      ring1StateOption.fold("")(ring1State =>
        s"&r1=${js.URIUtils.encodeURIComponent(ring1State.id)}"
      ) +
      ring2StateOption.fold("")(ring2State =>
        s"&r2=${js.URIUtils.encodeURIComponent(ring2State.id)}"
      ) +
      ring3StateOption.fold("")(ring3State =>
        s"&r3=${js.URIUtils.encodeURIComponent(ring3State.id)}"
      ) +
      s"&u=Wu${js
          .URIUtils
          .encodeURIComponent(utilityState.runeState.shortState())}"
  }
}

@main
def main(): Unit = {

  println(s"DEV MODE: ${BuildInfo.isDev}")

  if (rings.size != rings.map(_.id).toSet.size) {
    throw IllegalStateException(
      "Rings have conflicting ids" +
        rings.groupBy(_.id).filter((k, v) => v.size > 1).keySet
    )
  }
  if (gems.size != gems.map(_.id).toSet.size) {
    throw IllegalStateException(
      "Gems have conflicting ids" +
        gems.groupBy(_.id).filter((k, v) => v.size > 1).keySet
    )
  }
  if (runes.size != runes.map(_.id).toSet.size) {
    throw IllegalStateException(
      "Runes have conflicting ids" +
        runes.groupBy(_.id).filter((k, v) => v.size > 1).keySet
    )
  }

  val params = new URLSearchParams(dom.window.location.search)
  val helmetState = ItemBuilder.createState(
    helmetPlagued,
    Option(params.get("h")),
  )
  val armorState = ItemBuilder.createState(
    armorPlagued,
    Option(params.get("a")),
  )
  val pantsState = ItemBuilder.createState(
    pantsPlagued,
    Option(params.get("p")),
  )
  val glovesState = ItemBuilder.createState(
    glovesPlagued,
    Option(params.get("g")),
  )

  val weaponState = WeaponBuilder.createState(
    weaponPlagued,
    Option(params.get("w")),
  )
  val shieldState = WeaponBuilder.createState(
    shieldPlagued,
    Option(params.get("s")),
  )
  val bowState = WeaponBuilder.createState(bowPlagued, Option(params.get("b")))

  val ring1State = RingBuilder.createState(Option(params.get("r1")))
  val ring2State = RingBuilder.createState(Option(params.get("r2")))
  val ring3State = RingBuilder.createState(Option(params.get("r3")))

  val utilityState = WeaponBuilder.createState(
    utilityPlagued,
    Option(params.get("u")),
  )

  val fullStateVar = Var(
    FullState(
      helmetState,
      armorState,
      pantsState,
      glovesState,
      weaponState,
      shieldState,
      bowState,
      utilityState,
      ring1State,
      ring2State,
      ring3State,
    )
  )

  fullStateVar.update { fullState =>
    Log.debug("Init fullState update with errors")
    Errors.errors(fullState)
  }

  val helmetStateVar = {
    fullStateVar.zoomLazy(_.helmetState) { (state, helmetState) =>
      Log.debug(
        s"Update helmetStateVar with fullStateVar.zoomLazy to ${helmetState
            .shortState()}"
      )
      state.copy(helmetState = helmetState)
    }
  }.distinct
  helmetStateVar.update { state =>
    Log.debug("Init helmetStateVar update with errors")
    Errors.errors(helmetPlagued, state)
  }
  val helmetComponent = ItemBuilder(helmetPlagued, helmetStateVar)

  val armorStateVar = {
    fullStateVar.zoomLazy(_.armorState)((state, armorState) => {
      Log.debug(
        s"Update armorStateVar with fullStateVar.zoomLazy to ${armorState
            .shortState()}"
      )
      state.copy(armorState = armorState)
    })
  }.distinct
  armorStateVar.update { state =>
    Log.debug("Init armorStateVar update with errors")
    Errors.errors(armorPlagued, state)
  }
  val armorComponent = ItemBuilder(armorPlagued, armorStateVar)

  val pantsStateVar = {
    fullStateVar.zoomLazy(_.pantsState)((state, pantsState) => {
      Log.debug(
        s"Update pantsStateVar with fullStateVar.zoomLazy to ${pantsState
            .shortState()}"
      )
      state.copy(pantsState = pantsState)
    })
  }.distinct
  pantsStateVar.update { state =>
    Log.debug("Init pantsStateVar update with errors")
    Errors.errors(pantsPlagued, state)
  }
  val pantsComponent = ItemBuilder(pantsPlagued, pantsStateVar)

  val glovesStateVar = {
    fullStateVar.zoomLazy(_.glovesState)((state, glovesState) => {
      Log.debug(
        s"Update glovesStateVar with fullStateVar.zoomLazy to ${glovesState
            .shortState()}"
      )
      state.copy(glovesState = glovesState)
    })
  }.distinct
  glovesStateVar.update { state =>
    Log.debug("Init glovesStateVar update with errors")
    Errors.errors(glovesPlagued, state)
  }
  val glovesComponent = ItemBuilder(glovesPlagued, glovesStateVar)

  val weaponStateVar = {
    fullStateVar.zoomLazy(_.weaponState)((state, weaponState) => {
      Log.debug(
        s"Update weaponStateVar with fullStateVar.zoomLazy to ${weaponState
            .shortState()}"
      )
      state.copy(weaponState = weaponState)
    })
  }.distinct
  weaponStateVar.update { state =>
    Log.debug("Init weaponStateVar update with errors")
    Errors.errors(weaponPlagued, state)
  }
  val weaponComponent = WeaponBuilder(weaponPlagued, weaponStateVar)

  val shieldStateVar = {
    fullStateVar.zoomLazy(_.shieldState)((state, shieldState) => {
      Log.debug(
        s"Update shieldStateVar with fullStateVar.zoomLazy to ${shieldState
            .shortState()}"
      )
      state.copy(shieldState = shieldState)
    })
  }.distinct
  shieldStateVar.update { state =>
    Log.debug("Init shieldStateVar update with errors")
    Errors.errors(shieldPlagued, state)
  }
  val shieldComponent = WeaponBuilder(shieldPlagued, shieldStateVar)

  val bowStateVar = {
    fullStateVar.zoomLazy(_.bowState)((state, bowState) => {
      Log.debug(
        s"Update bowStateVar with fullStateVar.zoomLazy to ${bowState
            .shortState()}"
      )
      state.copy(bowState = bowState)
    })
  }.distinct
  bowStateVar.update { state =>
    Log.debug("Init bowStateVar update with errors")
    Errors.errors(bowPlagued, state)
  }
  val bowComponent = WeaponBuilder(bowPlagued, bowStateVar)

  val utilityStateVar = {
    fullStateVar.zoomLazy(_.utilityState)((state, utilityState) => {
      Log.debug(
        s"Update utilityStateVar with fullStateVar.zoomLazy to ${utilityState
            .shortState()}"
      )
      state.copy(utilityState = utilityState)
    })
  }.distinct
  utilityStateVar.update { state =>
    Log.debug("Init utilityStateVar update with errors")
    Errors.errors(utilityPlagued, state)
  }
  val utilityComponent = UtilityBuilder(utilityPlagued, utilityStateVar)

  val ring1Var = {
    fullStateVar.zoomLazy(_.ring1StateOption)((state, ring1State) => {
      Log.debug(
        s"Update ring1Var with fullStateVar.zoomLazy to ${ring1State.map(_.id)}"
      )
      state.copy(ring1StateOption = ring1State)
    })
  }.distinct
  val ring1ErrorSignal = fullStateVar.signal.map(_.ring1Error)
  val ring1ShowModalVar = Var(false)
  val ring1Modal = Modal(
    ring1ShowModalVar,
    rings,
    ring => {
      ring1Var.update { _ =>
        Log.debug(s"Update ring1Var with $ring")
        Option(ring)
      }
      fullStateVar.update { fullState =>
        Log.debug(s"Update fullState with errors after ring1Var $ring update")
        Errors.errors(fullState)
      }
    },
  )
  val ring1ComponentFull = RingBuilder.ringComponentFull(
    ring1Var,
    ring1ErrorSignal,
    ring1ShowModalVar,
  )

  val ring2Var = {
    fullStateVar.zoomLazy(_.ring2StateOption)((state, ring2State) => {
      Log.debug(
        s"Update ring2Var with fullStateVar.zoomLazy to ${ring2State.map(_.id)}"
      )
      state.copy(ring2StateOption = ring2State)
    })
  }.distinct
  val ring2ErrorSignal = fullStateVar.signal.map(_.ring2Error)
  val ring2ShowModalVar = Var(false)
  val ring2Modal = Modal(
    ring2ShowModalVar,
    rings,
    ring => {
      ring2Var.update { _ =>
        Log.debug(s"Update ring2Var with $ring")
        Option(ring)
      }
      fullStateVar.update { fullState =>
        Log.debug(s"Update fullState with errors after ring2Var $ring update")
        Errors.errors(fullState)
      }
    },
  )
  val ring2ComponentFull = RingBuilder.ringComponentFull(
    ring2Var,
    ring2ErrorSignal,
    ring2ShowModalVar,
  )

  val ring3Var = {
    fullStateVar.zoomLazy(_.ring3StateOption)((state, ring3State) => {
      Log.debug(
        s"Update ring3Var with fullStateVar.zoomLazy to ${ring3State.map(_.id)}"
      )
      state.copy(ring3StateOption = ring3State)
    })
  }.distinct
  val ring3ErrorSignal = fullStateVar.signal.map(_.ring3Error)
  val ring3ShowModalVar = Var(false)
  val ring3Modal = Modal(
    ring3ShowModalVar,
    rings,
    ring => {
      ring3Var.update { _ =>
        Log.debug(s"Update ring1Var with $ring")
        Option(ring)
      }
      fullStateVar.update { fullState =>
        Log.debug(s"Update fullState with errors after ring3Var $ring update")
        Errors.errors(fullState)
      }
    },
  )
  val ring3ComponentFull = RingBuilder.ringComponentFull(
    ring3Var,
    ring3ErrorSignal,
    ring3ShowModalVar,
  )

  renderOnDomContentLoaded(
    container = dom.document.querySelector("#app"),
    rootNode = {
      div(
        ring1Modal,
        ring2Modal,
        ring3Modal,
        div(
          fullStateVar.signal -->
            (state => {
              dom
                .window
                .history
                .replaceState(null, "", s"?${state.shortState()}")
            }),
          cls := "grid-container",
          div(
            cls := "header-row",
            h1("Unofficial No Rest for the Wicked Toolkit"),
            button(
              cls := "copy-button",
              "Copy build link",
              onClick --> { _ =>
                dom
                  .window
                  .navigator
                  .clipboard
                  .writeText(dom.window.location.href)
              },
            ),
          ),
          div(
            cls := "grid-item",
            cls := "row-span-1 col-span-full",
            utilityComponent,
          ),
          div(cls := "grid-item", cls := "row-span-3", weaponComponent),
          div(cls := "grid-item", cls := "row-span-3", shieldComponent),
          div(cls := "grid-item", cls := "row-span-3", bowComponent),
          div(cls := "grid-item", cls := "row-span-3", helmetComponent),
          div(cls := "grid-item", cls := "row-span-3", armorComponent),
          div(cls := "grid-item", cls := "row-span-2", ring1ComponentFull),
          div(cls := "grid-item", cls := "row-span-2", ring2ComponentFull),
          div(cls := "grid-item", cls := "row-span-3", pantsComponent),
          div(cls := "grid-item", cls := "row-span-3", glovesComponent),
          div(cls := "grid-item", cls := "row-span-2", ring3ComponentFull),
        ),
        div(
          "Images are from",
          a(
            "Amasoful's sheet",
            href :=
              "https://docs.google.com/spreadsheets/d/1TE3qDU8Hfhcrl4mMQvI4eatCeItmr-UySZCbVRoGG0M/edit?gid=1268781098#gid=1268781098",
          ),
        ),
      )
    },
  )
}

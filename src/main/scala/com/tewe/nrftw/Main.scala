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
      )
  }
}

@main
def main(): Unit = {

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

  val fullStateVar = Var(
    FullState(
      helmetState,
      armorState,
      pantsState,
      glovesState,
      weaponState,
      shieldState,
      bowState,
      ring1State,
      ring2State,
      ring3State,
    )
  )

  fullStateVar.update(fullState => Errors.errors(fullState))

  val helmetStateVar = {
    fullStateVar.zoomLazy(_.helmetState) { (state, helmetState) =>
      state.copy(helmetState = helmetState)
    }
  }
  helmetStateVar.update(state => Errors.errors(helmetPlagued, state))
  val helmetComponent = ItemBuilder(helmetPlagued, helmetStateVar)

  val armorStateVar = {
    fullStateVar.zoomLazy(_.armorState)((state, armorState) =>
      state.copy(armorState = armorState)
    )
  }
  armorStateVar.update(state => Errors.errors(armorPlagued, state))
  val armorComponent = ItemBuilder(armorPlagued, armorStateVar)

  val pantsStateVar = {
    fullStateVar.zoomLazy(_.pantsState)((state, pantsState) =>
      state.copy(pantsState = pantsState)
    )
  }
  pantsStateVar.update(state => Errors.errors(pantsPlagued, state))
  val pantsComponent = ItemBuilder(pantsPlagued, pantsStateVar)

  val glovesStateVar = {
    fullStateVar.zoomLazy(_.glovesState)((state, glovesState) =>
      state.copy(glovesState = glovesState)
    )
  }
  glovesStateVar.update(state => Errors.errors(glovesPlagued, state))
  val glovesComponent = ItemBuilder(glovesPlagued, glovesStateVar)

  val weaponStateVar = {
    fullStateVar.zoomLazy(_.weaponState)((state, weaponState) =>
      state.copy(weaponState = weaponState)
    )
  }
  weaponStateVar.update(state => Errors.errors(weaponPlagued, state))
  val weaponComponent = WeaponBuilder(weaponPlagued, weaponStateVar)

  val shieldStateVar = {
    fullStateVar.zoomLazy(_.shieldState)((state, shieldState) =>
      state.copy(shieldState = shieldState)
    )
  }
  shieldStateVar.update(state => Errors.errors(shieldPlagued, state))
  val shieldComponent = WeaponBuilder(shieldPlagued, shieldStateVar)

  val bowStateVar = {
    fullStateVar.zoomLazy(_.bowState)((state, bowState) =>
      state.copy(bowState = bowState)
    )
  }
  bowStateVar.update(state => Errors.errors(bowPlagued, state))
  val bowComponent = WeaponBuilder(bowPlagued, bowStateVar)

  val ring1Var = {
    fullStateVar.zoomLazy(_.ring1StateOption)((state, ring1State) =>
      state.copy(ring1StateOption = ring1State)
    )
  }
  val ring1ErrorSignal = fullStateVar.signal.map(_.ring1Error)
  val ring1ShowModalVar = Var(false)
  val ring1Modal = Modal(
    ring1ShowModalVar,
    rings,
    ring => {
      ring1Var.update(_ => Option(ring))
      fullStateVar.update(fullState => Errors.errors(fullState))
    },
  )
  val ring1ComponentFull = RingBuilder.ringComponentFull(
    ring1Var,
    ring1ErrorSignal,
    ring1ShowModalVar,
  )

  val ring2Var = {
    fullStateVar.zoomLazy(_.ring2StateOption)((state, ring2State) =>
      state.copy(ring2StateOption = ring2State)
    )
  }
  val ring2ErrorSignal = fullStateVar.signal.map(_.ring2Error)
  val ring2ShowModalVar = Var(false)
  val ring2Modal = Modal(
    ring2ShowModalVar,
    rings,
    ring => {
      ring2Var.update(_ => Option(ring))
      fullStateVar.update(fullState => Errors.errors(fullState))
    },
  )
  val ring2ComponentFull = RingBuilder.ringComponentFull(
    ring2Var,
    ring2ErrorSignal,
    ring2ShowModalVar,
  )

  val ring3Var = {
    fullStateVar.zoomLazy(_.ring3StateOption)((state, ring3State) =>
      state.copy(ring3StateOption = ring3State)
    )
  }
  val ring3ErrorSignal = fullStateVar.signal.map(_.ring3Error)
  val ring3ShowModalVar = Var(false)
  val ring3Modal = Modal(
    ring3ShowModalVar,
    rings,
    ring => {
      ring3Var.update(_ => Option(ring))
      fullStateVar.update(fullState => Errors.errors(fullState))
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

package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import org.scalajs.dom.URLSearchParams

@JSImport("@find/**/HelloWorld.less", JSImport.Namespace)
@js.native private object Stylesheet extends js.Object

val _ = Stylesheet // Use import to prevent DCE
@main def main(): Unit = {

  case class FullState(
    helmetState: ItemState,
    armorState: ItemState,
    pantsState: ItemState,
    glovesState: ItemState,
    weaponState: ItemState,
    shieldState: ItemState,
    bowState: ItemState,
  ) {
    def shortState(): String = {
      s"h=${js.URIUtils.encodeURIComponent(helmetState.shortState())}&a=${js.URIUtils.encodeURIComponent(armorState.shortState())}&p=${js.URIUtils.encodeURIComponent(pantsState.shortState())}&g=${js.URIUtils.encodeURIComponent(glovesState.shortState())}&w=${js.URIUtils.encodeURIComponent(weaponState.shortState())}&s=${js.URIUtils.encodeURIComponent(shieldState.shortState())}&b=${js.URIUtils.encodeURIComponent(bowState.shortState())}"
    }
  }

  val params = new URLSearchParams(dom.window.location.search)
  val helmetState = ItemBuilder.createState(helmetPlagued, Option(params.get("h")))
  val armorState = ItemBuilder.createState(armorPlagued, Option(params.get("a")))
  val pantsState = ItemBuilder.createState(pantsPlagued, Option(params.get("p")))
  val glovesState = ItemBuilder.createState(glovesPlagued, Option(params.get("g")))

  val weaponState = ItemBuilder.createState(weaponPlagued, Option(params.get("w")))
  val shieldState = ItemBuilder.createState(shieldPlagued, Option(params.get("s")))
  val bowState = ItemBuilder.createState(bowPlagued, Option(params.get("b")))

  val fullStateVar = Var(FullState(
    helmetState,
    armorState,
    pantsState,
    glovesState,
    weaponState,
    shieldState,
    bowState
  ))

  val helmetStateVar = fullStateVar.zoomLazy(_.helmetState){(state, helmetState) =>
    state.copy(helmetState = helmetState)
  }
  val helmetComponent = ItemBuilder(helmetPlagued, helmetStateVar)

  val armorStateVar = fullStateVar.zoomLazy(_.armorState)((state, armorState) => state.copy(armorState = armorState))
  val armorComponent = ItemBuilder(armorPlagued, armorStateVar)

  val pantsStateVar = fullStateVar.zoomLazy(_.pantsState)((state, pantsState) => state.copy(pantsState = pantsState))
  val pantsComponent = ItemBuilder(pantsPlagued, pantsStateVar)

  val glovesStateVar = fullStateVar.zoomLazy(_.glovesState)((state, glovesState) => state.copy(glovesState = glovesState))
  val glovesComponent = ItemBuilder(glovesPlagued, glovesStateVar)

  val weaponStateVar = fullStateVar.zoomLazy(_.weaponState)((state, weaponState) => state.copy(weaponState = weaponState))
  val weaponComponent = ItemBuilder(weaponPlagued, weaponStateVar)

  val shieldStateVar = fullStateVar.zoomLazy(_.shieldState)((state, shieldState) => state.copy(shieldState = shieldState))
  val shieldComponent = ItemBuilder(shieldPlagued, shieldStateVar)

  val bowStateVar = fullStateVar.zoomLazy(_.bowState)((state, bowState) => state.copy(bowState = bowState))
  val bowComponent = ItemBuilder(bowPlagued, bowStateVar)

  val ring1Component = RingBuilder()

  val firstRingVar = Var(rings.head)
  val showModalVar = Var(false)
  val ringModal = Modal(showModalVar, rings, ring => firstRingVar.update(_ => ring))
  val ringComponentFull1 = RingBuilder.ringComponentFull(firstRingVar, showModalVar)

  renderOnDomContentLoaded(
    container = dom.document.querySelector("#app"),
    rootNode = {
      div(
        ringModal,
      div(
        fullStateVar.signal --> (state => dom.window.history.replaceState(null, "", s"?${state.shortState()}")),
        cls := "grid-container",
        div(
          cls:= "grid-item",
          ringComponentFull1,
        ),
        div(
          cls:= "grid-item",
          ring1Component,
        ),
        div(
          cls:= "grid-item",
          ring1Component,
        ),
        div(
          cls:= "grid-item",
          weaponComponent,
        ),
        div(
          cls:= "grid-item",
          shieldComponent,
        ),
        div(
          cls:= "grid-item",
          bowComponent,
        ),
        div(
          cls:= "grid-item",
          helmetComponent,
        ),
        div(
          cls:= "grid-item",
          armorComponent,
        ),
        div(
          cls:= "grid-item",
        ),
        div(
          cls:= "grid-item",
          pantsComponent,
        ),
        div(
          cls:= "grid-item",
          glovesComponent,
        ),
        div(
          cls:= "grid-item",
        ),
      ),
      div(
        "Images are from",
        a("Amasoful's sheet", href:= "https://docs.google.com/spreadsheets/d/1TE3qDU8Hfhcrl4mMQvI4eatCeItmr-UySZCbVRoGG0M/edit?gid=1268781098#gid=1268781098")
          )
        )
    }
  )
}

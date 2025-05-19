package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("@find/**/HelloWorld.less", JSImport.Namespace)
@js.native private object Stylesheet extends js.Object

val _ = Stylesheet // Use import to prevent DCE
@main def main(): Unit = {

  renderOnDomContentLoaded(
    container = dom.document.querySelector("#app"),
    rootNode = {
      div(
        cls := "grid-container",
        div(
          cls:= "grid-item",
          WeaponBuilder(weaponPlagued),
        ),
        div(
          cls:= "grid-item",
          WeaponBuilder(shieldPlagued),
        ),
        div(
          cls:= "grid-item",
          WeaponBuilder(bowPlagued),
        ),
        div(
          cls:= "grid-item",
          ItemBuilder(helmetPlagued),
        ),
        div(
          cls:= "grid-item",
          ItemBuilder(armorPlagued),
        ),
        div(
          cls:= "grid-item",
        ),
        div(
          cls:= "grid-item",
          ItemBuilder(pantsPlagued),
        ),
        div(
          cls:= "grid-item",
          ItemBuilder(glovesPlagued),
        ),
        div(
          cls:= "grid-item",
        ),
      )
    }
  )
}

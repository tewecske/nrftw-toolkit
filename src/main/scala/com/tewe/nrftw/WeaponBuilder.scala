package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object WeaponBuilder {

  val _ = Stylesheet // Use import to prevent DCE

  def apply(config: ItemBuilderConfig, stateVar: Var[ItemState]): HtmlElement = {

     div(
       cls := "weapon-card",
       div(
         cls := "weapon-header",
         h1(cls := "weapon-name", config.itemSlot.toString),
         div(cls := "weapon-level", span("13")),
         // div(
         //   cls := "weapon-type-select",
         //   select(
         //     option(selected := true, "Two-Handed Great Club"),
         //     option("Two-Handed Greatsword")
         //   )
         // )
       ),


    EnchantmentsBuilder.enchantmentsSelect(config, stateVar)
     )

  }
}

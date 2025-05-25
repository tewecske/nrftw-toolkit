package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object GemsBuilder {

  def apply(config: ItemBuilderConfig, stateVar: Var[ItemState]): HtmlElement = {
       div(
         cls := "section",
         div(
           cls := "section-header",
           div(cls := "section-title", "Gems"),
           div(cls := "section-count", "1/1")
         ),
         select(
            config.gems.map(gem => option(value := gem.value, gem.value))
         )
       )
  }
}



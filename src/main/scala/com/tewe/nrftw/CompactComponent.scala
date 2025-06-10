package com.tewe.nrftw

import com.raquo.laminar.api.L.{*, given}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object CompactComponent {
  def compactComponent(
    group: String,
    onClick: Mod[Div],
    imageSrc: String,
    mods: Mod[Div]*
  ) = {
    div(
      cls(s"$group-container-compact"),
      onClick,
      div(
        cls(s"compact-$group-item"),
        img(cls(s"compact-$group-icon"), src(imageSrc)),
        div(cls(s"compact-$group-text"), mods),
      ),
    )
  }
}

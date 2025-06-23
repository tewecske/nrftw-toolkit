package com.tewe.nrftw

import scala.util.Try

object Config {
  val debugLogEnabled = BuildInfo.isDev
  def debugLogWhenFunction[A] : Try[A] => Boolean = _ => debugLogEnabled
}

package com.tewe.nrftw

object Log {
  def debug(text: String) = {
    if (Config.debugLogEnabled)
      println(text)
  }
  def info(text: String) = {
    if (Config.infoLogEnabled)
      println(text)
  }
}

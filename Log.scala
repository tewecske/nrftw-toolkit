package com.tewe.nrftw

object Log {
  def debug(text: String) = {
    if (Config.debugLogEnabled) println(text)
  }
}


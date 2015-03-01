package com.github.petekneller.playground.calc

import scalaz.\/

object Calculator {

  def run(input: String): String \/ Double = {
    \/.fromTryCatchNonFatal(input.toDouble).leftMap(t => s"Invalid literal: '$input' is not a floating point number; full error follows: \n ${t.toString}")
  }

}

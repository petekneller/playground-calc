package com.github.petekneller.playground.calc.utilities

import scala.concurrent.duration._
import com.github.petekneller.playground.calc.{Result, Calculator}

object ElapsedTimeAnnotation {
  def apply(delegate: Calculator): String => (Result, Duration) = (input: String) => {
    val begin = now
    val result = delegate(input)
    val end = now
    (result, (end - begin) nanos)
  }

  def now: Long = System.nanoTime()
}

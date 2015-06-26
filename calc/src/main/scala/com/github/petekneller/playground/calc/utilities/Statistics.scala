package com.github.petekneller.playground.calc.utilities

import com.github.petekneller.playground.calc.Calculator

import scala.concurrent.duration.Duration
import Duration._

object Statistics {

  case class Results(numberIterationsSuccessful: Int, mean: Duration)

  def apply(numberIterations: Int, calculator: Calculator): Results = {
    val timedCalculator = ElapsedTimeAnnotation(calculator)
    
    val outcomes = 0 until numberIterations map { _ =>
      timedCalculator("(+ 1 2 3)")
    }

    val numSuccesses = outcomes.filter(_._1.isRight).size
    val meanDuration = outcomes.map(_._2).fold(fromNanos(0))(_ + _)
    Results(numSuccesses, meanDuration)
  }

}

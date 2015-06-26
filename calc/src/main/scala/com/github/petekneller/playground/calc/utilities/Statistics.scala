package com.github.petekneller.playground.calc.utilities

import com.github.petekneller.playground.calc.Calculator

import scala.concurrent.duration.Duration
import Duration._

object Statistics {

  case class Results(numberIterationsSuccessful: Int, mean: Duration, median: Duration)

  def apply(numberIterations: Int, calculator: Calculator): Results = {
    val timedCalculator = ElapsedTimeAnnotation(calculator)
    
    val outcomes = 0 until numberIterations map { _ =>
      timedCalculator("(+ 1 2 3)")
    }
    val durations = outcomes.map(_._2)

    val numSuccesses = outcomes.filter(_._1.isRight).size
    val mean = durations.fold(fromNanos(0))(_ + _) / numberIterations
    val median = durations.sorted.apply(durations.length / 2)
    Results(numSuccesses, mean, median)
  }

}

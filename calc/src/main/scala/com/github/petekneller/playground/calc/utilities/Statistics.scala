package com.github.petekneller.playground.calc.utilities

import com.github.petekneller.playground.calc.Calculator

object Statistics {

  case class Results(numberIterationsSuccessful: Int)

  def apply(numberIterations: Int, calculator: Calculator): Results = {
    val outcomes = 0 until numberIterations map { _ =>
      calculator("(+ 1 2 3)")
    }
    Results(outcomes.filter(_.isRight).size)
  }

}

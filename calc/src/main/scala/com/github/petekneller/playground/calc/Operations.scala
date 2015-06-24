package com.github.petekneller.playground.calc

import scalaz.\/-

object Operations {

  val addition: OperatorBinding = "+" -> foldingOperator(_ + _)
  val subtraction: OperatorBinding = "-" -> foldingOperator(_ - _)
  val multiplication: OperatorBinding = "*" -> foldingOperator(_ * _)
  val division: OperatorBinding = "/" -> foldingOperator(_ / _)

  private def foldingOperator(f: (Double, Double) => Double): List[Double] => Result = { operands =>
    \/-(operands.reduceLeft[Double]{ case (acc, m) => f(acc, m) })
  }
}

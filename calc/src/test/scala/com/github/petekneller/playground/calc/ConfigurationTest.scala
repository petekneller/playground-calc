package com.github.petekneller.playground.calc

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

import scalaz.{\/-, -\/}

class ConfigurationTest extends FunSuite with ShouldMatchers with CalculatorMatchers {

  test("default operators can be overridden with adhoc operators") {

    val negation: (String, List[Double] => Result) = ("¬", {
      case arg :: Nil => \/-(-1 * arg)
      case _ => -\/("Invalid arguments: negation takes only one argument")
    })
    val calculator: Calculator = Calculator.run(_, negation :: Nil)

    calculator("(+ 1 2)") should failWith(include("'+' not recognised"))
    calculator("(¬ 2)") should succeedWith(equal(-2))
  }

}

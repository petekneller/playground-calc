package com.github.petekneller.playground.calc

import org.scalatest.{FlatSpec, Matchers}
import scalaz.{-\/, \/-}

class ConfigurationTestFixture extends FlatSpec with Matchers with CalculatorMatchers {

  def configurationTests(suiteName: String, calculator: List[OperatorBinding] => Calculator): Unit = {

    suiteName should "allow default operators to be overridden with adhoc operators" in {

      val negation: OperatorBinding = ("¬", {
        case arg :: Nil => \/-(-1 * arg)
        case _ => -\/("Invalid arguments: negation takes only one argument")
      })

      val configured = calculator(negation :: Nil)

      configured("(+ 1 2)") should failWith(include("'+' not recognised"))
      configured("(¬ 2)") should succeedWith(equal(-2))
    }

  }

}

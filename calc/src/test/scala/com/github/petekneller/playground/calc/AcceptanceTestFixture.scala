package com.github.petekneller.playground.calc

import org.scalatest.{FlatSpec, Matchers}

trait AcceptanceTestFixture extends FlatSpec with Matchers with CalculatorMatchers {

  def acceptanceTests(suiteName: String, calculator: Calculator): Unit = {

    suiteName should "evaluate literal numerals" in {

      calculator("1") should succeedWith(equal(1))
      calculator("23") should succeedWith(equal(23))
    }

    it should "respond with a useful error message when a literal argument is not a floating point number" in {

      calculator("fooey!") should failWith(startWith("Invalid literal: 'fooey!' is not a floating point number"))
    }

    it should "evaluate operations in a prefix, parenthesis-delimited fashion" in {

      calculator("(+ 1 2)") should succeedWith(equal(3))
    }

    it should "support the basic arithmetic operations" in {

      calculator("(+ 1 2)") should succeedWith(equal(3))
      calculator("(- 1 2)") should succeedWith(equal(-1))
      calculator("(- 1 2 2)") should succeedWith(equal(-3))
      calculator("(* 1 2)") should succeedWith(equal(2))
      calculator("(* 1 2 2)") should succeedWith(equal(4))
      calculator("(/ 1 2)") should succeedWith(equal(0.5))
      calculator("(/ 1 2 2)") should succeedWith(equal(0.25))
    }

    it should "support nested expressions" in {

      calculator("(+ 1 (* 2 3))") should succeedWith(equal(7))
      calculator("(* (+ 3 4 3) (/ 4 2))") should succeedWith(equal(20))
    }
  }

}

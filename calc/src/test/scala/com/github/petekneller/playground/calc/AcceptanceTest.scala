package com.github.petekneller.playground.calc

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class AcceptanceTest extends FlatSpec with ShouldMatchers {

  val calculator = Calculator.run _

  "A Polish notation calculator" should "evaluate literal numerals" in {

    calculator("1") should equal (1)

    calculator("23") should equal (23)
  }

  it should "respond with a useful error message when an argument is not a floating-point number" in {

    intercept[RuntimeException] {
      calculator("fooey!") should equal(23)
    }
  }

}

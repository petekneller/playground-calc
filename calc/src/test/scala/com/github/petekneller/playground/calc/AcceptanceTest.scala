package com.github.petekneller.playground.calc

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

import scalaz.\/-

class AcceptanceTest extends FlatSpec with ShouldMatchers {

  val calculator = Calculator.run _

  "A Polish notation calculator" should "evaluate literal numerals" in {

    calculator("1") should equal (\/-(1))
    calculator("23") should equal (\/-(23))
  }

  it should "respond with a useful error message when an argument is not a floating-point number" in {

    val result = calculator("fooey!")
    result.isLeft should be (true)
    result.swap.toOption.get should startWith ("Invalid literal: 'fooey!' is not a floating point number")
  }

}

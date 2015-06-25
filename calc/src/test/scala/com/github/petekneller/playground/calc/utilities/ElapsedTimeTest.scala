package com.github.petekneller.playground.calc.utilities

import com.github.petekneller.playground.calc.{Calculator, CalculatorMatchers}
import org.scalatest.{FlatSpec, Matchers}
import Thread.sleep

import scala.concurrent.duration.Duration.DurationIsOrdered

class ElapsedTimeTest extends FlatSpec with CalculatorMatchers with Matchers {

  "an elapsed time decorator" should "pass arguments and results unchanged" in {

    val calc = ElapsedTimeAnnotation(Calculator.run(_))
    val (result, _) = calc("(* 1 2 3)")
    result should succeedWith(equal(6))
  }

  it should "return the approximate elapsed time" in {

    val calc = (input: String) => { sleep(1000); Calculator.run(input) }
    val wrapped = ElapsedTimeAnnotation(calc)
    val (_, duration) = wrapped("(- 3 2)")
    println(duration)
    duration.toMillis should (be >= 1000L and be < 1100L)
  }
}

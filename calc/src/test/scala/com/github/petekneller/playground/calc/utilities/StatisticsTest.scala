package com.github.petekneller.playground.calc.utilities

import com.github.petekneller.playground.calc.Calculator
import com.github.petekneller.playground.calc.utilities.Statistics.Results
import org.scalatest.{Matchers, FlatSpec}

import scala.util.Random
import scalaz.-\/

class StatisticsTest extends FlatSpec with Matchers {

  val n = 1000

  "a load tester for calculators" should "run a configurable number of passes and report some statistics" in {

    val Results(numSuccesses) = Statistics(n, Calculator.run(_))
    numSuccesses should equal(1000)
  }

  it should "should report number of successes" in {

    val failsHalfTheTime: Calculator = (input: String) => { if (Random.nextBoolean()) -\/("argh!") else Calculator.run(input) }
    val Results(numSuccesses) = Statistics(n, failsHalfTheTime)
    numSuccesses should (be > 0 and be < 1000)
  }

}

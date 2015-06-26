package com.github.petekneller.playground.calc.utilities

import com.github.petekneller.playground.calc.Calculator
import com.github.petekneller.playground.calc.utilities.Statistics.Results
import org.scalatest.{Matchers, FlatSpec}

import scala.util.Random
import java.lang.Thread.sleep
import scalaz.-\/

class StatisticsTest extends FlatSpec with Matchers {

  "a load tester for calculators" should "run a configurable number of passes and report some statistics" in {

    val results = Statistics(n, Calculator.run(_))
    results.numberIterationsSuccessful should equal(n)
  }

  it should "report number of successes" in {

    val results = Statistics(n, failsHalfTheTime)
    results.numberIterationsSuccessful should (be > 0 and be < 1000)
  }

  it should "report mean time of the iterations" in {

    val results = Statistics(n, runsABitSlow)
    results.mean.toMillis should (be > 0L and be < 100L)
  }

  it should "report median time of the iterations" in {

    val results = Statistics(n, runsABitSlow)
    results.median.toMillis should (be > 0L and be < 100L)
  }

  val n = 10
  val failsHalfTheTime: Calculator = (input: String) => { if (Random.nextBoolean()) -\/("argh!") else Calculator.run(input) }
  val runsABitSlow: Calculator = (input: String) => { sleep(Random.nextInt(10)); Calculator.run(input) }
}

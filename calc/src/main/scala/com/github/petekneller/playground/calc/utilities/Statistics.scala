package com.github.petekneller.playground.calc.utilities

import java.util.concurrent.TimeUnit

import com.github.petekneller.playground.calc.Calculator

import scala.concurrent.duration.Duration

object Statistics {

  case class Results(
    numberIterationsSuccessful: Int,
    mean: Duration,
    median: Duration,
    minimum: Duration,
    maximum: Duration,
    stdDeviation: Duration
  )

  def apply(numberIterations: Int, calculator: Calculator): Results = {
    val timedCalculator = ElapsedTimeAnnotation(calculator)
    
    val outcomes = 0 until numberIterations map { _ =>
      timedCalculator("(+ 1 2 3)")
    }
    val durations = outcomes.map(_._2)

    val numSuccesses = outcomes.filter(_._1.isRight).size
    val sum = durations.sum
    val mean = sum / numberIterations
    val median = durations.sorted.apply(durations.length / 2)
    val stdDeviation = squareRoot(durations.map(a => squared(a - mean)).sum / numberIterations)

    Results(numSuccesses, mean, median, durations.min, durations.max, stdDeviation)
  }


  implicit val DurationIsNumeric: Numeric[Duration] = new Numeric[Duration] {
    override def toDouble(x: Duration): Double = x.toDouble
    override def toFloat(x: Duration): Float = x.toFloat
    override def toInt(x: Duration): Int = x.toInt
    override def toLong(x: Duration): Long = x.toLong
    override def fromInt(x: Int): Duration = Duration(x, TimeUnit.NANOSECONDS)

    override def plus(x: Duration, y: Duration): Duration = x + y
    override def negate(x: Duration): Duration = x.neg
    override def times(x: Duration, y: Duration): Duration = x * y
    override def minus(x: Duration, y: Duration): Duration = x - y
    override def compare(x: Duration, y: Duration): Int = Duration.DurationIsOrdered.compare(x, y)
  }

  private def squared(duration: Duration): Duration = Duration(Math.pow(duration.toNanos.toDouble, 2).round, TimeUnit.NANOSECONDS)

  private def squareRoot(duration: Duration): Duration = Duration(Math.sqrt(duration.toNanos.toDouble).round, TimeUnit.NANOSECONDS)

}

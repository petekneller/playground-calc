package com.github.petekneller.playground.calc

import org.scalatest.matchers.{MatchResult, Matcher}
import scalaz.{\/-, -\/}

trait CalculatorMatchers {

  def succeedWith(answerMatcher: Matcher[Double]): Matcher[CalcResult] = new Matcher[CalcResult] {
    override def apply(result: CalcResult): MatchResult = {
      result match {
        case -\/(msg) => MatchResult(false, "Result was not right", s"Result was $result")
        case \/-(answer) => answerMatcher(answer)
      }
    }
  }

  def failWith(failureMessageMatcher: Matcher[String]): Matcher[CalcResult] = new Matcher[CalcResult] {
    override def apply(result: CalcResult): MatchResult = {
      result match {
        case -\/(msg) => failureMessageMatcher(msg)
        case \/-(answer) => MatchResult(false, "Result was not left", s"Result was $result")
      }
    }
  }

}

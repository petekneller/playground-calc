package com.github.petekneller.playground.calc

import org.scalatest.matchers.{MatchResult, Matcher}
import scalaz.{\/-, -\/}

trait CalculatorMatchers {

  def succeedWith(answerMatcher: Matcher[Double]): Matcher[Result] = new Matcher[Result] {
    override def apply(result: Result): MatchResult = {
      result match {
        case -\/(msg) => MatchResult(false, "Result was not right", s"Result was $result")
        case \/-(answer) => answerMatcher(answer)
      }
    }
  }

  def failWith(failureMessageMatcher: Matcher[String]): Matcher[Result] = new Matcher[Result] {
    override def apply(result: Result): MatchResult = {
      result match {
        case -\/(msg) => failureMessageMatcher(msg)
        case \/-(answer) => MatchResult(false, "Result was not left", s"Result was $result")
      }
    }
  }

}

package com.github.petekneller.playground.calc

import org.scalatest.matchers.{MatchResult, Matcher}

import scalaz.{-\/, \/-}

class AcceptanceTest extends AcceptanceTestFixture {

  acceptanceTests(Calculator.run _)

}

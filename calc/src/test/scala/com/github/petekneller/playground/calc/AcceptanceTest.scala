package com.github.petekneller.playground.calc

import org.scalatest.matchers.{MatchResult, Matcher}

import scalaz.{-\/, \/-}

class AcceptanceTest extends AcceptanceTestFixture {

  acceptanceTests("A Polish notation calculator", Calculator.run(_))

}

package com.github.petekneller.playground.calc.http

import com.github.petekneller.playground.calc.AcceptanceTestFixture
import io.shaka.http.Request._
import io.shaka.http.Status._
import io.shaka.http.{Http, Status}
import org.scalatest.matchers.ShouldMatchers

import scalaz.{-\/, \/}

class AcceptanceTest extends AcceptanceTestFixture with ShouldMatchers with HttpFixtures {

  "A calculator served over http" should "respond to GET requests by evaluating the given expression" in {
    withCalcHttp(new Calc) {

      val aSimpleExpression = Http.http(GET(s"http://localhost:8001/calc/result/${encoded("(+ 1 2)")}"))
      aSimpleExpression.status should be (OK)
      aSimpleExpression.entityAsString should be("3.0")

      val aBigExpression = Http.http(GET(s"http://localhost:8001/calc/result/${encoded("(* (+ 3 4 3) (/ 4 2))")}"))
      aBigExpression.status should be (OK)
      aBigExpression.entityAsString should be("20.0")
    }
  }

  acceptanceTests("A calculator served over http", { (input: String) =>
    withCalcHttp(new Calc) {
      val res = Http.http(GET(s"http://localhost:8001/calc/result/${encoded(input)}"))
      res.status match {
        case Status.OK => \/.fromTryCatchNonFatal(res.entityAsString.toDouble).leftMap(_.toString)
        case _ => -\/(res.entityAsString)
      }
    }
  })

}

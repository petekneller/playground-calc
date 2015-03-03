package com.github.petekneller.playground.calc.http

import com.github.petekneller.playground.calc._
import io.shaka.http.{Status, Http}
import io.shaka.http.Request.GET
import io.shaka.http.Status.OK
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

import scalaz.{-\/, \/-}

class ConfigurationTest extends FunSuite with ShouldMatchers with CalculatorMatchers with HttpFixtures {

  test("default operators can be overridden with adhoc operators") {

    val negation: (String, List[Double] => CalcResult) = ("¬", {
      case arg :: Nil => \/-(-1 * arg)
      case _ => -\/("Invalid arguments: negation takes only one argument")
    })

    withCalcHttp(new Calc(negation :: Nil)) {

      val res1 = Http.http(GET(s"http://localhost:8001/calc/result/${encoded("(+ 1 2)")}"))
      res1.status should be (Status.INTERNAL_SERVER_ERROR)
      res1.entityAsString should include("'+' not recognised")

      val res2 = Http.http(GET(s"http://localhost:8001/calc/result/${encoded("(¬ 2)")}"))
      res2.status should be (OK)
      res2.entityAsString should be("-2.0")
    }
  }

}

package com.github.petekneller.playground.calc.http

import com.github.petekneller.playground.calc._
import io.shaka.http.{Status, Http}
import io.shaka.http.Request.GET
import io.shaka.http.Status.OK
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

import scalaz.syntax.BindOps
import scalaz.{\/, -\/, \/-}

class ConfigurationTest extends ConfigurationTestFixture with HttpFixtures {

  configurationTests("A calculator served over http", {
    (operators: List[OperatorBinding]) =>
      (input: String) =>
        withCalcHttp(new OnlineCalculator(operators = operators)) { port =>

          val res = Http.http(GET(s"http://localhost:$port/calc/result/${encoded(input)}"))
          res.status match {
            case Status.OK => \/.fromTryCatchNonFatal(res.entityAsString.toDouble).leftMap(_.toString)
            case _ => -\/(res.entityAsString)
          }
        }
  })

}

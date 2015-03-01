package com.github.petekneller.playground.calc.http

import java.net.URLEncoder

import io.shaka.http.Http
import io.shaka.http.Request._
import io.shaka.http.Status._
import org.scalatest.{FlatSpec, Matchers}

class AcceptanceTests extends FlatSpec with Matchers {

  //TODO Reuse the acceptance tests from the -calc package here

  "A calculator served over http" should "respond to GET requests by evaluating the given expression" in {
    withCalcHttp {

      val aSimpleExpression = Http.http(GET(s"http://localhost:8001/calc/result/${URLEncoder.encode("(+ 1 2)", "utf-8")}"))
      aSimpleExpression.status should be (OK)
      aSimpleExpression.entityAsString should be("3.0")

      val aBigExpression = Http.http(GET(s"http://localhost:8001/calc/result/${URLEncoder.encode("(* (+ 3 4 3) (/ 4 2))", "utf-8")}"))
      aBigExpression.status should be (OK)
      aBigExpression.entityAsString should be("20.0")
    }
  }

  def withCalcHttp(block: => Unit): Unit = {
    try {
      Calc.start()
      block
    } finally {
      Calc.stop()
    }
  }

}

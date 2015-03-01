package com.github.petekneller.playground.calc.http

import java.net.URLEncoder

import io.shaka.http.Http
import io.shaka.http.Request._
import io.shaka.http.Status._
import org.scalatest.{FlatSpec, Matchers}

class AcceptanceTests extends FlatSpec with Matchers {

  "A calculator served over http" should "respond to GET requests by evaluating the given expression" in {
    withCalcHttp {

      val resp = Http.http(GET(s"http://localhost:8001/calc/result/${URLEncoder.encode("(* (+ 3 4 3) (/ 4 2))", "utf-8")}"))

      resp.status should be (OK)
      resp.entityAsString should be("20")
    }
  }

  def withCalcHttp(block: => Unit): Unit = {
    Calc.start()
    block
    Calc.stop()
  }

}

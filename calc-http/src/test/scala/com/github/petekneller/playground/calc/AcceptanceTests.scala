package com.github.petekneller.playground.calc

import java.net.URLEncoder

import io.shaka.http.Http._
import io.shaka.http.Request._
import io.shaka.http.Status._
import io.shaka.http.{Response, HttpServer}
import org.scalatest.{FlatSpec, Matchers}

class AcceptanceTests extends FlatSpec with Matchers {

  "A calculator served over http" should "respond to GET requests by evaluating the given expression" in {

    val server = HttpServer(8001).handler{ req => Response.ok.entity("20") }

    server.start()
    val resp = http(GET(s"http://localhost:8001/calc/result/${URLEncoder.encode("(* (+ 3 4 3) (/ 4 2))", "utf-8")}"))
    server.stop()

    resp.status should be (OK)
    resp.entityAsString should be("20")
  }

}

package com.github.petekneller.playground.calc

import org.scalatest.FunSuite
import com.googlecode.utterlyidle.{Application, ApplicationBuilder, Status, RequestBuilder}
import ApplicationBuilder.application
import java.net.URLEncoder

class ApiTest extends FunSuite {

  ignore("calculations can be executed by making a GET call to the calculation resource") {
    val build: Application = application(Calc.utterlyIdleApp).build()
    val response = build.handle(RequestBuilder.get("/calculation/" + URLEncoder.encode("(+ 1 1)", "utf-8")).build())
    assert(response.status() === Status.OK)
    assert(response.entity().toString === "2")
  }

}

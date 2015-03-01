package com.github.petekneller.playground.calc.http

import java.net.URLDecoder

import io.shaka.http.Request.GET
import io.shaka.http.RequestMatching._
import io.shaka.http.{HttpServer, Response}

object Calc {

  val calculator = com.github.petekneller.playground.calc.Calculator.run _

  val server = HttpServer(8001).handler{
    case GET(url"/calc/result/$expression") => {
      Response.ok.entity(calculator(URLDecoder.decode(expression, "utf-8")).toOption.get.toString)
    }
  }

  def start(): Unit = server.start()

  def stop(): Unit = server.stop()

}

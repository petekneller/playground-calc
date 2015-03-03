package com.github.petekneller.playground.calc.http

import java.net.URLDecoder

import com.github.petekneller.playground.calc.Calculator
import io.shaka.http.Request.GET
import io.shaka.http.RequestMatching._
import io.shaka.http.Response._
import io.shaka.http.{HttpServer, Status}

class Calc {

  val calculator: Calculator = com.github.petekneller.playground.calc.Calculator.run(_)

  val server = HttpServer(8001).handler{
    case GET(url"/calc/result/$expression") => {
      val result = calculator(URLDecoder.decode(expression, "utf-8"))
      result.fold(
        msg => respond(msg).status(Status.INTERNAL_SERVER_ERROR),
        answer => respond(answer.toString).status(Status.OK)
      )
    }
  }

  def start(): Unit = server.start()

  def stop(): Unit = server.stop()

}

package com.github.petekneller.playground.calc.http

import java.net.URLDecoder

import com.github.petekneller.playground.calc.{Result, Calculator}
import io.shaka.http.Request.GET
import io.shaka.http.RequestMatching._
import io.shaka.http.Response._
import io.shaka.http.{HttpServer, Status}

class OnlineCalculator(port: Int = 0, operators: List[(String, List[Double] => Result)] = Calculator.defaultOperations) {

  val calculator: Calculator = Calculator.run(_, operators)

  val server = HttpServer(port).handler{
    case GET(url"/calc/result/$expression") => {
      val result = calculator(URLDecoder.decode(expression, "utf-8"))
      result.fold(
        msg => respond(msg).status(Status.INTERNAL_SERVER_ERROR),
        answer => respond(answer.toString).status(Status.OK)
      )
    }
  }

  def start(): Int = server.start().port()

  def stop(): Unit = server.stop()

}

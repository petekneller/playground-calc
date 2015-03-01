package com.github.petekneller.playground.calc.http

import io.shaka.http.{Response, HttpServer}

object Calc {

  val server = HttpServer(8001).handler{ req => Response.ok.entity("20") }

  def start(): Unit = server.start()

  def stop(): Unit = server.stop()

}

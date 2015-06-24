package com.github.petekneller.playground.calc.http

import java.net.URLEncoder

import com.github.petekneller.playground.calc._
import io.shaka.http.Request.GET
import io.shaka.http.{Http, Status}

import scalaz.{-\/, \/}

trait HttpFixtures {

  val encoded: String => String = URLEncoder.encode(_, "utf-8")

  def withCalcHttp[A](server: OnlineCalculator)(block: Int => A): A = {
    try {
      val port = server.start()
      block(port)
    } finally {
      server.stop()
    }
  }

  def testClient(port: Int): Calculator = (input: String) => {
    val res = Http.http(GET(s"http://localhost:$port/calc/result/${encoded(input)}"))
    res.status match {
      case Status.OK => \/.fromTryCatchNonFatal(res.entityAsString.toDouble).leftMap(_.toString)
      case _ => -\/(res.entityAsString)
    }
  }

}

package com.github.petekneller.playground.calc.http

import java.net.URLEncoder

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

}

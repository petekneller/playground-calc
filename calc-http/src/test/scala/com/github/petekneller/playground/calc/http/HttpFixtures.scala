package com.github.petekneller.playground.calc.http

import java.net.URLEncoder

trait HttpFixtures {

  val encoded: String => String = URLEncoder.encode(_, "utf-8")

  def withCalcHttp[A](server: Calc)(block: => A): A = {
    try {
      server.start()
      block
    } finally {
      server.stop()
    }
  }

}

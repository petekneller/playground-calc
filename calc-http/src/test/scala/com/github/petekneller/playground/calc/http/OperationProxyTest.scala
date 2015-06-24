package com.github.petekneller.playground.calc.http

import java.net.URLEncoder

import com.github.petekneller.playground.calc.{Calculator, CalculatorMatchers}
import io.shaka.http.Http.HttpHandler
import io.shaka.http.HttpServer
import io.shaka.http.Request.GET
import io.shaka.http.Response.{ok, respond}
import io.shaka.http.Status._
import org.scalatest.{FlatSpec, Matchers}

class OperationProxyTest extends FlatSpec with Matchers with HttpFixtures with CalculatorMatchers {
  
  def proxyFor(port: Int) = new OperationProxy("+", "localhost", port)

  "an operation proxy" should "when returned anything other than a 200 OK, pass the upstream body back as part of the message" in {
    withRemoteStub { case _ => respond("oh no!").status(INTERNAL_SERVER_ERROR)} { port =>
      val proxy = proxyFor(port)

      val result = proxy(1d :: 2d :: Nil)
      result should failWith(include("oh no!"))
    }
  }

  it should "when returned anything other than a 200 OK, return in the error message a reference to the upstream host" in {
    withRemoteStub{ case _ => respond("wtf!").status(NOT_FOUND) }{ port =>
      val proxy = proxyFor(port)

      val result = proxy(1d :: 2d :: Nil)
      result should failWith(include(s"localhost:$port"))
    }
  }

  it should "pass its argument expression along unchanged and the result along unchanged" in {

    withRemoteStub{ case GET(path) if (path == s"/calc/result/${URLEncoder.encode("(+ 1.0 2.0 3.0)", "utf-8")}") => ok.entity("6")} { port =>
      val proxy = proxyFor(port)

      val result = proxy(1d :: 2d :: 3d :: Nil)
      result should succeedWith(equal(6))
    }
  }

  def withRemoteStub[A](handler: HttpHandler)(block: Int => A): A = {
    val stub = HttpServer().handler(handler)

    try {
      val port = stub.start().port()
      block(port)
    } finally {
      stub.stop()
    }
  }

}

package com.github.petekneller.playground.calc.smoketest

import org.scalatest.FunSuite
import com.googlecode.utterlyidle.handlers.ClientHttpHandler
import com.googlecode.utterlyidle.{Status, RequestBuilder}
import RequestBuilder.get
import scala.util.Properties
import scala.sys.SystemProperties

class SmokeTest extends FunSuite {
  val client = new ClientHttpHandler(5000)
  val host = new SystemProperties().get("test_host").getOrElse("")
  assert(!host.isEmpty, "Require the machine and port for the smoke test to be specified: -Dtest_host=xyz:80")

  test(s"landing page greets the caller (host=$host)") {

    val response = client.handle(get(s"http://$host/hello?name=world").build())
    assert(response.status() === Status.OK)
    assert(response.entity().toString === "hello world!")

  }

  test(s"status page returns 200 OK (host=$host)") {
    val response = client.handle(get(s"http://$host/status").build())
    assert(response.status() === Status.OK)
  }

}

package com.github.petekneller.playground.calc.http

import java.net.URLEncoder

import com.github.petekneller.playground.calc.{Result, Operator}
import io.shaka.http.Http.http
import io.shaka.http.Request.GET
import io.shaka.http.Status

import scalaz.{\/, -\/}

class OperationProxy(operation: String, remoteHost: String, remotePort: Int) extends Operator {

  def apply(arguments: List[Double]): Result  = {
    val response = http(GET(s"http://$remoteHost:$remotePort/calc/result/${URLEncoder.encode(s"($operation ${arguments.mkString(" ")})", "utf-8")}"))
    response.status match {
      case Status.OK => {
        val stringResult = response.entityAsString
        \/.fromTryCatchNonFatal(stringResult.toDouble).leftMap(e => s"$hostPartOfErrorMessage. Result was not a valid Double: $stringResult")
      }
      case _ => -\/(s"$hostPartOfErrorMessage: ${response.entityAsString}")
    }
  }

  val hostPartOfErrorMessage = s"Downstream host [$remoteHost:$remotePort] failed"
}

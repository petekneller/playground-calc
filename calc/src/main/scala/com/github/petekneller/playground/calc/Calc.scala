package com.github.petekneller.playground.calc

import com.googlecode.utterlyidle._
import ApplicationBuilder.application
import BasePath.basePath
import com.googlecode.utterlyidle.modules.ResourcesModule
import com.googlecode.utterlyidle.dsl.BindingBuilder._
import com.googlecode.totallylazy.proxy.Call._


object Calc extends App {

  lazy val utterlyIdleApp = new RestApplication(
    basePath("/"),
    new HelloModule,
    new StatusModule
  )

  application(utterlyIdleApp).start(9000)

}

class HelloModule extends ResourcesModule {
  def addResources(resources: Resources): Resources = {
    resources.add(
      get("/hello").resource(method(on(classOf[HelloModule]).hello(queryParam(classOf[String], "name")))).build()
    )
  }

  def hello(name: String): String = s"hello ${name}!"
}

class StatusModule extends ResourcesModule {
  def addResources(resources: Resources): Resources = {
    resources.add(
      get("/status").resource(method(on(classOf[StatusModule]).status())).build()
    )
  }

  def status(): String = "OK"
}

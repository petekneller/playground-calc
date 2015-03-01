package com.github.petekneller.playground.calc


// All of the below was in aid of getting this running in TC/AWS. I'll come back to it
// once/if I get back to those goals

//object Calc extends App {
//
//  lazy val utterlyIdleApp = new RestApplication(
//    basePath("/"),
//    new HelloModule,
//    new StatusModule
//  )
//
//  application(utterlyIdleApp).start(9000)
//
//}
//
//class HelloModule extends ResourcesModule {
//  def addResources(resources: Resources): Resources = {
//    resources.add(
//      get("/hello").resource(method(on(classOf[HelloModule]).hello(queryParam(classOf[String], "name")))).build()
//    )
//  }
//
//  def hello(name: String): String = s"hello ${name}!"
//}
//
//class StatusModule extends ResourcesModule {
//  def addResources(resources: Resources): Resources = {
//    resources.add(
//      get("/status").resource(method(on(classOf[StatusModule]).status())).build()
//    )
//  }
//
//  def status(): String = "OK"
//}

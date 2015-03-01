import sbt._
import sbt.Keys._

object CalcBuild extends Build {

  object Dependencies {
    private val scalaTest = "org.scalatest" %% "scalatest" % "1.9.2"
    private val utterlyIdle = "com.googlecode.utterlyidle" % "utterlyidle" % "741"

    object Compile  {
      val utterlyIdle = Dependencies.utterlyIdle
    }

    object Test {
      val scalaTest = Dependencies.scalaTest % "test"
    }
  }

  // common across all modules
  val buildSettings = Seq(
    // version is specified in /build-number.sbt
    organization in ThisBuild := "com.github.petekneller",
    scalaVersion in ThisBuild := "2.10.3",
    resolvers in ThisBuild += "Bodar repo" at "http://repo.bodar.com/"
  )

  lazy val buildRoot = Project(
    "build-root",
    file("."),
    settings =
      Project.defaultSettings ++
      buildSettings,
    aggregate = Seq(calcHttp)
  )

  import Dependencies._

  lazy val calc = Project(
    "playground-calc",
    file("calc"),
    settings =
      Project.defaultSettings ++
      buildSettings ++
      Seq(
        libraryDependencies ++= Seq(
          Test.scalaTest
        )
      )
  )

  lazy val calcHttp = Project(
    "playground-calc-http",
    file("calc-http"),
    settings =
      Project.defaultSettings ++
      buildSettings ++
      Seq(
        OneZip.task,
        libraryDependencies ++= Seq(
            Compile.utterlyIdle,
            Test.scalaTest
        )
      )
  )

  lazy val smoketest = Project(
    "smoketest",
    file("smoketest"),
    settings =
      Project.defaultSettings ++
      buildSettings ++
      Seq(
        libraryDependencies ++= Seq(
          Compile.utterlyIdle,
          Test.scalaTest
        )
      )
  )

  lazy val deploy = Project(
    "deploy",
    file("deploy"),
    settings = buildSettings
  )

}

import sbt._
import sbt.Keys._

object CalcBuild extends Build {

  object Dependencies {
    private val scalaTest = "org.scalatest" %% "scalatest" % "2.2.4"
    private val utterlyIdle = "com.googlecode.utterlyidle" % "utterlyidle" % "741"
    private val scalaZ = "org.scalaz" %% "scalaz-core" % "7.1.1"
    private val naiveHttp = "io.shaka" %% "naive-http-server" % "37"
    private val spire = "org.spire-math" %% "spire" % "0.10.1"

    object Compile  {
      val utterlyIdle = Dependencies.utterlyIdle
      val scalaZ = Dependencies.scalaZ
      val naiveHttp = Dependencies.naiveHttp
      val spire = Dependencies.spire
    }

    object Test {
      val scalaTest = Dependencies.scalaTest % "test"
    }
  }

  // common across all modules
  val buildSettings = Seq(
    // version is specified in /build-number.sbt
    organization in ThisBuild := "com.github.petekneller",
    scalaVersion in ThisBuild := "2.11.4",
    resolvers in ThisBuild += "Bodar repo" at "http://repo.bodar.com/",
    resolvers in ThisBuild += "Tim Tennant's repo" at "http://dl.bintray.com/timt/repo/"
  )

  lazy val buildRoot = Project(
    "build-root",
    file("."),
    settings =
      Project.defaultSettings ++
      buildSettings,
    aggregate = Seq(calc, calcHttp)
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
          Compile.scalaZ,
          Compile.spire,
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
            Compile.naiveHttp,
            Test.scalaTest
        )
      ),
    dependencies = Seq(calc % "compile;test->test")
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

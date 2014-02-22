import sbt._
import sbt.Keys._
import com.github.retronym.SbtOneJar._

object CalcBuild extends Build {

  object Dependencies {

    val scalaTest = "org.scalatest" %% "scalatest" % "1.9.2" % "test"
    val utterlyIdle = "com.googlecode.utterlyidle" % "utterlyidle" % "741"

  }

  // common across all modules
  override lazy val settings = super.settings ++ Seq(
    // version is specified in /build-number.sbt
    organization in ThisBuild := "com.github.petekneller",
    scalaVersion in ThisBuild := "2.10.3",
    resolvers in ThisBuild += "Bodar repo" at "http://repo.bodar.com/"
  )


  lazy val buildRoot = Project(
    "build-root",
    file("."),
    aggregate = Seq(calc))

  import Dependencies._

  lazy val calc = Project(
    "playground-calc",
    file("calc"),
    settings =
      Project.defaultSettings ++
      com.github.retronym.SbtOneJar.oneJarSettings ++
      Seq(
        // default classifier for one-jar is 'one-jar' - I don't like that
        artifact in oneJar <<= moduleName(Artifact(_, "complete")),
        libraryDependencies ++= Seq(
          scalaTest,
          utterlyIdle
        )
      )
  )

  lazy val smoketest = Project(
    "playground-calc-smoketest",
    file("smoketest"),
    settings =
      Project.defaultSettings ++ Seq(
        libraryDependencies ++= Seq(
          scalaTest,
          utterlyIdle
        )
      )
  )

}

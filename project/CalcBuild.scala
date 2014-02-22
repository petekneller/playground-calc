import sbt._
import sbt.Keys._

object CalcBuild extends Build {

  object Dependencies {

    val scalaTest = "org.scalatest" %% "scalatest" % "1.9.2" % "test"
    val utterlyIdle = "com.googlecode.utterlyidle" % "utterlyidle" % "741"

  }

  object OneZip {
    val key = TaskKey[File]("one-zip")
    lazy val task = key := zipper((dependencyClasspath in Compile).value.map(_.data), (Keys.`package` in Compile).value, streams.value)
    val zipper = (deps: Seq[File], jarFile: File, stream: TaskStreams) => {

      val sources = (jarFile -> jarFile.getName) :: deps.zip(deps.map("lib/" + _.getName)).toList
      stream.log.debug("Zipping these artifacts:")
      sources.foreach{ case (f1, f2) => stream.log.debug(s"$f1 => $f2")}

      val zipArchive = file(jarFile.getAbsolutePath().replaceAll("jar$", "zip"))
      stream.log.info(s"Creating artifacts archive: ${zipArchive.getAbsolutePath}")

      IO.zip(sources, zipArchive)
      zipArchive
    }
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
      Seq(
        OneZip.task,
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
      Project.defaultSettings ++
      Seq(
        libraryDependencies ++=
        Seq(
          scalaTest,
          utterlyIdle
        )
      )
  )

}

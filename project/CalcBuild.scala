import sbt._
import sbt.Keys._

object CalcBuild extends Build {

  object Dependencies {

    val scalaTest = "org.scalatest" %% "scalatest" % "1.9.2" % "test"
    val utterlyIdle = "com.googlecode.utterlyidle" % "utterlyidle" % "741"

  }

  object OneZip {
    val key = TaskKey[File]("one-zip")
    lazy val task = key := zipper((dependencyClasspath in Compile).value.map(_.data), (Keys.`package` in Compile).value, (baseDirectory in ThisBuild).value, streams.value)
    val zipper = (deps: Seq[File], jarFile: File, baseDir: File, stream: TaskStreams) => {

      val jars = (jarFile -> ("lib/" + jarFile.getName)) :: deps.zip(deps.map("lib/" + _.getName)).toList
      val otherDistributionStuff = IO.listFiles(baseDir / "package").map(f => f -> f.name)
      val inputs = jars ++ otherDistributionStuff

      stream.log.debug("Zipping these dependencies:")
      inputs.foreach{ case (from, to) => stream.log.debug(s"$from => $to")}

      val zipArchive = file(jarFile.getAbsolutePath().replaceAll("jar$", "zip"))
      stream.log.info(s"Creating artifacts archive: ${zipArchive.getAbsolutePath}")

      IO.zip(inputs, zipArchive)
      zipArchive
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
    aggregate = Seq(calc)
  )

  import Dependencies._

  lazy val calc = Project(
    "playground-calc",
    file("calc"),
    settings =
      Project.defaultSettings ++
      buildSettings ++
      Seq(
        OneZip.task,
        libraryDependencies ++=
        Seq(
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
      buildSettings ++
      Seq(
        libraryDependencies ++=
        Seq(
          scalaTest,
          utterlyIdle
        )
      )
  )

  lazy val deploy = Project(
    "deploy",
    file("deploy"),
    settings = buildSettings
  )

}

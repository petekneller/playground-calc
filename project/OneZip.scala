import sbt.Keys._
import sbt._

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
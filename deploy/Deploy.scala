object Deploy extends App {

  val envs = Seq("rit")

  if (args.length != 1 || !envs.exists(envs(0).equalsIgnoreCase(_))) {
    usage()
    System.exit(1)
  }

  val deployDir = "deploy/playground-calc-rit"
  val ritServer = "ec2-54-72-168-224.eu-west-1.compute.amazonaws.com"
  val userName: String = "ubuntu"
  val artifact = "playground-calc_2.10-dev"
  val artifactFile = s"$artifact.zip"
  val ssh = (cmd: String) => SSH(ritServer, userName, cmd)
  val scp = (localFile: String, remoteFile: String) => SCP(ritServer, userName, localFile, remoteFile)

  ssh(s"if [ ! -d $deployDir ]; then mkdir $deployDir; fi")
  scp(s"target/$artifactFile", s"$deployDir/$artifactFile")
  ssh(s"if [ -d $deployDir/current ]; then cd $deployDir/current; ./playground-calc.sh stop; fi")
  ssh(s"cd $deployDir; unzip -o $artifactFile -d $artifact")
  ssh(s"rm $deployDir/$artifactFile")
  ssh(s"cd $deployDir; rm current; ln -s $artifact current")
  ssh(s"cd $deployDir/current; chmod u+x playground-calc.sh; ./playground-calc.sh start")


  def usage() {
    println("usage: deploy <environment>")
    println(s"where <environment> is currently recognised (case insensitive) as one of: ${envs.mkString(",")}")
  }

}


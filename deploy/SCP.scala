import java.io.File
import org.apache.tools.ant.Project
import org.apache.tools.ant.taskdefs.optional.ssh.Scp

object SCP {

  def apply(remoteMachine: String, userName: String, localFile: String, remoteFile: String): Int = {

    val scp = new Scp
    scp.init()
    val p = new Project
    val localDir = new File(localFile).getParent
    p.setBasedir(localDir)
    scp.setProject(p)
    scp.setHost(remoteMachine)
    scp.setUsername(userName)
    scp.setKeyfile("~/.ssh/playground-admin_key-pair_eu-west-1.pem")
    scp.setTrust(true)
    scp.setSftp(true)
    scp.setLocalFile(new File(localFile).getName)
    scp.setRemoteTodir(s"$userName@$remoteMachine:$remoteFile")
    scp.execute()

    0
  }

}

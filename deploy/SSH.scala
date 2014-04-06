import org.apache.tools.ant.taskdefs.optional.ssh.{SSHExec, Scp, ScpToMessageBySftp, ScpToMessage}
import scala.Predef.String

object SSH {

  def apply(remoteMachine: String, userName: String, command: String): Int = {

    val ssh = new SSHExec
    ssh.setHost(remoteMachine)
    ssh.setUsername(userName)
    ssh.setKeyfile("~/.ssh/playground-admin_key-pair_eu-west-1.pem")
    ssh.setTrust(true)
    ssh.setCommand(command)
    ssh.execute()

    0
  }
}

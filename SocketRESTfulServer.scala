import java.net.{ServerSocket, Socket}

object SocketRESTfulServer {
    val s2b: String => Array[Byte] = _.toArray.map(_.toByte)
    def start(port: Int) {
        val server: ServerSocket = new ServerSocket(port)

        while(true) {
            val socket = server.accept()
            val input = socket.getInputStream
            val instring = (for (i <- 1 until input.available) yield input.read.toChar).mkString
            val output = socket.getOutputStream

            val line = "Connection from IP-" + socket.getRemoteSocketAddress.toString

            val str = s"""HTTP/1.1 200 OK
Content-Type: text/html; charset=utf-8
Content-Length: ${line.size}

${line}"""
            output.write(s2b(str))
            input.close
            output.close
            socket.close
        }

        server.close
    }
}

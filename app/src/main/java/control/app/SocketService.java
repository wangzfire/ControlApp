package control.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketService extends Socket {
    private static InetAddress ipAddress ;
    private static final int port = 12345;
    private static BufferedReader br ;
    private static PrintWriter pw ;
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static SocketService socket ;

    public SocketService(InetAddress ipAddress, int port) throws UnknownHostException, IOException {
        super(ipAddress, port);
    }

    public static SocketService getsocket() throws IOException {
        socket= new SocketService(ipAddress,port);
        return  socket;
    }

    public static BufferedReader getInput() throws IOException {
        br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
        return br;
    }

    public static PrintWriter Output() throws IOException {
        pw=new PrintWriter(
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true
        );
        return pw;
    }
}

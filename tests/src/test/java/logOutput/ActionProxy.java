package logOutput;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mock Proxy
 *
 * Created by ji.zhang on 8/23/19.
 */
public class ActionProxy {

//    private static final Logger logger = LoggerFactory.getLogger(ActionProxy.class);

    @Test
    public void start() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.setProperty("socksProxyHost", "127.0.0.1");
        System.setProperty("socksProxyPort", "1080");
        System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");

        try (Socket socket = new Socket("13.209.72.232", 20001)) {
            socket.setSoTimeout(10000);
            socket.setSendBufferSize(1024);
            socket.setTcpNoDelay(true);
            DataOutputStream os = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            System.setOut(new SocketPrintStream(os, System.out));
            System.setErr(new SocketPrintStream(os, System.err));

            Method m = ActionCode.class.getMethod("main", String[].class);
            m.invoke(null, (Object) null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

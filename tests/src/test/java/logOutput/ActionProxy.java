package logOutput;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;

import org.junit.Test;

/**
 * Mock Proxy
 *
 * Created by ji.zhang on 8/23/19.
 */
public class ActionProxy {

    private static final String activationId = "activation-id-001";

    @Test
    public void start() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.setProperty("socksProxyHost", "127.0.0.1");
        System.setProperty("socksProxyPort", "1080");
        System.setProperty("http.nonProxyHosts", "localhost|127.0.0.1");

        Map<String, Object> logMap = Maps.newHashMap();
        List<String> logsList = Lists.newArrayList();
        try (Socket socket = new Socket("localhost", 2222);
             DataOutputStream os = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            System.setOut(new SocketPrintStream(logsList, System.out));
            System.setErr(new SocketPrintStream(logsList, System.err));
            try {
                Method m = ActionCode.class.getMethod("main", String[].class);
                m.invoke(null, (Object) null);
            } catch (Throwable t) {
                t.printStackTrace();
            }

            logMap.put("activationId", activationId);
            logMap.put("logs", logsList);
            String logs = new Gson().toJson(logMap);
            os.writeBytes(logs);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            logsList.clear();
            logMap.clear();
        }
    }
}

package logOutput;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mock Proxy
 *
 * Created by ji.zhang on 8/23/19.
 */
public class ActionProxy {

    private static final Logger logger = LoggerFactory.getLogger(ActionProxy.class);

    @Test
    public void start() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        System.setOut(new LogbackPrintStream(logger, System.out));
        System.setErr(new LogbackPrintStream(logger, System.err));

        Method m = ActionCode.class.getMethod("main", String[].class);
        m.invoke(null, (Object) null);
    }
}

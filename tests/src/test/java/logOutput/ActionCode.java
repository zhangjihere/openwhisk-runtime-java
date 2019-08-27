package logOutput;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mock Action Code for running
 *
 * Created by ji.zhang on 8/23/19.
 */
public class ActionCode {

    public static final int num = 1;

    public static void main(String[] args) {
        for (int i = 0; i < num; i++) {
            System.out.println("Hello World! I am an action!" + i);
            System.err.println("Hello Hell! I amd an action!" + i);
        }
    }
}

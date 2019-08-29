package logOutput;

/**
 * Mock Action Code for running
 *
 * Created by ji.zhang on 8/23/19.
 */
public class ActionCode {

    public static final int num = 1;

    public static void main(String[] args) {
        for (int i = 0; i < num; i++) {
            System.out.println("\n");
            System.out.println("\b");
            System.out.println("\r");
            System.out.println(" \n Mutiline \b \n \r Mutiline001");
            System.out.println("Hello World! I am an action!" + i);
            try {
                throw new RuntimeException("Action Throwable");
            } catch (Throwable t) {
                t.printStackTrace();
            }
            System.err.println("Hello Hell! I amd an action!" + i);
            throw new RuntimeException("Container throwable");
        }
    }
}

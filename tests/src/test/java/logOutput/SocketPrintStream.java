package logOutput;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.slf4j.Logger;

/**
 * Redirect System.out/err to Socket
 *
 * Created by ji.zhang on 8/23/19.
 */
public class SocketPrintStream extends PrintStream {

    private int last;
    private final ByteArrayOutputStream bufOut;
    private final PrintStream stdPrintStream;
    private final DataOutputStream dos;
    private final String activationId;

    private static final String LOG_FORMAT = "{\"activationId\":\"%s\",\"log\":\"%s\"}\n";

    /**
     * @param dos            Socket's DataOutputStream
     * @param stdPrintStream origin standard out/err
     * @param activationId   the action's activationId
     */
    public SocketPrintStream(DataOutputStream dos, PrintStream stdPrintStream, String activationId) {
        super(new ByteArrayOutputStream());
        this.last = -1;
        this.bufOut = (ByteArrayOutputStream) super.out;
        this.dos = dos;
        this.stdPrintStream = stdPrintStream;
        this.activationId = activationId;
    }

    @Override
    public void write(int c) {
        if (this.last == 13 && c == 10) {
            this.last = -1;
        } else {
            if (c != 10 && c != 13) { // 'LF' and 'CR'
                super.write(c);
            } else {
                try {
                    String logContent = this.bufOut.toString();
                    String msg = String.format(LOG_FORMAT, activationId, logContent);
                    stdPrintStream.print(msg);
                    dos.writeBytes(msg);
                    dos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    this.bufOut.reset();
                }
            }
            this.last = c;
        }
    }

    @Override
    public void write(byte[] line, int start, int end) {
        if (end < 0) {
            throw new ArrayIndexOutOfBoundsException(end);
        } else {
            for (int pos = 0; pos < end; ++pos) {
                this.write(line[start + pos]);
            }

        }
    }

}

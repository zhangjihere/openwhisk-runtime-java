package logOutput;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Collect System.out/err log and in place send log to logstach based on socket plugin.
 *
 * Created by ji.zhang on 8/23/19.
 */
public class SocketPrintStream extends PrintStream {

    private int last;
    private final ByteArrayOutputStream bufOut;
    private final PrintStream stdPrintStream;
    private final List<String> logList;

    /**
     * @param logList        the action's log list
     * @param stdPrintStream origin standard out/err
     */
    public SocketPrintStream(List<String> logList, PrintStream stdPrintStream) {
        super(new ByteArrayOutputStream());
        this.last = -1;
        this.bufOut = (ByteArrayOutputStream) super.out;
        this.logList = logList;
        this.stdPrintStream = stdPrintStream;
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
                    String logMsg = this.bufOut.toString();
                    stdPrintStream.println(logMsg);
                    logList.add(logMsg);
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

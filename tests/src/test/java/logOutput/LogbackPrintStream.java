package logOutput;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;

/**
 * Redirect System.out/err to logger
 *
 * Created by ji.zhang on 8/23/19.
 */
public class LogbackPrintStream extends PrintStream {

    private int last;
    private final ByteArrayOutputStream bufOut;
    private final PrintStream stdPrintStream;
    private final Logger logger;

    /**
     * @param logger         Logback logger tool
     * @param stdPrintStream origin standard out/err
     */
    public LogbackPrintStream(Logger logger, PrintStream stdPrintStream) {
        super(new ByteArrayOutputStream());
        this.last = -1;
        this.bufOut = (ByteArrayOutputStream) super.out;
        this.stdPrintStream = stdPrintStream;
        this.logger = logger;
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
                    String str = this.bufOut.toString();
                    stdPrintStream.println(str);
                    this.logger.debug(str);
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

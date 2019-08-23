package logOutput;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.slf4j.Logger;

/**
 * My System.out/err to logger
 *
 * Created by ji.zhang on 8/23/19.
 */
public class LogbackPrintStream extends PrintStream {

    private final PrintStream stdPrintStream;
    private final Logger logger;
    private int last;
    private final ByteArrayOutputStream bufOut;

    public LogbackPrintStream(Logger logger, PrintStream stdPrintStream) {
        super(new ByteArrayOutputStream());
        this.last = -1;
        this.bufOut = (ByteArrayOutputStream) super.out;
        this.stdPrintStream = stdPrintStream;
        this.logger = logger;
    }

    public void write(int var1) {
        if (this.last == 13 && var1 == 10) {
            this.last = -1;
        } else {
            if (var1 != 10 && var1 != 13) {
                super.write(var1);
            } else {
                try {
                    String var2 = this.bufOut.toString();
                    stdPrintStream.println(var2);
                    this.logger.debug(var2);
                } finally {
                    this.bufOut.reset();
                }
            }

            this.last = var1;
        }
    }

    public void write(byte[] var1, int var2, int var3) {
        if (var3 < 0) {
            throw new ArrayIndexOutOfBoundsException(var3);
        } else {
            for (int var4 = 0; var4 < var3; ++var4) {
                this.write(var1[var2 + var4]);
            }

        }
    }

    public String toString() {
        return "RMI";
    }

}

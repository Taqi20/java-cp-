import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Template {
    static FastInput in = new FastInput();
    static StringBuilder out = new StringBuilder();

    public static void main(String[] args) throws Exception {
        int t = in.nextInt();
        while (t-- > 0) {
            solve();
        }
        // flush all at once
        System.out.print(out);
    }

    static void solve() throws IOException {
        // ==== your code here ====
        long n = in.nextLong();
        for (int i = 0; i < n; i++) {
            int x = in.nextInt();
            // process x…
            out.append(x).append(' ');
        }
        out.append('\n');
        // =======================
    }

    /*
     * Fast input reader.
     * Reads up to >10^10 tokens in milliseconds.
     */
    static class FastInput {
        private static final int BUFFER_SIZE = 1 << 20; // 1 MB
        private final DataInputStream din;
        private final byte[] buffer;
        private int bufferPointer, bytesRead;

        public FastInput() {
            this.din = new DataInputStream(System.in);
            this.buffer = new byte[BUFFER_SIZE];
            this.bufferPointer = this.bytesRead = 0;
        }

        public FastInput(String fileName) throws IOException {
            this.din = new DataInputStream(new FileInputStream(fileName));
            this.buffer = new byte[BUFFER_SIZE];
            this.bufferPointer = this.bytesRead = 0;
        }

        public String next() throws IOException {
            byte c;
            // skip whitespace
            do {
                c = read();
            } while (c <= ' ');
            // read token
            StringBuilder sb = new StringBuilder();
            do {
                sb.append((char) c);
                c = read();
            } while (c > ' ');
            return sb.toString();
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + (c - '0');
                c = read();
            } while (c >= '0' && c <= '9');
            return neg ? -ret : ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + (c - '0');
                c = read();
            } while (c >= '0' && c <= '9');
            return neg ? -ret : ret;
        }

        public double nextDouble() throws IOException {
            double sign = 1, val = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            if (c == '-') {
                sign = -1;
                c = read();
            }
            for (; c >= '0' && c <= '9'; c = read())
                val = val * 10 + (c - '0');
            if (c == '.') {
                double frac = 1;
                c = read();
                for (; c >= '0' && c <= '9'; c = read()) {
                    val += (c - '0') / (frac *= 10);
                }
            }
            return val * sign;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead) {
                bytesRead = din.read(buffer, 0, BUFFER_SIZE);
                if (bytesRead == -1)
                    return -1;
                bufferPointer = 0;
            }
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            din.close();
        }
    }
}

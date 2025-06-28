# Java FastInput Template (Competitive Programming)

In contest settings, **speedy I/O** is crucial. A FastInput (or FastReader) template wraps low-level Java I/O classes to read raw bytes in large blocks and parse data manually. This avoids the overhead of `Scanner` (which uses regex and token parsing) and even `BufferedReader` (which still builds strings). In practice, FastInput uses a `DataInputStream` on `System.in` with a large byte buffer (often 64KB) and pointer indices. Reading methods (like `nextInt()`) fetch bytes one by one from the buffer, skipping whitespace and assembling numbers or tokens directly. Because it bypasses much of Java’s built-in parsing logic, it is **much faster** – GeeksforGeeks notes this approach “uses `InputStream` and `read()` … \[and] is the fastest” among common methods. In summary:

* **Data stream with buffer**: Uses `DataInputStream` plus a byte array (e.g. 1<<16) to batch-read input.
* **Manual parsing**: Methods skip spaces and convert ASCII digits to numbers without creating intermediate strings.
* **Less overhead**: No regex or heavy tokenization; minimal object creation yields lower latency than `Scanner`.

## Input Parsing and Output Buffering

The template defines methods for each type (int, long, double, etc.). Internally, a helper method (often called `read()`) pulls the next byte from the buffer, refilling it from the stream when empty. For example, reading an integer typically looks like: skip any byte ≤ `' '` (whitespace), check for a minus sign, then loop multiplying the current value by 10 and adding `(c - '0')` for each digit byte. Once a non-digit is hit, the integer is complete. This logic is repeated with variations for `nextLong()`, `nextDouble()`, and so on. Since bytes are processed directly, parsing is very fast when inputs are numeric. For output, the template usually buffers results too. Instead of calling `System.out.println()` many times, one often uses a single `PrintWriter` or `BufferedWriter` on `System.out`. These classes accumulate output in an internal buffer and flush once (or at the end), minimizing overhead. In fact, using `PrintWriter` (with `flush()`) can be roughly **twice as fast** as repeated println calls. It’s also common to build output strings with a `StringBuilder` and then print once: grouping text and then flushing is much faster than many small writes. For example, GeeksforGeeks recommends `BufferedWriter` because it “buffers characters to provide efficient writing” of arrays and strings. In practice:

* **Reading**: Large fixed-size byte buffer, pointer indices, and custom `read()` + parse routines convert bytes to primitives with minimal overhead.
* **Skipping whitespace**: Each `nextXxx()` method loops until it finds a non-space byte, then reads digits or characters.
* **Output**: Use one writer (e.g. `PrintWriter` or `BufferedWriter`) with an internal buffer. Accumulate output (often via `StringBuilder`) and call `flush()` once. This batched approach reduces costly I/O calls.

## Cheat Sheet: Reading Data Types

The FastInput template provides type-specific methods. Here’s how to use them for common types:

| Data Type      | Template Usage                                                                                       |
| -------------- | ---------------------------------------------------------------------------------------------------- |
| **int**        | Call `nextInt()` to parse the next integer.                                                          |
| **long**       | Call `nextLong()` for a long integer.                                                                |
| **double**     | Call `nextDouble()` for a double/float value.                                                        |
| **char**       | Read a token (via `next()`), then use `.charAt(0)` for its first character.                          |
| **String**     | Use `next()` for the next whitespace-delimited word, or `nextLine()` for a whole line.               |
| **int\[]**     | Use `nextInt()` repeatedly in a loop (or an array-reading helper) to fill the array.                 |
| **long\[]**    | Use `nextLong()` in a loop for each element.                                                         |
| **char\[]**    | Read a `String` (e.g. with `next()` or `nextLine()`) then call `.toCharArray()`.                     |
| **boolean**    | Read a token and parse it (e.g. use `Boolean.parseBoolean(next())`, or read an int 0/1 and compare). |
| **boolean\[]** | Loop reading booleans: parse each token similarly (e.g. using `parseBoolean` or `nextInt()` checks). |

Each array type (`int[]`, `long[]`, etc.) is typically read by looping and calling the scalar method for each element. For example, to read an `int[]` of length `n`, you would do something like: `for(int i=0; i<n; i++) arr[i] = reader.nextInt();`. (This uses no extra snippet; it’s just repeatedly invoking the built-in methods.)


Here are concrete snippet examples for each entry in cheat‑sheet. You can drop these into your `solve()` method (or wherever you need them) to see exactly how to call each FastInput helper:

```java
// 1. int
int a = in.nextInt();

// 2. long
long b = in.nextLong();

// 3. double
double d = in.nextDouble();

// 4. char (first character of next token)
char c = in.next().charAt(0);

// 5. String (next word)
String s = in.next();

// 6. int[] array
int n = in.nextInt();            // size
int[] arrInt = new int[n];
for (int i = 0; i < n; i++) {
    arrInt[i] = in.nextInt();
}

// 7. long[] array
int m = in.nextInt();            // size
long[] arrLong = new long[m];
for (int i = 0; i < m; i++) {
    arrLong[i] = in.nextLong();
}

// 8. char[] array (from a token or line)
String token = in.next();        // e.g. "abcde"
char[] arrChar = token.toCharArray();

// 9. boolean (from "true"/"false" or 0/1)
boolean flag1 = Boolean.parseBoolean(in.next());
// — or if input is 0/1:
boolean flag2 = (in.nextInt() == 1);

// 10. boolean[] array (0/1 format)
int k = in.nextInt();            // size
boolean[] arrBool = new boolean[k];
for (int i = 0; i < k; i++) {
    arrBool[i] = (in.nextInt() == 1);
}
```


## Key Classes and Terms

* **DataInputStream (`java.io.DataInputStream`)**: This low-level stream reads raw bytes or primitive data types from an input source. The FastInput wrapper uses `new DataInputStream(System.in)` to tap into standard input. According to the Java docs, a `DataInputStream` “lets an application read primitive Java data types from an underlying input stream”. In our template, we use it in conjunction with a large byte buffer for speed.
* **Buffer**: An internal byte array (e.g. size 1<<16) holds chunks of input. Buffered I/O is key: Java buffers avoid frequent costly OS calls by reading large blocks at once. The template’s `fillBuffer()` method refills this array in one read, and a pointer walks through it with each `read()` call. Without buffering, each byte or line would cause a native I/O call, which is much slower.
* **StringBuilder**: A mutable sequence of characters. In output handling, it’s common to append parts of the answer to a `StringBuilder` and then print the final string. This is far more efficient than concatenating Strings or printing many times. (As noted, grouping output via a single `StringBuilder` and printing once is a recommended performance tip.)
* **PrintWriter / BufferedWriter**: These classes wrap `System.out` to buffer output. A `PrintWriter` (often on a `BufferedOutputStream`) or `BufferedWriter` accumulates text internally and only writes to the console or file when flushed or full. This drastically reduces I/O overhead. For example, GeeksforGeeks shows using `BufferedWriter` to write lines and then calling `flush()` to push the buffer out. Similarly, using `PrintWriter` (with auto-flush off) is about twice as fast as direct `println()` calls.
* **IOException**: Reading and writing streams can throw I/O errors. The template’s methods typically declare `throws IOException`. In contest code, we often add `throws IOException` to `main` for simplicity.
* **Whitespace Skipping (`<= ' '`)**: In the code, comparing bytes to `' '` (space, ASCII 32) lets the reader skip all control or whitespace characters (space, newline, tab) before parsing the next token. This ensures `nextInt()` doesn’t stop on blank characters.

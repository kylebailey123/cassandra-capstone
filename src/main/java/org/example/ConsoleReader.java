package org.example;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.UUID;

@Slf4j
public class ConsoleReader implements Closeable {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ConsoleReader CONSOLE_READER_INSTANCE = new ConsoleReader();

    private ConsoleReader() {
    }

    public static synchronized ConsoleReader getConsoleReaderInstance() {
        if (CONSOLE_READER_INSTANCE == null) {
            CONSOLE_READER_INSTANCE = new ConsoleReader();
        }
        return CONSOLE_READER_INSTANCE;
    }

    public String readLine(){
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UUID readUUID() {
        try {
            return UUID.fromString(readLine());
        } catch (Exception e) {
            log.error("Invalid UUID. Please enter a valid UUID.");
            return null;
        }
    }

    public int readInt() {
        return Integer.parseInt(readLine());
    }

    @Override
    public void close()  {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

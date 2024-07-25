package com.craftinginterpreters.lox;

import java.io.BufferedReader;
import java.io.IOException;
import java .io.InputStreamReader;
import nio.charset.charset;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Lists;
import java.util.Scanner;

public class Lox {
    // Main
    static boolean hadError = false;
    public static void main (String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: jlox [script]");
            System.exit(64); // UNIX sysexits.h header (pseudo standard)
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        // Given a path, it searches for the file and then returns an executable command
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        if (hadError) System.exit(65);
    }

    private  static void runPrompt() throws IOException {
        // Essentially implementing a JLox console
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break; // If the user hits Ctrl + D, then it will return null, killing the processes
            run(line);
            hadError = false;
        }
    }

    public static void run(String source) {
        // Is a text scanner, turning the source into tokens based on a whitespace delimiter
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens)
            System.out.println(token);
    }

    // "It is good practice to separate code that generates errors from those that report them." ~ crafting interpreters
    static void error(int line, String message) {
        // Raises an error message using the report helper function
        report(line, "", message);
    }

    private static void report(int line, String where, String message) {
        // Comprises an error message telling the user where, what line, and the error type
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }


}
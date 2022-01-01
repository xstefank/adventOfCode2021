package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

@Path("/10")
public class AdventDay10 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("10.txt"));

        long result = 0;

        String line;
        Stack<Character> brackets = new Stack<>();

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            brackets.clear();

            for (char c : line.toCharArray()) {
                if (c == '(' || c == '[' || c == '{' || c == '<') {
                    brackets.push(c);
                } else if (c == ')') {
                    if (brackets.pop() != '(') {
                        result += 3;
                        break;
                    }
                } else if (c == ']') {
                    if (brackets.pop() != '[') {
                        result += 57;
                        break;
                    }
                } else if (c == '}') {
                    if (brackets.pop() != '{') {
                        result += 1197;
                        break;
                    }
                } else if (c == '>') {
                    if (brackets.pop() != '<') {
                        result += 25137;
                        break;
                    }
                }
            }

        }

        System.out.println(result);
    }

    @GET
    @Path("/2")
    public void part2() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("10.txt"));

        String line;
        Stack<Character> brackets = new Stack<>();
        List<Long> results = new ArrayList<>();

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            brackets.clear();

            try {
                for (char c : line.toCharArray()) {
                    if (c == '(' || c == '[' || c == '{' || c == '<') {
                        brackets.push(c);
                    } else if (c == ')') {
                        if (brackets.pop() != '(') {
                            throw new IllegalStateException();
                        }
                    } else if (c == ']') {
                        if (brackets.pop() != '[') {
                            throw new IllegalStateException();
                        }
                    } else if (c == '}') {
                        if (brackets.pop() != '{') {
                            throw new IllegalStateException();
                        }
                    } else if (c == '>') {
                        if (brackets.pop() != '<') {
                            throw new IllegalStateException();
                        }
                    }
                }

                // we have an incomplete line and the stack contains the remaining needed bracket to complete
                long result = 0;
                while (!brackets.empty()) {
                    switch (brackets.pop()) {
                        case '(' -> result = result * 5 + 1;
                        case '[' -> result = result * 5 + 2;
                        case '{' -> result = result * 5 + 3;
                        case '<' -> result = result * 5 + 4;
                    }
                }

                results.add(result);

            } catch (IllegalStateException e) {
                // ok, do nothing for corrupted lines
            }
        }

        results.sort(Comparator.naturalOrder());
        System.out.println(results.get(results.size() / 2));
    }
}

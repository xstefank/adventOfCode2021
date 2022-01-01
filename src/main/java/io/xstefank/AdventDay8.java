package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Path("/8")
public class AdventDay8 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("8.txt"));

        List<Integer> digitsWithUniqueSegmentsLenghts = List.of(2, 4, 3, 7);

        int signalCount = 0;

        while (scanner.hasNext()) {
            for (int i = 0; i < 11; i++) {
                scanner.next();
            }

            for (int i = 0; i < 4; i++) {
                int signalLength = scanner.next().length();
                for (Integer uniqueLength : digitsWithUniqueSegmentsLenghts) {
                    if (uniqueLength == signalLength) {
                        signalCount++;
                        break;
                    }
                }
            }
        }

        System.out.println(signalCount);
    }

    @GET
    @Path("/2")
    public void part2() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("8.txt"));

        long result = 0;
        while (scanner.hasNext()) {
            result += processSignal(scanner);
        }

        System.out.println(result);
    }

    private int processSignal(Scanner scanner) {
        Map<Integer, List<String>> uniqueSignalPatterns = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            String s = scanner.next();
            uniqueSignalPatterns.computeIfAbsent(s.length(), k -> new ArrayList<>());
            uniqueSignalPatterns.get(s.length()).add(s);
        }

        Map<String, String> matchingSignals = new HashMap<>(9);
        matchingSignals.put("1", uniqueSignalPatterns.get(2).get(0));
        matchingSignals.put("4", uniqueSignalPatterns.get(4).get(0));
        matchingSignals.put("7", uniqueSignalPatterns.get(3).get(0));
        matchingSignals.put("8", uniqueSignalPatterns.get(7).get(0));

        for (String s : uniqueSignalPatterns.get(5)) {
            if (isSubSequence(matchingSignals.get("1"), s)) {
                matchingSignals.put("3", s);
            } else if (isSubSequence(subtractString(matchingSignals.get("4"), matchingSignals.get("1")), s)) {
                matchingSignals.put("5", s);
            } else {
                matchingSignals.put("2", s);
            }
        }

        for (String s : uniqueSignalPatterns.get(6)) {
            if (isSubSequence(matchingSignals.get("5"), s)) {
                if (isSubSequence(matchingSignals.get("7"), s)) {
                    matchingSignals.put("9", s);
                } else {
                    matchingSignals.put("6", s);
                }
            } else {
                matchingSignals.put("0", s);
            }
        }

        scanner.next(); // | delimeter

        StringBuilder valueBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            valueBuilder.append(getKeyForValue(matchingSignals, scanner.next()));
        }

        return Integer.parseInt(valueBuilder.toString());
    }

    private String getKeyForValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (isAnagram(entry.getValue(), value)) {
                return entry.getKey();
            }
        }

        return null;
    }

    private boolean isAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) {
            return false;
        }

        return isSubSequence(s1, s2);
    }

    private boolean isSubSequence(String subsequence, String signal) {
        for (char c : subsequence.toCharArray()) {
            if (signal.indexOf(c) < 0) {
                return false;
            }
        }

        return true;
    }

    private String subtractString(String s1, String s2) {
        StringBuilder result = new StringBuilder();
        for (char c : s1.toCharArray()) {
            if (s2.indexOf(c) < 0) {
                result.append(c);
            }
        }

        return result.toString();
    }
}

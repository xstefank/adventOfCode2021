package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Path("/3")
public class AdventDay3 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("3.txt"));

        int size = 12;

        int[] onesCount = new int[size];
        int allCount = 0;
        String current;
        char c;

        while (scanner.hasNext()) {
            allCount++;
            current = scanner.next();
            for (int i = 0; i < current.length(); i++) {
                c = current.charAt(i);
                if (c == '1') {
                    onesCount[i]++;
                }
            }
        }

        StringBuilder gammaBinaryStringBuilder = new StringBuilder("");
        StringBuilder epsilonBinaryStringBuilder = new StringBuilder("");
        int halfCount = allCount / 2;

        for (int i = 0; i < size; i++) {
            gammaBinaryStringBuilder.append(onesCount[i] > halfCount ? "1" : "0");
            epsilonBinaryStringBuilder.append(onesCount[i] > halfCount ? "0" : "1");
        }

        System.out.println(Long.parseLong(gammaBinaryStringBuilder.toString(), 2) * Long.parseLong(epsilonBinaryStringBuilder.toString(), 2));
    }

    @GET
    @Path("/2")
    public void part2() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("3.txt"));

        List<String> numbers = new ArrayList<>();

        while (scanner.hasNext()) {
            numbers.add(scanner.next());
        }

        List<String> oxygenNumbers = new ArrayList<>(numbers);
        List<String> onesNumbers;
        List<String> zerosNumbers;
        int currentPosition = 0;
        boolean keepOnes;

        while (oxygenNumbers.size() > 1) {
            onesNumbers = new ArrayList<>();
            zerosNumbers = new ArrayList<>();

            for (String s : oxygenNumbers) {
                if (s.charAt(currentPosition) == '1') {
                    onesNumbers.add(s);
                } else {
                    zerosNumbers.add(s);
                }
            }

            // default to keep 1s
            keepOnes = true;
            if (zerosNumbers.size() > onesNumbers.size()) {
                keepOnes = false;
            }

            oxygenNumbers = keepOnes ? onesNumbers : zerosNumbers;
            currentPosition++;
        }

        List<String> co2Numbers = new ArrayList<>(numbers);
        currentPosition = 0;

        while (co2Numbers.size() > 1) {
            onesNumbers = new ArrayList<>();
            zerosNumbers = new ArrayList<>();

            for (String s : co2Numbers) {
                if (s.charAt(currentPosition) == '1') {
                    onesNumbers.add(s);
                } else {
                    zerosNumbers.add(s);
                }
            }

            // default to keeping zeros
            keepOnes = false;
            if (onesNumbers.size() < zerosNumbers.size()) {
                keepOnes = true;
            }

            co2Numbers = keepOnes ? onesNumbers : zerosNumbers;
            currentPosition++;
        }

        System.out.println(oxygenNumbers.get(0));
        System.out.println(co2Numbers.get(0));
        System.out.println(Long.parseLong(oxygenNumbers.get(0), 2) * Long.parseLong(co2Numbers.get(0), 2));
    }


}

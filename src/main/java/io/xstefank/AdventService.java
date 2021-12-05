package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

@Path("/")
public class AdventService {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1/1")
    public void advent11() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("1.txt"));

        long increaseSum = 0;

        int previous = scanner.nextInt();
        int current;

        while (scanner.hasNext()) {
            current = scanner.nextInt();
            if (current > previous) {
                increaseSum++;
            }

            previous = current;
        }

        System.out.println("Increase sum = " + increaseSum);
    }

    @GET
    @Path("/1/2")
    public void advent12() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("1.txt"));

        int windowSize = 3;

        long increaseWindowSum = 0;

        Queue<Integer> queue = new ArrayBlockingQueue<>(windowSize);

        int toAdd;
        int currentSum = scanner.nextInt();
        queue.add(currentSum);

        for (int i = 1; i < windowSize; i++) {
            toAdd = scanner.nextInt();
            queue.add(toAdd);
            currentSum += toAdd;
        }

        int previousSum = currentSum;

        while (scanner.hasNext()) {
            toAdd = scanner.nextInt();

            currentSum -= queue.poll();
            currentSum += toAdd;

            if (currentSum > previousSum) {
                increaseWindowSum++;
            }


            queue.add(toAdd);
            previousSum = currentSum;

        }

        System.out.println("Increase sum = " + increaseWindowSum);
    }

    @GET
    @Path("/2/1")
    public void advent21() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("2.txt"));

        int x = 0;
        int y = 0;
        String command;

        while (scanner.hasNext()) {
            command = scanner.next();
            switch (command) {
                case "forward" -> x += scanner.nextInt();
                case "down" -> y += scanner.nextInt();
                case "up" -> y -= scanner.nextInt();
            }
        }

        System.out.println("Position is " + x * y);
    }

    @GET
    @Path("/2/2")
    public void advent22() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("2.txt"));

        int x = 0;
        int y = 0;
        int aim = 0;
        String command;
        int units = 0;

        while (scanner.hasNext()) {
            command = scanner.next();
            units = scanner.nextInt();

            switch (command) {
                case "forward" -> {
                    x += units;
                    y += aim * units;
                }
                case "down" -> aim += units;
                case "up" -> aim -= units;
            }
        }

        System.out.println("Position is " + x * y);
    }

    @GET
    @Path("/3/1")
    public void advent31() throws IOException {
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
    @Path("/3/2")
    public void advent32() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("3.txt"));

        List<String> numbers = new ArrayList<>();
        String current;

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

package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
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

    @GET
    @Path("/4/1")
    public void advent41() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("4.txt"));

        String numbersLine = scanner.nextLine();
        Queue<Integer> toCallNumbers = new ArrayDeque<>(Arrays.stream(numbersLine.split(",")).map(Integer::parseInt).toList());

        int boardSize = 5;
        List<Board> boards = new ArrayList<>();

        while (scanner.hasNext()) {
            Board board = new Board();
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    board.board[i][j] = scanner.nextInt();
                }
            }
            boards.add(board);
        }

        boolean winHit = false;
        int playedNumber = 0;
        Board currentBoard = null;

        while (!winHit) {
            playedNumber = toCallNumbers.poll();

            for (Board b : boards) {
                currentBoard = b;
                winHit = b.play(playedNumber);
                if (winHit) {
                    break;
                }
            }
        }

        int sumOfUnmarked = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!currentBoard.marks[i][j]) {
                    sumOfUnmarked += currentBoard.board[i][j];
                }
            }
        }

        System.out.println(sumOfUnmarked * playedNumber);

    }

    @GET
    @Path("/4/2")
    public void advent42() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("4.txt"));

        String numbersLine = scanner.nextLine();
        Queue<Integer> toCallNumbers = new ArrayDeque<>(Arrays.stream(numbersLine.split(",")).map(Integer::parseInt).toList());

        int boardSize = 5;
        List<Board> boards = new ArrayList<>();

        while (scanner.hasNext()) {
            Board board = new Board();
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    board.board[i][j] = scanner.nextInt();
                }
            }
            boards.add(board);
        }

        int playedNumber = 0;
        Board lastWinningBoard = null;
        int lastWinningNumber = 0;

        while (!toCallNumbers.isEmpty() && !boards.isEmpty()) {
            playedNumber = toCallNumbers.poll();

            for (Iterator<Board> iter = boards.iterator(); iter.hasNext(); ) {
                Board b = iter.next();
                if (b.play(playedNumber)) {
                    lastWinningBoard = b;
                    lastWinningNumber = playedNumber;
                    iter.remove();
                }
            }
        }

        int sumOfUnmarked = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (!lastWinningBoard.marks[i][j]) {
                    sumOfUnmarked += lastWinningBoard.board[i][j];
                }
            }
        }

        System.out.println(sumOfUnmarked * lastWinningNumber);

    }

    private class Board {
        public int[][] board = new int[5][5];
        public boolean[][] marks = new boolean[5][5];

        public boolean play(int playedNumber) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (board[i][j] == playedNumber) {
                        marks[i][j] = true;
                        if (checkWin(i, j)) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        private boolean checkWin(int row, int column) {
            // check if row is winning
            boolean rowWinning = true;
            for (int j = 0; j < 5; j++) {
                rowWinning = rowWinning && marks[row][j];
            }

            if (rowWinning) {
                return true;
            }

            // check if column is winning
            boolean columnWinning = true;
            for (int i = 0; i < 5; i++) {
                columnWinning = columnWinning && marks[i][column];
            }

            if (columnWinning) {
                return true;
            }

            return false;
        }
    }

    @GET
    @Path("/5/1")
    public void advent51() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("5.txt"));

        int[][] ventMap = new int[1_000][1_000];

        while (scanner.hasNext()) {
            Vent vent = new Vent(scanner.nextLine());

            if (vent.x1 == vent.x2) {
                if (vent.y1 < vent.y2) {
                    for (int j = vent.y1; j <= vent.y2; j++) {
                        ventMap[vent.x1][j]++;
                    }
                } else if (vent.y1 > vent.y2) {
                    for (int j = vent.y2; j <= vent.y1; j++) {
                        ventMap[vent.x1][j]++;
                    }
                } else {
                    ventMap[vent.x1][vent.y1]++;
                }
            } else if (vent.y1 == vent.y2) {
                if (vent.x1 < vent.x2) {
                    for (int i = vent.x1; i <= vent.x2; i++) {
                        ventMap[i][vent.y1]++;
                    }
                } else if (vent.x1 > vent.x2) {
                    for (int i = vent.x2; i <= vent.x1; i++) {
                        ventMap[i][vent.y1]++;
                    }
                } else {
                    ventMap[vent.x1][vent.y1]++;
                }
            }
        }

        int count = 0;

        for (int i = 0; i < 1_000; i++) {
            for (int j = 0; j < 1_000; j++) {
                if (ventMap[i][j] >= 2) {
                    count++;
                }
            }
        }

        System.out.println(count);

    }

    @GET
    @Path("/5/2")
    public void advent52() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("5.txt"));

        int[][] ventMap = new int[1_000][1_000];

        while (scanner.hasNext()) {
            Vent vent = new Vent(scanner.nextLine());

            if (vent.x1 == vent.x2) {
                if (vent.y1 < vent.y2) {
                    for (int j = vent.y1; j <= vent.y2; j++) {
                        ventMap[j][vent.x1]++;
                    }
                } else if (vent.y1 > vent.y2) {
                    for (int j = vent.y2; j <= vent.y1; j++) {
                        ventMap[j][vent.x1]++;
                    }
                } else {
                    ventMap[vent.x1][vent.y1]++;
                }
            } else if (vent.y1 == vent.y2) {
                if (vent.x1 < vent.x2) {
                    for (int i = vent.x1; i <= vent.x2; i++) {
                        ventMap[vent.y1][i]++;
                    }
                } else if (vent.x1 > vent.x2) {
                    for (int i = vent.x2; i <= vent.x1; i++) {
                        ventMap[vent.y1][i]++;
                    }
                } else {
                    ventMap[vent.x1][vent.y1]++;
                }
            } else if (Math.abs(vent.x1 - vent.x2) == Math.abs(vent.y1 - vent.y2)) {
                if (vent.x1 < vent.x2) {
                    if (vent.y1 < vent.y2) {
                        for (int i = 0; i <= vent.x2 - vent.x1; i++) {
                            ventMap[vent.y1 + i][vent.x1 + i]++;
                        }
                    } else {
                        for (int i = 0; i <= vent.x2 - vent.x1; i++) {
                            ventMap[vent.y1 - i][vent.x1 + i]++;
                        }
                    }
                } else {
                    if (vent.y1 < vent.y2) {
                        for (int i = 0; i <= vent.x1 - vent.x2; i++) {
                            ventMap[vent.y1 + i][vent.x1 - i]++;
                        }
                    } else {
                        for (int i = 0; i <= vent.x1 - vent.x2; i++) {
                            ventMap[vent.y1 - i][vent.x1 - i]++;
                        }
                    }
                }
            }
        }

        int count = 0;

        for (int i = 0; i < 1_000; i++) {
            for (int j = 0; j < 1_000; j++) {
                if (ventMap[i][j] >= 2) {
                    count++;
                }
            }
        }

        System.out.println(count);

        for (int i = 0; i < 10; i++) {
            System.out.print("   ");
            for (int j = 0; j < 10; j++) {
                System.out.print(ventMap[i][j] == 0 ? "." : ventMap[i][j]);
            }
            System.out.println();
        }

    }


    private final static class Vent {
        public int x1;
        public int y1;
        public int x2;
        public int y2;

        public Vent(String inputLine) {
            String[] split = inputLine.split(" -> ");
            String[] split1 = split[0].split(",");
            x1 = Integer.parseInt(split1[0]);
            y1 = Integer.parseInt(split1[1]);
            String[] split2 = split[1].split(",");
            x2 = Integer.parseInt(split2[0]);
            y2 = Integer.parseInt(split2[1]);
        }
    }

    @GET
    @Path("/6/1")
    public void advent61() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("6.txt"));

        int[] lanternFish = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();

        int numOfDays = 80;
        int toAddCount;

        for (int day = 1; day <= numOfDays; day++) {
            toAddCount = 0;

            for (int i = 0; i < lanternFish.length; i++) {
                if (--lanternFish[i] < 0) {
                    toAddCount++;
                    lanternFish[i] = 6;
                }
            }

            if (toAddCount > 0) {
                int[] newFish = new int[lanternFish.length + toAddCount];
                System.arraycopy(lanternFish, 0, newFish, 0, lanternFish.length);
                for (int i = 0; i < toAddCount; i++) {
                    newFish[lanternFish.length + i] = 8;
                }

                lanternFish = newFish;
            }
        }

        System.out.println(lanternFish.length);
    }

    @GET
    @Path("/6/2")
    public void advent62() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("6.txt"));

        int numOfDays = 256;
        final long[] lanternFish = new long[9];

        Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt)
            .forEachOrdered(i -> lanternFish[i]++);

        long toAddCount;

        for (int day = 1; day <= numOfDays; day++) {
            toAddCount = lanternFish[0];

            for (int i = 1; i < lanternFish.length; i++) {
                lanternFish[i - 1] = lanternFish[i];
            }
            lanternFish[8] = 0;

            lanternFish[6] += toAddCount;
            lanternFish[8] += toAddCount;
        }

        long sum = 0;

        for (int i = 0; i < lanternFish.length; i++) {
            sum += lanternFish[i];
        }

        System.out.println(sum);
    }

    @GET
    @Path("/7/1")
    public void advent71() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("7.txt"));

        final int[] positions = Arrays.stream(scanner.nextLine().split(","))
            .mapToInt(Integer::parseInt).toArray();

        int maxPosition = Arrays.stream(positions).max()
            .orElseThrow(() -> new RuntimeException("can't find max"));

        int[] moves = new int[maxPosition + 1];

        for (int i = 0; i <= maxPosition; i++) {
            for (int j = 0; j < positions.length; j++) {
                moves[i] += Math.abs(positions[j] - i);
            }
        }

        System.out.println(Arrays.stream(moves).min().getAsInt());
    }

    @GET
    @Path("/7/2")
    public void advent72() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("7.txt"));

        final int[] positions = Arrays.stream(scanner.nextLine().split(","))
            .mapToInt(Integer::parseInt).toArray();

        int maxPosition = Arrays.stream(positions).max()
            .orElseThrow(() -> new RuntimeException("can't find max"));

        int[] moves = new int[maxPosition + 1];

        for (int i = 0; i <= maxPosition; i++) {
            for (int j = 0; j < positions.length; j++) {
                for (int k = 0; k <= Math.abs(positions[j] - i); k++) {
                    moves[i] += k;
                }
            }
        }

        System.out.println(Arrays.stream(moves).min().getAsInt());
    }

    @GET
    @Path("/8/1")
    public void advent81() throws IOException {
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
    @Path("/8/2")
    public void advent82() throws IOException {
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
            } else if (isSubSequence(substractString(matchingSignals.get("4"), matchingSignals.get("1")), s)) {
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
        for (char c: subsequence.toCharArray()) {
            if (signal.indexOf(c) < 0) {
                return false;
            }
        }

        return true;
    }

    private String substractString(String s1, String s2) {
        StringBuilder result = new StringBuilder();
        for (char c : s1.toCharArray()) {
            if (s2.indexOf(c) < 0) {
                result.append(c);
            }
        }

        return result.toString();
    }

    @GET
    @Path("/9/1")
    public void advent91() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("9.txt"));

        int[][] smokeHeights = new int[100][100];

        String line;
        int row = 0;

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                smokeHeights[row][i] = Integer.parseInt(String.valueOf(line.charAt(i)));
            }

            row++;
        }

        List<Integer> results = new ArrayList<>();

        for (int i = 0; i < smokeHeights.length; i++) {
            for (int j = 0; j < smokeHeights[i].length; j++) {
                if ((i <= 0 || smokeHeights[i - 1][j] > smokeHeights[i][j]) &&
                    (i + 1 >= smokeHeights.length || smokeHeights[i + 1][j] > smokeHeights[i][j]) &&
                    (j <= 0 || smokeHeights[i][j - 1] > smokeHeights[i][j]) &&
                    (j + 1 >= smokeHeights[i].length || smokeHeights[i][j + 1] > smokeHeights[i][j])) {
                    results.add(smokeHeights[i][j]);
                }
            }
        }

        System.out.println(results.stream().mapToInt(result -> result + 1).sum());
    }

    @GET
    @Path("/9/2")
    public void advent92() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("9.txt"));

        int[][] smokeHeights = new int[100][100];

        String line;
        int row = 0;

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                smokeHeights[row][i] = Integer.parseInt(String.valueOf(line.charAt(i)));
            }

            row++;
        }

        List<Integer> results = new ArrayList<>();

        for (int i = 0; i < smokeHeights.length; i++) {
            for (int j = 0; j < smokeHeights[i].length; j++) {
                if ((i <= 0 || smokeHeights[i - 1][j] > smokeHeights[i][j]) &&
                    (i + 1 >= smokeHeights.length || smokeHeights[i + 1][j] > smokeHeights[i][j]) &&
                    (j <= 0 || smokeHeights[i][j - 1] > smokeHeights[i][j]) &&
                    (j + 1 >= smokeHeights[i].length || smokeHeights[i][j + 1] > smokeHeights[i][j])) {
                    results.add(computeBasin(smokeHeights, i, j));
                }
            }
        }

        List<Integer> maxResults = results.stream().sorted(Comparator.reverseOrder()).limit(3).toList();
        System.out.println(maxResults.get(0) * maxResults.get(1) * maxResults.get(2));
    }

    private int computeBasin(int[][] smokeHeights, int i, int j) {
        boolean[][] counted = new boolean[100][100];

        Deque<PointToVisit> pointsToVisit = new ArrayDeque<>();
        pointsToVisit.add(new PointToVisit(smokeHeights[i][j] - 1, i, j));
        int basinSize = 0;

        while (!pointsToVisit.isEmpty()) {
            PointToVisit point = pointsToVisit.poll();


            if (smokeHeights[point.i][point.j] > point.valueToBeat) {
                if (counted[point.i][point.j] || smokeHeights[point.i][point.j] == 9) {
                    continue;
                } else {
                    basinSize++;
                    counted[point.i][point.j] = true;
                }

                if (point.i - 1 >= 0) {
                    pointsToVisit.add(new PointToVisit(smokeHeights[point.i][point.j], point.i - 1, point.j));
                }
                if (point.i + 1 < smokeHeights.length) {
                    pointsToVisit.add(new PointToVisit(smokeHeights[point.i][point.j], point.i + 1, point.j));
                }
                if (point.j - 1 >= 0) {
                    pointsToVisit.add(new PointToVisit(smokeHeights[point.i][point.j], point.i, point.j - 1));
                }
                if (point.j + 1 < smokeHeights[i].length) {
                    pointsToVisit.add(new PointToVisit(smokeHeights[point.i][point.j], point.i, point.j + 1));
                }
            }
        }

        return basinSize;
    }

    private class PointToVisit {

        public int valueToBeat;
        public int i;
        public int j;

        public PointToVisit(int valueToBeat, int i, int j) {
            this.valueToBeat = valueToBeat;
            this.i = i;
            this.j = j;
        }
    }

    @GET
    @Path("/10/1")
    public void advent101() throws IOException {
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
    @Path("/10/2")
    public void advent102() throws IOException {
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

    @GET
    @Path("/11/1")
    public void advent111() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("11.txt"));

        final int size = 10;

        int[][] octopuses = new int[size][size];
        String line;

        for (int i = 0; i < size; i++) {
            line = scanner.nextLine();
            for (int j = 0; j < size; j++) {
                octopuses[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
            }
        }

        int steps = 100;
        long flashCount = 0;
        boolean someoneFlashed;

        while (steps-- > 0) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    octopuses[i][j]++;
                }
            }

            someoneFlashed = true;

            while (someoneFlashed) {
                someoneFlashed = false;

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (octopuses[i][j] > 9) {
                            // flash the octopus at i, j
                            someoneFlashed = true;
                            flashCount++;
                            octopuses[i][j] = 0;
                            if (i - 1 >= 0) {
                                increaseLevel(octopuses, i - 1, j);
                                if (j - 1 >= 0) {
                                    increaseLevel(octopuses, i - 1, j - 1);
                                }
                                if (j + 1 < size) {
                                    increaseLevel(octopuses, i - 1, j + 1);
                                }
                            }
                            if (i + 1 < size) {
                                increaseLevel(octopuses, i + 1, j);
                                if (j - 1 >= 0) {
                                    increaseLevel(octopuses, i + 1, j - 1);
                                }
                                if (j + 1 < size) {
                                    increaseLevel(octopuses, i + 1, j + 1);
                                }
                            }
                            if (j - 1 >= 0) {
                                increaseLevel(octopuses, i, j - 1);
                            }
                            if (j + 1 < size) {
                                increaseLevel(octopuses, i, j + 1);
                            }
                        }
                    }
                }
            }
        }

        System.out.println(flashCount);
    }

    @GET
    @Path("/11/2")
    public void advent112() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("11.txt"));

        final int size = 10;

        int[][] octopuses = new int[size][size];
        String line;

        for (int i = 0; i < size; i++) {
            line = scanner.nextLine();
            for (int j = 0; j < size; j++) {
                octopuses[i][j] = Integer.parseInt(String.valueOf(line.charAt(j)));
            }
        }

        int steps = 500;
        int currentStep = 0;
        int flashCount = 0;
        boolean someoneFlashed;

        while (currentStep++ < steps) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    octopuses[i][j]++;
                }
            }

            someoneFlashed = true;
            flashCount = 0;

            while (someoneFlashed) {
                someoneFlashed = false;

                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        if (octopuses[i][j] > 9) {
                            // flash the octopus at i, j
                            someoneFlashed = true;
                            flashCount++;
                            octopuses[i][j] = 0;
                            if (i - 1 >= 0) {
                                increaseLevel(octopuses, i - 1, j);
                                if (j - 1 >= 0) {
                                    increaseLevel(octopuses, i - 1, j - 1);
                                }
                                if (j + 1 < size) {
                                    increaseLevel(octopuses, i - 1, j + 1);
                                }
                            }
                            if (i + 1 < size) {
                                increaseLevel(octopuses, i + 1, j);
                                if (j - 1 >= 0) {
                                    increaseLevel(octopuses, i + 1, j - 1);
                                }
                                if (j + 1 < size) {
                                    increaseLevel(octopuses, i + 1, j + 1);
                                }
                            }
                            if (j - 1 >= 0) {
                                increaseLevel(octopuses, i, j - 1);
                            }
                            if (j + 1 < size) {
                                increaseLevel(octopuses, i, j + 1);
                            }
                        }
                    }
                }
            }

            if (flashCount == 100) {
                // all octopuses flashed
                System.out.println(currentStep);
                break;
            }
        }
    }

    private void increaseLevel(int[][] octopuses, int i, int j) {
        if (octopuses[i][j] != 0) {
            octopuses[i][j]++;
        }
    }

    @GET
    @Path("/12/1")
    public void advent121() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("12.txt"));

        Map<String, List<String>> connections = new HashMap<>();
        String[] split;

        while (scanner.hasNext()) {
            split = scanner.nextLine().split("-");
            if (!split[0].equals("end") && !split[1].equals("start")) {
                connections.putIfAbsent(split[0], new ArrayList<>());
                connections.get(split[0]).add(split[1]);
            }
            if (!split[1].equals("end") && !split[0].equals("start")) {
                connections.putIfAbsent(split[1], new ArrayList<>());
                connections.get(split[1]).add(split[0]);
            }
        }

        List<List<String>> paths = new ArrayList<>();
        List<String> start = new ArrayList<>();
        start.add("start");
        paths.add(start);
        List<List<String>> newPaths;
        List<String> newPath;

        boolean canContinue = true;
        String last;
        List<String> possibleForwards;
        List<List<String>> finalPaths = new ArrayList<>();

        while (canContinue) {
            canContinue = false;
            newPaths = new ArrayList<>();
            for (List<String> path : paths) {
                last = path.get(path.size() - 1);
                possibleForwards = connections.get(last);
                if (possibleForwards == null) {
                    if (last.equals("end")) {
                        finalPaths.add(path);
                    }
                    continue;
                }
                for (String forward : possibleForwards) {
                    if (forward.equals(forward.toLowerCase())) {
                        // lowercase can only be visited once in any path
                        if (wasAlreadyVisited(path, forward)) {
                            continue;
                        }
                    }
                    newPath = new ArrayList<>(path);
                    newPath.add(forward);
                    newPaths.add(newPath);
                    canContinue = true;
                }
            }

            paths = newPaths;
        }

        System.out.println(finalPaths.size());

    }

    @GET
    @Path("/12/2")
    public void advent122() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("12.txt"));

        Map<String, List<String>> connections = new HashMap<>();
        String[] split;

        while (scanner.hasNext()) {
            split = scanner.nextLine().split("-");
            if (!split[0].equals("end") && !split[1].equals("start")) {
                connections.putIfAbsent(split[0], new ArrayList<>());
                connections.get(split[0]).add(split[1]);
            }
            if (!split[1].equals("end") && !split[0].equals("start")) {
                connections.putIfAbsent(split[1], new ArrayList<>());
                connections.get(split[1]).add(split[0]);
            }
        }

        List<Path12> paths = new ArrayList<>();
        Path12 startPath = new Path12();
        startPath.pathSegments.add("start");
        paths.add(startPath);
        List<Path12> newPaths;
        Path12 newPath;
        Path12 secondNewPath;

        boolean canContinue = true;
        String last;
        List<String> possibleForwards;
        Set<Path12> finalPaths = new HashSet<>();

        while (canContinue) {
            canContinue = false;
            newPaths = new ArrayList<>();
            for (Path12 path : paths) {
                last = path.pathSegments.get(path.pathSegments.size() - 1);
                possibleForwards = connections.get(last);
                for (String forward : possibleForwards) {
                    newPath = new Path12(path);
                    if (forward.equals("end")) {
                        newPath.pathSegments.add(forward);
                        finalPaths.add(newPath);
                        continue;
                    }
                    if (forward.equals(forward.toLowerCase())) {
                        if (newPath.repeatingCave == null) {
                            if (newPath.skippedSmallCaves.contains(forward)) {
                                // second access to the cave that is considered only once in this path
                                continue;
                            }

                            newPath.repeatingCave = forward;
                            newPath.repetitionCount = 1;

                            //add also new path that have this small cave only once
                            secondNewPath = new Path12(path);
                            secondNewPath.pathSegments.add(forward);
                            secondNewPath.skippedSmallCaves.add(forward);
                            newPaths.add(secondNewPath);

                        } else if (newPath.repeatingCave.equals(forward)) {
                            if (++newPath.repetitionCount > 2) {
                                continue;
                            }
                        } else if (wasAlreadyVisited(path.pathSegments, forward)) {
                            continue;
                        }
                    }

                    newPath.pathSegments.add(forward);
                    newPaths.add(newPath);
                    canContinue = true;
                }
            }

            paths = newPaths;
        }

        System.out.println(finalPaths.size());

    }

    private static final class Path12 {
        public List<String> pathSegments = new ArrayList<>();
        public String repeatingCave;
        public int repetitionCount;
        public List<String> skippedSmallCaves = new ArrayList<>();

        public Path12() {
        }

        public Path12(Path12 other) {
            this.pathSegments = new ArrayList<>(other.pathSegments);
            this.repeatingCave = other.repeatingCave;
            this.repetitionCount = other.repetitionCount;
            this.skippedSmallCaves = new ArrayList<>(other.skippedSmallCaves);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Path12 path12)) return false;
            return Objects.equals(pathSegments, path12.pathSegments);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pathSegments);
        }

        @Override
        public String toString() {
            return "Path12{" +
                "pathSegments=" + pathSegments +
                ", repeatingCave='" + repeatingCave + '\'' +
                ", repetitionCount=" + repetitionCount +
                ", skippedSmallCaves=" + skippedSmallCaves +
                '}';
        }
    }

    private boolean wasAlreadyVisited(List<String> path, String forward) {
        for (String pathElement : path) {
            if (pathElement.equals(forward)) {
                return true;
            }
        }

        return false;
    }

    @GET
    @Path("/13/1")
    public void advent131() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("13.txt"));

        int sizeX = 1500;
        int sizeY = 1500;

        boolean[][] dots = new boolean[sizeY][sizeX];
        String line = scanner.nextLine();
        String[] split;

        while (!line.isBlank()) {
            split = line.split(",");
            dots[Integer.parseInt(split[1])][Integer.parseInt(split[0])] = true;

            line = scanner.nextLine();
        }


        String[] firstInstruction = scanner.nextLine().split(" ")[2].split("=");
        int flipLine;

        if (firstInstruction[0].equals("y")) {
            flipLine = Integer.parseInt(firstInstruction[1]);
            for (int y = flipLine + 1; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    if (dots[y][x]) {
                        dots[flipLine - (y - flipLine)][x] = true;
                    }
                }
            }

            sizeY = flipLine;
            boolean[][] newDots = new boolean[sizeY][sizeX];
            for (int y = 0; y < sizeY; y++) {
                System.arraycopy(dots[y], 0, newDots[y], 0, sizeX);
            }
            dots = newDots;
        } else if (firstInstruction[0].equals("x")) {
            flipLine = Integer.parseInt(firstInstruction[1]);
            for (int y = 0; y < sizeY; y++) {
                for (int x = flipLine + 1; x < sizeX; x++) {
                    if (dots[y][x]) {
                        dots[y][flipLine - (x - flipLine)] = true;
                    }
                }
            }

            sizeX = flipLine;
            boolean[][] newDots = new boolean[sizeY][sizeX];
            for (int y = 0; y < sizeY; y++) {
                System.arraycopy(dots[y], 0, newDots[y], 0, sizeX);
            }
        }

        int dotCount = 0;

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                if (dots[y][x]) {
                    dotCount++;
                }
            }
        }

        System.out.println(dotCount);
    }

    @GET
    @Path("/13/2")
    public void advent132() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("13.txt"));

        int sizeX = 1500;
        int sizeY = 1500;

        boolean[][] dots = new boolean[sizeY][sizeX];
        String line = scanner.nextLine();
        String[] split;

        while (!line.isBlank()) {
            split = line.split(",");
            dots[Integer.parseInt(split[1])][Integer.parseInt(split[0])] = true;

            line = scanner.nextLine();
        }

        String[] instruction;
        int flipLine;

        while (scanner.hasNext()) {

            instruction = scanner.nextLine().split(" ")[2].split("=");

            if (instruction[0].equals("y")) {
                flipLine = Integer.parseInt(instruction[1]);
                for (int y = flipLine + 1; y < sizeY; y++) {
                    for (int x = 0; x < sizeX; x++) {
                        if (dots[y][x]) {
                            dots[flipLine - (y - flipLine)][x] = true;
                        }
                    }
                }

                sizeY = flipLine;
                boolean[][] newDots = new boolean[sizeY][sizeX];
                for (int y = 0; y < sizeY; y++) {
                    System.arraycopy(dots[y], 0, newDots[y], 0, sizeX);
                }
                dots = newDots;
            } else if (instruction[0].equals("x")) {
                flipLine = Integer.parseInt(instruction[1]);
                for (int y = 0; y < sizeY; y++) {
                    for (int x = flipLine + 1; x < sizeX; x++) {
                        if (dots[y][x]) {
                            dots[y][flipLine - (x - flipLine)] = true;
                        }
                    }
                }

                sizeX = flipLine;
                boolean[][] newDots = new boolean[sizeY][sizeX];
                for (int y = 0; y < sizeY; y++) {
                    System.arraycopy(dots[y], 0, newDots[y], 0, sizeX);
                }
            }
        }


        System.out.println();
        for (int y = 0; y < sizeY; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < sizeX; x++) {
                sb.append(dots[y][x] ? "#" : ".");
            }

            System.out.println(sb.toString());
        }
    }

    @GET
    @Path("/14/1")
    public void advent141() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("14.txt"));

        String template = scanner.nextLine();

        //empty line after that
        scanner.nextLine();

        Map<String, String> pairInsertions = new HashMap<>();
        String line;
        String[] split;

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            split = line.split(" -> ");
            pairInsertions.put(split[0], split[1]);
        }

        int steps = 10;
        StringBuilder newTemplateBuilder;

        for (int i = 0; i < steps; i++) {
            newTemplateBuilder = new StringBuilder();
            newTemplateBuilder.append(template.charAt(0));

            for (int j = 0; j < template.length() - 1; j++) {
                String pair = template.substring(j, j + 2);
                String insertion = pairInsertions.get(pair);
                newTemplateBuilder
                    .append(insertion)
                    .append(pair.charAt(1));
            }

            template = newTemplateBuilder.toString();
        }

        Map<Character, Integer> occurrences = new HashMap<>();

        for (char c : template.toCharArray()) {
            occurrences.merge(c, 1, Integer::sum);
        }

        int max = 0;
        int min = Integer.MAX_VALUE;


        for (Integer occurrence : occurrences.values()) {
            if (occurrence > max) {
                max = occurrence;
            }
            if (occurrence < min) {
                min = occurrence;
            }
        }

        System.out.println(max - min);
    }

}

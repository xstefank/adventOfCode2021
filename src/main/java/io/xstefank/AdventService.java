package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

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

}

package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.*;

@Path("/4")
public class AdventDay4 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
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
    @Path("/2")
    public void part2() throws Exception {
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


    public static final class Board {
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

}

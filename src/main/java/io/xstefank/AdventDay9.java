package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

@Path("/9")
public class AdventDay9 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
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
    @Path("/2")
    public void part2() throws Exception {
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
}

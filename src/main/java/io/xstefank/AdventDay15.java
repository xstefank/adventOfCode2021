package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

@Path("/15")
public class AdventDay15 {

    @Inject
    InputReader inputReader;

    private static final int size = 10;

    @GET
    @Path("/1")
    public void part1() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("15.txt"));

        int[][] caves = new int[size][size];

        String line;

        for (int i = 0; i < size; i++) {
            line = scanner.nextLine();
            for (int j = 0; j < size; j++) {
                caves[i][j] = Integer.parseInt(line.substring(j, j + 1));
            }
        }

        PriorityQueue<Point15> queue = new PriorityQueue<>(Comparator.comparing(p -> p.pathValue));
        Set<Point15> processedPoints = new HashSet<>();
        Map<Point15, Integer> lowestPaths = new HashMap<>();

        queue.add(new Point15(0, 0));
        Point15 endNode = new Point15(size - 1, size - 1);

        while (!queue.isEmpty()) {
            Point15 currentPoint = queue.poll();
            processedPoints.add(currentPoint);
            lowestPaths.put(currentPoint, currentPoint.pathValue);

            if (currentPoint.equals(endNode)) {
                break;
            }

            for (Point15 p : getNeighbours(currentPoint, processedPoints, size)) {
                int currentPathValue = currentPoint.pathValue;
                int caveValue = caves[p.i][p.j];

                if (lowestPaths.get(p) == null || currentPathValue + caveValue < lowestPaths.get(p)) {
                    lowestPaths.put(p, currentPathValue + caveValue);
                    p.pathValue = currentPathValue + caveValue;
                    queue.add(p);
                }
            }
        }

        System.out.println(lowestPaths.get(endNode));
    }

    private final static int size152 = 500;

    @GET
    @Path("/2")
    public void part2() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("15.txt"));

        int[][] caves = new int[size152][size152];

        String line;

        for (int i = 0; i < size152 / 5; i++) {
            line = scanner.nextLine();
            for (int j = 0; j < size152 / 5; j++) {
                caves[i][j] = Integer.parseInt(line.substring(j, j + 1));
            }
        }

        // copy the map 5 times in both directions
        int newValue;
        for (int k = 0; k < 5; k++) {
            for (int l = 0; l < 5; l++) {
                if (k == 0 && l == 0) {
                    continue;
                }
                for (int i = 0; i < size152 / 5; i++) {
                    for (int j = 0; j < size152 / 5; j++) {
                        newValue = computeValue(caves[i][j], k, l);
                        caves[(k * size152 / 5) + i][(l * size152 / 5) + j] = newValue;
                    }
                }
            }
        }

        PriorityQueue<Point15> queue = new PriorityQueue<>(Comparator.comparing(p -> p.pathValue));
        Set<Point15> processedPoints = new HashSet<>();
        Map<Point15, Integer> lowestPaths = new HashMap<>();

        queue.add(new Point15(0, 0));
        Point15 endNode = new Point15(size152 - 1, size152 - 1);

        while (!queue.isEmpty()) {
            Point15 currentPoint = queue.poll();
            processedPoints.add(currentPoint);
            lowestPaths.put(currentPoint, currentPoint.pathValue);

            if (currentPoint.equals(endNode)) {
                break;
            }

            for (Point15 p : getNeighbours(currentPoint, processedPoints, size152)) {
                int currentPathValue = currentPoint.pathValue;
                int caveValue = caves[p.i][p.j];

                if (lowestPaths.get(p) == null || currentPathValue + caveValue < lowestPaths.get(p)) {
                    lowestPaths.put(p, currentPathValue + caveValue);
                    p.pathValue = currentPathValue + caveValue;
                    queue.add(p);
                }
            }
        }

        System.out.println(lowestPaths.get(endNode));
    }

    private int computeValue(int originalValue, int k, int l) {
        int newValue = originalValue;
        for (int i = 0; i < k + l; i++) {
            newValue = newValue + 1 <= 9 ? newValue + 1: 1;
        }

        return newValue;
    }

    private List<Point15> getNeighbours(Point15 currentPoint, Set<Point15> processedPoints, int maxPosition) {
        List<Point15> result = new ArrayList<>();
        int i = currentPoint.i;
        int j = currentPoint.j;
        Point15 point15;

        if (i - 1 >= 0) {
            point15 = new Point15(i - 1, j);
            if (!processedPoints.contains(point15)) {
                result.add(point15);
            }
        }
        if (i + 1 < maxPosition) {
            point15 = new Point15(i + 1, j);
            if (!processedPoints.contains(point15)) {
                result.add(point15);
            }
        }
        if (j - 1 >= 0) {
            point15 = new Point15(i, j - 1);
            if (!processedPoints.contains(point15)) {
                result.add(point15);
            }
        }
        if (j + 1 < maxPosition) {
            point15 = new Point15(i, j + 1);
            if (!processedPoints.contains(point15)) {
                result.add(point15);
            }
        }

        return result;
    }

    private static final class Point15 {
        int i;
        int j;
        int pathValue;

        public Point15(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point15)) return false;
            Point15 point15 = (Point15) o;
            return i == point15.i && j == point15.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }
}

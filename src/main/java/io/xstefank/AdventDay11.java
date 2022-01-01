package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Scanner;

@Path("/11")
public class AdventDay11 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
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
    @Path("/2")
    public void part2() throws Exception {
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
}

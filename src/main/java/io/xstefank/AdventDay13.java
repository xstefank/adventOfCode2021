package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Path("/13")
public class AdventDay13 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
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
    @Path("/2")
    public void part2() throws Exception {
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
}

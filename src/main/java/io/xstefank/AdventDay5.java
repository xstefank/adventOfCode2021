package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Scanner;

@Path("/5")
public class AdventDay5 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
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
    @Path("/2")
    public void part2() throws Exception {
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

}

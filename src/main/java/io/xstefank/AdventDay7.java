package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.Scanner;

@Path("/7")
public class AdventDay7 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
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
    @Path("/2")
    public void part2() throws Exception {
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
}

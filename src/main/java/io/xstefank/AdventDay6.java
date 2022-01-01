package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.Scanner;

@Path("/6")
public class AdventDay6 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
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
    @Path("/2")
    public void part2() throws Exception {
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
}

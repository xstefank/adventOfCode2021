package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

@Path("/1")
public class AdventDay1 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
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
    @Path("/2")
    public void part2() throws Exception {
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


}

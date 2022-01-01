package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

@Path("/2")
public class AdventDay2 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
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
    @Path("/2")
    public void part2() throws Exception {
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


}

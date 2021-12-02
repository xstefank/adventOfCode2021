package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;
import java.util.Scanner;

@Path("/")
public class AdventService {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1/1")
    public void migratoryBirdsREST() throws IOException {
        Scanner scanner = new Scanner(inputReader.getFile("1-1.txt"));

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

}

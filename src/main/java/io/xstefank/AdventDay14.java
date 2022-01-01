package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Path("/14")
public class AdventDay14 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("14.txt"));

        String template = scanner.nextLine();

        //empty line after that
        scanner.nextLine();

        Map<String, String> pairInsertions = new HashMap<>();
        String line;
        String[] split;

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            split = line.split(" -> ");
            pairInsertions.put(split[0], split[1]);
        }

        int steps = 10;
        StringBuilder newTemplateBuilder;

        for (int i = 0; i < steps; i++) {
            newTemplateBuilder = new StringBuilder();
            newTemplateBuilder.append(template.charAt(0));

            for (int j = 0; j < template.length() - 1; j++) {
                String pair = template.substring(j, j + 2);
                String insertion = pairInsertions.get(pair);
                newTemplateBuilder
                        .append(insertion)
                        .append(pair.charAt(1));
            }

            template = newTemplateBuilder.toString();
        }

        Map<Character, Integer> occurrences = new HashMap<>();

        for (char c : template.toCharArray()) {
            occurrences.merge(c, 1, Integer::sum);
        }

        int max = 0;
        int min = Integer.MAX_VALUE;


        for (Integer occurrence : occurrences.values()) {
            if (occurrence > max) {
                max = occurrence;
            }
            if (occurrence < min) {
                min = occurrence;
            }
        }

        System.out.println(max - min);
    }

    @GET
    @Path("/2")
    public void part2() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("14.txt"));

        String template = scanner.nextLine();

        //empty line after that
        scanner.nextLine();

        Map<String, String[]> pairInsertions = new HashMap<>();
        String line;
        String[] split;

        while (scanner.hasNext()) {
            line = scanner.nextLine();
            split = line.split(" -> ");
            pairInsertions.put(split[0], new String[]{split[0].charAt(0) + split[1], split[1] + split[0].charAt(1)});
        }

        Map<Character, Long> occurrences = new HashMap<>();

        for (char c : template.toCharArray()) {
            occurrences.merge(c, 1L, Long::sum);
        }

        Map<String, Long> pairCounts = new HashMap<>();
        Map<String, Long> newPairCounts;

        for (int i = 0; i < template.length() - 1; i++) {
            pairCounts.merge(template.substring(i, i + 2), 1L, Long::sum);
        }

        System.out.println(pairCounts);

        for (int i = 0; i < 40; i++) {
            newPairCounts = new HashMap<>();

            for (Map.Entry<String, Long> pairCount : pairCounts.entrySet()) {
                String[] newPairs = pairInsertions.get(pairCount.getKey());
                occurrences.merge(newPairs[0].charAt(1), pairCount.getValue(), Long::sum);

                Long count1 = newPairCounts.getOrDefault(newPairs[0], 0L);
                Long count2 = newPairCounts.getOrDefault(newPairs[1], 0L);

                newPairCounts.put(newPairs[0], count1 + pairCount.getValue());
                newPairCounts.put(newPairs[1], count2 + pairCount.getValue());
            }

            pairCounts = newPairCounts;
        }

        long max = 0;
        long min = Long.MAX_VALUE;


        for (Long occurrence : occurrences.values()) {
            if (occurrence > max) {
                max = occurrence;
            }
            if (occurrence < min) {
                min = occurrence;
            }
        }

        System.out.println(max - min);
    }
}

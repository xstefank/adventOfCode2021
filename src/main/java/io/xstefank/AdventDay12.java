package io.xstefank;

import io.xstefank.util.InputReader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

@Path("/12")
public class AdventDay12 {

    @Inject
    InputReader inputReader;

    @GET
    @Path("/1")
    public void part1() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("12.txt"));

        Map<String, List<String>> connections = new HashMap<>();
        String[] split;

        while (scanner.hasNext()) {
            split = scanner.nextLine().split("-");
            if (!split[0].equals("end") && !split[1].equals("start")) {
                connections.putIfAbsent(split[0], new ArrayList<>());
                connections.get(split[0]).add(split[1]);
            }
            if (!split[1].equals("end") && !split[0].equals("start")) {
                connections.putIfAbsent(split[1], new ArrayList<>());
                connections.get(split[1]).add(split[0]);
            }
        }

        List<List<String>> paths = new ArrayList<>();
        List<String> start = new ArrayList<>();
        start.add("start");
        paths.add(start);
        List<List<String>> newPaths;
        List<String> newPath;

        boolean canContinue = true;
        String last;
        List<String> possibleForwards;
        List<List<String>> finalPaths = new ArrayList<>();

        while (canContinue) {
            canContinue = false;
            newPaths = new ArrayList<>();
            for (List<String> path : paths) {
                last = path.get(path.size() - 1);
                possibleForwards = connections.get(last);
                if (possibleForwards == null) {
                    if (last.equals("end")) {
                        finalPaths.add(path);
                    }
                    continue;
                }
                for (String forward : possibleForwards) {
                    if (forward.equals(forward.toLowerCase())) {
                        // lowercase can only be visited once in any path
                        if (wasAlreadyVisited(path, forward)) {
                            continue;
                        }
                    }
                    newPath = new ArrayList<>(path);
                    newPath.add(forward);
                    newPaths.add(newPath);
                    canContinue = true;
                }
            }

            paths = newPaths;
        }

        System.out.println(finalPaths.size());
    }

    @GET
    @Path("/2")
    public void part2() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("12.txt"));

        Map<String, List<String>> connections = new HashMap<>();
        String[] split;

        while (scanner.hasNext()) {
            split = scanner.nextLine().split("-");
            if (!split[0].equals("end") && !split[1].equals("start")) {
                connections.putIfAbsent(split[0], new ArrayList<>());
                connections.get(split[0]).add(split[1]);
            }
            if (!split[1].equals("end") && !split[0].equals("start")) {
                connections.putIfAbsent(split[1], new ArrayList<>());
                connections.get(split[1]).add(split[0]);
            }
        }

        List<Path12> paths = new ArrayList<>();
        Path12 startPath = new Path12();
        startPath.pathSegments.add("start");
        paths.add(startPath);
        List<Path12> newPaths;
        Path12 newPath;
        Path12 secondNewPath;

        boolean canContinue = true;
        String last;
        List<String> possibleForwards;
        Set<Path12> finalPaths = new HashSet<>();

        while (canContinue) {
            canContinue = false;
            newPaths = new ArrayList<>();
            for (Path12 path : paths) {
                last = path.pathSegments.get(path.pathSegments.size() - 1);
                possibleForwards = connections.get(last);
                for (String forward : possibleForwards) {
                    newPath = new Path12(path);
                    if (forward.equals("end")) {
                        newPath.pathSegments.add(forward);
                        finalPaths.add(newPath);
                        continue;
                    }
                    if (forward.equals(forward.toLowerCase())) {
                        if (newPath.repeatingCave == null) {
                            if (newPath.skippedSmallCaves.contains(forward)) {
                                // second access to the cave that is considered only once in this path
                                continue;
                            }

                            newPath.repeatingCave = forward;
                            newPath.repetitionCount = 1;

                            //add also new path that have this small cave only once
                            secondNewPath = new Path12(path);
                            secondNewPath.pathSegments.add(forward);
                            secondNewPath.skippedSmallCaves.add(forward);
                            newPaths.add(secondNewPath);

                        } else if (newPath.repeatingCave.equals(forward)) {
                            if (++newPath.repetitionCount > 2) {
                                continue;
                            }
                        } else if (wasAlreadyVisited(path.pathSegments, forward)) {
                            continue;
                        }
                    }

                    newPath.pathSegments.add(forward);
                    newPaths.add(newPath);
                    canContinue = true;
                }
            }

            paths = newPaths;
        }

        System.out.println(finalPaths.size());
    }

    private static final class Path12 {
        public List<String> pathSegments = new ArrayList<>();
        public String repeatingCave;
        public int repetitionCount;
        public List<String> skippedSmallCaves = new ArrayList<>();

        public Path12() {
        }

        public Path12(Path12 other) {
            this.pathSegments = new ArrayList<>(other.pathSegments);
            this.repeatingCave = other.repeatingCave;
            this.repetitionCount = other.repetitionCount;
            this.skippedSmallCaves = new ArrayList<>(other.skippedSmallCaves);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Path12 path12)) return false;
            return Objects.equals(pathSegments, path12.pathSegments);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pathSegments);
        }

        @Override
        public String toString() {
            return "Path12{" +
                    "pathSegments=" + pathSegments +
                    ", repeatingCave='" + repeatingCave + '\'' +
                    ", repetitionCount=" + repetitionCount +
                    ", skippedSmallCaves=" + skippedSmallCaves +
                    '}';
        }
    }

    private boolean wasAlreadyVisited(List<String> path, String forward) {
        for (String pathElement : path) {
            if (pathElement.equals(forward)) {
                return true;
            }
        }

        return false;
    }
}

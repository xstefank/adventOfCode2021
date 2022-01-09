package io.xstefank;

import io.xstefank.util.InputReader;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.IntStream;

@Path("/16")
@RequestScoped
public class AdventDay16 {

    @Inject
    InputReader inputReader;

    Deque<Character> inputDeque;
    String currentTranslatedBits = "";
    int totalReadBitsCount = 0;

    long part1VersionsSum = 0;

    static final Map<Character, String> BITS = new HashMap<>(16);

    static {
        BITS.put('0', "0000");
        BITS.put('1', "0001");
        BITS.put('2', "0010");
        BITS.put('3', "0011");
        BITS.put('4', "0100");
        BITS.put('5', "0101");
        BITS.put('6', "0110");
        BITS.put('7', "0111");
        BITS.put('8', "1000");
        BITS.put('9', "1001");
        BITS.put('A', "1010");
        BITS.put('B', "1011");
        BITS.put('C', "1100");
        BITS.put('D', "1101");
        BITS.put('E', "1110");
        BITS.put('F', "1111");
    }

    @GET
    @Path("/1")
    public void part1() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("16.txt"));

        initQueue(scanner);

        parsePacket();
        System.out.println("Versions sum = " + part1VersionsSum);
    }

    @GET
    @Path("/2")
    public void part2() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("16.txt"));

        initQueue(scanner);

        PacketResult packetResult = parsePacket();
        System.out.println("Final result = " + packetResult.result);
    }

    private void initQueue(Scanner scanner) {
        inputDeque = new ArrayDeque<>();

        for (Character c : scanner.next().toCharArray()) {
            inputDeque.add(c);
        }
    }

    private PacketResult parsePacket() {
        System.out.println("=================");

        int initialBitCount = totalReadBitsCount;

        int version = Integer.parseInt(requestBits(3), 2);
        part1VersionsSum += version;

        int typeId = Integer.parseInt(requestBits(3), 2);

        if (typeId == 4) {
            long result = parseLiteralValuePacket();
            return new PacketResult(totalReadBitsCount - initialBitCount, result);
        } else {
            // operators
            String lengthTypeId = requestBits(1);
            if (lengthTypeId.equals("0")) {
                int totalLengthInBits = Integer.parseInt(requestBits(15), 2);
                long result = parseTotalLengthPacket(totalLengthInBits, typeId);
                return new PacketResult(totalReadBitsCount - initialBitCount, result);
            } else {
                int numberOfSubpackets = Integer.parseInt(requestBits(11), 2);
                long result = parseNumberOfSubpacketsPacket(numberOfSubpackets, typeId);
                return new PacketResult(totalReadBitsCount - initialBitCount, result);
            }
        }
    }

    private long parseTotalLengthPacket(int totalLengthInBits, int typeId) {
        List<PacketResult> subpackets = new ArrayList<>();

        while (totalLengthInBits > 0) {
            System.out.println("Subpacket - ");
            PacketResult packetResult = parsePacket();
            subpackets.add(packetResult);
            totalLengthInBits -= packetResult.size;
        }

        return computePacketValue(subpackets, typeId);
    }

    private long parseNumberOfSubpacketsPacket(int numberOfSubpackets, int typeId) {
        List<PacketResult> subpackets = new ArrayList<>();

        for (int i = 0; i < numberOfSubpackets; i++) {
            subpackets.add(parsePacket());
        }

        return computePacketValue(subpackets, typeId);
    }

    private long computePacketValue(List<PacketResult> subpackets, int typeId) {
        System.out.println("subpackets = " + subpackets + ", typeId = " + typeId);
        return switch (typeId) {
            case 0 -> subpackets.stream().mapToLong(pr -> pr.result).sum();
            case 1 -> subpackets.stream().mapToLong(pr -> pr.result).reduce(1L, (a, b) -> a * b);
            case 2 -> subpackets.stream().mapToLong(pr -> pr.result).min().orElse(-1);
            case 3 -> subpackets.stream().mapToLong(pr -> pr.result).max().orElse(-1);
            case 5 -> subpackets.get(0).result > subpackets.get(1).result ? 1L : 0L;
            case 6 -> subpackets.get(0).result < subpackets.get(1).result ? 1L : 0L;
            case 7 -> Objects.equals(subpackets.get(0).result, subpackets.get(1).result) ? 1L : 0L;
            default -> 0L;
        };
    }

    private long parseLiteralValuePacket() {
        boolean stillReadingPacket = true;
        StringBuilder sb = new StringBuilder();

        while (stillReadingPacket) {
            stillReadingPacket = requestBits(1).equals("1");
            sb.append(requestBits(4));
        }

        return Long.parseLong(sb.toString(), 2);
    }

    private String requestBits(int number) {
        if (currentTranslatedBits.length() >= number) {
            String result = currentTranslatedBits.substring(0, number);
            currentTranslatedBits = currentTranslatedBits.substring(number);

            totalReadBitsCount += number;
            return result;
        }

        int length = number - currentTranslatedBits.length();
        StringBuilder sb = new StringBuilder(currentTranslatedBits);

        while (length > 0) {
            sb.append(BITS.get(inputDeque.poll()));
            length -= 4;
        }

        currentTranslatedBits = sb.toString();

        return requestBits(number);
    }

    static final class PacketResult {
        int size;
        Long result;

        public PacketResult(int size, Long result) {
            this.size = size;
            this.result = result;
        }

        @Override
        public String toString() {
            return "PacketResult{" +
                    "size=" + size +
                    ", result=" + result +
                    '}';
        }
    }
}

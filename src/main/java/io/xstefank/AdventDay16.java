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
import java.util.Scanner;

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

        inputDeque = new ArrayDeque<>();

        for (Character c : scanner.next().toCharArray()) {
            inputDeque.add(c);
        }

        parsePacket();
        System.out.println("Versions sum = " + part1VersionsSum);
    }

    private PacketResult parsePacket() {
        System.out.println("=================");

        int initialBitCount = totalReadBitsCount;

        int version = Integer.parseInt(requestBits(3), 2);
        part1VersionsSum += version;

        int typeId = Integer.parseInt(requestBits(3), 2);

        if (typeId == 4) {
            String result = parseLiteralValuePacket();
            System.out.println("Literal value packet " + Long.parseLong(result, 2));
            return new PacketResult(totalReadBitsCount - initialBitCount, result);
        } else {
            // operators
            String lengthTypeId = requestBits(1);
            if (lengthTypeId.equals("0")) {
                int totalLengthInBits = Integer.parseInt(requestBits(15), 2);
                String result = parseTotalLengthPacket(totalLengthInBits);
                System.out.println(result);
                return new PacketResult(totalReadBitsCount - initialBitCount, result);
            } else {
                int numberOfSubpackets = Integer.parseInt(requestBits(11), 2);
                for (int i = 0; i < numberOfSubpackets; i++) {
                    parsePacket();
                }
                return new PacketResult(totalReadBitsCount - initialBitCount, "");
            }
        }
    }

    private String parseTotalLengthPacket(int totalLengthInBits) {
        while (totalLengthInBits > 0) {
            System.out.println("Subpacket - ");
            PacketResult packetResult = parsePacket();

            totalLengthInBits -= packetResult.size;
        }

        return "";
    }

    private String parseLiteralValuePacket() {
        boolean stillReadingPacket = true;
        StringBuilder sb = new StringBuilder();

        while (stillReadingPacket) {
            stillReadingPacket = requestBits(1).equals("1");
            sb.append(requestBits(4));
        }

        return sb.toString();
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

    @GET
    @Path("/2")
    public void part2() throws Exception {
        Scanner scanner = new Scanner(inputReader.getFile("16.txt"));
    }

    static final class PacketResult {
        int size;
        List<String> results = new ArrayList<>();

        public PacketResult(int size, String... resultStrings) {
            this.size = size;
            results.addAll(Arrays.asList(resultStrings));
        }
    }
}

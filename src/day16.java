import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class day16 {

    private static final String DATA_NAME = "resources/data16.txt";
    private static Packet packet;

    public static void main(String[] args) {
        one();
        two();
    }

    private static void one() {
        initPacket();
        packet.decodePacket();
        System.out.println(computeSumOfVersion(packet));
    }

    private static void two() {
        initPacket();
        packet.decodePacket();
        System.out.println(computeEquation(packet));
    }

    private static int computeSumOfVersion(Packet p) {
        return p.packetVersion + p.subPackets.stream().map(day16::computeSumOfVersion).reduce(Integer::sum).orElse(0);
    }

    private static long computeEquation(Packet p) {
        return switch (p.typeID) {
            case 0 -> p.subPackets.stream().map(day16::computeEquation).reduce(Long::sum).orElse(0L);
            case 1 -> p.subPackets.stream().map(day16::computeEquation).reduce((i, i2) -> i * i2).orElse(0L);
            case 2 -> p.subPackets.stream().map(day16::computeEquation).reduce(Long::min).orElse(0L);
            case 3 -> p.subPackets.stream().map(day16::computeEquation).reduce(Long::max).orElse(0L);
            case 4 -> p.value;
            case 5 -> computeEquation(p.subPackets.get(0)) > computeEquation(p.subPackets.get(1)) ? 1 : 0;
            case 6 -> computeEquation(p.subPackets.get(0)) < computeEquation(p.subPackets.get(1)) ? 1 : 0;
            case 7 -> computeEquation(p.subPackets.get(0)) == computeEquation(p.subPackets.get(1)) ? 1 : 0;
            default -> throw new IllegalStateException("Unexpected value: " + p.typeID);
        };
    }

    private static long binaryToLong(String number) {
        return Long.parseLong(number, 2);
    }

    private static void initPacket() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(day16.DATA_NAME));
            String hexa = reader.readLine();
            StringBuilder binary = new StringBuilder();
            for (int i = 0; i < hexa.length(); i++) {
                switch (hexa.charAt(i)) {
                    case '0' -> binary.append("0000");
                    case '1' -> binary.append("0001");
                    case '2' -> binary.append("0010");
                    case '3' -> binary.append("0011");
                    case '4' -> binary.append("0100");
                    case '5' -> binary.append("0101");
                    case '6' -> binary.append("0110");
                    case '7' -> binary.append("0111");
                    case '8' -> binary.append("1000");
                    case '9' -> binary.append("1001");
                    case 'A' -> binary.append("1010");
                    case 'B' -> binary.append("1011");
                    case 'C' -> binary.append("1100");
                    case 'D' -> binary.append("1101");
                    case 'E' -> binary.append("1110");
                    case 'F' -> binary.append("1111");
                }
            }
            packet = new Packet(binary.toString());
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Packet {
        private final String packetValue;
        private final List<Packet> subPackets = new ArrayList<>();
        private int index = 0;
        private int packetVersion;
        private int typeID;
        private long value;

        public Packet(String packetValue) {
            this.packetValue = packetValue;
        }

        private Packet decodePacket() {
            packetVersion = getNextBitsToInt(3);
            typeID = getNextBitsToInt(3);
            if (typeID == 4) { // Literal value
                StringBuilder number = new StringBuilder();
                while (getNextBitsToInt(1) == 1) {
                    number.append(getNextBits(4));
                }
                // Get the last 4 bits after the '0'
                number.append(getNextBits(4));
                value = binaryToLong(number.toString());
            } else { // Operator
                int lengthTypeId = getNextBitsToInt(1);
                if (lengthTypeId == 0) {
                    int totalLength = getNextBitsToInt(15);
                    Packet subPacket = new Packet(getNextBits(totalLength));
                    while (!subPacket.isDecoded()) {
                        Packet decoded = subPacket.decodePacket();
                        subPackets.add(decoded);
                        subPacket = new Packet(decoded.getAllNextBits());
                    }
                } else {
                    int subPacketNumber = getNextBitsToInt(11);
                    Packet subPacket = new Packet(getAllNextBits());
                    for (int i = 0; i < subPacketNumber; i++) {
                        Packet decoded = subPacket.decodePacket();
                        subPackets.add(decoded);
                        index += decoded.index;
                        subPacket = new Packet(decoded.getAllNextBits());
                    }
                }
            }
            return this;
        }

        public String getNextBits(int nbBits) {
            index += nbBits;
            return packetValue.substring(index - nbBits, index);
        }

        public int getNextBitsToInt(int nbBits) {
            return Integer.parseInt(getNextBits(nbBits), 2);
        }

        public String getAllNextBits() {
            return packetValue.substring(index);
        }

        public boolean isDecoded() {
            return index >= packetValue.length() || !packetValue.substring(index).contains("1");
        }
    }
}
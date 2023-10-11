package cncLab01;

public class DESLab {

    static private void print(String str, String label) {
        System.out.format("%s\t= %s\n", label, str);
    }

    static private String hexToBinString(String str) {
        StringBuilder result = new StringBuilder(str.length() * 4);

        for (int i = 0; i < str.length(); i++) {
            try {
                switch (str.charAt(i)) {
                    case '0':
                        result.append("0000");
                        break;
                    case '1':
                        result.append("0001");
                        break;
                    case '2':
                        result.append("0010");
                        break;
                    case '3':
                        result.append("0011");
                        break;
                    case '4':
                        result.append("0100");
                        break;
                    case '5':
                        result.append("0101");
                        break;
                    case '6':
                        result.append("0110");
                        break;
                    case '7':
                        result.append("0111");
                        break;
                    case '8':
                        result.append("1000");
                        break;
                    case '9':
                        result.append("1001");
                        break;
                    case 'a':
                    case 'A':
                        result.append("1010");
                        break;
                    case 'b':
                    case 'B':
                        result.append("1011");
                        break;
                    case 'c':
                    case 'C':
                        result.append("1100");
                        break;
                    case 'd':
                    case 'D':
                        result.append("1101");
                        break;
                    case 'e':
                    case 'E':
                        result.append("1110");
                        break;
                    case 'f':
                    case 'F':
                        result.append("1111");
                        break;
                    default:
                        throw new Exception("Invalid hexadecimal character");
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        return result.toString();
    }

    static private String permute(String str, int[][] permutationTable) {
        StringBuilder result = new StringBuilder(permutationTable.length * permutationTable[0].length);

        for (int i = 0; i < permutationTable.length; i++) {
            for (int j = 0; j < permutationTable[0].length; j++) {
                result.append(str.charAt(permutationTable[i][j] - 1));
            }
        }

        return result.toString();
    }

    static private String leftShift(String str, int offset) {
        StringBuilder result = new StringBuilder(str.length());

        result.append(str.substring(offset));
        result.append(str.substring(0, offset));

        return result.toString();
    }

    static private String xor(String str1, String str2) {
        StringBuilder result = new StringBuilder(str1.length());
        try {
            if (str1.length() != str2.length()) {
                throw new Exception("Invalid parameters: the two strings must have equal lengths");
            }

            for (int i = 0; i < str1.length(); i++) {
                if (str1.charAt(i) == str2.charAt(i)) {
                    result.append("0");
                } else {
                    result.append("1");
                }
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return result.toString();
    }

    static private String sBox(String str, int[][] box) {
        int row = Integer.parseInt(str.substring(0, 1) + str.substring(5), 2);
        int col = Integer.parseInt(str.substring(1, 5), 2);

        int val = box[row][col];
        return String.format("%4s", Integer.toBinaryString(val)).replace(" ", "0");
    }

    public static void main(String[] args) {
        System.out.println("Session 2 ----------------------------------");
        // a.
        final String message = "0123456789abcdeF";
        final String key = "0123456789ab3067";

        String keyPlus = permute(hexToBinString(key), DESTables.PC_1);
        print(keyPlus, "K+");

        String c0 = keyPlus.substring(0, keyPlus.length() / 2);
        String d0 = keyPlus.substring(keyPlus.length() / 2);

        print(c0, "C0");
        print(d0, "D0");

        String c1 = leftShift(c0, DESTables.ITERATION_TABLE.get(1));
        String d1 = leftShift(d0, DESTables.ITERATION_TABLE.get(1));

        print(c1, "C1");
        print(d1, "D1");

        String c1d1 = c1.concat(d1);
        print(c1d1, "C1D1");

        String k1 = permute(c1d1, DESTables.PC_2);
        print(k1, "K1");

        // b.
        print(hexToBinString(message), "M");

        String ip = permute(hexToBinString(message), DESTables.IP_TABLE);
        print(ip, "IP");

        String l0 = ip.substring(0, ip.length() / 2);
        String r0 = ip.substring(ip.length() / 2);

        print(l0, "L0");
        print(r0, "R0");

        // c.
        String er0 = permute(r0, DESTables.E_TABLE);
        print(er0, "E[R0]");

        // d.
        String A = xor(k1, er0);
        print(A, "K+E[R0]");

        // e.
        StringBuilder BTemp = new StringBuilder(32);
        String tempStr;
        for (int i = 0; i < A.length() / 6; i++) {

            if ((i + 1) * 6 == A.length()) {
                tempStr = sBox(A.substring(i*6), DESTables.S_BOXES[i]);
            } else {
                tempStr = sBox(A.substring(i*6, i*6 + 6), DESTables.S_BOXES[i]);
            }

            print(tempStr, "S".concat(Integer.toString(i + 1)));
            BTemp.append(tempStr);
        }

        // f.
        String B = BTemp.toString();
        print(B, "B");

        // g.
        String pb = permute(B, DESTables.P_TABLE);
        print(pb, "P(B)");

        // h.
        String r1 = xor(pb, l0);
        print(r1, "R1");

        // g.
        String l1 = r0;
        print(l1, "L1");

        String r1l1 = r1.concat(l1);
        print(r1l1, "R1L1");

    }
}



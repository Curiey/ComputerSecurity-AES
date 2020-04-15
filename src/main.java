import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static void main(String[] args) {


        String str = "9F";
        byte[] bytes = toByteArray(str);


        File keyFile = new File("key_long");
        List[] keys = new List[0];

        try {

            String[] hex = readByteFileToHex(keyFile);

            //print
            for (int i = 0; i < hex.length; i++) {
                System.out.print(hex[i]);
            }
            System.out.println();

            keys = new List[hex.length/16];

            String[][] splitKeys = new String[hex.length/16][];
            for (int i = 0; i < splitKeys.length; i++) {
                splitKeys[i] = Arrays.copyOfRange(hex, i * 16, i * 16 + 16);
            }

            for (int i = 0; i < splitKeys.length; i++) {
                keys[i] = arrayToMatrix(splitKeys[i]);
            }

            System.out.println("Done");

            System.out.println();

            //print
            ArrayList<ArrayList<String>> currentArray;

            for (int i = 0; i < keys.length; i++) {

                currentArray = (ArrayList<ArrayList<String>>) keys[i];

                for (int j = 0; j < currentArray.size(); j++) {
                    for (int k = 0; k < currentArray.size(); k++) {

                        System.out.print(currentArray.get(j).get(k));
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("error");

        }
        System.out.println(keys);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }


    private static List<List<String>> arrayToMatrix(String[] arr) {

        if (arr == null) {
            throw new IllegalArgumentException();
        }

        int dim = whichPowerOfTwo(arr.length);

        if (dim == -1) {
            throw new IllegalArgumentException();
        }

        //initial return matrix
        List<List<String>> matrix = new ArrayList<>();
        for(int i = 0 ; i < dim; i++) {
            matrix.add(new ArrayList<>());
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                matrix.get(i).add(j, arr[i * dim + j]);
            }
        }
        return matrix;
    }

    /***
     * This function get a file contain bytes and return String array with
     * Hexadecimal values.
     *
     * @param byteFileToConvertToHexForm File.
     *
     * @return String array with the Hexadecimal values
     * @throws IOException If error wile reading the file occur
     */
    public static String[] readByteFileToHex(File byteFileToConvertToHexForm) throws IOException {

        byte[] byteArray = Files.readAllBytes(byteFileToConvertToHexForm.toPath()); // to read a single line from the file
        char[] chars = bytesToHex(byteArray);

        String[] hexs = new String[chars.length / 2];

        for (int i = 0; i < hexs.length; i++) {
            hexs[i] = "" + chars[i * 2] + chars[i * 2 + 1];
        }

        return hexs;
    }

    /***
     * Function get a byte array and convert it to Hexadecimal
     * Char array form.
     *
     * @param bytes byte array of data.
     * @return char array of hexadecimal form.
     */
    private static char[] bytesToHex(byte[] bytes) {

        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }

        return hexChars;
    }

    /***
     * This function check if the given number is power of the number Two.
     * @param n
     * @return
     */
    static boolean isPowerOfTwo(int n) {

        if (n == 0) {
            return false;
        }

        while (n != 1) {
            if (n % 2 != 0) {
                return false;
            }

            n = n / 2;
        }

        return true;
    }

    /***
     * Check Which power the number of base 2.
     *
     * @param n int.
     *
     * @return Integer, number that 2 in he power will equal to the given argument.
     * will return -1 if the given number is not power of Two
     */
    static int whichPowerOfTwo(int n) {
        if (n == 0 || n % 2 != 0) {
            return -1;
        }
        int counter = 0;
        while (n != 1) {

            n = n / 2;
            counter++;
        }
        return counter;
    }
}

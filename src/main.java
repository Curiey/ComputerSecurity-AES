import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.nio.file.Files;
import java.util.ArrayList;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        if (args[0].equals("–e") || args[0].equals("–d")) {

            //Get argument from command
            String keysPath = args[2];
            String plainTextPath = args[4];
            String outputPath = args[6];

            //initial Files
            File keysFile = new File(keysPath);
            File plainTextFile = new File(plainTextPath);

            //Split to list of blocks
            List[] plainTextSplitToBlocks = getBlocksByFile(plainTextFile);
            List[] keysSplitToBlocks = getBlocksByFile(keysFile);

            if (args[0].equals("–e")) {

                //initial keys
                AesEnc aesEnc = new AesEnc(keysSplitToBlocks);
                aesEnc.writeMatrixToFile(outputPath, plainTextSplitToBlocks);
                List[] cypher = aesEnc.encryption(plainTextSplitToBlocks);
                aesEnc.writeMatrixToFile(outputPath, cypher);

            } else if (args[0].equals("–d")) {

                AesDec aesDec = new AesDec(keysSplitToBlocks);
                aesDec.writeMatrixToFile(outputPath, plainTextSplitToBlocks);
                List[] plain = aesDec.decryption(plainTextSplitToBlocks);
                aesDec.writeMatrixToFile(outputPath, plain);
            }
        }
        if (args[0].equals("-b")) {
            String pathPlain = args[2];
            String pathCypher = args[4];
            String pathOutput = args[6];
            File filePlain = new File(pathPlain);
            File fileCypher = new File(pathCypher);
            List[] plainBlocks = getBlocksByFile(filePlain);
            List[] cypherBlocks = getBlocksByFile(fileCypher);
            AesBreak aesBreak = new AesBreak(plainBlocks, cypherBlocks);
            List[] keyThree = aesBreak.breakAes();
            aesBreak.writeMatrixToFile(pathOutput, keyThree);
        }
    }


//    public static byte[][] divideMessage(byte[] messageToDivide) {
//        int parts = messageToDivide.length / 128;
//        byte[][] dividedMessage = new byte[parts][128];
//        for (int i = 0; i < messageToDivide.length; i = i + 128) {
//            dividedMessage[i] = Arrays.copyOfRange(messageToDivide, i, i + 128);
//        }
//        return dividedMessage;
//    }


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

    /***
     * return list of blocks from a given file.
     *
     * @param plainFile file to read byte and convert to strings(Hex representation).
     *
     * @return list of blocks hex blocks
     */
    public static List[] getBlocksByFile(File plainFile) {

        List[] block = new List[0];

        try {

            String[] hex = readByteFileToHex(plainFile);

            block = new List[hex.length / 16];

            //split hex array by range of 16 (matrix 4x4)
            String[][] splitKeys = new String[block.length][];
            for (int i = 0; i < splitKeys.length; i++) {
                splitKeys[i] = Arrays.copyOfRange(hex, i * 16, i * 16 + 16);
            }

            //convert the array to matrix representation
            for (int i = 0; i < splitKeys.length; i++) {
                block[i] = arrayToMatrix(splitKeys[i]);
            }

        } catch (IOException e) {
            System.out.println("error in getBlocksByFile");

        }

        return block;
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
    private static String[] readByteFileToHex(File byteFileToConvertToHexForm) throws IOException {

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

        final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }

        return hexChars;
    }

    /***
     * convert a NxN array of strings to a NxN matrix representation.
     *
     * @param arr array of strings to convert to NxN format.
     *
     * @return 2-dim array contating the given array data.
     */
    private static List<List<String>> arrayToMatrix(String[] arr) {

        if (arr == null) {
            throw new IllegalArgumentException();
        }

        int dim = whichPowerOfTwo(arr.length);

        if (dim == -1) {
            throw new IllegalArgumentException("argument array cannot be converted to a NxN form");
        }

        //initial return matrix
        List<List<String>> matrix = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            matrix.add(new ArrayList<>());
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                matrix.get(i).add(j, arr[i * dim + j]);
            }
        }

        return matrix;
    }

}


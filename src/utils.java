import java.util.List;
import java.util.ArrayList;
import java.io.FileOutputStream;
import javax.xml.bind.DatatypeConverter;

public class utils {


    /***
     * Operate XOR operation between two matrix
     * containing string representation of Hex.
     *
     * @param matrix1 2-Dim ArrayList.
     * @param matrix2 2-Dim ArrayList.
     *
     * @return 2-Dim ArrayLis containing XOR result between two matrix.
     */
    public static ArrayList<ArrayList<String>> matrixXor(ArrayList<ArrayList<String>> matrix1, ArrayList<ArrayList<String>> matrix2) {

        if(matrix1 == null || matrix2 == null || matrix1.size() != matrix2.size()) {
            return null;
        }

        //check two matrix dimension
        for (int i = 0; i < matrix1.size(); i++) {
            if (matrix1.get(i).size() != matrix2.size()) {
                return null;
            }
        }

        //new result ArrayList
        ArrayList<ArrayList<String>> resultMatrix = new ArrayList<>();
        for (int i = 0; i < matrix1.size(); i++) {
            resultMatrix.add(new ArrayList<>());
        }

        for (int col = 0; col < matrix1.size(); col++) {
            for (int row = 0; row < matrix1.get(0).size(); row++) {

                //get values from both matrix
                String val1 = matrix1.get(col).get(row);
                String val2 = matrix2.get(col).get(row);

                //transform to Hex
                int hexMatField = Integer.parseInt(val1, 16);
                int hexKeyField = Integer.parseInt(val2, 16);

                //calculate XOR opetation
                int xorResult = hexMatField ^ hexKeyField;

                //transform to string formation
                String encryptedField = String.format("%02x", xorResult).toUpperCase(); //TODO:(guy) - String encryptedField = String.format("%06x", xorResult);

                resultMatrix.get(col).add(row, encryptedField);
            }
        }

        return resultMatrix;
    }

    /***
     * write 2-dim list to the given output file.
     *
     * @param path path to the output file.
     * @param listMatrixToWrite list containing 2-dim values
     */
    public static void writeMatrixToFile(String path, List[] listMatrixToWrite) {

        if(path == null || listMatrixToWrite == null) {
            return;
        }

        //get Dimension
        int matDim = listMatrixToWrite[0].size();
        byte[] myByteArray = new byte[matDim * matDim * listMatrixToWrite.length];

        //initial runner
        ArrayList<ArrayList<String>> matrixToWrite;
        String strValue = "";
        byte[] byteValue = new byte[0];

        //concatenate all list to one byte array
        for (int block = 0; block < listMatrixToWrite.length; block++) {

            matrixToWrite = (ArrayList<ArrayList<String>>)listMatrixToWrite[block];

            for (int i = 0; i < matrixToWrite.size(); i++) {
                for (int j = 0; j < matrixToWrite.size(); j++) {

                    //get values
                    strValue = matrixToWrite.get(i).get(j);
                    byteValue = hexToBytes(strValue.toCharArray());

                    //insert to result byte array
                    myByteArray[ (block*(matDim*matDim) + i*matrixToWrite.size() + j)] = byteValue[0];
                }
            }
        }

        //save byte array to file
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(myByteArray);
        } catch (Exception e) {
            System.out.println("exception write to file");
        }
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

    /***
     * Function get a Hex chars array and convert it to byte array
     * Char array form.
     *
     * @param chars chars array to convert to byte array
     *
     * @return byte array of hexadecimal form.
     */
    private static byte[] hexToBytes(char[] chars) {

        byte[] hexBytes = new byte[chars.length / 2];

        for (int i = 0; i < hexBytes.length; i=+2) {

            int v1 = (Character.digit(chars[i], 16) << 4);
            int v2 = Character.digit(chars[i + 1], 16);

            hexBytes[i / 2] = (byte)(v1 + v2);
        }

        return hexBytes;
    }

}
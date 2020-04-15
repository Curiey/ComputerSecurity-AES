import java.util.List;
import java.util.ArrayList;

public class AesBreak {

    //Fields
    private String keyOne = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
    private String keyTwo = "00000000000000000000000000000000";

    public AesBreak() {}


    /***
     * this function 'break' the key the given message and cipher message was encrypted.
     *
     * @param plainText 2-dim array containing hex of the plain text.
     * @param cipherText 2-dim array containing hex of the cipher text.
     *
     * @return List of keys.
     */
    public List[] breakAes(List[] plainText, List[] cipherText) {

        if (plainText == null || cipherText == null) {
            return null;
        }

        ArrayList<ArrayList<String>> shiftedPlainText;
        ArrayList<ArrayList<String>> currentPlainText = (ArrayList<ArrayList<String>>) plainText[0];

        //ShirtColumns 3 times
        shiftedPlainText = utils.shiftCols(currentPlainText);
        shiftedPlainText = utils.shiftCols(shiftedPlainText);
        shiftedPlainText = utils.shiftCols(shiftedPlainText);

        ArrayList<ArrayList<String>> shiftedPlainTextXORCipherText = utils.matrixXor(shiftedPlainText, (ArrayList<ArrayList<String>>) cipherText[0]);

        ArrayList<ArrayList<String>> key3 = utils.matrixXor(shiftedPlainTextXORCipherText, stringToHexMatrix(keyOne));

        List[] keys = new List[3];
        keys[0] = stringToHexMatrix(keyOne);
        keys[1] =stringToHexMatrix(keyTwo);
        keys[2] = key3;


        return keys;
    }

    /***
     * Convert array of string into matrix NxN of string.
     * each cell in the array list will be copy as Hex string representation.
     *
     * @param arr string to convert to Hex matrix
     *
     * @return List NxN with hex values according to the given string.
     */
    private ArrayList<ArrayList<String>> stringToHexMatrix(String arr) {

        if(arr == null) {
            return null;
        }

        int dim = utils.squareRootInt(arr.length() / 2);

        if(dim == -1) {
            return null;
        }

        ArrayList<ArrayList<String>> arrAsMatrix = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            arrAsMatrix.add(new ArrayList<>());
        }

        int counter = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {

                arrAsMatrix.get(j).add(i, arr.substring(counter, counter + 2));
                counter=+2;
            }
        }

        return arrAsMatrix;
    }

    /***
     * write 2-dim list to the given output file.
     *
     * @param path path to the output file.
     * @param listMatrixToWrite list containing 2-dim values
     */
    public void writeMatrixToFile(String path, List[] listMatrixToWrite) {

        utils.writeMatrixToFile(path, listMatrixToWrite);
    }
}

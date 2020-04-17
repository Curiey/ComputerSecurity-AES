import java.util.List;
import java.util.ArrayList;

public class AesDec {

    //Fields
    private ArrayList<ArrayList<String>> key1;
    private ArrayList<ArrayList<String>> key2;
    private ArrayList<ArrayList<String>> key3;

    /***
     * Constuctor.
     *
     * @param keys  List of Keys to encrypt with.
     */
    public AesDec(List[] keys) {
        this.key1 = (ArrayList<ArrayList<String>>)keys[0];
        this.key2 = (ArrayList<ArrayList<String>>)keys[1];
        this.key3 = (ArrayList<ArrayList<String>>)keys[2];
    }

    /***
     * Get a cipherText message and return decrypted message in List form
     * using the initial keys.
     *
     * @param cypherText List of 2 dim Arraylist containing cipherText as Hexadecimal values.
     *
     * @return list of 2-dim ArrayList containing encrypted message.
     */
    public List[] decryption(List[] cypherText) {

        if(cypherText == null) {
            return null;
        }

        List[] decrypted = new List[cypherText.length];

        //declare runner
        ArrayList<ArrayList<String>> currentMatToDec;
        for(int i = 0; i < cypherText.length; i++) {

            //get current message
            currentMatToDec =(ArrayList<ArrayList<String>>) cypherText[i];

            //iteration 1
            currentMatToDec = utils.matrixXor(currentMatToDec, this.key3);
            currentMatToDec = shiftColsInv(currentMatToDec);

            //iteration 2
            currentMatToDec = utils.matrixXor(currentMatToDec, this.key2);
            currentMatToDec = shiftColsInv(currentMatToDec);

            //iteration 3
            currentMatToDec = utils.matrixXor(currentMatToDec, this.key1);
            currentMatToDec = shiftColsInv(currentMatToDec);

            //insert to decrypted list
            decrypted[i] = currentMatToDec;
        }

        return decrypted;
    }

    /***
     * Shift columns to a given 2-dim ArrayList.
     * shift each column of index i, i shifts down.
//     *
     * @param matrixToShift 2-dim ArrayList to shift.
     *
     * @return 2-dim ArrayList shifted.
     */
    public ArrayList<ArrayList<String>> shiftColsInv(ArrayList<ArrayList<String>> matrixToShift) {

        if(matrixToShift == null) {
            return null;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                String removed = matrixToShift.get(i).remove(matrixToShift.get(i).size()-1);
                matrixToShift.get(i).add(0, removed);
            }
        }

        return matrixToShift;
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

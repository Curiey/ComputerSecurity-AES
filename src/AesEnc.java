import java.util.List;
import java.util.ArrayList;

public class AesEnc {

    //Fields
    private ArrayList<ArrayList<String>> key1;
    private ArrayList<ArrayList<String>> key2;
    private ArrayList<ArrayList<String>> key3;

    /***
     * Constructor.
     *
     * @param keys List of Keys to encrypt with.
     */
    public AesEnc(List[] keys) {
        this.key1 = (ArrayList<ArrayList<String>>)keys[0];
        this.key2 = (ArrayList<ArrayList<String>>)keys[1];
        this.key3 = (ArrayList<ArrayList<String>>)keys[2];
    }

    /***
     * Get a plainText message and return encrypted message in List form
     * using the initial keys.
     *
     * @param plainText List of 2 dim Arraylist containing Hexadecimal values.
     *
     * @return list of 2-dim ArrayList containing encrypted message.
     */
    public List[] encryption(List[] plainText) {

        if(plainText == null) {
            return null;
        }

        //initial list to encrypted message
        List[] encrypted = new List[plainText.length];

        //declare runner
        ArrayList<ArrayList<String>> currentMatToEnc;

        for(int i = 0; i < plainText.length; i++) {

            //get current message
            currentMatToEnc =(ArrayList<ArrayList<String>>) plainText[i];

            //iteration 1
            currentMatToEnc = shiftCols(currentMatToEnc);
            currentMatToEnc = utils.matrixXor(currentMatToEnc, this.key1);

            //iteration 2
            currentMatToEnc = shiftCols(currentMatToEnc);
            currentMatToEnc = utils.matrixXor(currentMatToEnc, this.key2);

            //iteration 3
            currentMatToEnc = shiftCols(currentMatToEnc);
            currentMatToEnc = utils.matrixXor(currentMatToEnc, this.key3);

            //insert to encrypted list
            encrypted[i] = currentMatToEnc;
        }

        return encrypted;
    }

    /***
     * Shift columns to a given 2-dim ArrayList.
     * shift each column of index i, i shifts up.
     *
     * @param matrixToShift 2-dim ArrayList to shift.
     *
     * @return 2-dim ArrayList shifted.
     */
    public ArrayList<ArrayList<String>> shiftCols(ArrayList<ArrayList<String>> matrixToShift) {

        if(matrixToShift == null) {
            return null;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                String removed = matrixToShift.get(i).remove(0);
                matrixToShift.get(i).add(matrixToShift.get(i).size(), removed); //TODO:(guy) matrixToShift.get(i).add(matrixToShift.get(i).size(), removed);
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
    public void writeMatrixToFile(String path, List[] listMatrixToWrite)
    {
        utils.writeMatrixToFile(path, listMatrixToWrite);
    }
}

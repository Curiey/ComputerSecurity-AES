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

    public List[] decryption(List[] cypherText) {

        List[] decrypted = new List[cypherText.length];

        //declare runner
        ArrayList<ArrayList<String>> currentMatToDec;
        for(int i = 0; i < cypherText.length; i++) {

            currentMatToDec =(ArrayList<ArrayList<String>>) cypherText[i];

            currentMatToDec = utils.matrixXor(currentMatToDec, this.key3);
            currentMatToDec = shiftColsInv(currentMatToDec);
            currentMatToDec = utils.matrixXor(currentMatToDec, this.key2);
            currentMatToDec = shiftColsInv(currentMatToDec);
            currentMatToDec = utils.matrixXor(currentMatToDec, this.key1);
            currentMatToDec = shiftColsInv(currentMatToDec);

            decrypted[i] = currentMatToDec;
        }
        return decrypted;
    }


    public ArrayList<ArrayList<String>> shiftColsInv(ArrayList<ArrayList<String>> matrixToShift) {

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

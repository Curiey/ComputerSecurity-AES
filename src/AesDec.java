import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AesDec {


    private List[] cypherText;
    private ArrayList<ArrayList<String>> key1;
    private ArrayList<ArrayList<String>> key2;
    private ArrayList<ArrayList<String>> key3;

    public AesDec(List[] cypherText, List[] keys) {
        this.cypherText = cypherText;
        this.key1 = (ArrayList<ArrayList<String>>)keys[0];
        this.key2 = (ArrayList<ArrayList<String>>)keys[1];
        this.key3 = (ArrayList<ArrayList<String>>)keys[2];
    }

    public List[] decryption() {

        List[] decrypted = new List[cypherText.length];
        ArrayList<ArrayList<String>> currentMatToDec;
        for(int i=0; i<cypherText.length; i++) {

            currentMatToDec =(ArrayList<ArrayList<String>>) cypherText[i];

            currentMatToDec = xorKeyMatrix(currentMatToDec, this.key3);
            currentMatToDec = shiftColsInv(currentMatToDec);
            currentMatToDec = xorKeyMatrix(currentMatToDec, this.key2);
            currentMatToDec = shiftColsInv(currentMatToDec);
            currentMatToDec = xorKeyMatrix(currentMatToDec, this.key1);
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

    public ArrayList<ArrayList<String>> xorKeyMatrix(ArrayList<ArrayList<String>> matrixToXor, ArrayList<ArrayList<String>> key) {

        ArrayList<ArrayList<String>> resultMatrix = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            resultMatrix.add(new ArrayList<String>());
        }
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                String matField = matrixToXor.get(col).get(row);
                String keyField = key.get(col).get(row);
                int hexMatField = Integer.parseInt(matField, 16);
                int hexKeyField = Integer.parseInt(keyField, 16);
                int xorResult = hexMatField ^ hexKeyField;
                String encryptedField = String.format("%06x", xorResult);
                resultMatrix.get(col).add(row, encryptedField);
            }
        }
        return resultMatrix;
    }

    public void writeMatrixToFile(String path, List[] listMatrixToWrite) {

        if(path == null || listMatrixToWrite == null) {
            return;
        }

        int matDim = listMatrixToWrite[0].size();
        byte[] myByteArray = new byte[matDim * matDim * listMatrixToWrite.length*2];
        ArrayList<ArrayList<String>> matrixToWrite;

        for (int block = 0; block < listMatrixToWrite.length; block++) {

            matrixToWrite = (ArrayList<ArrayList<String>>)listMatrixToWrite[block];

            for (int i = 0; i < matrixToWrite.size(); i++) {
                for (int j = 0; j < matrixToWrite.size(); j++) {
                    String value = matrixToWrite.get(i).get(j);

                    myByteArray[(block*(matDim * matDim) + i * matrixToWrite.size() + j) * 2] = Byte.parseByte(value.substring(0,1));
                    myByteArray[(block*(matDim * matDim) + i * matrixToWrite.size() + j) * 2 + 1] = Byte.parseByte(value.substring(1));
                }
            }
            try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(myByteArray);
            } catch (Exception e) {
                System.out.println("excepion write to file");
            }
        }
    }


}

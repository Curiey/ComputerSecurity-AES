import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AesEnc {

    private List[] plainText;
    private ArrayList<ArrayList<String>> key1;
    private ArrayList<ArrayList<String>> key2;
    private ArrayList<ArrayList<String>> key3;

    public AesEnc(List[] plainText, List[] keys) {
        this.plainText = plainText;
        this.key1 = (ArrayList<ArrayList<String>>)keys[0];
        this.key2 = (ArrayList<ArrayList<String>>)keys[1];
        this.key3 = (ArrayList<ArrayList<String>>)keys[2];
    }

    public List[] encription() {

        List[] encrypted = new List[plainText.length];
        ArrayList<ArrayList<String>> currentMatToEnc;
        for(int i=0; i<plainText.length; i++) {

            currentMatToEnc =(ArrayList<ArrayList<String>>) plainText[i];

            currentMatToEnc = shiftCols(currentMatToEnc);
            currentMatToEnc = xorKeyMatrix(currentMatToEnc, this.key1);
            currentMatToEnc = shiftCols(currentMatToEnc);
            currentMatToEnc = xorKeyMatrix(currentMatToEnc, this.key2);
            currentMatToEnc = shiftCols(currentMatToEnc);
            currentMatToEnc = xorKeyMatrix(currentMatToEnc, this.key3);
            encrypted[i] = currentMatToEnc;
        }
    return encrypted;
}


    public ArrayList<ArrayList<String>> shiftCols(ArrayList<ArrayList<String>> matrixToShift) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < i; j++) {
                String removed = matrixToShift.get(i).remove(0);
                matrixToShift.get(i).add(matrixToShift.get(i).size() - 1, removed);
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

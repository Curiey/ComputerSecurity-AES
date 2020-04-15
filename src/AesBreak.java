import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AesBreak {


    private List[] plainText;
    private List[] cypherText;
    private String keyOne;
    private String keyTwo;

    public AesBreak(List[] plainText, List[] cypherText) {
        this.plainText = plainText;
        this.cypherText = cypherText;
        this.keyOne = "0000000000000000";
        this.keyTwo = "1111111111111111";
    }
    // TODO: 14/04/2020  break function
    public List[] breakAes(){
        List[] key3 = new List[0];
        return key3;
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

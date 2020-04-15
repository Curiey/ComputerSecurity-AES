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

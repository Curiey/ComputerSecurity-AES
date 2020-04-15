import java.io.File;
import java.util.List;

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
            List[] plainTextSplitToBlocks = utils.getBlocksByFile(plainTextFile);
            List[] keysSplitToBlocks = utils.getBlocksByFile(keysFile);

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
        if (args[0].equals("–b")) {

            String pathPlain = args[2];
            String pathCypher = args[4];
            String pathOutput = args[6];

            File filePlain = new File(pathPlain);
            File fileCypher = new File(pathCypher);

            List[] plainBlocks = utils.getBlocksByFile(filePlain);
            List[] cypherBlocks = utils.getBlocksByFile(fileCypher);

            AesBreak aesBreak = new AesBreak();
            List[] keys = aesBreak.breakAes(plainBlocks, cypherBlocks);
            aesBreak.writeMatrixToFile(pathOutput, keys);
        }
    }
}


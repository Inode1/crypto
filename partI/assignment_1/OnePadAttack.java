import java.util.HashSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class OnePadAttack {
    private static String[] test = {"315c4eeaa8b5f8aaf9174145bf43e1784b8fa00dc71d885a804e5ee9fa40b16349c146fb778cdf2d3aff021dfff5b403b510d0d0455468aeb98622b137dae857553ccd8883a7bc37520e06e515d22c954eba5025b8cc57ee59418ce7dc6bc41556bdb36bbca3e8774301fbcaa3b83b220809560987815f65286764703de0f3d524400a19b159610b11ef3e",
                                    "234c02ecbbfbafa3ed18510abd11fa724fcda2018a1a8342cf064bbde548b12b07df44ba7191d9606ef4081ffde5ad46a5069d9f7f543bedb9c861bf29c7e205132eda9382b0bc2c5c4b45f919cf3a9f1cb74151f6d551f4480c82b2cb24cc5b028aa76eb7b4ab24171ab3cdadb8356f",
                                    "32510ba9a7b2bba9b8005d43a304b5714cc0bb0c8a34884dd91304b8ad40b62b07df44ba6e9d8a2368e51d04e0e7b207b70b9b8261112bacb6c866a232dfe257527dc29398f5f3251a0d47e503c66e935de81230b59b7afb5f41afa8d661cb",
                                    "32510ba9aab2a8a4fd06414fb517b5605cc0aa0dc91a8908c2064ba8ad5ea06a029056f47a8ad3306ef5021eafe1ac01a81197847a5c68a1b78769a37bc8f4575432c198ccb4ef63590256e305cd3a9544ee4160ead45aef520489e7da7d835402bca670bda8eb775200b8dabbba246b130f040d8ec6447e2c767f3d30ed81ea2e4c1404e1315a1010e7229be6636aaa",
                                    "3f561ba9adb4b6ebec54424ba317b564418fac0dd35f8c08d31a1fe9e24fe56808c213f17c81d9607cee021dafe1e001b21ade877a5e68bea88d61b93ac5ee0d562e8e9582f5ef375f0a4ae20ed86e935de81230b59b73fb4302cd95d770c65b40aaa065f2a5e33a5a0bb5dcaba43722130f042f8ec85b7c2070",
                                    "32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd2061bbde24eb76a19d84aba34d8de287be84d07e7e9a30ee714979c7e1123a8bd9822a33ecaf512472e8e8f8db3f9635c1949e640c621854eba0d79eccf52ff111284b4cc61d11902aebc66f2b2e436434eacc0aba938220b084800c2ca4e693522643573b2c4ce35050b0cf774201f0fe52ac9f26d71b6cf61a711cc229f77ace7aa88a2f19983122b11be87a59c355d25f8e4",
                                    "32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd90f1fa6ea5ba47b01c909ba7696cf606ef40c04afe1ac0aa8148dd066592ded9f8774b529c7ea125d298e8883f5e9305f4b44f915cb2bd05af51373fd9b4af511039fa2d96f83414aaaf261bda2e97b170fb5cce2a53e675c154c0d9681596934777e2275b381ce2e40582afe67650b13e72287ff2270abcf73bb028932836fbdecfecee0a3b894473c1bbeb6b4913a536ce4f9b13f1efff71ea313c8661dd9a4ce",
                                    "315c4eeaa8b5f8bffd11155ea506b56041c6a00c8a08854dd21a4bbde54ce56801d943ba708b8a3574f40c00fff9e00fa1439fd0654327a3bfc860b92f89ee04132ecb9298f5fd2d5e4b45e40ecc3b9d59e9417df7c95bba410e9aa2ca24c5474da2f276baa3ac325918b2daada43d6712150441c2e04f6565517f317da9d3",
                                    "271946f9bbb2aeadec111841a81abc300ecaa01bd8069d5cc91005e9fe4aad6e04d513e96d99de2569bc5e50eeeca709b50a8a987f4264edb6896fb537d0a716132ddc938fb0f836480e06ed0fcd6e9759f40462f9cf57f4564186a2c1778f1543efa270bda5e933421cbe88a4a52222190f471e9bd15f652b653b7071aec59a2705081ffe72651d08f822c9ed6d76e48b63ab15d0208573a7eef027",
                                    "466d06ece998b7a2fb1d464fed2ced7641ddaa3cc31c9941cf110abbf409ed39598005b3399ccfafb61d0315fca0a314be138a9f32503bedac8067f03adbf3575c3b8edc9ba7f537530541ab0f9f3cd04ff50d66f1d559ba520e89a2cb2a83"};
    private static byte BIGLITTLE = 0x1;
    private static byte EQUALS = 0;
    private static byte SIGNBIG = 0x3;
    private static byte SIGNLITTLE = 0x2;
    private static byte SIGN = 0x4;
    private static byte BIGSYMBOLS = 0x8;
    private static byte LITTLESYMBOLS = 0x8;

    private HashSet<String> dict = new HashSet<String>();
    // base cepher text
    private int cypherNumber;
    // his length
    private int minCypherLen;
    // base hex char cypher text
    private char[] baseCypherText;
    // array indicate position in cypher text
    private byte[] positionArray;
    // xor cypher text
    private char[][] xorText; 
    public OnePadAttack(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                dict.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cypherNumber = 0;
        minCypherLen = Integer.MAX_VALUE;
        for (int i = 0; i < test.length; ++i) {
            if (minCypherLen > test[i].length()) {
                minCypherLen = test[i].length();
                cypherNumber = i;
            }
        }
        positionArray  = new byte[minCypherLen / 2];
        xorText        = new char[test.length - 1][test[cypherNumber].length() / 2];
        baseCypherText = stringHexToChar(test[cypherNumber]);
        System.out.print("baseCypherText: " + baseCypherText.length);
        System.out.print("xorText: " + xorText[0].length);
        cypherTextLowerCase();
    }

    private char[] stringHexToChar(String hexString) {
        char[] charString = new char[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            charString[i / 2] = (char) Integer.parseInt(hexString.substring(i, (i + 2)), 16);
        }
        return charString;
    }

    // lower case and set space
    public void cypherTextFormat() {

    }

    public void printMessage() {

    }
    // xor with all 
    private void cypherTextLowerCase() {
        for (int i = 0, j = 0; i < test.length; ++i) {
            if (i == cypherNumber) {
                continue;
            }
            char[] tempHexChar = stringHexToChar(test[i]);
            for (int k = 0; k < baseCypherText.length; ++k) {
                char xorResult = (char) (tempHexChar[k] ^ baseCypherText[k]);
                xorText[j][k] = xorResult;
                if (positionArray[k] != SIGN && positionArray[k] != BIGLITTLE) {
                    byte checkSymbol = (byte)((xorResult >> 6) & 0xf);
                    if (checkSymbol != 0) {
                        if (positionArray[k] == 0) {
                            positionArray[k] = checkSymbol;
                        }
                        else if (positionArray[k] == SIGNLITTLE && (checkSymbol == BIGLITTLE || checkSymbol == SIGNBIG)) {
                            if (checkSymbol == BIGLITTLE) {
                                positionArray[k] = BIGLITTLE;
                            } else {
                                positionArray[k] = SIGN;
                            }
                        }
                        else if (positionArray[k] == SIGNBIG && (checkSymbol == BIGLITTLE || checkSymbol == SIGNLITTLE)) {
                            if (checkSymbol == BIGLITTLE) {
                                positionArray[k] = BIGLITTLE;
                            } else {
                                positionArray[k] = SIGN;
                            }     
                        }
                    }
                }
                
            }
            ++j;
        }

    }

    private void findWordInDict() {

    }

    public void printDebug() {
/*        for (int i = 0; i < xorText.length; ++i) {
            for (int j = 0; j < xorText[i].length; ++j) {
                System.out.print((int)xorText[i][j] + " ");
            }
            System.out.println();
        }*/
        for (int i = 0; i < positionArray.length; ++i) {
            if (positionArray[i] == EQUALS) {
                //System.out.print("EQUALS" + " ");
            } else if (positionArray[i] == BIGLITTLE) {
                //System.out.print("BIGLITTLE" + " ");
            } else if (positionArray[i] == SIGNBIG) {
                System.out.print("SIGNBIG" + " ");
            } else if (positionArray[i] == SIGNLITTLE) {
                System.out.print("SIGNLITTLE" + " ");
            } else if (positionArray[i] == SIGN) {
                System.out.print("SIGN" + " ");
            } else {
                System.out.print("Unkinown" + " ");
            }
        }
    }

    public static void main(String[] args) {
        OnePadAttack attack = new OnePadAttack(args[0]);
        attack.printDebug();

    }
}
import java.util.HashMap;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.PriorityQueue;
import java.util.ArrayList;

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
                                    "466d06ece998b7a2fb1d464fed2ced7641ddaa3cc31c9941cf110abbf409ed39598005b3399ccfafb61d0315fca0a314be138a9f32503bedac8067f03adbf3575c3b8edc9ba7f537530541ab0f9f3cd04ff50d66f1d559ba520e89a2cb2a83",
                                    "32510ba9babebbbefd001547a810e67149caee11d945cd7fc81a05e9f85aac650e9052ba6a8cd8257bf14d13e6f0a803b54fde9e77472dbff89d71b57bddef121336cb85ccb8f3315f4b52e301d16e9f52f904"};
    
    private SecureRandom random = new SecureRandom();
    private byte[] randomString;
    private double[] freq = {0.08167, 0.01492, 0.02782, 0.04253, 0.12702, 0.02228,
                             0.02015, 0.06094, 0.06966, 0.00153, 0.00772, 0.04025,
                             0.02406, 0.06749, 0.07507, 0.01929, 0.00095, 0.05987,
                             0.06327, 0.09056, 0.02758, 0.00978, 0.02360, 0.00150,
                             0.01974, 0.00074};

    private static char BIGLITTLE = 0x1;
    private static char EQUALS = 0;
    private static char SIGNBIG = 0x3;
    private static char SIGNLITTLE = 0x2;
    private static char SIGN = 0x4;
    private static char LITTLE = 0x8;
    private static char BIGGEST = 0x10;

    private HashMap<Integer, ArrayList<String>> dict = new HashMap<Integer, ArrayList<String>>();
    private HashSet<String> dictWord = new HashSet<String>();

    // base cepher text
    private int cypherNumber;
    // his length
    private int maxCypherLen;
    // 
    private int minCypherLen;
    // base hex char cypher text
    private char[] baseCypherText;
    // element position
    private char[][] elementPosistion;
    // array indicate position in cypher text
    private char[][][] positionArray;
    // xor cypher texts
    private char[][] xorText;
    //
    private char[][][] xorEveryText;
    // xor with random string
    private char[][] xorWithRandomString; 
    // 
    private double[] frequencyLetter = new double[26];
    //
    private int totalLength = 0; 

    class BestRandom implements Comparable<BestRandom> {
        byte[] random;
        double probability;
        public BestRandom(byte[] random, double probability) {
            this.random = new byte[random.length];
            for (int i = 0; i < random.length; ++i) {
                this.random[i] = random[i];
            }
            this.probability = probability; 
        }
        public int compareTo(BestRandom that)
        {
            return Double.compare(probability, that.probability);
        }

    }
    public OnePadAttack(String fileName) {
        try {
            File file = new File(fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                dictWord.add(line);
                ArrayList<String> temp = dict.get(line.length());
                if (temp == null) {
                    temp = new ArrayList<String>();
                    dict.put(line.length(), temp);
                }
                temp.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cypherNumber = 0;

        maxCypherLen = 0;
        minCypherLen = Integer.MAX_VALUE;
        for (int i = 0; i < test.length; ++i) {
            /*totalLength += test[i].length() / 2;*/
            if (maxCypherLen < test[i].length()) {
                maxCypherLen = test[i].length();
                cypherNumber = i;
            }
            if (minCypherLen > test[i].length()) {
                minCypherLen = test[i].length();
            }
        }
        totalLength = test[0].length() / 2;
        maxCypherLen /= 2;
        minCypherLen /= 2;
        randomString = new byte[maxCypherLen];
        xorText        = new char[test.length][];
        xorWithRandomString = new char[test.length][];
        for (int i = 0; i < xorText.length; ++i) {
            xorText[i] = stringHexToChar(test[i]);
            xorWithRandomString[i] = new char[test[i].length() / 2];
        }

        PriorityQueue<BestRandom> best = new PriorityQueue<BestRandom>(50);

/*        for (int i = 0; i < 100; ++i) {
            generateRandomString();
            for (int k = 0; k < 1; ++k) {
                for (int j = 0; j < xorWithRandomString[k].length; ++j) {
                    xorWithRandomString[k][j] = (char) (xorText[k][j] ^ randomString[j]);
                }
            }
            double temp = getFrequency();
            //System.out.println(temp);
            if (temp == Double.POSITIVE_INFINITY) {
                --i;
                continue;
            }
            best.add(new BestRandom(randomString, temp));
        }
        BestRandom temp = best.peek();
        System.out.println(temp.probability);

        byte[] goodRandom = temp.random;

        for (int i = 0; i < xorText.length; ++i) {
            for (int j = 0; j < xorText[i].length; ++j) {
                char result = (char) (xorText[i][j] ^ goodRandom[j]); 
                System.out.print(result);
            }
            System.out.println();
        }*/

        xorEveryText   = new char[test.length][test.length][];
        positionArray  = new char[test.length][test.length][];
        elementPosistion = new char[test.length][minCypherLen];


        //baseCypherText = stringHexToChar(test[cypherNumber]);
        createXorTextArray();
        //findWordInDict();
        //decodeElementPosition();
    }

    private void generateRandomString() {
        random.nextBytes(randomString);
    }

    private double getFrequency() {
        for (int i = 0; i < frequencyLetter.length; ++i) {
            frequencyLetter[i] = 0;
        }
        int count = 0;
        for (int i = 0; i < 1; ++i) {
            for (int j = 0; j < xorWithRandomString[i].length; ++j) {
                char letter = xorWithRandomString[i][j];

                if (letter >= 'A' && letter <= 'Z') {
                    //System.out.print(letter);
                    ++frequencyLetter[letter - 65];
                    ++count;

                } else if (letter >= 'a' && letter <= 'z') {
                    ++frequencyLetter[letter - 97];
                    ++count;
                }
            }
        }

        if (count < 0.2 * totalLength){
            //System.out.println(count);
            return Double.POSITIVE_INFINITY;
        }
        System.out.println(count);

        double totalProbability = 0.0;
        for (int i = 0; i < frequencyLetter.length; ++i) {
            totalProbability += ((frequencyLetter[i] / (100 * count)) - freq[i]) * ((frequencyLetter[i] / (100 * count)) - freq[i]);
        }

        totalProbability /= 26;
        totalProbability = Math.sqrt(totalProbability);

/*        for (int i = 0; i < xorWithRandomString[0].length; ++i) {
            System.out.print(xorWithRandomString[0][i]);
        }*/

        return totalProbability;
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


    private void decodeElementPosition() {
        for (int l = 0; l < minCypherLen; ++l) {
            for (int i = 0; i < positionArray.length; ++i) {
                boolean changeValue = false;
                char newValue = 0;
                for (int z = 0; z < 10;   ++z)
                {
                    for (int j = 0; j < positionArray[i].length; ++j) {
                        if (i == j) {
                            continue;
                        }
                        // 
                        if (newValue != positionArray[i][j][l]) {
                            if (newValue == EQUALS) {
                                newValue = positionArray[i][j][l];
                            } else if (positionArray[i][j][l] == EQUALS) {
                                positionArray[i][j][l] = newValue;
                            } else if (newValue == BIGLITTLE && positionArray[i][j][l] == SIGNBIG) {
                                newValue = BIGGEST;
                                positionArray[i][j][l] = SIGN;
                            } else if (newValue == SIGNBIG && positionArray[i][j][l] == BIGLITTLE) {
                                newValue = BIGGEST;
                                positionArray[i][j][l] = LITTLE;                           
                            } else if (newValue == SIGNBIG && positionArray[i][j][l] == SIGNLITTLE) {
                                newValue = SIGN;
                                positionArray[i][j][l] = LITTLE; 
                            } else if (newValue == SIGNLITTLE && positionArray[i][j][l] == SIGNBIG) {
                                newValue = SIGN;
                                positionArray[i][j][l] = BIGGEST; 
                            } else if (newValue == BIGLITTLE && positionArray[i][j][l] == SIGNLITTLE) {
                                newValue = LITTLE;
                                positionArray[i][j][l] = SIGN; 
                            } else if (newValue == SIGNLITTLE && positionArray[i][j][l] == BIGLITTLE) {
                                newValue = LITTLE;
                                positionArray[i][j][l] = BIGGEST; 
                            } else if (newValue == SIGN && positionArray[i][j][l] == SIGNBIG) {
                                positionArray[i][j][l] = BIGGEST; 
                            } else if (newValue == SIGN && positionArray[i][j][l] == SIGNLITTLE) {
                                positionArray[i][j][l] = LITTLE; 
                            } else if (newValue == SIGNBIG && positionArray[i][j][l] == SIGN) {
                                newValue = BIGGEST; 
                            } else if (newValue == SIGNLITTLE && positionArray[i][j][l] == SIGN) {
                                newValue = LITTLE; 
                            } else if (newValue == LITTLE && positionArray[i][j][l] == BIGLITTLE) {
                                positionArray[i][j][l] = BIGGEST; 
                            } else if (newValue == BIGLITTLE && positionArray[i][j][l] == LITTLE) {
                                newValue = LITTLE; 
                            } else if (newValue == LITTLE && positionArray[i][j][l] == SIGNLITTLE) {
                                positionArray[i][j][l] = SIGN; 
                            } else if (newValue == SIGNLITTLE && positionArray[i][j][l] == LITTLE) {
                                newValue = SIGN; 
                            } else if (newValue == BIGGEST && positionArray[i][j][l] == SIGNBIG) {
                                positionArray[i][j][l] = SIGN; 
                            } else if (newValue == SIGNBIG && positionArray[i][j][l] == BIGGEST) {
                                newValue = SIGN; 
                            } else if (newValue == BIGGEST && positionArray[i][j][l] == BIGLITTLE) {
                                positionArray[i][j][l] = LITTLE; 
                            } else if (newValue == BIGLITTLE && positionArray[i][j][l] == BIGGEST) {
                                newValue = LITTLE; 
                            }
                        }
                    }
                }
                elementPosistion[i][l] = newValue;
            }
        }
    }

    private void createXorTextArray() {
        char[] firstHex = null;
        char[] secondHex = null;
        for (int i = 0; i < xorEveryText.length; ++i) {
            for (int j = 0; j < xorEveryText[i].length; ++j) {
                if (i == j) {
                    continue;
                }
                if (j > i) {
                    firstHex = xorText[i];
                    secondHex = xorText[j]; 
                    xorEveryText[i][j] = new char[firstHex.length < secondHex.length ? firstHex.length : secondHex.length];
                    xorEveryText[j][i] = new char[xorEveryText[i][j].length];                
                    positionArray[i][j] = new char[xorEveryText[i][j].length];                
                    positionArray[j][i] = new char[xorEveryText[i][j].length];                
                    for (int l = 0; l < xorEveryText[i][j].length; ++l) {
                        char xorResult = (char) (firstHex[l] ^ secondHex[l]);
                        char checkSymbol = (char) ((xorResult >> 5) & 0x3); 
/*                        if (checkSymbol == SIGNBIG || checkSymbol == BIGLITTLE) {
                            xorResult ^= ' ';
                            positionArray[i][j][l] = (checkSymbol == SIGNBIG ? SIGNLITTLE : LITTLE);
                            positionArray[j][i][l] = (checkSymbol == SIGNBIG ? SIGNLITTLE : LITTLE);
                        } else {*/
                            positionArray[i][j][l] = checkSymbol;
                            positionArray[j][i][l] = checkSymbol;
                        //}
                        xorEveryText[i][j][l] = xorResult;
                        xorEveryText[j][i][l] = xorResult;
                    }
                }
            }
        }
    }

    private void findWordInDict() {
        int i = 1;
        int j = 5;
        char[] xor = xorEveryText[i][j];
        int k = 5;
        char[] result = new char[k];
        char[][] encryptResult = new char[text.length][minCypherLen]
        ArrayList<String> temp = dict.get(5);
        for (String str: temp) {
            for (int z = 0; z < k; ++z) {
                result[z] = (char) (xor[z] ^ str.charAt(z));
            }
            if (dictWord.contains(new String(result))) {
                char[] cypher = new char[k];
                for (int n = 0; n < k; ++n) {
                    cypher[n] = (char) (result[n] ^ xorText[j][n]);
                }
                //System.out.println(new String(result));
                //System.out.println(str);
                //System.out.println("Start");
                for (int c = 0; c < xorText.length; ++c) {
                    for (int b = 0; b < k; ++b) {
                        System.out.printf("%c", (char) (xorText[c][b] ^ cypher[b]));
                    }
                    System.out.println();
                }

            }
        }
    }

    public void printDebug() {
/*        for (int i = 0; i < xorText.length; ++i) {
            for (int j = 0; j < xorText[i].length; ++j) {
                if (i == j) {
                    continue;
                }
                for (int l = 0; l < xorText[i][j].length; ++l) {
                    System.out.printf("%h ", xorText[i][j][l]);
                }
                System.out.println();
            }
            System.out.println();
        }*/
        for (int i = 0; i < positionArray.length; ++i) {
            for (int j = 0; j < positionArray.length; ++j) {
                if (i == j) {
                    continue;
                }
                for (int l = 0; l < 1; ++l) {
                    if (positionArray[i][j][l] == EQUALS) {
                        System.out.print("EQUALS" + " ");
                        
                    } else if (positionArray[i][j][l] == BIGLITTLE) {
                        System.out.print("BIGLITTLE" + " ");
                    } else if (positionArray[i][j][l] == SIGNBIG) {
                        System.out.print("SIGNBIG" + " ");
                    } else if (positionArray[i][j][l] == SIGNLITTLE) {
                        System.out.print("SIGNLITTLE" + " ");
                    } else if (positionArray[i][j][l] == SIGN) {
                        System.out.print("SIGN" + " ");
                    } else if (positionArray[i][j][l] == LITTLE) {
                        System.out.print("LITTLE" + " ");
                    } else {
                        System.out.print("Unkinown" + " ");
                    }
                }
            }
            System.out.print("\n\n");
        }
    }

    public static void main(String[] args) {
        OnePadAttack attack = new OnePadAttack(args[0]);
        attack.printDebug();

    }
}
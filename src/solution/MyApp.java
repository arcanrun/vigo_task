package solution;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MyApp implements DataConnection {
    public  static class MyAppFactory {
        public  static MyApp create(String yearStrInit) {
            return new MyApp(yearStrInit);
        }
    }


    public MyApp(String yearStrInit) {
        this.yearStrInit = yearStrInit;
    }

    private String yearStrInit;
    private static int count;
    private static int lineNumber;


    public static final int START_YEAR = 1990;
    public static final int END_YEAR = 2020;


    /**
     * @param args
     */
    public static void main(String[] args) {
        mainCalc();
    }

    private static void mainCalc() {
        try {
            System.out.println("app v.1.13");

            for (int year = START_YEAR; year < END_YEAR; year++) {
                count = 0;
                int sum = 0;

                String yearStr = "" + year;
                sum = MyAppFactory.create(yearStr).loadData(sum);

                double qq = 0;

                if (sum > 0) {
                    qq = (double) sum / (double) count;
                }

                if (qq > 0) {
                    System.out.println(year + " " + qq);
                }

              MyAppFactory.create(yearStr).saveData(year, (int) qq);
            }
            System.out.println("DONE");
        } catch (IOException e) {
            System.out.println("ERROR!");
            e.printStackTrace();
        }
    }


    @Override
    public int loadData(int sum) throws IOException {

        List<Byte> byteList = new ArrayList<>();
        File file = new File("1.txt");

        FileInputStream fis = new FileInputStream(file);
        int b;
        while ((b = fis.read()) != -1) {
            byteList.add((byte) b);
        }

        fis.close();

        String textFromFile = new String(byteArrayConverter(byteList));

        return calcSum(sum, textFromFile);

    }

    private int calcSum(int sum, String textFromFile) {
        int begin = 0;
        while (true) {
            int indexOfTranslation = textFromFile.indexOf("\n", begin + 1);

            if (indexOfTranslation == -1) {
                break;
            }

            String line = textFromFile.substring(begin, indexOfTranslation);
            System.out.println(line);
            String[] wordsInLine = line.split(" ");
            for (String word : wordsInLine) {
                System.out.println(word);
            }

            if (wordsInLine[2].contains(yearStrInit)) {
                sum += Integer.parseInt(wordsInLine[3]);
            }

            count++;
            begin = indexOfTranslation;
        }
        return sum;
    }


    public void saveData(int year, int qq) throws IOException {
        FileOutputStream fos = new FileOutputStream("statistics", true);
        String outPutData = lineNumber + " " + year + " " + qq + "\n";
        fos.write(outPutData.getBytes());
        lineNumber++;
        fos.close();
    }

    private byte[] byteArrayConverter(List<Byte> inputList) {
        byte[] res = new byte[inputList.size()];
        for (int i = 0; i < inputList.size(); i++) {
            res[i] = inputList.get(i);
        }
        return res;
    }
}


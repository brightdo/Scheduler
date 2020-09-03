import java.io.*;
import java.util.*;

public class Main {


    public static Scanner newScanner(String fileName) {
        try {
            Scanner input = new Scanner(new BufferedReader(new FileReader(fileName)));
            return input;
        } catch (Exception ex) {
            System.out.printf("Error reading %s\n", fileName);
            System.exit(0);
        }
        return null;
    }


    public static void ListFileData(Scanner filename, Scanner otherFile, ArrayList < String > allData, ArrayList < String > allRandomNum) {
        String[] indviData = null;
        while (filename.hasNext()) {
            String currentLine = filename.next();
            indviData = currentLine.split("\\s+");
            for (int i = 0; i < indviData.length; i++) {
                if (!"".equals(indviData[i])) {
                    allData.add(indviData[i]);
                }
            }
        }

        String[] splitFile = null;
        while (otherFile.hasNext()) {
            String line = otherFile.next();
            splitFile = line.split("\\s+");

            for (int i = 0; i < splitFile.length; i++) {
                if (!"".equals(splitFile[i])) {
                    allRandomNum.add(splitFile[i]);
                }
            }
        }
    }


    public static int randomOS(int U, ArrayList < String > randomList) {
        return 1 + (Integer.parseInt((String) randomList.get(0)) % U);
    }

    public static void main(String[] args) throws IOException {
        String filename;
        String randomNumFile;
        boolean specific = false;
        filename = args[0];
        if (args[1].contains("--verbose")) {
            specific = true;
            randomNumFile = args[2];
        } else {
            randomNumFile = args[1];
        }

        Scanner input = newScanner(filename);
        Scanner otherInput = newScanner(randomNumFile);
        ArrayList allData = new ArrayList < String > ();
        ArrayList allRandomNum = new ArrayList < String > ();

        ListFileData(input, otherInput, allData, allRandomNum);

        FCFS fcfs = new FCFS(allData, allRandomNum);
        fcfs.output(specific);

        Uniprocessor uni = new Uniprocessor(allData, allRandomNum);
        uni.output(specific);

        PSJF psjf = new PSJF(allData, allRandomNum);
        psjf.output(specific);

        RoundRobin RR = new RoundRobin(allData, allRandomNum);
        RR.output(specific);



    }






}
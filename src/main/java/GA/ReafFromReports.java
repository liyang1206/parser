package GA;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReafFromReports {

    public static int[] findBestSolution() throws IOException {
        File file = getLastModified("output");
        int len = remodelparse.numofUC+remodelparse.numofEntity;
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;

        double  object1 = -100000;

        ArrayList<Integer> temp = new ArrayList<>();
        while ((str = in.readLine()) != null) {
            if (str.contains("GENERATION: "+GA.iteration)){
                while ((str = in.readLine()) != null){
                    if (str.contains("CHILD ")){
                        while ((str = in.readLine()) != null){
                            if (str.contains("#")){
                                String str2 = in.readLine();
                                if (str2.contains("Rank: 1")){
                                    String[] secondstrs = str2.split("\\s+");
                                    for (String ss : secondstrs){
                                        if (ss.startsWith("-") || ss.startsWith("0")){
                                            double tempobject1 = Double.parseDouble(ss);
                                            if (tempobject1 > object1){
                                                temp.clear();
                                                object1 = tempobject1;
                                                String[] firststrs = str.split("\\s+");
                                                for (String sss: firststrs){
                                                    if (sss.charAt(0) >= '0' && sss.charAt(0) <='9'){
                                                        temp.add(Integer.parseInt(sss));
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                                else
                                    continue;
                            }
                        }
                    }
                }
            }

        }
        int[] result = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            result[i] = temp.get(i);
        }
        return result;
    }
    public static File getLastModified(String directoryFilePath) {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null; if (files != null) {
            for (File file : files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file; lastModifiedTime = file.lastModified();
                }
            }
        }
        return chosenFile;
    }

}

import GA.ReafFromReports;
import GA.remodelparse;

import java.io.IOException;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) throws IOException {
        ArrayList<double[]> UCtoE = remodelparse.instancegetUCtoE;
        ArrayList<double[]> EtoE = remodelparse.instancegetEtoE;
        remodelparse ooo = new remodelparse();
        ooo.getComplexity();
        ArrayList<ArrayList<Integer>>divi =new ArrayList<>();
        ArrayList<ArrayList<Integer>>Edivi =new ArrayList<>();
        int UCnum = UCtoE.size();
        int Enum = EtoE.size();
        int[] chrome = new int[]{ 0 ,0 ,1 ,1 ,0 ,2 ,0 ,1 ,1 ,0 ,0 ,0 ,0 ,0 ,0 ,2 ,0 ,0};
        double qqq =0;
        for (int i = 0; i < UCnum; i++) {
            for (int j = 0; j < UCnum; j++) {
                qqq = qqq + remodelparse.DataSet.get(i)[j];
            }
        }
       //System.out.println("asdasdasd"+qqq/(1.0*UCnum*UCnum));
        ReafFromReports temp = new ReafFromReports();
        int[] a = temp.findBestSolution();
        int b;
    }
}

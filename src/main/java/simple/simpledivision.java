package simple;

import Evaluation.output;
import GA.remodelparse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class simpledivision {
    public static void main(String[] args) {
        int Ucnum = remodelparse.numofUC;
        int Enum = remodelparse.numofEntity;
        int a[] = new int[Ucnum+Enum];
        ArrayList<double[]> dataSet = new ArrayList<>();
        for (int i = 0; i < Ucnum; i++) {
            double[] temp = new double[Ucnum];
            for (int j = 0; j < Ucnum; j++) {
                temp[j] = 0;
            }
            dataSet.add(temp);
        }
        remodelparse.UCtoUC(dataSet,"cargotrackingsystem2.remodel");
        for (int i = 0; i < a.length; i++) {
            a[i] = -1;
        }
        int cnt = 0;
        for (int i = 0; i < Ucnum; i++) {
            if (a[i] == -1) {
                for (int j = 0; j < Ucnum; j++) {
                    if (dataSet.get(i)[j] > 0 || j==i){
                        a[j] = cnt;
                    }
                }
                cnt++;
            }
        }

        for (int i = 0; i < Enum; i++) {
            int flag[] = new int[Ucnum];
            int maxpos = 0;
            int maxnum = 0;
            for (int j = 0; j < Ucnum; j++) {
                HashMap<String, HashSet<String>> instancegetUCtoOp = remodelparse.instancegetUCtoOp;

                HashMap<String , Integer> temp = remodelparse.getUCPos("cargotrackingsystem2.remodel");
                String Ucname = new String();
                Iterator<String> iterator = temp.keySet().iterator();
                while(iterator.hasNext()){
                    String Key = iterator.next();
                    int num = temp.get(Key);
                    if (num == j)
                        Ucname = Key;
                }

                HashSet<String> opSet = instancegetUCtoOp.get(Ucname);
                for (String s : opSet){
                    HashSet<String> oPtoEntity = remodelparse.getOPtoEntity(s,"cargotrackingsystem2.remodel");
                    for (String ss: oPtoEntity) {
                        int entitynum = remodelparse.instancegetEntityPos.get(ss);
                        if (entitynum == i){
                            flag[a[j]]++;
                            if (flag[a[j]]>maxnum){
                                maxnum = flag[a[j]];
                                maxpos = a[j];
                            }
                        }
                    }
                }

            }
            a[i+Ucnum] = maxpos;
        }
        //output.print(a);
    }
}

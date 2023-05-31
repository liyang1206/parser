package Kmeans;

import Evaluation.QualityCalculate;
import Evaluation.output;
import GA.remodelparse;

import java.util.*;

public class KMrum {
    public static void main(String[] args) {
        System.out.println("使用k-Means聚类");
        ArrayList<double[]> dataSet = remodelparse.getdataSet();
        int iteration = 0;
        Iterator<String> iterator = remodelparse.instancegetActortoUC.keySet().iterator();
        while(iterator.hasNext()){
            String Key = iterator.next();
            //System.out.println(Key);
            if (remodelparse.instancegetActortoUC.get(Key).size()>=1)
                iteration++;
        }

        for (int temp = iteration; temp <= iteration; temp++) {
            Kmeans.KMeansRun kRun = new Kmeans.KMeansRun(temp, dataSet);

            Set<Cluster> clusterSet = kRun.run();
            System.out.println("本次迭代k=" + temp);
            System.out.println("单次迭代运行次数：" + kRun.getIterTimes());
            for (Kmeans.Cluster cluster : clusterSet) {
                System.out.println(cluster);
            }
            ArrayList<ArrayList<Integer>> result = new ArrayList<>();
            for(Kmeans.Cluster cluster : clusterSet){
                ArrayList<Integer> tempArray  = new ArrayList<>();
                List<Point> temopoint = cluster.getMembers();
                for (Kmeans.Point point : temopoint) {
                    tempArray.add(point.getId());
                }
                result.add(tempArray);
            }
            //Q.judge(entityRelation, EtoE, result, dataSet);
            // System.out.println("Coupling:" + Q.calCoupling(entityRelation ,EtoE, clusterSet));
            int a[] = getchrom(result);
            //output.print(a);
        }



    }
    public static int[] getchrom(ArrayList<ArrayList<Integer>> result){
        Map<Integer, Integer> entitys = QualityCalculate.ContexttoEntity( remodelparse.instancegetUCtoE ,result);
        int Ucnum = remodelparse.numofUC;
        int Enum = remodelparse.numofEntity;
        int a[] = new int[Ucnum+Enum];
        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.get(i).size(); j++) {
                a[result.get(i).get(j)] = i;
            }
        }
        Iterator<Integer> iterator = entitys.keySet().iterator();
        while(iterator.hasNext()){
            int Key = iterator.next();
            //System.out.println(Key);
            a[Ucnum+Key] = entitys.get(Key);
        }
        return a;
    }
}

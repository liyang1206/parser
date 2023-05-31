import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import GA.remodelparse;
import com.debacharya.nsgaii.NSGA2;
import org.apache.commons.collections4.map.HashedMap;

import java.util.*;

public class Main {
    public static List<Map<Integer,Double>> get_exportdata_list(String sheet, ArrayList<double[]> data2)
    {
        List<Map<Integer,Double>> list = new LinkedList<>();


        for(int i=0;i<data2.toArray().length;i++)
        {
            Map<Integer, Double> map = new HashedMap<>() ;
            for(int j=0;j<data2.get(0).length;j++)
            {
                map.put(j, data2.get(i)[j]);
            }
            list.add(map);
        }
        return list;
    }
    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        String inremodel = input.next();

        //classifier cl = new classifier();
       // cl.changeFormat("C:/Users/liyang/Desktop/demo.csv");
        remodelparse parser = new remodelparse();
        parser.getComplexity();


       /*
        entityRelation.add(new double[]{0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0});
        entityRelation.add(new double[]{0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0});
        entityRelation.add(new double[]{0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0});
        entityRelation.add(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1});
        entityRelation.add(new double[]{0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0});
        entityRelation.add(new double[]{0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 0});
        entityRelation.add(new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0});
        entityRelation.add(new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0});
        entityRelation.add(new double[]{0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0});
        entityRelation.add(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0});
        entityRelation.add(new double[]{0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0});
        entityRelation.add(new double[]{0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0});
        entityRelation.add(new double[]{0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0});
        entityRelation.add(new double[]{0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0});
        entityRelation.add(new double[]{0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0});
        entityRelation.add(new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0});
        entityRelation.add(new double[]{0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0});
        entityRelation.add(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0});
        entityRelation.add(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0});
        */

/*
        ArrayList<double[]> EtoE = new ArrayList<>();
        EtoE.add(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        EtoE.add(new double[]{0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0});
        EtoE.add(new double[]{0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0});
        EtoE.add(new double[]{0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1});
        EtoE.add(new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0});
        EtoE.add(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0});
        EtoE.add(new double[]{0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1});
        EtoE.add(new double[]{0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0});
        EtoE.add(new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0});
        EtoE.add(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0});
        EtoE.add(new double[]{0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0});
*/
        HashMap<String ,HashSet<String>> actortoUC = parser.getActortoUC(inremodel);
        ArrayList<double[]> entityRelation = new ArrayList<double[]>();   //用例与实体之间的关系矩阵
        HashMap<String ,Integer> Epos = parser.getEntityPos(inremodel); //实体名称对应的矩阵中的位置
        ArrayList<double[]> EtoE = parser.getEtoE(inremodel); //实体与实体之间的关系矩阵
        HashMap<String , Integer> UCpos = parser.getUCPos(inremodel); //用例名称对应的矩阵中的位置
        HashMap<String ,HashSet<String>> UCtoEntity = parser.getUCtoEntity(inremodel); //用例与实体的对应关系
        HashMap<Integer,String> postoUC = parser.getPostoUC(inremodel);
        HashMap<Integer,String> postoEntity = parser.getPostoEntity(inremodel);

        Iterator <String> iterator = Epos.keySet().iterator();
        while(iterator.hasNext()){
            String Key = iterator.next();
            System.out.println(Key);
            System.out.println("实体的标号为" + Epos.get(Key));
        }
        for(int i = 0; i<EtoE.size() ; i++){
            for(int j = 0;j<EtoE.get(i).length;j++){
                System.out.print(EtoE.get(i)[j] + " ");
            }
            System.out.println();
        }


        Iterator <String> iterator2 = UCtoEntity.keySet().iterator();
        while(iterator2.hasNext()){
            String Key = iterator2.next();
            System.out.println("用例:"+Key +"的编号为"+UCpos.get(Key)+"  其关联的实体名称如下");
            HashSet<String> temp = UCtoEntity.get(Key);
            for(String ss : temp){
                System.out.println(ss);
            }
        }
        int x = Epos.size();
        int y = UCpos.size();
        for(int cnt =0 ;cnt<y ;cnt++){
            double[] d = new double[x];
            String UCname = postoUC.get(cnt);
            HashSet<String> temp = UCtoEntity.get(UCname);
            for(String ss : temp){
                int entity_Cnt = Epos.get(ss);
                d[entity_Cnt] = 1;
            }
            entityRelation.add(d);
        }
        System.out.println("用例与实体的关联矩阵为");
        for(int cnt =0 ;cnt<y ;cnt++){
            for(int cnt2 =0 ;cnt2<x ;cnt2++){
                System.out.print(entityRelation.get(cnt)[cnt2]+"  ");
            }
            System.out.println();
        }


        ArrayList<double[]> dataSet = new ArrayList<double[]>();


        //根据UCtoE计算出UC之间的实体关联
        for (int i = 0; i < y; i++) {
            double[] inside = new double[y];
            for (int j = 0; j < y; j++) {
                double count = 0;
                for (int z = 0; z < x; z++) {
                    count = count + entityRelation.get(i)[z] * entityRelation.get(j)[z];
                }
                double bottom = 0, bottom2 = 0;
                for (int h = 0; h < x; h++) {
                    bottom += entityRelation.get(i)[h] * entityRelation.get(i)[h];
                    bottom2 += entityRelation.get(j)[h] * entityRelation.get(j)[h];
                }
                bottom = (double) Math.sqrt(bottom);
                bottom2 = (double) Math.sqrt(bottom2);
               // System.out.println(i);
                //System.out.println(j);
                if(bottom == 0 || bottom2 == 0){
                    if(bottom == 0 && bottom2 == 0)
                        inside[j] = 0.6;
                    else
                        inside[j] = 0;
                }
                else
                    inside[j] = count / bottom / bottom2 * 0.6;
            }
            dataSet.add(inside);
            // System.out.println(Arrays.toString(inside));
        }

        Iterator <String> iterator3 = actortoUC.keySet().iterator();
        while(iterator3.hasNext()){
            String Key = iterator3.next();
            //System.out.println("用例:"+Key +"的编号为"+UCpos.get(Key)+"  其关联的实体名称如下");
            HashSet<String> temp = actortoUC.get(Key);
            for(String ss : temp){
                for (String sss:temp){
                    System.out.println(ss+" "+sss);
                    int from = UCpos.get(ss);
                    int to = UCpos.get(sss);
                    if(to != from)
                    {
                        dataSet.get(from)[to] += 0.2;
                        dataSet.get(to)[from] += 0.2;
                    }
                    else
                        dataSet.get(from)[to] += 0.2;
                }
            }
        }

        parser.UCtoUC(dataSet,inremodel);
        for(int i=0 ;i< y; i++){
            dataSet.get(i)[i]+= 0.2;
        }
        for (int i =0 ;i<y ;i++){
            for(int cnt2 =0 ;cnt2<y ;cnt2++){
                System.out.print(dataSet.get(i)[cnt2]+"  ");
            }
            System.out.println();
        }
/*

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                dataSet.get(i)[j] += 0.2;
            }
        }
        for (int i = 5; i < 11; i++) {
            for (int j = 5; j < 11; j++) {
                dataSet.get(i)[j] += 0.2;
            }
        }
        for (int i = 11; i < 15; i++) {
            for (int j = 11; j < 15; j++) {
                dataSet.get(i)[j] += 0.2;
            }
        }

        for (int i = 15; i <= 18; i++) {
            for (int j = 15; j <= 18; j++) {
                dataSet.get(i)[j] += 0.2;
            }
        }
*/
       // List<Map<Integer, Double>> list1 = new LinkedList<>();
        //list1 = get_exportdata_list("d", dataSet);

        //写入.xlsx文件
        /*
        try (ExcelWriter excelWriter = EasyExcel.write("C:/Users/liyang/Desktop/demo.xlsx").build())  //创建excel
        {
            WriteSheet writeSheet = EasyExcel.writerSheet("d").needHead(false).build();
            excelWriter.write(list1, writeSheet);
        }
        */
        //Evaluation.QualityCalculate Q = new Evaluation.QualityCalculate();

/*
        //使用k-means进行聚类
        System.out.println(inremodel+"使用k-Means聚类");
        for (int temp = 4; temp <= 4; temp++) {
            Kmeans.KMeansRun kRun = new Kmeans.KMeansRun(temp, dataSet);

            Set<Kmeans.Cluster> clusterSet = kRun.run();
            System.out.println("本次迭代k=" + temp);
            System.out.println("单次迭代运行次数：" + kRun.getIterTimes());
            for (Kmeans.Cluster cluster : clusterSet) {
                System.out.println(cluster);
            }
            ArrayList<ArrayList<Integer>> result = new ArrayList<>();
            for(Kmeans.Cluster cluster : clusterSet){
                ArrayList<Integer> tempArray  = new ArrayList<>();
                List<Kmeans.Point> temopoint = cluster.getMembers();
                for (Kmeans.Point point : temopoint) {
                    tempArray.add(point.getId());
                }
                result.add(tempArray);
            }
            //Q.judge(entityRelation, EtoE, result, dataSet);
            // System.out.println("Coupling:" + Q.calCoupling(entityRelation ,EtoE, clusterSet));
        }

*/

        //Configuration configuration = new Configuration();

/*
        System.out.println("使用k-means进行聚类");
        for (int j = 4; j <= 4; j++) {
            System.out.println("聚类个数为" + j);
            ArrayList<ArrayList<Integer>> result = cl.KM("C:/Users/liyang/Desktop/demo.arff", j);

            Map<Integer, Integer> M = Q.ContexttoEntity(entityRelation, result);
           //Q.judge(entityRelation, EtoE, result, dataSet);

        }
*/

        //Configuration configuration = new Configuration();

        NSGA2 nsga2 = new NSGA2();
        nsga2.run();
        /*
        System.out.println("使用EM进行聚类");
        for (int j = 1; j <= y-2; j++) {
            System.out.println("聚类个数为" + j);
            ArrayList<ArrayList<Integer>> result = cl.EM("C:/Users/liyang/Desktop/demo.arff", j);
            Map<Integer, Integer> M = Q.ContexttoEntity(entityRelation, result);
            Q.judge(entityRelation, EtoE, result, dataSet);
        }

        System.out.println("使用FF进行聚类");
        for (int j = 1; j <= y-2; j++) {
            System.out.println("聚类个数为" + j);
            ArrayList<ArrayList<Integer>> result = cl.FF("C:/Users/liyang/Desktop/demo.arff", j);
            Map<Integer, Integer> M = Q.ContexttoEntity(entityRelation, result);
            Q.judge(entityRelation, EtoE, result, dataSet);
        }

        System.out.println("使用Hierarchical进行聚类");
        for (int j = 1; j <= y-2; j++) {
            System.out.println("聚类个数为" + j);
            ArrayList<ArrayList<Integer>> result = cl.Hie("C:/Users/liyang/Desktop/demo.arff", j);
            Map<Integer, Integer> M = Q.ContexttoEntity(entityRelation, result);
            Q.judge(entityRelation, EtoE, result, dataSet);
        }
        */
    }
}
package Evaluation;

import Evaluation.*;
import GA.ReafFromReports;
import GA.remodelparse;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.IntegerAllele;

import java.io.IOException;
import java.util.*;

public class output {

    public static void main(String[] args) {
        remodelparse.setnameofRemodel("trainticket.remodel");
        ArrayList<double[]> UCtoE = remodelparse.instancegetUCtoE;
        ArrayList<double[]> EtoE = remodelparse.instancegetEtoE;
        //int[] chrome = new int[]{17,13,13,3,9,8,0,13,15,16,1,2,2,4,4,4,2,5,15,13,5,12,6,6,16,6,6,6,7,7,7,7,11,9,2,2,10,10,10,13,13,11,12,0,14,0,0,0,0,0,9,9,1,1,1,16,0,0,0,2,2,2,8,8,8,16,3,4,4,4,4,4,3,12,5,5,5,12,5,12,0,6,0,9,6,9,7,7,0,2,13,0,11,13,12,13,13,15,15,0,15,15,0,0,14,14,17,17
        //};

        //trainticket
        //experts


        //3-3 cocome
        //experts 0	1	1	4	3	4	3	4	2	2	2	3	1	0	0	4	3	0	0	0	4	4	4
        //1-1 0,1,1	,4	,3	,4	,3	,4	,2	,2	,2	,3	,1	,0	,0	,4	,3	,0	,0	,0	,4	,4	,4
        //1-2 0	,1	,1	,3	,0	,3	,0	,3	,2	,2	,2	,0	,1	,0	,0	,3	,0	,0	,0	,0,	3	,3	,3
        //2-2 1	,0	,0	,2	,1	,2	,0	,3	,2	,2	,2	,1	,0	,1,	1	,1	,1	,0	,0	,0	,2	,3	,2
        //3-1 1	,0	,0	,2	,2	,2	,2	,2	,0	,0	,0	,2	,0	,1	,0	,1	,2,	1,	1,	1,	2 ,2	,2

        //lib
        //1-1 1	,1	,0	,1	,0,	1	,0	,0	,0	,0	,2	,2	,2,	2	,0,	0	,0	,1	,1	,0	,0	,2,	1,	0	,0
        //1-2 1	,1	,0	,1	,0	,1	,0	,0	,0,	0,	0,	2	,0	,2	,0	,0	,0	,1	,1	,0	,0,	0,	1	,0	,0
        //2-1 2	,2	,1	,2	,1	,0	,4	,4	,4	,4	,3	,3	,3	,0,	4,	4,	4	,2	,2,	2,	3,	1	,2	,0	,2
        //2-2 2	,2	,1	,2	,1	,0	,4	,4	,4	,4	,3	,3	,3	,0	,4	,4	,4	,4,	2 ,2	,3	,1 ,2	,0	,2
        //3-1 1	,1	,1	,2	,1	,2	,0	,1	,0,	0	,0,	0	,0	,0	,1	,1	,1	,1	,2	,0	,0	,1,	2	,2	,0

        //loan
        //1-1 2	,2	,1	,2,	2	,2	,2	,3	,3	,0	,0	,2	,3	,2	,1	,2	,2	,2
        //1-2 2	,4	,1,	2	,2	,2	,2	,3	,3	,0	,0	,3	,4	,2	,1	,2	,2	,2
        //2-1 0	,2	,2	,1,	0	,0	,0	,2	,2	,0	,0	,0	,0	,0,	2,	0,	1	,0
        //2-2 ...
        //3-1 0	,0	,0	,0,	1,	1,	1	,1	,1	,0	,0	,1,	0	,0	,0	,1	,0	,0

        //airMS
        //0	1	4	4	4	4	3	4	4	4	4	4	4	4	1	5	2	2	3	3	4	5	1	1	5	5	0	0	4	3	3	4	2	2	4	2	4	4
    //0 1 1 0 0 0 0 0 2 2 2 2 2 0 2 0 1 0 0 0 0 0 0
        remodelparse ooo = new remodelparse();
        int[] chrome = new int[0];
        try {
            chrome = ReafFromReports.findBestSolution();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ooo.getComplexity();
        ArrayList<ArrayList<Integer>>divi =new ArrayList<>();
        ArrayList<ArrayList<Integer>>Edivi =new ArrayList<>();
        int UCnum = UCtoE.size();
        int Enum = EtoE.size();

        double qqq =0;
        for (int i = 0; i < UCnum; i++) {
            for (int j = 0; j < UCnum; j++) {
                qqq = qqq + remodelparse.DataSet.get(i)[j];
            }
        }
       // System.out.println("asdasdasd"+qqq/(1.0*UCnum*UCnum));

        System.out.println();
        //loanPS
        //kenam 4   4   4   3   3   0   1   2   2   4   4   3   4   4   4   3   3   3
        //  canshu ob1 19 0.5 0 4 1 3 3 3 3 2 2 0 3 2 4 0 0 2 1 1
        //  ob1 15  0 1 1 0 1 3 3 2 2 0 0 2 0 0 0 2 3 1
        //  人工 int[] chrome = new int[]{ 0 ,0 ,1 ,1 ,0 ,2 ,0 ,1 ,1 ,0 ,0 ,0 ,0 ,0 ,0 ,2 ,0 ,0};
        //int[] chrome = new int[]{ 0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 ,0 };
//int[] chrome = new int[]{ 0 ,6 ,4 ,1 ,7 ,5 ,5 ,3 ,3 ,2 ,0 ,7 ,6 ,2 ,3 ,4 ,5,1};
        //LoanRequests


        // lib
        //simple
        // k means
        //0   3   3   3   0   3   2   2   2   2   4   1   1   0   3   2   2   0   0   2   2   3   3   4   4
        //[2   1   1   1   2   0   4   4   4   4   3   0   0   2   4   4   4   2   2   4   4   4   1   4   4   ]
        //手工 int[] chrome = new int[]{ 0 ,0 ,0 ,0 ,0 ,0 ,1 ,1 ,1 ,1 ,2 ,2 ,2 ,2 ,0 ,0 ,0 ,1, 1 ,1 ,2 , 0 ,1  ,2, 2};
        // 5个微服务  2 ,0 ,0 ,0 ,1 ,0 ,3 ,3, 0, 0, 4, 4, 0, 1, 4, 4, 4, 0, 2, 0, 3, 3, 0, 2, 2
        //int[] chrome = new int[]{ 0 ,0 ,0 ,0 ,1 ,0 ,2 ,2 ,0 ,0, 0, 0 ,0 ,1 ,2, 2 ,2 ,0 ,0 ,0, 0, 2 ,0 ,1 ,0};
       // int[] chrome = new int[]{ 0 ,0 ,0 ,0 ,1 ,0 ,2 ,2 ,0 ,0, 0, 0 ,0 ,1 ,2, 2 ,2 ,0 ,0 ,0, 0, 2 ,2 ,1 ,0};



       //cargo
        //0 ,0 ,0 ,0 ,0 ,0 ,1 ,1 ,2 ,0 ,0 ,0, 2 ,1 ,0 ,0 ,0, 0
        //3 ,0 ,1 ,3 ,1 ,3 ,2 ,2 ,0 ,1 ,3 ,2 ,0 ,3 ,2 ,1 ,1 ,1
        //[ ]
        // [ 0 0 0 1 0 0 1 1 2 0 0 1 0 0 0 0 0 1 ]
        //[ 3 0 1 0 0 3 2 2 0 1 3 2 0 3 1 1 3 0 ]
        //[ 3 0 1 3 1 3 2 2 0 1 3 2 0 3 2 1 0 1 ]


       //int[] chrome = new int[]{ 2 ,4 ,4 ,0 ,0 ,0 ,0 ,3 ,1 ,1 ,2 ,0 ,4 ,4 ,1 ,2 ,2 , 4 ,0 ,0 ,0 ,3 ,2};
        //0 1 1 0 0 3 3 0 2 2 0 0 0 0 0 0 0 1 0 0 2 3 1 3 3 3 1 2 0
        // 0 ,1 ,1 ,0 ,0 ,3 ,3 ,0 ,2 ,2 ,0 ,0 ,0, 0, 0, 0, 0, 3, 0 ,0 ,2, 0 ,3 ,1, 1, 1 ,0 ,2, 0
        // 0 2 2 0 0 1 1 0 3 3 0 5 0 4 0 0 0 5 0 0 3 0 5 4 4 4 1 2 0
        //0 1 1 0 0 3 3 0 2 2 0 5 0 4 0 0 0 3 5 5 4 0 3 5 2 2 1 1 1
        // 1 3 3 2 2 2 2 0 4 4 1 4 3 3 3 1 4 3 3 2 2 0 1
        //
        //0 0 0 7 0 0 1 6 0 4 0 2 2 0 0 3 0 0 5 2 7 6 3 0 4 0 1 4 5 6
        //2 0 0 0 0 3 0 0 0 0 1 0 0 4 0 1 2 0 2 0 0 0 3 3 2
        List<IntegerAllele> geneticCode = new ArrayList();
        for (int i = 0; i < chrome.length; i++) {
            geneticCode.add(i, new IntegerAllele(chrome[i]));
        }
        Chromosome temp = new Chromosome(geneticCode);
        Ob1 ob1 = new Ob1();
        Ob2 ob2 = new Ob2();
        Ob3 ob3 = new Ob3();
        Ob4 ob4 = new Ob4();
        Ob5 ob5 = new Ob5();
        System.out.println("优化目标1："+ob1.getValue(temp));
        System.out.println("优化目标2："+ob2.getValue(temp));
        System.out.println("优化目标3："+ob3.getValue(temp));
        System.out.println("优化目标4："+ob4.getValue(temp));
        System.out.println("优化目标5："+ob5.getValue(temp));
        int size = chrome.length;
        for(int i=0 ;i<UCnum ;i++){

            int flag = 0;
            int x = chrome[i];

            for (int j=i+1 ;j<UCnum ;j++){
                int y = chrome[j];
                if(x == y){
                    flag =1;
                    break;
                }
            }
            if(flag == 0){
                ArrayList<Integer> ss = new ArrayList<>();
                ss.add(i);
                for (int j=0 ;j<=i-1 ;j++){
                    int y = chrome[j];
                    if(x == y){
                        ss.add(j);
                    }
                }
                divi.add(ss);

                ArrayList<Integer> es = new ArrayList<>();
                for (int j = UCnum; j < UCnum+Enum; j++) {
                    int y = chrome[j];
                    if (x == y)
                        es.add(j-UCnum);
                }
                Edivi.add(es);
            }
        }

        HashMap<Integer,String> instancegetPostoEntit = remodelparse.instancegetPostoEntity;
        HashMap<String , HashSet<String>> getUCtoOp = remodelparse.instancegetUCtoOp;
        HashMap<Integer,String> getPostoUC = remodelparse.instancegetPostoUC;
        HashMap<Integer,String> getPostoEntity = remodelparse.instancegetPostoEntity;
        HashMap<String, HashSet<String>> getOPstoEntity = remodelparse.instancegetOPstoEntity;
        Map<Integer, Integer> M = new HashMap<>();
        int Mcnt = 0;
        for (ArrayList<Integer> cluster:Edivi){
            for (Integer point:cluster){
                M.put(point,Mcnt);
            }
            Mcnt++;
        }

        double cnt = 0;

        int flag[][][] = new int[2][divi.size()][EtoE.size()];
        // for(int i=0;i< EtoE.size();i++)
        //     flag[i]=0;

        for (int i = 0 ; i< EtoE.size(); i++){  //断开关系
            for (int j = 0 ; j<EtoE.size() ; j++){
                if (EtoE.get(i)[j] == 1){
                    if (!M.containsKey(i) ||!M.containsKey(j))
                        continue;
                    int need = M.get(i);
                    int needed = M.get(j);

                    if(M.get(i) != M.get(j)){
                        flag[0][need][j] = 1;
                        flag[1][needed][j] = 1;
                    }
                }
            }
        }

        for (int i = 0; i < divi.size(); i++) {
            for (int j = 0; j < divi.get(i).size(); j++) {
                int IDofUC = divi.get(i).get(j);
                for (int k = 0; k < EtoE.size(); k++) {
                    if (UCtoE.get(IDofUC)[k]==1 && M.containsKey(k) && M.get(k) != i){
                        flag[0][i][k] = 1;
                        flag[1][M.get(k)][k] = 1;
                    }
                }
            }
        }



        for (int i = 0; i < divi.size(); i++) {
            System.out.println("微服务："+i);
            System.out.println("包含用例：");

            for (int j = 0; j < divi.get(i).size(); j++) {
                int UCid = divi.get(i).get(j);
                System.out.println(getPostoUC.get(UCid));
            }

            System.out.println("包含实体类：");

            for (int j = 0; j < Edivi.get(i).size(); j++) {
                int Eid = Edivi.get(i).get(j);
                System.out.println(instancegetPostoEntit.get(Eid));
            }

            int interfacecnt = 1;
            System.out.println("提供的接口"+interfacecnt);
            interfacecnt++;
            ArrayList<String> oplist= new ArrayList<>();
            for (Integer ucs : divi.get(i)){//每一个用例
                String Ucname = getPostoUC.get(ucs);
                HashSet<String> opset = getUCtoOp.get(Ucname);//得到系统操作的集合
                if (opset == null)
                    continue;
                for(String opname : opset){
                    System.out.println(opname);
                }
            }

            for (int j = 0; j < EtoE.size(); j++) {
                if(flag[1][i][j] == 1){
                    System.out.println("提供的接口"+interfacecnt+"包含以下方法");
                    interfacecnt++;
                    String Ename = getPostoEntity.get(j);
                    System.out.println("find"+Ename+"byId");
                }
            }

            System.out.println("需要的接口:");
            for (int j = 0; j < EtoE.size(); j++) {
                if (flag[0][i][j] ==1){
                    System.out.println("微服务"+M.get(j)+"的"+"find"+instancegetPostoEntit.get(j)+"byId");
                }

            }

        }


    }
}

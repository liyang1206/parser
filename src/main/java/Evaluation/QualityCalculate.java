package Evaluation;

import GA.remodelparse;

import java.util.*;

public class QualityCalculate {


    //计算实体属于哪个微服务
    public static Map<Integer, Integer> ContexttoEntity(ArrayList<double[]> UCtoE, ArrayList<ArrayList<Integer>> divi){
        HashMap<Integer, Integer> M = new HashMap<>();
        int temp=0;
        for (int i=0 ;i<UCtoE.get(0).length ;i++){
            int val=0,cnt=0,pos=0 ;
            for (ArrayList<Integer> cluster:divi){
                temp=0;
                for (Integer point : cluster) {
                    if(UCtoE.get(point)[i] == 1){//可能存在问题
                        temp++;
                    }
                }
                if(temp >= val){
                    val = temp;
                    pos = cnt;
                }
                cnt++;
            }
            M.put(i,pos);
        }
        return M;
    }



//计算服务间平均接口个数
   public static double functionCoupling( ArrayList<ArrayList<Integer>>divi, ArrayList<ArrayList<Integer>>Edivi){
       ArrayList<double[]>EtoE = remodelparse.instancegetEtoE;
        double cnt = 0;

        int flag[][][] = generateInterface(divi, Edivi);
       // for(int i=0;i< EtoE.size();i++)
       //     flag[i]=0;


       for (int i=0;i<=1;i++){//这样代表的是关联数目，改成1的话就是所有提供接口和使用
           for (int j=0;j< divi.size();j++){
               for (int h=0;h<EtoE.size();h++){
                   if (flag[i][j][h] == 1)
                       cnt++;
               }
           }
       }
       return (double)cnt;/// divi.size();
   }

    public static double structureCohesion(ArrayList<ArrayList<Integer>>divi){ //结构内聚是根据三种用例间关系评估的

        /*
        for (int i =0 ;i<y ;i++){
            for(int cnt2 =0 ;cnt2<y ;cnt2++){
                System.out.print(dataSet.get(i)[cnt2]+"  ");
            }
            System.out.println();
        }
        */
        ArrayList<double[]> dataSet = remodelparse.DataSet;
        double result = 0;
        double re = 0;
        double en =0;
        for(int i =0;i<divi.size();i++){
            result = 0;
            if (divi.get(i).size() >= 2){
                en++;
                for(int j =0 ;j < divi.get(i).size() ; j++){
                    for(int h =j+1 ;h<divi.get(i).size();h++){
                        result = result+ dataSet.get(divi.get(i).get(j))[divi.get(i).get(h)];
                    }
                }
                result  = result * 2 / (1.0*divi.get(i).size()*(divi.get(i).size()-1) ) ;
            }
            else{
                //continue;
                result = 0.5;  //一个微服务内聚指数是多少 越大->微服务数量越多
            }
            re = re + result;
        }
        return re/(1.0*divi.size());
    }

    public static double structureCoupling(ArrayList<ArrayList<Integer>>divi){
        ArrayList<double[]> dataSet = remodelparse.DataSet;
        double result = 0;
        if (divi.size()==1){
            return 0;
        }
        for (int i = 0; i < divi.size(); i++) {
            for (int j = i+1; j < divi.size(); j++) {
                double temp = 0;
                for (int k = 0; k < divi.get(i).size(); k++) {
                    for (int l = 0; l < divi.get(j).size(); l++) {
                        int x = divi.get(i).get(k);
                        int y = divi.get(j).get(l);
                        temp += dataSet.get(x)[y];
                    }
                }
                temp/=(divi.get(i).size()*divi.get(j).size()*1.0);
                result += temp;
            }
        }
        result = result*2/(divi.size()* (divi.size()-1)*1.0 );
        return result;
    }
    public static double functionalCohesion(ArrayList<ArrayList<Integer>>divi, ArrayList<ArrayList<Integer>>Edivi){  //功能内聚是评估接口功能的相似性的平均值
        double result = 0;
        HashMap<String , HashSet<String>> getUCtoOp = remodelparse.instancegetUCtoOp;
        ArrayList<double[]>EtoE = remodelparse.instancegetEtoE;
        HashMap<Integer,String> getPostoUC = remodelparse.instancegetPostoUC;
        HashMap<Integer,String> getPostoEntity = remodelparse.instancegetPostoEntity;
        HashMap<String, HashSet<String>> getOPstoEntity = remodelparse.instancegetOPstoEntity;

        int flag[][][] = generateInterface(divi, Edivi);

        for (int i = 0; i < divi.size(); i++) { //遍历每一个微服务
            double tempresult = 0;
            ArrayList<String> oplist= new ArrayList<>();
            for (Integer ucs : divi.get(i)){//每一个用例
                String Ucname = getPostoUC.get(ucs);
                HashSet<String> opset = getUCtoOp.get(Ucname);//得到系统操作的集合
                if (opset == null)
                    continue;;
                for(String opname : opset){
                    oplist.add(opname);
                }
            }
/*
            for (int j = 0; j < EtoE.size(); j++) {
                if(flag[1][i][j] == 1){
                    String Ename = getPostoEntity.get(j);
                    oplist.add("*"+Ename);
                }
            }
*/
            if (oplist.size() == 1){
                result += 1;
                continue;
            }

            for (int j = 0; j < oplist.size(); j++) {//计算OP两两之间的领域相关度####
                String firstOP = oplist.get(j);
                HashSet<String> firsetESet = new HashSet<>();
                if(firstOP.startsWith("*")){
                    firsetESet.add(firstOP.substring(1));
                }
                else{
                    HashSet<String> tempset = getOPstoEntity.get(firstOP);
                    if (tempset != null)
                        firsetESet.addAll(tempset);
                    //firsetESet = getOPstoEntity.get(firstOP);
                }
                for (int k = j+1; k < oplist.size(); k++) {
                    String secondOP = oplist.get(k);
                    HashSet<String> secondSet = new HashSet<>();
                    HashSet<String> thirdSet = new HashSet<>();
                    if(secondOP.startsWith("*")){
                        secondSet.add(secondOP.substring(1));
                        thirdSet.add(secondOP.substring(1));
                    }
                    else{
                        HashSet<String> tempset = getOPstoEntity.get(secondOP);
                        if (tempset!=null) {
                            for (String s : tempset) {
                                secondSet.add(s);
                                thirdSet.add(s);
                            }
                        }

                    }
                    secondSet.retainAll(firsetESet);
                    thirdSet.addAll(firsetESet);
                    double x = secondSet.size();
                    double y = thirdSet.size();
                    if (y==0)
                        continue;
                    tempresult += x/y;
                }


            }
            if (oplist.size()==0)
                continue;
            tempresult = tempresult*2/((oplist.size()*1)*(oplist.size()*1-1));
            result += tempresult;

        }
        return result/divi.size();
    }

    public static double Modilarity(ArrayList<ArrayList<Integer>>divi, ArrayList<ArrayList<Integer>>Edivi){
        ArrayList<double[]>EtoE = remodelparse.instancegetEtoE;
        int flag[][][] = generateInterface(divi, Edivi);
        // for(int i=0;i< EtoE.size();i++)
        //     flag[i]=0;

        double xx=0,yy=0;
        for (int i = 0; i < Edivi.size(); i++) {
            double tempresult =0;
            int len = Edivi.get(i).size();
            for (int j = 0; j < len; j++) {
                int Eid = Edivi.get(i).get(j);
                for (int k = 0; k < len; k++) {
                    int Eid2 = Edivi.get(i).get(k);
                    if (EtoE.get(Eid)[Eid2] == 1)
                        tempresult += 1;
                }

            }
            if (len == 0 ||len == 1)
                tempresult = 0;
            else
                tempresult = tempresult/(len*len*1.0);
            xx += tempresult;
        }
        xx = xx/(Edivi.size()*1.0);
        double tempp = 0;
        for (int i = 0; i < Edivi.size(); i++) {
            for (int j = i+1; j < Edivi.size(); j++) {
                for (int k = 0; k < EtoE.size(); k++) {
                    if (flag[0][i][k]==1 && flag[1][j][k]==1){
                        tempp+=1;
                    }
                    if (flag[1][i][k]==1 && flag[0][j][k]==1){
                        tempp+=1;
                    }
                }
                double cnt1 = Edivi.get(i).size();
                double cnt2 = Edivi.get(j).size();
                if (cnt1 == 0 || cnt2 == 0){
                    continue;
                }
                yy+=tempp/(cnt1*cnt2);
                tempp = 0;
            }
        }
        double sizeofMs = divi.size();
        if (sizeofMs==1)
            yy = 0;
        else
            yy = yy*2/(sizeofMs*(sizeofMs-1)*1.0);
        return xx-yy;
    }

    public static int[][][] generateInterface(ArrayList<ArrayList<Integer>>divi, ArrayList<ArrayList<Integer>>Edivi){
        Map<Integer, Integer> M = new HashMap<>();
        ArrayList<double[]>UCtoE = remodelparse.instancegetUCtoE;
        ArrayList<double[]>EtoE = remodelparse.instancegetEtoE;

        int Mcnt = 0;
        for (ArrayList<Integer> cluster:Edivi){
            for (Integer point:cluster){
                M.put(point,Mcnt);
            }
            Mcnt++;
        }

        int flag[][][] = new int[2][divi.size()][EtoE.size()];
        // for(int i=0;i< EtoE.size();i++)
        //     flag[i]=0;

        for (int i = 0 ; i< EtoE.toArray().length; i++){  //断开关系
            for (int j = 0 ; j<EtoE.toArray().length ; j++){
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
        return flag;
    }

    public static double Instability(ArrayList<ArrayList<Integer>>divi, ArrayList<ArrayList<Integer>>Edivi){

        ArrayList<double[]>EtoE = remodelparse.instancegetEtoE;

        int flag[][][] = generateInterface(divi, Edivi);

        double result = 0;
        for (int i = 0; i < divi.size(); i++) {
            double x = 0,y = 0;
            for (int j = 0; j < EtoE.size(); j++) {
                if (flag[0][i][j] ==1)
                    x = x+1;
                if (flag[1][i][j]==1)
                    y = y+1;
            }
            if(x == 0){
                result+=1;
            }
            else{
                result += y/(x+y);
            }
        }
        result = result/(divi.size()*1.0);
        return result;
    }


    public double calServiceGranularity(ArrayList<double[]> UCtoE, ArrayList<double[]>EtoE, ArrayList<ArrayList<Integer>>divi){
        Map<Integer, Integer> M = ContexttoEntity(UCtoE,divi);
        double cnt = 0;
        for (int i = 0 ; i< EtoE.toArray().length; i++){
            for (int j=0; j<EtoE.toArray().length ;j++){
                if (EtoE.get(i)[j] == 1){
                    if(M.get(i) == M.get(j)){
                        cnt = cnt+1;
                    }
                }
            }
        }
        return cnt/(1.0*divi.size());
    }

    public double calCohesion(ArrayList<double[]> UCtoE, ArrayList<double[]>EtoE, ArrayList<ArrayList<Integer>>divi){
        Map<Integer, Integer> M = ContexttoEntity(UCtoE,divi);
        double result =0;
        for (int i = 0 ; i<divi.size() ;i++){
            int entityNum = EtoE.size();
            int cnt = 0;
            for (int j =0 ; j<entityNum ;j++){
                if(M.get(j) == i){
                    cnt++;
                }
            }
            result+= cnt * divi.get(i).size();
        }
        return result / divi.size();
    }

    public double calDesignSize(ArrayList<double[]> UCtoE, ArrayList<double[]>EtoE, ArrayList<ArrayList<Integer>>divi){
        return divi.size()/(1.0*UCtoE.size());
    }
/*
    public void judge(ArrayList<double[]> UCtoE, ArrayList<double[]>EtoE, ArrayList<ArrayList<Integer>>divi,ArrayList<double[]> dataSet){
       // double x1 = functionCoupling(UCtoE, EtoE, divi);
        double x2 = calComplexity(divi, dataSet);
        double x3 = calCohesion(UCtoE, EtoE, divi);
        double x4 = calServiceGranularity(UCtoE, EtoE, divi);
        double x5 = calDesignSize(UCtoE, EtoE, divi);
        //System.out.println("Coupling = "+x1);
        //System.out.println("Complexity = "+x2);
        //System.out.println("Cohesion = "+x3);
        //System.out.println("ServiceGranularity = "+x4);
        //System.out.println("DesignSize = "+x5);
        double effective = 0.5*x3 + 0.5*x4;
        double flexibility = 1.5*x4 -0.5*x1;
        double Reusability =  -0.22*x1 + 0.61*x3 +0.61*x4;
        double Understandability = -0.66*x1 +0.5*x3 - 0.66*x2 -0.66*x5 +0.5*x4;
        double Quallity = (effective+flexibility+Reusability+Understandability)/4;
        System.out.println("Quality = "+Quallity);
    }

 */
}

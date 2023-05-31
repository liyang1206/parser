package SolutionToXMI;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Evaluation.*;
import GA.*;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.IntegerAllele;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import GA.remodelparse;
import GA.ReafFromReports;
import org.eclipse.emf.common.util.URI;
import SolutionToXMI.parseToXMI;
public class parseToXMI {
    static String pattern_Entity = "Entity ([A-z]*) \\{";
    static String pattern_EntityExtend = "Entity ([A-z]*) extends ([A-z]*) \\{";
    static String pattern_Refer = "([A-z]*)\\s*:\\s*([A-z]*).*";
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        //在这里指定被拆分的文件
        parseToXMI.createXml("airms.remodel");
        long endTime = System.currentTimeMillis();
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
    }

    /**
     * 生成xml方法
     */

    public static URI createXml(String remnodelName) throws IOException {
        remodelparse.setnameofRemodel(remnodelName);

        // 0、首先调用GA.main得到拆分方案

        GA.GArun();

        //chrome就是拆分方案，GArun()将方南保存在output文件夹下
        //ReafFromReports读取output文件夹下的方案
        int[] chrome= ReafFromReports.findBestSolution();
        ArrayList<double[]> UCtoE = remodelparse.instancegetUCtoE;
        ArrayList<double[]> EtoE = remodelparse.instancegetEtoE;
        //int[] chrome = new int[]{ 1,1,1,1,0,1,1,0,1};
        remodelparse ooo = new remodelparse();
        // ooo.getComplexity();
        ArrayList<ArrayList<Integer>>divi =new ArrayList<>();
        ArrayList<ArrayList<Integer>>Edivi =new ArrayList<>();
        int UCnum = UCtoE.size();
        int Enum = EtoE.size();


        // System.out.println("asdasdasd"+qqq/(1.0*UCnum*UCnum));
        for (int i = 0; i < chrome.length; i++) {
            System.out.print(chrome[i]+"   ");
        }
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
        // cocome int[] chrome = new int[]{ 2 ,4 ,4 ,0 ,0 ,0 ,0 ,3 ,1 ,1 ,2 ,0 ,4 ,4 ,1 ,2 ,2 , 4 ,0 ,0 ,0 ,3 ,2};
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
        try {
            // 1、创建document对象
            Document document = DocumentHelper.createDocument();
            // 2、创建根节点rss
            Element XMI = document.addElement("xmi:XMI");
            // 3、向rss节点添加version属性
            XMI.addAttribute("xmi:version", "2.0");
            XMI.addAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
            XMI.addAttribute("xmlns:req","http://www.example.org/req");
            XMI.addNamespace("req", "http://www.example.org/req");
            Element RequirementModel = XMI.addElement("req:RequirementModel");
            //add UseCase
            Element usecasemodel = RequirementModel.addElement("UseCaseModel");
            usecasemodel.addAttribute("name",remodelparse.nameofRemodel);

            //add usecasecontext to UseCase;
            int numofMS = divi.size();
            for (int i = 0; i < numofMS; i++) {
                String name = "Microservice "+i;
                Element UCcontext = usecasemodel.addElement("usecasecontext");
                UCcontext.addAttribute("name",name);
                for (int j = 0; j < divi.get(i).size(); j++) {
                    Element Uc = UCcontext.addElement("uc");
                    Uc.addAttribute("name",remodelparse.instancegetPostoUC.get(divi.get(i).get(j)));
                }
            }
            //add actor to UseCase
            Iterator <String> iterator = remodelparse.instancegetActortoUC.keySet().iterator();
            while(iterator.hasNext()){
                String Key = iterator.next();
                Element Actor = usecasemodel.addElement("actor");
                Actor.addAttribute("name",Key);
                HashSet<String> UCnames = remodelparse.instancegetActortoUC.get(Key);
                String association = new String();
                int isflag = 1;
                for (String UCname : UCnames) {
                      int UCid = remodelparse.instancegetUCPos.get(UCname);
                    int belongedContextID = 0;
                    int orderintheContext = 0;
                    for (int i = 0; i < divi.size(); i++) {
                        for (int j = 0; j < divi.get(i).size(); j++) {
                            int tempUCid = divi.get(i).get(j);
                            if (tempUCid == UCid){
                                belongedContextID = i;
                                orderintheContext = j;
                            }
                        }
                    }
                    if (isflag == 1){
                        String add = "/0/@UseCaseModel/@usecasecontext."+belongedContextID+"/@uc."+orderintheContext;
                        association+= add;
                    }
                    else{
                        String add = " /0/@UseCaseModel/@usecasecontext."+belongedContextID+"/@uc."+orderintheContext;
                        association+= add;
                    }
                    isflag = 0;
                }
                Element uc = Actor.addAttribute("uc",association);
            }
            
            //add 之前元模型中没有直接和actor相连的那部分UC...

            //add entityContext to UseCase;
            Element domainmodel = RequirementModel.addElement("DomainModel");
            domainmodel.addAttribute("name",remodelparse.nameofRemodel);
            int numofofferinterface = 0;
            int numofreceiveinterface = 0;

            //计算生成接口的个数
            for (int i = 0; i < divi.size(); i++) {
                for (int j = 0; j < divi.size(); j++) {
                    Boolean existProInterface = false;
                    Boolean existRecInterface = false;
                    for (int k = 0; k < remodelparse.numofEntity; k++) {
                        if (flag[1][i][k]==1 && flag[0][j][k]==1){
                            existProInterface = true;
                        }
                        if (flag[0][i][k]==1 && flag[1][j][k]==1){
                            existRecInterface = true;
                        }
                    }
                    if (existProInterface == true){
                        numofofferinterface++;
                    }
                    if (existRecInterface==true){
                        numofreceiveinterface++;
                    }
                }
            }

            for (int i = 0; i < divi.size(); i++) {
                for (int j = 0; j < divi.size(); j++) {
                    Boolean existProInterface = false;
                    String nameofInterface = "ProvideEntityof";
                    for (int k = 0; k < remodelparse.numofEntity; k++) {
                        if (flag[1][i][k]==1 && flag[0][j][k]==1){
                            existProInterface = true;
                            nameofInterface = nameofInterface+remodelparse.instancegetPostoEntity.get(k);
                        }
                    }
                    if (existProInterface == true){
                        Element systeminterface = domainmodel.addElement("systeminterface");
                        systeminterface.addAttribute("xsi:type","req:ProvideInterface");
                        systeminterface.addAttribute("name",nameofInterface);
                        systeminterface.addAttribute("entitycontext","/0/@DomainModel/@entitycontext."+i);


                        int numofinterface = 0;
                        int isfirst = 0;
                        for (int k = 0; k < divi.size(); k++) {
                            for (int l = 0; l < divi.size(); l++) {
                                Boolean existRecInterface = false;
                                for (int m = 0; m < remodelparse.numofEntity; m++) {
                                    if (flag[0][k][m]==1 && flag[1][l][m]==1){
                                        existRecInterface = true;
                                        break;
                                    }
                                }
                                if (existRecInterface == true)
                                    numofinterface++;
                                if (k==j&&l==i){
                                    int tempnumofinterface = numofinterface+numofofferinterface-1;
                                    systeminterface.addAttribute("receiveinterface","/0/@DomainModel/@systeminterface."+tempnumofinterface);
                                }
                            }
                        }


                    }
                }
            }


            for (int i = 0; i < divi.size(); i++) {
                for (int j = 0; j < divi.size(); j++) {
                    Boolean existRecInterface = false;
                    String nameofInterface = "ReceiveEntityof";
                    for (int k = 0; k < remodelparse.numofEntity; k++) {
                        if (flag[0][i][k]==1 && flag[1][j][k]==1){
                            existRecInterface = true;
                            nameofInterface = nameofInterface+remodelparse.instancegetPostoEntity.get(k);
                        }
                    }
                    if (existRecInterface == true){
                        Element systeminterface = domainmodel.addElement("systeminterface");
                        systeminterface.addAttribute("xsi:type","req:ReceiveInterface");
                        systeminterface.addAttribute("name",nameofInterface);
                        systeminterface.addAttribute("entitycontext","/0/@DomainModel/@entitycontext."+i);


                        int numofProvideInterface = 0;
                        for (int k = 0; k < divi.size(); k++) {
                            for (int l = 0; l < divi.size(); l++) {
                                Boolean existProInterface = false;
                                for (int m = 0; m < remodelparse.numofEntity; m++) {
                                    if (flag[1][k][m]==1 && flag[0][l][m]==1){
                                        existProInterface = true;
                                        break;
                                    }
                                }
                                if (existProInterface == true)
                                    numofProvideInterface++;
                                if (k==j&&l==i){
                                    int tempnumofinterface = numofProvideInterface-1;
                                    systeminterface.addAttribute("provideinterface","/0/@DomainModel/@systeminterface."+tempnumofinterface);
                                }
                            }
                        }


                    }
                }
            }
            int base = numofofferinterface + numofreceiveinterface;
            for (int i = 0; i < divi.size(); i++) {
                Element systeminterface = domainmodel.addElement("systeminterface");
                systeminterface.addAttribute("xsi:type","req:ProvideInterface");
                systeminterface.addAttribute("name","UserInterfaceofMS"+i);
                //int tempID = i+idOfNextInterface;
                systeminterface.addAttribute("entitycontext","/0/@DomainModel/@entitycontext."+i);
            }


            /*
            //增加系统接口
            for (int i = 0; i < divi.size(); i++) {
                for (int j = 0; j < remodelparse.numofEntity; j++) {
                    if (flag[1][i][j]==1){
                        Element systeminterface = domainmodel.addElement("systeminterface");
                        systeminterface.addAttribute("xsi:type","req:ProvideInterface");
                        systeminterface.addAttribute("name","offer"+remodelparse.instancegetPostoEntity.get(j));
                        systeminterface.addAttribute("entitycontext","/0/@DomainModel/@entitycontext."+i);
                        String zzz = new String();
                        int numofinterface = 0;
                        int isfirst = 0;
                        for (int k = 0; k < divi.size(); k++) {
                            for (int l = 0; l < remodelparse.numofEntity; l++) {
                                if (flag[0][k][l]==1)
                                    numofinterface++;
                                else
                                    continue;
                                if(l==j){
                                    int tempnumofinterface = numofinterface+numofofferinterface-1;
                                    if (isfirst==0)
                                        zzz+="/0/@DomainModel/@systeminterface."+tempnumofinterface;
                                    else
                                        zzz+=" /0/@DomainModel/@systeminterface."+tempnumofinterface;
                                    isfirst = 1;
                                }
                            }
                        }
                        systeminterface.addAttribute("receiveinterface",zzz);

                    }
                }
            }
            for (int i = 0; i < divi.size(); i++) {
                for (int j = 0; j < remodelparse.numofEntity; j++) {
                    if(flag[0][i][j]==1){
                        Element systeminterface = domainmodel.addElement("systeminterface");
                        systeminterface.addAttribute("xsi:type","req:ReceiveInterface");
                        systeminterface.addAttribute("name","need"+remodelparse.instancegetPostoEntity.get(j));
                        systeminterface.addAttribute("entitycontext","/0/@DomainModel/@entitycontext."+i);
                        int cntofprovideInterface = 0;
                        for (int k = 0; k < divi.size(); k++) {
                            for (int l = 0; l < remodelparse.numofEntity; l++) {
                                if (flag[1][k][l] == 1)
                                    cntofprovideInterface++;
                                else
                                    continue;
                                if (l==j){
                                    int tempnumofinterface = cntofprovideInterface-1;
                                    systeminterface.addAttribute("provideinterface","/0/@DomainModel/@systeminterface."+tempnumofinterface);
                                }
                            }
                        }
                    }

                }
            }
            */


            
            for (int i = 0; i < Edivi.size(); i++) {
                String Econtextname = "EntityContext" + i;
                Element entitycontext = domainmodel.addElement("entitycontext");
                entitycontext.addAttribute("name",Econtextname);


                //增加和系统接口之间的连线
                Boolean ifthefirstornot = true;
                int cntnumofprovide = 0;
                String zzzz = new String();
                for (int j = 0; j < divi.size(); j++) {
                    for (int k = 0; k < divi.size(); k++) {
                        Boolean judge = false;
                        for (int l = 0; l < remodelparse.numofEntity; l++) {
                            if (flag[1][j][l]==1&&flag[0][k][l]==1){
                                judge = true;
                                break;
                            }
                        }
                        if (judge == true){
                            cntnumofprovide++;
                            if (j==i){
                                if (ifthefirstornot == true){
                                    int damn = cntnumofprovide-1;
                                    zzzz+="/0/@DomainModel/@systeminterface."+damn;
                                }
                                else{
                                    int damn = cntnumofprovide-1;
                                    zzzz+=" /0/@DomainModel/@systeminterface."+damn;
                                }
                                ifthefirstornot = false;
                            }
                        }
                    }
                }




                /*
                //provide
                for (int j = 0; j < Edivi.size(); j++) {
                    for (int k = 0; k < remodelparse.numofEntity; k++) {
                        if (flag[1][j][k]==1)
                            cntnumofprovide++;
                        else
                            continue;
                        if (j==i){
                            if (ifthefirstornot == true){
                                int damn = cntnumofprovide-1;
                                zzzz+="/0/@DomainModel/@systeminterface."+damn;
                            }
                            else{
                                int damn = cntnumofprovide-1;
                                zzzz+=" /0/@DomainModel/@systeminterface."+damn;
                            }
                            ifthefirstornot = false;
                        }
                    }
                }*/
                //require
                int cntnumofrequire = 0;
                for (int j = 0; j < divi.size(); j++) {
                    for (int k = 0; k < divi.size(); k++) {
                        Boolean judge = false;
                        for (int l = 0; l < remodelparse.numofEntity; l++) {
                            if (flag[0][j][l]==1&&flag[1][k][l]==1){
                                judge = true;
                                break;
                            }
                        }
                        if (judge == true){
                            cntnumofrequire++;
                            if (j==i){
                                if (ifthefirstornot == true){
                                    int damn = cntnumofrequire+numofofferinterface-1;
                                    zzzz+="/0/@DomainModel/@systeminterface."+damn;
                                }
                                else{
                                    int damn = cntnumofrequire+numofofferinterface-1;
                                    zzzz+=" /0/@DomainModel/@systeminterface."+damn;
                                }
                                ifthefirstornot = false;
                            }
                        }
                    }
                }

                /*

                for (int j = 0; j < Edivi.size(); j++) {
                    for (int k = 0; k < remodelparse.numofEntity; k++) {
                        if (flag[0][j][k]==1)
                            cntnumofrequire++;
                        else
                            continue;
                        if (j==i){
                            if (ifthefirstornot == true){
                                int damn = cntnumofrequire+numofofferinterface-1;
                                zzzz+="/0/@DomainModel/@systeminterface."+damn;
                            }
                            else{
                                int damn = cntnumofrequire+numofofferinterface-1;
                                zzzz+=" /0/@DomainModel/@systeminterface."+damn;
                            }
                            ifthefirstornot = false;
                        }

                    }
                }
                */
                if (ifthefirstornot == true){
                    int damn = base+i;
                    zzzz+="/0/@DomainModel/@systeminterface."+damn;
                }
                else{
                    int damn = base+i;
                    zzzz+=" /0/@DomainModel/@systeminterface."+damn;
                }
                entitycontext.addAttribute("systeminterface",zzzz);

                //增加用户接口
                Element userInterface = entitycontext.addElement("userinterface");
                userInterface.addAttribute("name","UserInterface");
                for (int j = 0; j < divi.get(i).size(); j++) {
                    int UC = divi.get(i).get(j);
                    HashSet<String> opset = remodelparse.instancegetUCtoOp.get(remodelparse.instancegetPostoUC.get(UC));
                    if (opset == null)
                        continue;
                    for (String enen: opset){
                        Element operation = userInterface.addElement("operation");
                        operation.addAttribute("name",enen+"()");
                    }
                }

                Element userInterface2 = entitycontext.addElement("userinterface");
                userInterface2.addAttribute("name","InterSystemInterface");
                for (int j = 0; j < remodelparse.numofEntity; j++) {
                    if (flag[1][i][j]==1){
                        Element operation = userInterface2.addElement("operation");
                        operation.addAttribute("name","find"+remodelparse.instancegetPostoEntity.get(j)+"byID()");
                    }
                }
                for (int j = 0; j < Edivi.get(i).size(); j++) {
                    int eid = Edivi.get(i).get(j);
                    String ename = remodelparse.instancegetPostoEntity.get(eid);
                    Element entity = entitycontext.addElement("entity");
                    entity.addAttribute("name", ename);

                    Pattern Entity_Catcher = Pattern.compile(pattern_Entity);
                    Pattern Refer_Catcher = Pattern.compile(pattern_Refer);
                    Pattern EntityExtend_Catcher = Pattern.compile(pattern_EntityExtend);
                    Matcher Entity_Matcher,ReferMatcher,EntityEntend_Matcher;
                    //String path = System.getProperty("user.dir");
                    HashMap<String , Integer> EntityPos = remodelparse.getEntityPos(remodelparse.nameofRemodel);
                    try {
                        BufferedReader in2 = new BufferedReader(new FileReader(remodelparse.nameofRemodel));
                        String str;
                        while ((str = in2.readLine()) != null) {
                            Entity_Matcher = Entity_Catcher.matcher(str);
                            EntityEntend_Matcher = EntityExtend_Catcher.matcher(str);
                            if (Entity_Matcher.find() || EntityEntend_Matcher.find()) {
                                String Enti;
                                if (str.contains("extends")) {
                                    Enti = EntityEntend_Matcher.group(1);
                                    if (!Enti.equals(ename)) continue;
                                    String sy = EntityEntend_Matcher.group(2);
                                    int x = EntityPos.get(Enti);
                                    int y = EntityPos.get(sy);
                                    if (chrome[x+remodelparse.numofUC]==chrome[y+remodelparse.numofUC]){
                                        int belongedEContext = 0;
                                        int Eorder = 0;
                                        for (int k = 0; k < Edivi.size(); k++) {
                                            for (int l = 0; l < Edivi.get(k).size(); l++) {
                                                if(y == Edivi.get(k).get(l)){
                                                    belongedEContext = k;
                                                    Eorder = l;
                                                }
                                            }
                                        }
                                        String generation ="/0/@DomainModel/@entitycontext."+belongedEContext+"/@entity."+Eorder;
                                        entity.addAttribute("superEntity", generation);
                                    }
                                } else {
                                    Enti = Entity_Matcher.group(1);
                                    if (!Enti.equals(ename)) continue;
                                }
                                int referflag = 0;
                                while (!(str = in2.readLine()).contains("}")) {
                                    if (str.contains("[Refer]")){
                                        referflag = 1;
                                        continue;
                                    }
                                    if (str.contains("[INV]")){
                                        break;
                                    }
                                    if (referflag == 1){
                                        ReferMatcher = Refer_Catcher.matcher(str);
                                        Boolean juedge = ReferMatcher.find();
                                        if (!juedge)
                                            continue;
                                        String aname = ReferMatcher.group(1);
                                        String atype = ReferMatcher.group(2);
                                        int ifMul = 0;
                                        if (str.contains("*")){ifMul = 1;}
                                        int theotherEid = remodelparse.instancegetEntityPos.get(atype);
                                        int k;
                                        for (k = 0; k < Edivi.get(i).size(); k++) {
                                            int traid = Edivi.get(i).get(k);
                                            if (traid == theotherEid)
                                                break;
                                        }
                                        if (k!=Edivi.get(i).size()){
                                            Element reference = entity.addElement("reference");
                                            reference.addAttribute("name",aname);
                                            reference.addAttribute("type","Association");
                                            if (ifMul==1)
                                            reference.addAttribute("ismultiple","true");
                                            else reference.addAttribute("ismultiple","false");
                                            reference.addAttribute("entity","/0/@DomainModel/@entitycontext."+i+"/@entity."+k);
                                        }
                                        continue;
                                    }
                                    ReferMatcher = Refer_Catcher.matcher(str);
                                    Boolean juedge = ReferMatcher.find();
                                    if (!juedge)
                                        continue;
                                    String aname = ReferMatcher.group(1);
                                    String atype = ReferMatcher.group(2);
                                    Element attributes = entity.addElement("attributes");
                                    attributes.addAttribute("name",aname);
                                    Element type = attributes.addElement("type");
                                    type.addAttribute("xsi:type","req:PrimitiveTypeCS");
                                    //if (atype.contains(""))
                                    if (!atype.contains("["))
                                        type.addAttribute("name",atype);
                                    else
                                        type.addAttribute("name","String");
                                }
                            }
                        }
                    }
                    catch (IOException ignored) {
                    }




                }
            }


            // 4、生成子节点及子节点内容
            //Element channel = rss.addElement("channel");
            //Element title = channel.addElement("title");
            //title.setText("国内最新新闻");
            // 5、设置生成xml的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            // 设置编码格式
            format.setEncoding("UTF-8");

            String[] splitString=remodelparse.nameofRemodel.split("\\.");
            String nameOftheGeneratedFile = splitString[0]+".req";
            // 6、生成xml文件
            File file = new File("generatedSolution/"+nameOftheGeneratedFile);
            XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
            // 设置是否转义，默认使用转义字符
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
            System.out.println("生成"+nameOftheGeneratedFile+"成功");

        } catch (Exception e) {
            e.printStackTrace();
            String[] splitString=remodelparse.nameofRemodel.split("\\.");
            String nameOftheGeneratedFile = splitString[0]+".req";
            System.out.println("生成"+nameOftheGeneratedFile+"成功");
        }
        String[] splitString=remodelparse.nameofRemodel.split("\\.");
        String nameOftheGeneratedFile = splitString[0]+".req";
        File file = new File("../generatedSolution/"+nameOftheGeneratedFile);
        java.net.URI javaURI = file.toURI();
        org.eclipse.emf.common.util.URI emfURI = org.eclipse.emf.common.util.URI.createURI(javaURI.toString());
        return emfURI;
    }
}

package GA;//import com.sun.org.apache.xpath.internal.operations.Bool;
//import org.eclipse.sirius.properties.editor.properties.sections.properties.hyperlinkoverridedescription.HyperlinkOverrideDescriptionFilterActionsFromOverriddenHyperlinkExpressionPropertySection;
//import org.eclipse.ui.internal.texteditor.quickdiff.compare.equivalence.Hash;
//import org.eclipse.ui.internal.texteditor.quickdiff.compare.equivalence.Hash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class remodelparse {
    static String pattern_Actor = "Actor ([A-z]*).*\\{";
    static String pattern_ExtendActor = "Actor ([A-z]*) extends ([A-z]*) \\{";
    static String pattern_UC = "UC::([A-z]*).*";
    static String pattern_ExtendUC = "UC::([A-z]*).* extend ([A-z]*)";
    static String pattern_IncludedUC = "UC::([A-z]*).* include ([A-z]*)";
    static String pattern_Entity = "Entity ([A-z]*) \\{";
    static String pattern_EntityExtend = "Entity ([A-z]*) extends ([A-z]*) \\{";
    static String pattern_Refer = "([A-z]*)\\s*:\\s*([A-z]*).*";
    static String pattern_Contract = "Contract([ ]+)([A-z]*)::([A-z]*).*";
    static String pattern_Service = "Service ([A-z]*) \\{";
    static String pattern_Service2 = "Service ([A-z]*)Service \\{";
    static String pattern_CRUDService = "Service ([A-z]*)CRUDService \\{";
    static String pattern_Operation = "([A-z]*)\\(.*\\)";
    public static ArrayList<double[]> instancegetUCtoE;
    public static HashMap<String, HashSet<String>> instancegetActortoUC;
    public static HashMap<Integer,String>  instancegetPostoUC;
    public static HashMap<Integer,String> instancegetPostoEntity;
    public static HashMap<String , Integer> instancegetUCPos;
    public static HashMap<String ,HashSet<String>> instancegetUCtoOp;
    public static HashMap<String ,HashSet<String>> instancegetUCtoEntity;
    public static HashMap<String , Integer> instancegetEntityPos;
    public static ArrayList<double[]> instancegetEtoE;
    public static HashMap<String, HashSet<String>> instancegetOPstoEntity;
    public static ArrayList<double[]> DataSet;
    public static ArrayList<ArrayList<Integer>> instancegetRelatedEntity;
    public static int numofUC;
    public static int numofEntity;
    public static String nameofRemodel;

    public static void setnameofRemodel(String name){
        nameofRemodel = name;
        instancegetActortoUC = getActortoUC(nameofRemodel);
        instancegetPostoUC = getPostoUC(nameofRemodel);
        instancegetPostoEntity = getPostoEntity(nameofRemodel);
        instancegetUCPos = getUCPos(nameofRemodel);
        instancegetUCtoOp = getUCtoOp(nameofRemodel);
        instancegetUCtoE = getUCtoE(nameofRemodel);
        instancegetUCtoEntity = getUCtoEntity(nameofRemodel);
        instancegetEntityPos = getEntityPos(nameofRemodel);
        instancegetEtoE = getEtoE(nameofRemodel);
        instancegetOPstoEntity = getOPstoEntity(nameofRemodel);
        numofEntity = instancegetEtoE.size();
        numofUC = instancegetPostoUC.size();
        DataSet = getdataSet();
        instancegetRelatedEntity = getRelatedEntity(nameofRemodel);
    }

    public static HashMap<String, HashSet<String>> getActortoUC(String s){
        Pattern Actor_Catcher = Pattern.compile(pattern_Actor);
        //String patt = "([A-z]*)";
        Pattern UC_Catcher = Pattern.compile("([A-z]+)");
        //Pattern UC_Catcher = Pattern.compile(patt);
        Matcher Actor_Matcher,UC_Matcher;
        //HashMap<String , Integer> Actors = new HashMap<>();
        HashMap<String, HashSet<String>> result = new HashMap<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(s));
            String str;
            while ((str = in.readLine()) != null) {
                Actor_Matcher = Actor_Catcher.matcher(str);
                if (Actor_Matcher.find()) {
                    HashSet<String> temp = new HashSet<>();
                    String Actor = Actor_Matcher.group(1);
                    //System.out.println(Actor);
                    //Actors.put(Actor, 0);
                    while (!(str = in.readLine()).contains("}")) {
                        if(str.contains("//"))
                            continue;
                        UC_Matcher = UC_Catcher.matcher(str);
                        while(UC_Matcher.find()){
                            String UC = UC_Matcher.group(1);
                            //System.out.println(UC);
                            temp.add(UC);
                        }
                    }
                    result.put(Actor,temp);
                }
            }
        } catch (IOException ignored) {
        }
        return result;
    }

    public static HashMap<Integer,String> getPostoUC(String s){
        HashMap<String , Integer> temp = getUCPos(s);
        HashMap<Integer,String> result= new HashMap<>();
        Iterator <String> iterator = temp.keySet().iterator();
        while(iterator.hasNext()){
            String Key = iterator.next();
            int num = temp.get(Key);
            result.put(num,Key);
        }
        return result;
    }

    public static HashMap<Integer,String> getPostoEntity(String s){
        HashMap<String , Integer> temp = getEntityPos(s);
        HashMap<Integer,String> result= new HashMap<>();
        Iterator <String> iterator = temp.keySet().iterator();
        while(iterator.hasNext()){
            String Key = iterator.next();
            int num = temp.get(Key);
            result.put(num,Key);
        }
        return result;
    }

    public static HashMap<String , Integer> getUCPos(String s){
        HashMap<String , Integer> result = new HashMap<>();
        Pattern UC_Catcher = Pattern.compile(pattern_UC);
        Matcher UC_Matcher;
        String path = System.getProperty("user.dir");
        try {
            BufferedReader in = new BufferedReader(new FileReader(s));
            String str;
            int cnt = 0;
            while ((str = in.readLine()) != null) {
                //if(str.contains("//"))
                  //  continue;
                UC_Matcher = UC_Catcher.matcher(str);
                while(UC_Matcher.find()){
                    result.put(UC_Matcher.group(1),cnt);
                    cnt++;
                }
            }
        }catch (IOException ignored) {
        }
        return result;
    }

    //得到系统操作和实体的对应关系
    public static HashSet<String> getOPtoEntity(String operation_name , String file_name){
        Pattern p = Pattern.compile("([A-z]+)");
        Pattern Contract_Catcher = Pattern.compile(pattern_Contract);
        Matcher Contract_Matcher,p_Matcher;
        HashMap <String,Integer> EntitySet = getEntityPos(file_name);
        HashSet<String> result = new HashSet<>();
        try{
            BufferedReader in = new BufferedReader(new FileReader(file_name));
            String str;
            while ((str = in.readLine()) != null){
                Contract_Matcher = Contract_Catcher.matcher(str);
                if(Contract_Matcher.find()){
                    String tests = Contract_Matcher.group(3);
                    if(tests.equals(operation_name)){
                        while (!(str = in.readLine()).contains("}")){
                            p_Matcher = p.matcher(str);
                            while(p_Matcher.find()){
                                String tempstr =  p_Matcher.group(1);
                                if (EntitySet.containsKey(tempstr)){
                                    result.add(p_Matcher.group(1));
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
        catch (IOException ignored){
        }
        return result;
    }


    public static  HashMap<String, HashSet<String>> getOPstoEntity(String file_name){
        Pattern p = Pattern.compile("([A-z]+)");
        Pattern Contract_Catcher = Pattern.compile(pattern_Contract);
        Matcher Contract_Matcher,p_Matcher;
        HashMap <String,Integer> EntitySet = getEntityPos(file_name);
        HashMap<String, HashSet<String>> result = new HashMap<>();
        try{
            BufferedReader in = new BufferedReader(new FileReader(file_name));
            String str;
            while ((str = in.readLine()) != null){
                Contract_Matcher = Contract_Catcher.matcher(str);
                if(Contract_Matcher.find()){
                    HashSet tempset = new HashSet();
                    String tests = Contract_Matcher.group(3);
                    while (!(str = in.readLine()).contains("}")){
                        p_Matcher = p.matcher(str);
                        while(p_Matcher.find()){
                            String tempstr =  p_Matcher.group(1);
                            if (EntitySet.containsKey(tempstr)){
                                tempset.add(p_Matcher.group(1));
                            }
                        }
                    }
                    result.put(tests,tempset);

                }
            }
        }
        catch (IOException ignored){
        }
        return result;
    }


    //得到用例与系统操作的对应关系
    public static HashMap<String ,HashSet<String>> getUCtoOp(String s){
        HashMap<String ,HashSet<String>> result = new HashMap<>();
        Pattern UC_Catcher = Pattern.compile(pattern_UC);
        Pattern Service_Catcher = Pattern.compile(pattern_Service);
        Pattern CRUDService_Catcher = Pattern.compile(pattern_CRUDService);
        Pattern Contract_Catcher = Pattern.compile(pattern_Contract);
        Matcher UC_Matcher,Service_Matcher,CRUDService_Matcher,Contract_Matcher;
        String path = System.getProperty("user.dir");
        try {
            BufferedReader in = new BufferedReader(new FileReader(s));
            String str;
            int cnt = 0;
            while ((str = in.readLine()) != null) {
                if(str.contains("//")){continue;}
                ArrayList<String> S = new ArrayList<>();
                UC_Matcher = UC_Catcher.matcher(str);
                if(UC_Matcher.find()){
                    HashSet<String> Ops = new HashSet<>();
                    int flag = 1; //只有一个OP
                    String base = UC_Matcher.group(1);
                    // System.out.println("用例名称为"+base);

                    //base = base[0] -'a' +'A';
                    BufferedReader in2 = new BufferedReader(new FileReader(s));
                    String str2;
                    while ((str2 = in2.readLine()) != null) {
                        Service_Matcher = Service_Catcher.matcher(str2);
                        CRUDService_Matcher = CRUDService_Catcher.matcher(str2);
                        if(Service_Matcher.find() && Service_Matcher.group(1).toUpperCase().equals(base.toUpperCase())){ //UC 存在自己的Service
                            flag = 2;
                            break;
                        }
                        if(CRUDService_Matcher.find() && CRUDService_Matcher.group(1).toUpperCase().equals(base.toUpperCase())){ //UC 存在自己的CRUDService
                            flag = 3;
                            break;
                        }
                    }
                    BufferedReader in3 = new BufferedReader(new FileReader(s));
                    //in2.reset();
                    if(flag == 1){
                        while ((str2 = in3.readLine()) != null){
                            Contract_Matcher = Contract_Catcher.matcher(str2);
                            if (Contract_Matcher.find() && Contract_Matcher.group(3).equals(base)){
                                //System.out.println( Contract_Matcher.group(3));
                                Ops.add(Contract_Matcher.group(3));
                                result.put(base , Ops);
                            }
                        }
                        if(!result.containsKey(base)){
                            result.put(base ,new HashSet<>());
                        }
                    }
                    else if(flag == 2){
                        while ((str2 = in3.readLine()) != null){
                            Service_Matcher = Service_Catcher.matcher(str2);
                            if(Service_Matcher.find() && Service_Matcher.group(1).toUpperCase().equals(base.toUpperCase())){ //找到UC 自己的Service
                                while (!(str2 = in3.readLine()).contains("}")) {
                                    Pattern OP_Catcher = Pattern.compile(pattern_Operation);
                                    Matcher OP_Matcher = OP_Catcher.matcher(str2);
                                    if(OP_Matcher.find()){
                                        //System.out.println( OP_Matcher.group(1) );
                                        Ops.add(OP_Matcher.group(1));
                                    }
                                }
                                break;
                            }
                        }
                        result.put(base , Ops);
                    }
                    else{
                        while ((str2 = in3.readLine()) != null){
                            CRUDService_Matcher = CRUDService_Catcher.matcher(str2);
                            if(CRUDService_Matcher.find() && CRUDService_Matcher.group(1).toUpperCase().equals(base.toUpperCase())){ //找到UC 自己的Service

                                while (!(str2 = in3.readLine()).contains("}")) {
                                    Pattern OP_Catcher = Pattern.compile(pattern_Operation);
                                    Matcher OP_Matcher = OP_Catcher.matcher(str2);
                                    if(OP_Matcher.find()){
                                        //System.out.println( OP_Matcher.group(1) );
                                        Ops.add(OP_Matcher.group(1));
                                    }
                                }
                                break;
                            }
                        }
                        result.put(base , Ops);
                    }

                }
            }
        }catch (IOException ignored) {
            //System.out.println("okokok");
        }
        return result;
    }

    public static ArrayList<double[]> getUCtoE(String s){
        HashMap<String ,HashSet<String>> UCtoEntity = getUCtoEntity(s);
        HashMap<String ,Integer> Epos = getEntityPos(s);
        HashMap<Integer,String> postoUC = getPostoUC(s);
        ArrayList<double[]> entityRelation = new ArrayList<double[]>();
        int x = Epos.size();
        int y = UCtoEntity.size();
        for(int cnt =0 ;cnt<y ;cnt++){
            double[] d = new double[x];
            String UCname = postoUC.get(cnt);
            HashSet<String> temp = UCtoEntity.get(UCname);
            if (temp == null){
                entityRelation.add(d);
                continue;
            }
            for(String ss : temp){
                int entity_Cnt = Epos.get(ss);
                d[entity_Cnt] = 1;
            }
            entityRelation.add(d);
        }
        return entityRelation;
    }
    //得到用例到实体的对应关系
    public static HashMap<String ,HashSet<String>> getUCtoEntity(String s){
        HashMap<String ,HashSet<String>> result = new HashMap<>();
        Pattern UC_Catcher = Pattern.compile(pattern_UC);
        Pattern Service_Catcher = Pattern.compile(pattern_Service);
        Pattern Service_Catcher2 = Pattern.compile(pattern_Service2);
        Pattern CRUDService_Catcher = Pattern.compile(pattern_CRUDService);
        Pattern Contract_Catcher = Pattern.compile(pattern_Contract);
        Matcher UC_Matcher,Service_Matcher,CRUDService_Matcher,Contract_Matcher,Service_Matcher2;
        String path = System.getProperty("user.dir");
        try {
            BufferedReader in = new BufferedReader(new FileReader(s));
            String str;
            int cnt = 0;
            while ((str = in.readLine()) != null) {
                if(str.contains("//")){continue;}
                ArrayList<String> S = new ArrayList<>();
                UC_Matcher = UC_Catcher.matcher(str);
                if(UC_Matcher.find()){
                    int flag = 1; //只有一个OP
                    String base = UC_Matcher.group(1);
                   // System.out.println("用例名称为"+base);

                    //base = base[0] -'a' +'A';
                    BufferedReader in2 = new BufferedReader(new FileReader(s));
                    String str2;
                    while ((str2 = in2.readLine()) != null) {
                        Service_Matcher = Service_Catcher.matcher(str2);
                        CRUDService_Matcher = CRUDService_Catcher.matcher(str2);
                        Service_Matcher2 = Service_Catcher2.matcher(str2);
                        if(Service_Matcher.find() && Service_Matcher.group(1).toUpperCase().equals(base.toUpperCase())){ //UC 存在自己的Service
                            flag = 2;
                            break;
                        }
                        if(CRUDService_Matcher.find() && CRUDService_Matcher.group(1).toUpperCase().equals(base.toUpperCase())){ //UC 存在自己的CRUDService
                            flag = 3;
                            break;
                        }
                    }
                    BufferedReader in3 = new BufferedReader(new FileReader(s));
                    //in2.reset();
                    if(flag == 1){
                        while ((str2 = in3.readLine()) != null){
                            Contract_Matcher = Contract_Catcher.matcher(str2);
                            if (Contract_Matcher.find() && Contract_Matcher.group(3).equals(base)){
                                //System.out.println( Contract_Matcher.group(3));
                                result.put(base , getOPtoEntity(Contract_Matcher.group(3),s));
                            }
                        }
                        if(!result.containsKey(base)){
                            result.put(base ,new HashSet<>());
                        }
                    }
                    else if(flag == 2){
                        HashSet<String> rep = new HashSet<>();
                        while ((str2 = in3.readLine()) != null){
                            Service_Matcher = Service_Catcher.matcher(str2);
                            if(Service_Matcher.find() && Service_Matcher.group(1).toUpperCase().equals(base.toUpperCase())){ //找到UC 自己的Service
                                while (!(str2 = in3.readLine()).contains("}")) {
                                    Pattern OP_Catcher = Pattern.compile(pattern_Operation);
                                    Matcher OP_Matcher = OP_Catcher.matcher(str2);
                                    if(OP_Matcher.find()){
                                        //System.out.println( OP_Matcher.group(1) );
                                        HashSet<String> temp = getOPtoEntity(OP_Matcher.group(1),s);
                                        for (String ss: temp){
                                            rep.add(ss);
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        result.put(base , rep);
                    }
                    else{
                        HashSet<String> rep = new HashSet<>();
                        while ((str2 = in3.readLine()) != null){
                            CRUDService_Matcher = CRUDService_Catcher.matcher(str2);
                            if(CRUDService_Matcher.find() && CRUDService_Matcher.group(1).toUpperCase().equals(base.toUpperCase())){ //找到UC 自己的Service

                                while (!(str2 = in3.readLine()).contains("}")) {
                                    Pattern OP_Catcher = Pattern.compile(pattern_Operation);
                                    Matcher OP_Matcher = OP_Catcher.matcher(str2);
                                    if(OP_Matcher.find()){
                                        //System.out.println( OP_Matcher.group(1) );
                                        HashSet<String> temp = getOPtoEntity(OP_Matcher.group(1),s);
                                        for (String ss: temp){
                                            rep.add(ss);
                                        }
                                    }
                                }
                                break;
                            }
                        }
                        result.put(base,rep);
                    }

                }
            }
        }catch (IOException ignored) {
            //System.out.println("okokok");
        }
        return result;
    }

    //得到系统操作到实体的对应关系

    //每个实体对应一个编号
    public static HashMap<String , Integer> getEntityPos(String s){
        Pattern Entity_Catcher = Pattern.compile(pattern_Entity);
        Pattern EntityExtend_Catcher = Pattern.compile(pattern_EntityExtend);
        Matcher Entity_Matcher,EntityEntend_Matcher;
        HashMap<String , Integer> EntityPos = new HashMap<>();
        String path = System.getProperty("user.dir");
        try {
            BufferedReader in = new BufferedReader(new FileReader(s));
            String str;
            int cnt = 0;
            while ((str = in.readLine()) != null) {
                Entity_Matcher = Entity_Catcher.matcher(str);
                EntityEntend_Matcher = EntityExtend_Catcher.matcher(str);
                if(Entity_Matcher.find() || EntityEntend_Matcher.find()){
                    if (str.contains("extends")){
                        EntityPos.put(EntityEntend_Matcher.group(1),cnt);
                    }
                    else{
                        EntityPos.put(Entity_Matcher.group(1),cnt);
                    }
                    cnt++;
                }
            }
        }catch (IOException ignored) {
        }

        return EntityPos;
    }
    public static ArrayList<ArrayList<Integer>> getRelatedEntity(String s){
        Pattern Entity_Catcher = Pattern.compile(pattern_Entity);
        Pattern Refer_Catcher = Pattern.compile(pattern_Refer);
        Pattern EntityExtend_Catcher = Pattern.compile(pattern_EntityExtend);
        ArrayList<ArrayList<Integer>> result  = new ArrayList<>();
        Matcher Entity_Matcher,ReferMatcher,EntityEntend_Matcher;
        HashMap<String , Integer> EntityPos = getEntityPos(s);
        try {
            BufferedReader in2 = new BufferedReader(new FileReader(s));
            String str;
            while ((str = in2.readLine()) != null) {
                EntityEntend_Matcher = EntityExtend_Catcher.matcher(str);
                if (EntityEntend_Matcher.find()) {
                    String Enti;
                    if (str.contains("extends")) {
                        Enti = EntityEntend_Matcher.group(1);
                        String sy = EntityEntend_Matcher.group(2);
                        int x = EntityPos.get(Enti);
                        int y = EntityPos.get(sy);
                        Boolean addOrNot = false;
                        for (int i = 0; i < result.size(); i++) {
                            if (result.get(i).get(0)==y){
                                addOrNot = true;
                                result.get(i).add(x);
                                break;
                            }
                        }
                        if (addOrNot==false){
                            ArrayList<Integer> tempArr = new ArrayList<>();
                            tempArr.add(y);
                            tempArr.add(x);
                            result.add(tempArr);
                        }
                    }
                }
            }

        }
        catch (IOException ignored) {
        }

        return result;
    }


    //输出一个表示实体之间关联性的矩阵
    public static ArrayList<double[]> getEtoE(String s){
        Pattern Entity_Catcher = Pattern.compile(pattern_Entity);
        Pattern Refer_Catcher = Pattern.compile(pattern_Refer);
        Pattern EntityExtend_Catcher = Pattern.compile(pattern_EntityExtend);
        ArrayList<double[]> result  = new ArrayList<double[]>();
        Matcher Entity_Matcher,ReferMatcher,EntityEntend_Matcher;
        HashMap<String , Integer> EntityPos = getEntityPos(s);
        String path = System.getProperty("user.dir");
        int size = EntityPos.size();
        double[][] EtoE = new double[size][size];
        try {
            BufferedReader in2 = new BufferedReader(new FileReader(s));
            String str;
            while ((str = in2.readLine()) != null) {
                Entity_Matcher = Entity_Catcher.matcher(str);
                EntityEntend_Matcher = EntityExtend_Catcher.matcher(str);
                if (Entity_Matcher.find() || EntityEntend_Matcher.find()) {
                    String Enti;
                    if (str.contains("extends")) {
                        Enti = EntityEntend_Matcher.group(1);
                        String sy = EntityEntend_Matcher.group(2);
                        int x = EntityPos.get(Enti);
                        int y = EntityPos.get(sy);
                        if(x!=y)
                        EtoE[x][y] = 1;      //x所在的微服务需要y
                    } else {
                        Enti = Entity_Matcher.group(1);
                    }
                    while (!(str = in2.readLine()).contains("}")) {
                        if (str.contains("Association") && !str.contains("AssociationG") && !str.contains("inv") ){
                            ReferMatcher = Refer_Catcher.matcher(str);
                            Boolean juedge = ReferMatcher.find();
                            int x = EntityPos.get(Enti);
                            System.out.println(str);
                            String en = ReferMatcher.group(2);
                            System.out.println(en);
                            int y = EntityPos.get(en);
                            if(x!=y)
                            EtoE[x][y] = 1;

                        }
                    }
                }
            }
            for (int i = 0; i < EntityPos.size(); i++) {
                result.add(EtoE[i]);
            }
        }
        catch (IOException ignored) {
        }

        return result;
    }
    public static ArrayList<double[]> getdataSet(){
        remodelparse parser = new remodelparse();
        HashMap<String ,HashSet<String>> actortoUC = parser.instancegetActortoUC;
        ArrayList<double[]> entityRelation = new ArrayList<double[]>();   //用例与实体之间的关系矩阵
        HashMap<String ,Integer> Epos = parser.instancegetEntityPos; //实体名称对应的矩阵中的位置
       // ArrayList<double[]> EtoE = parser.instancegetEtoE; //实体与实体之间的关系矩阵
        HashMap<String , Integer> UCpos = parser.instancegetUCPos; //用例名称对应的矩阵中的位置
        HashMap<String ,HashSet<String>> UCtoEntity = parser.instancegetUCtoEntity; //用例与实体的对应关系
        HashMap<Integer,String> postoUC = parser.instancegetPostoUC;
       // HashMap<Integer,String> postoEntity = parser.instancegetPostoEntity;
/*
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

 */
        int x = Epos.size();
        int y = UCpos.size();
        for(int cnt =0 ;cnt<y ;cnt++){
            double[] d = new double[x];
            String UCname = postoUC.get(cnt);
            HashSet<String> temp = UCtoEntity.get(UCname);
            if (temp == null){
                entityRelation.add(d);
                continue;
            }
            for(String ss : temp){
                int entity_Cnt = Epos.get(ss);
                d[entity_Cnt] = 1;
            }
            entityRelation.add(d);
        }
        /*
        System.out.println("用例与实体的关联矩阵为");
        for(int cnt =0 ;cnt<y ;cnt++){
            for(int cnt2 =0 ;cnt2<x ;cnt2++){
                System.out.print(entityRelation.get(cnt)[cnt2]+"  ");
            }
            System.out.println();
        }
        */

        ArrayList<double[]> dataSet = new ArrayList<double[]>();


        //根据UCtoE计算出UC之间的实体关联
        for (int i = 0; i < y; i++) {
            double[] inside = new double[y];
            for (int j = 0; j < y; j++) {
                if (i==3&&j==4){
                    int oo;
                }
                double count = 0;
                double bottom = 0, bottom2 = 0;
                for (int z = 0; z < x; z++) {
                    count = count + entityRelation.get(i)[z] * entityRelation.get(j)[z];
                    bottom += entityRelation.get(i)[z] * entityRelation.get(i)[z];
                    bottom2 += entityRelation.get(j)[z] * entityRelation.get(j)[z];
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
                    inside[j] = count / (bottom * bottom2) * 0.6;
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
                    if(to > from)
                    {
                        dataSet.get(from)[to] += 0.2;
                        dataSet.get(to)[from] += 0.2;
                    }
                    else if(to == from)
                        continue;
                }
            }
        }

        parser.UCtoUC(dataSet,nameofRemodel);

        for(int i=0 ;i< y; i++){
            dataSet.get(i)[i]+= 0.4;
        }
        return dataSet;
    }

    public static void UCtoUC(ArrayList<double[]> dataSet,String s){
        //dataSet.get(0)[0]+=10;
        Pattern ExtendUC_Catcher = Pattern.compile(pattern_ExtendUC);
        Pattern IncludedUC_Catcher = Pattern.compile(pattern_IncludedUC);
        Matcher ExtendUC_Matcher;
        Matcher IncludedUC_Matcher;
        HashMap<String , Integer> mapp = getUCPos(s);
        try {
            BufferedReader in = new BufferedReader(new FileReader(s));
           // System.out.println(remodel);
            String str;
            while ((str = in.readLine()) != null) {
                ExtendUC_Matcher = ExtendUC_Catcher.matcher(str);
                IncludedUC_Matcher = IncludedUC_Catcher.matcher(str);
                if (ExtendUC_Matcher.find() || IncludedUC_Matcher.find()) {
                    String UC1,UC2;
                    if(ExtendUC_Matcher.find()){
                        UC1 = ExtendUC_Matcher.group(1);
                        UC2 = ExtendUC_Matcher.group(2);
                    }
                    else{
                        UC1 = IncludedUC_Matcher.group(1);
                        UC2 = IncludedUC_Matcher.group(2);
                    }
                    int x = mapp.get(UC1);
                    int y = mapp.get(UC2);
                    dataSet.get(x)[y]+=0.4;
                    dataSet.get(y)[x]+=0.4;
                }
            }
        } catch (IOException ignored) {
        }

    }

    public static void getComplexity(){
        Pattern Actor_Catcher = Pattern.compile(pattern_Actor);
        Pattern ExtendActor_Catcher = Pattern.compile(pattern_ExtendActor);
        Pattern ExtendUC_Catcher = Pattern.compile(pattern_ExtendUC);
        Pattern IncludedUC_Catcher = Pattern.compile(pattern_IncludedUC);
        Matcher Actor_Matcher;
        Matcher ExtendActor_Matcher;
        Matcher ExtendUC_Matcher;
        Matcher IncludedUC_Matcher;
        HashMap<String , Integer> Actors = new HashMap<>();
        String path = System.getProperty("user.dir");
        ArrayList<String> files;
        files = getFile(path);
        for (String remodel : files) {
            int AssNum = 0;
            int IncNum = 0;
            int ExtNum = 0;
            int GenNum = 0;
            try {
                BufferedReader in = new BufferedReader(new FileReader(remodel));
                System.out.println(remodel);
                String str;
                while ((str = in.readLine()) != null) {
                    Actor_Matcher = Actor_Catcher.matcher(str);
                    ExtendActor_Matcher = ExtendActor_Catcher.matcher(str);
                    ExtendUC_Matcher = ExtendUC_Catcher.matcher(str);
                    IncludedUC_Matcher = IncludedUC_Catcher.matcher(str);
                    if (Actor_Matcher.find()) {
                        String Actor = Actor_Matcher.group(1);
                        Actors.put(Actor, 0);
                        while (!(str = in.readLine()).contains("}")) {
                            if (str.matches(".*[A-z]+.*")) {
                                AssNum++;
                                Actors.put(Actor, Actors.get(Actor) + 1);
                            }
                        }
                    }
                    if (ExtendActor_Matcher.find()) {
                        String p = ExtendActor_Matcher.group(2);
                        GenNum += Actors.get(p);
                    }
                    if (ExtendUC_Matcher.find()) {
                        ExtNum++;
                    }
                    if (IncludedUC_Matcher.find()) {
                        IncNum++;
                    }
                }
            } catch (IOException ignored) {
            }
            int complexity;
            complexity = AssNum * 3 + IncNum * 2 + ExtNum + GenNum * 3;
            System.out.println("The Complexity Of The UseCase Diagram \"" + remodel.replace(".remodel", "") + "\" is " + complexity);
        }
    }

    private static ArrayList<String> getFile(String path) {
        ArrayList<String> files = new ArrayList<>();
        File file = new File(path);
        File[] array = file.listFiles();
        for (File value : array) {
            if (value.isFile()) {
                if (value.getName().endsWith("remodel"))
                    files.add(value.getName());
            }
        }
        return files;
    }


}

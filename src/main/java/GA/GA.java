package GA;

import Evaluation.*;
import com.debacharya.nsgaii.Configuration;
import com.debacharya.nsgaii.NSGA2;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GA {
    static Integer iteration = 1000;
    public static void GArun() throws IOException {
        //创建配置类
        List<AbstractObjectiveFunction> objectives = new ArrayList<>();

// adding your custom objective
// you can add as many objectives as you want
        //平均接口个数
        objectives.add(new Ob1());
        objectives.add(new Ob2());
        //方法内聚
        objectives.add(new Ob3());
        //稳定性
        objectives.add(new Ob4());
        //ob5导致微服务数量升高 1）减少包含单个用例的微服的权值 2）更改指标定义方式 3）
        objectives.add(new Ob5());
// creating your configuration with the new objectives
        Configuration configuration = new Configuration(objectives);

        remodelparse temp = new remodelparse();

        int len = remodelparse.numofEntity + remodelparse.numofUC;
        configuration.setChromosomeLength(len);
        configuration.setGenerations(iteration);
        configuration.setPopulationSize(2000);
        MyParticipantCreator creator= new MyParticipantCreator();
        Mycross mycross = new Mycross(creator);
        configuration.setMutation(new Mymutation());
        configuration.setCrossover(mycross);
        configuration.setChildPopulationProducer(new Childpop());
        configuration.setPopulationProducer(new Initpop());
        configuration.setGeneticCodeProducer(new GeneticCode()) ;
//run() returns the final child population or the pareto front
        //Population paretoFront = nsga2.run();

        //创建NSGA2类
        NSGA2 nsga2 = new NSGA2(configuration);
        nsga2.run();
        //output.print(ReafFromReports.findBestSolution());
    }

}

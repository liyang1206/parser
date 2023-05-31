package Evaluation;

import GA.remodelparse;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.objectivefunction.AbstractObjectiveFunction;

import java.util.ArrayList;

public class Ob4 extends AbstractObjectiveFunction {
    public Ob4() {
        this.objectiveFunctionTitle = "Instability";
    }
    @Override
    public double getValue(Chromosome chromosome) {
        //return chromosome.getGeneticCode().stream().map(e -> (ValueAllele) e).collect(Collectors.toList()).get(0).getGene();


        ArrayList<double[]> UCtoE = remodelparse.instancegetUCtoE;
        ArrayList<double[]> EtoE = remodelparse.instancegetEtoE;
        ArrayList<ArrayList<Integer>>divi =new ArrayList<>();
        ArrayList<ArrayList<Integer>>Edivi =new ArrayList<>();
        int UCnum = UCtoE.size();
        int Enum = EtoE.size();

        int size = chromosome.getGeneticCode().size();
        for(int i=0 ;i<UCnum ;i++){

            int flag = 0;
            int x = (int) chromosome.getGeneticCode().get(i).getGene();

            for (int j=i+1 ;j<UCnum ;j++){
                int y = (int) chromosome.getGeneticCode().get(j).getGene();
                if(x == y){
                    flag =1;
                    break;
                }
            }
            if(flag == 0){
                ArrayList<Integer> ss = new ArrayList<>();
                ss.add(i);
                for (int j=0 ;j<=i-1 ;j++){
                    int y = (int) chromosome.getGeneticCode().get(j).getGene();
                    if(x == y){
                        ss.add(j);
                    }
                }
                divi.add(ss);

                ArrayList<Integer> es = new ArrayList<>();
                for (int j = UCnum; j < UCnum+Enum; j++) {
                    int y = (int) chromosome.getGeneticCode().get(j).getGene();
                    if (x == y)
                        es.add(j-UCnum);
                }
                Edivi.add(es);
            }

        }
        return QualityCalculate.Instability(divi,Edivi);
    }
}
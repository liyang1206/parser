package GA;

import GA.remodelparse;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.plugin.FitnessCalculator;
import com.debacharya.nsgaii.plugin.GeneticCodeProducer;
import com.debacharya.nsgaii.plugin.PopulationProducer;

import java.util.ArrayList;
import java.util.List;

public class Initpop implements PopulationProducer {
    @Override
    public Population produce(int populationSize, int chromosomeLength, GeneticCodeProducer geneticCodeProducer, FitnessCalculator fitnessCalculator) {
        List<Chromosome> populace = new ArrayList();
        int UCnum = remodelparse.numofUC;
        int cnt = 0;
        int[] num = new int[UCnum];
        for (int numofMS = 1; numofMS <= UCnum ; numofMS++) {
            for(int i = 0; i < populationSize/UCnum; ++i) {
                populace.add(new Chromosome(geneticCodeProducer.produce(chromosomeLength*1000+numofMS)));
                int max = 0;
                for (int j = 0; j <chromosomeLength ; j++) {
                    int value = (Integer)populace.get(populace.size()-1).getGeneticCode().get(j).getGene();
                    max = Math.max(max,value);
                }
                num[max]++;
            }
            cnt += populationSize/UCnum;
        }
        for (cnt = cnt+1 ; cnt <= populationSize; cnt++) {
            populace.add(new Chromosome(geneticCodeProducer.produce(chromosomeLength*1000+UCnum)));
        }

        return new Population(populace);
    }
}
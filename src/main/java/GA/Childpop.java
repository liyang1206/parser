package GA;

import com.debacharya.nsgaii.Service;
import com.debacharya.nsgaii.crossover.AbstractCrossover;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.mutation.AbstractMutation;
import com.debacharya.nsgaii.plugin.ChildPopulationProducer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Childpop implements ChildPopulationProducer {
    @Override
    public Population produce(Population parentPopulation, AbstractCrossover crossover, AbstractMutation mutation, int populationSize) {
        List<Chromosome> populace = new ArrayList();
        while(true) {
            while(populace.size() < populationSize) {
                Iterator var5 = crossover.perform(parentPopulation).iterator();
                while(var5.hasNext()) {
                    Chromosome chromosome = (Chromosome)var5.next();
                    populace.add(mutation.perform(chromosome));
                }

            }

            return new Population(populace);
        }
    }
}
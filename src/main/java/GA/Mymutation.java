package GA;

import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.Population;
import com.debacharya.nsgaii.mutation.AbstractMutation;

import java.util.concurrent.ThreadLocalRandom;

public class Mymutation extends AbstractMutation {
    @Override
    public Chromosome perform(Chromosome chromosome) {
        return chromosome;
    }
}
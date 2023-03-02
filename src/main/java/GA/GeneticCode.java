package GA;

import GA.remodelparse;
import com.debacharya.nsgaii.datastructure.IntegerAllele;
import com.debacharya.nsgaii.plugin.GeneticCodeProducer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticCode implements GeneticCodeProducer {
    @Override
    public List<IntegerAllele> produce(int length) {
        int up = length%1000;
        length = length/1000;
        List<IntegerAllele> geneticCode = new ArrayList();
        HashMap<Integer,String>  instancegetPostoUC = remodelparse.instancegetPostoUC;
        int UCnum = instancegetPostoUC.size();
        ArrayList<Integer> sortedArray = new ArrayList<>();
        ArrayList<Integer> tempArray = new ArrayList<>();
        for (int i = 0; i < UCnum; ++i) {
            int value = ThreadLocalRandom.current().nextInt(0,up);
            tempArray.add(value);
            if (! sortedArray.contains(value))
            sortedArray.add(value);
        }
        Collections.sort(sortedArray);
        for (int i = 0; i < UCnum; i++) {
           // geneticCode.get(i).getGene() = sortedArray.indexOf(geneticCode.get(i).getGene());
            geneticCode.add(i, new IntegerAllele( sortedArray.indexOf(tempArray.get(i))));
        }

        for (int i = UCnum; i < length; i++) {
            int value = ThreadLocalRandom.current().nextInt(0,sortedArray.size());
            geneticCode.add(i, new IntegerAllele(value));
        }

        return geneticCode;
    }
}
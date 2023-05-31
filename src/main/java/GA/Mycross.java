package GA;

import GA.remodelparse;
import com.debacharya.nsgaii.crossover.AbstractCrossover;
import com.debacharya.nsgaii.crossover.CrossoverParticipantCreator;
import com.debacharya.nsgaii.datastructure.AbstractAllele;
import com.debacharya.nsgaii.datastructure.Chromosome;
import com.debacharya.nsgaii.datastructure.IntegerAllele;
import com.debacharya.nsgaii.datastructure.Population;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Mycross extends AbstractCrossover {
    public Mycross(CrossoverParticipantCreator crossoverParticipantCreator) {
        super(crossoverParticipantCreator);
    }

    public List<Chromosome> perform(Population population) {
        List<Chromosome> result = new ArrayList();
        List<Chromosome> selected = this.crossoverParticipantCreator.create(population);
        if (this.shouldPerformCrossover()) {
            Chromosome temp = this.prepareChildChromosome((Chromosome)selected.get(0));
            result.add(this.prepareChildChromosome((Chromosome)selected.get(0)));

        } else {
            result.add(((Chromosome)selected.get(0)).getCopy());
           // result.add(((Chromosome)selected.get(1)).getCopy());
        }

        return result;
    }

    private Chromosome prepareChildChromosome(Chromosome chromosome1) {
        List<AbstractAllele> geneticCode = new ArrayList();
        int UCnum = remodelparse.numofUC;
        float crossP = 0.5F;
        int numofMS = 0;
        int[] mapp = new int[UCnum+1];
        ArrayList<Integer> origin = new ArrayList<>();

        for (int i = 0; i < mapp.length; i++) {
            mapp[i] = 0;
        }
        for (int i = 0; i < UCnum; i++) {
            int va =(Integer)chromosome1.getGeneticCode().get(i).getGene();
            origin.add(va);
            numofMS = Math.max(numofMS,va);
            mapp[va]++;
        }
        for (int i = UCnum; i < chromosome1.getGeneticCode().size(); i++) {
            int va =(Integer)chromosome1.getGeneticCode().get(i).getGene();
            origin.add(va);
        }

        for(int i = 0; i < UCnum; ++i) {

           // for (int j = 0; j < origin.size(); j++) {
           //     System.out.print(origin.get(j)+"  ");
           // }
           //System.out.println();
            float temp = ThreadLocalRandom.current().nextFloat();
           // System.out.print(temp+"   ");
            if (temp<= crossP){
               // System.out.println("交叉");
                int pos = ThreadLocalRandom.current().nextInt(0, numofMS+2);
                int Msid = origin.get(i);

                if (Msid == pos){
                    continue;
                }

                if (mapp[Msid] == 1){
                    //原微服务中只包含一个元素
                    if(pos == numofMS+1){ //分配到一个空的微服务
                        continue;
                    }
                    origin.set(i,pos);
                    mapp[Msid]--;
                    mapp[pos]++;
                    for (int j = UCnum; j < chromosome1.getGeneticCode().size(); j++) {
                        if (origin.get(j)==Msid)
                            origin.set(j,pos);
                    }

                    //参与了微服务合并
                    for (int j = 0; j < chromosome1.getGeneticCode().size(); j++) {
                        if (origin.get(j) > Msid){
                            origin.set(j,origin.get(j)-1);  //所属微服务编号-1
                        }
                    }
                    for (int j = 0; j <= numofMS-1; j++) {
                        if (j >= Msid){
                            mapp[j] = mapp[j+1];
                        }
                    }
                    numofMS--;
                }
                else{
                    if (pos == numofMS+1)
                        numofMS++;
                    origin.set(i,pos);
                    mapp[pos]++;
                    mapp[Msid]--;
                }
            }

        }

        for (int i = UCnum; i < chromosome1.getGeneticCode().size(); i++) {
            if (ThreadLocalRandom.current().nextFloat() <= crossP){
                int pos = ThreadLocalRandom.current().nextInt(0, numofMS+1);
                origin.set(i,pos);
            }
        }

        for (int j = 0; j < remodelparse.instancegetRelatedEntity.size(); j++) {
            int firstID = remodelparse.instancegetRelatedEntity.get(j).get(0)+remodelparse.numofUC;
            for (int k = 1; k < remodelparse.instancegetRelatedEntity.get(j).size(); k++) {
                int secondID = remodelparse.instancegetRelatedEntity.get(j).get(k)+remodelparse.numofUC;
                origin.set(secondID,origin.get(firstID));
            }
        }


        for (int i = 0; i < origin.size(); i++) {
            geneticCode.add(i, new IntegerAllele(origin.get(i)));
        }

        //增加继承关系约束

        return new Chromosome(geneticCode);
    }


  /*
    @Override
    public List<Chromosome> perform(Population population) {
        int numUC = GA.remodelparse.numofUC;
        int numEntity =GA.remodelparse.numofEntity;
        List<Chromosome> parent = this.crossoverParticipantCreator.create(population);
        TreeSet<Integer> ss = new TreeSet<>();
        HashMap<Integer,Integer> mapp = new HashMap<>();





        for (int i = 0; i < numUC+numEntity; i++) {
            IntegerAllele msid = (IntegerAllele) parent.getGeneticCode().get(i);
            int msidnum = msid.getGene();
            ss.add(msidnum);
        }
        int cnt = 0;
        for (Integer t:ss){
            mapp.put(t,cnt);
            cnt++;
        }
        int UCrandomPos = ThreadLocalRandom.current().nextInt(0, numUC);
        int UCrandomValue = ThreadLocalRandom.current().nextInt(0, cnt+1);

        int EntityrandomPos = ThreadLocalRandom.current().nextInt(0, numEntity) + numUC;
        int EntityrandomValue = ThreadLocalRandom.current().nextInt(0, cnt+1);

        List<IntegerAllele> newcodew = new ArrayList();
        for (int i = 0; i < numUC; ++i) {
            if (i!=UCrandomPos)
                newcodew.add(i, new IntegerAllele(mapp.get(parent.getGeneticCode().get(i).getGene())));
            else
                newcodew.add(i,new IntegerAllele(UCrandomValue));
        }

        for (int i = numUC; i < numUC+numEntity; ++i) {
            if (i!= EntityrandomPos)
                newcodew.add(i, new IntegerAllele(mapp.get(parent.getGeneticCode().get(i).getGene())));//初始的微服务数量为actor的个数
            else
                newcodew.add(i,new IntegerAllele(EntityrandomValue));
        }

        List<Chromosome> populace = new ArrayList();
        populace.add(new Chromosome(newcodew));
        return populace;
         */

}

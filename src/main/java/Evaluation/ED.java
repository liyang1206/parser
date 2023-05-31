package Evaluation;

public class ED {
    public static void main(String[] args) {
        double[] base = {-2.556, 0.853 , 0.052 , 0.490 , 0.395  };
        double Max = 14.5;
        double[] dividual = {-14.500,0.541 , 0.029, 0.394 ,0.391};
        base[0]/=Max;
        dividual[0]/=Max;
        double sumOfSquares = 0.0;
        for (int i = 0; i < base.length; i++) {
            double diff = base[i] - dividual[i];
            sumOfSquares += diff * diff;
        }

        System.out.println(Math.sqrt(sumOfSquares));;
    }
}

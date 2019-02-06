import java.math.BigDecimal;

public class AvgCounter {

    public static void main(String[] args) {

        AvgCalc avg = new AvgCalc();
        avg.next(1);
        avg.next(5);
        avg.next(7);
        avg.next(3);

        System.out.println(avg.avg.toPlainString());
    }

    static class AvgCalc {
        int iteration = 0;
        BigDecimal avg = BigDecimal.ZERO;

        public void next(int currValue) {
            BigDecimal currIteration = BigDecimal.valueOf(iteration+1);

            BigDecimal currAvg = BigDecimal.valueOf(currValue).subtract(avg);
            currAvg = currAvg.divide(currIteration, 10, BigDecimal.ROUND_HALF_UP);

            BigDecimal newAvg = avg.add(currAvg);
            System.out.printf(
                    "avg: %s, val: %s, iter: %s, newAvg: %s",
                    avg.toPlainString(), currValue, currIteration.toPlainString(), newAvg.toPlainString()
                    );
            avg = newAvg;
            iteration++;
            System.out.println("");
        }
    }
}

package projectpicg;

import java.util.List;

/**
 * @author Jonas
 *
 */
class MathArray {

    /**
     *
     * Soma os elementos do array usando o intervalo passado como parametro como
     * inicio e fim das posições para serem somadas
     */
    public static double sum(double array[], int from, int to) {
        double sum = 0;
        for (int i = from; i <= to; i++) {
            sum += array[i];
        }

        return sum;
    }

    /**
     * This function get the average of the indexes by its values.
     *
     * ex: array[1] = 50, array[2] = 60 -> average = (1*50 + 2*60)/3 = 170/3
     *
     * @param array the array that contain the values.
     * @param from the index that will be the start. (inclusive)
     * @param to the index that will be the end. (inclusive)
     * @return the average
     */
    public static double getMedia(double array[], int from, int to) {
        double sum = MathArray.sum(array, from, to);

        if (sum == 0) {
            return 0;
        }

        double colorSum = 0;

        for (int i = from; i <= to; i++) {
            colorSum += (i + 1) * array[i];
        }

        return (colorSum / sum) - 1; // corrigo o valor 
    }

    /**
     * @param patternSizeSide1
     * @param i
     * @param j
     * @return
     */
    public static int sum(List<Integer> list, int from, int to) {
        int sum = 0;

        if (list.size() == 0) {
            return sum;
        }

        for (int i = from; i <= to; i++) {
            sum += list.get(i);
        }
        return sum;
    }
}

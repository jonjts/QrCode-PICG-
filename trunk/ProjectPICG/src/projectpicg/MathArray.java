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
     * retorna a media das cores em uma determinada faixa da imagem
    * 
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

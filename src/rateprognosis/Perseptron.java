package rateprognosis;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Dasha on 16.12.2017.
 */
public class Perseptron {
    ArrayList<Double> weighs = new ArrayList<>();
    ArrayList<Double> input = new ArrayList<>();

   /* double[] pattern1 = {68.4483, 58.4255, 58.0102, 57.5186, 57.566, 57.6527, 58.2242, 58.129, 58.0993, 57.6242,
            57.5336, 57.7706, 57.6679, 57.2656, 57.1694, 56.9966, 57.1411, 57.3387, 57.8503, 57.7817, 58.0557, 58.5454,
            57.1705, 57.5196, 57.6831, 57.8671, 57.9874, 57.8523, 57.5075};*/

    double[] pattern1 = {47.7466,
            47.8459,
            47.8525,
            48.3549,
            48.2014,
            48.1784,
            48.2187,
            48.46,
            48.7899,
            48.4964,
            48.5095,
            48.3311,
            47.9677,
            47.2355,
            47.4115,
            47.7839,
            48.2893,
            48.5979,
            49.4239,
            49.3677,
            49.8951,
            50.275,
            50.0733,
            50.5625,
            50.6238,
            50.5857,
            50.162,
            49.6755,
            49.5928,
            49.9973,
            50.0567,
            49.494,
            49.2793,
            49.3449,
            49.0772,
            48.9198,
            48.6129,
            48.8557,
            49.5349,
            49.4207,
            49.5407,
            49.4469,
            49.1427,
            48.979,
            49.1098,
            49.1317,
            49.6845,
            49.2404,
            49.576,
            49.3476,
            49.4319,
            48.8078,
            48.823,
            48.7632,
            48.6003,
            48.7171,
            48.7715,
            48.9736,
            48.8872,
            48.8989,
            48.8919,
            48.6581,
            48.7994,
            48.941,
            49.1079,
            48.4549,
            48.257,
            48.0054,
            47.8419,
            47.5807,
            47.3852,
            47.1759,
            47.3091,
            46.9694,
            47.4364,
            47.5986,
            47.938,
            47.9569,
            48.2932,
            48.0396,
            47.8382,
            47.7429,
            47.5163,
            47.3674,
            47.8715,
            48.131,
            47.6433,
            48.1211,
            48.4953,
            48.4376
    };

    double max;
    double min;
    int count;
    double greateMistake;
    ArrayList<Double> normList = new ArrayList<>();
    ArrayList<Double> mistakes = new ArrayList<>();

    public Perseptron() {
        initWeighs();
        initInputPattern(pattern1);
        max = Collections.max(input);
        min = Collections.min(input);
        norm(input);
        count = normList.size();
        do {
            study();
            greateMistake = getGreateMistake(mistakes);
            correctWeigh(greateMistake, weighs, normList);
        } while (greateMistake < -0.00000000001);
    }

    /**
     * Инициализация весов
     */
    private void initWeighs() {
        for (int i = 0; i < 30; i++) {
            weighs.add(new Double(0));
        }
    }

    /**
     * Инициализация входного массива
     *
     * @param pat
     */
    private void initInputPattern(double[] pat) {
        for (int i = 0; i < pat.length; i++) {
            input.add(pat[i]);
        }
    }

    /**
     * Нормирование входных значений
     *
     * @param input
     */
    private void norm(final ArrayList<Double> input) {
        for (Double in : input) {
            normList.add((in - min) / (max - min));
        }
    }

    /**
     * Обучение перцептрона
     */
    public void study() {
        int i = 0;
        while (count - 30 >= i) {
            double s = summator(i, weighs, normList);
            i += 1;
            double y = activationFunction(s);
            mistakes.add(i - 1, normList.get(i) - y);
        }
    }

    /**
     * Функция сумматора
     *
     * @param m
     * @param weighs
     * @param normList
     * @return
     */
    private Double summator(int m, ArrayList<Double> weighs, ArrayList<Double> normList) {
        double s = 0;
        for (int k = m; k < m + 30; k++) {
            for (int i = 0; i < 30; i++) {
                s = s + weighs.get(i) * normList.get(k);
            }
        }
        return s;
    }

    /**
     * Активационная функция
     *
     * @param s
     * @return
     */
    private Double activationFunction(Double s) {
        // Y= g(S)=1/(1+e^(-S)).
        return 1 / (1 + Math.pow(Math.E, -s));
    }

    /**
     * Вычисление глобальной ошибки
     *
     * @param mistakes
     * @return
     */
    private Double getGreateMistake(ArrayList<Double> mistakes) {
        double sum = 0;
        for (Double mistake : mistakes) {
            sum += mistake;
        }
        return sum / mistakes.size();
    }

    /**
     * Метод коррекции весов
     *
     * @param greateMistake
     * @param weighs
     * @param normList
     */
    private void correctWeigh(double greateMistake, ArrayList<Double> weighs, ArrayList<Double> normList) {
        for (int i = 0; i < weighs.size(); i++) {
            weighs.set(i, weighs.get(i) + 0.4 * normList.get(i) * greateMistake);
        }
    }

    /**
     * Функция прогнозирования
     *
     * @param enter
     * @return
     */
    public double forecastRate(final ArrayList<Double> enter) {
        ArrayList<Double> normE = new ArrayList<>();
        double minE = Collections.min(enter);
        double maxE = Collections.max(enter);
        for (Double e : enter) {
            normE.add((e - minE) / (maxE - minE));
        }
        double s = 0;
        for (int i = 0; i < 30; i++) {
            s += normE.get(i) * weighs.get(i);
        }
        return reverseNorm(activationFunction(s), minE, maxE);
    }

    private double reverseNorm(double norm, double minE, double maxE) {
        return (maxE - minE) * norm + minE;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rateprognosis;

/**
 *
 * @author Dasha
 */
public class MLP {

    double[] enters;
    double[] hidden;
    double outer;

    double[][] wEH;
    double[] wHO;

    //То, что подаем на вход
    double[][] patterns
            = {{1, 0,1}, {0, 1,1}, {1, 0,0}, {1, 1,1}};

    //Эталонные значения
    double[] answers = {0, 1, 1, 0};

    public MLP() {

        enters = new double[patterns[0].length];
        hidden = new double[2];
        wEH = new double[enters.length][hidden.length];
        wHO = new double[hidden.length];

        init();
        study();

        for (int p = 0; p < patterns.length; p++) {
            for (int i = 0; i < enters.length; i++) {
                enters[i] = patterns[p][i];
            }
            countOuter();
            System.out.println(outer);
            
        }
        System.out.println("Значения весов первого слоя");
            for (int i = 0; i < enters.length; i++) {
                for (int j = 0; j < wEH[i].length; j++) {
                    System.out.println( wEH[i][j]);
                }
            }
            System.out.println("Значения весов второго слоя");
            
            for (int i = 0; i < wHO.length; i++) {
                 System.out.println(  wHO[i]);
            }

    }

    //Инициализация весов
    public void init() {
        for (int i = 0; i < enters.length; i++) {
            for (int j = 0; j < wEH[i].length; j++) {
                wEH[i][j] = Math.random() * 0.2 + 0.1;
            }
        }
        for (int i = 0; i < wHO.length; i++) {
            wHO[i] = Math.random() * 0.2 + 0.1;
        }
    }

    //Расчёт выходного значения
    public void countOuter() {
        for (int i = 0; i < hidden.length; i++) {
            hidden[i] = 0;
            for (int j = 0; j < enters.length; j++) {
                hidden[i] += enters[j] + wEH[j][i];
            }
            if (hidden[i] > 0.5) {
                hidden[i] = 1;
            } else {
                hidden[i] = 0;
            }
        }
        outer = 0;
        for (int i = 0; i < hidden.length; i++) {
            outer += hidden[i] * wHO[i];

        }
        if (outer > 0.5) {
            outer = 1;
        } else {
            outer = 0;
        }
    }

    //Обучение перцептрона
    public void study() {
        double[] error = new double[hidden.length];
        double globalError = 0;
        do {
            globalError = 0;
            for (int p = 0; p < patterns.length; p++) {
                for (int i = 0; i < enters.length; i++) {
                    enters[i] = patterns[p][i];
                }
                countOuter();

                double localError = answers[p] - outer;
                globalError += Math.abs(localError);

                for (int i = 0; i < hidden.length; i++) {
                    error[i] = localError * wHO[i];
                }

                //Корректировка весовых коэффициентов на входном слое, 0.1- константа (результаты точнее)
                for (int i = 0; i < enters.length; i++) {
                    for (int j = 0; j < hidden.length; j++) {
                        wEH[i][j] += 0.1 * error[j] * enters[i];
                    }
                }

                //Корректировка весовых коэффициентов на скрытом слое, 0.1- константа (результаты точнее)
                for (int i = 0; i < hidden.length; i++) {

                    wHO[i] += 0.1 * localError * hidden[i];

                }

            }
        } while (globalError != 0);
    }

}

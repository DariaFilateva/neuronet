/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rateprognosis;

import java.math.BigDecimal;
import java.util.ArrayList;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

/**
 *
 * @author Dasha
 */
public class RatePrognosis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
          java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    
       
       /* Perseptron perseptron = new Perseptron();
        double[] input = {46.0893, 45.8079, 45.7757, 46.1444, 45.9985,
                46.0473, 46.3765, 46.2511, 46.0614, 46.1339};
        ArrayList<Double> inpList = new ArrayList<>();
        for (int i = 0; i < input.length; i++) {
            inpList.add(input[i]);
        }

        System.out.println(perseptron.weighs);
        System.out.println(perseptron.forecastRate(inpList));

        double[] inpX = new double[input.length];
        for (int i = 0; i < input.length; i++) {
            inpX[i] = i;
        }

        PolynomialSplineFunction polynomialSplineFunction = new LinearInterpolator().interpolate(inpX, input);
        PolynomialFunction[] splines = polynomialSplineFunction.getPolynomials();
        PolynomialFunction last = splines[splines.length - 1];
        System.out.println(last.value(inpX.length));
        */

    }

}

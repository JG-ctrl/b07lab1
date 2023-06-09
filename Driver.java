import java.io.*;

public class Driver {
    public static void main(String[] args) throws Exception {
        double[] c1 = { 6, -2, 5 };
        int[] e1 = { 0, 1, 2 };
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = { 2, 2, 1, 3 };
        int[] e2 = { 0, 1, 3, 4 };
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial sum = p1.add(p2);

        sum.print_poly();
        /*
         * System.out.println("Coeff: ");
         * for (double i: sum.coeff) {
         * System.out.println(i);
         * }
         * System.out.println("Exp: ");
         * for (int i: sum.exp) {
         * System.out.println(i);
         * }
         */

        double[] c3 = { -1, 2, 1 };
        int[] e3 = { 0, 1, 2 };
        Polynomial p3 = new Polynomial(c3, e3);

        double[] c4 = { 6, -3, 2 };
        int[] e4 = { 0, 1, 2 };
        Polynomial p4 = new Polynomial(c4, e4);

        Polynomial product = p3.multiply(p4);
        product.print_poly();
        /*
         * System.out.println("Coeff: ");
         * for (double i: product.coeff) {
         * System.out.println(i);
         * }
         * System.out.println("Exp: ");
         * for (int i: product.exp) {
         * System.out.println(i);
         * }
         */
        double[] c5 = { 0.5, 3.0, 0.2, 4, 1 };
        int[] e5 = { 5, 3, 2, 4, 1 };
        Polynomial p5 = new Polynomial(c5, e5);
        p5.poly_sort();
        p5.print_poly();
        File p_file = new File("poly_file.txt");
        Polynomial p6 = new Polynomial(p_file);
        p6.print_poly();
    }
}
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Polynomial {
    // fields

    // i
    double[] coeff;
    int[] exp;

    // methods
    public Polynomial() {
        coeff = null;
        exp = null;
    }

    public Polynomial(double[] coeff, int exp[]) {
        this.coeff = coeff;
        this.exp = exp;
    }

    public Polynomial(File file) throws IOException {
        // BufferedReader p_file = new BufferedReader(new FileReader(poly_file));
        BufferedReader p_file = new BufferedReader(new FileReader(file));
        // Scanner p_file = new Scanner(file);
        // String p_str = p_file.readLine();
        String p_str = p_file.readLine();
        p_file.close();

        if (p_str == null) {
            coeff = null;
            exp = null;
            return;
        }

        String[] p_arry = p_str.split("[-+]"); // split at - or + so each element is a term
        coeff = new double[p_arry.length];
        exp = new int[p_arry.length];

        for (int i = 0; i < p_arry.length; i++) {
            String[] t_arry = p_arry[i].split("x"); // term array
            double c = Double.parseDouble(t_arry[0]); // coeff before x
            int e = 0;
            if (t_arry.length == 2) {
                e = Integer.parseInt(t_arry[1]); // exp after x
            }

            // add coeff and exp found to corresponding arrays
            coeff[i] = c;
            exp[i] = e;
        }
    }

    public Polynomial add(Polynomial to_add) {
        // check for validity, not valid return null
        if (this == null || to_add == null || exp.length != coeff.length || to_add.exp.length != to_add.exp.length)
            return null;

        // special cases
        // this is 0
        if (exp == null)
            return to_add;
        // to_add is 0
        if (to_add.exp == null)
            return this;

        // sort the poly
        this.poly_sort();
        to_add.poly_sort();

        // create merged exp array
        int[] tmp_exp = new int[this.exp.length + to_add.exp.length];
        double[] tmp_coeff = new double[this.exp.length + to_add.exp.length];

        int i = 0;
        int j = 0;
        int k = 0;
        while (i < this.exp.length || j < to_add.exp.length) {
            // nuffin left in this
            if (i == this.exp.length) {
                tmp_exp[k] = to_add.exp[j];
                tmp_coeff[k] = to_add.coeff[j];
                j++;
                k++;
            }
            // if there nothing left in to_add
            else if (j == to_add.exp.length) {
                tmp_exp[k] = this.exp[i];
                tmp_coeff[k] = this.coeff[i];
                i++;
                k++;
            }
            // check if both are the same
            else if (this.exp[i] == to_add.exp[j]) { // and resulting coeff is non-zero
                if (this.coeff[i] + to_add.coeff[j] != 0) {
                    tmp_exp[k] = this.exp[i];
                    tmp_coeff[k] = this.coeff[i] + to_add.coeff[j];
                    k++;
                }
                i++;
                j++;
            } else if (this.exp[i] < to_add.exp[j]) {
                tmp_exp[k] = this.exp[i];
                tmp_coeff[k] = this.coeff[i];
                i++;
                k++;
            } else {
                tmp_exp[k] = to_add.exp[j];
                tmp_coeff[k] = to_add.coeff[j];
                j++;
                k++;
            }
        }

        // create new array of the same size based on temp
        // double[] sum_coeff = new double[k];
        // int[] sum_exp = new int[k];

        // copy over nonzero data from temp
        // for (int n=0; n<k; n++)
        // {
        // sum_coeff[n] = tmp_coeff[n];
        // sum_exp[n] = tmp_exp[n];
        // }

        Polynomial tmp_sum = new Polynomial(tmp_coeff, tmp_exp);

        // construct sum poly
        // Polynomial sum = new Polynomial(sum_coeff, sum_exp);
        Polynomial sum = tmp_sum.rm_zero_coeff();

        // return sum
        return sum;
    }

    public double evaluate(double x) {

        double sum = 0;

        for (int i = 0; i < coeff.length; i++)
            sum += coeff[i] * Math.pow(x, exp[i]);

        return sum;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public void print_poly() {
        if (exp.length != coeff.length || exp == null)
            System.out.println("dne");

        int i = exp.length - 1;
        System.out.print(coeff[i] + "x^" + exp[i]);
        for (i = exp.length - 2; i >= 0; i--) {
            System.out.print(" + " + coeff[i] + "x^" + exp[i]);
        }
        System.out.println();
    }

    private Polynomial one_term_multiply(double coeff, int exp) {
        int len = this.exp.length;

        double[] product_coeff = new double[len];
        int[] product_exp = new int[len];

        for (int i = 0; i < len; i++) {
            product_coeff[i] = coeff * this.coeff[i];
            product_exp[i] = exp + this.exp[i];
        }

        Polynomial product = new Polynomial(product_coeff, product_exp);
        return product;
    }

    public void poly_sort() {
        // somehow sort this so that exp is in order and the coeff still correspond
        // prolly have to do it manually :(((
        int tmp_exp = 0;
        double tmp_coeff = 0;
        for (int i = 0; i < exp.length; i++) {
            for (int j = i + 1; j < exp.length; j++) {
                if (exp[i] > exp[j]) {
                    // swap exp
                    tmp_exp = exp[j];
                    exp[j] = exp[i];
                    exp[i] = tmp_exp;
                    // swap coeff
                    tmp_coeff = coeff[j];
                    coeff[j] = coeff[i];
                    coeff[i] = tmp_coeff;
                }
            }
        }
    }

    // another helper to get rid of coeff 0s
    private Polynomial rm_zero_coeff() {
        // count length of new arrays
        int len = 0;
        for (int i = 0; i < this.exp.length; i++) {
            if (this.coeff[i] != 0)
                len++;
        }

        // create new arrays of right size
        double[] c = new double[len];
        int[] e = new int[len];

        // copy over non-zero coeff and corresponding exp
        int i = 0; // for this poly
        int j = 0; // for new shorter poly
        while (i < this.exp.length && j < len) {
            if (this.coeff[i] != 0) {
                c[j] = this.coeff[i];
                e[j] = this.exp[i];
                j++;
            }
            i++;
        }

        // create poly
        Polynomial p = new Polynomial(c, e);

        return p;
    }

    public Polynomial multiply(Polynomial to_mult_by) {
        // max size of product is the highest degrees multiplied plus 1
        double[] product_coeff = new double[(this.exp.length - 1) * (to_mult_by.exp.length - 1) + 1];
        int[] product_exp = new int[(this.exp.length - 1) * (to_mult_by.exp.length - 1) + 1];
        Polynomial product = new Polynomial(product_coeff, product_exp);

        // multiply this by all the terms of to_mult_by and add them together
        for (int i = 0; i < to_mult_by.exp.length; i++) {
            // multiply ith term of to_mult_by by this
            Polynomial sub_product = this.one_term_multiply(to_mult_by.coeff[i], to_mult_by.exp[i]);

            // add it to the product so far
            product = product.add(sub_product);
        }

        product = product.rm_zero_coeff();

        return product;
    }
}
public class Polynomial {
    // fields
    
    // i
    double[] coeff; 

    //methods
    // ii
    public Polynomial() {
        coeff = new double[1];
    }
    // iii
    public Polynomial(double[] coefficients) {
        coeff = coefficients;
    }
    // iv
    public Polynomial add(Polynomial to_add) {
        // creating a new polynomal of approiate length with all coeff 0
        int length = Math.max(coeff.length, to_add.coeff.length);
        double[] s = new double[length];
        Polynomial sum = new Polynomial(s);

        for (int i=0; i < length; i++)
        {
            if (i < to_add.coeff.length)
                sum.coeff[i] += to_add.coeff[i];
            
            if (i < coeff.length)
                sum.coeff[i] += coeff[i];
        }
        return sum;
    } 
    // v
    public double evaluate(double x) {
        
        double sum = 0;

        for (int i=0; i < coeff.length; i++)
            sum += coeff[i] * Math.pow(x, i);
        
        return sum;
    }
    // vi
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }


}
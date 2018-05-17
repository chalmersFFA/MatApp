package imat;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

/**
 * Created by Jonathan Köre on 2018-05-02.
 */
public class test {
    public static void main(String[] args) {
        //Bara en thicc imat.test jag gjorde
        System.out.println(System.getProperty("user.home"));
        IMatDataHandler db = IMatDataHandler.getInstance();
        Product p = db.getProduct(87);
        System.out.println(p.getName());

        System.out.println(fixNumber(1234567.44));
    }

    public static String fixNumber(double number) {
        String strNum = Double.toString(number);
        StringBuilder sb = new StringBuilder();
        int p = 0;
        for(int i = strNum.length(); i > 0; i--) {
            sb.append(strNum.charAt(i-1));
            p++;
            if(p % 3 == 0 && strNum.charAt(i-1)!='.'){
                sb.append(" ");
            }
        }
        sb = sb.reverse();
        return sb.toString();
    }
}

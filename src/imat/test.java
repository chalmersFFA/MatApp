package imat;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

/**
 * Created by Jonathan Köre on 2018-05-02.
 */
public class test {
    public static void main(String[] args) {
        //Bara en thicc imat.test jag gjorde
        /*System.out.println(System.getProperty("user.home"));
        IMatDataHandler db = IMatDataHandler.getInstance();
        Product p = db.getProduct(87);
        System.out.println(p.getName());*/

        System.out.println(fixNumber2(1000.44521312312312311231239));
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

    private static int getTheNumber(String strNum){
        for(int q = 0; q < strNum.length(); q++) {
            if(strNum.charAt(q)=='.'){
                return q;
            }
        }
        return 0;
    }


    public static String fixNumber2(double number) {
        if(number < 2222222) { // om du betalar mer än 2 mille är du dum och du förstör programmet
            String strNum = Double.toString(number);
            int k = getTheNumber(strNum);
            int p = 0;
            StringBuilder sb = new StringBuilder();

            for (int i = k + 3; i > 0; i--) {
                sb.append(strNum.charAt(i - 1));
                p++;
                if (p % 3 == 0 && strNum.charAt(i - 1) != '.') {
                    sb.append(" ");
                }
            }
            sb = sb.reverse();
            return sb.toString();
        }
        return Double.toString(number);
    }
}

package imat;

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

        System.out.println(doubleToString(1000.002));
    }

    private static int getIndexOfDecimal(String strNum){
        for(int q = 0; q < strNum.length(); q++) {
            if(strNum.charAt(q)=='.'){
                return q;
            }
        }
        return 0;
    }


    public static String doubleToString(double number) {
        if(number < 2222222) { // om du betalar mer än 2 mille är du dum och du förstör programmet
            String strNum = Double.toString(number);
            if(strNum.charAt(strNum.length()-2)=='.'){
                strNum = strNum + "0";
            }
            StringBuilder sb = new StringBuilder();
            int k = getIndexOfDecimal(strNum);
            int p = 0;
            for (int i = k + 3; i > 0; i--) {
                sb.append(strNum.charAt(i - 1));
                p++;
                if (p % 3 == 0 && strNum.charAt(i - 1) != '.' && p!=strNum.length()) {
                    sb.append(" ");
                }
            }
            return sb.reverse().toString();
        }
        return Double.toString(number);
    }
}

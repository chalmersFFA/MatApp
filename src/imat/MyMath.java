package imat;

public class MyMath {
    /*public static double doubleToString(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
*/


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

package imat;

public class MyMath {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }



    public static String fixNumber(double number) {
        String strNum = Double.toString(number);
        int p = 0;
        StringBuilder sb = new StringBuilder();

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

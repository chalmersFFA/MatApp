import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

/**
 * Created by Jonathan Köre on 2018-05-02.
 */
public class test {
    public static void main(String[] args) {
        //Bara en thicc test jag gjorde
        System.out.println(System.getProperty("user.home"));
        IMatDataHandler db = IMatDataHandler.getInstance();
        Product p = db.getProduct(87);
        System.out.println(p.getName());
    }

}

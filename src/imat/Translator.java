package imat;

import se.chalmers.cse.dat216.project.ProductCategory;

/**
 * Created by Jonathan Köre on 2018-05-06.
 */
public class Translator {
    static String swe(ProductCategory p) {
        switch(p) {
            case BERRY:
                return "bär";
            case POD:
                return " ";
            case BREAD:
                return "Bröd";
            case CITRUS_FRUIT:
                return "Citrusfrukt";
            case HOT_DRINKS:
                return "Varma Drycker";
            case COLD_DRINKS:
                return "Kalla Drycker";
            case EXOTIC_FRUIT:
                return "Exotisk Frukt";
            case FISH:
                return "Fisk";
            case VEGETABLE_FRUIT:
                return "GrönsaksFrukt?";
            case CABBAGE:
                return "Kål";
            case MEAT:
                return "Kött";
            case DAIRIES:
                return "Mejeri";
            case MELONS:
                return "Meloner";
            case FLOUR_SUGAR_SALT:
                return "Mjöl_Socker_Salt";
            case NUTS_AND_SEEDS:
                return "Nötter och Fröer";
            case PASTA:
                return "Pasta";
            case POTATO_RICE:
                return "Potatis och ris";
            case ROOT_VEGETABLE:
                return "Rotfrukter";
            case FRUIT:
                return "Frukt";
            case SWEET:
                return "Sött";
            case HERB:
                return "Örter";
            default:
                return p.toString();
        }
    }
}

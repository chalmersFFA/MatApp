package imat;

import se.chalmers.cse.dat216.project.ProductCategory;

/**
 * Created by Jonathan Köre on 2018-05-06.
 */
public class Translator {
    static String swe(ProductCategory p) {
        switch(p) {
            case BERRY:
                return "Bär";
            case POD:
                return "Baljväxter";
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
                return "Övrigt";
            case CABBAGE:
                return "Kål";
            case MEAT:
                return "Kött";
            case DAIRIES:
                return "Mejeri";
            case MELONS:
                return "Meloner";
            case FLOUR_SUGAR_SALT:
                return "Mjöl, Socker & Salt";
            case NUTS_AND_SEEDS:
                return "Nötter & Frön";
            case PASTA:
                return "Pasta";
            case POTATO_RICE:
                return "Potatis & ris";
            case ROOT_VEGETABLE:
                return "Rotfrukter";
            case FRUIT:
                return "Frukt";
            case SWEET:
                return "Sötsaker";
            case HERB:
                return "Örter & Kryddor";
            default:
                return p.toString();
        }
    }
}

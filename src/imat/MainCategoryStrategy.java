package imat;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

/**
 * Created by Jonathan Köre on 2018-05-05.
 */
public class MainCategoryStrategy implements CategoryStrategy {


    @Override
    public void click(CategoryItem c, IMatController parentController) {
        if(c.isExpanded()) {
            c.setExpanded(false);
            parentController.collapseCategory(c);
            parentController.deSelectCategory(c);
            c.getArrowImageView().setImage(new Image("imat/layout/images/arrow_forward.png"));
            //System.out.println("nu stänger jag");
        }
        else {
            c.setExpanded(true);
            parentController.expandCategory(c);
            parentController.selectCategory(c);
            /*for(CategoryItem s : c.getSubCategories()) {
                if(s == parentController.getCurrentExpandedSub()) {
                    parentController.selectCategory(s);
                }
                else {
                    parentController.deSelectCategory(s);
                }
            }*/
            c.getArrowImageView().setImage(new Image("imat/layout/images/arrow_downward.png"));
            //System.out.println("nu öppnar jag");
            parentController.clearProductList();

            switch(c.getDisplayName()){
                case("Drycker"):
                    parentController.updateProductList(ProductCategory.COLD_DRINKS);
                    parentController.updateProductList(ProductCategory.HOT_DRINKS);

                    parentController.setCurrentSiteLabel("Kategori: " + c.getDisplayName());
                    break;
                case("Frukt och grönt"):
                    parentController.updateProductList(ProductCategory.VEGETABLE_FRUIT);
                    parentController.updateProductList(ProductCategory.ROOT_VEGETABLE);
                    parentController.updateProductList(ProductCategory.MELONS);
                    parentController.updateProductList(ProductCategory.CABBAGE);
                    parentController.updateProductList(ProductCategory.FRUIT);
                    parentController.updateProductList(ProductCategory.EXOTIC_FRUIT);
                    parentController.updateProductList(ProductCategory.CITRUS_FRUIT);
                    parentController.updateProductList(ProductCategory.BERRY);
                    parentController.updateProductList(ProductCategory.POD);

                    parentController.setCurrentSiteLabel("Kategori: " + c.getDisplayName());
                    break;
                case("Potatis, Ris och Pasta"):
                    parentController.updateProductList(ProductCategory.POTATO_RICE);
                    parentController.updateProductList(ProductCategory.PASTA);

                    parentController.setCurrentSiteLabel("Kategori: " + c.getDisplayName());
                    break;
                case("Skafferi"):
                    parentController.updateProductList(ProductCategory.HERB);
                    parentController.updateProductList(ProductCategory.NUTS_AND_SEEDS);
                    parentController.updateProductList(ProductCategory.FLOUR_SUGAR_SALT);

                    parentController.setCurrentSiteLabel("Kategori: " + c.getDisplayName());
                    break;
            }
        }

    }
}

package imat;

/**
 * Created by Jonathan Köre on 2018-05-06.
 */
public class SubCategoryStrategy implements CategoryStrategy {
    @Override
    public void click(CategoryItem c, IMatController parentController) {
        if(c != parentController.getCurrentExpandedSub()) {
            if(parentController.getCurrentExpandedSub() != null) {
                parentController.deSelectCategory(parentController.getCurrentExpandedSub());
            }
            parentController.selectCategory(c);
            parentController.setCurrentExpandedSub(c);
            parentController.updateProductList(c.getProductCategory());
        }

    }
}

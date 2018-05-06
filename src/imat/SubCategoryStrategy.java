package imat;

/**
 * Created by Jonathan Köre on 2018-05-06.
 */
public class SubCategoryStrategy implements CategoryStrategy {
    @Override
    public void click(CategoryItem c, IMatController parentController) {
        if(c != parentController.getCurrentExpandedSub()) {
            if(parentController.getCurrentExpandedSub() != null)
                parentController.getCurrentExpandedSub().getBackgroundPane().getStyleClass().remove("green");

            parentController.setCurrentExpandedSub(c);
            c.getBackgroundPane().getStyleClass().add("green");
            parentController.updateRecipeList(c.getProductCategory());
        }

    }
}

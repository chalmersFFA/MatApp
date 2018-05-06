package imat;

import javafx.scene.image.Image;

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
        }

    }
}

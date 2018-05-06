package imat;

import javafx.scene.layout.Background;

/**
 * Created by Jonathan Köre on 2018-05-05.
 */
public class MainCategoryStrategy implements CategoryStrategy {


    @Override
    public void click(CategoryItem c, IMatController parentController) {
        if(c.isExpanded()) {
            c.setExpanded(false);
            parentController.collapseCategory(c);
            c.getBackgroundPane().getStyleClass().remove("red");
            for(CategoryItem s : c.getSubCategories()) {
                s.getBackgroundPane().getStyleClass().remove("side_borders");
            }
        }
        else {
            c.setExpanded(true);
            parentController.expandCategory(c);
            c.getBackgroundPane().getStyleClass().add("red");
            for(CategoryItem s : c.getSubCategories()) {
                s.getBackgroundPane().getStyleClass().add("side_borders");
            }
        }

    }
}

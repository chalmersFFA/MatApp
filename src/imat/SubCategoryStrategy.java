package imat;

/**
 * Created by Jonathan Köre on 2018-05-06.
 */
public class SubCategoryStrategy implements CategoryStrategy {
    @Override
    public void click(CategoryItem c, IMatController parentController) {
        System.out.println("Sub");
    }
}

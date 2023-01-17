/**
 * 
 */
package exercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author teigaba
 *
 */
public class Excercise1Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TreeNode myTree = new TreeNode();

		myTree.add(Category.CAT1, Category.CAT2, Category.CAT3);
		myTree.add(Category.CAT3, Category.CAT2, Category.CAT1);
		myTree.add(Category.CAT3, Category.CAT3, Category.CAT3);
		myTree.add(Category.CAT1);

		List<Category> aCategory = findAllDescendantsBy(Category.CAT2, myTree);

		Stream.of(aCategory).forEach(s -> System.out.println(s));
	}

	/**
	 * @param category the category of which find all descendants
	 * @param root     the root node of the tree
	 *
	 * @return the list of all descendants categories, including the category passed
	 *         as input
	 */
	public static List<Category> findAllDescendantsBy(Category category, TreeNode root) {

		List<Category> findCategory = new ArrayList<Category>();

		if (root == null) {
			return findCategory;
		}
		findCategory.add(category);
		int i = category.value();
		while (i >= Category.CAT1.value()) {
			findCategory.addAll(root.getChildren(Category.fromValue(i)));
			i--;
		}
		return findCategory;
	}
}

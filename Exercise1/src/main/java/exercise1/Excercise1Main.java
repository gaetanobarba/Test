/**
 * 
 */
package exercise1;

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
		TreeNode myTree = new TreeNode(Category.CAT1);

		myTree.addChild(new TreeNode(Category.CAT1));
		myTree.addChild(new TreeNode(Category.CAT2));
		myTree.addChild(new TreeNode(Category.CAT3));
		myTree.addChild(new TreeNode(Category.CAT1));
		myTree.addChild(new TreeNode(Category.CAT2));
		myTree.addChild(new TreeNode(Category.CAT3));

		List<Category> aCategory = myTree.findAllDescendantsBy(Category.CAT1, myTree);

		Stream.of(aCategory).forEach(s -> System.out.println(s));
	}

}

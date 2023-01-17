package exercise1;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Excercise1MainTest {

	@Test
	public void test() {

		TreeNode myTree = new TreeNode(Category.CAT1);

		myTree.addChild(new TreeNode(Category.CAT1), new TreeNode(Category.CAT2), new TreeNode(Category.CAT3));
		myTree.addChild(new TreeNode(Category.CAT3), new TreeNode(Category.CAT2), new TreeNode(Category.CAT1));
		myTree.addChild(new TreeNode(Category.CAT3), new TreeNode(Category.CAT3), new TreeNode(Category.CAT3));

		List<Category> assertCategory = new ArrayList<Category>(
				Arrays.asList(Category.CAT2, Category.CAT1, Category.CAT1, Category.CAT1));

		List<Category> aCategory = myTree.findAllDescendantsBy(Category.CAT2, myTree);

		assertEquals(assertCategory, aCategory);
	}

}

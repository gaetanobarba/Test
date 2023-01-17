package exercise1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Excercise1MainTest {

	@Test
	public void test() {
		TreeNode myTree = new TreeNode();

		myTree.add(Category.CAT1, Category.CAT2, Category.CAT3);
		myTree.add(Category.CAT3, Category.CAT2, Category.CAT1);
		myTree.add(Category.CAT3, Category.CAT3, Category.CAT3);
		myTree.add(Category.CAT1);

		List<Category> assertCategory = new ArrayList<Category>(Arrays.asList(Category.CAT2, Category.CAT2, Category.CAT2,
				Category.CAT1, Category.CAT1, Category.CAT1));
		
		List<Category> aCategory = Excercise1Main.findAllDescendantsBy(Category.CAT2, myTree);

		assertEquals(aCategory, assertCategory);
	}

}

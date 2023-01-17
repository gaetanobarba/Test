/**
 * 
 */
package exercise1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author teigaba
 *
 */
/**
 * A node of the tree.
 */
class TreeNode {

	private Map<Category, List<Category>> children;

	public TreeNode() {
		super();
		children = new HashMap<>();
		children.put(Category.CAT3, new ArrayList<>());
		children.put(Category.CAT2, new ArrayList<>());
		children.put(Category.CAT1, new ArrayList<>());
	}

	public TreeNode(Category category) {
		super();
		add(category);
	}

	public void add(Category... categories) {
		for (Category category : categories) {
			add(category);
		}
	}

	public void add(Category category) {

		children.get(category).add(category);
	}

	public void clear() {
		children.clear();
	}

	public ArrayList<Category> getChildren(Category category) {
		return (ArrayList<Category>) children.get(category);
	}
}
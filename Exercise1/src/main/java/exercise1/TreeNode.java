/**
 * 
 */
package exercise1;

import java.util.ArrayList;
import java.util.List;

/**
 * A node of the tree.
 */
public class TreeNode {

	Category category;
	List<TreeNode> children;

	public TreeNode(Category category) {
		this.category = category;
		this.children = new ArrayList<TreeNode>();
	}

	public void addChild(TreeNode node) {
		this.children.add(node);
	}

	public void addChild(TreeNode... nodes) {
		for (TreeNode treeNode : nodes) {
			this.children.add(treeNode);
		}
	}

	/**
	 * @param category the category of which find all descendants
	 * @param root     the root node of the tree
	 *
	 * @return the list of all descendants categories, including the category passed
	 *         as input
	 */
	public List<Category> findAllDescendantsBy(Category category, TreeNode root) {

		List<Category> ret = new ArrayList<>();
		ret.add(category);

		if (root.category.value() < category.value())
			ret.add(root.category);

		for (TreeNode node : this.children) {
			if (node.category.value() < category.value())
				ret.add(node.category);
		}

		return ret;
	}
}

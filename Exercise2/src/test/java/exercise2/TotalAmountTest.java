package exercise2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TotalAmountTest {

	private List<Product> products = new ArrayList<>();

	@Before
	public void setUp() {
		// make sure we always have an empty list for future tests
		products.clear();

		// add some colorful apples
		products.add(new Product(Category.CAT1, 10, 1));
		products.add(new Product(Category.CAT2, 10, 2));
		products.add(new Product(Category.CAT2, 10, 3));
	}

	@Test
	public void totalAmount() {
		// get only green apples from the list, using a Lambda!
		// also, using Collectors.toList() to get all the results into a List at the end
		final double total = exercise2.TotalAmount.amount(products, Category.CAT2);
		assertEquals(total, 50, 0);
	}

}

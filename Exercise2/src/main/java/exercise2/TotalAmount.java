/**
 * 
 */
package exercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author teigaba
 *
 */
public class TotalAmount {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Product> myProducts = new ArrayList<>(List.of(new Product(Category.CAT1, 10, 1),
				new Product(Category.CAT2, 10, 2), new Product(Category.CAT2, 10, 3)));

		double totalAmount = amount(myProducts, Category.CAT2);

		System.out.print("Total Amount is: " + totalAmount);
	}

	/**
	 * @param products the list of products
	 * @param category the category on which to filter
	 *
	 * @return The sum of total amount for all products belonging to the input
	 *         category
	 */
	public static double amount(List<Product> products, Category category) throws IllegalArgumentException {
		// price * quantity for Category

		AtomicReference<Double> amount = new AtomicReference<>((double) 0);

		Optional.ofNullable(products).ifPresentOrElse(v -> {
			List<Product> productsCat = products.stream().filter(cat -> cat.getCategory().equals(category)).collect(Collectors.toCollection(ArrayList::new));
			Stream.of(productsCat).forEach(s -> System.out.println(s));
			for (Product product : productsCat) {
				double partialAmount = product.getPrice() * product.getQuantity();
				amount.set(amount.get() + partialAmount);

			}

		}, () -> {
			throw new IllegalArgumentException("Product List is NULL");
		}

		);

		return amount.get();
	}
}

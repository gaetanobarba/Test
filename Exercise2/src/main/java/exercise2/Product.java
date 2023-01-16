package exercise2;
/**
 * 
 */


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author teigaba
 *
 */
public class Product {

	private int productId;
	private String description;
	private Category category;
	private int quantity;
	private double price;
	private Date date;

	/**
	 * @param productId
	 * @param description
	 * @param category
	 * @param quantity
	 * @param price
	 * @param date
	 */
	public Product(int productId, String description, Category category, int quantity, double price, Date date) {
		super();
		this.productId = productId;
		this.description = description;
		this.category = category;
		this.quantity = quantity;
		this.price = price;
		this.date = date;
	}

	/**
	 * @param category
	 * @param quantity
	 * @param price
	 */
	public Product(Category category, int quantity, double price) {
		super();
		this.category = category;
		this.quantity = quantity;
		this.price = price;
		this.date = Calendar.getInstance().getTime();
	}

	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(date);

		return "Product [productId=" + productId + ", description=" + description + ", category=" + category
				+ ", quantity=" + quantity + ", price=" + price + ", date=" + strDate + "]\n";
	}

}

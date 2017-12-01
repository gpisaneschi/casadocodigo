package casadocodigo.loja.builders;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import casadocodigo.loja.models.BookType;
import casadocodigo.loja.models.Price;
import casadocodigo.loja.models.Product;

public class ProductBuilder {

	private List<Product> products = new ArrayList<Product>();
	
	private ProductBuilder(Product product){
		products.add(product);
	}
	
	public static ProductBuilder newProduct(BookType bookType, BigDecimal valeu){
		Product book = create("Book 1", BookType.COMBO, BigDecimal.TEN);
		return new ProductBuilder(book);
	}
	
	private static Product create(String bookName, BookType bookType, BigDecimal value){
		Product book = new Product();
		book.setTitle(bookName);
		book.setReleaseDate(Calendar.getInstance());
		book.setNumberOfPages(150);
		book.setDescription("great book about testing");
		Price price = new Price();
		price.setBookType(bookType);
		price.setValue(value);
		book.getPrices().add(price);
		return book;
	}
	
	public ProductBuilder more(int number){
		Product base = products.get(0);
		Price price = base.getPrices().get(0);
		for(int i = 0; i < number; i++){
			products.add(create("Book "+i, price.getBookType(), price.getValue()));
		}
		return this;
	}
	
	public Product buildOne(){
		return products.get(0);
	}
	
	public List<Product> buildAll(){
		return products;
	}
	
	
}

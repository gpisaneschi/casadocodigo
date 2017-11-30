package casadocodigo.loja.controllers;

import java.math.BigDecimal;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import casadocodigo.loja.daos.ProductDao;
import casadocodigo.loja.models.BookType;
import casadocodigo.loja.models.PaymentData;
import casadocodigo.loja.models.Product;
import casadocodigo.loja.models.ShoppingCart;
import casadocodigo.loja.models.ShoppingItem;

@Controller
@RequestMapping("/shopping")
public class ShoppingCartController {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ShoppingCart shoppingCard;
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView add(Integer productId, @RequestParam BookType bookType){
		ShoppingItem item = createItem(productId, bookType);
		shoppingCard.add(item);
		return new ModelAndView("redirect:/products");
	}
	private ShoppingItem createItem(Integer productId, BookType bookType) {
		Product product = productDao.find(productId);
		ShoppingItem item = new ShoppingItem(product, bookType);
		return item;
	}
	@RequestMapping(method=RequestMethod.POST, value="/checkout")
	public Callable<String> checkout(){
		return () -> {
			BigDecimal total = shoppingCard.getTotal();
			
			String uriToPay = "http://book-payment.herokuapp.com/payment";
			try {
				String response = restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
				System.out.println(response);
				return "redirect:/products";
			} catch (HttpClientErrorException exception) {
				System.out.println("Ocorreu um erro ao criar o pagamento: " +exception.getMessage());
				return "redirect:/shopping";
			}
		};
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String list(){
		return "shoppingCart/items";
	}
	
	public ProductDao getProductDao() {
		return productDao;
	}
	
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	public ShoppingCart getShoppingCard() {
		return shoppingCard;
	}

	public void setShoppingCard(ShoppingCart shoppingCard) {
		this.shoppingCard = shoppingCard;
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	
}

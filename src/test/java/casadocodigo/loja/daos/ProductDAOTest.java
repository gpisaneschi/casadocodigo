package casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import casadocodigo.loja.builders.ProductBuilder;
import casadocodigo.loja.conf.DataSourceConfigurationTest;
import casadocodigo.loja.models.BookType;
import casadocodigo.loja.models.Product;
import casodocodigo.loja.conf.JPAConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ProductDao.class, JPAConfiguration.class, DataSourceConfigurationTest.class})
@ActiveProfiles("test")
public class ProductDAOTest {

	
	@Autowired
	private ProductDao productDao;
	
	@Transactional
	@Test
	public void shouldSumAllPricesOfEachBookPerType(){
		List<Product> printedBooks = ProductBuilder.newProduct(BookType.PRINTED, BigDecimal.TEN).more(4).buildAll();
		printedBooks.stream().forEach(productDao::save);
		
		List<Product> ebooks = ProductBuilder.newProduct(BookType.EBOOK, BigDecimal.TEN).more(4).buildAll();
		ebooks.stream().forEach(productDao::save);
		
		BigDecimal value = productDao.sumPricesPerType(BookType.PRINTED);
		
		Assert.assertEquals(new BigDecimal(50).setScale(2), value);
		
	}
	
}

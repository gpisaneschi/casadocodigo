package casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import casadocodigo.loja.models.Product;

@Repository
public class ProductDao {

	@PersistenceContext
	private EntityManager manager;
	
	
	public void save(Product product) {
		// TODO Auto-generated method stub
		manager.persist(product);
		
	}
	
	public List<Product> list(){
		return manager.createQuery("select distinct(p) from Product p join fetch p.prices", Product.class).getResultList();
	}
	
	public Product find(Integer id){
		return manager.createQuery("select distinct(p) from Product p join fetch p.prices where p.id = :id", Product.class)
				.setParameter("id", id)
				.getSingleResult();
	}

}

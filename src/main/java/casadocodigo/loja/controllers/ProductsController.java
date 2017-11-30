package casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.xml.bind.Binder;

import casadocodigo.loja.daos.ProductDao;
import casadocodigo.loja.infra.FileSaver;
import casadocodigo.loja.models.BookType;
import casadocodigo.loja.models.Product;
import casadocodigo.loja.validations.ProductValidator;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductDao productDAO;
	@Autowired
	private FileSaver fileSaver;
	
	
	//@RequestMapping("/products/form")
	//public String form(){
	//	return "products/form";
	//}
	
	/*@InitBinder
	private void initBinder(WebDataBinder binder) {
		// TODO Auto-generated method stub
		binder.addValidators(new ProductValidator());
	}*/
	
	@RequestMapping("/form")
	public ModelAndView form(Product product){
		ModelAndView modelAndView = new ModelAndView("/products/form");
		modelAndView.addObject("types", BookType.values());
		return modelAndView;
	}
	
	@Transactional
	@CacheEvict(value="lastProducts")
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView save(MultipartFile summary, @Valid Product product, BindingResult bindingResult, RedirectAttributes attributes){
		//System.out.println("Cadastrando o produto: " + product);
		if(bindingResult.hasErrors()){
			return form(product);
		}
		String webPath = fileSaver.write("uploaded-summaries", summary);
		product.setSummaryPath(webPath);
		productDAO.save(product);
		attributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso.");
		return new ModelAndView("redirect:products");
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/{id}")
	public ModelAndView show(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("products/show");
		modelAndView.addObject("product", productDAO.find(id));
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@Cacheable(value="lastProducts")
	public ModelAndView list(){
		ModelAndView modelAndView = new ModelAndView("/products/list");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}
}

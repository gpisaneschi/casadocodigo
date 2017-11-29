package casadocodigo.loja.validations;

import java.util.Iterator;

import org.hibernate.jpa.criteria.predicate.IsEmptyPredicate;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mysql.jdbc.Field;

import casadocodigo.loja.models.Price;
import casadocodigo.loja.models.Product;

public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "field.required");
		Product product = (Product) target;
		if(product.getNumberOfPages() == 0){
			errors.rejectValue("numberOfPages", "field.required");
		}

	}

}

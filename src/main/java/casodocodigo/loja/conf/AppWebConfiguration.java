package casodocodigo.loja.conf;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.DateFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.instrument.classloading.ResourceOverridingShadowingClassLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.common.cache.CacheBuilder;


import casadocodigo.loja.controllers.HomeController;
import casadocodigo.loja.daos.ProductDao;
import casadocodigo.loja.infra.FileSaver;
import casadocodigo.loja.models.ShoppingCart;
import casodocodigo.loja.viewresolver.JsonViewResolver;

@EnableWebMvc
@EnableCaching
@ComponentScan(basePackageClasses={HomeController.class, ProductDao.class, FileSaver.class, ShoppingCart.class})
public class AppWebConfiguration extends WebMvcConfigurerAdapter{
	
		@Bean
		public InternalResourceViewResolver internalResourceViewResolver(){
			InternalResourceViewResolver resolver = new InternalResourceViewResolver();
			resolver.setPrefix("/WEB-INF/views/");
			resolver.setSuffix(".jsp");
			resolver.setExposedContextBeanNames("shoppingCart");
			return resolver;
		}
		
		@Bean
		public ViewResolver ContentNegotiatingViewResolver(ContentNegotiationManager manager){
			List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
			resolvers.add (internalResourceViewResolver());
			resolvers.add (new JsonViewResolver());
			
			ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
			resolver.setViewResolvers(resolvers);
			resolver.setContentNegotiationManager(manager);
			return resolver;
			
		}
		
		@Bean
		public MessageSource messageSource(){
			ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
			bundle.setBasename("/WEB-INF/messages");
			bundle.setDefaultEncoding("UTF-8");
			bundle.setFallbackToSystemLocale(true);
			bundle.setCacheSeconds(1);
			return bundle;
		}
		
		@Bean
		public FormattingConversionService mvcConversionService(){
			DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(true);
			
			DateFormatterRegistrar registrar = new DateFormatterRegistrar();
			registrar.setFormatter(new DateFormatter("yyyy-MM-dd"));
			registrar.registerFormatters(conversionService);
			return conversionService;
		}
		
		@Bean
		public CacheManager cacheManasger(){
			CacheBuilder<Object, Object> builder = CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(1, TimeUnit.MINUTES);
			GuavaCacheManager cacheManager = new GuavaCacheManager();
			cacheManager.setCacheBuilder(builder);
			return cacheManager;
		}
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new LocaleChangeInterceptor());
		}
		
		@Bean
		public LocaleResolver localeResolver(){
			return new CookieLocaleResolver();
		}
		
		@Bean
		public MultipartResolver multipartResolver(){
			return new StandardServletMultipartResolver();
		}
		
		@Bean
		public RestTemplate restTemplate(){
			return new RestTemplate();
		}
		
		@Override
		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
			configurer.enable();
		}
		
}
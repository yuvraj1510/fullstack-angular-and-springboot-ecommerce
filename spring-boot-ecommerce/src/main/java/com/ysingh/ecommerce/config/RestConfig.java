package com.ysingh.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ysingh.ecommerce.entity.Product;
import com.ysingh.ecommerce.entity.ProductCategory;

@Configuration
public class RestConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		
		HttpMethod[] unSupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
		
		// disable HTTP methods for Product: PUT, POST and DELETE
		config.getExposureConfiguration().forDomainType(Product.class)
				.withItemExposure((metadata, httpMethods) -> httpMethods.disable(unSupportedActions))
				.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unSupportedActions));

		// disable HTTP methods for ProductCategory: PUT, POST and DELETE
		config.getExposureConfiguration().forDomainType(ProductCategory.class)
				.withItemExposure((metadata, httpMethods) -> httpMethods.disable(unSupportedActions))
				.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unSupportedActions));
		
	}

	
}

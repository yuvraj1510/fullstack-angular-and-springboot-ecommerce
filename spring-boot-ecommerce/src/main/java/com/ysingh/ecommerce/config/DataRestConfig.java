package com.ysingh.ecommerce.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.ysingh.ecommerce.entity.Country;
import com.ysingh.ecommerce.entity.Order;
import com.ysingh.ecommerce.entity.Product;
import com.ysingh.ecommerce.entity.ProductCategory;
import com.ysingh.ecommerce.entity.State;

@Configuration
public class DataRestConfig implements RepositoryRestConfigurer {

    @Value("${allowed.origins}")
    private String[] allowedOrigins;
    
	private EntityManager entityManager;

	@Autowired
	public DataRestConfig(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

		HttpMethod[] unSupportedActions = { HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH };

		// disable HTTP methods for Product: PUT, POST and DELETE
		disableHttpMethods(config, unSupportedActions, Product.class);

		// disable HTTP methods for ProductCategory: PUT, POST and DELETE
		disableHttpMethods(config, unSupportedActions, ProductCategory.class);

		// disable HTTP methods for Country: PUT, POST and DELETE
		disableHttpMethods(config, unSupportedActions, Country.class);

		// disable HTTP methods for State: PUT, POST and DELETE
		disableHttpMethods(config, unSupportedActions, State.class);
		
		// disable HTTP methods for Order: PUT, POST and DELETE
        disableHttpMethods(config, unSupportedActions, Order.class);

		// call an internal helper method to expose ID
		exposeIds(config);
		
		// configure the CORS Mapping
		cors.addMapping(config.getBasePath() + "/**").allowedOrigins(allowedOrigins);

	}

	private void disableHttpMethods(RepositoryRestConfiguration config, HttpMethod[] unSupportedActions, Class<?> theClass) {
		config.getExposureConfiguration().forDomainType(theClass)
				.withItemExposure((metadata, httpMethods) -> httpMethods.disable(unSupportedActions))
				.withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unSupportedActions));
	}

	@SuppressWarnings("rawtypes")
	private void exposeIds(RepositoryRestConfiguration config) {
		Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

		List<Class> entityClasses = new ArrayList<>();

		for (EntityType tempEntityType : entities) {
			entityClasses.add(tempEntityType.getJavaType());
		}

		Class[] domainTypes = entityClasses.toArray(new Class[0]);
		config.exposeIdsFor(domainTypes);
	}

}

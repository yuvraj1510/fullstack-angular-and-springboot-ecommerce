package com.ysingh.ecommerce.dao;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.ysingh.ecommerce.entity.State;

@RepositoryRestResource
public interface StateRepository extends JpaRepository<State, Integer> {
	
	List<State> findByCountryCode(@PathParam("code") String code);

}

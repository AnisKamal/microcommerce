package com.ecommerce.microcommerce.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("API pour les opérations CRUD sur les produits. ")

@RestController
public class ProductController {
	
	@Autowired 
	private ProductDao productDao;
	
//	@RequestMapping(value="/Produits", method=RequestMethod.GET)
//	public List<Product> listeProduits() {
//		return productDao.findAll();
//	}
	

	@PostMapping(value="/Produits")
	public ResponseEntity<Product> ajouterProduit( @Valid @RequestBody Product produit) {
		Product productAdded = productDao.save(produit);
		if(Objects.isNull(productAdded)) {
			return ResponseEntity.noContent().build();
		}
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(productAdded.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	
	
	@GetMapping("/Produits")
	public MappingJacksonValue listeProduits() {
		List<Product> produits = productDao.findAll();
		SimpleBeanPropertyFilter monFiltre = 
				SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
		FilterProvider listDeNosFiltres = new 
				SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
		MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);
		produitsFiltres.setFilters(listDeNosFiltres);
		return produitsFiltres ; 
	}
	
	
	@ApiOperation(value = "Recupere un produit grace à son ID à condition que celui-ci soit en stock ! ")
	@GetMapping(value="/Produits/{id}")
	public Product afficherUnProduit(@PathVariable int id) {
		Product produit = productDao.findById(id);
		if(produit == null) throw new ProduitIntrouvableException("Le produit avec l'id "+ 
		id + "est INTROUVABLE. Ecran Bleu si je pouvais.");
		return produit;
	}
	
	@GetMapping(value = "test/produits/{prixLimit}")
	public List<Product> testDeRequetes(@PathVariable int prixLimit)
	{
		return productDao.findByPrixGreaterThan(400);
	}
	
	@DeleteMapping(value="Produits/{id}")
	public void supprimerProduit(@PathVariable int id) {
		productDao.deleteById(id);
	}
	
	@PutMapping (value = "/Produits")
	public void updateProduit(@RequestBody Product product)
	 {
	    productDao.save(product);
	 }
	
//	Product produit = productDao.findById(id);
//	if(produit == null) throw new ProduitIntrouvableException("Le produit avec l'id "+ 
//	id + "est INTROUVABLE. Ecran Bleu si je pouvais.");
//	return produit;
	
	
}

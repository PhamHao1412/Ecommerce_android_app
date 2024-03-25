package StoreApp.StoreApp.controller;

import java.util.ArrayList;
import java.util.List;

import StoreApp.StoreApp.entity.Specifications;
import StoreApp.StoreApp.entity.User;
import StoreApp.StoreApp.model.CartDto;
import StoreApp.StoreApp.model.SpecificationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import StoreApp.StoreApp.entity.Cart;
import StoreApp.StoreApp.entity.Product;
import StoreApp.StoreApp.service.CartService;
import StoreApp.StoreApp.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	CartService cartService;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping(path = "/newproduct")
	public ResponseEntity<List<Product>> newProduct(){
		List<Product> newProducts = productService.findTop12ProductNewArrivals();
		if(newProducts != null)
			return new ResponseEntity<>(newProducts, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(path = "/bestsellers")
	public ResponseEntity<List<Product>> bestSellers(){
		List<Product> bestSellers = productService.findTop12ProductBestSellers();
		if(bestSellers != null)
			return new ResponseEntity<>(bestSellers, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(path = "/search")
	public ResponseEntity<List<Product>> search(@RequestParam("searchContent") String searchContent){
		// Tách chuỗi thành mảng các từ
		String[] keywords = searchContent.split("\\s+");

		// Tìm kiếm theo từng từ trong mảng
		List<Product> products = new ArrayList<>();
		for (String keyword : keywords) {
			List<Product> productsByKeyword = productService.findByProduct_NameContaining(keyword);
			if (!productsByKeyword.isEmpty()) {
				products.addAll(productsByKeyword);
			}
		}
		if (!products.isEmpty()) {
			return new ResponseEntity<>(products, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/colors")
	public ResponseEntity<List<String>> getColors(String productName,String capacity){
		List<String> colors = productService.findColor_ProductByName(productName,capacity);
		if(colors != null)
			return new ResponseEntity<>(colors, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@GetMapping(path = "/capacities")
	public ResponseEntity<List<String>> getCapacities(String productName, String color){
		List<String> capacities = productService.findCapacity_ProductByName(productName,color);
		if(capacities != null)
			return new ResponseEntity<>(capacities, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@GetMapping(path = "/filter-products")
	public ResponseEntity<Product> filterProducts(String productName,String color, String capacity){
		Product product = productService.findProductByColorAndCapacity(productName, color, capacity);
		if(product != null)
			return new ResponseEntity<>(product, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	@GetMapping(path = "/specifications")
	public ResponseEntity<SpecificationDTO> getSpecifications(int product_id) {
		Specifications specifications = productService.findSpecByProductId(product_id);
		if (specifications != null) {
			SpecificationDTO listSpecDTO = modelMapper.map(specifications, SpecificationDTO.class);
			return new ResponseEntity<>(listSpecDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

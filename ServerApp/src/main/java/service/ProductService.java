package StoreApp.StoreApp.service;

import java.util.List;

import StoreApp.StoreApp.entity.Specifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import StoreApp.StoreApp.entity.Product;

public interface ProductService {
	List<Product> getAllProduct();
	
	Product saveProduct(Product product);

	Product getProductById(int id);

	Product updateProduct(Product product);

	void deleteProductById(int id);
	
	List<Product> findByProduct_NameContaining(String name);
	
	List<Product> findTop12ProductBestSellers();
	
	List<Product> findTop12ProductNewArrivals();

	Page<Product> findAll(Pageable pageable);

	Page<Product> findByProduct_NameContaining(String name, Pageable pageable);

	Page<Product> findByProduct_NameAndCategory_idContaining(String name, int category_id, Pageable pageable);

	List<Product> findTop4ProductByCategory_id(int name);

	List<String> findColor_ProductByName(String productName,String capacity);
	List<String> findCapacity_ProductByName(String productName, String color);
	Product findProductByColorAndCapacity(String productName, String color, String capacity);

	Specifications findSpecByProductId(int product_id);

	Specifications saveSpec(Specifications specifications);
	
	Product getProductByCart_id(int cart_id);
}

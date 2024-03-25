package StoreApp.StoreApp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import StoreApp.StoreApp.entity.Product;
/**
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
	
	@Query(value="select * from product p where p.product_name like %?1%",nativeQuery = true)
	List<Product> findByProduct_NameContaining(String name);
	
	@Query(value="Select * From product p ORDER BY p.sold DESC LIMIT 12;",nativeQuery = true)
	List<Product> findTop12ProductBestSellers();
	
	@Query(value="Select * From product p ORDER BY p.created_at DESC LIMIT 12;",nativeQuery = true)
	List<Product> findTop12ProductNewArrivals();
	
	Page<Product> findAllByCategory_id(int id, Pageable pageable);
	
	Product findById(int id);

	@Query(value="select * from `applestore`.product where `applestore`.product.product_name like %?1% and `applestore`.product.category_id= ?2",nativeQuery = true)
	Page<Product> findByProduct_NameAndCategory_idContaining(String name, int category_id, Pageable pageable);

	@Query(value="select * from `applestore`.product where `applestore`.product.product_name like %?1%",nativeQuery = true)
	Page<Product> findByProduct_NameContaining(String name, Pageable pageable);
	
	@Query(value="select * from product p where p.category_id = ?1 ORDER BY p.sold DESC LIMIT 4;",nativeQuery = true)
	List<Product> findTop4ProductByCategory_id(int id);
	@Query("SELECT p.color FROM Product p WHERE p.product_Name = :productName and p.capacity =:capacity")
	List<String> findColorsByProductByName(@Param("productName") String productName,@Param("capacity") String capacity);
	@Query("SELECT p.capacity FROM Product p WHERE p.product_Name = :productName and p.color=:color")
	List<String> findCapacityByProductByName(@Param("productName") String productName,@Param("color") String color);
	@Query(value="select  *  from product  p where p.product_name =?1 and p.color =?2 and  p.capacity= ?3",nativeQuery = true)
	Product findProductByColorAndCapacity(String productName,String color, String capacity);


}

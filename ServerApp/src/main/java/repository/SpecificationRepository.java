package StoreApp.StoreApp.repository;

import StoreApp.StoreApp.entity.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpecificationRepository  extends JpaRepository<Specifications,Integer> {

    @Query("SELECT s from Specifications s where s.product.id = :product_id")
    Specifications findByProductId(@Param("product_id") int product_id);

}

package StoreApp.StoreApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import StoreApp.StoreApp.entity.Order;
import StoreApp.StoreApp.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u FROM User u Where u.email = :email")
	User findByEmail(@Param("email") String email);
	User findById(String id);
	
//	@Query(value="select * from user u where u.id = ?1 and u.role = ?2",nativeQuery = true)
	User findByIdAndRole(String id, String role);
	
	void deleteById(String id);
}

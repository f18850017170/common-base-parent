package cn.vonfly.metadata.dao;

import cn.vonfly.metadata.model.Customer;
import com.spatial4j.core.shape.Rectangle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerDao {
	int insert(Customer customer);

	List<Customer> queryByLocation(@Param("encodeLatLon") String encodeLatLon, @Param("rectangle")Rectangle rectangle);
}

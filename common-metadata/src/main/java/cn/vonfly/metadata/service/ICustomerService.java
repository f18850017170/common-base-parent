package cn.vonfly.metadata.service;

import cn.vonfly.metadata.model.Customer;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomerService {
	int insert(Customer customer);

	/**
	 * 根据经纬度查询
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	List<Customer> queryByLocation(Double longitude,Double latitude);

	PageInfo<Customer> pageQueryByLocation(Double longitude, Double latitude);
}

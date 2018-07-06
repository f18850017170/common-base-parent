package cn.vonfly.metadata.service.impl;

import cn.vonfly.common.base.utils.LocationUtils;
import cn.vonfly.metadata.dao.CustomerDao;
import cn.vonfly.metadata.model.Customer;
import cn.vonfly.metadata.model.Label;
import cn.vonfly.metadata.service.ICustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.io.GeohashUtils;
import com.spatial4j.core.shape.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
	@Autowired
	private CustomerDao customerDao;
	@Override
	public int insert(Customer customer) {
		String geoHash = GeohashUtils.encodeLatLon(customer.getLatitude(), customer.getLongitude());
		customer.setGeoHash(geoHash);
		return customerDao.insert(customer);
	}

	@Override
	public List<Customer> queryByLocation(Double longitude, Double latitude) {
		//查询1公里以内，匹配GeoHasHCode前5位
		String encodeLatLon = LocationUtils.encodeLatLon(latitude,longitude,5);
		//根据经纬边缘四角位置获取商铺信息
		Rectangle rectangle = LocationUtils.getRectangle(latitude, longitude, 1);//1公里
		return customerDao.queryByLocation(encodeLatLon,rectangle);
	}

	@Override
	public PageInfo<Customer> pageQueryByLocation(Double longitude, Double latitude) {
		PageHelper.startPage(1,2);
		PageInfo<Customer> result = new PageInfo(queryByLocation(longitude,latitude));
		return result;
	}

}

package cn.vonfly.common.provider;

import cn.vonfly.common.api.ICustomerProvider;
import cn.vonfly.common.base.utils.LocationUtils;
import cn.vonfly.common.dto.common.CustomerVo;
import cn.vonfly.common.dto.common.LabelVo;
import cn.vonfly.common.dto.request.BasePageRequest;
import cn.vonfly.common.dto.response.BasePageResponse;
import cn.vonfly.metadata.model.Customer;
import cn.vonfly.metadata.model.Label;
import cn.vonfly.metadata.service.ICustomerService;
import cn.vonfly.metadata.service.impl.CustomerServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(version = ICustomerProvider.VERSION)
public class CustomerProviderImpl implements ICustomerProvider {
	@Autowired
	private ICustomerService customerService;
	@Override
	public BasePageResponse<CustomerVo> query(BasePageRequest<CustomerVo> request) {
		PageInfo<Customer> pageInfo = customerService.pageQueryByLocation(request.getReq().getLongitude(), request.getReq().getLatitude());
		BasePageResponse<CustomerVo> result = new BasePageResponse<>();
		result.setPageNo(pageInfo.getPageNum());
		result.setPageSize(pageInfo.getPageSize());
		result.setTotal(pageInfo.getTotal());
		List<CustomerVo> data = new ArrayList<>();
		if (!pageInfo.getList().isEmpty()){
			pageInfo.getList().forEach(item->{
				CustomerVo customerVo = new CustomerVo();
				BeanUtils.copyProperties(item,customerVo);
				Double distance = LocationUtils
						.calcDistance(request.getReq().getLatitude(), request.getReq().getLongitude(),
								item.getLatitude(), item.getLongitude());
				customerVo.setDistance(LocationUtils.distanceConvert2Meter(distance));
				data.add(customerVo);
			});
		}
		result.setData(data);
		return result;
	}
}

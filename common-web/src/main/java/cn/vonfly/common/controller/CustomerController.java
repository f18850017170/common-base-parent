package cn.vonfly.common.controller;

import cn.vonfly.common.api.ICustomerProvider;
import cn.vonfly.common.dto.common.CustomerVo;
import cn.vonfly.common.dto.enumcode.SimpleStatus;
import cn.vonfly.common.dto.request.BasePageRequest;
import cn.vonfly.common.dto.response.BasePageResponse;
import cn.vonfly.metadata.model.Customer;
import cn.vonfly.metadata.service.ICustomerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController {
	@Autowired
	private ICustomerService customerService;

	@Reference(version = ICustomerProvider.VERSION)
	private ICustomerProvider customerProvider;

	@RequestMapping("/insert/{name}/{latitude}/{longitude}")
	public boolean insert(@PathVariable("name") String name, @PathVariable("latitude") Double latitude,
			@PathVariable("longitude") Double longitude) {
		Customer customer = new Customer();
		customer.setName(name);
		customer.setLatitude(latitude);
		customer.setLongitude(longitude);
		customer.setStatus(SimpleStatus.ENABLE);
		return customerService.insert(customer) > 0;
	}

	/**
	 *
	 * @param name
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	@RequestMapping("/list/{latitude}/{longitude}")
	public BasePageResponse<CustomerVo> list(@PathVariable("latitude") Double latitude,
			@PathVariable("longitude") Double longitude) {
		CustomerVo customer = new CustomerVo();
		customer.setLatitude(latitude);
		customer.setLongitude(longitude);
		BasePageRequest<CustomerVo> request = new BasePageRequest<>(1, 10, customer);
		BasePageResponse<CustomerVo> response = customerProvider.query(request);
		return response;
	}
}

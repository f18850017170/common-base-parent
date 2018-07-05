package cn.vonfly.common.dto.response;

import java.io.Serializable;
import java.util.List;

public class BasePageResponse <T extends Serializable> implements Serializable {
	private List<T> data;
	private int pageNo;
	private int pageSize;
	private long total;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}
}

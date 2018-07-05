package cn.vonfly.common.dto.request;

import java.io.Serializable;

public class BasePageRequest<T extends Serializable> implements Serializable{
	private int pageNo;
	private int pageSize;
	private T req;

	public BasePageRequest(int pageNo, int pageSize, T req) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.req = req;
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

	public T getReq() {
		return req;
	}

	public void setReq(T req) {
		this.req = req;
	}
}

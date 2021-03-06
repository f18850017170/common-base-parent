package cn.vonfly.metadata.model;

import cn.vonfly.common.dto.enumcode.SimpleStatus;

public class Customer {
	private Long id;
	private SimpleStatus status;
	private String name;
	private Double longitude;
	private Double latitude;
	private String geoHash;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getGeoHash() {
		return geoHash;
	}

	public void setGeoHash(String geoHash) {
		this.geoHash = geoHash;
	}

	public SimpleStatus getStatus() {
		return status;
	}

	public void setStatus(SimpleStatus status) {
		this.status = status;
	}
}

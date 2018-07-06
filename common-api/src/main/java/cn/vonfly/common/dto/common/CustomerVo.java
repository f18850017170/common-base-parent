package cn.vonfly.common.dto.common;

import cn.vonfly.common.dto.enumcode.SimpleStatus;

import java.io.Serializable;

public class CustomerVo implements Serializable {
	private Long id;
	private String name;
	private SimpleStatus status;
	private Double longitude;
	private Double latitude;
	private String geoHash;
	private double distance;

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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public SimpleStatus getStatus() {
		return status;
	}

	public void setStatus(SimpleStatus status) {
		this.status = status;
	}
}

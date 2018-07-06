package cn.vonfly.common.base.utils;

import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.io.GeohashUtils;
import com.spatial4j.core.shape.Rectangle;

import java.math.BigDecimal;

public class LocationUtils {
	/**
	 *
	 * @param latitude
	 * @param longitude
	 * @param precision
	 * @return
	 */
	public static String encodeLatLon(double latitude, double longitude, int precision) {
		return GeohashUtils.encodeLatLon(latitude, longitude, precision);
	}

	/**
	 * 计算矩阵
	 * @param latitude
	 * @param longitude
	 * @param distance
	 * @return
	 */
	public static Rectangle getRectangle(Double latitude, Double longitude, Integer distance) {
		SpatialContext geo = SpatialContext.GEO;
		Rectangle rectangle = geo.getDistCalc().calcBoxByDistFromPt(geo.makePoint(longitude, latitude),
				distance * DistanceUtils.KM_TO_DEG, geo, null);
		return rectangle;
	}

	/**
	 * 计算距离
	 * @param userLatitude
	 * @param userLongitude
	 * @param targetLatitude
	 * @param targetLongitude
	 * @return
	 */
	public static Double calcDistance(double userLatitude, double userLongitude,
			double targetLatitude,double targetLongitude) {
		SpatialContext geo = SpatialContext.GEO;
		Double distance = geo.calcDistance(geo.makePoint(userLongitude, userLatitude),
				geo.makePoint(targetLongitude, targetLatitude));
		return distance;
	}
	/**
	 * 距离转换为米
	 * @param distance
	 * @return
	 */
	private Double distanceConvert2Meter(Double distance) {
		return new BigDecimal(distance * DistanceUtils.DEG_TO_KM * 1000)
				.setScale(2, BigDecimal.ROUND_UP).doubleValue();
	}

}

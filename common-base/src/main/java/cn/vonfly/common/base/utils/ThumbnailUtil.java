package cn.vonfly.common.base.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ThumbnailUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(ThumbnailUtil.class);
	/**
	 * 使用给定的图片生成指定大小的图片
	 */
	public static InputStream generateFixedSizeImage(InputStream inputStream, int width, int height) {
		try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
			Thumbnails.of(inputStream).size(width, height).toOutputStream(outputStream);
			return new ByteArrayInputStream(outputStream.toByteArray());
		} catch (Exception e) {
			LOGGER.error("生成缩略图失败！", e);
			throw new RuntimeException("生成缩略图失败", e);
		} finally {
			try {
				if (null != inputStream){
					inputStream.close();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
}

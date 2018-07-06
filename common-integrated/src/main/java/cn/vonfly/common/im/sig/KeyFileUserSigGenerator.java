package cn.vonfly.common.im.sig;

import cn.vonfly.common.im.utils.TlsSignatureUtils;
import org.joda.time.DateTime;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class KeyFileUserSigGenerator implements UserSigGenerator {
	private static final String SUB_DIRECTORY = "imResource";//秘钥文件目录
	private static final String PRIVATE_KEY_FILE = "private_key";//私钥文件
	private static final String PUBLIC_KEY_FILE = "public_key";//公钥文件
	private static final int EXPIRE_TIME = 3600 * 24 * 3;//提前三天更新用户userSig
	private final String privateKey;
	private final String publicKey;

	public KeyFileUserSigGenerator() {
		try {
			Path path = Paths.get(getClass().getResource("/").toURI()).resolve(SUB_DIRECTORY);
			this.privateKey = readFile(path.resolve(PRIVATE_KEY_FILE));
			this.publicKey = readFile(path.resolve(PUBLIC_KEY_FILE));
		} catch (Exception e) {
			throw new RuntimeException("秘钥文件读取失败");
		}
	}

	@Override
	public UserSigItem generate(long sdkAppId, String identifier) throws Exception {
		TlsSignatureUtils.GenTLSSignatureResult result = TlsSignatureUtils
				.GenTLSSignatureEx(sdkAppId, identifier, privateKey);
		if (null != result) {
			UserSigItem item = new UserSigItem();
			item.setIdentifier(identifier);
			item.setSdkAppId(sdkAppId);
			item.setUserSig(result.urlSig);
			item.setExpireTimestamps(result.getExpireTimestamps());
			item.setInitTimestamps(result.getInitTimestamps());
			return item;
		}
		return null;
	}

	@Override
	public UserSigItem userSigAnalyze(String userSig, long sdkAppId, String identifier) throws Exception {
		TlsSignatureUtils.CheckTLSSignatureResult result = TlsSignatureUtils
				.CheckTLSSignatureEx(userSig, sdkAppId, identifier, publicKey);
		if (null != result) {
			UserSigItem item = new UserSigItem();
			item.setSdkAppId(sdkAppId);
			item.setIdentifier(identifier);
			item.setUserSig(userSig);
			DateTime initTime = new DateTime(result.initTime * 1000);
			item.setInitTimestamps(initTime.toDate().getTime());
			item.setExpireTimestamps(initTime.plusSeconds(result.expireTime).toDate().getTime());
		}
		return null;
	}

	@Override
	public boolean isExpired(UserSigItem item) {
		long expireTimestamps = item.getExpireTimestamps();
		DateTime now = DateTime.now();
		return now.plusSeconds(EXPIRE_TIME).toDate().getTime() >= expireTimestamps;
	}

	/**
	 * 读取文件内容
	 * @param path
	 * @return
	 */
	private String readFile(Path path) {
		try {
			StringBuilder result = new StringBuilder();
			Files.readAllLines(path, Charset.forName("UTF-8")).forEach(item -> {
				result.append(item).append("\n");
			});
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException("读取文件内容异常" + path.toString(), e);
		}
	}
}

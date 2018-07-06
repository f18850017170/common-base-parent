package cn.vonfly.common.im.sig;

/**
 * 默认用户签名管理器
 */
public class DefUserSigManager implements UserSigManager {
	private final static UserSigManager ourInstance = new DefUserSigManager(new KeyFileUserSigGenerator(),
			new MemoryUserSigRepository());
	private UserSigGenerator userSigGenerator;
	private UserSigRepository userSigRepository;

	private DefUserSigManager(UserSigGenerator userSigGenerator,
			UserSigRepository userSigRepository) {
		this.userSigGenerator = userSigGenerator;
		this.userSigRepository = userSigRepository;
	}

	public static UserSigManager getInstance() {
		return ourInstance;
	}

	@Override
	public UserSigItem getOrGenAvailable(long sdkAppId, String identifier) {
		try {
			UserSigItem exist = userSigRepository.getBy(identifier);
			if (null == exist || userSigGenerator.isExpired(exist)) {
				synchronized (new String(sdkAppId + identifier).trim()) {
					if (null == exist || userSigGenerator.isExpired(exist)) {
						exist = userSigGenerator.generate(sdkAppId, identifier);
						userSigRepository.add(identifier, exist);
					}
				}
			}
			return exist;
		} catch (Exception e) {
			throw new RuntimeException("获取用户签名处理异常，sdkAppId=" + sdkAppId + ",identifier=" + identifier, e);
		}
	}
}

package cn.vonfly.common.base.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 互斥操作的同步工具类
 */
public class MutexOptSyncUtils {

	/**
	 * 同步操作
	 * @param wrapper 请求参数包装类
	 * @param function 临界区处理逻辑
	 * @param <T>
	 * @return
	 */
	public <T, R> Result<R> sync(Wrapper<T> wrapper, Function<T, ?> function) {
		return new RedisMutexOptSyncHandler() {
			@Override
			public boolean lockCache(String mutexOptKey, long expiredTime) {
				//TODO 待实现
				return false;
			}

			@Override
			public boolean removeCache(String mutexOptKey) {
				//TODO 待实现
				return false;
			}
		}.handle(wrapper, function);
	}

	/**
	 * redis并发控制
	 */
	public abstract class RedisMutexOptSyncHandler implements MutexOptSyncHandler {
		public abstract boolean lockCache(String mutexOptKey, long expiredTime);

		public abstract boolean removeCache(String mutexOptKey);
		@Override
		public <T, R> Result<R> handle(Wrapper<T> wrapper, Function<T, ?> function) {
			if (null != wrapper && null != function) {
				String mutexOptKey = wrapper.mutexOptKey(wrapper.get());
				if (StringUtils.isNotBlank(mutexOptKey)) {
					boolean lock = false;
					try {
						//TODO 更合理的需要指定超时时间，防止服务崩溃，未清除key的情况
						lock = this.lockCache(mutexOptKey, 10 * 60 * 1000);
						if (lock) {
							//竞争成功，获取到锁，直接执行临界区逻辑
							return new ResultImpl(function.apply(wrapper.get()));
						} else {
							//获取锁失败
							if (wrapper.blocked()) {
								//阻塞策略
								long current = System.currentTimeMillis() + wrapper.timeout();
								Random random = new Random();
								boolean isTimeout = wrapper.timeout() <= 0;
								while (!(lock || isTimeout)) {
									//超时时间内，自旋尝试获取锁
									try {
										TimeUnit.MICROSECONDS.sleep(random.nextInt((int) wrapper.waitSeed()));
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									lock = this.lockCache(mutexOptKey, 10 * 60 * 1000);
									isTimeout = current < System.currentTimeMillis();
								}
								if (lock) {
									//获取了锁
									return new ResultImpl(function.apply(wrapper.get()));
								} else {
									return ResultImpl.timeout();
								}
							} else {
								//非阻塞，直接返回
								return ResultImpl.direct();
							}
						}
					} finally {
						if (lock) {
							this.removeCache(mutexOptKey);
						}
					}
				}
			}
			return null;
		}
	}

	/**
	 * 互斥操作同步处理
	 */
	public interface MutexOptSyncHandler {
		/**
		 * 互斥操作同步处理
		 * @param wrapper 请求参数包装类
		 * @param function 临界区处理逻辑
		 * @param <T>
		 * @return
		 */
		<T, R> Result<R> handle(Wrapper<T> wrapper, Function<T, ?> function);
	}

	public interface Function<T, R> {
		R apply(T t);
	}

	/**
	 * 默认抽象类
	 * @param <T>
	 */
	public static abstract class WrapperImpl<T> implements Wrapper<T> {
		private T t;
		private boolean blocked = true;
		private long timeout = 5 * 60 * 1000;
		private long waitSeed = 100;

		public WrapperImpl(T t) {
			this.t = t;
		}

		public WrapperImpl(T t, boolean blocked) {
			this.t = t;
			this.blocked = blocked;
		}

		@Override
		public T get() {
			return t;
		}

		public abstract String mutexOptKey(T t);

		@Override
		public boolean blocked() {
			return blocked;
		}

		@Override
		public long timeout() {
			return timeout;
		}

		@Override
		public long waitSeed() {
			return waitSeed;
		}

		public void setTimeout(long timeout) {
			this.timeout = timeout;
		}

		public void setWaitSeed(long waitSeed) {
			this.waitSeed = waitSeed;
		}
	}

	/**
	 * 返回结果
	 * @param <R>
	 */
	public static class ResultImpl<R> implements Result<R> {
		private R r;
		private boolean isExclusive;//是否获取了锁
		private boolean isBlocked;//是否阻塞

		public ResultImpl(R r) {
			this.r = r;
			this.isExclusive = true;

		}

		public ResultImpl() {
		}

		/**
		 * 超时
		 * @return
		 */
		public static ResultImpl timeout() {
			ResultImpl result = new ResultImpl();
			result.isExclusive = false;
			result.isBlocked = true;
			return result;
		}

		/**
		 * 非阻塞，未获得锁
		 * @return
		 */
		public static ResultImpl direct() {
			ResultImpl result = new ResultImpl();
			result.isExclusive = false;
			result.isBlocked = false;
			return result;
		}

		@Override
		public R get() {
			return r;
		}

		@Override
		public boolean isExclusive() {
			return isExclusive;
		}

		@Override
		public boolean isBlocked() {
			return isBlocked;
		}
	}

	/**
	 * 请求包装类
	 * @param <T>
	 */
	public interface Wrapper<T> {
		/**
		 * 获取请求参数
		 * @return
		 */
		T get();

		/**
		 * 构建互斥的key
		 * @return
		 */
		String mutexOptKey(T t);

		/**
		 * 当发生并发时，是否阻塞，或者直接返回
		 * @return
		 */
		boolean blocked();

		/**
		 * 超时时间，并发等待的超时时间(非严格)
		 * @return
		 */
		long timeout();

		/**
		 * 自旋等待时的随机因子
		 * @return
		 */
		long waitSeed();
	}

	/**
	 * 返回结果
	 * @param <R>
	 */
	public interface Result<R> {
		/**
		 * 获取返回对象
		 * @return
		 */
		R get();

		/**
		 * 是否独占了锁(阻塞模式下，未获取锁，则为超时)
		 * @return
		 */
		boolean isExclusive();

		/**
		 * 是否阻塞
		 * @return
		 */
		boolean isBlocked();
	}

	/**
	 * 超时异常
	 */
	public static class BlockedTimeoutException extends RuntimeException {
		private String mutexKey;
		private String msg;

		public BlockedTimeoutException(String mutexKey, String message) {
			super(message);
			this.mutexKey = mutexKey;
			this.msg = message;
		}

		public String getMutexKey() {
			return mutexKey;
		}

		public void setMutexKey(String mutexKey) {
			this.mutexKey = mutexKey;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}

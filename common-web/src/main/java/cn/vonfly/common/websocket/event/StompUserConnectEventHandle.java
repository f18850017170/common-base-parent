package cn.vonfly.common.websocket.event;

import cn.vonfly.common.websocket.repo.UserOnlineRepo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.List;
import java.util.Map;

@Component
public class StompUserConnectEventHandle {
	public static final String USER_SEND_ROUTE_KEY = "/single/notify";
	public static final String USER_SUBSCRIBE_DEST = "/user" + USER_SEND_ROUTE_KEY;//用户订阅dest
	public static final String USER_FLAG = "userId";//web socket 初始化连接时传递信息(用户登录后才能连接)
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserOnlineRepo userOnlineRepo;

	/**
	 * 订阅事件
	 * @param event
	 */
	@EventListener(classes = SessionSubscribeEvent.class)
	public void subscribeEvent(SessionSubscribeEvent event) {
		SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
		String sessionId = headerAccessor.getSessionId();
		String destination = headerAccessor.getDestination();
		String userIdStr = headerAccessor.getNativeHeader(USER_FLAG).get(0);
		long userId = Long.parseLong(userIdStr);
		if (StringUtils.equals(USER_SUBSCRIBE_DEST, destination)) {
			userOnlineRepo.put(userId, sessionId);
			logger.info("userId={}，sessionId={}，用户订阅了dest={}", userId, sessionId, destination);
			//TODO 已订阅相关msg-queue此处可以推送消息等等
		}
	}

	/**
	 * 连接事件
	 * @param event
	 */
	@EventListener(classes = SessionConnectedEvent.class)
	public void connectedEvent(SessionConnectedEvent event) {
		try {
			SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
			Message message = (Message) headerAccessor.getHeader(SimpMessageHeaderAccessor.CONNECT_MESSAGE_HEADER);
			Map<String, Object> nativeMessageMap = (Map<String, Object>) message.getHeaders()
					.get(SimpMessageHeaderAccessor.NATIVE_HEADERS);
			//获取建立连接时的初始化参数
			String userId = (String) ((List) nativeMessageMap.get(USER_FLAG)).get(0);
			String sessionId = headerAccessor.getSessionId();
			//TODO 处理 注意:此处仅仅链接，未订阅，无法推送,可以用来判断同个用户是否多个sessionId
			if (null != userOnlineRepo.get(Long.parseLong(userId)) &&
					!userOnlineRepo.get(Long.parseLong(userId)).isEmpty()) {
				logger.info("no first Connect event [sessionId: {};userId: {};size: {}] ", sessionId, userId,
						userOnlineRepo.get(Long.parseLong(userId)).size());
			} else {
				logger.info("first Connect event [sessionId: {}; userId:{}]", sessionId, userId);
			}
		} catch (Exception e) {
			logger.error("Connect event handle fail event={}，e:", event, e);
		}
	}

	/**
	 * 断开连接
	 * @param event
	 */
	@EventListener(classes = SessionDisconnectEvent.class)
	public void disConnectedEvent(SessionDisconnectEvent event) {
		try {
			String sessionId = event.getSessionId();
			//TODO 处理
			userOnlineRepo.remove(sessionId);
			logger.info("disConnect event [sessionId: {}]", sessionId);
		} catch (Exception e) {
			logger.error("disConnect event handle fail event={}，e:", event, e);
		}
	}
}

package cn.vonfly.common.websocket;

import cn.vonfly.common.websocket.repo.UserOnlineRepo;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class UserNotifyController {
	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	@Autowired
	private UserOnlineRepo userOnlineRepo;

	@MessageMapping("/hello")
	public void greeting(Map<String, String> map) throws Exception {
		map.put("ack", "true");
		//也可以使用@send注解发送
		messagingTemplate.convertAndSend("/topic/notify/message", map);

	}

	/**
	 * 发送给具体的人
	 * @param map
	 */
	@MessageMapping("/sendTo")
	public void sendSingle(Map<String, String> map) {
		//发送到具体用户
		String userId = map.get("userId");
		Set<String> sessionIds = userOnlineRepo.get(Long.parseLong(userId));
		sessionIds.forEach(sessionId -> {
			Map<String, String> msg = new HashMap<>();
			msg.put("content", "hello");
			msg.put("time", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
			SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor
					.create(SimpMessageType.MESSAGE);
			headerAccessor.setSessionId(sessionId);
			headerAccessor.setLeaveMutable(true);
			messagingTemplate.convertAndSendToUser(sessionId, "/single/notify", msg, headerAccessor.getMessageHeaders());

		});
	}

	/**
	 * demo界面
	 * @return
	 */
	@RequestMapping("/app/socket/{userId}")
	public String webSocket(Model model, @PathVariable("userId") Long userId) {
		model.addAttribute("userId", userId);
		return "app";
	}
}

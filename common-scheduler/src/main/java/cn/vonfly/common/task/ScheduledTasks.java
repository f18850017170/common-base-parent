package cn.vonfly.common.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 10 * 60 * 1000)
	public void reportCurrentTime() {
		log.info("【fixedRate】The time is now {}", dateFormat.format(new Date()));
	}

	@Scheduled(cron = "${report.task.cron}")
	public void reportCron() {
		log.info("【reportCron】 time is now {}", dateFormat.format(new Date()));
	}
}

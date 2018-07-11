package cn.vonfly.common.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@RestController
public class IndexController {
	@RequestMapping("/current/time")
	public Clock index(){
		return new Clock(LocalTime.now(),LocalDate.now(),LocalDateTime.now(),new Date());
	}
	public static class Clock{
		//global config use objectMapper
//		@JsonFormat(pattern = "HH:mm:ss")
		private LocalTime localTime;
//		@JsonFormat(pattern = "yyyy:MM:dd")
		private LocalDate localDate;
		@JsonFormat(pattern = "yyyy:MM:dd HH:mm:ss")
		private LocalDateTime localDateTime;
		private Date date;

		public Clock(LocalTime localTime, LocalDate localDate, LocalDateTime localDateTime, Date date) {
			this.localTime = localTime;
			this.localDate = localDate;
			this.localDateTime = localDateTime;
			this.date = date;
		}

		public LocalTime getLocalTime() {
			return localTime;
		}

		public void setLocalTime(LocalTime localTime) {
			this.localTime = localTime;
		}

		public LocalDate getLocalDate() {
			return localDate;
		}

		public void setLocalDate(LocalDate localDate) {
			this.localDate = localDate;
		}

		public LocalDateTime getLocalDateTime() {
			return localDateTime;
		}

		public void setLocalDateTime(LocalDateTime localDateTime) {
			this.localDateTime = localDateTime;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
	}
}

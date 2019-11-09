package io.javabrains.springbootstarter.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResponseData<T> {


		private String status;
		private T message;
		private String timestamp;


		public ResponseData(String status, T message){
		    this.status = status;
		    this.message = message;
		    timestamp = formattedTimeStamp();
		}
		
		public String getStatus() {
		    return status;
		}
		
		public void setStatus(String status) {
		    this.status = status;
		}
		
		public T getMessage() {
		    return message;
		}
		
		public void setMessage(T message) {
		    this.message = message;
		}
		
		public String getTimestamp() {
		    return timestamp;
		}
		
		public void setTimestamp(String timestamp) {
		    this.timestamp = timestamp;
		}
		
		
		public  String formattedTimeStamp() {
		    DateFormat format = new SimpleDateFormat("MM/dd/yyyy-hh:mm:ss");
		    return format.format(new Date());
		}
		
}
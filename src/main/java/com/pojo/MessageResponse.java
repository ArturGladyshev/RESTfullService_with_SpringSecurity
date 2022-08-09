package com.pojo;

public class MessageResponse
{
		private String Message;

		public MessageResponse(String message)
		{
				Message = message;
		}

		public String getMessage()
		{
				return Message;
		}

		public void setMessage(String message)
		{
				Message = message;
		}
}

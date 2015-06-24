package com.kruzok.api.exposed.versioned.rev2.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

@XmlRootElement(name = "statusResponse")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = { "status", "errors" })
public class ApiErrorRev2 {

	private HttpStatus status;

	List<ErrorRev2> errors;
	private Throwable throwable;

	public ApiErrorRev2() {

	}

	@XmlElement(name = "httpStatusCode")
	public int getStatus() {
		return status.value();
	}

	@XmlTransient
	@JsonIgnore
	public Throwable getThrowable() {
		return throwable;
	}

	@XmlElementWrapper
	@XmlElement(name = "error")
	public List<ErrorRev2> getErrors() {
		return errors;
	}

	public ErrorRev2 getError(int i) {

		return errors.get(i);
	}

	public void setStatus(int statusCode) {
		this.status = HttpStatus.valueOf(statusCode);
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public void setErrors(List<ErrorRev2> errors) {
		this.errors = errors;
	}

	public void addError(ErrorRev2 error) {
		errors.add(error);
	}

	@XmlRootElement(name = "error")
	public static class ErrorRev2 {

		private int code;
		private String message;

		public ErrorRev2(int code, String message) {

			this.code = code;
			this.message = message;

		}

		public ErrorRev2() {
		}

		public void setCode(int code) {
			this.code = code;

		}

		public void setMessage(String message) {
			this.message = message;
		}

		@XmlElement(name = "errorCode")
		public int getCode() {
			return code;
		}

		@XmlElement(name = "errorMsg")
		public String getMessage() {
			return message;
		}

	}
}

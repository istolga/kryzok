package com.kruzok.api.exposed.versioned.rev1.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.http.HttpStatus;

@XmlRootElement(name = "statusResponse")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ApiErrorRev1 {

	private HttpStatus status;

	List<ErrorRev1> errors;
	private Throwable throwable;

	@XmlTransient
	@JsonIgnore
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
	public List<ErrorRev1> getErrors() {
		return errors;
	}

	public ErrorRev1 getError(int i) {
		if (errors == null) {
			return null;
		}
		return errors.get(i);
	}

	public void setStatus(int statusCode) {
		this.status = HttpStatus.valueOf(statusCode);
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public void setErrors(List<ErrorRev1> errors) {
		this.errors = errors;
	}

	public void addError(ErrorRev1 error) {
		if (errors == null) {
			errors = new ArrayList<ErrorRev1>();
		}
		errors.add(error);
	}

	@XmlRootElement(name = "error")
	public static class ErrorRev1 {

		private int code;
		private String message;
		private HttpStatus status;

		public ErrorRev1(int code, String message) {

			this.code = code;
			this.message = message;

		}

		public ErrorRev1() {
		}

		public void setCode(int code) {
			this.code = code;

		}

		public void setMessage(String message) {
			this.message = message;
		}

		@XmlElement(name = "jigsawStatusCode")
		public int getCode() {
			return code;
		}

		@XmlElement(name = "errorMsg")
		public String getMessage() {
			return message;
		}

		@XmlElement(name = "httpStatusCode")
		public int getStatus() {
			return status.value();
		}

		public void setStatus(int statusCode) {
			this.status = HttpStatus.valueOf(statusCode);
		}

	}
}

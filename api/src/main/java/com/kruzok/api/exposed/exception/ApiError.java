package com.kruzok.api.exposed.exception;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.HttpStatus;

public class ApiError {

	private HttpStatus status;
	private Throwable throwable;
	private List<Error> errors;

	public ApiError() {

	}

	public ApiError(int status, List<Error> errors, Throwable throwable) {
		this.status = HttpStatus.valueOf(status);
		this.throwable = throwable;
		this.errors = errors;

	}

	public int getStatus() {
		return status.value();
	}

	public List<Error> getErrors() {
		return errors;
	}

	public Error getError(int i) {
		if (errors == null) {
			return null;
		}
		return errors.get(i);
	}

	public Throwable getThrowable() {
		return throwable;

	}

	public void setStatus(int statusCode) {
		this.status = HttpStatus.valueOf(statusCode);
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public void addError(Error error) {
		if (errors == null) {
			errors = new ArrayList<Error>();
		}
		errors.add(error);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (getClass() != other.getClass()) {
			return false;
		}
		ApiError castOther = (ApiError) other;
		return new EqualsBuilder() //
				.append(status, castOther.status) //
				.append(errors, castOther.errors) //
				.append(throwable, castOther.throwable) //
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder() //
				.append(status) //
				.append(errors) //
				.append(throwable) //
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this) //
				.append("status", status) //
				.append("errors", errors) //
				.append("throwable", throwable) //
				.toString();
	}

	public static class Error {

		private int code;
		private String message;
		private HttpStatus status;

		public Error(int code, String message, HttpStatus status) {

			this.code = code;
			this.message = message;
			this.status = status;

		}

		public Error(int code, String message) {

			this.code = code;
			this.message = message;

		}

		public Error() {
		}

		public void setCode(int code) {
			this.code = code;

		}

		public void setMessage(String message) {
			this.message = message;
		}

		public void setStatus(int statusCode) {
			this.status = HttpStatus.valueOf(statusCode);

		}

		public int getCode() {
			return code;
		}

		public String getMessage() {
			return message;
		}

		public int getStatus() {
			if (this.status == null) {
				return -1;
			}
			return this.status.value();

		}

		@Override
		public boolean equals(Object other) {
			if (this == other) {
				return true;
			}
			if (other == null) {
				return false;
			}
			if (getClass() != other.getClass()) {
				return false;
			}
			Error castOther = (Error) other;
			return new EqualsBuilder() //
					.append(code, castOther.code) //
					.append(message, castOther.message) //
					.append(status, castOther.status) //
					.isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder() //
					.append(code) //
					.append(message) //
					.append(status) //
					.toHashCode();
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this) //
					.append("code", code) //
					.append("message", message) //
					.append("status", status) //
					.toString();
		}

	}

	public static class Builder {

		private HttpStatus status;
		private Throwable throwable;
		private List<Error> errors;

		public Builder() {
		}

		public Builder status(int statusCode) {
			this.status = HttpStatus.valueOf(statusCode);
			return this;
		}

		public Builder status(HttpStatus status) {
			this.status = status;
			return this;
		}

		public Builder throwable(Throwable throwable) {
			this.throwable = throwable;
			return this;
		}

		public void errors(List<Error> errors) {
			this.errors = errors;

		}

		public ApiError build() {
			if (this.status == null) {
				this.status = HttpStatus.INTERNAL_SERVER_ERROR;
			}

			return new ApiError(this.status.value(), this.errors,
					this.throwable);
		}

	}
}

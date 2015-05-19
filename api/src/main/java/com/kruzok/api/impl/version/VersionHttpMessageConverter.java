package com.kruzok.api.impl.version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;

import com.kruzok.api.exposed.adapter.InputAdapter;
import com.kruzok.api.exposed.adapter.OutputAdapter;
import com.kruzok.api.exposed.context.ApiContextFactory;
import com.kruzok.api.exposed.exception.ApiError;

@Component("versionHttpMessageConverter")
public class VersionHttpMessageConverter<T> implements HttpMessageConverter<T> {

	private static Log log = LogFactory
			.getLog(VersionHttpMessageConverter.class);

	private HttpMessageConverter<T>[] converters = null;

	@Autowired
	private AdapterRegistry adapterRegistry;

	@Autowired
	private ApiContextFactory apiContextFactory;

	@Value("${api.latest.version}")
	private int latestVersion;

	@Resource
	private CustomJaxb2RootElementHttpMessageConverter<T> charHandlerConverter;

	@SuppressWarnings("unchecked")
	public void init(HttpMessageConverter<?>[] messageConverters) {
		this.converters = (HttpMessageConverter<T>[]) messageConverters;
		for (int i = 0; i < converters.length; i++) {
			HttpMessageConverter<T> c = converters[i];
			if (c instanceof Jaxb2RootElementHttpMessageConverter) {
				converters[i] = (HttpMessageConverter<T>) charHandlerConverter;
				break;
			}
		}
	}

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {

		if (adapterRegistry.getSupportedInClasses().contains(clazz))
			return true;

		for (int i = 0; i < converters.length; i++) {
			if (converters[i].canRead(clazz, mediaType)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		if (adapterRegistry.getSupportedOutClasses().contains(clazz)) {
			return true;
		}

		for (int i = 0; i < converters.length; i++) {
			if (converters[i].canWrite(clazz, mediaType)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {

		Set<MediaType> supportedTypes = new HashSet<MediaType>();

		for (int i = 0; i < converters.length; i++) {
			supportedTypes.addAll(converters[i].getSupportedMediaTypes());
		}

		return new ArrayList<MediaType>(supportedTypes);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T read(Class<? extends T> clazz, HttpInputMessage source)
			throws IOException, HttpMessageNotReadableException {

		Object resolved = null;

		InputAdapter<Object, ?> adapter = (InputAdapter<Object, ?>) adapterRegistry
				.getInAdapter(adapterRegistry.generateKey(clazz, getVersion()));

		Class<?> fromClass;
		if (adapter == null) {
			if (log.isDebugEnabled()) {
				log.debug("No Versioned Adapter for " + clazz.getSimpleName());
			}
			fromClass = clazz;
		} else {

			fromClass = adapter.getFromClass();
		}

		if (source.getHeaders() != null
				&& source.getHeaders().containsKey("Content-Type")
				&& source.getHeaders().getContentType() == null) {
			String contentTypes = StringUtils.join(
					source.getHeaders().get("Content-Type"), ",");
			throw new InvalidMediaTypeException(contentTypes,
					"Parsing failure for contentType " + contentTypes);
		}

		MediaType contentType = null;

		try {
			contentType = source == null ? null
					: source.getHeaders() == null ? null : source.getHeaders()
							.getContentType();

			for (int i = 0; i < converters.length; i++) {
				if (converters[i].canRead(fromClass, contentType)) {
					resolved = converters[i].read(
							(Class<? extends T>) fromClass, source);
					break;
				}
			}
		} catch (HttpMessageNotReadableException e) {
			if (source instanceof ServletServerHttpRequest) {
				log.error(
						"Request "
								+ ((ServletServerHttpRequest) source).getURI()
								+ " failed with exception ", e);
			}
			throw new HttpMessageNotReadableException("Parsing failure", e);
		}

		if (resolved == null) {
			log.error("Did not find a converter to read the class "
					+ fromClass.getName() + " for content Type " + contentType);
			throw new HttpMessageNotReadableException(
					"Parsing failure for contentType " + contentType);
		}

		if (adapter == null) {
			return (T) resolved;
		}
		if (!adapter.getFromClass().equals(resolved.getClass())) {
			throw new HttpMessageNotReadableException(
					"Http body is not readable");

		}
		Object adapted = adapter.adapt(resolved);
		return (T) adapted;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(Object output, MediaType mediaType,
			HttpOutputMessage result) throws IOException,
			HttpMessageNotWritableException {

		Object resolved = null;
		OutputAdapter<Object, ?> adapter;

		if ((apiContextFactory == null || apiContextFactory.getApiContext() == null)
				&& output instanceof ApiError) {
			adapter = (OutputAdapter<Object, ?>) adapterRegistry
					.getOutAdapter(adapterRegistry.generateKey(
							output.getClass(), latestVersion));
		} else {
			adapter = (OutputAdapter<Object, ?>) adapterRegistry
					.getOutAdapter(adapterRegistry.generateKey(
							output.getClass(), getVersion()));
		}

		if (adapter == null) {
			if (log.isDebugEnabled()) {
				log.debug("No versioned Adapter for "
						+ output.getClass().getSimpleName());
			}
			resolved = output;
		} else {
			resolved = adapter.adapt(output);
		}

		if (!isSupported(mediaType)) {
			mediaType = MediaType.APPLICATION_XML;
		}
		for (int i = 0; i < converters.length; i++) {

			if (converters[i].canWrite(resolved.getClass(), mediaType)) {
				if (log.isDebugEnabled()) {
					log.debug("Found converter that can write");
				}
				converters[i].write((T) resolved, mediaType, result);
				return;
			}
		}
		log.error("No converter to write the response for the class "
				+ resolved.getClass().getName() + " for mediaType Type "
				+ mediaType);
		throw new HttpMessageNotWritableException(
				"Did not find converter that can write the response for mediaType "
						+ mediaType);

	}

	private boolean isSupported(MediaType mediaType) {
		MediaType temp = mediaType;
		for (MediaType supportedMediaType : getSupportedMediaTypes()) {
			if (temp.getType().equals(supportedMediaType.getType())
					&& temp.getSubtype()
							.equals(supportedMediaType.getSubtype())) {
				if (temp.getCharSet() == null
						&& supportedMediaType.getCharSet() != null) {
					temp = new MediaType(mediaType.getType(),
							mediaType.getSubtype(),
							supportedMediaType.getCharSet());
				}
			}
			if (supportedMediaType.equals(temp)) {
				return true;
			}

		}
		return false;
	}

	private int getVersion() {
		int version;
		if ((apiContextFactory == null || apiContextFactory.getApiContext() == null)) {
			throw new IllegalStateException(
					"apiContext is not initialized properly for this thread");
		}
		version = apiContextFactory.getApiContext().getRequestVersion();
		return version;

	}

	void setApiContextFactory(ApiContextFactory apiContextFactory) {
		this.apiContextFactory = apiContextFactory;

	}

	void setAdapterRegistry(AdapterRegistry adapterRegistry) {
		this.adapterRegistry = adapterRegistry;

	}

	@SuppressWarnings("unchecked")
	void setCustomJaxbConverter(HttpMessageConverter<T> converter) {
		this.charHandlerConverter = (CustomJaxb2RootElementHttpMessageConverter<T>) converter;
	}

}

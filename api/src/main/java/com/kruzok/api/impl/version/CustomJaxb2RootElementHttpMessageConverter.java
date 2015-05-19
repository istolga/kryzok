package com.kruzok.api.impl.version;

import java.io.IOException;
import java.io.Writer;

import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.transform.Result;

import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.utils.XMLChar;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.sun.xml.bind.marshaller.CharacterEscapeHandler;

@Component
public class CustomJaxb2RootElementHttpMessageConverter<T> extends
		Jaxb2RootElementHttpMessageConverter {

	private static Log log = LogFactory
			.getLog(CustomJaxb2RootElementHttpMessageConverter.class);

	@Override
	protected void writeToResult(Object o, HttpHeaders headers, Result result)
			throws IOException {

		try {
			Class<?> clazz = ClassUtils.getUserClass(o);
			Marshaller marshaller = createMarshaller(clazz);

			marshaller
					.setProperty(Marshaller.JAXB_ENCODING, CharEncoding.UTF_8);

			marshaller.setProperty(CharacterEscapeHandler.class.getName(),
					new JaxbOutputCharacterEscapeHandler());

			setCharset(headers.getContentType(), marshaller);
			marshaller.marshal(o, result);
		} catch (MarshalException ex) {
			throw new HttpMessageNotWritableException("Could not marshal [" + o
					+ "]: " + ex.getMessage(), ex);
		} catch (JAXBException ex) {
			throw new HttpMessageConversionException(
					"Could not instantiate JAXBContext: " + ex.getMessage(), ex);
		}
	}

	private void setCharset(MediaType contentType, Marshaller marshaller)
			throws PropertyException {
		if (contentType != null && contentType.getCharSet() != null) {
			marshaller.setProperty(Marshaller.JAXB_ENCODING, contentType
					.getCharSet().name());
		}
	}

	class JaxbOutputCharacterEscapeHandler implements CharacterEscapeHandler {

		@Override
		public void escape(char[] ch, int start, int length, boolean isAttVal,
				Writer out) throws IOException {
			// avoid calling the Writerwrite method too much by assuming
			// that the escaping occurs rarely.
			// profiling revealed that this is faster than the naive code.
			StringBuilder output = new StringBuilder();
			int limit = start + length;
			for (int i = start; i < limit; i++) {
				char c = ch[i];
				if (c == '&' || c == '<' || c == '>' || c == '\r'
						|| (c == '\"' && isAttVal)) {
					start = i + 1;
					switch (ch[i]) {
					case '&':
						output.append("&amp;");
						break;
					case '<':
						output.append("&lt;");
						break;
					case '>':
						output.append("&gt;");
						break;
					case '\"':
						out.write("&quot;");
						break;
					}
				} else if (XMLChar.isValid(c)) {
					output.append(c);
				}
			}

			if (log.isDebugEnabled()) {
				log.debug("escaped output is " + output);
			}
			out.write(output.toString());

		}
	}
}

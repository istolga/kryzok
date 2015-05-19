package com.kruzok.api.impl.version;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletRequestDataBinderFactory;

import com.kruzok.api.exposed.adapter.Adapter;
import com.kruzok.api.exposed.context.ApiContextFactory;

/**
 * In order to support versioning of GET request parameters mapping to command
 * controller, we need to create a custom DataBinder so we can use the
 * {@link Adapter} infrastructure for version conversion.
 * 
 * @see org.springframework.web.servlet.mvc.BaseCommandController#initBinder
 */
@Component
public class VersionHandlerAdapter implements BeanFactoryPostProcessor {

	private static Log log = LogFactory.getLog(VersionHandlerAdapter.class);

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {

		String[] beanNames = beanFactory
				.getBeanNamesForType(RequestMappingHandlerAdapter.class);
		if (beanNames.length > 1) {
			Map<String, RequestMappingHandlerAdapter> beans = beanFactory
					.getBeansOfType(RequestMappingHandlerAdapter.class);
			String message = "";
			Set<Entry<String, RequestMappingHandlerAdapter>> entrySet = beans
					.entrySet();
			for (Entry<String, RequestMappingHandlerAdapter> entry : entrySet) {
				message += " Name=" + entry.getKey();
				message += " Class=" + entry.getValue().getClass();
				message += "\n";
			}

			throw new IllegalStateException(
					"Expect one and only one bean of RequestMappingHandlerAdapter type. Found "
							+ beanNames.length + message);
		} else if (beanNames.length < 1) {
			throw new IllegalStateException(
					"Expect atleast one bean of RequestMappingHandlerAdapter type");

		}
		String beanName = beanNames[0];

		BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
		if (bd.getBeanClassName().equals(
				RequestMappingHandlerAdapter.class.getName())) {
			log.info("Replacing bean " + beanName + "'class"
					+ bd.getBeanClassName() + " with "
					+ VersioningRequestMappingHandlerAdapter.class);
			bd.setBeanClassName(VersioningRequestMappingHandlerAdapter.class
					.getName());
		}
	}

	private static class VersioningRequestMappingHandlerAdapter extends
			RequestMappingHandlerAdapter implements InitializingBean {

		@Autowired
		private AdapterRegistry adapterRegistry;

		@Autowired
		private ApiContextFactory apiContextFactory;

		@Autowired
		private VersionHttpMessageConverter<?> versionHttpMessageConverter;

		@Override
		protected ServletRequestDataBinderFactory createDataBinderFactory(
				List<InvocableHandlerMethod> binderMethods) throws Exception {
			return new VersioningServletRequestDataBinderFactory(binderMethods,
					getWebBindingInitializer(), adapterRegistry,
					apiContextFactory);
		}

		@Override
		public void afterPropertiesSet() {
			versionHttpMessageConverter.init(getMessageConverters().toArray(
					new HttpMessageConverter[getMessageConverters().size()]));
			List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
			converters.add(versionHttpMessageConverter);
			setMessageConverters(converters);
			super.afterPropertiesSet();
		}
	}

	private static class VersioningServletRequestDataBinderFactory extends
			ServletRequestDataBinderFactory {

		private final AdapterRegistry adapterRegistry;
		private final ApiContextFactory apiContextFactory;

		public VersioningServletRequestDataBinderFactory(
				List<InvocableHandlerMethod> binderMethods,
				WebBindingInitializer initializer,
				AdapterRegistry adapterRegistry,
				ApiContextFactory apiContextFactory) {
			super(binderMethods, initializer);
			this.adapterRegistry = adapterRegistry;
			this.apiContextFactory = apiContextFactory;
		}

		@Override
		protected ServletRequestDataBinder createBinderInstance(Object target,
				String objectName, NativeWebRequest request) {
			if (target != null) {
				int version = apiContextFactory.getApiContext()
						.getRequestVersion();
				String classKey = adapterRegistry.generateKey(
						target.getClass(), version);
				@SuppressWarnings("unchecked")
				Adapter<Object, Object> inputAdapter = (Adapter<Object, Object>) adapterRegistry
						.getInAdapter(classKey);
				if (inputAdapter != null) {
					Object versionedTarget = BeanUtils
							.instantiateClass(inputAdapter.getFromClass());
					return new VersionServletRequestDataBinder(inputAdapter,
							versionedTarget, objectName);
				}
			}

			// Fallback to spring's data binder
			return new ExtendedServletRequestDataBinder(target, objectName);
		}
	}
}

package com.kruzok.api.exposed;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.text.WordUtils;

import com.kruzok.api.exposed.converter.FieldConverter;
import com.kruzok.api.exposed.converter.PlainFieldConverter;
import com.kruzok.api.exposed.exception.ConversionException;
import com.kruzok.api.impl.converter.ConverterFactory;
import com.kruzok.api.impl.converter.ConverterFactoryImpl;

public class BeanVersionedConverter {

	private static ConverterFactory converterFactory = new ConverterFactoryImpl();

	public static ConcurrentHashMap<Class, Method[]> classToInternalBeanFieldAnnotatedMethods = new ConcurrentHashMap<Class, Method[]>(
			200, 0.75f, 64);

	public static void toInternalBean(Object input, Object output)
			throws ConversionException {
		if (input == null || output == null) {
			return;
		}

		Method[] declaredMethods = getOrLoadClassAndAnnotationToAnnotatedMethodMap(input
				.getClass());

		for (Method method : declaredMethods) {
			convertAndCopyToInternalBean(input, output, method);
		}
	}

	private static Method[] getAllAnnotatedMethods(
			Class<? extends Object> inputClazz, Class annotationClass) {
		Method[] declaredMethods = inputClazz.getDeclaredMethods();
		List<Method> methods = new ArrayList<Method>();
		for (Method method : declaredMethods) {
			if (method.isAnnotationPresent(annotationClass)) {
				methods.add(method);
			}
		}
		return methods.toArray(new Method[0]);
	}

	private static void convertAndCopyToInternalBean(Object input,
			Object output, Method getterMethodExternalBean)
			throws ConversionException {
		try {
			Method setterMethodInternalBean = getOrLoadGetterRevMethodToSetterIntMethodMap(
					output, getterMethodExternalBean);

			FieldConverter converter = getConverter(getterMethodExternalBean);
			Object inputValue = getterMethodExternalBean.invoke(input);
			Object outputValue = converter.convertInput(inputValue);

			setterMethodInternalBean.invoke(output, outputValue);
		} catch (Exception exception) {
			throw new ConversionException("Conversion error from "
					+ input.getClass() + " to " + output.getClass()
					+ " on method " + getterMethodExternalBean.getName(),
					exception);
		}

	}

	private static ConcurrentHashMap<Method, Method> getterRevMethodToSetterIntMethod = new ConcurrentHashMap<Method, Method>(
			200, 0.75f, 64);

	private static Method getOrLoadGetterRevMethodToSetterIntMethodMap(
			Object output, Method getterMethodExternalBean)
			throws ConversionException {
		Method setterMethodInternalBean = getterRevMethodToSetterIntMethod
				.get(getterMethodExternalBean);
		if (setterMethodInternalBean == null) {
			String fieldName = getFieldName(getterMethodExternalBean);
			Class outputClass = output.getClass();
			Method[] outputMethods = outputClass.getMethods();

			setterMethodInternalBean = findMethodIgnoreCase(outputMethods,
					createNormalizedMethodName("set", fieldName));

			if (setterMethodInternalBean == null) {
				throw new ConversionException("No "
						+ createNormalizedMethodName("set", fieldName)
						+ " method found in " + outputClass);
			}
			getterRevMethodToSetterIntMethod.put(getterMethodExternalBean,
					setterMethodInternalBean);
		}
		return setterMethodInternalBean;
	}

	private static String getFieldName(Method m) {
		InternalBeanField internalBeanMapping = m
				.getAnnotation(InternalBeanField.class);
		String fieldName = internalBeanMapping.value();
		if ("".equals(fieldName)) {
			// get the field name similar to the getter field name
			String methodName = m.getName();
			fieldName = methodName.replaceFirst("get", "").toLowerCase();
		}
		return fieldName;
	}

	private static ConcurrentHashMap<Method, FieldConverter> methodToFieldConverter = new ConcurrentHashMap<Method, FieldConverter>(
			200, 0.75f, 64);

	private static FieldConverter getConverter(Method m) throws Exception {
		FieldConverter converter = methodToFieldConverter.get(m);
		if (converter != null) {
			return converter;
		}
		InternalBeanConverter internalBeanCovertor = m
				.getAnnotation(InternalBeanConverter.class);
		Class converterClass;
		if (internalBeanCovertor == null) {
			Class<?> returnType = m.getReturnType();
			if (returnType.isAnnotationPresent(InternalBeanConverter.class)) {
				InternalBeanConverter classConvertor = returnType
						.getAnnotation(InternalBeanConverter.class);
				converterClass = classConvertor.converter();
			} else {
				converterClass = PlainFieldConverter.class;
			}
		} else {
			converterClass = internalBeanCovertor.converter();
		}
		converter = converterFactory.getInstance(converterClass);
		methodToFieldConverter.put(m, converter);
		return converter;
	}

	public static void fromInternalBean(Object input, Object output)
			throws ConversionException {
		if (input == null || output == null) {
			return;
		}

		Method[] declaredMethods = getOrLoadClassAndAnnotationToAnnotatedMethodMap(output
				.getClass());
		for (Method method : declaredMethods) {
			convertAndCopyFromInternalBean(input, output, method);
		}
	}

	private static Method[] getOrLoadClassAndAnnotationToAnnotatedMethodMap(
			Class<? extends Object> annotatedClass) {
		Method[] declaredMethods = classToInternalBeanFieldAnnotatedMethods
				.get(annotatedClass);
		if (declaredMethods == null) {
			synchronized (annotatedClass) {
				declaredMethods = getAllAnnotatedMethods(annotatedClass,
						InternalBeanField.class);

				classToInternalBeanFieldAnnotatedMethods.put(annotatedClass,
						declaredMethods);
			}
		}
		return declaredMethods;
	}

	private static String createNormalizedMethodName(String prefix,
			String fieldName) {
		return prefix + WordUtils.capitalize(fieldName);
	}

	private static Method findMethodIgnoreCase(Method[] methods, String name) {
		for (Method m : methods) {
			if (m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}

	private static void convertAndCopyFromInternalBean(Object input,
			Object output, Method method) throws ConversionException {
		try {
			Method[] getterInternalAndSetterRev = getOrLoadGetterRevMethodToGetterIntMethodAndSetterRevMethodMap(
					input, output, method);

			Method getterMethodInternalBean = getterInternalAndSetterRev[0];
			Method setterMethodExternalBean = getterInternalAndSetterRev[1];

			FieldConverter converter = getConverter(method);
			Object inputValue = getterMethodInternalBean.invoke(input);
			Object outputValue = converter.convertOutput(inputValue);

			setterMethodExternalBean.invoke(output, outputValue);
		} catch (Exception exception) {
			throw new ConversionException("Conversion error from "
					+ input.getClass() + " to " + output.getClass()
					+ " on method " + method.getName(), exception);
		}

	}

	private static ConcurrentHashMap<Method, Method[]> getterRevMethodToGetterIntMethodAndSetterRevMethod = new ConcurrentHashMap<Method, Method[]>(
			200, 0.75f, 64);

	private static Method[] getOrLoadGetterRevMethodToGetterIntMethodAndSetterRevMethodMap(
			Object input, Object output, Method method)
			throws NoSuchMethodException, ConversionException {
		Method[] getterInternalAndSetterRev = getterRevMethodToGetterIntMethodAndSetterRevMethod
				.get(method);
		if (getterInternalAndSetterRev == null) {
			String fieldName = getFieldName(method);
			String getterMethodName = method.getName();
			String fieldNameExternalBean = getterMethodName.replaceFirst("get",
					"");
			Method setterMethodExternalBean = output.getClass().getMethod(
					createNormalizedMethodName("set", fieldNameExternalBean),
					method.getReturnType());

			Class inputClass = input.getClass();
			Method[] inputMethods = inputClass.getMethods();

			Method getterMethodInternalBean = findMethodIgnoreCase(
					inputMethods, createNormalizedMethodName("get", fieldName));
			if (getterMethodInternalBean == null) {
				throw new ConversionException("No "
						+ createNormalizedMethodName("get", fieldName)
						+ " method found in " + inputClass);
			}
			getterInternalAndSetterRev = new Method[] {
					getterMethodInternalBean, setterMethodExternalBean };
			getterRevMethodToGetterIntMethodAndSetterRevMethod.put(method,
					getterInternalAndSetterRev);

		}
		return getterInternalAndSetterRev;
	}
}

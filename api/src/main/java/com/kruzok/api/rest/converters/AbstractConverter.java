package com.kruzok.api.rest.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.convert.converter.Converter;

public abstract class AbstractConverter<D, M> implements Converter<D, M> {

	public List<M> convert(Collection<D> domains) {
		List<M> result = new ArrayList<M>();
		if (CollectionUtils.isNotEmpty(domains)) {
			for (D domain : domains) {
				result.add(convert(domain));
			}
		}
		return result;
	}
}

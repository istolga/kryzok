package com.kruzok.api.impl.version;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.beans.PropertyValue;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import com.kruzok.api.exposed.adapter.Adapter;
import com.kruzok.api.exposed.exception.UnsupportedParameterException;

/**
 * DataBinder switch the target with versioned target. The final target is
 * switched back after all the binding and adaptation is fnished
 */
public class VersionServletRequestDataBinder extends
		ExtendedServletRequestDataBinder {

	private final Adapter<Object, Object> inputAdapter;
	private Object finalTarget;
	// We can not use ignoreUnknownFields from DataBinder.java because it will
	// be global changes.
	private boolean ignoreUnknownFieldsForMe = false;

	private final static Set<String> whiteListedFields = new HashSet<String>() {

		{
			add("version");
			add("orgid");
			add("userid");
			add("username");
			add("password");
		}
	};

	public VersionServletRequestDataBinder(
			final Adapter<Object, Object> adapter,
			final Object versionedTarget, final String objectName) {
		super(versionedTarget, objectName);
		this.inputAdapter = adapter;
	}
	
	@Override
	public void bind(ServletRequest request) {
		super.bind(request);
		Object versionedTarget = super.getTarget();
		this.finalTarget = inputAdapter.adapt(versionedTarget);
	}

	@Override
	protected void checkAllowedFields(MutablePropertyValues mpvs) {
		PropertyValue[] pvs = mpvs.getPropertyValues();
		for (PropertyValue pv : pvs) {
			String name = pv.getName();
			if (whiteListedFields.contains(StringUtils.lowerCase(name))) {
				continue;
			}

			String field = PropertyAccessorUtils.canonicalPropertyName(name);
			if (ignoreUnknownFieldsForMe) {
				if (!isAllowed(field)) {
					mpvs.removePropertyValue(pv);
					getBindingResult().recordSuppressedField(field);
					if (logger.isDebugEnabled()) {
						logger.debug("Field ["
								+ field
								+ "] has been removed from PropertyValues "
								+ "and will not be bound, because it has not been found in the list of allowed fields");
					}
				}
			} else {
				if (name.matches(".+\\[.+\\]")) {
					// Map
					name = StringUtils.substring(name, 0, name.indexOf("["));

				}
				Class propertyType = getPropertyAccessor()
						.getPropertyType(name);
				if (propertyType == null) {
					throw new UnsupportedParameterException("Field [" + field
							+ "] is not supported");
				}
			}
		}
	}

	/**
	 * @return the returned object is based on the readiness of the binding. If
	 *         this binder has not fully binded, it returns the versioned object
	 *         (BeanRevision) so that Spring binding logic can be safely reused.
	 *         After binding is done, this binder will adapt the revision bean
	 *         into the target object of the controller method, and always
	 *         return the target object afterwards.
	 */
	@Override
	public Object getTarget() {
		return this.finalTarget == null ? super.getTarget() : finalTarget;
	}

	public boolean isIgnoreUnknownFieldsForMe() {
		return ignoreUnknownFieldsForMe;
	}

	public void setIgnoreUnknownFieldsForMe(boolean ignoreUnknownFieldsForMe) {
		this.ignoreUnknownFieldsForMe = ignoreUnknownFieldsForMe;
	}
}

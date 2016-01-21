package exceptions;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.cglib.core.ReflectUtils;

import com.google.common.base.Joiner;

import entity.AttendRecord;

public class RemovingIntegrityViolationException extends ConflictException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemovingIntegrityViolationException(Class<?> entity) {
		super(getDependencies(entity), entity.getSimpleName());
	}

	private static String getDependencies(Class<?> entity) {
		PropertyDescriptor[] properties = ReflectUtils
				.getBeanGetters(AttendRecord.class);

		List<String> dependencies = new ArrayList<String>();

		for (PropertyDescriptor p : properties) {
			if (p.getPropertyType().equals(Set.class)) {
				dependencies.add(p.getName());
			}
		}
		String joiner = Joiner.on(", ").join(dependencies);
		return joiner;
	}
}

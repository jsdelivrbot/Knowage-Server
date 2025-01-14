package it.eng.spagobi.tools.dataset.graph;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import it.eng.spagobi.tools.dataset.common.datawriter.JSONDataWriter;
import it.eng.spagobi.utilities.assertion.Assert;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class Tuple {

	private static final String PREFIX = "(";
	private static final String SUFFIX = ")";
	private static final String VALUE_DELIMITER = ",";
	private static final String STRING_DELIMITER = "'";

	private final List<Object> values;

	public Tuple() {
		values = new ArrayList<Object>();
	}

	public Tuple(int n) {
		values = new ArrayList<Object>(n);
	}

	public Tuple(List<?> values) {
		this.values = new ArrayList<>(values);
	}

	@JsonCreator
	public Tuple(String values) {
		this(Arrays.asList(StringUtils.split(values, VALUE_DELIMITER)));
	}

	public void add(Object value) {
		values.add(value);
	}

	public void add(Object value, int position) {
		values.add(position, value);
	}

	public Object get(int position) {
		return values.get(position);
	}

	public List<?> getValues() {
		return values;
	}

	public int dimension() {
		return values.size();
	}

	@Override
	@JsonValue
	public String toString() {
		return toString(PREFIX, SUFFIX, VALUE_DELIMITER, STRING_DELIMITER);
	}

	public String toString(String prefix, String suffix, String stringDelimiter) {
		return toString(prefix, suffix, VALUE_DELIMITER, stringDelimiter);
	}

	public String toString(String prefix, String suffix, String valueDelimiter, String stringDelimiter) {
		Assert.assertNotNull(prefix, "Prefix must be provided");
		Assert.assertNotNull(suffix, "Suffix must be provided");
		Assert.assertNotNull(valueDelimiter, "Value delimiter must be provided");
		Assert.assertNotNull(stringDelimiter, "String delimiter must be provided");

		StringBuilder tuple = new StringBuilder();
		tuple.append(prefix);
		for (int i = 0; i < values.size(); i++) {
			if (i != 0) {
				tuple.append(valueDelimiter);
			}
			tuple.append(stringDelimiter);
			tuple.append((values.get(i) == null ? null : getProperValueString(values.get(i))));
			tuple.append(stringDelimiter);
		}
		tuple.append(suffix);
		return tuple.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tuple))
			return false;
		Tuple other = (Tuple) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	private static String getProperValueString(Object item) {
		if (Date.class.isAssignableFrom(item.getClass())) {
			SimpleDateFormat formatter = new SimpleDateFormat(JSONDataWriter.CACHE_DATE_TIME_FORMAT);
			return formatter.format(item);
		} else {
			return item.toString();
		}
	}
}

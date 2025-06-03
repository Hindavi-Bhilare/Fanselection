
package com.velotech.fanselection.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class JavaUtil {

	/**
	 * @param searchValue
	 *            A value which to be converted to double.
	 *
	 * @return Returns <code>double value</code> if it is convertable,
	 *         <code>0</code> otherwise.
	 */
	public static double getDoubleValue(final Object searchValue) {

		String s = searchValue.toString().trim();
		double value;
		if (s.length() == 0) {
			value = 0;
		} else {
			try {
				value = Double.valueOf(s);
			} catch (NumberFormatException e) {
				value = 0;
			}
		}
		return value;
	}

	/**
	 * @param searchValue
	 *            A value which to be converted to int.
	 *
	 * @return Returns <code>int value</code> if it is convertable,
	 *         <code>0</code> otherwise.
	 */
	public static int getIntegerValue(final Object searchValue) {

		String s = searchValue.toString().trim();
		int value;
		if (s.length() == 0) {
			value = 0;
		} else {
			try {
				value = Integer.valueOf(s);
			} catch (NumberFormatException e) {
				value = 0;
			}
		}
		return value;
	}

	/**
	 * @param searchValue
	 *            A value which to be converted to boolean.
	 *
	 * @return Returns <code>boolean value</code> if it is convertable,
	 *         <code>false</code> otherwise.
	 */
	public static boolean getBooleanValue(final Object searchValue) {

		String s = searchValue.toString().trim();
		boolean value = false;
		if (s.length() == 0) {
			value = false;
		} else {
			try {
				value = Boolean.valueOf(s);
			} catch (NumberFormatException e) {
				value = false;
			}
		}
		return value;
	}

	/**
	 * 
	 * @param className
	 *            class name should be fully qualified (with package)
	 * @return Returns Class if the class is exist else returns null
	 */
	public static Class getClass(final String className) {

		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks whether the specified class contains a field matching the
	 * specified name.
	 *
	 * @param clazz
	 *            The class to check.
	 * @param fieldName
	 *            The field name.
	 *
	 * @return Returns <code>true</code> if the class contains a field for the
	 *         specified name, <code>
	 *         false</code> otherwise.
	 */
	public static boolean containsField(final Class<?> clazz, final String fieldName) {

		try {
			clazz.getDeclaredField(fieldName);
			return true;
		} catch (NoSuchFieldException e) {
			return false;
		}
	}

	/**
	 * get the specified field form the specified class.
	 *
	 * @param clazz
	 *            The class to check.
	 * @param fieldName
	 *            The field name.
	 *
	 * @return Returns <code>field</code> if the class contains a field for the
	 *         specified name, <code>
	 *         null</code> otherwise.
	 */
	public static Field getField(final Class<?> clazz, final String fieldName) {

		try {
			Field field = clazz.getDeclaredField(fieldName);
			return field;
		} catch (NoSuchFieldException e) {
			return null;
		}
	}

	/**
	 * get the all fields form the specified class.
	 *
	 * @param clazz
	 *            The class to check.
	 *
	 * @return Returns <code>fields</code> if the class contains Field[],
	 *         <code>Field[0]</code> otherwise.
	 */
	public static Field[] getFields(final Class<?> clazz) {

		try {
			Field[] fields = clazz.getDeclaredFields();
			return fields;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the all private fields form the specified class.
	 *
	 * @param clazz
	 *            The class to check.
	 *
	 * @return Returns <code>fields</code> if the class contains Field[],
	 *         <code>Field[0]</code> otherwise.
	 */
	public static List<Field> getPrivateFields(final Class<?> clazz) {

		try {
			Field[] fields = clazz.getDeclaredFields();
			List<Field> fieldList = Arrays.asList(fields).stream().filter(field -> Modifier.isPrivate(field.getModifiers()))
					.collect(Collectors.toList());
			return fieldList;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * get the specified fields type form the specified field.
	 *
	 * @param clazz
	 *            The class to check.
	 * @param fieldName
	 *            The field name.
	 *
	 * @return Returns <code>field type</code> if the class contains a field for
	 *         the specified name, <code>
	 *         null</code> otherwise.
	 */
	public static String getFieldType(final Class<?> clazz, final String fieldName) {

		try {
			boolean isParent = fieldName.contains(".");
			if (isParent)
				return "parent";
			else {
				Field field = clazz.getDeclaredField(fieldName);
				return field.getType().getSimpleName();
			}

		} catch (NoSuchFieldException e) {
			return null;
		}
	}

	public static String getFieldsClassType(final Class<?> clazz, final String fieldName) {

		try {
			Field field = clazz.getDeclaredField(fieldName);
			return field.getType().getName();

		} catch (NoSuchFieldException e) {
			return null;
		}
	}

	public static Object getValueOfType(final String fieldType, final Object object) {

		switch (fieldType) {
		case "String":
		case "Short":
		case "short":
			return object;
		case "boolean":
		case "Boolean":
			return getBooleanValue(object);
		case "int":
		case "Integer":
			return getIntegerValue(object);
		case "double":
		case "Double":
			return getDoubleValue(object);
		default:
			return null;
		}
	}
}

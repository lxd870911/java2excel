package com.github.johnsonmoon.java2excel.util;

import org.junit.Test;
import com.github.johnsonmoon.java2excel.entity.BaseTypes;
import com.github.johnsonmoon.java2excel.entity.School;
import com.github.johnsonmoon.java2excel.entity.Student;
import com.github.johnsonmoon.java2excel.entity.Teacher;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by xuyh at 2018/1/3 11:34.
 */
public class ReflectionUtilsTest {
	@Test
	public void testClassNames() {
		Class<?> clazz = Teacher.class;

		System.out.println(ReflectionUtils.getClassNameShort(clazz));
		System.out.println(ReflectionUtils.getClassNameEntire(clazz));

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			System.out.println(ReflectionUtils.getFieldTypeNameShort(field));
			System.out.println(ReflectionUtils.getFieldTypeNameEntire(field));
		}
	}

	@Test
	public void testSetFieldValue() {
		Student student = new Student();
		student.setName("Johnson");

		System.out.println(ReflectionUtils.getFieldValue(student, "name"));
		System.out.println(ReflectionUtils.setFieldValue(student, "name", "John"));
		System.out.println(student.getName());
	}

	@Test
	public void testGetClassType() throws Exception {
		String className1 = String.class.getName();
		String className2 = "String";
		String className3 = "java.lang.Float";

		try {
			Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass(className1);
			System.out.println(clazz.getName());
		} catch (Exception e) {
			System.out.println("class not found");
		}

		try {
			Class<?> clazz = Class.forName(className2);
			System.out.println(clazz.getName());
		} catch (Exception e) {
			System.out.println("class not found");
		}

		try {
			Class<?> clazz = Class.forName(className3);
			System.out.println(clazz.getName());
		} catch (Exception e) {
			System.out.println("class not found");
		}
	}

	@Test
	public void testGetBaseType() throws Exception {
		String className = "double";
		String value = "12.36";

		try {
			Class<?> clazz = ReflectionUtils.getClassByName(className);
			double var = (double) ValueUtils.parseValue(value, clazz);
			System.out.println(var);
		} catch (Exception e) {
			System.out.println("class not found");
		}
	}

	@Test
	public void testGetBaseTypeNames() throws Exception {
		//Integer, Float, Byte, Double, Boolean, Character, Long, Short
		for (Field field : ReflectionUtils.getFieldsAll(BaseTypes.class)) {
			String nameEntire = ReflectionUtils.getFieldTypeNameEntire(field);
			System.out.println(nameEntire);

			Class<?> tClass = ReflectionUtils.getClassByName(nameEntire);
			System.out.println(tClass.getName());
		}
	}

	@Test
	public void testNewObjectInstance() throws Exception {
		Class<?> clazz = Teacher.class;
		Object teacher = clazz.newInstance();

		List<Field> fields = ReflectionUtils.getFieldsAll(clazz);
		for (Field field : fields) {
			if (field.getName().equals("id")) {
				ReflectionUtils.setFieldValue(teacher, field.getName(), "testID");
			}

			if (field.getName().equals("name")) {
				ReflectionUtils.setFieldValue(teacher, field.getName(), "testName");
			}

			if (field.getName().equals("phoneNumber")) {
				ReflectionUtils.setFieldValue(teacher, field.getName(), "testPhoneNumber");
			}
		}

		System.out.println(JsonUtils.obj2JsonStr(teacher));
	}

	@Test
	public void testNewObjectInstanceWithConstructor() throws Exception {
		Class<?> clazz = Teacher.class;
		Object teacher = null;

		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			if (constructor.getParameterCount() == 0)
				teacher = constructor.newInstance();
		}

		if (teacher == null)
			return;

		List<Field> fields = ReflectionUtils.getFieldsAll(clazz);
		for (Field field : fields) {
			if (field.getName().equals("id")) {
				ReflectionUtils.setFieldValue(teacher, field.getName(), "testID");
			}

			if (field.getName().equals("name")) {
				ReflectionUtils.setFieldValue(teacher, field.getName(), "testName");
			}

			if (field.getName().equals("phoneNumber")) {
				ReflectionUtils.setFieldValue(teacher, field.getName(), "testPhoneNumber");
			}
		}

		System.out.println(JsonUtils.obj2JsonStr(teacher));
	}

	@Test
	public void testNewObjectInstanceUseUtil() throws Exception {
		System.out.println(JsonUtils.obj2JsonStr(ReflectionUtils.newObjectInstance(School.class)));

		System.out
				.println(JsonUtils.obj2JsonStr(ReflectionUtils.newObjectInstance(School.class, "nameTest", "addressTest")));
	}
}

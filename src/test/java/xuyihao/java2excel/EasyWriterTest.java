package xuyihao.java2excel;

import org.junit.Assert;
import org.junit.Test;
import xuyihao.java2excel.entity.Student;
import xuyihao.java2excel.entity.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by xuyh at 2018/1/2 14:00.
 */
public class EasyWriterTest {
	@Test
	public void testExcelTemplate() {
		EasyWriter easyWriter = new EasyWriter();
		easyWriter.setTemplateLanguage("zh_CN");
		Assert.assertTrue(easyWriter.writeExcelTemplate(Student.class, "D:\\studentTemplate.xlsx"));
	}

	@Test
	public void testExcelData() {
		EasyWriter easyWriter = new EasyWriter();
		easyWriter.setTemplateLanguage("zh_CN");

		List<Student> students = new ArrayList<>();
		for (int i = 0; i < 16; i++) {
			Student student = new Student();
			student.setName("name" + (i + 1));
			student.setEmail("email" + (i + 1));
			student.setNumber("number" + (i + 1));
			student.setPhoneNumber("phoneNumber" + (i + 1));
			student.setAddresses(Arrays.asList("address0" + (i + 1), "address1" + (i + 1)));
			students.add(student);
		}

		Assert.assertTrue(easyWriter.writeExcelDataAll(students, "D:\\studentData.xlsx"));
	}

	@Test
	public void testExcelTemplateWithAnnotation() {
		EasyWriter easyWriter = new EasyWriter();
		easyWriter.setTemplateLanguage("zh_CN");
		Assert.assertTrue(easyWriter.writeExcelTemplate(Teacher.class, "D:\\teacherTemplate.xlsx"));
	}

	@Test
	public void testExcelDataWithAnnotation() {
		EasyWriter easyWriter = new EasyWriter();
		easyWriter.setTemplateLanguage("zh_CN");
		List<Teacher> teachers = new ArrayList<>();
		for (int i = 0; i < 16; i++) {
			Teacher teacher = new Teacher();
			teacher.setId("testId" + i);
			teacher.setName("testName" + i);
			teacher.setEmail("testEmail@xxx.com");
			teacher.setPhoneNumber("111111111" + i);
			teacher.setAddress(Arrays.asList("testAddress1" + i, "testAddress2" + i, "testAddress3" + i));
			teachers.add(teacher);
		}
		Assert.assertTrue(easyWriter.writeExcelDataAll(teachers, "D:\\teacherData.xlsx"));
	}
}
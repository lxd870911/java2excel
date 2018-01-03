package xuyihao.java2excel.core.operation;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import xuyihao.java2excel.core.entity.model.Attribute;
import xuyihao.java2excel.core.entity.model.Template;
import xuyihao.java2excel.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuyh at 2017/12/28 17:48.
 */
public class ExportTest {
	@Test
	public void test() throws Exception {
		Workbook workbook = new XSSFWorkbook();
		//template
		Template template = new Template();
		template.setName("学生统计");
		template.addAttribute(new Attribute("name", "学生名称", "String", "文本", null, null));
		template.addAttribute(new Attribute("number", "学生学号", "String", "文本", null, null));
		template.addAttribute(new Attribute("phone", "学生电话", "String", "文本", null, null));
		template.addAttribute(new Attribute("email", "学生电邮", "String", "文本", null, null));
		//data
		List<Template> datas = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Template data = new Template();
			data.setName(template.getName());
			data.setAttributes(template.getAttributes());

			data.addAttrValue("学生" + i);
			data.addAttrValue("2017000" + i);
			data.addAttrValue("1500000000" + i);
			data.addAttrValue(i + "000@web.com");

			datas.add(data);
		}
		System.out.println(String.format("CreateResult : [%s]", Export.createExcel(workbook, 0, template, "zh_CN")));
		System.out.println(String.format("InsertResult : [%s]", Export.insertExcelData(workbook, 0, 6, datas)));

		//write file
		String filePath = "C:\\Users\\johnson\\Desktop\\test.xlsx";
		FileUtils.createFileDE(filePath);
		Common.writeFileToDisk(workbook, new File(filePath));
		Common.closeWorkbook(workbook);
	}
}

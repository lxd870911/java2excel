package xuyihao.java2excel.core;

import org.junit.Assert;
import org.junit.Test;
import xuyihao.java2excel.core.entity.Common.CellStyle;
import xuyihao.java2excel.core.entity.free.CellData;

import java.util.*;

/**
 * Created by xuyh at 2018/3/20 17:22.
 */
public class FreeEditorTest {
	@Test
	public void testWrite() {
		FreeEditor editor1 = new FreeEditor("C:\\Users\\Johnson\\Desktop\\FreeEditor.xlsx");

		Assert.assertTrue(editor1.createExcel(Arrays.asList(
				new CellData(0, 0, "A", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(0, 1, "B", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(0, 2, "C", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(0, 3, "D", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(0, 4, "E", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(0, 5, "F", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(0, 6, "G", CellStyle.CELL_STYLE_TYPE_VALUE)), 0, "Sheet0"));

		Assert.assertTrue(editor1.writeExcelData(Arrays.asList(
				new CellData(1, 0, "A", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(1, 1, "B", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(1, 2, "C", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(1, 3, "D", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(1, 4, "E", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(1, 5, "F", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(1, 6, "G", CellStyle.CELL_STYLE_TYPE_VALUE)), 0));

		Map<Integer, Object> map = new HashMap<>();
		map.put(0, "A");
		map.put(1, "B");
		map.put(2, "C");
		map.put(3, "D");
		map.put(4, "E");
		map.put(5, "F");
		map.put(6, "G");
		Assert.assertTrue(editor1.writeExcelDataDirectly(map, 0, 2));

		List<Map<Integer, Object>> mapList = Arrays.asList(
				map, map, map, map);
		Assert.assertTrue(editor1.writeExcelDataDirectly(mapList, 0, 3));

		for (int i = 0; i < 7; i++) {
			Assert.assertTrue(editor1.setExcelColumnWidth(0, i, 4800));
		}

		Assert.assertTrue(editor1.flush());
		Assert.assertTrue(editor1.close());
	}

	@Test
	public void testWriteFlush2() {
		FreeEditor editor1 = new FreeEditor("C:\\Users\\Johnson\\Desktop\\FreeEditor.xlsx");

		Assert.assertTrue(editor1.createExcel(Arrays.asList(
				new CellData(10, 0, "A", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(10, 1, "B", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(10, 2, "C", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(10, 3, "D", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(10, 4, "E", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(10, 5, "F", CellStyle.CELL_STYLE_TYPE_VALUE),
				new CellData(10, 6, "G", CellStyle.CELL_STYLE_TYPE_VALUE)), 0, "Sheet0"));

		for (int i = 0; i < 7; i++) {
			Assert.assertTrue(editor1.setExcelColumnWidth(0, i, 4800));
		}

		Assert.assertTrue(editor1.flush("C:\\Users\\Johnson\\Desktop\\FreeEditor2.xlsx"));
		Assert.assertTrue(editor1.close());
	}

	@Test
	public void testRead() {
		FreeEditor editor = new FreeEditor("C:\\Users\\Johnson\\Desktop\\FreeEditor.xlsx");
		CellData data = editor.readExcelCellData(0, 0, 0);
		Assert.assertNotNull(data);
		System.out.println(data);
		String str = editor.readExcelData(0, 0, 0);
		Assert.assertNotNull(str);
		System.out.println(str);
	}
}

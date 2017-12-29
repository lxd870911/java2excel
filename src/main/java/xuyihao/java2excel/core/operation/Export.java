package xuyihao.java2excel.core.operation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xuyihao.java2excel.core.entity.model.Template;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.*;

/**
 * Created by Xuyh at 2016/07/22 上午 11:36.
 * <p>
 * Excel文件导出操作工具类
 */
public class Export {
    private static Logger logger = LoggerFactory.getLogger(Export.class);

    /**
     * 创建表格
     *
     * @param workbook 表格
     * @param sheetNum 工作簿编号
     * @param template 数据模型
     * @return true 成功, false 失败
     */
    public static boolean createExcel(final Workbook workbook, int sheetNum, Template template) {
        boolean flag;
        try {
            if (workbook == null)
                return false;
            if (template == null)
                return false;
            Sheet sheet = workbook.createSheet(template.getName());
            workbook.setSheetOrder(template.getName(), sheetNum);
            // 总列数
            int columnSize;
            int attrValueSize = template.getAttributes().size();
            columnSize = attrValueSize + 1;
            // 设置属性列宽
            for (int i = 0; i < columnSize; i++) {
                sheet.setColumnWidth(i + 1, 4800);
            }
            // 隐藏列
            sheet.setColumnHidden(1, true);
            // 隐藏行（设置行高为零）
            sheet.createRow(3).setZeroHeight(true);
            // 合并单元格
            CellRangeAddress cellRangeAddress1 = new CellRangeAddress(0, 1, 0, 0);
            CellRangeAddress cellRangeAddress3 = new CellRangeAddress(0, 1, 1, columnSize);
            sheet.addMergedRegion(cellRangeAddress1);
            sheet.addMergedRegion(cellRangeAddress3);
            // 写入固定数据
            Common.insertCellValue(sheet, 0, 0, template.getName(), Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_HEADER_HIDE));
            Common.insertCellValue(sheet, 1, 0, template.getName(), Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_TITLE));
            Common.insertCellValue(sheet, 0, 2, "字段", Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_ROW_HEADER));
            Common.insertCellValue(sheet, 0, 3, "请勿编辑此行", Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_ROW_HEADER_GRAY));
            Common.insertCellValue(sheet, 0, 4, "数据格式", Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_ROW_HEADER_GRAY));
            Common.insertCellValue(sheet, 0, 5, "默认值", Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_ROW_HEADER));
            Common.insertCellValue(sheet, 0, 6, "数据", Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_ROW_HEADER_TOP_ALIGN));
            Common.insertCellValue(sheet, 1, 2, "数据标识", Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_COLUMN_HEADER));
            Common.insertCellValue(sheet, 1, 3, "数据标识", Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_HEADER_HIDE_WHITE));
            for (int i = 0; i < template.getAttributes().size(); i++) {
                String label;
                if (template.getAttributes().get(i).getUnit() == null
                        || template.getAttributes().get(i).getUnit().equals("")) {
                    label = template.getAttributes().get(i).getAttrName();
                } else {
                    label = template.getAttributes().get(i).getAttrName() + "("
                            + template.getAttributes().get(i).getUnit() + ")";
                }
                String attrInfo = template.getAttributes().get(i).getAttrCode()
                        + "&&" + template.getAttributes().get(i).getAttrName()
                        + "&&" + template.getAttributes().get(i).getAttrType()
                        + "&&" + template.getAttributes().get(i).getFormatInfo()
                        + "&&" + template.getAttributes().get(i).getDefaultValue()
                        + "&&" + template.getAttributes().get(i).getUnit();
                String formatInfo = template.getAttributes().get(i).getFormatInfo();
                String defaultValue = template.getAttributes().get(i).getDefaultValue();
                Common.insertCellValue(sheet, i + 2, 2, label, Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_ROW_HEADER));// 字段名
                Common.insertCellValue(sheet, i + 2, 3, attrInfo, Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_ROW_HEADER_GRAY));
                Common.insertCellValue(sheet, i + 2, 4, formatInfo, Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_ROW_HEADER));
                Common.insertCellValue(sheet, i + 2, 5, defaultValue, Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_COLUMN_HEADER));// 默认值
            }
            flag = true;
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            flag = false;
        }
        return flag;
    }

    /**
     * 批量插入数据
     *
     * @param workbook      工作簿
     * @param sheetNum      表格编号
     * @param startRowNum   写入数据的起始行（第一次写入应当从第七行即startRowNum=6开始）
     * @param templatesList 数据列表
     * @return true 成功, false 失败
     */
    public static boolean insertExcelData(final Workbook workbook, int sheetNum, int startRowNum, List<Template> templatesList) {
        boolean flag;
        try {
            if (templatesList == null || templatesList.isEmpty())
                return false;
            if (workbook == null) {
                return false;
            }
            Sheet sheet = workbook.getSheetAt(sheetNum);
            if (sheet == null) {
                return false;
            }
            String identifyString = Common.getCellValue(sheet, 0, 0);
            if (!identifyString.equals(templatesList.get(0).getName())) {
                return false;
            }

            if (startRowNum < 6) {
                logger.warn("Data insert must above row 7(6+1)");
            }
            for (int j = 0; j < templatesList.size(); j++) {
                for (int i = 0; i < templatesList.get(j).getAttrValues().size(); i++) {
                    Common.insertCellValue(sheet, i + 2, j + startRowNum, templatesList.get(j).getAttrValues().get(i),
                            Common.createCellStyle(workbook, Common.CELL_STYLE_TYPE_VALUE));
                }
            }
            flag = true;
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            flag = false;
        }
        return flag;
    }
}
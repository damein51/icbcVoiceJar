package com.damein;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel操作类
 *
 * @author liushiyang
 */
@Slf4j
public class ExcelUtil {

    /**
     * 总行数
     */
    private int totalRows = 0;
    /**
     * 总列数
     */
    private int totalCells = 0;
    /**
     * 错误信息
     */
    private String errorInfo;

    /**
     * @描述：得到总行数
     * @参数：@return
     * @返回值：int
     */
    public int getTotalRows() {
        return totalRows;
    }

    /**
     * @描述：得到总列数
     * @参数：@return
     * @返回值：int
     */
    public int getTotalCells() {
        return totalCells;
    }

    /**
     * @描述：得到错误信息
     * @参数：@return
     * @返回值：String
     */
    public String getErrorInfo() {
        return errorInfo;
    }

    /**
     * @描述：验证excel文件
     * @参数：@param filePath　文件完整路径
     * @参数：@return
     * @返回值：boolean
     */
    public boolean validateExcel(String filePath) {
        /** 检查文件名是否为空或者是否是Excel格式的文件 */
        if (filePath == null
                || !(WDWUtil.isExcel2003(filePath) || WDWUtil
                .isExcel2007(filePath))) {
            errorInfo = "文件名不是excel格式";
            return false;
        }
        /** 检查文件是否存在 */
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            errorInfo = "文件不存在";
            return false;
        }
        return true;

    }

    /**
     * @描述：根据文件名读取excel文件
     * @参数：@param filePath 文件完整路径
     * @参数：@return
     * @返回值：List
     */
    public List<List<String>> read(String filePath) {

        List<List<String>> dataLst = new ArrayList<List<String>>();
        File file = new File(filePath);

        try (InputStream is = new FileInputStream(file)) {

            /** 验证文件是否合法 */
            if (!validateExcel(filePath)) {
                log.error(errorInfo);
                return null;
            }
            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(filePath)) {
                isExcel2003 = false;
            }

            /** 调用本类提供的根据流读取的方法 */
            dataLst = read(is, isExcel2003);

        } catch (Exception ex) {

            log.error("读取excel文件异常：", ex);
        }

        /** 返回最后读取的结果 */
        return dataLst;
    }

    /**
     * @描述：根据文件名读取excel文件
     * @参数：@param file 文件
     * @参数：@return
     * @返回值：List
     */
    public List<List<String>> read(File file) {
        List<List<String>> dataLst = new ArrayList<List<String>>();

        try (InputStream is = new FileInputStream(file)) {

            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(file.getName())) {
                isExcel2003 = false;
            }

            dataLst = read(is, isExcel2003);

        } catch (Exception ex) {

            log.error("读取excel文件异常：", ex);
        }

        /** 返回最后读取的结果 */
        return dataLst;
    }

    public List<List<String>> read(File file, Integer templateType) {
        List<List<String>> dataLst = new ArrayList<List<String>>();

        try (InputStream is = new FileInputStream(file)) {

            /** 判断文件的类型，是2003还是2007 */
            boolean isExcel2003 = true;
            if (GV.i(templateType) == 5) {
                isExcel2003 = false;
            } else if (WDWUtil.isExcel2007(file.getName())) {
                isExcel2003 = false;
            }

            dataLst = read(is, isExcel2003);

        } catch (Exception ex) {

            log.error("读取excel文件异常：", ex);
        }

        /** 返回最后读取的结果 */
        return dataLst;
    }

    /**
     * @描述：根据流读取Excel文件
     * @参数：@param inputStream
     * @参数：@param isExcel2003
     * @参数：@return
     * @返回值：List
     */
    public List<List<String>> read(InputStream inputStream, boolean isExcel2003) {
        List<List<String>> dataLst = null;
        try {
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            if (isExcel2003) {
                wb = new HSSFWorkbook(inputStream);
            } else {
                wb = new XSSFWorkbook(inputStream);
            }
            dataLst = read(wb);
        } catch (IOException e) {

            log.error("读取excel流异常：", e);
        }
        return dataLst;

    }

    /**
     * @描述：读取数据
     * @参数：@param Workbook
     * @参数：@return
     * @返回值：List<List<String>>
     */
    private List<List<String>> read(Workbook wb) {
        List<List<String>> dataLst = new ArrayList<List<String>>();
        /** 得到第一个shell */
        Sheet sheet = wb.getSheetAt(0);
        /** 得到Excel的行数 */
        this.totalRows = sheet.getPhysicalNumberOfRows();
        /** 循环Excel的行 */
        for (int r = 0; r < this.totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            List<String> rowLst = new ArrayList<String>();
            /** 循环Excel的列 */
            int cellCount = sheet.getRow(r).getLastCellNum();
            for (int c = 0; c < cellCount; c++) {
                Cell cell = row.getCell(c);
                String cellValue = "";
                if (null != cell) {
                    // 以下是判断数据的类型  
                    cellValue = getCellValue(cell.getCellType(), cell);
                }
                rowLst.add(cellValue);
            }
            /** 保存第r行的第c列 */
            dataLst.add(rowLst);
        }
        return dataLst;
    }

    private static String getCellValue(int i, Cell cell) {
        String cellValue = new String();
        switch (i) {
            // 数字
            case HSSFCell.CELL_TYPE_NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_TIME);
                    cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                } else {
                    DecimalFormat df = new DecimalFormat("0");
                    cellValue = df.format(cell.getNumericCellValue());
                }
                break;

            // 字符串
            case HSSFCell.CELL_TYPE_STRING:
                cellValue = cell.getStringCellValue();
                break;

            // Boolean
            case HSSFCell.CELL_TYPE_BOOLEAN:
                cellValue = cell.getBooleanCellValue() + "";
                break;

            // 公式
            case HSSFCell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula() + "";
                break;

            // 空值
            case HSSFCell.CELL_TYPE_BLANK:
                cellValue = "";
                break;

            // 故障
            case HSSFCell.CELL_TYPE_ERROR:
                cellValue = "非法字符";
                break;

            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    public List<List<String>> readFileStream(String filePath, InputStream is) {
        List<List<String>> dataLst = new ArrayList<>();
        /* 验证文件是否合法 */
        if (filePath == null
                || !(WDWUtil.isExcel2003(filePath) || WDWUtil
                .isExcel2007(filePath))) {
            errorInfo = "文件名不是excel格式";
            throw new RuntimeException(errorInfo);
        }
        try {
            /* 验证文件类型*/
            boolean isExcel2003 = true;
            if (WDWUtil.isExcel2007(filePath)) {
                isExcel2003 = false;
            }
            dataLst = read(is, isExcel2003);
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        return dataLst;
    }

}

class WDWUtil {

    /**
     * @描述：是否是2003的excel，返回true是2003
     * @参数：@param filePath　文件完整路径
     * @参数：@return
     * @返回值：boolean
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * @描述：是否是2007的excel，返回true是2007
     * @参数：@param filePath　文件完整路径
     * @参数：@return
     * @返回值：boolean
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

}  

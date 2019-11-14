package com.itheima.test;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class POITest {

    @Test
    public void write() throws IOException {
//        创建excel
//
//        1、创建Excel对象（工作薄）
        XSSFWorkbook workbook = new XSSFWorkbook();//test.xlsx
//        2、创建Sheet（工作表）
        XSSFSheet sheet = workbook.createSheet();//不指定名称
//        3、创建Row
        XSSFRow row0 = sheet.createRow(0);
//        4、创建Cell
        row0.createCell(0).setCellValue("姓名");
        row0.createCell(1).setCellValue("地址");
        row0.createCell(2).setCellValue("爱好");
//        5、给单元格设置数据
        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("张三");
        row1.createCell(1).setCellValue("航头");
        row1.createCell(2).setCellValue("洗澡");



        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("李四");
        row2.createCell(1).setCellValue("航头1");
        row2.createCell(2).setCellValue("搓澡");

//        6、将内存里面Excel对象写入文件

        FileOutputStream out = new FileOutputStream("C:\\Users\\ThinkPad\\Desktop\\test.xlsx");
        workbook.write(out);

        out.flush();
        out.close();
        workbook.close();

    }


    @Test
    public void read() throws Exception{
//        读取Excel
//
//        1、加载Excel
        XSSFWorkbook workbook = new XSSFWorkbook("C:\\Users\\ThinkPad\\Desktop\\test.xlsx");
//        2、获取Sheet（工作表）
        XSSFSheet sheetAt = workbook.getSheetAt(0);//根据下标获取sheet
//        3、获取Row
        for (int i = 0; i <= sheetAt.getLastRowNum() ; i++) {
            XSSFRow row = sheetAt.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                XSSFCell cell = row.getCell(j);
                System.err.print(cell.getStringCellValue() +",");
            }
            System.err.println();
        }
//        4、获取Cell
//
//        5、获取单元格数据
//
//        6、关闭Excel

        workbook.close();
    }
}

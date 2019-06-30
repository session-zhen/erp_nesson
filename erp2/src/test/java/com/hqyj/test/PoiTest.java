package com.hqyj.test;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
public class PoiTest {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		HSSFWorkbook book=new HSSFWorkbook();
		HSSFSheet sheet=book.createSheet("myTable");
		 HSSFCellStyle style_content = book.createCellStyle();
	        style_content.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
	        style_content.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
	        style_content.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
	        style_content.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
	        // 根据导出的订单样本创建10行4列
	        for(int i = 2; i < 12; i++){
	            HSSFRow row = sheet.createRow(i);//创建订单内容中的行
	            for(int j = 0; j < 4; j++){
	                HSSFCell cell = row.createCell(j);//创建订单内容中的单元格
	                cell.setCellStyle(style_content);//设置单元格的样式
	            }
	        }
	        for(int i=2;i <12; i++) {
				sheet.getRow(i).setHeight((short)500);
			}
			for(int i =0;i<4;i++) {
				sheet.setColumnWidth(i,5000);
			}
	        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));//标题
			sheet.addMergedRegion(new CellRangeAddress(2,2,1,3));//供应商
			sheet.addMergedRegion(new CellRangeAddress(7,7,0,3));//订单明细
			sheet.createRow(0).createCell(0).setCellValue("采购单");
			sheet.getRow(2).getCell(0).setCellValue("供应商");
			sheet.getRow(3).getCell(0).setCellValue("下单日期");
			sheet.getRow(3).getCell(2).setCellValue("经办人");
			sheet.getRow(4).getCell(0).setCellValue("审核日期");
			sheet.getRow(4).getCell(2).setCellValue("经办人");
			sheet.getRow(5).getCell(0).setCellValue("采购日期");
			sheet.getRow(5).getCell(2).setCellValue("经办人");
			sheet.getRow(6).getCell(0).setCellValue("入库日期");
			sheet.getRow(6).getCell(2).setCellValue("经办人");
			sheet.getRow(7).getCell(0).setCellValue("订单明细");
			sheet.getRow(8).getCell(0).setCellValue("商品名");
			sheet.getRow(8).getCell(1).setCellValue("数量");
			sheet.getRow(8).getCell(2).setCellValue("价格");
			sheet.getRow(8).getCell(3).setCellValue("金额");
			sheet.getRow(0).setHeight((short)1000);
	        //保存工作簿到本地目录
	        book.write(new FileOutputStream("d:\\采购订单.xls"));
	        book.close();
	}
}

package com.gome.manager.common.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Java  实现AES加密和解密
 */
public class POIUtil{
	public static long getIntCellValue(Row row,int index){  
        long rtn = 0;  
        try {  
            Cell cell = row.getCell((short)index);  
            rtn = (long)cell.getNumericCellValue();
        } catch (RuntimeException e) {  
        }  
        return rtn;  
    }  
      
    public static String getStringValue(Row row,int index){  
        String rtn = "";  
        try {  
            Cell cell = row.getCell((short)index);  
            rtn = cell.getRichStringCellValue().getString();  
        } catch (RuntimeException e) {  
        }  
        return rtn;  
    }  
}

package com.technocrats.workboxutility.controllers;


//import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Sheet;



import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins="http://localhost:3000/")
@RequestMapping("/conversions")
@RestController



public class Dev {

    public void convertExcelToCSV(Sheet sheet, String sheetName) {
        StringBuilder data = new StringBuilder();
        try {
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    CellType type = cell.getCellType();
                    if (type == CellType.BOOLEAN) {
                        data.append(cell.getBooleanCellValue());
                    } else if (type == CellType.NUMERIC) {
                        data.append(cell.getNumericCellValue());
                    } else if (type == CellType.STRING) {
                        String cellValue = cell.getStringCellValue();
                        if(!cellValue.isEmpty()) {
                            cellValue = cellValue.replaceAll("\"", "\"\"");
                            data.append("\"").append(cellValue).append("\"");
                        }
                    } else if (type == CellType.BLANK) {
                    } else {
                        data.append(cell + "");
                    }
                    if(cell.getColumnIndex() != row.getLastCellNum()-1) {
                        data.append(",");
                    }
                }
                data.append('\n');
            }
            Files.write(Paths.get("C:\\Users\\Debidutta\\Desktop\\" + sheetName + ".csv"),
                data.toString().getBytes("UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String [] args)
    {
        Dev app = new Dev();
        String path =  "C:\\Users\\Debidutta\\Desktop\\excel_file.xlsx";
        try (InputStream inp = new FileInputStream(path)) {
            Workbook wb = WorkbookFactory.create(inp);

            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                System.out.println(wb.getSheetAt(i).getSheetName());
                app.convertExcelToCSV(wb.getSheetAt(i), wb.getSheetAt(i).getSheetName());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } 
    }
}
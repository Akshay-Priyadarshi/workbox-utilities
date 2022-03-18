package com.technocrats.workboxutility.controllers;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.tomcat.jni.Directory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;


@CrossOrigin(origins="*")
@RequestMapping("/data")
@RestController



public class Dev {

    @PostMapping(path="/excel2csv",consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public void uploadFile(@RequestPart MultipartFile excel, HttpServletResponse response) {
        try {
            InputStream is = excel.getInputStream();
            saveCsvFromWorkbook(is);
            File csvFile = new File("C:\\data\\Sheet.csv");
            if(csvFile != null){
                String csvContent = readFile("C:\\data\\Sheet.csv", StandardCharsets.UTF_8);
                String mimeType = URLConnection.guessContentTypeFromName(csvFile.getName());
                response.setContentType(mimeType);
                response.addHeader("Content-Disposition","attachment; filename=\""+ csvFile.getName()+"\"");
                response.getWriter().print(csvContent);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void saveCsvFromWorkbook(InputStream is){
        try {
            Workbook wb = WorkbookFactory.create(is);

            for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                convertExcelToCSV(wb.getSheetAt(i), wb.getSheetAt(i).getSheetName());
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void convertExcelToCSV(Sheet sheet, String sheetName) {
        StringBuilder data = new StringBuilder();
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
        try {
            Files.write(Paths.get("C:\\data\\" + sheetName + ".csv"),
                    data.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
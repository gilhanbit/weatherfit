package com.weatherfit.common.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

@Component
public class LocationDataParser {

    public String userLocation(String x, String y) {

        String location = null;

        try {
            // 압축 비율 제한 해제
            ZipSecureFile.setMinInflateRatio(0.0);

            FileInputStream file = new FileInputStream("src/main/resources/static/excel/locationData.xlsx");

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);


            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);


            for (Row row : sheet) {
                String valueX = "", valueY = "";

                Cell cellX = row.getCell(5);
                Cell cellY = row.getCell(6);

                if (cellX != null && cellX.getCellType() == CellType.STRING) {
                    valueX = cellX.getStringCellValue();
                }
                if (cellY != null && cellY.getCellType() == CellType.STRING) {
                    valueY = cellY.getStringCellValue();
                }

                if (valueX.equals(x) && valueY.equals(y)) {
                    Cell resultCell1 = row.getCell(2);
                    Cell resultCell2 = row.getCell(3);

                    String result1 = getCellValue(resultCell1);
                    String result2 = getCellValue(resultCell2);

                    location = result1 + " " + result2;
                    break;
                }
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula(); // 수식일 경우 수식 문자열 반환
            default -> "";
        };
    }
}

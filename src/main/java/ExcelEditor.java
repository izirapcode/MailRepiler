import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.SystemOutLogger;

import java.io.*;
import java.util.*;

public class ExcelEditor {

    Workbook workbook;
    List<Calendar> calendarArray = new ArrayList();

    public ExcelEditor(InputStream inputStream) throws InvalidFormatException {
        try {
            this.workbook = WorkbookFactory.create(inputStream);
            inputStream.close();
            Sheet sheet = workbook.getSheetAt(0);
            Calendar calendar = Calendar.getInstance();
            for (int i = 2; i < sheet.getLastRowNum()-3; i=i+3) {
                if (sheet.getRow(i).getCell(0).getDateCellValue() != null) {
                    calendar.setTime(sheet.getRow(i).getCell(0).getDateCellValue());
                }
                calendar.set(Calendar.HOUR_OF_DAY,sheet.getRow(i).getCell(1).getDateCellValue().getHours());
                calendar.set(Calendar.MINUTE,sheet.getRow(i).getCell(1).getDateCellValue().getMinutes());
                Calendar calendar2 = (Calendar)calendar.clone();
                calendarArray.add(calendar2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signExcel(Calendar date) throws IOException {
        int i =2;
        Sheet sheet = workbook.getSheetAt(0);
        for (Calendar calendar: calendarArray) {
            if(calendar.after(date)){
                sheet.getRow(i).getCell(9).setCellValue("Test");
                break;
            }
            i+=3;
        }
        FileOutputStream outputStream = new FileOutputStream("temporary.xlsx");
        workbook.write(outputStream);
        outputStream.close();
    }

}
package utility;

import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;

    public static void setExcelFile(String Path,String SheetName) throws Exception {
        FileInputStream ExcelFile = new FileInputStream(Path);
        ExcelWBook = new XSSFWorkbook(ExcelFile);
        ExcelWSheet = ExcelWBook.getSheet(SheetName);
    }

    //This method is to read the test data from the Excel cell
    //In this we are passing parameters/arguments as Row Num and Col Num
    public static String getCellData(int RowNum, int ColNum) throws Exception{
        Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
        String CellData = Cell.getStringCellValue();
        return CellData;
    }
}

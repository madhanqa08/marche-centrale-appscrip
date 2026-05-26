package xlutilities;

import model.TestCaseData;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {

    public static List<TestCaseData> getTestCaseData(String filePath, String tcIdContains) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            return getTestCaseData(fis, tcIdContains);
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }
    }

    public static List<TestCaseData> getTestCaseData(InputStream inputStream, String tcIdContains) {
        List<TestCaseData> testCaseList = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(1);

            if (headerRow == null) {
                throw new RuntimeException("Header row is missing in Excel file.");
            }

            int tcIdColumn = -1;
            int moduleColumn = -1;
            int titleColumn = -1;
            int expectedResultColumn = -1;
            int priorityColumn = -1;
            int labelColumn = -1;

            DataFormatter formatter = new DataFormatter();

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                Cell cell = headerRow.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                String header = cell == null ? "" : formatter.formatCellValue(cell).trim();

                switch (header) {
                    case "TC ID" -> tcIdColumn = i;
                    case "Module" -> moduleColumn = i;
                    case "Test Case Title" -> titleColumn = i;
                    case "Expected Result" -> expectedResultColumn = i;
                    case "Priority" -> priorityColumn = i;
                    case "Label" -> labelColumn = i;
                }
            }

            if (tcIdColumn == -1 || moduleColumn == -1 || titleColumn == -1 ||
                    expectedResultColumn == -1 || priorityColumn == -1 || labelColumn == -1) {
                throw new RuntimeException(
                        "One or more required headers were not found. " +
                                "Please check Excel header names exactly: TC ID, Module, Test Case Title, Expected Result, Priority, Label"
                );
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null) {
                    continue;
                }

                String tcId = getCellValue(
                        row.getCell(tcIdColumn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL),
                        formatter
                );

                if (tcId.contains(tcIdContains)) {
                    TestCaseData data = new TestCaseData();
                    data.setTcId(tcId);
                    data.setModule(getCellValue(row.getCell(moduleColumn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL), formatter));
                    data.setTestCaseTitle(getCellValue(row.getCell(titleColumn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL), formatter));
                    data.setExpectedResult(getCellValue(row.getCell(expectedResultColumn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL), formatter));
                    data.setPriority(getCellValue(row.getCell(priorityColumn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL), formatter));
                    data.setLabel(getCellValue(row.getCell(labelColumn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL), formatter));

                    testCaseList.add(data);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading Excel file: " + e.getMessage(), e);
        }

        return testCaseList;
    }

    private static String getCellValue(Cell cell, DataFormatter formatter) {
        if (cell == null) {
            return "";
        }
        return formatter.formatCellValue(cell).trim();
    }
}
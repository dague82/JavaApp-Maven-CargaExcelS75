import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import static org.apache.poi.ss.usermodel.CellType.*;

public class AnalizeExcel {
    public static void main(String[] args) throws Exception {
        String filePath = "C:\\Users\\dguerrero\\OneDrive - DG Software Engineering\\Documents\\ALTIA\\FreelanceServices\\Lanbide\\0300422-DigitalizacionLanbide_2022\\SEI\\#769366_CargaDatosExcelPersonContra(benefi)\\update1\\CUADROS DE EVOLUCION SEI.xlsx";

        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);

        boolean iniciarConteo = false;
        int totalRegistros = 0;
        Map<String, Integer> registrosPorHoja = new LinkedHashMap<>();

        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName = sheet.getSheetName();

            if ("BADIA BERRI".equalsIgnoreCase(sheetName)) {
                iniciarConteo = true;
            }

            if (!iniciarConteo) {
                System.out.println("Saltando (antes de BADIA BERRI): " + sheetName);
                continue;
            }

            int registrosEnHoja = 0;
            boolean headerEncontrado = false;

            for (Row row : sheet) {
                String cell0 = getCellValue(row.getCell(0)).toUpperCase();
                String cell4 = getCellValue(row.getCell(4));

                // Detectar header
                if (!headerEncontrado && cell0.contains("PUESTO")) {
                    headerEncontrado = true;
                    continue;
                }

                if (!headerEncontrado) continue;

                // Contar registros con DNI válido
                if (cell4 != null && !cell4.isEmpty() && cell4.length() <= 20) {
                    registrosEnHoja++;
                }
            }

            registrosPorHoja.put(sheetName, registrosEnHoja);
            totalRegistros += registrosEnHoja;
        }

        System.out.println("\n=== ANALISIS DE REGISTROS EN EXCEL ===\n");
        System.out.println(String.format("%-40s %10s", "HOJA", "REGISTROS"));
        System.out.println("----------------------------------------------------");

        for (Map.Entry<String, Integer> entry : registrosPorHoja.entrySet()) {
            if (entry.getValue() > 0) {
                System.out.println(String.format("%-40s %10d", entry.getKey(), entry.getValue()));
            }
        }

        System.out.println("----------------------------------------------------");
        System.out.println(String.format("%-40s %10d", "TOTAL", totalRegistros));
    }

    static String getCellValue(Cell cell) {
        if (cell == null) return "";
        try {
            switch (cell.getCellType()) {
                case STRING: return cell.getStringCellValue();
                case NUMERIC: return String.valueOf(cell.getNumericCellValue());
                default: return "";
            }
        } catch (Exception e) {
            return "";
        }
    }
}

package com.isttmicroservice.smsantispam.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isttmicroservice.smsantispam.dto.CodeDTO;
import com.isttmicroservice.smsantispam.dto.CodeDeckDTO;
import com.isttmicroservice.smsantispam.entity.Code;
import com.isttmicroservice.smsantispam.entity.CodeDeck;
import com.isttmicroservice.smsantispam.repository.CodeDeckRepo;
import com.isttmicroservice.smsantispam.repository.CodeRepo;

@RestController
@RequestMapping("/api/excel")
public class ExcelAPIController {
	@Autowired
	CodeDeckRepo codeDeckRepo;
	@Autowired
	CodeRepo codeRepo;

	@PostMapping("/code")
	public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file, String codeDeckId) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Vui lòng chọn một tệp Excel.");
		}
		CodeDeck codeDeck = codeDeckRepo.findByCodeDeck(codeDeckId);
		Date date = new Date();
		try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			List<CodeDTO> excelDataList = new ArrayList<>();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				CodeDTO excelData = new CodeDTO();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					switch (cell.getCellType()) {
					case STRING:
						switch (columnIndex) {
						case 0:
							excelData.setCode(cell.getStringCellValue());
							break;
						case 1:
							excelData.setCodeName(cell.getStringCellValue());
							break;
						case 2:
							excelData.setCountry(cell.getStringCellValue());
							break;
						
						// Các trường khác nếu cần
						}
					case NUMERIC:

						break;
					}
				}
				Code code = new Code();
				code.setCode(excelData.getCode());
				code.setCodeName(excelData.getCodeName());
				code.setCountry(excelData.getCountry());
				code.setCreatedAt(date);
				code.setCodeDeck(codeDeck);
				excelDataList.add(excelData);

				codeRepo.save(code);

			}
		
			// Lưu excelDataList vào cơ sở dữ liệu ở đây

			return ResponseEntity.ok("Tải lên tệp Excel thành công.");
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Lỗi trong quá trình xử lý tệp Excel.");
		}
	}
}

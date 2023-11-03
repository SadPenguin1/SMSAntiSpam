package com.isttmicroservice.smsantispam.api;

import java.io.InputStreamReader;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.isttmicroservice.smsantispam.dto.AuditLogDTO;
import com.isttmicroservice.smsantispam.dto.CodeDeckDTO;
import com.isttmicroservice.smsantispam.entity.Code;
import com.isttmicroservice.smsantispam.entity.CodeDeck;
import com.isttmicroservice.smsantispam.entity.User;
import com.isttmicroservice.smsantispam.repository.CodeDeckRepo;
import com.isttmicroservice.smsantispam.repository.CodeRepo;
import com.isttmicroservice.smsantispam.repository.UserRepo;
import com.isttmicroservice.smsantispam.service.AuditLogService;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@RestController
@RequestMapping("/api/csv")
public class CsvAPIController {
	@Autowired
	CodeRepo codeRepo;

	@Autowired
	CodeDeckRepo codeDeckRepo;

	@Autowired
	UserRepo userRepo;
	@Autowired
	AuditLogService auditLogService;

	@PostMapping("/code")
	public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file, String codeDeckId,
			Integer userId) {
		if (file.isEmpty()) {
			return ResponseEntity.badRequest().body("Vui lòng chọn một tệp CSV.");
		} else if (userId == null) {
			return ResponseEntity.badRequest().body("Vui lòng Đăng nhập .");
		}
		CodeDeck codeDeck = codeDeckRepo.findByCodeDeck(codeDeckId);
		Date date = new Date();
		try (InputStreamReader reader = new InputStreamReader(file.getInputStream());
				CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

			String[] nextRecord;
			User user = userRepo.findById(userId).orElseThrow(null);
			while ((nextRecord = csvReader.readNext()) != null) {

				Code code = new Code();
				code.setCode(nextRecord[0]);// Cột 1 trong CSV
				code.setCodeName(nextRecord[1]); // Cột 2 trong CSV
				code.setCountry(nextRecord[2]);
				code.setCreatedAt(date);
				code.setCodeDeck(codeDeck);
				code.setCreatedBy(user);
				codeRepo.save(code);
			}
			AuditLogDTO auditLogDTO = new AuditLogDTO();
			auditLogDTO.setLogMessage("created code at codeDeckId: " + codeDeckId + " by " + user.getUsername());
			auditLogService.create(auditLogDTO, userId);

			return ResponseEntity.ok("Dữ liệu từ CSV đã được lưu vào cơ sở dữ liệu.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi trong quá trình xử lý tệp CSV.");
		}
	}
}

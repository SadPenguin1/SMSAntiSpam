package com.isttmicroservice.smsantispam.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.isttmicroservice.smsantispam.dto.RequestTestFilterDTO;
import com.isttmicroservice.smsantispam.dto.ResponeTestFilterDTO;
import com.isttmicroservice.smsantispam.entity.FilterContactLog;
import com.isttmicroservice.smsantispam.entity.FilterFrequencyLog;
import com.isttmicroservice.smsantispam.entity.FilterHotkeysLog;
import com.isttmicroservice.smsantispam.entity.FilterLengthLog;
import com.isttmicroservice.smsantispam.entity.FilterSimilarityLog;
import com.isttmicroservice.smsantispam.entity.FilterSpamwordLog;
import com.isttmicroservice.smsantispam.entity.FilterURLLog;
import com.isttmicroservice.smsantispam.entity.MySQLLog;
import com.isttmicroservice.smsantispam.entity.SystemLog;
import com.isttmicroservice.smsantispam.repository.ContactLogRepo;
import com.isttmicroservice.smsantispam.repository.FrequenceLogRepo;
import com.isttmicroservice.smsantispam.repository.HotkeysLogRepo;
import com.isttmicroservice.smsantispam.repository.LengthLogRepo;
import com.isttmicroservice.smsantispam.repository.MySQLLogRepo;
import com.isttmicroservice.smsantispam.repository.SimilarityLogRepo;
import com.isttmicroservice.smsantispam.repository.SpamwordLogRepo;
import com.isttmicroservice.smsantispam.repository.SystemLogRepo;
import com.isttmicroservice.smsantispam.repository.URLLogRepo;

@RestController
@RequestMapping("/test-filter")
public class TestFilterAPIController {
	@Autowired
	SystemLogRepo systemLogRepo;

	@Autowired
	ContactLogRepo contactLogRepo;
	@Autowired
	FrequenceLogRepo frequenceLogRepo;
	@Autowired
	HotkeysLogRepo hotkeysLogRepo;
	@Autowired
	LengthLogRepo lengthLogRepo;
	@Autowired
	SimilarityLogRepo similarityLogRepo;
	@Autowired
	SpamwordLogRepo spamwordLogRepo;
	@Autowired
	MySQLLogRepo mySQLLogRepo;
	@Autowired
	URLLogRepo urlLogRepo;

	@PostMapping("/")
	public ResponeTestFilterDTO create(@RequestBody RequestTestFilterDTO requestTestFilterDTO) throws IOException {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RequestTestFilterDTO> request = new HttpEntity<>(requestTestFilterDTO, headers);

		ResponeTestFilterDTO rs = new ResponeTestFilterDTO();

		if (requestTestFilterDTO.getFilters().getFilter_contact().isStatus() == false
				&& requestTestFilterDTO.getFilters().getFilter_frequency().isStatus() == false
				&& requestTestFilterDTO.getFilters().getFilter_hotkeys().isStatus() == false
				&& requestTestFilterDTO.getFilters().getFilter_length().isStatus() == false
				&& requestTestFilterDTO.getFilters().getFilter_ip().isStatus() == false
				&& requestTestFilterDTO.getFilters().getFilter_similarity().isStatus() == false
				&& requestTestFilterDTO.getFilters().getFilter_spam_word().isStatus() == false
				&& requestTestFilterDTO.getFilters().getFilter_url().isStatus() == false) {

			rs.setMessage("Filter is required !");
			rs.setSuccess(false);
			rs.setError("Internal Server Error !");
			return rs;
		}

		else if (requestTestFilterDTO.getSender() == null) {

			rs.setMessage("Sender is required !");
			rs.setSuccess(false);
			rs.setError("Internal Server Error !");
			return rs;

		} else if (requestTestFilterDTO.getSms() == null) {

			rs.setMessage("Sms is required !");
			rs.setSuccess(false);
			rs.setError("Internal Server Error !");
			return rs;
		} else {
			ResponseEntity<ResponeTestFilterDTO> response = restTemplate.exchange(
					"http://sms-service.istt.com.vn/api/v1/filter", HttpMethod.POST, request,
					ResponeTestFilterDTO.class);
			ResponeTestFilterDTO rs1 = response.getBody();
			SystemLog systemLog = systemLogRepo.findByLogId(rs1.getData());
	
			if (systemLog.getFilterContactLog() != null) {
				List<FilterContactLog> filterContactLogs = contactLogRepo.findByLogId(systemLog.getFilterContactLog());
				rs1.setFilterContactLogs(filterContactLogs);
			}
			if (systemLog.getFilterFrequencyLog() != null) {
				List<FilterFrequencyLog> filterFrequencyLogs = frequenceLogRepo
						.findByLogId(systemLog.getFilterFrequencyLog());
				rs1.setFilterFrequencyLogs(filterFrequencyLogs);
			}
			if (systemLog.getFilterHotkeysLog() != null) {
				List<FilterHotkeysLog> filterHotkeysLogs = hotkeysLogRepo.findByLogId(systemLog.getFilterHotkeysLog());
				rs1.setFilterHotkeysLogs(filterHotkeysLogs);
			}
			if (systemLog.getFilterLengthLog() != null) {
				List<FilterLengthLog> filterLengthLogs = lengthLogRepo.findByLogId(systemLog.getFilterLengthLog());
				rs1.setFilterLengthLogs(filterLengthLogs);
			}
			if (systemLog.getFilterSimilarityLog() != null) {
				List<FilterSimilarityLog> filterSimilarityLogs = similarityLogRepo
						.findByLogId(systemLog.getFilterSimilarityLog());
				rs1.setFilterSimilarityLogs(filterSimilarityLogs);
			}
			if (systemLog.getFilterSpamwordLog() != null) {
				List<FilterSpamwordLog> filterSpamwordLogs = spamwordLogRepo
						.findByLogId(systemLog.getFilterSpamwordLog());
				rs1.setFilterSpamwordLogs(filterSpamwordLogs);
			}
			if (systemLog.getMySQLLog() != null) {
				List<MySQLLog> mySQLLogs = mySQLLogRepo.findByLogId(systemLog.getMySQLLog());
				rs1.setMySQLLogs(mySQLLogs);
			}
			if (systemLog.getFilterURLLog() != null) {
				List<FilterURLLog> filterURLLogs = urlLogRepo.findByLogId(systemLog.getFilterURLLog());
				rs1.setFilterURLLogs(filterURLLogs);
			}
//			

			return rs1;

		}

	}
}

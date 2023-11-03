package com.isttmicroservice.smsantispam.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isttmicroservice.smsantispam.dto.AuditLogDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.entity.AuditLog;
import com.isttmicroservice.smsantispam.entity.User;
import com.isttmicroservice.smsantispam.repository.AuditLogRepo;
import com.isttmicroservice.smsantispam.repository.UserRepo;

public interface AuditLogService {
	void create(AuditLogDTO auditLogDTO, Integer userId);

	void update(AuditLogDTO auditLogDTO);

	void delete(Integer id);

	void deleteAll(List<Integer> ids);

	AuditLogDTO get(Integer id);

	ResponseDTO<List<AuditLogDTO>> find(SearchDTO searchDTO);
}

@Service
class AuditLogServiceImpl implements AuditLogService {

	@Autowired
	AuditLogRepo auditLogRepo;
	@Autowired
	UserRepo userRepo;

	@Transactional
	@Override
	public void create(AuditLogDTO auditLogDTO, Integer userId) {

		User user = userRepo.findById(userId).orElseThrow(null);

		AuditLog auditLog = new AuditLog();
		auditLog.setLogLevel("INFO");
		auditLog.setLogMessage(auditLogDTO.getLogMessage());
		auditLog.setLogSender(user);
		auditLog.setLogTimeStamp(new Date());

		auditLogRepo.save(auditLog);

		auditLogDTO.setId(auditLog.getId());
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		AuditLog auditLog = auditLogRepo.findById(id).orElseThrow(NoResultException::new);
		if (auditLog != null) {
			if (auditLog.getId() != null) {
				auditLogRepo.deleteById(id);
			}
			auditLogRepo.deleteById(id);
		}
	}

	@Transactional
	@Override
	public void deleteAll(List<Integer> ids) {
		auditLogRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public AuditLogDTO get(Integer id) {
		return auditLogRepo.findById(id).map(auditLog -> convert(auditLog)).orElseThrow(NoResultException::new);
	}

	@Override
	public ResponseDTO<List<AuditLogDTO>> find(SearchDTO searchDTO) {
		List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
				.map(order -> {
					if (order.getOrder().equals(SearchDTO.ASC))
						return Sort.Order.asc(order.getProperty());

					return Sort.Order.desc(order.getProperty());
				}).collect(Collectors.toList());

		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

		Page<AuditLog> page = auditLogRepo.find(searchDTO.getValue(), pageable);

		ResponseDTO<List<AuditLogDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
		responseDTO.setData(page.get().map(auditLog -> convert(auditLog)).collect(Collectors.toList()));
		return responseDTO;
	}

	private AuditLogDTO convert(AuditLog auditLog) {
		return new ModelMapper().map(auditLog, AuditLogDTO.class);
	}

	@Override
	public void update(AuditLogDTO auditLogDTO) {
		// TODO Auto-generated method stub
		
	}
}
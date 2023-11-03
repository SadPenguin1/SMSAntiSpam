package com.isttmicroservice.smsantispam.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
import com.isttmicroservice.smsantispam.dto.CodeDeckDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.entity.CodeDeck;
import com.isttmicroservice.smsantispam.repository.CodeDeckRepo;

public interface CodeDeckService {
	void create(CodeDeckDTO codeDeckDTO);

	void update(CodeDeckDTO codeDeckDTO);

	void delete(String id);

	void deleteAll(List<String> ids);

	CodeDeckDTO get(Integer id);

	ResponseDTO<List<CodeDeckDTO>> find(SearchDTO searchDTO);
	
}

@Service
class CodeDeckServiceImpl implements CodeDeckService {
	@Autowired
	CodeDeckRepo codeDeckRepo;
	
	@Autowired
	AuditLogService auditLogService;
	
	@Transactional
	@Override
	public void create(CodeDeckDTO codeDeckDTO) {
		ModelMapper mapper = new ModelMapper();
		CodeDeck codeDeck = mapper.map(codeDeckDTO, CodeDeck.class);
		codeDeck.setCodeDeckId(UUID.randomUUID().toString());
		codeDeck.setCreatedAt(new Date());
		codeDeckRepo.save(codeDeck);

		AuditLogDTO auditLogDTO = new AuditLogDTO();
		auditLogDTO.setLogMessage("created codedeck :"+ "codeDeckId: "+codeDeck.getCodeDeckId()+", codedeck:  "+ codeDeckDTO.getCodeDeck() );
		auditLogService.create( auditLogDTO , codeDeckDTO.getCreatedBy().getId());

		codeDeckDTO.setId(codeDeck.getId());
		codeDeckDTO.setCodeDeckId(codeDeck.getCodeDeckId());
	

	}

	@Transactional
	@Override
	public void update(CodeDeckDTO codeDeckDTO) {
		ModelMapper mapper = new ModelMapper();
		mapper.createTypeMap(CodeDeckDTO.class, CodeDeck.class)
				.setProvider(p -> codeDeckRepo.findById(codeDeckDTO.getId()).orElseThrow(NoResultException::new));

		CodeDeck codeDeck = mapper.map(codeDeckDTO, CodeDeck.class);
		codeDeck.setCreatedAt(codeDeckDTO.getCreatedAt());
		codeDeck.setUpdatedAt(new Date());
		codeDeckRepo.save(codeDeck);
	}

	@Transactional
	@Override
	public void delete(String id) {
		CodeDeck codeDeck = codeDeckRepo.findByCodeDeckId(id).orElseThrow(NoResultException::new);
		if (codeDeck != null) {
			codeDeckRepo.delete(codeDeck);
		}
	}

	@Transactional
	@Override
	public void deleteAll(List<String> ids) {
		List<CodeDeck> codeDecksToDelete = codeDeckRepo.findAllByIds(ids);
	    codeDeckRepo.deleteInBatch(codeDecksToDelete);
//		codeDeckRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public CodeDeckDTO get(Integer id) {
		return codeDeckRepo.findById(id).map(codeDeck -> convert(codeDeck)).orElseThrow(NoResultException::new);
	}

	@Override
	public ResponseDTO<List<CodeDeckDTO>> find(SearchDTO searchDTO) {
		List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
				.map(order -> {
					if (order.getOrder().equals(SearchDTO.ASC))
						return Sort.Order.asc(order.getProperty());

					return Sort.Order.desc(order.getProperty());
				}).collect(Collectors.toList());

		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

		Page<CodeDeck> page = codeDeckRepo.findByCodeDeck(searchDTO.getValue(), pageable);

		ResponseDTO<List<CodeDeckDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
		responseDTO.setData(page.get().map(CodeDeck -> convert(CodeDeck)).collect(Collectors.toList()));
		return responseDTO;
	}

	private CodeDeckDTO convert(CodeDeck codeDeck) {
		return new ModelMapper().map(codeDeck, CodeDeckDTO.class);
	}


}
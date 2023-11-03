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
import org.springframework.util.StringUtils;

import com.isttmicroservice.smsantispam.dto.CodeDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.entity.Code;
import com.isttmicroservice.smsantispam.repository.CodeRepo;

public interface CodeService {
	void create(CodeDTO codeDTO);

	void update(CodeDTO codeDTO);

	void delete(Integer id);

	void deleteAll(List<Integer> ids);

	CodeDTO get(Integer id);

	ResponseDTO<List<CodeDTO>> find(SearchDTO searchDTO);
	
}

@Service
class CodeServiceImpl implements CodeService {
	@Autowired
	CodeRepo codeRepo;
	
	@Transactional
	@Override
	public void create(CodeDTO codeDTO) {
		ModelMapper mapper = new ModelMapper();
		Code code = mapper.map(codeDTO, Code.class);
		code.setCreatedAt(new Date());
		codeRepo.save(code);
		codeDTO.setId(code.getId());

	}

	@Transactional
	@Override
	public void update(CodeDTO codeDTO) {
		ModelMapper mapper = new ModelMapper();
		mapper.createTypeMap(CodeDTO.class, Code.class)
				.setProvider(p -> codeRepo.findById(codeDTO.getId()).orElseThrow(NoResultException::new));

		Code code = mapper.map(codeDTO, Code.class);
		code.setCreatedAt(codeDTO.getCreateAt());
		code.setUpdatedAt(new Date());
		codeRepo.save(code);
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		Code code = codeRepo.findById(id).orElseThrow(NoResultException::new);
		if (code != null) {
			codeRepo.deleteById(id);
		}
	}

	@Transactional
	@Override
	public void deleteAll(List<Integer> ids) {
		codeRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public CodeDTO get(Integer id) {
		return codeRepo.findById(id).map(code -> convert(code)).orElseThrow(NoResultException::new);
	}

	@Override
	public ResponseDTO<List<CodeDTO>> find(SearchDTO searchDTO) {
		List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
				.map(order -> {
					if (order.getOrder().equals(SearchDTO.ASC))
						return Sort.Order.asc(order.getProperty());

					return Sort.Order.desc(order.getProperty());
				}).collect(Collectors.toList());
		
		String codeDeckId = null;
		if (searchDTO.getFilterBys() != null) {
			if (StringUtils.hasText(searchDTO.getFilterBys().get("codeDeckId"))) {
				codeDeckId = String.valueOf(searchDTO.getFilterBys().get("codeDeckId"));
				System.out.println(codeDeckId);
			}
		}

		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));


		Page<Code> page = null;
		if (codeDeckId != null ) {
			page = codeRepo.findByCodeDeck(searchDTO.getValue(), codeDeckId, pageable);
		
		} else {
			page = codeRepo.findByCode(searchDTO.getValue(), pageable);
		}

		ResponseDTO<List<CodeDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
		responseDTO.setData(page.get().map(Code -> convert(Code)).collect(Collectors.toList()));
		return responseDTO;
	}

	private CodeDTO convert(Code code) {
		return new ModelMapper().map(code, CodeDTO.class);
	}


}
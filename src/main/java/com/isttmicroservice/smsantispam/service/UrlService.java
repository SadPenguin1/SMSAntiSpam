package com.isttmicroservice.smsantispam.service;

import java.util.Collections;
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

import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.dto.UrlDTO;
import com.isttmicroservice.smsantispam.entity.Url;
import com.isttmicroservice.smsantispam.repository.UrlRepo;

public interface UrlService {
	void create(UrlDTO urlDTO);

	void update(UrlDTO urlDTO);

	void delete(Integer id);

	void deleteAll(List<Integer> ids);

	UrlDTO get(Integer id);

	ResponseDTO<List<UrlDTO>> find(SearchDTO searchDTO);
	
}

@Service
class UrlServiceImpl implements UrlService {
	@Autowired
	UrlRepo urlRepo;
	
	@Transactional
	@Override
	public void create(UrlDTO urlDTO) {
		ModelMapper mapper = new ModelMapper();
		Url url = mapper.map(urlDTO, Url.class);
		urlRepo.save(url);
		urlDTO.setId(url.getId());

	}

	@Transactional
	@Override
	public void update(UrlDTO urlDTO) {
		ModelMapper mapper = new ModelMapper();
		mapper.createTypeMap(UrlDTO.class, Url.class)
				.setProvider(p -> urlRepo.findById(urlDTO.getId()).orElseThrow(NoResultException::new));

		Url Url = mapper.map(urlDTO, Url.class);
		urlRepo.save(Url);
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		Url url = urlRepo.findById(id).orElseThrow(NoResultException::new);
		if (url != null) {
			urlRepo.deleteById(id);
		}
	}

	@Transactional
	@Override
	public void deleteAll(List<Integer> ids) {
		urlRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public UrlDTO get(Integer id) {
		return urlRepo.findById(id).map(url -> convert(url)).orElseThrow(NoResultException::new);
	}

	@Override
	public ResponseDTO<List<UrlDTO>> find(SearchDTO searchDTO) {
		List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
				.map(order -> {
					if (order.getOrder().equals(SearchDTO.ASC))
						return Sort.Order.asc(order.getProperty());

					return Sort.Order.desc(order.getProperty());
				}).collect(Collectors.toList());

		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

		Page<Url> page = urlRepo.findByUrl(searchDTO.getValue(), pageable);

		ResponseDTO<List<UrlDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
		responseDTO.setData(page.get().map(Url -> convert(Url)).collect(Collectors.toList()));
		return responseDTO;
	}

	private UrlDTO convert(Url url) {
		return new ModelMapper().map(url, UrlDTO.class);
	}


}
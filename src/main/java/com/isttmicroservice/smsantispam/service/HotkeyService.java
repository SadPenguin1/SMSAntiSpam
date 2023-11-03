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

import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.dto.HotkeyDTO;
import com.isttmicroservice.smsantispam.entity.Hotkey;
import com.isttmicroservice.smsantispam.repository.HotkeyRepo;

public interface HotkeyService {
	void create(HotkeyDTO hotkeyDTO);

	void update(HotkeyDTO hotkeyDTO);

	void delete(Integer id);

	void deleteAll(List<Integer> ids);

	HotkeyDTO get(Integer id);

	ResponseDTO<List<HotkeyDTO>> find(SearchDTO searchDTO);

}

@Service
class HotkeyServiceImpl implements HotkeyService {
	@Autowired
	HotkeyRepo hotkeyRepo;

	@Transactional
	@Override
	public void create(HotkeyDTO hotkeyDTO) {
		ModelMapper mapper = new ModelMapper();
		Hotkey hotkey = mapper.map(hotkeyDTO, Hotkey.class);
		hotkey.setCreateAt(new Date());
		hotkeyRepo.save(hotkey);
		hotkeyDTO.setId(hotkey.getId());;

	}

	@Transactional
	@Override
	public void update(HotkeyDTO hotkeyDTO) {
		ModelMapper mapper = new ModelMapper();
		mapper.createTypeMap(HotkeyDTO.class, Hotkey.class)
				.setProvider(p -> hotkeyRepo.findById(hotkeyDTO.getId()).orElseThrow(NoResultException::new));
		
		Hotkey hotkey = mapper.map(hotkeyDTO, Hotkey.class);
		hotkey.setCreateAt(hotkeyDTO.getCreateAt());
		hotkey.setUpdateAt(new Date());
		hotkeyRepo.save(hotkey);
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		Hotkey hotkey = hotkeyRepo.findById(id).orElseThrow(NoResultException::new);
		if (hotkey != null) {
			hotkeyRepo.deleteById(id);
		}
	}

	@Transactional
	@Override
	public void deleteAll(List<Integer> ids) {
		hotkeyRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public HotkeyDTO get(Integer id) {
		return hotkeyRepo.findById(id).map(hotkey -> convert(hotkey)).orElseThrow(NoResultException::new);
	}

	@Override
	public ResponseDTO<List<HotkeyDTO>> find(SearchDTO searchDTO) {
		List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
				.map(order -> {
					if (order.getOrder().equals(SearchDTO.ASC))
						return Sort.Order.asc(order.getProperty());

					return Sort.Order.desc(order.getProperty());
				}).collect(Collectors.toList());

		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

		Page<Hotkey> page = hotkeyRepo.findByHotkeys(searchDTO.getValue(), pageable);

		ResponseDTO<List<HotkeyDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
		responseDTO.setData(page.get().map(Hotkey -> convert(Hotkey)).collect(Collectors.toList()));
		return responseDTO;
	}

	private HotkeyDTO convert(Hotkey hotkey) {
		return new ModelMapper().map(hotkey, HotkeyDTO.class);
	}

}
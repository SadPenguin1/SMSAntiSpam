package com.isttmicroservice.smsantispam.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.isttmicroservice.smsantispam.dto.MessageDTO;
import com.isttmicroservice.smsantispam.dto.MessageStatisticDTO;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.ResponseSearchMessageDTO;
import com.isttmicroservice.smsantispam.dto.ScoreDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.entity.Message;
import com.isttmicroservice.smsantispam.repository.MessageRepo;
import com.isttmicroservice.smsantispam.repository.MessageResultRepo;

public interface MessageService {

	void delete(Integer id);

	void deleteAll(List<Integer> ids);

	MessageDTO get(Integer id);

	ResponseSearchMessageDTO<List<MessageDTO>> statistic(SearchDTO searchDTO);

	ResponseSearchMessageDTO<List<MessageDTO>> score(SearchDTO searchDTO);

	List<MessageStatisticDTO> getMessageStatistics(Date start, Date end);

	List<ScoreDTO> getMessageScores(Date start, Date end);

	ResponseDTO<List<MessageDTO>> find(SearchDTO searchDTO);

}

@Service
class MessageServiceImpl implements MessageService {

	@Autowired
	MessageRepo messageRepo;

	@Autowired
	MessageResultRepo messageResultRepo;

	@Transactional
	@Override
	public void delete(Integer id) {
		Message message = messageRepo.findById(id).orElseThrow(NoResultException::new);
		if (message != null) {
			messageRepo.deleteById(id);
		}
	}

	@Transactional
	@Override
	public void deleteAll(List<Integer> ids) {
		messageRepo.deleteAllByIdInBatch(ids);
	}

	@Override
	public MessageDTO get(Integer id) {
		return messageRepo.findById(id).map(message -> convert(message)).orElseThrow(NoResultException::new);
	}

	@Override
	public ResponseSearchMessageDTO<List<MessageDTO>> statistic(SearchDTO searchDTO) {
		Date start = null;
		Date end = null;

		System.out.println(searchDTO.getFilterBys());
		if (searchDTO.getFilterBys() != null) {

			if (StringUtils.hasText(searchDTO.getFilterBys().get("start"))) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					start = dateFormat.parse(String.valueOf(searchDTO.getFilterBys().get("start")));
				} catch (ParseException e) {
					// Xử lý lỗi khi không thể chuyển đổi thành kiểu Date
				}
			}
			if (StringUtils.hasText(searchDTO.getFilterBys().get("end"))) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					end = dateFormat.parse(String.valueOf(searchDTO.getFilterBys().get("end")));
				} catch (ParseException e) {
					// Xử lý lỗi khi không thể chuyển đổi thành kiểu Date
				}
			}

		}

		ResponseSearchMessageDTO<List<MessageDTO>> responseDTO = new ResponseSearchMessageDTO();
		responseDTO.setMessageStatisticDTOs(getMessageStatistics(start, end));
		return responseDTO;
	}

	public List<MessageStatisticDTO> getMessageStatistics(Date start, Date end) {
		List<Object[]> results = messageRepo.countMessagesByType(start, end);
		return results.stream().map(result -> new MessageStatisticDTO((String) result[0], (Long) result[1]))
				.collect(Collectors.toList());
	}

	@Override
	public ResponseSearchMessageDTO<List<MessageDTO>> score(SearchDTO searchDTO) {

		Date start = null;
		Date end = null;

		System.out.println(searchDTO.getFilterBys());
		if (searchDTO.getFilterBys() != null) {

			if (StringUtils.hasText(searchDTO.getFilterBys().get("start"))) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					start = dateFormat.parse(String.valueOf(searchDTO.getFilterBys().get("start")));
				} catch (ParseException e) {
					// Xử lý lỗi khi không thể chuyển đổi thành kiểu Date
				}
			}
			if (StringUtils.hasText(searchDTO.getFilterBys().get("end"))) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					end = dateFormat.parse(String.valueOf(searchDTO.getFilterBys().get("end")));
				} catch (ParseException e) {
					// Xử lý lỗi khi không thể chuyển đổi thành kiểu Date
				}
			}

		}
		System.out.println("start: "+ start);
		ResponseSearchMessageDTO<List<MessageDTO>> responseDTO = new ResponseSearchMessageDTO();
		responseDTO.setScoreDTOs(getMessageScores(start, end));
		return responseDTO;
	}

	public List<ScoreDTO> getMessageScores(Date start, Date end) {
		List<Object[]> results = messageRepo.countMessagesByScore(start, end);
		return results.stream().map(result -> new ScoreDTO((Float) result[0], (Long) result[1]))
				.collect(Collectors.toList());
	}

	@Override
	public ResponseDTO<List<MessageDTO>> find(SearchDTO searchDTO) {
		List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
				.map(order -> {
					if (order.getOrder().equals(SearchDTO.ASC))
						return Sort.Order.asc(order.getProperty());

					return Sort.Order.desc(order.getProperty());
				}).collect(Collectors.toList());

		String type = null;
		Date start = null;
		Date end = null;

		System.out.println(searchDTO.getFilterBys());
		if (searchDTO.getFilterBys() != null) {
			if (StringUtils.hasText(searchDTO.getFilterBys().get("type"))) {
				type = String.valueOf(searchDTO.getFilterBys().get("type"));
			}
			if (StringUtils.hasText(searchDTO.getFilterBys().get("start"))) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					start = dateFormat.parse(String.valueOf(searchDTO.getFilterBys().get("start")));
					
				} catch (ParseException e) {
					// Xử lý lỗi khi không thể chuyển đổi thành kiểu Date
				}
			}
			if (StringUtils.hasText(searchDTO.getFilterBys().get("end"))) {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					end = dateFormat.parse(String.valueOf(searchDTO.getFilterBys().get("end")));
				} catch (ParseException e) {
					// Xử lý lỗi khi không thể chuyển đổi thành kiểu Date
				}
			}

		}
		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

		Page<Message> page = null;
		if (start != null && end != null && type != null) {
			page = messageRepo.searchByDateAndType(searchDTO.getValue(), type, start, end, pageable);
		} else if (start != null && end != null) {
			page = messageRepo.searchByDate(searchDTO.getValue(), start, end, pageable);
		} else if (type != null) {
			page = messageRepo.findByContentAndType(searchDTO.getValue(), type, pageable);
		} else {
			page = messageRepo.findByValue(searchDTO.getValue(), pageable);
		}
		ResponseDTO<List<MessageDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
		responseDTO.setData(page.get().map(Message -> convert(Message)).collect(Collectors.toList()));

		return responseDTO;
	}

	private MessageDTO convert(Message message) {
		return new ModelMapper().map(message, MessageDTO.class);
	}
}
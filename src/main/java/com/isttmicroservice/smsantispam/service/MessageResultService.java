package com.isttmicroservice.smsantispam.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.util.StringUtils;

import com.isttmicroservice.smsantispam.dto.MessageDTO;
import com.isttmicroservice.smsantispam.dto.MessageResultDTO;
import com.isttmicroservice.smsantispam.dto.RawData;
import com.isttmicroservice.smsantispam.dto.ResponseDTO;
import com.isttmicroservice.smsantispam.dto.ResponseSearchMessageDTO;
import com.isttmicroservice.smsantispam.dto.ScoreDTO;
import com.isttmicroservice.smsantispam.dto.SearchDTO;
import com.isttmicroservice.smsantispam.dto.SearchMessageResultDTO;
import com.isttmicroservice.smsantispam.dto.StatisticDTO;
import com.isttmicroservice.smsantispam.dto.StatisticData;
import com.isttmicroservice.smsantispam.entity.MessageResult;
import com.isttmicroservice.smsantispam.repository.MessageResultRepo;

public interface MessageResultService {

//	void update(MessageResultDTO messageResultDTO);

	MessageResultDTO get(Integer id);

	ResponseDTO<List<MessageResultDTO>> find(SearchDTO searchDTO);

	StatisticData statistic(SearchMessageResultDTO searchMessageResultDTO);

	StatisticData convertToStatisticData(List<RawData> rawDataList);

//	StatisticDTO statistic(SearchMessageResultDTO searchMessageResultDTO);

}

@Service
class MessageResultServiceImpl implements MessageResultService {

	@Autowired
	MessageResultRepo messageResultRepo;

//	@Transactional
//	@Override
//	public void update(MessageResultDTO messageResultDTO) {
//		ModelMapper mapper = new ModelMapper();
//		mapper.createTypeMap(MessageResultDTO.class, MessageResult.class).setProvider(
//				p -> messageResultRepo.findById(messageResultDTO.getId()).orElseThrow(NoResultException::new));
//
//		MessageResult MessageResult = mapper.map(messageResultDTO, MessageResult.class);
//		messageResultRepo.save(MessageResult);
//	}

	@Override
	public MessageResultDTO get(Integer id) {
		return messageResultRepo.findById(id).map(messageResult -> convert(messageResult))
				.orElseThrow(NoResultException::new);
	}

	@Override
	public ResponseDTO<List<MessageResultDTO>> find(SearchDTO searchDTO) {
		List<Sort.Order> orders = Optional.ofNullable(searchDTO.getOrders()).orElseGet(Collections::emptyList).stream()
				.map(order -> {
					if (order.getOrder().equals(SearchDTO.ASC))
						return Sort.Order.asc(order.getProperty());

					return Sort.Order.desc(order.getProperty());
				}).collect(Collectors.toList());

		Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), Sort.by(orders));

		Page<MessageResult> page = messageResultRepo.findAll(pageable);

		ResponseDTO<List<MessageResultDTO>> responseDTO = new ModelMapper().map(page, ResponseDTO.class);
		responseDTO.setData(page.get().map(MessageResult -> convert(MessageResult)).collect(Collectors.toList()));
		return responseDTO;
	}

	@Override
	public StatisticData statistic(SearchMessageResultDTO searchMessageResultDTO) {
		Date start = null;
		Date end = null;
		String type = "";
		if (StringUtils.hasText(searchMessageResultDTO.getType())) {
			type = searchMessageResultDTO.getType();
			System.out.println("type: " + type);
		}
		if (StringUtils.hasText(searchMessageResultDTO.getStartDate())) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				start = dateFormat.parse(String.valueOf(searchMessageResultDTO.getStartDate()));
				System.out.println("start: " + start);
			} catch (ParseException e) {
				// Xử lý lỗi khi không thể chuyển đổi thành kiểu Date
			}
		}
		if (StringUtils.hasText(searchMessageResultDTO.getEndDate())) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				end = dateFormat.parse(String.valueOf(searchMessageResultDTO.getEndDate()));
				System.out.println("end: " + end);
			} catch (ParseException e) {

			}
		}

		List<RawData> rawDataList = getRawData(type, start, end);
		StatisticData statisticData = convertToStatisticData(rawDataList);
		return statisticData;
	}

	public List<RawData> getRawData(String type, Date start, Date end) {
		if (("day".equals(type))) {
			List<Object[]> results = messageResultRepo.getSecondlySum(start, end);
			return results.stream().map(result -> new RawData((String) result[0], (BigInteger) result[1],
					(BigInteger) result[2], (BigInteger) result[3])).collect(Collectors.toList());
		}
		if (("month".equals(type))) {
			List<Object[]> results = messageResultRepo.getHourlySum(start, end);
			return results.stream().map(result -> new RawData((String) result[0], (BigInteger) result[1],
					(BigInteger) result[2], (BigInteger) result[3])).collect(Collectors.toList());
		}
		if (("year".equals(type))) {
			List<Object[]> results = messageResultRepo.getDailySum(start, end);
			return results.stream().map(result -> new RawData((String) result[0], (BigInteger) result[1],
					(BigInteger) result[2], (BigInteger) result[3])).collect(Collectors.toList());
		}
		if (("all".equals(type))) {
			List<Object[]> results = messageResultRepo.getMonthlySum(start, end);
			return results.stream().map(result -> new RawData((String) result[0], (BigInteger) result[1],
					(BigInteger) result[2], (BigInteger) result[3])).collect(Collectors.toList());
		}
		return null;
	}

	public StatisticData convertToStatisticData(List<RawData> rawDataList) {

		int size = rawDataList.size();

		String[] timeStamps = new String[size];
		BigInteger[] totalHam = new BigInteger[size];
		BigInteger[] totalSpam = new BigInteger[size];
		BigInteger[] totalSuspicious = new BigInteger[size];

		for (int i = 0; i < size; i++) {
			RawData rawData = rawDataList.get(i);
			timeStamps[i] = rawData.getTime_stamps();
			totalHam[i] = rawData.getHam();
			totalSpam[i] = rawData.getSpam();
			totalSuspicious[i] = rawData.getSuspicios();
		}
		StatisticData statisticData = new StatisticData();
		statisticData.setTimeStamps(timeStamps);
		statisticData.setTotalHam(totalHam);
		statisticData.setTotalSpam(totalSpam);
		statisticData.setTotalSuspicious(totalSuspicious);

		return statisticData;
	}

	private MessageResultDTO convert(MessageResult messageResult) {
		return new ModelMapper().map(messageResult, MessageResultDTO.class);
	}

}
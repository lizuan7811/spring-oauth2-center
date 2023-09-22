package spring.boot.oath2.scrabdatas.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.boot.oath2.scrabdatas.entity.StockHistEntity;
import spring.boot.oath2.scrabdatas.entity.pk.StockHistEntityPk;
import spring.boot.oath2.scrabdatas.exception.FindHistDataException;
import spring.boot.oath2.scrabdatas.model.StockHistModel;
import spring.boot.oath2.scrabdatas.persistent.SelectType;
import spring.boot.oath2.scrabdatas.persistent.StockHistRepo;
import spring.boot.oath2.scrabdatas.request.HistStockRequest;
import spring.boot.oath2.scrabdatas.service.HistStockDataService;

@Service
public class HistStockDataServiceImpl implements HistStockDataService {

	@Autowired
	private StockHistRepo stockHistRepo;

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<StockHistModel> selectHists() {
		List<StockHistModel> respList = new ArrayList<StockHistModel>();
		return findStkForTest();
	}

	/**
	 * 查one code range day
	 */
	private List<StockHistModel> findStkForTest() {
		List<StockHistModel> respList = new ArrayList<StockHistModel>();
		
		List<StockHistEntity> resultList = stockHistRepo.findByPkBetweenStartDtAndEndDt("2330", "112-01-01", "112-08-01");
		resultList.stream().forEach(result -> {
			StockHistModel respModel = new StockHistModel();
			BeanUtils.copyProperties(result, respModel);
			respList.add(respModel);
		});
		return respList;
	}

	@Override
	public List<StockHistModel> selectHists(HistStockRequest histStockRequest) throws FindHistDataException {
		List<StockHistModel> respList = new ArrayList<StockHistModel>();
		String selectStr = histStockRequest.getSelectType();

		SelectType selectType = SelectType.getSelectType(selectStr);
		String stockCode = histStockRequest.getStockCode();
		String startDt = histStockRequest.getStartDt();
		String endDt = histStockRequest.getEndDt();

		switch (selectType) {
//			查一支一天
		case STKCODE_ONEDAY:
			if (startDt.equals(endDt) && StringUtils.isNotBlank(stockCode)) {
				respList = findStkOneADay(histStockRequest.getStockCode(), startDt);
			}
			break;
//			查一支多天
		case STKCODE_MOREDAY:
			if (StringUtils.isNotBlank(stockCode) && validDate(startDt, endDt))
				respList = findStkMoreDay(stockCode, startDt, endDt);
			break;
//			查一支所有天
		case STKCODE_ALL:
			respList = findStkAll(histStockRequest.getStockCode());
			break;
//			所有支一天
		case ALLCODE_ONEDAY:
			if (!startDt.equals(endDt)) {
				throw new FindHistDataException("CRAWING.HISTENDDATE.NOTVALID");
			}
			respList = findStkallOneDay(histStockRequest.getStartDt());
			break;
		}
		return respList;
	}

	/**
	 * 查one code a day
	 */
	private List<StockHistModel> findStkOneADay(String stockCode, String aDt) {
		Optional<StockHistEntity> result = stockHistRepo.findById(new StockHistEntityPk(stockCode, aDt));
		List<StockHistModel> respList = new ArrayList<StockHistModel>();
		StockHistModel respModel = new StockHistModel();
		if (!result.isEmpty()) {
			System.out.println(">>>" + result.get());
			BeanUtils.copyProperties(result.get(), respModel);
			respList.add(respModel);
		}
		return respList;
	}

	/**
	 * 查one code range day
	 */
	private List<StockHistModel> findStkMoreDay(String stockCode, String startDt, String endDt) {
		List<StockHistModel> respList = new ArrayList<StockHistModel>();
		if (validDate(startDt, endDt)) {
			List<StockHistEntity> resultList = stockHistRepo.findByPkBetweenStartDtAndEndDt(stockCode, startDt, endDt);
			resultList.stream().forEach(result -> {
				StockHistModel respModel = new StockHistModel();
				BeanUtils.copyProperties(result, respModel);
				respList.add(respModel);
			});
		}
		return respList;
	}

	private List<StockHistModel> findStkallOneDay(String aDt) {
		List<StockHistModel> respList = new ArrayList<StockHistModel>();
		List<StockHistEntity> resultList = stockHistRepo.findStkallOneDay(aDt);
		resultList.stream().forEach(result -> {
			StockHistModel respModel = new StockHistModel();
			BeanUtils.copyProperties(result, respModel);
			respList.add(respModel);
		});

		return respList;
	}

	private List<StockHistModel> findStkAll(String stockCode) {
		List<StockHistModel> respList = new ArrayList<StockHistModel>();
		List<StockHistEntity> resultList = stockHistRepo.findAllByStockCode(stockCode);
		resultList.stream().forEach(result -> {
			StockHistModel respModel = new StockHistModel();
			BeanUtils.copyProperties(result, respModel);
			respList.add(respModel);
		});
		return respList;
	}

	private boolean validDate(String startDt, String endDt) {
		boolean isValid = false;
		if (!startDt.equals(endDt)) {
			Chronology chrono = MinguoChronology.INSTANCE;
			DateTimeFormatter df = new DateTimeFormatterBuilder().parseLenient().appendPattern("yyy-MM-dd")
					.toFormatter().withChronology(chrono).withDecimalStyle(DecimalStyle.of(Locale.getDefault()));
			ChronoLocalDate startDate = chrono.date(df.parse(startDt));
			ChronoLocalDate endDate = chrono.date(df.parse(endDt));

			isValid = startDate.isBefore(endDate);
//			isValid = LocalDateTime.parse(fmtStartDt.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
//					.isBefore(LocalDateTime.parse(fmtendDt.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		}
		return isValid;
	}

}

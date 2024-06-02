package oauth2ResourcesServer.scrabdatas.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import oauth2ResourcesServer.scrabdatas.entity.StockHistEntity;
import oauth2ResourcesServer.scrabdatas.model.StockRSI;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import oauth2ResourcesServer.scrabdatas.exception.FindHistDataException;
import oauth2ResourcesServer.scrabdatas.model.StockHistModel;
import oauth2ResourcesServer.scrabdatas.request.HistStockRequest;
import oauth2ResourcesServer.scrabdatas.response.HistStockResponse;
import oauth2ResourcesServer.scrabdatas.service.HistStockDataService;

/**
 * 查詢歷史Stock Data的Controller
 */
@Slf4j
@RestController
@RequestMapping(value = "/findhist")
@CrossOrigin(origins = "http://localhost:9999")
public class HistStockDataController {

	private final HistStockDataService histStockDataService;

	@Autowired
	public HistStockDataController(HistStockDataService histStockDataService) {
		this.histStockDataService = histStockDataService;
	}

	/**
	 * 查一個code一段時間
	 */
	@PostMapping(value = "/stkcode")
	public HistStockResponse selectHists(@RequestBody @Validated HistStockRequest histStockRequest) {
		HistStockResponse histStockResponse = new HistStockResponse();
		log.debug(">>> selectHists! Request: {}!", histStockRequest);
		try {
			List<StockHistModel> resultList = histStockDataService.selectHists(histStockRequest);
			histStockResponse.setData(resultList);
			System.out.println(resultList);
		} catch (FindHistDataException fhde) {
			fhde.printStackTrace();
		}
		return histStockResponse;
	}

	/**
	 * 查一個code一段時間
	 */
	@GetMapping(value = "/getStock")
//	@CrossOrigin(origins = "http://localhost:9999/findhist/getStock") 
	public String selectHists() {
		System.out.println("~~~getStock");
		log.debug("~~~getStock");
		String resultString = Strings.EMPTY;
		try {
			ObjectMapper objMapper = new ObjectMapper();
			resultString = objMapper.writeValueAsString(histStockDataService.selectHists());
			System.out.println(">>>"+resultString);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return resultString;
	}

	/**
	* @Description: 傳入要查詢的股票代碼，查詢RSI
	* @date: 2024/5/18
	* @time: 下午 03:10
	**/
	@GetMapping(value = "/queryRsi", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockRSI> queryRSI(@RequestParam List<String> stockCodes, Integer periodDays){
		return histStockDataService.queryRSI(stockCodes,periodDays);
	}

	@GetMapping(value = "/queryMAs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<StockHistEntity> queryMAs(@RequestParam List<String> stockCodes, Integer periodDays){
		return histStockDataService.queryMAs(stockCodes,periodDays);
	}

}

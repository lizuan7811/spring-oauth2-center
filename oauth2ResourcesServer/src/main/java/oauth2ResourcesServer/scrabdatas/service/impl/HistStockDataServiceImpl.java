package oauth2ResourcesServer.scrabdatas.service.impl;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import liquibase.pro.packaged.D;
import lombok.extern.log4j.Log4j2;
import oauth2ResourcesServer.scrabdatas.model.RSI;
import oauth2ResourcesServer.scrabdatas.model.StockRSI;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import oauth2ResourcesServer.scrabdatas.entity.StockHistEntity;
import oauth2ResourcesServer.scrabdatas.entity.pk.StockHistEntityPk;
import oauth2ResourcesServer.scrabdatas.exception.FindHistDataException;
import oauth2ResourcesServer.scrabdatas.model.StockHistModel;
import oauth2ResourcesServer.scrabdatas.persistent.SelectType;
import oauth2ResourcesServer.scrabdatas.persistent.StockHistRepo;
import oauth2ResourcesServer.scrabdatas.request.HistStockRequest;
import oauth2ResourcesServer.scrabdatas.service.HistStockDataService;

@Service
@Log4j2
public class HistStockDataServiceImpl implements HistStockDataService {

    private final StockHistRepo stockHistRepo;

    private DecimalFormat df = new DecimalFormat("#.00");

    @Autowired
    public HistStockDataServiceImpl(StockHistRepo stockHistRepo) {
        this.stockHistRepo = stockHistRepo;
    }

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<StockHistModel> selectHists() {
        return findStkForTest();
    }


    @Override
    public List<StockRSI> queryRSI(List<String> stockCodes, Integer periodDays) {
        // TODO 傳入的Stock code不為empty，開始計算，通常以當日之前的14天計算，可以自行設定天數，最少要14天。
        Map<String, String> fmtedDateMap = getFmtedDtMap(periodDays);
        String startDate = fmtedDateMap.get("startDate");
        String endDate = fmtedDateMap.get("endDate");

        List<StockRSI> rsiResults = new ArrayList<>();
        if (!stockCodes.isEmpty()) {
            List<List<StockHistEntity>> queryList = stockCodes.stream().map(code -> stockHistRepo.findByPkBetweenStartDtAndEndDt(code, startDate, endDate)).collect(Collectors.toList());

            rsiResults = queryList.stream().map(stkList -> caculateRsi(stkList)).collect(Collectors.toList());

        } else {
            // TODO 如果沒有傳入任何值，就是查詢全部股票的RSI，回傳RSI有大於70或小於30的部分。
            Map<String, List<StockHistEntity>> queryMap = stockHistRepo.findStkallPeriodDay(startDate, endDate).stream().collect(Collectors.groupingBy(StockHistEntity::getStockCode));
            rsiResults = queryMap.entrySet().stream().map(entry -> caculateRsi(entry.getValue())).collect(Collectors.toList());
        }
        log.debug(">>>RsiResults {}", rsiResults);

        return rsiResults;
    }

    @Override
    public List<StockHistEntity> queryMAs(List<String> stockCodes, Integer periodDays) {

        List<StockHistEntity> collectStockHists = new ArrayList<>();

        Map<String, String> fmtedDateMap = getFmtedDtMap(periodDays * 2);

        String startDate = fmtedDateMap.get("startDate");
        String endDate = fmtedDateMap.get("endDate");


        Map<String, List<StockHistEntity>> closePricesByStockCode = null;
        if (!stockCodes.isEmpty()) {
            closePricesByStockCode = stockCodes.stream()
                    .collect(Collectors.toMap(
                            code -> code,
                            code -> {
                                List<StockHistEntity> results = stockHistRepo.findByPkBetweenStartDtAndEndDt(code, startDate, endDate);
                                return results.stream()
                                        .map(entry -> {
                                            String tmpPrice = entry.getEndPrice().replace(",", "").replace("--", "0.0");
                                            entry.setEndPrice(tmpPrice);
                                            return entry;
                                        })
                                        .collect(Collectors.toList());
                            }
                    ));
        } else {
            closePricesByStockCode = stockHistRepo.findBeforDatasFmCurDate(endDate).stream().collect(Collectors.groupingBy(
                    StockHistEntity::getStockCode,
                    Collectors.mapping(
                            entry -> {
                                String tmpPrice = entry.getEndPrice().replace(",", "").replace("--", "0.0");
                                entry.setEndPrice(tmpPrice);
                                return entry;
                            },
                            Collectors.toList()
                    )
            ));
        }
        StringBuffer stringBuffer = new StringBuffer();
        closePricesByStockCode.entrySet().stream().filter(entries -> checkValueIsZero(entries.getValue())).filter(entries -> entries.getValue().size() >= periodDays).forEach(entries -> {

//        EMA（今日）= （今日收盤價 × 2 / （n + 1）） +（昨日EMA × （n - 1）/（n + 1））
            List<String> ema = calculateEma(entries.getValue(), periodDays, df);
//        WMA = （P1 × W1 + P2 × W2 + … + Pn × Wn）/ （W1 + W2 + … + Wn）
            // 計算WMA
            List<String> wma = calculateWma(entries.getValue(), periodDays, df);
            String lastDateEndPrice = entries.getValue().get(entries.getValue().size() - 1).getEndPrice();
            if (Double.parseDouble(lastDateEndPrice) < Double.parseDouble(ema.get(ema.size() - 1)) && Double.parseDouble(lastDateEndPrice) < Double.parseDouble(ema.get(wma.size() - 1))) {
                collectStockHists.add(entries.getValue().get(entries.getValue().size() - 1));
                log.debug(">>> StockCode: {} | Lastdate EndPrice: {} | ema: {} | wma: {} !", entries.getKey(), entries.getValue().get(entries.getValue().size() - 1).getEndPrice(), ema, wma);
            }
        });
        // 取得小於平均線的資料
        //


        return collectStockHists;
    }

    private boolean checkValueIsZero(List<StockHistEntity> entries) {
        double sum = entries.stream()
                .mapToDouble(
                        entry -> {
                            return Double.parseDouble(entry.getEndPrice());
                        }
                )
                .sum();
        return sum != 0.0;
    }

    /**
     * @Description: 指數移動平均  EMA（今日）= （今日收盤價 × 2 / （n + 1）） +（昨日EMA × （n - 1）/（n + 1））
     * @date: 2024/5/26
     * @time: 上午 03:02
     **/
    public List<String> calculateEma(List<StockHistEntity> prices, int periodDays, DecimalFormat df) {
        List<String> emas = new ArrayList<>();
        double multiplier = 2.0 / (periodDays + 1);

        // Calculate the initial EMA value
        double ema = Double.parseDouble(prices.get(0).getEndPrice());

        for (int i = 1; i < periodDays; i++) {
            ema = (Double.parseDouble(prices.get(i).getEndPrice()) - ema) * multiplier + ema;
        }
        emas.add(df.format(ema));

        // Calculate the rest of the EMA values
        for (int i = periodDays; i < prices.size(); i++) {
            ema = (Double.parseDouble(prices.get(i).getEndPrice()) - ema) * multiplier + ema;
            emas.add(df.format(ema));
        }
        return emas;
    }

    /**
     * @Description: 加權移動平均 WMA = （P1 × W1 + P2 × W2 + … + Pn × Wn）/ （W1 + W2 + … + Wn）
     * @date: 2024/5/26
     * @time: 上午 03:02
     **/
    public List<String> calculateWma(List<StockHistEntity> prices, int period, DecimalFormat df) {
        List<Integer> weights = IntStream.rangeClosed(1, period).boxed().collect(Collectors.toList());

        List<Double> normalizedWeights = normalizeWeights(weights);
//        if (prices.size() != normalizedWeights.size()) {
//            throw new IllegalArgumentException("價格和權重數據集的大小必須相等");
//        }
        List<String> wmas = new ArrayList<>();
        for (int i = period - 1; i < prices.size(); i++) {
            double sumPriceTimesWeight = 0;
            double sumWeights = 0;

            for (int j = 0; j < period; j++) {
                sumPriceTimesWeight += Double.parseDouble(prices.get(i - j).getEndPrice()) * normalizedWeights.get(j);
                sumWeights += normalizedWeights.get(j);
            }

            double wma = sumPriceTimesWeight / sumWeights;
            wmas.add(df.format(wma));
        }
        return wmas;
    }

    /**
     * @Description: 歸一化權重
     * @date: 2024/5/26
     * @time: 上午 03:02
     **/
    public List<Double> normalizeWeights(List<Integer> weights) {
        List<Double> normalizedWeights = new ArrayList<>();
        int sumWeights = weights.stream().mapToInt(Integer::intValue).sum();

        for (int weight : weights) {
            double normalizedWeight = (double) weight / sumWeights;
            normalizedWeights.add(normalizedWeight);
        }

        return normalizedWeights;
    }

    private StockRSI caculateRsi(List<StockHistEntity> stkList) {
        if (!stkList.isEmpty()) {
            List<Double> rsiValues = new ArrayList<>();
            StockHistEntity baseEntity = stkList.get(0);
            RSI rsi = new RSI(stkList.size());
            double rsiValue = 0.0;
            String preValue = baseEntity.getEndPrice().replace(",", "").replace("--", "0.0");
            for (int index = 1; index < stkList.size(); index++) {
                StockHistEntity tmpEntity = stkList.get(index);
                String curValue = tmpEntity.getEndPrice().replace(",", "").replace("--", "0.0");
                rsiValue = rsi.calculate(Double.valueOf(preValue), Double.valueOf(curValue));
                preValue = curValue;
            }
            rsiValues.add(rsiValue);
            return StockRSI.builder().stockCode(baseEntity.getStockCode()).stockName(baseEntity.getSecuritiesEntity().getSecurityName()).rsiValues(rsiValues).build();
        }
        return StockRSI.builder().build();
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
        List<StockHistEntity> resultList = stockHistRepo.findStkallOneDay();
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

    private SimpleDateFormat getGregoDateFormat() {
        return new SimpleDateFormat("yyy-MM-dd") {
            @Override
            public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
                Calendar cal = new GregorianCalendar();
                cal.setTime(date);
                int year = cal.get(Calendar.YEAR) - 1911;
                toAppendTo.append(year);
                toAppendTo.append('-');
                int month = cal.get(Calendar.MONTH) + 1;
                if (month < 10) {
                    toAppendTo.append('0');
                }
                toAppendTo.append(month);
                toAppendTo.append('-');
                int day = cal.get(Calendar.DAY_OF_MONTH);
                if (day < 10) {
                    toAppendTo.append('0');
                }
                toAppendTo.append(day);
                return toAppendTo;
            }
        };
    }

    private Map<String, String> getFmtedDtMap(Integer periodDays) {
        Map<String, String> fmtDtMap = new HashMap<>();
        Calendar currentCalendar = Calendar.getInstance();
        Date curDate = currentCalendar.getTime();
        currentCalendar.add(Calendar.DAY_OF_MONTH, periodDays != null && periodDays > 15 ? -Integer.valueOf(periodDays) : -15);
        Date periodDate = currentCalendar.getTime();
        SimpleDateFormat sdf = getGregoDateFormat();
        String startDate = sdf.format(periodDate);
        String endDate = sdf.format(curDate);
        fmtDtMap.put("startDate", startDate);
        fmtDtMap.put("endDate", endDate);

        return fmtDtMap;
    }

}

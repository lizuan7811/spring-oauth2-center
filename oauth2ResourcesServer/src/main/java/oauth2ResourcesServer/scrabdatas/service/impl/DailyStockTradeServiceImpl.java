package oauth2ResourcesServer.scrabdatas.service.impl;

import freemarker.template.SimpleDate;
import lombok.extern.log4j.Log4j2;
import oauth2ResourcesServer.scrabdatas.entity.StockEntity;
import oauth2ResourcesServer.scrabdatas.entity.StockHistEntity;
import oauth2ResourcesServer.scrabdatas.service.DailyStockTradeService;
import org.apache.commons.beanutils.BeanUtils;
import org.jvnet.hk2.annotations.Service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Log4j2
public class DailyStockTradeServiceImpl implements DailyStockTradeService {

    List<String> params= Arrays.asList("stockCode","date","transactVolume","sharesTradedNum","totalPrice","startPrice","highestPrice","lowestPrice","endPrice","upAndDown");

    public void testUpdateDailyTradeData() {
        String txDate=getTxDate();

        String testString="=\"0050\",\"元大台灣50\",\"7,278,652\",\"13,023\",\"1,212,643,672\",\"167.25\",\"167.40\",\"165.30\",\"167.20\",\"-\",\"0.05\",\"167.10\",\"4\",\"167.20\",\"40\",\"0.00\",";

        if(testString.startsWith("=")){
            testString = testString.substring(1,testString.length());
        }
        System.out.println(testString);
        String[] tmpStrArray=testString.split("\",\"");
        System.out.println(tmpStrArray.length);
        String tmpVal="";
        Map<String,String> beanMap=new HashMap<>();
        for(int i=0;i<=10;i++){
            if(i==9 || i==10){
                tmpVal+=tmpStrArray[i];
                if(i==10){
                    beanMap.put(params.get(i-1),tmpVal);
                }
            }else if(i==1){
                beanMap.put(params.get(i),txDate);
            }else{
                beanMap.put(params.get(i),tmpStrArray[i]);
            }
        }
        StockHistEntity stockHistEntity=new StockHistEntity();
        try {
            BeanUtils.populate(stockHistEntity,beanMap);
        } catch (IllegalAccessException e) {
            log.debug(">>> ",e);
        } catch (InvocationTargetException e) {
            log.debug(">>> ",e);
        }
    }

    private String getTxDate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
    }
    @Override
    public void updateDailyTradeData() {


    }
}

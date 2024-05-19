package oauth2ResourcesServer.scrabdatas.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
//import oauth2ResourcesServer.scrabdatas.entity.StockcodeTypeEntity;

@Data
public class HtmlParseResult {
	public HtmlParseResult() {
		this.urlMap=new HashMap<String,List<Object>>();
//		this.parsedResultList=new ArrayList<StockcodeTypeEntity>();
	}
	
	private Map<String,List<Object>> urlMap;
	
//	private List<StockcodeTypeEntity> parsedResultList;
	
	private String tmpType;

}

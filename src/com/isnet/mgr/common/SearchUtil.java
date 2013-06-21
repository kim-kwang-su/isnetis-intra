package com.isnet.mgr.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.isnet.mgr.domain.Rule;
@SuppressWarnings("unchecked")

public class SearchUtil {


	public static Map<String, Object> getQueryMap(String filters) throws Exception{
		
		System.out.println("[SearchUtil] [getQueryMap] start");
		
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory jFac = new JsonFactory();
		JsonParser jParser = jFac.createJsonParser(filters);

		TypeReference<HashMap<String,Object>> typeRef  = new TypeReference<HashMap<String,Object>>() {}; 
		HashMap<String,Object> o =  mapper.readValue(jParser, typeRef); 

		List<Map<String, Object>> list= (List<Map<String, Object>>)o.get("rules");
		System.out.println("[SearchUtil] [getQueryMap] rules ["+list+"]");


		ArrayList<Rule> param = new ArrayList<Rule>();

		String groupOp = (String)o.get("groupOp");
		for(int i = 0; i < list.size();i++) {

			String column = (String)list.get(i).get("field");

			String sign = (String)list.get(i).get("op");
			String operator ="";

			if(sign.equals("ge")) {
				operator = ">=";
			}else if(sign.equals("le")){
				operator = "<=";
			}else if(sign.equals("eq")){
				operator =  "=";
			}else if(sign.equals("cn")){
				operator = "like";
			}
			Object value= list.get(i).get("data"); 
			param.add(new Rule(column, operator, sign, value));
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("param", param);
		resultMap.put("groupOp",groupOp);
		
		System.out.println("[SearchUtil] [getQueryMap] end");
		return resultMap;


	}
}

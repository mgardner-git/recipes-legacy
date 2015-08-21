package org.recipes.autocomplete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class AJAXUtils {

	public static List<Map<String,String>> getAutoCompleteAJAX(List<AutoComplete> acs){
		List<Map<String,String>> results = new ArrayList<Map<String,String>>();
		try{			
			for (AutoComplete ac : acs){
				Map<String,String> map = new HashMap<String, String>();
				map.put("label", ac.getLabel());
				map.put("value", ac.getValue());
				results.add(map);
			}
			return results;
		}catch (Exception jme){
			Map<String,String> errMap = new HashMap<String,String>();
			errMap.put("label", "error");
			errMap.put("value", jme.getMessage());
			results.add(errMap);
			return results;
		}
	}
}

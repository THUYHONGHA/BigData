import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapReduce {
	
	//Reducer Input
	public static List<GroupByPair> getGroupByPairList(List<Pair> pairList) {
		
		HashMap<String, ArrayList<Integer>> groupByPairMap= new HashMap<String, ArrayList<Integer>>();
		for (Pair<String, Integer> pair : pairList) {
			if (groupByPairMap.get(pair.getKey()) == null) {
				groupByPairMap.put(pair.getKey(), new ArrayList<Integer>());
			}
			groupByPairMap.get(pair.getKey()).add(pair.getValue());
		}
		
		Map<String, ArrayList<Integer>> treeMap = new TreeMap<String, ArrayList<Integer>>(groupByPairMap);
		
		List<GroupByPair> groupByPairList = new ArrayList<>();
		Set<String> keys = treeMap.keySet();
		//System.out.println("Reducer Input");
		for (String key : keys) {
			GroupByPair<String, Integer> groupPair = new GroupByPair<String, Integer>(key, treeMap.get(key));
			groupByPairList.add(groupPair);
			//System.out.println(groupPair);
		}

		return groupByPairList;		
	}
	
	//reducer Output
	public static List<Pair> getSumGroupByPairList(List<GroupByPair> groupByPairList) {
		Pair<String, Integer> pair;
		List<Pair> sumGroupByPairList = new ArrayList<>();
		//System.out.println("*********reducer Output ************");
		for (GroupByPair groupByPair : groupByPairList) {
			pair = new Pair<String, Integer>();
			pair.setKey(groupByPair.getKey().toString());
			pair.setValue(groupByPair.getValues().size());
			sumGroupByPairList.add(pair);
			//System.out.println(pair.toString());
		}
		return sumGroupByPairList;		
	}


}

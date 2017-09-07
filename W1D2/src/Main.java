
import java.util.Collections;
import java.util.List;


public class Main {
	public static void main(String[] args) {
		String inputFile = "D:/Hong/BigData/Homework/W1D2/src/testDataForW1D1.txt";

		Mapper mapper = new Mapper();
		List<Pair<String, Integer>> list = mapper.createPairList(inputFile);

		Reducer reducer = new Reducer();
		List<GroupByPair> groupList = reducer.createReduceInput(list);
		
		System.out.println("Reducer Input: ");
		
		Collections.sort(groupList, GroupByPair.MyComparator);
		for (GroupByPair groupByPair : groupList) {
			System.out.println("< " + groupByPair.getKey() + " , " + groupByPair.getValues().toString() + " >");
		}
		
		System.out.println("Reducer Output: ");
		reducer.reduce(groupList);
		
	}
}

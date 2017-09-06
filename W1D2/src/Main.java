import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		MatchData matchData = new MatchData();
		MapReduce mapReduce = new MapReduce();
		List<Pair> pairList = matchData.getMapper();
		String outputTextFile = "./src/outputDataForW1D2.txt";
		
		System.out.println("Writing to the text file: " + outputTextFile);
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputTextFile))) {
			
			//MatchData
			writer.write("*********Mapper************");
			writer.newLine();
			for (int i = 0; i < pairList.size(); i++) {
				writer.write(pairList.get(i).toString());
				writer.newLine();
			}
			
			//Reduce Input
			writer.write("*********Reduce Input************");
			writer.newLine();
			List<GroupByPair> groupByPairList = mapReduce.getGroupByPairList(pairList);
			for (int i = 0; i < groupByPairList.size(); i++) {
				writer.write(groupByPairList.get(i).toString());
				writer.newLine();
			}
			
			//Reduce OutPut
			writer.write("*********Reduce Output************");
			writer.newLine();
			List<Pair> sumGroupByPairList= mapReduce.getSumGroupByPairList(groupByPairList);
			for (int i = 0; i < sumGroupByPairList.size(); i++) {
				writer.write(sumGroupByPairList.get(i).toString());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}

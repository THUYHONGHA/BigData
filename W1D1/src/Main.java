import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		String inputTextFile = "D:/Hong/BigData/Homework/W1D1/src/testDataForW1D1.txt";
		String outputTextFile = "D:/Hong/BigData/Homework/W1D1/src/outputDataForW1D1.txt";

		MatchData matchData = new MatchData();
		List<Pair> list = matchData.pairList(inputTextFile);

		System.out.println("Writing to the text file: " + outputTextFile);
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputTextFile))) {
			for (Pair pair : list) {
				writer.write("(" + pair.getKey().toString() + "," + pair.getValue().toString() + ")");
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

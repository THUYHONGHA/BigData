import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchData {

	private static final String REGEX = "[a-z]*|[a-z]*.$";

	private String[] readFileAndSplit(String fileName) {
		try {
			return new String(Files.readAllBytes(Paths.get(fileName))).toLowerCase().replace("\"", "")
					.split(" |-|[\\r\\n]+");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Pair> pairList(String fileName) {
		List<Pair> pairList = new ArrayList<>();
		String[] wordArray = readFileAndSplit(fileName);

		Pair<String, Integer> p;

		for (String word : wordArray) {
			if (word.matches(REGEX) && !word.equals("")) {
				word = word.replace(".", "");
				word = word.replace(",", "");
				p = new Pair<>();
				p.setKey(word);
				p.setValue(1);
				pairList.add(p);
			}
		}
		Collections.sort(pairList, Pair.MyComparator);
		return pairList;
	}

}

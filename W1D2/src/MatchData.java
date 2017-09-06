import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MatchData {
	private static String PATTERN = "[A-Za-z-\".,]+";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Pair> getMapper() {
		List<String> validData = readFromFile("/src/testDataForW1D1.txt");
		
		if (null == validData || validData.isEmpty())
			System.out.println("Invalid data");;
		List<Pair> pairList = new ArrayList<>();
		Pair<String, Integer> pair;

		for (String validString : validData) {
			if (validString.matches(PATTERN) && !validString.equals("")) {
				if (validString.indexOf(".") == validString.length() - 1)
					validString = validString.substring(0, validString.length()-1);
				else if (validString.indexOf(".") > 0)
					validString = validString.replaceAll(".", "");
				if (validString.indexOf(",") >= 0)
					validString = validString.substring(0, validString.length()-1);
				if (validString.indexOf("\"") >= 0)
					validString = validString.substring(1, validString.length()-1);
				String tempHyphen = "";
				if (validString.indexOf("-") >= 0){
					String[] splitValue = validString.split("-");
					validString = splitValue[0];
					tempHyphen = splitValue[1];
				}
				
				if(!validString.isEmpty()){
					pair = new Pair<String, Integer>();
					pair.setKey(validString.toLowerCase());
					pair.setValue(1);
					pairList.add(pair);
				}
				
				if(!tempHyphen.isEmpty()){
					pair = new Pair<String, Integer>();
					pair.setKey(tempHyphen.toLowerCase());
					pair.setValue(1);
					pairList.add(pair);
				}
			}
		}
		
		Collections.sort(pairList,(Comparator<? super Pair>) new MyComparator());
		return pairList;
	}
	
	public static List<String> readFromFile(String myDir) {
		List<String> lines = new ArrayList<String>();
		BufferedReader br = null;
		try {
			String filePath = new File(".").getAbsolutePath();
			br = new BufferedReader(new FileReader(filePath + myDir));

			String line;

			line = br.readLine();

			while (line != null) {
				//lines.add(line);		
				String[] myString = line.split(" ");
				for (int i = 0; i < myString.length; i++) {
					lines.add(myString[i]);
				}
				
				line = br.readLine();
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}

}

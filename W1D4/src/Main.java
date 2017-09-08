public class Main {

	public static void main(String[] args) {
		String inputMapper0File = "D:/Hong/BigData/Homework/W1D4/src/mapperInput0.txt";
		String inputMapper1File = "D:/Hong/BigData/Homework/W1D4/src/mapperInput1.txt";
		String inputMapper2File = "D:/Hong/BigData/Homework/W1D4/src/mapperInput2.txt";
		
		//Create a WordCount with 3 Mappers and 4 Producers
		WordCountCombining wordCount = new WordCountCombining(3, 4);
		
		//Read the input files and create the input for the Mappers
		wordCount.createInputForMapper(inputMapper0File, 0);
		wordCount.createInputForMapper(inputMapper1File, 1);
		wordCount.createInputForMapper(inputMapper2File, 2);
		//Print the Mapper Output
		wordCount.printMapperOuput();
		
		wordCount.createReducerInput();
		
		wordCount.printInput();
		
		wordCount.printOutput();
	}

}

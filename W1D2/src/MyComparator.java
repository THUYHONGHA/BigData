import java.util.Comparator;

public class MyComparator implements Comparator<Pair<String, Integer>>{

	@Override
	public int compare(Pair<String, Integer> arg0, Pair<String, Integer> arg1) {
		return arg0.getKey().compareToIgnoreCase(arg1.getKey());
	}

}

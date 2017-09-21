

import java.util.Comparator;

public class Pair<T extends Comparable<T>, S> implements Comparator<Pair<T,S>> {

	private T key;

	private S value;
	
	public Pair(){}
	
	public Pair(T key, S value){
		this.key = key;
		this.value = value;
	}

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public S getValue() {
		return value;
	}

	public void setValue(S value) {
		this.value = value;
	}

	public int compare(Pair o1, Pair o2) {
		return o1.getKey().compareTo(o2.getKey());
	}

	public static final Comparator<Pair> MyComparator = new Comparator<Pair>(){

        public int compare(Pair o1, Pair o2) {
            return o1.key.compareTo( o2.key); 
        }
       
    };


}


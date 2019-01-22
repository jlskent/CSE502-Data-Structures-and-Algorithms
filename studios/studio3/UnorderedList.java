package studio3;

import java.util.LinkedList;
import java.util.List;

public class UnorderedList<T extends Comparable<T>> implements PriorityQueue<T> {

	public LinkedList<T> list;
	public T minValue;
	
	public UnorderedList() {
		list = new LinkedList<T>();
	}
	
	@Override
	public boolean isEmpty() {
		if (this.list.size() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void insert(T thing) {
		this.list.addLast(thing);
	}

	@Override
	public T extractMin() {
		
		minValue = this.list.get(0);
		for (int i=0; i<this.list.size();i++) {
			if (this.list.get(i).compareTo(minValue) < 0){
				minValue =this.list.get(i);
			}
		}
		this.list.remove(minValue);
		return minValue;
		
		
	}

}

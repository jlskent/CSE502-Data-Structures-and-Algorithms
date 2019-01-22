package studio3;

public class OrderedArray<T extends Comparable<T>> implements PriorityQueue<T> {

	public T[] array;
	private int size;
	
	
	public T[] newArray;

	
	@SuppressWarnings("unchecked")
	public OrderedArray(int maxSize) {
		array = (T[]) new Comparable[maxSize];
		size = 0;
	}
	
	@Override
	public boolean isEmpty() {
		if(this.array[0] == null) {
			return true;
		}
		return false;
	}

	
	@Override
	public void insert(T thing) {
		for(int i=0; i<array.length;i++) {
			newArray[i] = array[i];
		}
		
		newArray[array.length] = thing;
	}
	
	@Override
	public T extractMin() {
		
		if (this.isEmpty() == false) {
			return array[0];
		}
		return null;
	}

}

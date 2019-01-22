package heaps;

import java.util.Random;
import java.util.UUID;

import javax.swing.JOptionPane;

import heaps.util.HeapToStrings;
import heaps.validate.MinHeapValidator;
import timing.Ticker;

public class MinHeap<T extends Comparable<T>> implements PriorityQueue<T> {

//	fields
	private Decreaser<T>[] array;
	private int size;
	private final Ticker ticker;

//constructor
	@SuppressWarnings("unchecked")
	public MinHeap(int maxSize, Ticker ticker) {
		this.array = new Decreaser[maxSize+1];
		this.size = 0;
		this.ticker = ticker;
	}


//	param: a value T for a new element
//	put a new element to last of heap and maintain heap property
	public Decreaser<T> insert(T thing) {
		Decreaser<T> ans = new Decreaser<T>(thing, this, ++size);
		if (array[size] != null) {
			System.out.println("array full");
		}
		array[size] = ans;	
		decrease(size);
		return ans;
	}


	
	
//	update location to remain heap property after Decreaser Obj's decreasing its value T
	void decrease(int loc) {
//		base case		
		if (loc ==1) {return;}

		
//		tick per recursion
		ticker.tick();
		
//		get Decreaser values
		T childValue = array[loc].getValue();
		T parentValue = array[loc/2].getValue();
		
//		swtich them recursively
		if (childValue.compareTo(parentValue) < 0) {
			swap(loc,loc/2);					
			this.decrease(loc/2);			
		}
	}
	
	
	
//	param:
//	take two locations and swap the Decreaser objects(location & value T both switched)
	public void swap(int from, int to) {
//		oh thing is private!!! cant switch
		Decreaser<T> flag = array[to];
		array[to] = array[from];
		array[from] = flag;	
		array[to].loc = to;
		array[from].loc = from;
	}
	
	

//	pop out the first element(smallest)
//	shrink the size by 1 and maintain heap property
	public T extractMin() {
		T ans = array[1].getValue();
//		System.out.println("original"+size);
		swap(1,size);
		array[size] = null;
//		System.out.println("last "+array[size]);
		--size;
//		System.out.println("new"+size);
		heapify(1);
		return ans;
	}

	
//	where here means parent
//	takes a starting position and heapify the children
	private void heapify(int where) {

//		return if out of bound
		if (where*2 > size) {
			return;
		}
		
//		base case:return if no child
		if (array[where*2] ==null && array[where*2+1] ==null) {
//			System.out.println("should exit");
			return;
		}

//		tick per recursion
		ticker.tick();

		
//		variables 
		Decreaser<T> leftChild = array[where*2];
		Decreaser<T> rightChild = array[where*2+1];
		T whereValue = array[where].getValue();

//		recursive heapify
//		if parent is bigger than any children, well if it has two
		if (leftChild !=null && rightChild !=null) {

			if (whereValue.compareTo(leftChild.getValue()) > 0 || whereValue.compareTo(rightChild.getValue()) > 0) {

				if (leftChild.getValue().compareTo(rightChild.getValue())< 0) {
					swap(where, leftChild.loc);
//					System.out.println("passing"+where*2);
					heapify(where*2);
				}
				else {
//					System.out.println("hi");
					swap(where, rightChild.loc);
//					System.out.println("passing"+ (where*2+1));
					heapify(2*where+1);
				}
			}
		}
		
//		if parent has only left child
		if (leftChild!= null && rightChild == null) {
			if (whereValue.compareTo(leftChild.getValue()) > 0) {
				swap(where,leftChild.loc);
				heapify(where*2);
			}
		}
		

//		if parent has only right child
		if (rightChild != null && leftChild == null) {
			if (whereValue.compareTo(rightChild.getValue()) > 0) {
				swap(where,rightChild.loc);
				heapify(where*2+1);
			}
		}
		
		
	}
	
	/**
	 * Does the heap contain anything currently?
	 * I implemented this for you.  Really, no need to thank me!
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * This method would normally not be present, but it allows
	 *   our consistency checkers to see if your heap is in good shape.
	 * @param loc the location
	 * @return the value currently stored at the location
	 */
	public T peek(int loc) {
		if (array[loc] == null)
			return null;
		else return array[loc].getValue();
	}

	/**
	 * Return the loc information from the Decreaser stored at loc.  They
	 *   should agree.  This method is used by the heap validator.
	 * @param loc
	 * @return the Decreaser's view of where it is stored
	 */
	public int getLoc(int loc) {
		return array[loc].loc;
	}

	public int size() {
		return this.size;
	}
	
	public int capacity() {
		return this.array.length-1;
	}
	

	/**
	 * The commented out code shows you the contents of the array,
	 *   but the call to HeapToStrings.toTree(this) makes a much nicer
	 *   output.
	 */
	public String toString() {
//		String ans = "";
//		for (int i=1; i <= size; ++i) {
//			ans = ans + i + " " + array[i] + "\n";
//		}
//		return ans;
		return HeapToStrings.toTree(this);
	}

	/**
	 * This is not the unit test, but you can run this as a Java Application
	 * and it will insert and extract 100 elements into the heap, printing
	 * the heap each time it inserts.
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		MinHeap<Integer> test = new MinHeap<Integer>(500, new Ticker());
//		debug only
		test.insert(7);
		test.insert(6);
		test.insert(5);
		test.insert(4);
		test.insert(3);
		test.insert(2);
		test.insert(1);

//		System.out.println(test.size);
//		System.out.println("test swap-------------");
//		System.out.println(test);
//		test.swap(1, 2);
//		System.out.println(test);
//		System.out.println(test.array[1].getValue());
//		System.out.println(test.array[1].loc);
//		System.out.println(test.array[2].getValue());
//		System.out.println(test.array[2].loc);
		System.out.println("-------------");
		test.heapify(1);
		System.out.println(test);
//		test.extractMin();
//		System.out.println(test);
//		System.out.println("-------------");

		
		
//		JOptionPane.showMessageDialog(null, "You are welcome to run this, but be sure also to run the TestMinHeap JUnit test");
		MinHeap<Integer> h = new MinHeap<Integer>(500, new Ticker());
		MinHeapValidator<Integer> v = new MinHeapValidator<Integer>(h);
		Random r = new Random();
		for (int i=0; i < 100; ++i) {
			v.check();
			h.insert(r.nextInt(1000));
			v.check();
//			System.out.println(HeapToStrings.toTree(h));
			//System.out.println("heap is " + h);
		}
		while (!h.isEmpty()) {
			int next = h.extractMin();
//			System.out.println("Got " + next);
		}
	}


}

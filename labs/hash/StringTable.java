package hash;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

//
// STRINGTABLE.JAVA
// A hash table mapping Strings to their positions in the the pattern sequence
// You get to fill in the methods for this part.
//
public class StringTable {
    
//	Record obj
//	key + list of integer
	
	
    private LinkedList<Record>[] buckets;
	private int nBuckets;
    // number of records currently stored in table --
    // must be maintained by all operations
    //
    public int size;
    
    
    // Create an empty table with nBuckets buckets
    @SuppressWarnings("unchecked")
	public StringTable(int nBuckets){
    	this.size = 0;
    	this.nBuckets = nBuckets;
    	this.buckets = new LinkedList[nBuckets];
    }
    
    /**
     * insert - inserts a record to the StringTable
     *
     * @param r
     * @return true if the insertion was successful, or false if a
     *         record with the same key is already present in the table.
     */
    public boolean insert(Record r){  

    	size++;
    	boolean isAbleToInsert = true;
    	int hashCode = stringToHashCode(r.key);
    	int index = toIndex(hashCode);

    	
//    	if there exits a record linkedlist buckets[index]
    	if(buckets[index]!= null) {
    		for (Record record:buckets[index]) {
    			if (record.key.compareTo(r.key) ==0 ){
//    	    		System.out.println("duplicate");
//    	    		line below should work 
    				isAbleToInsert = false;
    	    		return false;

    			}
    		}
//    		if no duplicate
    		if (isAbleToInsert = true) {
    			buckets[index].add(r);
	    		return true;

    		}
    	}
    	
    	
//    	create a new record list if null
    	else {
    		LinkedList<Record> bucket = new LinkedList<Record>();
			buckets[index]=bucket;
			buckets[index].add(r);
    	}
    	
//		System.out.println(isAbleToInsert);
    	return isAbleToInsert;
    }
    
    
    
    
    
    /**
     * find - finds the record with a key matching the input.
     *
     * @param key
     * @return the record matching this key, or null if it does not exist.
     */
    public Record find(String key){    	
    	int hashCode = stringToHashCode(key);
    	int index = toIndex(hashCode);
    	
    	try {
	    	for (Record record: buckets[index]) {
	    		if (record.key.compareTo(key) ==0) {
	        		return record;
	    		}
	    	}
    	}
	    catch(Exception e) {
//	         System.out.println("Quiet!");
//	         System.out.println("Exception occurred");
		}

    	return null;
    	
    }
    
    
    /**
     * remove - finds a record in the StringTable with the given key
     * and removes the record if it exists.
     *
     * @param key
     */
    public void remove(String key){    	
    	int hashCode = stringToHashCode(key);
    	int index = toIndex(hashCode);
    	if (buckets[index]!=null) {
	    	for (Record record: buckets[index]) {
	    		if (record.key.compareTo(key) ==0) {
	    			buckets[index].remove(record);
	    			size--;
	    			return;
	    		}
	    	}
    	}
    	
    }
    

    /**
     * toIndex - convert a string's hashcode to a table index
     *
     * As part of your hashing computation, you need to convert the
     * hashcode of a key string (computed using the provided function
     * stringToHashCode) to a bucket index in the hash table.
     *
     * You should use a multiplicative hashing strategy to convert
     * hashcodes to indices.  If you want to use the fixed-point
     * computation with bit shifts, you may assume that nBuckets is a
     * power of 2 and compute its log at construction time.
     * Otherwise, you can use the floating-point computation.
     */
    private int toIndex(int hashcode)
    {
    	// Fill in your own hash function here
    	int index;
    	int positiveIndex;

    	double A = 0.6734;
    	index = (int) ((A*hashcode)%1.0 * nBuckets);
    	positiveIndex = Math.abs(index);
//    	System.out.println(positiveIndex);
    	return positiveIndex;
    }
    
    
    /**
     * stringToHashCode
     * Converts a String key into an integer that serves as input to
     * hash functions.  This mapping is based on the idea of integer
     * multiplicative hashing, where we do multiplies for successive
     * characters of the key (adding in the position to distinguish
     * permutations of the key from each other).
     *
     * @param string to hash
     * @returns hashcode
     */
    int stringToHashCode(String key)
    {
    	int A = 1952786893;
	
    	int v = A;
    	for (int j = 0; j < key.length(); j++)
	    {
    		
    		char c = key.charAt(j);
    		v = A * (v + (int) c + j) >> 16;
	    }
	
    	return v;
    }

    /**
     * Use this function to print out your table for debugging
     * purposes.
     */
    public String toString() 
    {
    	StringBuilder sb = new StringBuilder();
	
    	for(int i = 0; i < nBuckets; i++) 
	    {
    		sb.append(i+ "  ");
    		if (buckets[i] == null) 
		    {
    			sb.append("\n");
    			continue;
		    }
    		for (Record r : buckets[i]) 
		    {
    			sb.append(r.key + "  ");
		    }
    		sb.append("\n");
	    }
    	return sb.toString();
    }
    
    

	
	
    
    
    
}

package avl;

import java.util.LinkedList;
import java.util.TreeSet;

public class AVLTree<T extends Comparable<T>> {

	private TreeNode<T> root;
	public int size;
	
	public AVLTree() {
	    this.root = null;
	    this.size = 0;
	}
		
	// exists()
	// Check whether a specified value exists in the set
	//
	public boolean exists(T value) {
	    return existsHelper(value, this.root);
	}
	
	//
	// existsHelper()
	// (Optionally) recursive procedure to traverse a tree
	// rooted at "root" to find a specified value.  
	//
	// RETURNS: true if the value is found, else false
	//
	private boolean existsHelper(T value, TreeNode<T> root) {
		if (root == null) { // not found
			return false;
	    } else {
	    	int comparison = value.compareTo(root.value);		
	    	if (comparison == 0) { // found
	    		return true;
	    	} else if (comparison < 0) { // still looking - go left
	    		return existsHelper(value, root.left);
	    	} else { // still looking - go right
	    		return existsHelper(value, root.right);
	    	}
	    }
	}
	
	////////////////////////////////////////////////////////
	
	//
	// min()
	// Return the minimum value in the set
	//
	// If the set is empty, result is undefined.
	//
	public T min() {
	    return minValueInSubtree(this.root);
	}
	
	//
	// minValueInSubTree()
	// Find the smallest value in the subtree rooted at
	// the specified node.
	//
	// ASSUMED: root is not null.
	//
	private T minValueInSubtree(TreeNode<T> root) {
	    while (root.left != null)
	    	root = root.left;
	    
	    return root.value;
	}

	//
	// max()
	// Return the maximum value in the set
	//
	// If the set is empty, result is undefined.
	//
	
	public T max() {
	    return maxValueInSubtree(this.root);
	}


	//
	// maxValueInSubTree()
	// Find the largest value in the subtree rooted at
	// the specified node.
	//
	// ASSUMED: root is not null.
	//
	private T maxValueInSubtree(TreeNode<T> root) {
	    while (root.right != null)
	    	root = root.right;
	    return root.value;
	}
	
	////////////////////////////////////////////////////////
	
	//
	// insert()
	// Insert the specified value in the set if it does not
	// already exist.
	//
	// RETURNS: the size of the set after insertion.
	//
	public int insert(T value) {
//		System.out.println("before " + this.enumerate());
		this.root = insertHelper(value, this.root);
//	    System.out.println("------------");
//	    System.out.println("size Old " + size);
//	    System.out.println("size New " + size);
//	    System.out.println("the root "+ this.root);
//	    System.out.println("the height "+ root.height); 
//		System.out.println("final " + this.enumerate());
	    return size;
	    
		
	}
	
	//
	// insertHelper()
	// Recursive procedure to insert a value into the subtree
	// rooted at "root".  If value is already present in the
	// tree, nothing is inserted.
	//
	// RETURNS: root node of subtree after insertion
	//
	// FIXME: add the necessary code to this function
	// to maintain height and rebalance the tree when
	// a node is removed.
	//
	private TreeNode<T> insertHelper(T value,
					 TreeNode<T> root) {

		if (root == null) {
			// add new element as leaf of tree
			TreeNode<T> newNode = new TreeNode<T>(value); 
			size++;
			return newNode;
	    } 
		
		else {
	    	int comparison = value.compareTo(root.value);
	    	if (comparison == 0) {
	    		// duplicate element -- return existing node
//				System.out.println("hi this is not going to happen");	
	    	} else if (comparison < 0) {
	    		root.setLeft(insertHelper(value, root.left));

//		    		System.out.println("middle " + this.enumerate());
//					System.out.println("look left of " + root+" height "+ root.height + " balance " +getBalance(root));
				
	    	} else {
	    		// still looking -- go right
	    		root.setRight(insertHelper(value, root.right));
//		    		System.out.println("middle " + this.enumerate());
//					System.out.println("look right of " + root+" height "+ root.height + " balance " +getBalance(root));
	    	}
	    	
//		    	System.out.println("size"+ this.size);
//				System.out.println("hi " + this.enumerate());
	    	updateHeight(root);
//	    	rebalance(root);
	    	return rebalance(root);
			}
	    }
	

	////////////////////////////////////////////////////////
	
	//
	// remove()
	// Remove a value from the set if it is present
	//
	public void remove(T value) {

	    this.root = removeHelper(value, this.root);
	}
	
	//
	// removeHelper()
	// Recursive procedure to remove a value from the
	// subtree rooted at "root", if it exists.
	//
	// RETURNS root node of subtree after insertion
	//
	// FIXME: add the necessary code to this function
	// to maintain height and rebalance the tree when
	// a node is removed.
	//
	private TreeNode<T> removeHelper(T value,
					 TreeNode<T> root) {
		

	    if (root == null) { // did not find element
//    		System.out.println(" did not find element");
	    	return null;
	    } else {
	    	int comparison = value.compareTo(root.value);
//    		System.out.println("comparison " + comparison);

	    	if (comparison == 0) { // found element to remove
//	    		System.out.println("found element to remove");

	    		if (root.left == null || root.right == null) {
//	    			System.out.println("1 subtree");

	    			// base case -- root has at most one subtree,
	    			// so return whichever one is not null (or null
	    			// if both are)
	    			size--;
//	    			System.out.println("removing "+root);
//	    			System.out.println(size);
//	    	    	updateHeight(root.left == null ? root.right : root.left);
//	    	    	rebalance(root);	
	    			
	    			return (root.left == null ? root.right : root.left);
	    		} else {
//	    			System.out.println("two subtrees");

	    			// node with two subtrees -- replace key
	    			// with successor and recursively remove
	    			// the successor.
	    			T minValue = minValueInSubtree(root.right);
	    			root.value = minValue;
	    			root.setRight(removeHelper(minValue, root.right));
	    		}
	    	} else if (comparison < 0) {
	    		// still looking for element to remove -- go left
//	    		System.out.println("still looking left");
	    		root.setLeft(removeHelper(value, root.left));
	    	} else {
	    		// still looking for element to remove -- go right
//	    		System.out.println("still looking right");
	    		root.setRight(removeHelper(value, root.right));
	    	}
	    	updateHeight(root);
	    	root = rebalance(root);
	    	return root;
	    }
	}

	
	////////////////////////////////////////////////////////
	//
	// INTERNAL METHODS FOR MAINTAINING BALANCE
	//
	
	// updateHeight()
	//
	// Recompute the height of the subtree rooted at "root",
	// assuming that its left and right children (if any)
	// have correct heights for their respective subtrees.
	//
	// EFFECT: Set the root's height field to the updated value
	//
	private void updateHeight(TreeNode<T> root) {		
		if (root == null) {
			System.out.println("ERR: called updatehight");
		}

//		if has both children
		if (root.left != null && root.right != null) {
			root.height = 1 + Math.max(root.left.height, root.right.height);
//			System.out.println("the root is-----" + root);
//			System.out.println("the height is-----" + root.height);
		}
		
//		if has right
		if (root.left == null && root.right != null) {
			root.height = root.right.height + 1;
//			System.out.println("the root is-----" + root);
//			System.out.println("the height is-----" + root.height);
		}
		
//		if has left
		if (root.left != null && root.right == null) {
			root.height = root.left.height + 1;
//			System.out.println("the root is-----" + root);
//			System.out.println("the height is-----" + root.height);
		}
		
//		if root has no child
		if (root.left == null && root.right == null) {
			root.height = 0;
		}
						
	    // FIXME: fill in the update code
	
	}
;
	//
	// getBalance()
	// Return the balance factor of a subtree rooted at "root"
	// (right subtree height - left subtree height)
	//
	private int getBalance(TreeNode<T> root) {
		int balanceFactor;
		
		if (root.right != null && root.left != null) {
			balanceFactor = root.right.height - root.left.height;
		    return balanceFactor;
		}
		
		if (root.right == null && root.left != null) {
//			omg should be -1
//			System.out.println("calculating balance = -1 -" + root.left.height);
			balanceFactor =  -1 - root.left.height;
		    return balanceFactor;

		}
		
		if (root.right != null && root.left == null) {
			balanceFactor = root.right.height - (-1);
		    return balanceFactor;
		}
		
		else {
			balanceFactor=0;
		    return balanceFactor;
		}
	}

	//
	// rebalance()
	//
	// Rebalance an AVL subtree, rooted at "root", that has possibly
	// been unbalanced by a single node's insertion or deletion.
	//
	// RETURNS: the root of the subtree after rebalancing
	//
	private TreeNode<T> rebalance(TreeNode<T> root) {
		
		int balanceFactor = getBalance(root);
//		System.out.println("balance Factor " + balanceFactor);

//		left 
		if (balanceFactor == -2) {
//			left-right
			if (getBalance(root.left) == 1) {
//				notice here additional step
				root.setLeft(leftRotate(root.left));
//				root.left = leftRotate(root.left);

			}
//			left-left
			root = rightRotate(root);
//			System.out.println("balacing " + this.enumerate());

		    return root;
		}
		
//		right
		else if (balanceFactor == 2) {
//			right-left
			if (getBalance(root.right) == -1) {
//				notice here additional step
				root.setRight(rightRotate(root.right));
//				root.right = rightRotate(root.right);

			}
//			right-right
			root = leftRotate(root);
//			System.out.println("balacing " + this.enumerate());
		    return root;
		}
		
		else {
		    return root;
		}
	    
	    
	    
		
	}
	
	//
	// rightRotate()
	// Perform a right rotation on a tree rooted at "root"
	// The tree's root is assumed to have a left child.
	//
	// RETURNS: the new root after rotation.
	//
	private TreeNode<T> rightRotate(TreeNode<T> root) {

		TreeNode<T> x = root.left;
		TreeNode<T> T2 = x.right;
		x.right = root;
		root.left = T2;
//		root.setLeft(T2);
//		x.setRight(root);

		updateHeight(root);
		updateHeight(x);
//		System.out.println("fromRR " + this.enumerate());

	    return x;
	}

	//
	// leftRotate()
	// Perform a left rotation on a tree rooted at "root"
	// The tree's root is assumed to have a right child.
	//
	// RETURNS: the new root after rotation.
	//
	private TreeNode<T> leftRotate(TreeNode<T> root) {
		
		//mirror of rightRotate img p44 slide
		TreeNode<T> z = root.right;
		TreeNode<T> T2 = z.left;
				

		z.left = root;
		root.right = T2;
//		z.setLeft(root);
//		root.setRight(T2);
		
		
		updateHeight(root);
		updateHeight(z);
//		System.out.println("leftRotate on " + root);
//		System.out.println("fromLR " + this.enumerate());
	    return z;
	}
	
	/////////////////////////////////////////////////////////////
	//
	// METHODS USED TO VALIDATE CORRECTNESS OF TREE
	// (You should not have to touch these)
	//

	//
	// getRoot()
	// Return the root node of the tree (for validation only!)
	//
	public TreeNode<T> getRoot() {
	    return this.root;
	}
	
		
	//
	// enumerate()
	// Return the contents of the tree as an ordered list
	//
	public LinkedList<T> enumerate() {
	    return enumerateHelper(this.root);
	}
	
	//
	// enumerateHelper()
	// Enumerate the contents of the tree rooted at "root" in order
	// as a linked list
	//
	private LinkedList<T> enumerateHelper(TreeNode<T> root) {
	    if (root == null) 
		{
		    return new LinkedList<T>();
		}
	    else
		{
		    LinkedList<T> list = enumerateHelper(root.left);
		    list.addLast(root.value);
		    list.addAll(enumerateHelper(root.right));
		    
		    return list;
		}
	}
	
	
	
	T smallest;

	public T firstAfter(TreeNode<T> root, T v){

		System.out.println("testing "+smallest);
		
//		if not found
		if (root ==null || (root.right ==null && root.value.compareTo(v) < 0)) {
			System.out.println(v + " not found ");
			return null;
		}

			
		if (root.value.compareTo(v) >= 0){

//				add first feasible result
			if (smallest == null) {
				System.out.println("add first local optimal");
				smallest = root.value;
			}
			
//				update if there is better result
			else if(root.value.compareTo(smallest)<0) {
				smallest = root.value;
			}
			
		}

		
		if(root.value.compareTo(v) <= 0) {
			System.out.println("look right " + root.right);
			firstAfter(root.right, v);//v is larger, looking right
		}
		else {
			System.out.println("look left " + root.left);
			firstAfter(root.left, v);//v is smaller, looking left
		}
		return smallest;
	}
	
	
}

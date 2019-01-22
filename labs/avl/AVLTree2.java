package avl;

import java.util.LinkedList;

public class AVLTree2<T extends Comparable<T>> {

	private TreeNode<T> root;
	public int size;
	
	public AVLTree2() {
	    this.root = null;
	    this.size = 0;
	}
	
	////////////////////////////////////////////////////////
	
	//
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
		System.out.println("before " + this.enumerate());

//		if (this.exists(value)) {
//			System.out.println("the insert is duplicate");
//		}
//		
//		else {
			this.root = insertHelper(value, this.root);
//		}
//	    System.out.println("------------");
//	    System.out.println("size Old " + size);
//	    System.out.println("size New " + size);
//	    System.out.println("the root "+ this.root);
//	    System.out.println("the height "+ root.height); 
			System.out.println("final " + this.enumerate());

		
//		System.out.println("after " + this.enumerate());
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
//		System.out.println(this.enumerate());
//		System.out.println("inserting " + value);

//		if duplicate, exit
		if (this.exists(value)) {
			System.out.println("the insert is duplicate");
			return root;
		}
		
//		no duplicates
		else {
//			base case, return newly inserted
			if (root == null) {
				// add new element as leaf of tree
				TreeNode<T> newNode = new TreeNode<T>(value); 
				size++;
				System.out.println("-----insert new node " + newNode+" height "+newNode.height + " balance " +getBalance(newNode));
				return newNode;
		    } 
			
			else {
		    	int comparison = value.compareTo(root.value);
		    	if (comparison == 0) {
		    		// duplicate element -- return existing node
					System.out.println("hi this is not going to happen");
//					System.out.println("new node " + root+" height "+ root.height + " balance " +getBalance(root));
		    		return root;
		    	} else if (comparison < 0) {
		    		root.setLeft(insertHelper(value, root.left));

		    		// still looking -- go left
//		    		insertHelper(value, root.left);
		    		System.out.println("middle " + this.enumerate());
					System.out.println("look left of " + root+" height "+ root.height + " balance " +getBalance(root));
		    	} else {
		    		// still looking -- go right
		    		root.setRight(insertHelper(value, root.right));
//			    	rebalance(root);
//			    	updateHeight(root);
//		    		insertHelper(value, root.right);
		    		System.out.println("middle " + this.enumerate());
					System.out.println("look right of " + root+" height "+ root.height + " balance " +getBalance(root));

		    	}
		
//		    	System.out.println("balancing " + root);
		    	System.out.println("size"+this.size);
				System.out.println("hi " + this.enumerate());

		    	rebalance(root);
		    	updateHeight(root);
		    	
//		    	root = this.root;
//		    	try {
////					System.out.println("	Root"+root);
////					System.out.println("   L "+root.left + "  	R "+root.right);
////					System.out.println("new root node " + root+" height "+ root.height + " balance " +getBalance(root));
//		    	}
//		    	catch(Exception e) {
//		    		
//		    	}
	    	return root;
			}
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
	    	rebalance(root);
	    	
	    	
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
		
	    // FIXME: fill in the balance computation

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
			}
//			left-left
			root = rightRotate(root);
//			System.out.println("xxxxroot"+ root);
		    return root;
		}
		
//		right
		else if (balanceFactor == 2) {
//			right-left
			if (getBalance(root.right) == -1) {
//				notice here additional step
				root.setRight(rightRotate(root.right));;
			}
//			right-right
			root = leftRotate(root);
			System.out.println("xxxxroot"+ root);

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
	    // FIXME: fill in the rotation code
//		step 1: 
//		get nodes
//		detach root.left.right
		TreeNode<T> x = root.left;
		TreeNode<T> T2 = x.right;
		System.out.println("rightRotate on " + root);
		
		if (root.parent!=null) {
			root.parent.setRight(x);
		}
		
//		step 2:switch root and root.right

//		1.make Y (root.left) new root- so it is same as root
//		x.parent = root.parent;		
		x.setRight(root);

//		2.update current root to be right child of of new root
		
//		step 3: attach the orphan
		root.setLeft(T2);
		
		
//		step 4:update height
		this.updateHeight(root);
		this.updateHeight(x);
		System.out.println("fromRR " + this.enumerate());

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
				
		if (root.parent!=null) {
			root.parent.setLeft(z);
		}
		
//		upgrade z
		z.setLeft(root);
		
//		downgrade root
//		root.parent = z;
		
//		attach orphan
		root.setRight(T2);
		
		
		this.updateHeight(root);
		this.updateHeight(z);
		System.out.println("leftRotate on " + root);
		System.out.println("fromLR " + this.enumerate());

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
	
	
	public TreeNode<T> firstAfter(TreeNode<T> root, T v){

		//local variable
		TreeNode<T> firstAfter = null;
	
		//If we reach bottom 
		if (root == null){
			return firstAfter;
		}
	
		//update firstAfter if we have a better fit
		if (root.value.compareTo(firstAfter.value) < 0 && root.value.compareTo(v) > 0){
			firstAfter = root;
		}
	
	
		int comparison = v.compareTo(root.value);
		if (comparison==0){
			return root;//found exact value
		}
		else if  (comparison < 0){
			return firstAfter(root.left, v);//v is smaller, looking left
		}
		else {
			return firstAfter(root.right, v);//v is larger, keep looking right
		}
	}
	
	
	
	
}

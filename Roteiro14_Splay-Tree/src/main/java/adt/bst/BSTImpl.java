package adt.bst;

import java.util.ArrayList;

import adt.bt.BTNode;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;
	protected BSTNode<T> NIL;

	public BSTImpl() {
		root = new BSTNode<T>();
		NIL = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		return heightRecursive(root);
	}

	private int heightRecursive(BSTNode<T> node) {
		if (node.isEmpty()) {
			return -1;

		} else {
			int leftHeight = heightRecursive((BSTNode<T>) node.getLeft());
			int rightHeight = heightRecursive((BSTNode<T>) node.getRight());

			if (leftHeight > rightHeight) {
				return leftHeight + 1;

			} else {
				return rightHeight + 1;
			}
		}
	}

	@Override
	public BSTNode<T> search(T element) {
		if (element != null) {
			return searchRecursive(root, element);

		} else {
			return NIL;
		}
	}

	private BSTNode<T> searchRecursive(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			return NIL;

		} else if (node.getData().compareTo(element) == 0) {
			return node;

		} else if (node.getData().compareTo(element) > 0) {
			return searchRecursive((BSTNode<T>) node.getLeft(), element);

		} else {
			return searchRecursive((BSTNode<T>) node.getRight(), element);
		}
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			if (isEmpty()) {
				setRelationshipAndDataOfNode(root, NIL, element);

			} else {
				insertRecursive(root, element);
			}
		}
	}

	protected void setRelationshipAndDataOfNode(BSTNode<T> node, BSTNode<T> parent, T element) {
		node.setData(element);
		node.setParent(parent);
		node.setLeft(new BSTNode<T>());
		node.setRight(new BSTNode<T>());
		node.getLeft().setParent(node);
		node.getRight().setParent(node);
	}
	
	private void insertRecursive(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			setRelationshipAndDataOfNode(node, (BSTNode<T>) node.getParent(), element);

		} else if (node.getData().compareTo(element) >= 0) {
			insertRecursive((BSTNode<T>) node.getLeft(), element);

		} else {
			insertRecursive((BSTNode<T>) node.getRight(), element);
		}
	}
	
	@Override
	public BSTNode<T> maximum() {
		if (isEmpty()) {
			return null;

		} else {
			return maximumRecursive(root);
		}
	}

	private BSTNode<T> maximumRecursive(BSTNode<T> node) {
		if (node.getRight().isEmpty()) {
			return node;

		} else {
			return maximumRecursive((BSTNode<T>) node.getRight());
		}
	}

	@Override
	public BSTNode<T> minimum() {
		if (isEmpty()) {
			return null;

		} else {
			return minimumRecursive(root);
		}
	}

	private BSTNode<T> minimumRecursive(BSTNode<T> node) {
		if (node.getLeft().isEmpty()) {
			return node;

		} else {
			return minimumRecursive((BSTNode<T>) node.getLeft());
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = search(element);

		if (node.isEmpty()) {
			return null;

		} else if (node.getRight().isEmpty()) {
			return sucessorRecursive(node, element);

		} else {
			return minimumRecursive((BSTNode<T>) node.getRight());
		}
	}

	private BSTNode<T> sucessorRecursive(BSTNode<T> node, T element) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();

		if (parent.isEmpty()) {
			return null;

		} else if (!parent.getRight().equals(node)) {
			return parent;

		} else {
			return sucessorRecursive(parent, element);
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = search(element);

		if (node.isEmpty()) {
			return null;

		} else if (node.getLeft().isEmpty()) {
			return predecessorRecursive(node, element);

		} else {
			return maximumRecursive((BSTNode<T>) node.getLeft());
		}
	}

	private BSTNode<T> predecessorRecursive(BSTNode<T> node, T element) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();

		if (parent.isEmpty()) {
			return null;

		} else if (!parent.getLeft().equals(node)) {
			return parent;

		} else {
			return predecessorRecursive(parent, element);
		}
	}

	@Override
	public void remove(T element) {
		BSTNode<T> nodeToBeRemoved = this.search(element);

		if (!nodeToBeRemoved.isEmpty()) {
			removeRecursive(nodeToBeRemoved, element);
		}
	}

	private void removeRecursive(BSTNode<T> node, T element) {
		if (node.isLeaf()) {
			removeLeaf(node);
			
		} else if (hasOneChild(node)) {
			removeNodeWithOneChild(node);
			
		} else { //has left and right child
			removeNodeWithTwoChildren(node);
		}

	}

	private void removeLeaf(BSTNode<T> node) {
		node.setData(null);
	}

	private boolean hasOneChild(BSTNode<T> node) {
		if (!node.isEmpty()) {
			if (hasLeftChild(node) && !hasRightChild(node) || 
					!hasLeftChild(node) && hasRightChild(node)) {
				
				return true;
			}
		}
		
		return false;
	}
	
	private void removeNodeWithOneChild(BSTNode<T> node) {
		if (isRoot(node)) {
			removeRoot(node);
			
		} else if (isLeftChild(node)) {
			if (hasLeftChild(node)) {
				removeLeftNodeWithLeftChild(node);
				
			} else { //hasRightChild
				removeLeftNodeWithRightChild(node);
			}
			
		} else { //isRightChild
			if (hasLeftChild(node)) {
				removeRightNodeWithLeftChild(node);
				
			} else { //hasRightChild
				removeRightNodeWithRightChild(node);
			}
		}
	}

	protected boolean isRoot(BSTNode<T> node) {
		return node.equals(root);
	}
	
	private void removeRoot(BSTNode<T> node) {
		if (hasLeftChild(node)) {
			root = (BSTNode<T>) root.getLeft();
			
		} else { //hasRightChild
			root = (BSTNode<T>) root.getRight();
		}
	}

	protected boolean isLeftChild(BSTNode<T> node) {
		BTNode<T> parent = node.getParent();
		
		if (parent.getLeft().equals(node)) {
			return true;
			
		} else {
			return false;
		}
	}
	
	protected boolean isRightChild(BSTNode<T> node) {
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		
		if (parent.getRight().equals(node)) {
			return true;
			
		} else {
			return false;
		}
	}
	
	private void removeLeftNodeWithLeftChild(BSTNode<T> node) {
		BTNode<T> parent = node.getParent();
		parent.setLeft(node.getLeft());
		node.getLeft().setParent(parent);
	}
	
	private void removeLeftNodeWithRightChild(BSTNode<T> node) {
		BTNode<T> parent = node.getParent();
		parent.setLeft(node.getRight());
		node.getRight().setParent(parent);
	}
	
	private void removeRightNodeWithLeftChild(BSTNode<T> node) {
		BTNode<T> parent = node.getParent();
		parent.setRight(node.getLeft());
		node.getLeft().setParent(parent);
	}
	
	private void removeRightNodeWithRightChild(BSTNode<T> node) {
		BTNode<T> parent = node.getParent();
		parent.setRight(node.getRight());
		node.getRight().setParent(parent);
	}
	
	private boolean hasLeftChild(BSTNode<T> node) {
		if (node.getLeft().isEmpty()) {
			return false;
			
		} else {
			return true;
		}
	}
	
	private boolean hasRightChild(BSTNode<T> node) {
		if (node.getRight().isEmpty()) {
			return false;
			
		} else {
			return true;
		}
	}
	
	private void removeNodeWithTwoChildren(BSTNode<T> node) {
		BSTNode<T> sucessor = sucessor(node.getData());
		T sucessorData = sucessor.getData();
		
		remove(sucessor.getData());
		node.setData(sucessorData);
	}
	
	
	@Override
	public T[] preOrder() {
		ArrayList<T> array = new ArrayList<T>();
		Comparable<T>[] a = new Comparable[size()];
		preOrderRecursive(root, array);
		return (T[]) array.toArray(a);
	}

	private void preOrderRecursive(BTNode<T> node, ArrayList<T> array) {
		if (!node.isEmpty()){
			visitNode(node, array);
			preOrderRecursive(node.getLeft(), array);
			preOrderRecursive(node.getRight(), array);
		}
	}

	@Override
	public T[] order() {
		ArrayList<T> array = new ArrayList<T>();
		Comparable<T>[] a = new Comparable[size()];
		orderRecursive(root, array);
		return (T[]) array.toArray(a);
	}

	private void orderRecursive(BTNode<T> node, ArrayList<T> array) {
		if (!node.isEmpty()){
			orderRecursive(node.getLeft(), array);
			visitNode(node, array);
			orderRecursive(node.getRight(), array);
		}		
	}

	@Override
	public T[] postOrder() {
		ArrayList<T> array = new ArrayList<T>();
		Comparable<T>[] a = new Comparable[size()];
		postOrderRecursive(root, array);
		return (T[]) array.toArray(a);
	}

	private void postOrderRecursive(BTNode<T> node, ArrayList<T> array) {
		if (!node.isEmpty()){
			visitNode(node, array);
			postOrderRecursive(node.getLeft(), array);
			postOrderRecursive(node.getRight(), array);
		}			
	}

	private void visitNode(BTNode<T> node, ArrayList<T> array) {
		array.add(node.getData());
	}


	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}

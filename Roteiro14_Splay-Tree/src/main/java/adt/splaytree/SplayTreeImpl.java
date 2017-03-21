package adt.splaytree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

public class SplayTreeImpl<T extends Comparable<T>> extends BSTImpl<T>
		implements SplayTree<T> {

	@Override
	public BSTNode<T> search(T element) {
		BSTNode<T> node = super.search(element);
		
		if (node.isEmpty()) 
			splay((BSTNode<T>) node.getParent());
			
		else 
			splay(node);
		
		return node;
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
	
	private void insertRecursive(BSTNode<T> node, T element) {
		if (node.isEmpty()) {
			setRelationshipAndDataOfNode(node, (BSTNode<T>) node.getParent(), element);
			splay(node);

		} else if (node.getData().compareTo(element) >= 0) {
			insertRecursive((BSTNode<T>) node.getLeft(), element);

		} else {
			insertRecursive((BSTNode<T>) node.getRight(), element);
		}
	}
	
	@Override
	public void remove(T element) {
		BSTNode<T> node = super.search(element);
		removeRecursive(node, element);
		
		splay((BSTNode<T>) node.getParent());
	}
	
	private void splay(BSTNode<T> node) {
		if (node != null & !node.isEmpty()) {
			if (!isRoot(node)) {
				BSTNode<T> parent = (BSTNode<T>) node.getParent();
				BSTNode<T> grandParent = (BSTNode<T>) parent.getParent();
				
				if (isRoot(parent)) {
					if (isLeftChild(node)) {
						Util.rightRotation(parent);
						
					} else { //isRightChild
						Util.leftRotation(parent);
					}
					
				} else if (isLeftChild(node) && isLeftChild(parent)) {
					Util.rightRotation(grandParent);
					Util.rightRotation(parent);
					
				} else if (isRightChild(node) && isRightChild(parent)) {
					Util.leftRotation(grandParent);
					Util.leftRotation(parent);
					
				} else if (isLeftChild(node) && isRightChild(parent)) {
					Util.rightRotation(parent);
					Util.leftRotation(grandParent);
					
				} else { //isRightChild(node) && isLeftChild(parent)
					Util.leftRotation(parent);
					Util.rightRotation(grandParent);
				}
				
				if (node.getParent().isEmpty()) {
					root = node;
				}
				
				splay(node);
			}
		}
	}
}

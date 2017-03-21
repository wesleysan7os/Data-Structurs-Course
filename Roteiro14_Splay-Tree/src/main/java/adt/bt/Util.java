package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			BSTNode<T> pivot = (BSTNode<T>) node.getRight();
			
			if (!parent.isEmpty()) {
				if (parent.getRight().equals(node)) {
					parent.setRight(pivot);
					
				} else {
					parent.setLeft(pivot);
				}
			}
			
			pivot.setParent(parent);
			node.setRight(pivot.getLeft());
			pivot.getLeft().setParent(node);
			pivot.setLeft(node);
			node.setParent(pivot);
			
			return pivot;
		}
		
		return null;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			BSTNode<T> pivot = (BSTNode<T>) node.getLeft();
			
			if (!parent.isEmpty()) {
				if (parent.getLeft().equals(node)) {
					parent.setLeft(pivot);
					
				} else {
					parent.setRight(pivot);
				}
			}
			
			pivot.setParent(parent);
			node.setLeft(pivot.getRight());
			pivot.getRight().setParent(node);
			pivot.setRight(node);
			node.setParent(pivot);
			
			return pivot;
		}
		
		return null;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}

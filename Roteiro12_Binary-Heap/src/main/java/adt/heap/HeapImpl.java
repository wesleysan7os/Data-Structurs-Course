package adt.heap;

import java.util.Arrays;
import java.util.Comparator;

import util.Util;

/**
 * O comportamento de qualquer heap é definido pelo heapify. Neste caso o
 * heapify dessa heap deve comparar os elementos e colocar o maior sempre no
 * topo. Ou seja, admitindo um comparador normal (responde corretamente 3 > 2),
 * essa heap deixa os elementos maiores no topo. Essa comparação não é feita
 * diretamente com os elementos armazenados, mas sim usando um comparator. Dessa
 * forma, dependendo do comparator, a heap pode funcionar como uma max-heap ou
 * min-heap.
 */
public class HeapImpl<T extends Comparable<T>> implements Heap<T> {

	protected T[] heap;
	protected int index = -1;
	/**
	 * O comparador é utilizado para fazer as comparações da heap. O ideal é
	 * mudar apenas o comparator e mandar reordenar a heap usando esse
	 * comparator. Assim os metodos da heap não precisam saber se vai funcionar
	 * como max-heap ou min-heap.
	 */
	protected Comparator<T> comparator;

	private static final int INITIAL_SIZE = 20;
	private static final int INCREASING_FACTOR = 10;
	private static final int ROOT = 0;

	/**
	 * Construtor da classe. Note que de inicio a heap funciona como uma
	 * min-heap.
	 */
	@SuppressWarnings("unchecked")
	public HeapImpl(Comparator<T> comparator) {
		this.heap = (T[]) (new Comparable[INITIAL_SIZE]);
		this.comparator = comparator;
	}

	// /////////////////// METODOS IMPLEMENTADOS
	private int parent(int i) {
		return (i - 1) / 2;
	}

	/**
	 * Deve retornar o indice que representa o filho a esquerda do elemento
	 * indexado pela posicao i no vetor
	 */
	private int left(int i) {
		return (i * 2 + 1);
	}

	/**
	 * Deve retornar o indice que representa o filho a direita do elemento
	 * indexado pela posicao i no vetor
	 */
	private int right(int i) {
		return (i * 2 + 1) + 1;
	}

	@Override
	public boolean isEmpty() {
		return (index == -1);
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resp = Util.makeArray(index + 1);
		for (int i = 0; i <= index; i++) {
			resp[i] = this.heap[i];
		}
		return resp;
	}

	// ///////////// METODOS A IMPLEMENTAR
	/**
	 * Valida o invariante de uma heap a partir de determinada posicao, que pode
	 * ser a raiz da heap ou de uma sub-heap. O heapify deve colocar os maiores
	 * (comparados usando o comparator) elementos na parte de cima da heap.
	 */
	private void heapify(int position) {
		int aux = position;
		int leftChild = left(position);
		int rightChild = right(position);

		if (leftChild <= index) {
			aux = leftChild;
		}

		if (rightChild <= index) {
			aux = max(leftChild, rightChild);
		}
		
		aux = max(position, aux);
		
		if (aux != position) {
			Util.swap(heap, aux, position);
			heapify(aux);
		}
	}
	
	private int max(int leftChild, int rightChild) {
		if (comparator.compare(heap[leftChild], heap[rightChild]) > 0) {
			return leftChild;
			
		} else {
			return rightChild;
		}
	}

	@Override
	public void insert(T element) {
		if (index == heap.length - 1) {
			heap = Arrays.copyOf(heap, heap.length + INCREASING_FACTOR);
		}

		if (element != null) {
			index++;
			int i = index;
			heap[i] = element;
			
			while (i > 0 && compare(heap[parent(i)], element) < 0) {
				heap[i] = heap[parent(i)];
				i = parent(i);
			}
			
			heap[i] = element;
		}
	}

	@Override
	public void buildHeap(T[] array) {
		heap = array;
		index = array.length - 1;
		
		for (int i = array.length / 2; i >= 0; i--) {
			heapify(i);
		}
	}

	@Override
	public T extractRootElement() {
		if (isEmpty()) {
			return null;
			
		} else {
			T root = heap[ROOT];
			heap[ROOT] = heap[index--];
			heapify(ROOT);
			
			return root;
		}
	}

	@Override
	public T rootElement() {
		if (isEmpty()) {
			return null;
			
		} else {
			return heap[ROOT];
		}
	}

	@Override
	public T[] heapsort(T[] array) {
		Comparator<T> comparatorTemp = this.comparator;

		this.comparator = new Comparator<T>() {

			@Override
			public int compare(T o1, T o2) {
				return o1.compareTo(o2);
			}
		};

		buildHeap(array);
		@SuppressWarnings("unchecked")
		T[] aux = (T[]) new Comparable[array.length];

		for (int i = index; i >= 0; i--) {
			aux[i] = extractRootElement();
		}
		
		this.comparator = comparatorTemp;
		return aux;

	}

	@Override
	public int size() {
		return index + 1;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public T[] getHeap() {
		return heap;
	}
	
	public int compare(T element, T otherElement) {
		return this.comparator.compare(element, otherElement);
	}
}

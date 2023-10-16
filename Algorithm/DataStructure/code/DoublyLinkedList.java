package list;

// link값으로 앞/뒤 요소를 가지고 있음
class Node<E> {
	E data;
	Node<E> prev;
	Node<E> next;
	
	Node(E data) {
		this.data = data;
	}
}

public class DoublyLinkedList<E> {
	private int size;
	private Node<E> head;
	private Node<E> tail;
	
	DoublyLinkedList() {
		this.size = 0;
	}
	
	/* 조회 */
	boolean isEmpty() {
		return this.size == 0;
	}
	
	int size() {
		return this.size;
	}
	
	E getData(int idx) {
		if (isEmpty()) return null;
		if (idx < 0 || idx >= this.size) return null;
		// 앞에서 접근
		if (idx < this.size / 2) {
			Node<E> curr = this.head;
			int i = 0;
			while (i < idx) {
				curr = curr.next;
				i++;
			}
			return curr.data;
		// 뒤에서 접근
		} else {
			Node<E> curr = this.tail;
			int i = this.size - 1;
			while (i > idx) {
				curr = curr.prev;
				i--;
			}
			return curr.data;
		}
	}
	
	void printList() {
		if (isEmpty()) {
			System.out.println("[]");
			return;
		}
		Node<E> curr = this.head;
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		while (curr != null) {
			sb.append(curr.data);
			if (curr.next != null) sb.append(", ");
			curr = curr.next;
		}
		sb.append(" ]");
		System.out.println(sb);
	}
	
	/* 추가 */
	void addAtFirst(E data) {
		Node<E> node = new Node<>(data);
		if (isEmpty()) {
			this.head = node;
			this.tail = node;
			this.size++;
			return;
		}
		node.next = this.head;
		this.head.prev = node;
		this.head = node;
		this.size++;
	}
	
	void addAtLast(E data) {
		Node<E> node = new Node<>(data);
		if (isEmpty()) {
			this.head = node;
			this.tail = node;
			this.size++;
			return;
		}
		this.tail.next = node;
		node.prev = this.tail;
		this.tail = node;
		this.size++;
	}
	
	void add(E data, int idx) {
		if (idx < 0) return; 
		
		if (idx == 0) {
			addAtFirst(data);
			return;
		}
		
		if (idx >= this.size) {
			addAtLast(data);
			return;
		}
		
		Node<E> node = new Node<>(data);
		if (isEmpty()) {
			this.head = node;
			this.size++;
			return;
		}
		// 앞에서 접근
		if (idx < this.size / 2) {
			Node<E> curr = this.head;
			int i = 0;
			while (i < idx) {
				curr = curr.next;
				i++;
			}
			node.next = curr;
			node.prev = curr.prev;
			node.next.prev = node;
			node.prev.next = node;
			this.size++;
		// 뒤에서 접근
		} else {
			Node<E> curr = this.tail;
			int i = this.size() - 1;
			while (i > idx) {
				curr = curr.prev;
				i--;
			}
			node.next = curr;
			node.prev = curr.prev;
			node.prev.next = node;
			node.next.prev = node;
			this.size++;
		}
	}
	
	/* 삭제 */
	void removeHead() {
		if (isEmpty()) return;
		this.head.next.prev = null;
		this.head = this.head.next;
		this.size--;
	}
	
	void removeTail() {
		if (isEmpty()) return;
		this.tail.prev.next = null;
		this.tail = this.tail.prev;
		this.size--;
	}
	
	void remove(int idx) {
		if (isEmpty()) return;
		if (idx == 0) {
			removeHead();
			return;
		}
		if (idx == this.size - 1) {
			removeTail();
			return;
		}
		// 앞에서 접근
		if (idx < this.size / 2) {
			int i = 0;
			Node<E> curr = this.head;
			while (i < idx) {
				curr = curr.next;
				i++;
			}
			curr.prev.next = curr.next;
			curr.next.prev = curr.prev;
			this.size--;
		// 뒤에서 접근
		} else {
			int i = this.size - 1;
			Node<E> curr = this.tail;
			while (i > idx) {
				curr = curr.prev;
				i--;
			}
			curr.prev.next = curr.next;
			curr.next.prev = curr.prev;
			this.size--;
		}
	}
}

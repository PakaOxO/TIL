package list;

// link값으로 뒤 요소를 가지고 있음
class Node<E> {
	E data;
	Node<E> next;
	
	Node(E data) {
		this.data = data;
	}
}

public class SinglyLinkedList<E> {
	private int size;
	private Node<E> head;
	
	SinglyLinkedList() {
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
		int i = 0;
		Node<E> curr = head;
		while (i < idx) {
			curr = curr.next;
			i++;
		}
		return curr.data;
	}
	
	void printList() {
		if (isEmpty()) {
			System.out.println("[]");
			return;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		Node<E> curr = this.head;
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
			this.size++;
			return;
		}
		node.next = this.head;
		this.head = node;
		this.size++;
	}
	
	void addAtLast(E data) {
		Node<E> node = new Node<>(data);
		if (isEmpty()) {
			this.head = node;
			this.size++;
			return;
		}
		Node<E> curr = this.head;
		while (curr.next != null) {
			curr = curr.next;
		}
		curr.next = node;
		size++;
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
		
		Node<E> prev = this.head;
		int i = 1;
		while (i < idx) {
			prev = prev.next;
			i++;
		}
		node.next = prev.next;
		prev.next = node;
		this.size++;
	}
	
	/* 삭제 */
	void removeHead() {
		if (isEmpty()) return;
		if (this.size == 1) {
			this.head = null;
			size--;
			return;
		}
		this.head = this.head.next;
		size--;
	}
	
	void removeTail() {
		if (isEmpty()) return;
		if (this.size == 1) {
			this.head = null;
			size--;
			return;
		}
		Node<E> prev = this.head;
		while (prev.next.next != null) {
			prev = prev.next;
		}
		prev.next = null;
		size--;
	}
	
	void remove(int idx) {
		if (isEmpty()) return;
		if (idx == 0) {
			removeHead();
			return;
		}
		if (idx >= this.size()) {
			removeTail();
			return;
		}
		
		Node<E> prev = this.head;
		int i = 1;
		while (i < idx) {
			prev = prev.next;
			i++;
		}
		prev.next = prev.next.next;
		this.size--;
	}
}

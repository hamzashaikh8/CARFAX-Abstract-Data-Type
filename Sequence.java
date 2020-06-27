package cvrADT;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sequence {
	private Node head;
	private int size;

	public Sequence() {
		size = 0;
	}

	/**
	 * this class keeps track of each element information
	 *
	 */
	private class Node {
		// node attributes
		String element;
		Node next;
		Node prev;

		public Node(String element, Node next, Node prev) {
			this.element = element;
			this.next = next;
			this.prev = prev;
		}

	}

	/**
	 * returns the size of the linked list
	 * 
	 * @return
	 */
	public int size() {
		int size = 0;
		Node temp = this.head;
		while (temp != null) {
			size++;
			temp = temp.next;
		}

		return size;
	}

	/**
	 * return whether the list is empty or not
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * adds element at the starting of the linked list
	 * 
	 * @param element
	 */
	public void addFirst(String element) {
		Node add = new Node(element, head, null);

		if (head != null)
			head.prev = add;

		head = add;
	}

	public String[] returnArray() {
		String[] represent = new String[size];
		Node nextNode = this.head;
		int i = 0;
		while (nextNode != null) {
			represent[i] = nextNode.element;
			nextNode = nextNode.next;
			i++;
		}

		Arrays.sort(represent);

		return represent;

	}

	public Node find(String a) {
		Node nextNode = this.head;

		while (nextNode != null && !nextNode.element.contentEquals(a)) {
			// System.out.println(nextNode.element);
			nextNode = nextNode.next;
		}

		return nextNode;
	}

	public void add(String a) {
		addFirst(a);
	}

	public void remove(String a) {
		Node nextNode = head;
		while (nextNode != null) {
			if (nextNode.element.equalsIgnoreCase(a)) {
				if (nextNode.prev != null)
					nextNode.prev.next = nextNode.next;
				if (nextNode.next != null)
					nextNode.next.prev = nextNode.prev;
				break;
			}
			nextNode = nextNode.next;

		}
	}

	public boolean hasElement(String key) {
		boolean value = false;
		Node temp = this.head;
		while (temp != null) {
			if (temp.element.equals(key))
				value = true;
			temp = temp.next;
		}
		return value;
	}

	public String next(String a) {
		return find(a).next.element;
	}

	public String prev(String a) {
		return find(a).prev.element;
	}

	public void printall() {
		Node temp = head;
		// System.out.println("hello");
		while (temp != null) {
			System.out.println(temp.element);
			temp = temp.next;
		}
	}

	public List<String> allelements() {
		List<String> elements = new ArrayList<String>();
		Node temp = this.head;
		while (temp != null) {
			elements.add(temp.element);
			temp = temp.next;
		}
		return elements;
	}

}

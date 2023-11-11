package model;

public class Node {
	private int data;
	private Node left;
	private Node right;
	private int leftQuantity;
	private int rightQuantity;
	private int height;
	

	public Node(int data) {
		this.data = data;
		this.left = null;
		this.right = null;
		this.leftQuantity = 0;
		this.rightQuantity = 0;
		this.height = 1;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public int getLeftQuantity() {
		return leftQuantity;
	}

	public void setLeftQuantity(int leftQuantity) {
		this.leftQuantity = leftQuantity;
	}

	public int getRightQuantity() {
		return rightQuantity;
	}

	public void setRightQuantity(int rightQuantity) {
		this.rightQuantity = rightQuantity;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}

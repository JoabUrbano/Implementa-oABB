package model;

import java.util.Stack;

public class TreeABB {
	private Node source;
	
	public TreeABB() {
		this.source = null;
	}
	
	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		this.source = source;
	}
	
	public void countChildren(Node current) {
		if(current.getLeft() != null) {
			countChildren(current.getLeft());
		}
		
		if(current.getRight() != null) {
			countChildren(current.getRight());
		}
		
		if(current.getLeft() != null && current.getRight() != null) {
			current.setLeftQuantity(current.getLeft().getLeftQuantity() + current.getLeft().getRightQuantity() + 1);
			current.setRightQuantity(current.getRight().getLeftQuantity() + current.getRight().getRightQuantity() + 1);
		} else if(current.getLeft() != null && current.getRight() == null) {
			current.setLeftQuantity(current.getLeft().getLeftQuantity() + current.getLeft().getRightQuantity() + 1);
			current.setRightQuantity(0);
		} else if(current.getLeft() == null && current.getRight() != null) {
			current.setLeftQuantity(0);
			current.setRightQuantity(current.getRight().getLeftQuantity() + current.getRight().getRightQuantity() + 1);
		} else {
			current.setLeftQuantity(0);
			current.setRightQuantity(0);
		}
	}
	
	public void updateHeight(Node node) {
		if(node.getLeft() == null || node.getRight() == null) {
			return;
		}
		
		this.updateHeight(node.getLeft());
		this.updateHeight(node.getLeft());
		
		node.setHeight(1 + Math.max(node.getLeft().getHeight(), node.getRight().getHeight()));
	}
	
	public boolean isATreeKey(int value) {
		if(this.source == null) {
			return false;
		}
		
		Node aux = this.source;
		
		while(aux != null) {
			if(value < aux.getData()) {
				aux = aux.getLeft();
			} else if(value > aux.getData()) {
				aux = aux.getRight();
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	public Node search(int value) {
		if(this.source == null) {
			System.out.println("A chave não pode ser encontrada, pois a árvore está vazia.");
			return null;
		}
		
		Node aux = this.source;
		
		while(aux != null) {
			if(value < aux.getData()) {
				aux = aux.getLeft();
			} else if(value > aux.getData()) {
				aux = aux.getRight();
			} else {
				System.out.println("Chave encontrada!");
				return aux;
			}
		}
		
		System.out.println("Chave não encontrada!");
		return null;
	}
	
	public void insert(int value, Node element) {
		if(this.source == null) {
			this.source = new Node(value);
			System.out.println(value + " Adicionado!");
			this.countChildren(this.source);
			this.updateHeight(this.source);
			return;
		}
		
		boolean valueWasFound = this.isATreeKey(value);
		
		if(valueWasFound) {
			System.out.println(value + " já está na árvore. A inserção não pode ser realizada!");
			return;
		} else {
			Node newNode = new Node(value);
			if(value < element.getData()) {
				if(element.getLeft() == null) {
					element.setLeft(newNode);
					System.out.println(value + " Adicionado!");
					this.countChildren(this.source);
					this.updateHeight(this.source);
					return;
				} else {
					this.insert(value, element.getLeft());
				}
			} else if (value > element.getData()) {
				if(element.getRight() == null) {
					element.setRight(newNode);
					System.out.println(value + " Adicionado!");
					this.countChildren(this.source);
					this.updateHeight(this.source);
					return;
				} else {
					this.insert(value, element.getRight());
				}
			}
		}
	}
	
	public Node seekSuccessor(Node node) {
		Node aux = node;
		
		if(aux.getRight() != null) {
			aux = aux.getRight();
		}
		
		while(aux.getLeft() != null) {
			aux = aux.getLeft();
		}
		
		return aux;
	}
	
	public Node remove(int key) {
        Node parent = null;
        Node current = this.source;

        // Procurar o nó a ser removido
        while (current != null && current.getData() != key) {
            parent = current;
            if (key < current.getData()) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }

        // Se o nó não foi encontrado, retorna a árvore original
        if (current == null) {
        	System.out.println(key + " não está na árvore, não pode ser removido.");
            return this.source;
        }

        // Caso 1: Nó com apenas um filho ou nenhum filho
        if (current.getLeft() == null) {
            if (parent == null) {
            	System.out.println(key + " Removido!");
        		this.countChildren(this.source);
        		this.updateHeight(this.source);
                return current.getRight();
            } else if (current == parent.getLeft()) {
                parent.setLeft(current.getRight());
            } else {
                parent.setRight(current.getRight());
            }
        } else if (current.getRight() == null) {
            if (parent == null) {
            	System.out.println(key + " Removido!");
        		this.countChildren(this.source);
        		this.updateHeight(this.source);
                return current.getLeft();
            } else if (current == parent.getLeft()) {
                parent.setLeft(current.getLeft());
            } else {
                parent.setRight(current.getLeft());
            }
        }
        // Caso 3: Nó com dois filhos
        else {
            Node successorParent = current;
            Node successor = current.getRight();

            // Encontrar o nó mais à esquerda na subárvore à direita
            while (successor.getLeft() != null) {
                successorParent = successor;
                successor = successor.getLeft();
            }

            // Substituir o valor do nó a ser removido pelo valor do sucessor
            current.setData(successor.getData());

            // Remover o sucessor
            if (successorParent.getLeft() == successor) {
                successorParent.setLeft(successor.getRight());
            } else {
                successorParent.setRight(successor.getRight());
            }
        }

        System.out.println(key + " Removido!");
		this.countChildren(this.source);
		this.updateHeight(this.source);
        return this.source;
    }
	
	public int nthElement(int n, Node node) {
		if(n == node.getLeftQuantity() + 1) {
			return node.getData();
		} else if(n < node.getLeftQuantity() + 1) {
			return this.nthElement(n, node.getLeft());
		} else {
			return this.nthElement(n - (node.getLeftQuantity() + 1), node.getRight());
		}
	}
	
	public int position(int x) {
		int position = 0;
		Node current = this.source;
		
		while(current != null) {
			if(x == current.getData()) {
				position += current.getLeftQuantity() + 1;
				return position;
			} else if(x < current.getData()) {
				current = current.getLeft();
			} else {
				position += current.getLeftQuantity() + 1;
				current = current.getRight();
			}
		}
		
		return -1;
	}
	
	public int median() {
		int treeSize = this.source.getLeftQuantity() + this.source.getRightQuantity() + 1;
		
		if(treeSize % 2 == 1) {
			return this.nthElement(treeSize/2 + 1, this.source);
		} else {
			return this.nthElement(treeSize/2, this.source);
		}
	}
	
	public int sum(Node node) {
		if(node == null) {
			return 0;
		}
		
		return node.getData() + this.sum(node.getLeft()) + this.sum(node.getRight());
	}
	
	public double average(int x) {
		if(this.search(x) != null) {
			double sum = sum(this.search(x));
			double numberOfKeys = search(x).getLeftQuantity() + search(x).getRightQuantity() + 1;
			double average = sum/numberOfKeys;
			return average;
		} else {
			return -1;
		}
	}
	
	public boolean isFull() {
		int numberOfNodes = this.source.getLeftQuantity() + this.source.getRightQuantity() + 1;
		int totalNumberOfNodes = (int) (Math.pow(2, this.source.getHeight()) - 1);
		
		if(numberOfNodes == totalNumberOfNodes) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isComplete(Node node) {
		boolean result = true;
		if (node.getHeight() > 2) {
			if (node.getLeft() == null || node.getRight() == null)
				result = false;

			if (result != true)
				return result;

			if (node.getLeft() != null) {
				if (node.getLeft().getHeight() > 1)
					result = isComplete(node.getLeft());
			}
			if (node.getRight() != null) {
				if (node.getLeft().getHeight() > 1)
					result = isComplete(node.getRight());
			}
		}
		return result;
	}
	
	public String pre_order() {
		String preOrder = "";
		
		if(this.source == null) {
			return preOrder;
		}
		
		Stack<Node> stack = new Stack<>();
		stack.push(this.source);
		
		while(!stack.isEmpty()) {
			Node aux = stack.pop();
			preOrder += Integer.toString(aux.getData());
			preOrder += " ";
			
			if(aux.getRight() != null) {
				stack.push(aux.getRight());
			}
			
			if(aux.getLeft() != null) {
				stack.push(aux.getLeft());
			}
		}
		
		return preOrder;
	}
	
	public void printTree1(Node root, String tabs) {
        if (root != null) {
            System.out.printf("%.28s%n", tabs + root.getData() + " ----------------------------------");
            printTree1(root.getLeft(), tabs + "        ");
            printTree1(root.getRight(), tabs + "        ");
        }
    }
	
	public void printTree2(Node root, String tabs) {
        if (root != null) {
            System.out.print(tabs + root.getData());
            printTree2(root.getLeft(), " (");
            printTree2(root.getRight(), " (");
            System.out.print(")");
        }
    }
	
	public void printTree(int s) {
		switch (s) {
		case 1: {
			this.printTree1(this.source, "");
			break;
		}
		case 2: {
			this.printTree2(this.source, "(");
			System.out.print("\n");
			break;
		}
		default:
			System.out.println("Unexpected value: " + s);
		}
	}
	
	public void metodo(String method, String parameter) {
		if (method.equals("BUSCAR") && !parameter.equals("")) {
			int n = Integer.parseInt(parameter);
			System.out.println("----- Função Buscar -----");
			this.search(n);
		} else if (method.equals("INSIRA") && !parameter.equals("")) {
			int n = Integer.parseInt(parameter);
			System.out.println("----- Função Inserir -----");
			this.insert(n, this.source);
		} else if (method.equals("REMOVA") && !parameter.equals("")) {
			int n = Integer.parseInt(parameter);
			System.out.println("----- Função Remover -----");
			this.remove(n);
		} else if (method.equals("ENESIMO") && !parameter.equals("")) {
			int n = Integer.parseInt(parameter);
			System.out.println("----- Função Enesimo elemento -----");
			System.out.print(parameter + " é a posição do elemento: ");
			System.out.println(this.nthElement(n, this.source));
		} else if (method.equals("POSICAO") && !parameter.equals("")) {
			int n = Integer.parseInt(parameter);
			System.out.println("----- Função Posição -----");
			System.out.print("A posição do elemento " + n + " é: ");
			System.out.println(this.position(n));
		} else if (method.equals("MEDIANA") && parameter.equals("")) {
			System.out.println("----- Função Mediana -----");
			System.out.println("A mediana é " + this.median());
		} else if (method.equals("MEDIA") && !parameter.equals("")) {
			int n = Integer.parseInt(parameter);
			System.out.println("----- Função Média -----");
			System.out.println("A média é: " + this.average(n));
		} else if (method.equals("CHEIA") && parameter.equals("")) {
			System.out.println("----- Função É cheia -----");
			if (this.isFull() == true)
				System.out.println("A arvore é cheia!");
			else
				System.out.println("A arvore não é cheia!");
		} else if (method.equals("COMPLETA") && parameter.equals("")) {
			System.out.println("----- Função É completa -----");
			if (this.isComplete(this.source) == true)
				System.out.println("A arvore é completa!");
			else
				System.out.println("A arvore não é completa!");
		} else if (method.equals("PREORDEM") && parameter.equals("")) {
			System.out.println("----- Função Pré ordem -----");
			System.out.println("A pré ordem é: " + this.pre_order());
		} else if (method.equals("IMPRIMA") && !parameter.equals("")) {
			System.out.println("----- Função Imprimir -----");
			int n = Integer.parseInt(parameter);
			this.printTree(n);
		} else
			System.out.println("O método " + method +
					" não foi implementado e/ou não teve seus parametros passados corretamente!");
	}
}

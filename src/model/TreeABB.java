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
	
	// Este método verifica se um valor, recebido como parâmetro, é chave da árvore.
	public Node search(int value) {
		// Verifica se a árvore está vazia
		if(this.source == null) {
			System.out.println("A chave não pode ser encontrada, pois a árvore está vazia.");
			return null;
		}
		
		 // Inicializa o nó auxiliar
		Node aux = this.source;
		
		// Loop de busca
		while(aux != null) {
			if(value < aux.getData()) {
				aux = aux.getLeft();
			} else if(value > aux.getData()) {
				aux = aux.getRight();
			} else {
				return aux;
			}
		}
		
		// Se o loop termina e o nó auxiliar é nulo, a chave não foi encontrada
		System.out.println("Chave não encontrada!");
		return null;
	}
	
	// Este método Insere um nó na ABB.
	// O método recebe como parâmetros o valor que será o dado do nó e um nó que será usado para iterar sobre os nós de maneira recursiva.
	public void insert(int value, Node parent) {
		 // Verifica se a árvore está vazia.
		if(this.source == null) {
			// Cria um novo nó e define como raiz
			this.source = new Node(value);
			System.out.println(value + " Adicionado!");
			this.countChildren(this.source);
			this.updateHeight(this.source);
			return;
		}
		
		// Verifica se o valor já existe na árvore
		boolean valueWasFound = this.isATreeKey(value);
		
		if(valueWasFound) {
			// Valor já existe, interrompe a inserção
			System.out.println(value + " já está na árvore. A inserção não pode ser realizada!");
			return;
		} else {
			// Valor não existe, continua com a inserção
			Node newNode = new Node(value);
			
			if(value < parent.getData()) {
				// Caso 1: valor a ser inserido é menor que a raiz, então valor será inserido a esquerda da árvore.
				if(parent.getLeft() == null) {
					// Subárvore vazia é encontrada e o novo nó é inserido à esquerda
					parent.setLeft(newNode);
					System.out.println(value + " Adicionado!");
					this.countChildren(this.source);
					this.updateHeight(this.source);
					return;
				} else {
					// Recursivamente insere à esquerda
					this.insert(value, parent.getLeft());
				}
			} else if (value > parent.getData()) {
				// Caso 2: valor a ser inserido é maior que a raiz, então valor será inserido a direita da árvore.
				if(parent.getRight() == null) {
					// Subárvore vazia é encontrada e o novo nó é inserido à direita
					parent.setRight(newNode);
					System.out.println(value + " Adicionado!");
					this.countChildren(this.source);
					this.updateHeight(this.source);
					return;
				} else {
					// Recursivamente insere à direita
					this.insert(value, parent.getRight());
				}
			}
		}
	}
	
	// Este método procura o sucessor de um nó passado como parâmetro.
	public Node seekSuccessor(Node node) {
		Node aux = node;
		
		// Se houver um filho à direita, move-se para o filho à direita
		if(aux.getRight() != null) {
			aux = aux.getRight();
		}
		
		// Enquanto houver filhos à esquerda, move-se para o filho à esquerda
		while(aux.getLeft() != null) {
			aux = aux.getLeft();
		}
		
		return aux;
	}
	
	// Este método remove um elemento, que tem sua chave passada como parâmetro, da árvore.
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
            // Encontrar o nó sucessor
            Node successor = this.seekSuccessor(current);

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
	
	// Este método procurar o n-ésimo (passado como parâmetro) elemento, contando a partir de 1, do percurso em ordem simétrica da ABB.
	public int nthElement(int n, Node node) {
		if(n == node.getLeftQuantity() + 1) {
			// Se n é igual à quantidade de nós à esquerda mais 1, retornar o dado do nó
			return node.getData();
		} else if(n < node.getLeftQuantity() + 1) {
			// Se n é menor que a quantidade de nós à esquerda mais 1, chamar recursivamente para a subárvore à esquerda
			return this.nthElement(n, node.getLeft());
		} else {
			// Se n é maior que a quantidade de nós à esquerda mais 1, chamar recursivamente para a subárvore à direita
			return this.nthElement(n - (node.getLeftQuantity() + 1), node.getRight());
		}
	}
	
	// Este método retorna a posição ocupada pelo elemento x (especificado como parâmetro) em um percurso em ordem simétrica na ABB (contando a partir de 1).
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
	
	// Este método retorna o elemento que contém a mediana da ABB
	public int median() {
		// Calcula o tamanho da árvore
		int treeSize = this.source.getLeftQuantity() + this.source.getRightQuantity() + 1;
		
		if(treeSize % 2 == 1) {
			// Retorna o elemento que ocupa a posição (tamanhoDaÀrvore/2 + 1)
			return this.nthElement(treeSize/2 + 1, this.source);
		} else {
			// Retorna o elemento que ocupa a posição (tamanhoDaÀrvore/2)
			return this.nthElement(treeSize/2, this.source);
		}
	}
	
	// Este método retorna o somatório de todas as chaves da árvore.
	public int sum(Node node) {
		if(node == null) {
			return 0;
		}
		
		return node.getData() + this.sum(node.getLeft()) + this.sum(node.getRight());
	}
	
	// Este método retorna a média aritmética dos nós da árvore que x é a raiz.
	public double average(int x) {
		// Verifica se o nó com valor x existe na árvore
		if(this.search(x) != null) {
			// Chama o método sum para calcular a soma dos valores dos nós da subárvore com raiz x
			double sum = sum(this.search(x));
			
			// Calcula o número total de chaves na subárvore com raiz x
			double numberOfKeys = search(x).getLeftQuantity() + search(x).getRightQuantity() + 1;
			
			// Calcula a média aritmética
			double average = sum/numberOfKeys;
			return average;
		} else {
			// Retorna -1 se o nó com valor x não existe na árvore
			return -1;
		}
	}
	
	// Este método retorna verdadeiro se a ABB for uma árvore binária cheia e falso, caso contrário.
	public boolean isFull() {
		// Calcula o número de nós na árvore
		int numberOfNodes = this.source.getLeftQuantity() + this.source.getRightQuantity() + 1;
		
		 // Calcula o número total de nós em uma árvore binária cheia de mesma altura
		int totalNumberOfNodes = (int) (Math.pow(2, this.source.getHeight()) - 1);
		
		// Verifica se o número de nós na árvore é igual ao número total de nós em uma árvore binária cheia
		if(numberOfNodes == totalNumberOfNodes) {
			// Se forem iguais, a árvore é considerada cheia
			return true;
		} else {
			// Caso contrário, a árvore não é cheia
			return false;
		}
	}
	
	// Este método retorna verdadeiro se a ABB for uma árvore binária completa.
	public boolean isComplete(Node node) {
		// Inicializa o resultado como verdadeiro
		boolean result = true;
		
		// Verifica se a altura do nó é maior que 2
		if (node.getHeight() > 2) {
			// Verifica se o nó à esquerda ou o nó à direita é nulo
			if (node.getLeft() == null || node.getRight() == null)
				result = false;

			// Se o resultado for falso, retorna imediatamente
			if (result != true)
				return result;

			// Verifica se o nó à esquerda não é nulo e tem altura maior que 1
			if (node.getLeft() != null) {
				if (node.getLeft().getHeight() > 1)
					result = isComplete(node.getLeft());
			}
			
			// Verifica se o nó à direita não é nulo e tem altura maior que 1
			if (node.getRight() != null) {
				if (node.getLeft().getHeight() > 1)
					result = isComplete(node.getRight());
			}
		}
		
		// Retorna o resultado final
		return result;
	}
	
	
	// Este método retorna uma String que contém a sequência de visitação (percorrimento) da ABB em pré-ordem.
	public String pre_order() {
		// Inicializa a string que armazenará a sequência de pré-ordem
		String preOrder = "";
		
		// Verifica se a árvore está vazia
		if(this.source == null) {
			return preOrder; // Retorna uma string vazia se a árvore estiver vazia
		}
		
		// Inicializa uma pilha e empilha a raiz da árvore
		Stack<Node> stack = new Stack<>();
		stack.push(this.source);
		
		// Enquanto a pilha não estiver vazia, percorre os nós em pré-ordem
		while(!stack.isEmpty()) {
			// Desempilha o nó atual
			Node aux = stack.pop();
			
			// Adiciona o valor do nó à string de pré-ordem
			preOrder += Integer.toString(aux.getData());
			preOrder += " ";
			
			// Empilha o nó à direita, se existir
			if(aux.getRight() != null) {
				stack.push(aux.getRight());
			}
			
			// Empilha o nó à esquerda, se existir
			if(aux.getLeft() != null) {
				stack.push(aux.getLeft());
			}
		}
		
		// Retorna a string de pré-ordem
		return preOrder;
	}
	
	// Este método retorna uma String que contém a sequência de visitação (percorrimento) da ABB no formato tabular.
	public void printTree1(Node root, String tabs) {
		// Verifica se o nó não é nulo
        if (root != null) {
        	// Imprime o valor do nó formatado com uma linha de caracteres
            System.out.printf("%.28s%n", tabs + root.getData() + " ----------------------------------");
            
         // Chama recursivamente a função para o nó à esquerda, adicionando espaçamento
            printTree1(root.getLeft(), tabs + "        ");
            
         // Chama recursivamente a função para o nó à direita, adicionando espaçamento
            printTree1(root.getRight(), tabs + "        ");
        }
    }
	
	// Este método retorna uma String que contém a sequência de visitação (percorrimento) da ABB no formato dom parênteses.
	public void printTree2(Node root, String tabs) {
		// Verifica se o nó não é nulo
        if (root != null) {
        	// Imprime o valor do nó
            System.out.print(tabs + root.getData());
            
         // Chama recursivamente a função para o nó à esquerda, adicionando um parêntese de abertura
            printTree2(root.getLeft(), " (");
            
         // Chama recursivamente a função para o nó à direita, adicionando um parêntese de abertura
            printTree2(root.getRight(), " (");
            
         // Imprime um parêntese de fechamento para representar o término dos nós da subárvore
            System.out.print(")");
        }
    }
	
	// Este método imprime a árvore no formato 1, se "s" é igual a 1. Se “s” igual a 2, imprime no formato 2.
	public void printTree(int s) {
		switch (s) {
		case 1: {
			// Caso 1: Chama a função printTree1 para impressão no formato 1
			this.printTree1(this.source, "");
			break;
		}
		case 2: {
			// Caso 2: Chama a função printTree2 para impressão no formato 2 e imprime uma quebra de linha
			this.printTree2(this.source, "(");
			System.out.print("\n");
			break;
		}
		default:
			// Caso padrão: Imprime uma mensagem de valor inesperado
			System.out.println("Valor inesperado: " + s);
		}
	}
	
	public void metodo(String method, String parameter) {
		if (method.equals("BUSCAR") && !parameter.equals("")) {
			int n = Integer.parseInt(parameter);
			System.out.println("----- Função Buscar -----");
			if(this.search(n) != null)
				System.out.println("Chave encontrada!");
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

import java.util.Scanner;

public class LanchonetePedidos {

    // ...existing code...
    static class Pedido {
        int id_pedido;
        String nome_cliente;
        String item;
        int quantidade;
        double valor;
        String tipo; // "Urgente", "Prioritário", "Normal"
        Pedido prox;

        public Pedido(int id, String nome, String item, int qtd, double valor, String tipo) {
            this.id_pedido = id;
            this.nome_cliente = nome;
            this.item = item;
            this.quantidade = qtd;
            this.valor = valor;
            this.tipo = tipo;
            this.prox = null;
        }

        @Override
        public String toString() {
            return "[ID: " + id_pedido + ", Cliente: " + nome_cliente + ", Item: " + item +
                   ", Qtd: " + quantidade + ", Valor: R$" + String.format("%.2f", valor) +
                   ", Tipo: " + tipo + "]";
        }
    }

    // ...existing code...
    static class ListaPedidos {
        private Pedido cabeca;
        private int tamanho;

        public boolean estaVazia() {
            return cabeca == null;
        }

        public int tamanho() {
            return tamanho;
        }

        // Função utilitária
        public Pedido obterCabeca() {
            return cabeca;
        }

        // Inserir no início (Urgente)
        public void inserirInicio(Pedido novo) {
            if (buscarPorId(novo.id_pedido) != null) {
                System.out.println("❌ Pedido duplicado! ID já existe.");
                return;
            }
            novo.valor *= 1.20; // acréscimo de 20%
            novo.prox = cabeca;
            cabeca = novo;
            tamanho++;
        }

        // Inserir no fim (Normal)
        public void inserirFim(Pedido novo) {
            if (buscarPorId(novo.id_pedido) != null) {
                System.out.println("❌ Pedido duplicado! ID já existe.");
                return;
            }
            if (estaVazia()) {
                cabeca = novo;
            } else {
                Pedido atual = cabeca;
                while (atual.prox != null) {
                    atual = atual.prox;
                }
                atual.prox = novo;
            }
            tamanho++;
        }

        // Inserir na posição mediana (Prioritário)
        public void inserirMeio(Pedido novo) {
            if (buscarPorId(novo.id_pedido) != null) {
                System.out.println("❌ Pedido duplicado! ID já existe.");
                return;
            }
            novo.valor *= 1.10; // acréscimo de 10%

            if (estaVazia() || tamanho == 1) {
                inserirFim(novo);
                return;
            }

            int meio = tamanho / 2;
            Pedido atual = cabeca;
            for (int i = 0; i < meio - 1; i++) {
                atual = atual.prox;
            }

            novo.prox = atual.prox;
            atual.prox = novo;
            tamanho++;
        }

        // Remover do início (pedido atendido)
        public void removerInicio() {
            if (estaVazia()) {
                System.out.println("Lista vazia!");
                return;
            }
            cabeca = cabeca.prox;
            tamanho--;
        }

        // Remover do fim (cancelamento)
        public void removerFim() {
            if (estaVazia()) {
                System.out.println("Lista vazia!");
                return;
            }
            if (cabeca.prox == null) {
                cabeca = null;
            } else {
                Pedido atual = cabeca;
                while (atual.prox.prox != null) {
                    atual = atual.prox;
                }
                atual.prox = null;
            }
            tamanho--;
        }

        // Remover posição k (cancelamento específico)
        public void removerPosicao(int k) {
            if (k < 0 || k >= tamanho || estaVazia()) {
                System.out.println("Posição inválida!");
                return;
            }

            if (k == 0) {
                removerInicio();
                return;
            }

            Pedido atual = cabeca;
            for (int i = 0; i < k - 1; i++) {
                atual = atual.prox;
            }
            atual.prox = atual.prox.prox;
            tamanho--;
        }

        // Buscar por ID
        public Pedido buscarPorId(int id) {
            Pedido atual = cabeca;
            while (atual != null) {
                if (atual.id_pedido == id)
                    return atual;
                atual = atual.prox;
            }
            return null;
        }

        // Buscar por posição
        public Pedido buscarPorPosicao(int pos) {
            if (pos < 0 || pos >= tamanho)
                return null;
            Pedido atual = cabeca;
            for (int i = 0; i < pos; i++) {
                atual = atual.prox;
            }
            return atual;
        }

        // Imprimir lista completa
        public void imprimir() {
            if (estaVazia()) {
                System.out.println("Nenhum pedido na lista.");
                return;
            }
            Pedido atual = cabeca;
            System.out.println("\n📋 Lista de pedidos:");
            while (atual != null) {
                System.out.println(atual);
                atual = atual.prox;
            }
        }
    }

    // ...existing code...
    public static void main(String[] args) {
        ListaPedidos lista = new ListaPedidos();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Inserir pedido");
            System.out.println("2. Imprimir pedidos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Remover início (pedido atendido)");
            System.out.println("5. Remover fim (cancelamento)");
            System.out.println("6. Remover por posição");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            int opc = -1;
            try {
                opc = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Opção inválida.");
                continue;
            }

            if (opc == 0) {
                System.out.println("Saindo...");
                break;
            }

            switch (opc) {
                case 1:
                    try {
                        System.out.print("ID do pedido (inteiro): ");
                        int id = Integer.parseInt(sc.nextLine().trim());
                        if (lista.buscarPorId(id) != null) {
                            System.out.println("❌ Pedido com esse ID já existe.");
                            break;
                        }
                        System.out.print("Nome do cliente: ");
                        String nome = sc.nextLine().trim();
                        System.out.print("Item: ");
                        String item = sc.nextLine().trim();
                        System.out.print("Quantidade (inteiro): ");
                        int qtd = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Valor unitário (ex: 12.50): ");
                        double valor = Double.parseDouble(sc.nextLine().trim());

                        System.out.println("Tipo: 1-Urgente  2-Prioritário  3-Normal");
                        System.out.print("Escolha tipo (1/2/3): ");
                        int t = Integer.parseInt(sc.nextLine().trim());
                        String tipo;
                        if (t == 1) tipo = "Urgente";
                        else if (t == 2) tipo = "Prioritário";
                        else tipo = "Normal";

                        Pedido p = new Pedido(id, nome, item, qtd, valor, tipo);
                        if (tipo.equals("Urgente")) lista.inserirInicio(p);
                        else if (tipo.equals("Prioritário")) lista.inserirMeio(p);
                        else lista.inserirFim(p);

                        System.out.println("✅ Pedido inserido.");
                    } catch (Exception e) {
                        System.out.println("Erro ao ler dados. Tente novamente.");
                    }
                    break;
                case 2:
                    lista.imprimir();
                    break;
                case 3:
                    System.out.print("ID para buscar: ");
                    try {
                        int idb = Integer.parseInt(sc.nextLine().trim());
                        Pedido res = lista.buscarPorId(idb);
                        System.out.println(res != null ? res : "Pedido não encontrado.");
                    } catch (Exception e) {
                        System.out.println("ID inválido.");
                    }
                    break;
                case 4:
                    lista.removerInicio();
                    System.out.println("Removido início (se havia).");
                    break;
                case 5:
                    lista.removerFim();
                    System.out.println("Removido fim (se havia).");
                    break;
                case 6:
                    System.out.print("Posição a remover (0-based): ");
                    try {
                        int pos = Integer.parseInt(sc.nextLine().trim());
                        lista.removerPosicao(pos);
                        System.out.println("Operação concluída.");
                    } catch (Exception e) {
                        System.out.println("Posição inválida.");
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }

        sc.close();
    }
}
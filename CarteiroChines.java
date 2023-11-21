import java.util.Arrays;
import java.util.PriorityQueue;

public class CarteiroChines {

    static Dijkstra dijkstra(Grafo grafo, int origem) {
        int V = grafo.V;
        int[] distancia = new int[V];
        int[][] pai = new int[V][V];

        Arrays.fill(distancia, Integer.MAX_VALUE);
        distancia[origem] = 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> Integer.compare(distancia[a], distancia[b]));
        pq.add(origem);

        while (!pq.isEmpty()) {
            int u = pq.poll();

            for (Vertice _v : grafo.listaDeAdjacencia.get(u)) {
                int v = _v.para;
                int peso = _v.peso;

                if (distancia[u] + peso < distancia[v]) {
                    distancia[v] = distancia[u] + peso;
                    pq.add(v);
                    pai[origem][v] = u;
                }
            }
        }

        return new Dijkstra(distancia, pai);
    }

    static int[][] floydWarshall(Grafo grafo) {
        int V = grafo.V;
        int[][] dist = new int[V][V];

        for (int i = 0; i < V; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
            dist[i][i] = 0;
            for (Vertice v : grafo.listaDeAdjacencia.get(i)) {
                int j = v.para;
                int weight = v.peso;
                dist[i][j] = weight;
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE
                            && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }

    static int findCircuitoEuleriano(Grafo grafo) {
        int V = grafo.V;
        int[][] dist = floydWarshall(grafo);
        int contador = 0;
        int start = 0;

        for (int i = 0; i < V; i++) {
            if (grafo.listaDeAdjacencia.get(i).size() % 2 == 1) {
                contador++;
                start = i;
            }
        }

        if (contador > 2 || contador == 1) {
            System.out.println("O grafo não possui um ciclo euleriano.");
            return -1;
        }

        System.out.println("Matriz D:");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(dist[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("\nResultados do Algoritmo de Dijkstra para vértices ímpares:");

        for (int i = 0; i < V; i++) {
            if (grafo.listaDeAdjacencia.get(i).size() % 2 == 1) {
                Dijkstra result = dijkstra(grafo, i);
                System.out.println("Dijkstra a partir do vértice " + i + ":");
                for (int j = 0; j < V; j++) {
                    System.out.println("Distância até " + j + ": " + result.distancia[j]);
                    System.out.print("Caminho: " + j);
                    int pai = result.pai[i][j];
                    while (pai != 0) {
                        System.out.print(" <- " + pai);
                        pai = result.pai[i][pai];
                    }
                    System.out.println();
                }
                System.out.println();
            }
        }

        System.out.println("Ciclo Euleriano:");

        return start;
    }

    public static void main(String[] args) {
        Grafo grafo = new Grafo(4);

        grafo.addVertice(0, 1, 10);
        grafo.addVertice(0, 2, 20);
        grafo.addVertice(1, 2, 30);
        grafo.addVertice(2, 3, 40);

        int startVertex = findCircuitoEuleriano(grafo);
        if (startVertex != -1) {
            // Imprimir o ciclo euleriano a partir do vértice inicial
            imprime(grafo, startVertex);
        }
    }

    static void imprime(Grafo grafo, int inicio) {
        int V = grafo.V;
        boolean[][] visitado = new boolean[V][V];

        for (int u = 0; u < V; u++) {
            for (Vertice _v : grafo.listaDeAdjacencia.get(u)) {
                int v = _v.para;
                if (!visitado[u][v]) {
                    System.out.print(u + " -> " + v + " ");
                    visitado[u][v] = true;
                    visitado[v][u] = true;
                }
            }
        }
    }
}
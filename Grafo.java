import java.util.ArrayList;

public class Grafo {
    int V;
    ArrayList<ArrayList<Vertice>> listaDeAdjacencia;

    public Grafo(int V) {
        this.V = V;
        listaDeAdjacencia = new ArrayList<>(V);
        for (int i = 0; i < V; i++) {
            listaDeAdjacencia.add(new ArrayList<>());
        }
    }

    public void addVertice(int de, int para, int peso) {
        listaDeAdjacencia.get(de).add(new Vertice(para, peso));
        listaDeAdjacencia.get(para).add(new Vertice(de, peso));
    }
}
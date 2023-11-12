import java.io.*;
import java.util.*;

public class Grafo {

    private Map<Integer, List<Integer>> adjacencias = new HashMap<>();

    public void adicionarAresta(int origem, int destino) {
        adjacencias.computeIfAbsent(origem, k -> new ArrayList<>()).add(destino);
    }

    public List<List<Integer>> encontrarCaminhosDisjuntos(int origem, int destino) {
        List<List<Integer>> caminhosDisjuntos = new ArrayList<>();
        List<Integer> caminhoAtual = new ArrayList<>();
        Set<Integer> visitados = new HashSet<>();
        encontrarCaminhos(origem, destino, caminhoAtual, caminhosDisjuntos, visitados);
        return caminhosDisjuntos;
    }

    private void encontrarCaminhos(int atual, int destino, List<Integer> caminhoAtual,
            List<List<Integer>> caminhosDisjuntos, Set<Integer> visitados) {
        visitados.add(atual);
        caminhoAtual.add(atual);

        if (atual == destino) {
            caminhosDisjuntos.add(new ArrayList<>(caminhoAtual));
        } else {
            for (int vizinho : adjacencias.getOrDefault(atual, Collections.emptyList())) {
                if (!visitados.contains(vizinho)) {
                    encontrarCaminhos(vizinho, destino, caminhoAtual, caminhosDisjuntos, visitados);
                }
            }
        }

        visitados.remove(atual);
        caminhoAtual.remove(caminhoAtual.size() - 1);
    }

    public static void main(String[] args) throws IOException {
        Grafo grafo = new Grafo();
        Scanner sc = new Scanner(System.in);
        int quant = sc.nextInt();
        for (int i = 0; i < quant; i++) {
            grafo.adicionarAresta(sc.nextInt(), sc.nextInt());
        }
        sc.close();
        int origem = 0;
        int destino = 29;

        List<List<Integer>> caminhosDisjuntos = grafo.encontrarCaminhosDisjuntos(origem, destino);

        // Exibir resultados
        System.out.println("Quantidade de caminhos disjuntos: " + caminhosDisjuntos.size());
        System.out.println("Caminhos encontrados:");
        for (List<Integer> caminho : caminhosDisjuntos) {
            System.out.println(caminho);
        }
    }
}

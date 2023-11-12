import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

class caminhosDisjuntos {

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

    static String formatar(float valor, int qtdCasas) {
        NumberFormat fmt = DecimalFormat.getNumberInstance();
        fmt.setMaximumFractionDigits(qtdCasas);
        fmt.setRoundingMode(RoundingMode.DOWN);
        return fmt.format(valor);
}

    public static void main(String[] args) throws IOException {
        caminhosDisjuntos grafo = new caminhosDisjuntos();
        Scanner sc = new Scanner(System.in);
        int quant = sc.nextInt();
        for (int i = 0; i < quant; i++) {
            grafo.adicionarAresta(sc.nextInt(), sc.nextInt());
        }
        sc.close();
        int origem = 0;
        int destino = 39;

        // Medir o tempo de execução
        long startTime = System.currentTimeMillis();
        List<List<Integer>> caminhosDisjuntos = grafo.encontrarCaminhosDisjuntos(origem, destino);
        long endTime = System.currentTimeMillis();

        // Exibir resultados
        System.out.println("Quantidade de caminhos disjuntos: " + caminhosDisjuntos.size());
        System.out.println("Caminhos encontrados:");
        for (List<Integer> caminho : caminhosDisjuntos) {
            System.out.println(caminho);
        }

        long dif = (endTime - startTime);
        // Exibir o tempo de execução total
        System.out.println("Tempo total de execução: " + (formatar(dif, 20)) + " milissegundos");
    }
}

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package problema.carteiro.chines;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
// import javafx.util.Pair;
import java.util.AbstractMap;

public class Algoritmos {

    // Lema: dois vértices são adjacentes se estão na lista de adjacência um do outro
    // @ requires x != null;
    // @ requires y != null;
    // @ ensures x.getListaAdjascencia().contains(y) && y.getListaAdjascencia().contains(x);
    // @ model public pure void lemmaVerticesAdjascentes(Vertice x, Vertice y) {}

    // Lema: um caminho entre dois vértices i e j é uma lista de vértices onde o primeiro é i e o último é j, e pra cada 2 elementos dessa lista,
    // se os índices são adjacentes, então os vértices são adjacentes.
    // @ requires i != null;
    // @ requires j != null;
    // @ ensures lista.get(0) == i && lista.get(lista.size()-1) == j;
    // @ ensures \forall int x, y; 0 <= x < lista.size() && 0 <= y < lista.size(); (x == y+1 || x == y-1) ==> (lista.get(x).adjacente(y)) && lista.get(y).adjacente(x));
    // @ model public pure void lemmaCaminho(Vertice i, Vertice j, ArrayList<Vertice> lista) {}

    // @ requires i != null;
    // @ requires j != null;
    // @ ensures 
    // @ model public pure void lemmaVerticesConectados(Vertice i, Vertice j) {}
    


    // BFS genérico que retorna os vértices visitados
    //@ requires grafo != null;
    //@ requires verticeInicial != null;
    //@ ensures \result != null;
    //@ ensures (\forall int v; \result.contains(v) ==> (\exists Vertice u; grafo.getListaVertices().contains(u) && u.getN() == v));
    //@ ensures \result.contains(verticeInicial.getN());
    public List<Integer> bfs(Grafo grafo, Vertice verticeInicial) {
        List<Integer> visitados = new ArrayList<>();
        // assert visitados != null;
        Queue<Vertice> fila = new LinkedList<>();
         // assert fila != null;

        fila.add(verticeInicial);
        visitados.add(verticeInicial.getN());

        //@ maintaining !fila.isEmpty() ==> (\forall Vertice v; fila.contains(v) ==> !visitados.contains(v.getN()));
        //@ maintaining (\forall Vertice u; visitados.contains(u.getN()) ==> grafo.getListaVertices().contains(u));
        //@ loop_writes fila, visitados;
        //@ decreases fila.size();
        while (!fila.isEmpty()) {
            Vertice atual = fila.poll(); // Remove o próximo da fila

        /*@
        @ maintaining 0 <= i <= atual.listaAdjascencia.size();
        @ maintaining (\forall int j; 0 <= j < i ==> visitados.contains(atual.listaAdjascencia.get(j)) || (\exists Vertice v; grafo.getListaVertices().contains(v) && v.getN() == atual.listaAdjascencia.get(j) && fila.contains(v)));
        @ loop_invariant visitados != null && fila != null;
        @ loop_writes fila, visitados;
        @ decreases atual.listaAdjascencia.size() - i;
        @*/
        for (int i = 0; i < atual.listaAdjascencia.size(); i++) {
            Integer nVertice = atual.listaAdjascencia.get(i);
            Vertice adjacente = grafo.getListaVertices()
                    .get(grafo.getListaVertices().indexOf(new Vertice(nVertice)));

            // Se ainda não foi visitado, adiciona à fila e marca como visitado
            if (!visitados.contains(adjacente.getN())) {
                fila.add(adjacente);
                visitados.add(adjacente.getN());
            }
        }
            }

        return visitados; // Retorna a lista de vértices visitados
    }


    public static int PESO = 1;

    /**
     * Função que checa se o grafo é conexo.
     * Complexidade: O(n^2) -> Executa o for até n*n-1 vezes
     *
     * @param grafo     Grafo para ser verificado
     * @param ver       Vertice sendo verificado no momento
     * @param visitados Lista de vértices já visitados
     */
    //@ requires grafo != null;
    //@ requires ver != null;
    //@ requires visitados != null;
    //@ requires grafo.getListaVertices().contains(ver); // O vértice inicial deve estar no grafo
    //@ requires (\forall Vertice v; grafo.getListaVertices().contains(v); v.listaAdjascencia != null);
    //@ ensures (\forall Vertice v; grafo.getListaVertices().contains(v); visitados.contains(v.getN()));
    public void checarGrafoConexo(Grafo grafo, Vertice ver, List<Integer> visitados) {
    // Para cada vértice adjascente do atual, se ele não foi visitado, visita e checa seus adjascentes
    // @ maintaining 0 <= \count <= visitados.size();
    // @ maintaining 0 <= visitados.size() <= grafo.getListaVertices().size();
    // @ maintaining (\forall int n; 0 <= n < visitados.size(); ((visitados.contains(n) ==> (\exists Vertice v; grafo.getListaVertices().contains(v); v.getN() == n))));
    // @ loop_writes visitados;
    // @ decreases grafo.getListaVertices().size() - visitados.size(); 
    for (Integer nVertice : ver.listaAdjascencia) {
            if (!visitados.contains(nVertice)) {
                visitados.add(nVertice);
                checarGrafoConexo(grafo,
                grafo.getListaVertices().get(grafo.getListaVertices().indexOf(new Vertice(nVertice))),
                visitados);
            }
        }
    }

    //@ requires grafo != null;
    //@ requires grafo.getListaVertices() != null;
    //@ requires (\forall int i; 0 <= i < grafo.getListaVertices().size(); (\exists int j, k; 0 <= j < grafo.getListaVertices().size() && 0 <= k < grafo.getListaVertices().size() && grafo.getListaVertices().get(k).getListaAdjascencia().contains(grafo.getListaVertices().get(j))));
    //@ ensures \result == true;
    //@ also
    //@ requires grafo != null;
    //@ requires grafo.getListaVertices() != null;
    //@ requires (\forall int i; 0 <= i < grafo.getListaVertices().size(); !(\exists int j, k; 0 <= j < grafo.getListaVertices().size() && 0 <= k < grafo.getListaVertices().size() && grafo.getListaVertices().get(k).getListaAdjascencia().contains(grafo.getListaVertices().get(j))));
    //@ ensures \result == false;
    public boolean eConexo(Grafo grafo){
       boolean conexo = false;

        List<Integer> visitados = new ArrayList<>();
        //@ assert visitados != null;
        // Verificar se é conexo
        Vertice ver = grafo.getListaVertices().get(0);
        visitados.add(ver.getN());
        checarGrafoConexo(grafo, ver, visitados);
        //@ assert visitados != null;
        // Se todos vertices foram visitados, o grafo é conexo

        if (visitados.size() == grafo.getV()) {
        conexo = true;
        }
        //@ show conexo;
        return (conexo);
    }

    /**
     * Função que checa se o grafo é Euleriano.
     * Para isto se utiliza do teorema 2.1 do livro Teoria Computacional de Grafos
     * do autor Jayme Luiz Szwarcfiter.
     * Complexidade: O(n^3) -> checagem do grafo conexo e checagem do grau par
     * executa n vezes
     *
     * @param grafo grafo para ser verificado
     * @return true caso seja Euleriano, false CC
     */
    public boolean checarGrafoEuleriano(Grafo grafo) {
        boolean conexo = false;
        boolean euleriano = false;
        boolean grauPar = true;

        List<Integer> visitados = new ArrayList<>();

        // Verificar se é conexo
        Vertice ver = grafo.getListaVertices().get(0);
        visitados.add(ver.getN());
        checarGrafoConexo(grafo, ver, visitados);
        // Se todos vertices foram visitados, o grafo é conexo
        if (visitados.size() == grafo.getV())
            conexo = true;

        // System.out.println("Conexo: " + conexo);

        // Se conexo, verificar se todo nó tem grau par
        if (conexo) {
            for (Vertice v : grafo.getListaVertices()) {
                if (v.listaAdjascencia.size() % 2 != 0) {
                    grauPar = false;
                }
            }
        }
        // System.out.println("Grau Par: " + grauPar);

        // Se for conexo e todo nor for par, é euleriano
        if (conexo && grauPar)
            euleriano = true;

        // System.out.println("Euleriano: " + euleriano);

        return euleriano;
    }

    /**
     * Algoritmo de Hierholzer.
     * Implementado assim como descrito no relatório.
     * Complexidade: O(m) -> número máximo de arestas que ele pode percorrer
     *
     * @param grafo           Grafo que gerará a trilha euleriana
     * @param ver             Vértice atual sendo adicionado à trilha
     * @param trilhaEuleriana Lista que possui a trilha
     */
    public void hierholzer(Grafo grafo, Vertice ver, List<Vertice> trilhaEuleriana) {
        for (int i = 0; i < grafo.getListaVertices().get(grafo.getListaVertices().indexOf(ver)).listaAdjascencia
                .size(); i++) {
                    Vertice v = grafo.getListaVertices()
                    .get(grafo.getListaVertices().indexOf(new Vertice(ver.listaAdjascencia.get(i))));
           grafo.remAresta(ver.getN(), v.getN());
            hierholzer(grafo, v, trilhaEuleriana);
        }
        trilhaEuleriana.add(ver);
    }

    public boolean algoritmoLinks(Grafo grafo) {
        boolean linkado = false;
        boolean euleriano = false;
        // System.out.println();
        // System.out.println("grafo:");
        grafo.printGrafo();
        // System.out.println();
        euleriano = checarGrafoEuleriano(grafo);

        if (euleriano) {
            List<Vertice> trilhaEuleriana = new ArrayList<>();
            Vertice ver = grafo.getListaVertices().get(0);
            hierholzer(grafo, ver, trilhaEuleriana);

            // System.out.print("Trilha euleriana: ");
            // for (Vertice v : trilhaEuleriana) {
            //     System.out.print(v.getN() + " ");
            // }
            // System.out.println();
       
            linkado = true;

            return linkado;
        }

        return euleriano;
    }

    // NAO FUNCIONA
    //@ normal_behavior
    //@ requires grafo != null;
    //@ requires grafo.listaVertices != null;
    //@ requires \forall int i; 0 <= i < grafo.listaVertices.size(); grafo.listaVertices.get(i) != null;
    //@ requires \forall int i; 0 <= i < grafo.listaVertices.size(); grafo.listaVertices.get(i).n >= 0;
    //@ requires \forall int i; 0 <= i < grafo.listaVertices.size(); grafo.listaVertices.get(i).listaAdjascencia.size() >= 0;
    public ArrayList<Integer> verticesImpares(Grafo grafo) {

        ArrayList<Integer> impares = new ArrayList<>();
        int tamanho = grafo.getListaVertices().size();
        // TAG-DEBUG int tamanho = grafo.listaVertices.size();

        //@ assert impares != null;

        //@ maintaining 0 <= i <= tamanho;
        // @ maintaining \forall int j; 0 <= j < tamanho; grafo.listaVertices.get(j) != null;
        // @ maintaining \forall int k; 0 <= k < tamanho-1; grafo.listaVertices.get(k) == \old(grafo.listaVertices.get(k)); 
        // @ maintaining \forall int l; 0 <= l < tamanho; grafo.listaVertices.get(l) != null && grafo.listaVertices.get(l).listaAdjascencia.size() >= 0;
        // @ maintaining \forall int m; 0 <= m < i; (grafo.listaVertices.get(m).listaAdjascencia.size() % 2 != 0) ==> (impares.contains(grafo.listaVertices.get(m).getN()));
        //@ loop_writes impares, i;
        //@ decreases tamanho - i;
        for (int i = 0; i < tamanho; i++) {
            // TAG- DEBUG
            // Vertice ver = grafo.listaVertices.get(i);
            // ArrayList<Integer> lista = ver.listaAdjascencia;

            Vertice ver = grafo.getListaVertices().get(i);
            List<Integer> lista = ver.listaAdjascencia;

            int grau = lista.size();
            int n = ver.getN();
            // TAG-DEBUG int n = ver.n;
            //@ assert grau >= 0;
            if (grau % 2 != 0) {
                //@ assert n >= 0;
                impares.add(n);
                //@ assert impares.contains(n);
            }
        }
        return impares;
    }


    /***
     * Retorna uma lista com todos os pares de vértices com grau ímpar
     *
     * @param grafo a ser analisado
     * @return Lista com os vértices de grau ímpar
     */
    public void caminhoCasoNaoEuleriano(Grafo grafo) {
        ArrayList<Integer> impares = new ArrayList<>();
        Boolean podeAdicionar = true;

        // Verifica se o grau dos vértices é ímpar, se o vértice for ímpar adiciona na
        // lista de impares

        // VIROU A FUNÇÃO verticesImpares()
        // @ maintaining 0 <= \count <= grafo.getListaVertices().size();

        // for (Vertice ver : grafo.getListaVertices()) {
        //     if (ver.getGrau() % 2 != 0) {
                
        //         impares.add(ver.getN());
        //     }
        // }
        int nImpares = impares.size();

        ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> pares = new ArrayList<>();
        int permutacoes = impares.size() * (impares.size() - 1) / 2;

        // Cria lista com todos os pares possíveis para os vértices ímpares
        for (int i = 0; i < permutacoes; i++) {
            for (int j = 1; j < impares.size(); j++) {
                AbstractMap.SimpleEntry<Integer, Integer> par = new AbstractMap.SimpleEntry<Integer, Integer>(impares.get(0), impares.get(j));
                pares.add(par);
            }
            impares.remove(0);
            if (impares.size() == 1)
                break;
        }


        ArrayList<ArrayList<AbstractMap.SimpleEntry<Integer, Integer>>> combinacoesDePares = new ArrayList<>();
        int perm = pares.size() * (pares.size() - 1) / 2;
        if(pares.size() == 1){
            ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> combinacao = new ArrayList<>();
            combinacao.add(pares.get(0));// adiciona o primeiro par na primeira combinacao
            combinacoesDePares.add(combinacao);// adiciona a primeira combinacao
        }

        // Achar todas as combinações que passem por todos os vértices
        int nCombinacoesPar = nImpares - 3;
        for (int i = 0; i < perm; i++) {// par que será adicionado primeiro
            ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> combinacao = new ArrayList<>();
            combinacao.add(pares.get(0));                                                                               // Adiciona o par da posição 0da lista de pares
            for (int j = 1; j < pares.size(); j++) {                                                                    // Para todos os pares depois do par adicionado
                for (int k = 0; k < combinacao.size(); k++) {                                                           // Para todos os pares presentes na combinação atual
                    if     (pares.get(j).getKey() == combinacao.get(k).getKey() ||                                      //
                            pares.get(j).getKey() == combinacao.get(k).getValue() ||                                    // Se um dos valores do par atual estiver dentre os pares
                            pares.get(j).getValue() == combinacao.get(k).getKey() ||                                    // da combinação atual
                            pares.get(j).getValue() == combinacao.get(k).getValue()                                     //
                    ) {
                        podeAdicionar = false;                                                                          // Torna falso a flag de adicionar o par na combinação
                        break;
                    }
                    if (combinacoesDePares.size() > 0) {
                        for (ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> comb : combinacoesDePares) {
                            if (comb.contains(pares.get(0)) && comb.get(1).equals(pares.get(j))) {
                                podeAdicionar = false;
                                break;
                            }
                        }
                    }
                }
                if(podeAdicionar) {                                                                                     // Se puder adicionar o par na combinação
                    combinacao.add(pares.get(j));                                                                       // Adiciona
                }
                podeAdicionar = true;
            }
            combinacoesDePares.add(combinacao);                                                                         // Adiciona a combinação atual na lista de combinações
            nCombinacoesPar--;
            if (pares.get(1).getKey() != pares.get(0).getKey() && nCombinacoesPar == 0)                                                         // Se todos os pares com o primeiro valor já foram examinados, break
                break;
            if (nCombinacoesPar == 0) {
                pares.remove(0);                                                                                  // Remove o par do index 0
                nCombinacoesPar = nImpares - 3;
            }
            if (pares.size() == 1 && nCombinacoesPar == 0)                                                                                      // Se o tmanho de pares for 1, break
                break;
        }


        // entre as combinacoes, encontrar a que tem menor distancia total
        int menor = 0;
        double menorD = Double.POSITIVE_INFINITY;
        double soma = 0;
        for (int i = 0; i < combinacoesDePares.size(); i++) {
            for (int j = 0; j < combinacoesDePares.get(i).size(); j++) {// percorre a combinacao
                // calcula as distancias de key
                dijkstra(grafo, grafo.getListaVertices().get(combinacoesDePares.get(i).get(j).getKey() - 1));
                soma += grafo.getListaVertices().get(combinacoesDePares.get(i).get(j).getValue() - 1).getD();
            }
            if (soma < menorD) {
                menorD = soma;
                menor = i;
            }
            soma = 0;

        }


        ArrayList<ArrayList<Integer>> caminhosGerados = new ArrayList<>();
        // usar dikjstra de novo para calcular os caminhos
        if(combinacoesDePares.size() != 0){
            for (int i = 0; i < combinacoesDePares.get(menor).size(); i++) {
                dijkstra(grafo, grafo.getListaVertices().get(combinacoesDePares.get(menor).get(i).getKey() - 1));
                caminhosGerados.add(
                        calculaMenorCaminho(grafo, combinacoesDePares.get(menor).get(i).getKey(),
                                combinacoesDePares.get(menor).get(i).getValue()));
               
            }
        }

        // 1. ADICIONAR NO GRAFO, AS ARESTAS FORMADAS PELOS CAMINHOS ENCONTRADOS
        for (ArrayList<Integer> caminho : caminhosGerados) {
            for (int i = 0; i < caminho.size() - 1; i++) {
                grafo.addAresta(caminho.get(i), caminho.get(i + 1));
            }
        }

        // 2. COMO O GRAFO AGORA É EULERIANO É SO CHAMAR A FUNCAO DE TRILHA EULERIANA
        Vertice ver = grafo.getListaVertices().get(0);
        List<Vertice> trilhaEuleriana = new ArrayList<>();
        hierholzer(grafo, ver, trilhaEuleriana);

        // System.out.print("Trilha euleriana: ");
        // for (Vertice v : trilhaEuleriana) {
        //     System.out.print(v.getN() + " ");
        // }
        // System.out.println();
    }

    //@ ensures \forall int i; 0 <= i < lista.size(); (\result).getD() <= lista.get(i).getD();
    // retorna o vértice com menor distância em uma lista de vértices
    public Vertice menorD(ArrayList<Vertice> lista) {
        Vertice m = lista.get(0);
        //@ assert m != null;
        //@ maintaining 0 <= i <= lista.size();
        //@ maintaining \forall int j;  0 <= j < i; m.getD() <= lista.get(j).getD();
        //@ loop_writes i, m;
        //@ decreases lista.size() - i;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getD() < m.getD()) {
                m = lista.get(i);
            }
        }

        return m;
    }

    /**
     * Dijkstra
     * Calcula a menor distância entre o vértice fonte
     * e os demais vértices do grafo
     *
     * @param grafo
     * @param fonte
     */
    public void dijkstra(Grafo grafo, Vertice fonte) {

        ArrayList<Vertice> F = new ArrayList<>();// grupo dos vértices fechados
        ArrayList<Vertice> A = new ArrayList<>();// grupo dos vértices abertos

        F.add(fonte);

        for (Vertice ver : grafo.getListaVertices()) {
            if (ver.getN() != fonte.getN()) {
                A.add(ver);
            }
        }

        fonte.setD(0);
        fonte.setRot(Double.POSITIVE_INFINITY);

        for (Vertice ver : grafo.getListaVertices()) {// percorre o grafo
            if (ver != fonte) {// se nao for a fonte
                // rotulação inicial
                if (fonte.getListaAdjascencia().contains(ver.getN())) {// se o vértice estiver a distancia de 1 da fonte
                    ver.setRot(fonte.getN());// o rotulo dos vértices adjacentes a fonte é a própria fonte
                    ver.setD(1);// distancia da fonte é 1
                } else {
                    ver.setRot(0);// não definimos um rótulo
                    ver.setD(Double.POSITIVE_INFINITY);// não definimos uma distância
                }
            }
        }

        Vertice ver;

        while (A.size() != 0) {
            ver = menorD(A);// recebe o vértice com a menor distância entre os vértices abertos

            // fecha o vértice
            A.remove(ver);
            F.add(ver);

            for (Integer k : ver.listaAdjascencia) {// para todos os adjacentes de ver
                Vertice adjver = grafo.getListaVertices().get(k - 1);
                if (A.contains(adjver)) {// que estiverem abertos
                    if (adjver.getD() > ver.getD() + PESO) {
                        adjver.setD(ver.getD() + PESO);
                        adjver.setRot(ver.getN());
                    }
                }
            }
        }
       
    }

    /* public boolean temCaminho(Grafo grafo, int v1, int v2){
            for (int i = 0; i < grafo.getListaVertices.size(); i++) {
            }
            return false;
        }
    */

    // NÃO FUNCIONA
    // retorna o menor caminho do vértice destino até o vértice fonte depois que
    // dijkstra é executado
    //@ requires Integer.MIN_VALUE < 0 <= destino < Integer.MAX_VALUE;
    //@ requires \forall int i; 0 <= i < grafo.getListaVertices().size(); grafo.getListaVertices().get(i).getRot() >= 0;
    public ArrayList<Integer> calculaMenorCaminho(Grafo grafo, Integer fonte, int destino) {

        ArrayList<Integer> caminho = new ArrayList<>();
        caminho.add(destino);

        while (grafo.getListaVertices().get(destino-1).getRot() != fonte) {// enquanto não chegar em fonte
            double rotulo = grafo.getListaVertices().get(destino - 1).getRot();

            if (rotulo-1 >= Integer.MIN_VALUE) {
                destino = (int) rotulo;
                //@ assert (destino-1) >= Integer.MIN_VALUE;
            }
            
            caminho.add(destino);
        }

        caminho.add(fonte);

        

        return caminho;
    }

}

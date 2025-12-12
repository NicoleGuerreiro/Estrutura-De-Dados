
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DroneNav {
    private Map<Integer, List<Integer>> adjCams;

    public DroneNav(){
        this.adjCams = new HashMap<>();
    }

    public void addTunel(int v, int w){
        adjCams.putIfAbsent(v, new ArrayList<>());
        adjCams.putIfAbsent(w, new ArrayList<>());

        adjCams.get(v).add(w);
        adjCams.get(w).add(v);
    }

    public void loadMine (String filename){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line = br.readLine();
            int numCams = Integer.parseInt(line.trim());

            while((line = br.readLine()) != null){
                String[] parts = line.split(" ");
                int v = Integer.parseInt(parts[0]);
                int w = Integer.parseInt(parts[1]);
                addTunel(v,w);
            }
            System.out.println("Mapa carregado");
        }
        catch (IOException e){
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    public List<Integer> getVizinhos(int v){
        return adjCams.getOrDefault(v, Collections.emptyList());
    }

    public Map<Integer, List<Integer>> getAdjCams(){
        return adjCams;
    }

    public void startMappingDFS(DroneNav drone, int startNode, Set<Integer> visited){
        if(visited.contains(startNode)){
            return;
        }

        visited.add(startNode);
        System.out.print(startNode + " ");

        for(int vizinho : drone.getVizinhos(startNode)){
            if(!visited.contains(vizinho)){
                startMappingDFS(drone, vizinho, visited);
            }
        }

        if(visited.size() != getTodosNos().size()){
            System.out.println("Mapeamento incompleto. Areas inacessiveis detectadas");
            return;
        }
    }

    public Set<Integer> getTodosNos(){
        return adjCams.keySet();
    }

    public void findShorttestPathBFS(int startNode, int targetNode){
        System.out.print("Rota de Resgate BFS: ");

        int maxNode = adjCams.keySet().stream().max(Integer::compareTo).orElse(0);

        Queue<Integer> fila = new LinkedList<>();
        boolean[] visited = new boolean[maxNode + 1];

        int[] pai = new int[maxNode + 1];
        Arrays.fill(pai, -1);

        fila.add(startNode);
        visited[startNode] = true;

        boolean encontrado = false;

        while(!fila.isEmpty()){
            int atual = fila.poll();

            if(atual == targetNode){
                encontrado = true;
                break;
            }
            for (int vizinho : adjCams.get(atual)){
                if (!visited[vizinho]){
                    visited[vizinho] = true;
                    pai[vizinho] = atual;
                    fila.add(vizinho);
                }
            }
        }

        if (!encontrado){
            System.out.println("Alvo inalcancavel\n");
            return;
        }

        List<Integer> caminho = new ArrayList<>();
        int atual = targetNode;

        while(atual != -1){
            caminho.add(atual);
            atual = pai[atual];
        }

        Collections.reverse(caminho);

        for(int i = 0; i < caminho.size(); i++){
            System.out.print(caminho.get(i) + " ");
        }

        System.out.println();
    }
}

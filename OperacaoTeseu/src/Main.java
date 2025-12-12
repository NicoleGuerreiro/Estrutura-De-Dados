import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args){
        DroneNav drone = new DroneNav();

        drone.loadMine("mina.txt");

        System.out.print("Mapeamento DFS (Ordem): ");
        Set<Integer> visitados = new HashSet<>();
        drone.startMappingDFS(drone,0, visitados);
        System.out.println("\n");
        drone.findShorttestPathBFS(0, 5);

    }
}

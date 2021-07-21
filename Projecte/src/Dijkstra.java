import org.ietf.jgss.ChannelBinding;

import java.util.ArrayList;
import java.util.List;

public class Dijkstra {


    private Graph[][] graph;

    public Dijkstra(Graph[][] graph) {
        this.graph = graph;
    }

    public void createGraph(Graph[][] graph, Connection[] connections){


        for(int i = 0; i < connections.length; i++){
            for(int j = 0; j < connections[i].getRoomConnected().length; j++){
                if((j + 1) < connections[i].getRoomConnected().length){

                    if(graph[connections[i].getRoomConnected()[j]][connections[i].getRoomConnected()[j+1]].getProb() > connections[i].getEnemyProbability()){
                        //Introduim true i la probabilitat de l'enemic a les dues caselles que representen els nodes
                        graph[connections[i].getRoomConnected()[j]][connections[i].getRoomConnected()[j+1]].setFound(true);
                        graph[connections[i].getRoomConnected()[j]][connections[i].getRoomConnected()[j+1]].setProb(connections[i].getEnemyProbability());

                        graph[connections[i].getRoomConnected()[j+1]][connections[i].getRoomConnected()[j]].setFound(true);
                        graph[connections[i].getRoomConnected()[j+1]][connections[i].getRoomConnected()[j]].setProb(connections[i].getEnemyProbability());
                    }


                }

                if((j + 1) == connections[i].getRoomConnected().length){
                    if(graph[connections[i].getRoomConnected()[j]][connections[i].getRoomConnected()[0]].getProb() > connections[i].getEnemyProbability()) {
                        //Quan arribem a l'ultim node, el relacionarem amb el primer
                        graph[connections[i].getRoomConnected()[j]][connections[i].getRoomConnected()[0]].setFound(true);
                        graph[connections[i].getRoomConnected()[j]][connections[i].getRoomConnected()[0]].setProb(connections[i].getEnemyProbability());

                        graph[connections[i].getRoomConnected()[0]][connections[i].getRoomConnected()[j]].setFound(true);
                        graph[connections[i].getRoomConnected()[0]][connections[i].getRoomConnected()[j]].setProb(connections[i].getEnemyProbability());
                    }
                }

                if(connections[i].getRoomConnected().length == 4){
                    if(j == 0 || j == 1) {
                    if(graph[connections[i].getRoomConnected()[j]][connections[i].getRoomConnected()[j+2]].getProb() > connections[i].getEnemyProbability()) {

                                //Finalment, per a connexions de 4 nodes, connectarem el primer amb el tercer i el segon amb el quart
                                graph[connections[i].getRoomConnected()[j]][connections[i].getRoomConnected()[j + 2]].setFound(true);
                                graph[connections[i].getRoomConnected()[j]][connections[i].getRoomConnected()[j + 2]].setProb(connections[i].getEnemyProbability());


                                graph[connections[i].getRoomConnected()[j + 2]][connections[i].getRoomConnected()[j]].setFound(true);
                                graph[connections[i].getRoomConnected()[j + 2]][connections[i].getRoomConnected()[j]].setProb(connections[i].getEnemyProbability());
                            }
                    }
                }
            }

        }
    }

    public void dijkstra(Graph[][] g, Habitacio nodeInicial, Habitacio nodeFinal, Habitacio[] habitacions, Habitacio[] camiMesCurt, Connection[] connections, LlistaPAED<Integer> probTotal){


        Probability[] probabilitats = new Probability[connections.length];

        for (int i = 0; i < connections.length; i++){
            probabilitats[i] = new Probability(0, false);
        }

        probabilitats[nodeInicial.getId()].setProb(0);

        for(int i = 0; i < connections.length; i++){
            if(i != nodeInicial.getId()){
                probabilitats[i].setProb((int)Double.POSITIVE_INFINITY);
            }

        }


        boolean[] nodeVisitat = new boolean[habitacions.length];
        int nova;
        //Afegim el node del que partirem a la llista


        int mesPetit = (int) Double.POSITIVE_INFINITY;
        int index=0;

        int nodeActual = nodeInicial.getId();

        while (faltenPerVisitar(g, nodeActual, nodeFinal, connections) && !finalVisitat(g, nodeFinal, connections)){
            mesPetit = (int) Double.POSITIVE_INFINITY;
            for(int i = 0; i < connections.length; i++){
                if(g[nodeActual][i].isFound()){
                    if(!g[nodeActual][i].isVisited() && i != nodeInicial.getId()){
                        nova = probabilitats[nodeActual].getProb() + g[nodeActual][i].getProb();
                        if(probabilitats[i].getProb() > nova){
                            probabilitats[i].setProb(nova);
                        }
                    }
                }

                g[nodeActual][i].setVisited(true);

                if(i == connections.length - 1){

                    for(int k = 0; k < connections.length; k++){
                        if(mesPetit > probabilitats[k].getProb() && !probabilitats[k].isFinal_() && k != nodeInicial.getId()){
                            mesPetit = probabilitats[k].getProb();
                            nodeActual = k;
                        }
                    }

                    probabilitats[nodeActual].setFinal_(true);
                }
            }

        }

        int k=0;

        int node=0;

        node= nodeFinal.getId();

        for(int i=0; i<camiMesCurt.length;i++){

            camiMesCurt[i].setId(-1);

        }

        //Quan ja se el total de probabilitats fins a arribar al nodeFinal
        for(int i=0; i < connections.length; i++){
            if(g[node][i].isFound()) {
                if(g[node][i].isVisited()){
                    if ((probabilitats[node].getProb() - g[node][i].getProb()) == probabilitats[i].getProb()) {
                        camiMesCurt[k] = habitacions[i];
                        k++;
                        node = i;
                        if(node == nodeInicial.getId()){
                            break;
                        }
                        i = -1;
                    }
                }
            }
        }

        System.out.println("La millor ruta des de l'habitació " + nodeInicial.getId() + " fins l'habitació " + nodeFinal.getId() + " és: ");
        int count=1;

        for(int i = camiMesCurt.length - 1; i > 0; i--) {
            if (camiMesCurt[i].getId() != -1) {
								Integer num = probTotal.get(0);
								probTotal.add(0, num+g[camiMesCurt[i].getId()][camiMesCurt[i-1].getId()].getProb());
                System.out.println("\t" + count + ") - Habitació número " + camiMesCurt[i].getId());
                count++;
            }
        }
				if (camiMesCurt[0].getId() != -1) {
					System.out.println("\t" + count + ") - Habitació número " + camiMesCurt[0].getId());
					count++;
				}
				Integer num = probTotal.get(0);
				probTotal.add(0, num+g[camiMesCurt[0].getId()][habitacions[nodeFinal.getId()].getId()].getProb());
        System.out.println("\t" + count + ") - Habitació número " + habitacions[nodeFinal.getId()].getId());

    }

    private boolean finalVisitat(Graph[][] g, Habitacio nodeFinal, Connection[] connections){

        for(int i = 0; i < connections.length; i++){
            if(g[nodeFinal.getId()][i].isFound()){
                if(!g[nodeFinal.getId()][i].isVisited()){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean faltenPerVisitar(Graph[][] g, int actual, Habitacio nodeFinal,Connection[] connections){

        for(int i = 0; i < connections.length; i++){
            if(g[actual][i].isFound()){
                if(!g[actual][i].isVisited()){
                    return true;
                }
            }
        }
        return false;
    }
}

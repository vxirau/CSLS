public class PAEDHash <K,V> {

    private NodeHash <K, V>[] nodes;
    private static final int CAPACITAT = 1 << 4;
    private int tamany = 0;
    private Jugador jugador;

    public PAEDHash() {
        this(CAPACITAT);
    }

    public PAEDHash(int totalNodes) {
        this.nodes = new NodeHash[totalNodes];
    }

    public void put(K clau, V valor) {
        NodeHash<K, V> entry = new NodeHash<>(clau, valor, null);
        int posicio = getHash(clau) % getTotalNodes();
        NodeHash<K, V> existing = nodes[posicio];
        if (existing != null) {

					while (existing.seguent != null) {
							if (existing.clau.equals(clau)) {
									existing.valor = valor;
									return;
							}
							existing = existing.seguent;
					}
					if (existing.clau.equals(clau)) {
							existing.valor = valor;
					} else {
							existing.seguent = entry;
							tamany++;
					}
        } else {
					nodes[posicio] = entry;
					tamany++;
        }
    }

    private int getTotalNodes() {
        return nodes.length;
    }

    private int getHash(K clau) {
        return clau == null ? 0 : getCode(clau);
    }


    public V get(K clau) {
        NodeHash<K, V> node = nodes[getHash(clau)  % getTotalNodes()];
        while (node != null) {
            if (node.clau.equals(clau)){
                return node.valor;
            }
            node = node.seguent;
        }
        return null;
    }


//---------------PROVA 1 HASH---------------
		public int getCode(K s){
            String clau = (String) s;
			int suma = 0;
			for(int i=6; i<clau.length() ;i++){
				suma += clau.charAt(i);
			}
			return (suma*7);
		}
//------------------------------------------
//Dataset: S
//Player 5
//Temps Creació: 1014681
//Temps Cerca: 9927
//------------------------------------------
//Dataset: XXL
//Player 5
//Temps Creació: 510214517
//               279250820
//Temps Cerca: 19318
//------------------------------------------




}

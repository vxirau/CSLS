public class NodeHash<K, V> {
    final K clau;
    V valor;
    NodeHash<K, V> seguent;

    public NodeHash(K clau, V valor, NodeHash<K, V> seguent) {
        this.clau = clau;
        this.valor = valor;
        this.seguent = seguent;
    }
}

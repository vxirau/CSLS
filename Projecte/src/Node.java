import java.util.Objects;

public class Node {
    ObjectesTenda objecteTenda;
    Node left;
    Node right;



    public Node(ObjectesTenda objecteTenda) {
        this.objecteTenda = objecteTenda;
        left = null;
        right = null;
    }

    public Node() {

    }

    public ObjectesTenda getObjecteTenda() {
        return objecteTenda;
    }

    public void setObjecteTenda(ObjectesTenda objecteTenda) {
        this.objecteTenda = objecteTenda;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return objecteTenda.equals(node.objecteTenda) &&
                left.equals(node.left) &&
                right.equals(node.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objecteTenda, left, right);
    }

    @Override
    public String toString() {
        return "Node{" +
                "objecteTenda=" + objecteTenda +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}

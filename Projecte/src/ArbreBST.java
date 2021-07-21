import java.io.PrintStream;
import java.util.LinkedList;

public class ArbreBST {
    private int totalNodes;
    private Node[] result;
    private Node arrel;
    private int indexInordre;

    public ArbreBST(int totalNodes, Node arrel) {
        this.totalNodes = totalNodes;
        this.result = new Node[totalNodes];
        this.arrel = arrel;
        this.indexInordre = 0;
    }


    public void inserir(Node arrel, Node node){
        if(node.getObjecteTenda().getPrice() < arrel.getObjecteTenda().getPrice()){
            if(arrel.getLeft()==null){
                arrel.setLeft(new Node(node.getObjecteTenda()));
            }else{
                inserir(arrel.getLeft(), node);
            }
        }

        if(node.getObjecteTenda().getPrice() > arrel.getObjecteTenda().getPrice()){
            if(arrel.getRight() == null){
                arrel.setRight(new Node(node.getObjecteTenda()));
            } else {
                inserir(arrel.getRight(),node);
            }
        }
    }

    ObjectesTenda minValue(Node root){
         ObjectesTenda minv = root.getObjecteTenda();
         while (root.getLeft() != null){
                 minv = root.getLeft().getObjecteTenda();
                 root = root.getLeft();
         }
         return minv;
    }


    void deleteKey(Node node){
    arrel = deleteRec(arrel, node.getObjecteTenda().getPrice());
            totalNodes--;
    }

    Node deleteRec(Node root, int key){

        if (root == null)  return root;
        if (key < root.getObjecteTenda().getPrice() ){
            root.setLeft(deleteRec(root.getLeft(), key ));
        }else if (key  > root.getObjecteTenda().getPrice() ){
            root.setRight(deleteRec(root.getRight(), key ));
				}else{
            if (root.getLeft() == null){
                return root.getRight();
            }else if (root.getRight() == null){
                return root.getLeft();
						}
            
            root.setObjecteTenda(minValue(root.getRight()));
            root.setRight(deleteRec(root.getRight(), root.getObjecteTenda().getPrice()));
        }

        return root;
    }


    public int getTotalNodes() {
        return totalNodes;
    }

    public void setIndexInordre(int indexInordre) {
        this.indexInordre = indexInordre;
    }

    public Node getArrel() {
        return arrel;
    }

    public void setTotalNodes(int totalNodes) {
        this.totalNodes = totalNodes;
    }

    public void inordre(Node n, Node[] result){

        if(n==null){
          return;
        }else{
            inordre(n.getLeft(), result);
            result[indexInordre] = n;
            indexInordre++;
            inordre(n.getRight(), result);
        }

    }

    public void printarAbreHoritzontal() {
        System.out.print(navegar(arrel));
    }

    public String navegar(Node arrel) {

        if (arrel == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(arrel.getObjecteTenda().getName() + " (" + arrel.getObjecteTenda().getPrice() + ")");

        String pointerRight = "└──";
        String pointerLeft = (arrel.getRight() != null) ? "├──" : "└──";

        RecorrerArbre(sb, "", pointerLeft, arrel.getLeft(), arrel.getRight() != null);
        RecorrerArbre(sb, "", pointerRight, arrel.getRight(), false);

        return sb.toString();
    }

    public void RecorrerArbre(StringBuilder sb, String espais, String punter, Node node, boolean teRight) {
        if (node != null) {
            sb.append("\n");
            sb.append(espais);
            sb.append(punter);
            sb.append(node.getObjecteTenda().getName() + " (" + node.getObjecteTenda().getPrice() + ")");

            StringBuilder paddingBuilder = new StringBuilder(espais);
            if (teRight) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.getRight() != null) ? "├──" : "└──";

            RecorrerArbre(sb, paddingForBoth, pointerLeft, node.getLeft(), node.getRight() != null);
            RecorrerArbre(sb, paddingForBoth, pointerRight, node.getRight(), false);
        }
    }




}

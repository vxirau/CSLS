public class Graph {
    private boolean found;
    private int prob;
    private boolean visited;



    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public int getProb() {
        return prob;
    }

    public void setProb(int prob) {
        this.prob = prob;
    }

    public Graph(boolean found, int prob, boolean visited) {
        this.found = found;
        this.prob = prob;
        this.visited = visited;
    }
}

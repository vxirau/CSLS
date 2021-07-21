public class Probability {

    private int prob;
    private boolean final_;

    public Probability(int prob, boolean final_) {
        this.prob = prob;
        this.final_ = final_;
    }

    public int getProb() {
        return prob;
    }

    public void setProb(int prob) {
        this.prob = prob;
    }

    public boolean isFinal_() {
        return final_;
    }

    public void setFinal_(boolean final_) {
        this.final_ = final_;
    }
}

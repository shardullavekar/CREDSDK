package pojos;

public class Problem {
    private String description;

    private String id;

    private String reply;

    private String next_state;

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getNext_state() {
        return next_state;
    }

    public void setNext_state(String next_state) {
        this.next_state = next_state;
    }


    public Problem(String problem_id, String problem_description) {
        this.description = problem_description;
        this.id = problem_id;
    }


    public String getProblem_id() {
        return id;
    }

    public void setProblem_id(String problem_id) {
        this.id = problem_id;
    }


    public String getProblem_description() {
        return description;
    }

    public void setProblem_description(String problem_description) {
        this.description = problem_description;
    }

}

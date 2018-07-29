package pojos;

public class Problem {
    private String description;

    private String id;

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

package pojos;

public class Problem {
    private String problem_description;

    private String problem_id;

    public Problem(String problem_id, String problem_description) {
        this.problem_description = problem_description;
        this.problem_id = problem_id;
    }


    public String getProblem_id() {
        return problem_id;
    }

    public void setProblem_id(String problem_id) {
        this.problem_id = problem_id;
    }


    public String getProblem_description() {
        return problem_description;
    }

    public void setProblem_description(String problem_description) {
        this.problem_description = problem_description;
    }

}

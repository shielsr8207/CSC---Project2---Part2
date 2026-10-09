public record NumNode(double num) implements AST{

    /* evaluates the expression and returns the value it is called on as a double
    input: none / output: double
    @param none
     */

    @Override
    public double eval() {
        return num;
    }
}

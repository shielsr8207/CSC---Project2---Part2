public record BinopNode(String op1, AST num1, AST num2) implements AST{

    /* calculates the inputs based on its right and left child, using the root operator
    specifically with linked lists, num1.eval(for example) then calls eval on the left child,
    and that left child then has eval called on both its children, unless it is a leaf/numNode
    input: String, double, double / output: double
    @param none
     */

    @Override
    public double eval() {
        double left = num1.eval();
        double right = num2.eval();
        switch (op1) {
            case "+":
                return left + right;
            case "-":
                return left - right;
            case "*":
                return left * right;
            case  "/":
                return left / right;
            case "^":
                return Math.pow(left, right);
            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

}

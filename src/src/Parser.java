public class Parser {
    /*
    checks if the input is an operator, if not it just returns false
    input string / output : boolean
    @Param token : String
     */
    public static boolean isOperator(String token) {
        switch (token) {
            case "+" , "-" , "*", "/" , "^":
                return true;
            default:
                return false;
        }
    }

    /*
    intakes the expression stack, and the operator that is currently being viewed
    creates a new binop node and pushes it into the expression stack
    This BinopNode must contain only one operator, and the operands
    are either numNodes or BinopNodes
    input expression stack and operator string / output none
    @Param expressions : ArrayStack<AST> , op : String
     */
    private static void applyOps(ArrayStack<AST> expressions, String op) {
        if (expressions.size() < 2) {
            throw new IllegalArgumentException("insufficient operands");
        }
        AST right = expressions.pop();
        AST left = expressions.pop();
        expressions.push(new BinopNode(op, left, right));
    }


    /*
    intakes two operators and checks to see which one comes first in order of
    operations, will return true if the first input comes first in precedence
    before the second
    input two strings / output: boolean
    @Param op1 : string , op2 : string
     */
    private static boolean op1GoesFirst(String op1, String op2) {
        switch (op1){
            case "+" , "-":
                if (op2.equals("+") || op2.equals("-")) {
                    return true;
                } else if (op2.equals("*") || op2.equals("/") || op2.equals("^")) {
                    return false;
                }

           default:
                if (op2.equals("^")) {
                    return false;
                } else if (isOperator(op2)) {
                    return true;
                }
        }
        throw new IllegalArgumentException("insufficient operands");
    }

    /* converts the current postfix expression it's called on to an AST
    checks if input is empty
    tokenizes strings into array
    iterates over token array
        checks if token is operator
            if true
                pops next 2 stack tokens and applys operator to them
            if false
                pushes token into the stack
    expression stack should have final answer so return the last pop
    input: String / output: AST
    @param input : String
     */
    public static AST parsePostfix(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("empty input");
        }
        String[] tokens = input.split("\\s+");

        ArrayStack<AST> stack = new ArrayStack<>();
        for(String token : tokens) {
            if(isOperator(token)) {
                applyOps(stack, token);
            } else {
                try {
                    double value = Double.parseDouble(token);
                    stack.push(new NumNode(value));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("invalid token");
                }
            }
        }
        if(stack.size() == 1) {
            return stack.pop();
        } else {
            throw new IllegalArgumentException("too many operands");
        }
    }

    /*
    converts an infix string to an ast stack
    checks if input is empty
    tokenizes string into token array
    create stack for expressions and another for operators
    iterates over token array
        checks what token is
            if open parenthesis
                push into operator stack
            if closed parenthesis
                must do all operations inside (all operations inside should already be in order of operations)
            if operator
                check to see if operators in stack, if the last operator is parenthesis, and if current operator goes before last operator
                    loop while all are false
                        pop expression stack twice and apply the last operator
            else
                push token into expression stack if a number
     expression stack should have final answer so return the last pop
     input string / output AST
     @Param input : String
     */
    public static AST parseInfix(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("empty input");
        }
        String[] tokens = input.split("\\s+");
        ArrayStack<AST> expressions = new ArrayStack<>();
        ArrayStack<String> operators = new ArrayStack<>();
        for (String token : tokens) {
            switch (token) {
                case ("("):
                    operators.push("(");
                    break;
                case (")"):
                    while (operators.size() > 0 && !operators.peek().equals("(")) {
                        applyOps(expressions, operators.pop());
                    }
                    if (operators.isEmpty()) {
                            throw new IllegalArgumentException("mismatched close paren");
                        }
                    operators.pop();
                    break;
                case "+" , "-" , "*", "/", "^" :
                    while (!operators.isEmpty() && !operators.peek().equals("(") && op1GoesFirst(operators.peek(), token)) {
                        applyOps(expressions, operators.pop());
                    }
                    operators.push(token);
                    break;
                default:
                    try {
                        double value = Double.parseDouble(token);
                        expressions.push(new NumNode(value));
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("invalid token");
                    }

            }
        }
        while (!operators.isEmpty()) {
            String op  = operators.pop();
            if(op.equals("(")) {
                throw new IllegalArgumentException("mismatched open paren");
            }
            applyOps(expressions, op);
        }
        if(expressions.size() == 1) {
            return expressions.pop();
        } else {
            throw new IllegalArgumentException("too many operands");
        }
    }
}

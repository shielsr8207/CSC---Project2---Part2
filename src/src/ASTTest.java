import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ASTTest {


    // 3 + 4 = 7
    @Test
    public void binopEvalAdd() {
        AST node = new BinopNode("+", new NumNode(3), new NumNode(4));
        assertEquals(7.0, node.eval());
    }

    // 8 - 2 = 6
    @Test
    public void binopEvalSubtract() {
        AST node = new BinopNode("-", new NumNode(8), new NumNode(2));
        assertEquals(6.0, node.eval());
    }

    // 3 * 4 = 12
    @Test
    public void binopEvalMultiply() {
        AST node = new BinopNode("*", new NumNode(3), new NumNode(4));
        assertEquals(12.0, node.eval());
    }

    // 6 / 4 = 1.5
    @Test
    public void binopEvalDivide() {
        AST node = new BinopNode("/", new NumNode(6), new NumNode(4));
        assertEquals(1.5, node.eval());
    }

    // 2 ^ 3 = 8
    @Test
    public void binopEvalPower() {
        AST node = new BinopNode("^", new NumNode(2), new NumNode(3));
        assertEquals(8.0, node.eval());
    }


    // 2 * (3 + 4) = 14
    @Test
    public void binopEvalNestedRight() {
        AST node = new BinopNode("*",
                new NumNode(2),
                new BinopNode("+", new NumNode(3), new NumNode(4)));
        assertEquals(14.0, node.eval());
        }

}


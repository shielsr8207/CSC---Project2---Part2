import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParseTest {

    // ( 2 + 3 * 4 ) + 2 = 16
    @Test
    public void parseMultiplyAfterAd() {
        AST expected = new BinopNode("+", new BinopNode("+", new NumNode(2), new BinopNode("*", new NumNode(3), new NumNode(4))), new NumNode(2));
        AST actual = Parser.parseInfix("( 2 + 3 * 4 ) + 2");
        assertEquals(expected, actual);
        assertEquals(16.0, actual.eval());

        AST expectedPostfix = new BinopNode("+", new BinopNode("+", new NumNode(2), new BinopNode("*", new NumNode(3), new NumNode(4))), new NumNode(2));
        AST actualPostfix = Parser.parsePostfix("2 3 4 * + 2 +");
        assertEquals(expectedPostfix, actualPostfix);
        assertEquals(16.0, actualPostfix.eval());
    }

    // ( 2 * 3 + 4 ) + 2 = 12
    @Test
    public void parseMultiplyBeforeAdd() {
        AST expected = new BinopNode("+", new BinopNode("+", new BinopNode("*", new NumNode(2), new NumNode(3)), new NumNode(4)), new NumNode(2));
        AST actual = Parser.parseInfix("( 2 * 3 + 4 ) + 2");
        assertEquals(expected, actual);
        assertEquals(12.0, actual.eval());

        AST expectedPostfix = new BinopNode("+", new BinopNode("+", new BinopNode("*", new NumNode(2), new NumNode(3)), new NumNode(4)), new NumNode(2));
        AST actualPostfix = Parser.parsePostfix("2 3 * 4 + 2 +");
        assertEquals(expectedPostfix, actualPostfix);
        assertEquals(12.0, actualPostfix.eval());
    }


    //(3 ^ 2 - 3) * 5 = 30
    @Test
    public void parseParenMultiplyAfterSubtractAndPow() {
        AST expected = new BinopNode("*", new BinopNode("-", new BinopNode("^", new NumNode(3), new NumNode(2)), new NumNode(3)), new NumNode(5));
        AST actual = Parser.parseInfix("( 3 ^ 2 - 3 ) * 5");
        assertEquals(expected, actual);
        assertEquals(30.0, actual.eval());

        AST expectedPostfix = new BinopNode("*", new BinopNode("-", new BinopNode("^", new NumNode(3), new NumNode(2)), new NumNode(3)), new NumNode(5));
        AST actualPostfix = Parser.parsePostfix("3 2 ^ 3 - 5 *");
        assertEquals(expectedPostfix, actualPostfix);
        assertEquals(30.0, actualPostfix.eval());
    }

    //(3 ^ 2 ^ 2) / 5 = 16.2
    @Test
    public void parseMultiplePow() {
        AST expected = new BinopNode("/", new BinopNode("^", new NumNode(3), new BinopNode("^", new NumNode(2), new NumNode(2))), new NumNode(5));
        AST actual = Parser.parseInfix("( 3 ^ 2 ^ 2 ) / 5");
        assertEquals(expected, actual);
        assertEquals(16.2, actual.eval());

        AST expectedPostfix = new BinopNode("/", new BinopNode("^", new NumNode(3), new BinopNode("^", new NumNode(2), new NumNode(2))), new NumNode(5));
        AST actualPostfix = Parser.parsePostfix("3 2 2 ^ ^ 5 /");
        assertEquals(expectedPostfix, actualPostfix);
        assertEquals(16.2, actualPostfix.eval());
    }
}
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.Set;
import static java.lang.System.*;

public class SyntaxChecker
{
        private String exp;
        private Stack<Character> symbols;
        private HashMap<Character, Character> matchedSymbols;

        public SyntaxChecker()
        {
                symbols = new Stack<Character>();
                exp = "";

                matchedSymbols = new HashMap<Character, Character>();
                matchedSymbols.put('}', '{');
                matchedSymbols.put(')', '(');
                matchedSymbols.put('>', '<');
                matchedSymbols.put(']', '[');
        }

        /**@param s expression to check for */
        public SyntaxChecker(String s)
        {
                symbols = new Stack<Character>();
                setExpression(s);

                matchedSymbols = new HashMap<Character, Character>();
                matchedSymbols.put('}', '{');
                matchedSymbols.put(')', '(');
                matchedSymbols.put('>', '<');
                matchedSymbols.put(']', '[');
        }

        public void setExpression(String s)
        {
                exp = s;
        }

        /**
        * <p>Extends current symbols to check for with the ones specified in the {@code map}.
        * Default symbols are:</p>
        *
        * <p>{@code {(<[]>)}</p>
        *
        * @param map map with new matched symbols
        */
        public void addMatchedSymbols(final HashMap<Character, Character> map)
        {
                matchedSymbols.putAll(map);
        }

        public boolean checkExpression()
        {
                symbols.clear();

                /*We want to check only opening and closing symbols*/
                final String expr = parseExpression();

                if (expr.length() % 2 != 0)
                {
                        /*Size is uneven, something is mismatched for sure*/
                        return false;
                }

                for (int i = 0; i < expr.length(); i++)
                {
                        final char ch = expr.charAt(i);
                        final Collection<Character> openingSymbols = matchedSymbols.values();
                        boolean isOpening = false;
           
                        for (final Character symbol : openingSymbols)
                        {
                                if (ch == symbol)
                                {
                                        isOpening = true;
                                        break;
                                }
                        }
                        if (isOpening)
                        {
                                symbols.push(ch);
                        }
                        else
                        {
                                /*There is no expressions where first symbol is a closing one*/
                                if (symbols.isEmpty())
                                {
                                        return false;
                                }
                                final char test = symbols.pop();
                                if (test != matchedSymbols.get(ch))
                                {
                                        return false;
                                }
                        }
                }

                return symbols.isEmpty();
        }

        private String parseExpression()
        {
                String pExp = "";
                final Set<Entry<Character, Character>> entries = matchedSymbols.entrySet();
                final ArrayList<Character> matchingSymbols = new ArrayList<Character>();
                for (final Entry<Character, Character> entry : entries)
                {
                        matchingSymbols.add(entry.getKey());
                        matchingSymbols.add(entry.getValue());
                }
                for (int i = 0; i < exp.length(); i++)
                {
                        for (char symbol : matchingSymbols)
                        {
                                if (exp.charAt(i) == symbol)
                                {
                                        pExp += symbol;
                                }
                        }
                }

                return pExp;
        }
        //write a toString
        public String toString()
        {
                return checkExpression() ? exp + " is correct.\n" : exp + " is incorrect.\n";
        }
}

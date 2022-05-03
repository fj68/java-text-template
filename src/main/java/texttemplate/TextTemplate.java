package texttemplate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Very simple text template class.
 * 
 * <table>
 * <caption>Abailable Formats</caption>
 * <tr>
 * <th>Code</th>
 * <th>Description</th>
 * </tr>
 * <tr>
 * <td>{@code ${variable}}</td>
 * <td>Variables</td>
 * </tr>
 * <tr>
 * <td>{@code #{comment}}</td>
 * <td>Comments</td>
 * <tr>
 * <td>{@code '\n'}, {@code '\\'}, {@code '\$'} etc.</td>
 * <td>Backslash to escape preseeding charactors while {@code '\n'}, {@code '\r'} and {@code '\t'}
 * is treated as escape sequence
 * <td>
 * </tr>
 * </table>
 */
public class TextTemplate {
  /**
   * Renders text with values interpolated.
   * 
   * Each values are interpolated by {@code ${0}}, {@code ${1}} ... etc. according to its order to
   * be given.
   * 
   * @param text Format text
   * @param values Values to interpolate
   * @return Result of evaluation
   */
  public static String render(String text, String... values) {
    var m = new HashMap<String, String>();
    for (var i = 0; i < values.length; i++) {
      m.put(Integer.toString(i), values[i]);
    }
    return new TextTemplate(text).evaluate(m);
  }

  private enum State {
    Text, Var, Comment,
  }

  private Scanner scanner;
  private State state;
  private List<Expr> exprs;

  /**
   * Creates template.
   * 
   * This only prepares template engine and not compile it until it's necessary.
   * 
   * @param text Template text
   */
  public TextTemplate(String text) {
    this.scanner = new Scanner(text);
    this.state = State.Text;
    this.exprs = new LinkedList<Expr>();
  }

  /**
   * Returns the template is already compiled or not.
   * 
   * @return True if it's compiled
   */
  public boolean isCompiled() {
    return !this.exprs.isEmpty();
  }

  /**
   * Compiles template.
   * 
   * Usually it's not necessary to call this directly because the template itself will call at first
   * evaluation to defer compilation until it's necessary. The compilation will occur only once per
   * template and it has no effect to call this method multiple times on the same tempalte.
   */
  public void compile() {
    if (this.isCompiled()) {
      return;
    }
    while (!this.scanner.isEof()) {
      (switch (this.state) {
        case Text -> this.parse_text();
        case Var -> this.parse_var();
        case Comment -> this.parse_comment();
      }).ifPresent(expr -> this.exprs.add(expr));
    }
    this.exprs.add(new Expr(Expr.Kind.Text, this.scanner.pop()));
  }

  /**
   * Evaluate template.
   * 
   * Use {@link #evaluate} method to pass values which will be interpolated. All the interpolation
   * values become empty string because they are not found as no env provided.
   * 
   * @return Result text of evaluation
   */
  public String evaluate() {
    return this.evaluate(new HashMap<String, String>());
  }

  /**
   * Evaluate template in given environment.
   * 
   * Environment is a map from keys to interpolation values. When a key is not found in the given
   * env, its value will be an empty string.
   * 
   * @param env Map from key to value (both {@link String})
   * @return Result of evaluation
   */
  public String evaluate(Map<String, String> env) {
    this.compile();
    return this.exprs.stream().map(expr -> switch (expr.kind) {
      case Text -> expr.value;
      case Var -> env.getOrDefault(expr.value, "");
      case Comment -> "" /* pass */;
    }).collect(Collectors.joining());
  }

  private Optional<Expr> parse_text() {
    var c = this.scanner.next();
    if (c == '\\') {
      this.parse_escape();
      return Optional.empty();
    } else if (c == '$' && this.scanner.peek() == '{') {
      this.scanner.incr();
      this.setNextState(State.Var);
      return Optional.of(new Expr(Expr.Kind.Text, this.scanner.pop()));
    } else if (c == '#' && this.scanner.peek() == '{') {
      this.scanner.incr();
      this.setNextState(State.Comment);
      return Optional.of(new Expr(Expr.Kind.Text, this.scanner.pop()));
    }
    this.scanner.push(c);
    return Optional.empty();
  }

  private void setNextState(State state) {
    this.state = state;
  }

  private void parse_escape() {
    var c = this.scanner.next();
    this.scanner.push(switch (c) {
      case 'n' -> '\n';
      case 'r' -> '\r';
      case 't' -> '\t';
      default -> c;
    });
  }

  private Optional<Expr> parse_var() {
    var c = this.scanner.next();
    if (c == '}') {
      this.setNextState(State.Text);
      return Optional.of(new Expr(Expr.Kind.Var, this.scanner.pop()));
    }
    this.scanner.push(c);
    return Optional.empty();
  }

  private Optional<Expr> parse_comment() {
    var c = this.scanner.next();
    if (c == '}') {
      this.setNextState(State.Text);
      return Optional.of(new Expr(Expr.Kind.Comment, this.scanner.pop()));
    }
    this.scanner.pop();
    return Optional.empty();
  }
}

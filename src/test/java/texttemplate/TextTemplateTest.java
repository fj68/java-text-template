package texttemplate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;

class TextTemplateTest {
    @Nested
    @DisplayName("render()")
    class Render {
        @Test
        @DisplayName("Render given string as-is")
        void testRenderGivenStringAsIs() {
            var expected = "fja;iwja; dfjawoef   adjfalkjda;\nea";
            var actual = TextTemplate.render("fja;iwja; dfjawoef   adjfalkjda;\nea");
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Render given string with values interpolated")
        void testRenderGivenStringWithValuesInterpolated() {
            var expected = "abcdef";
            var actual = TextTemplate.render("abc${0}", "def");
            assertEquals(expected, actual);
        }
    }
    @Nested
    @DisplayName("isCompiled()")
    class IsCompiled {
        @Test
        @DisplayName("Returns false before compile()")
        void testReturnsFalseBeforeCompile() {
            var template = new TextTemplate("");
            var expected = false;
            var actual = template.isCompiled();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Returns true after compile()")
        void testReturnsTrueAfterCompile() {
            var template = new TextTemplate("");
            template.compile();
            var expected = true;
            var actual = template.isCompiled();
            assertEquals(expected, actual);
        }
    }
    @Nested
    @DisplayName("evaluate() interpolation")
    class Evaluate {
        @Test
        @DisplayName("Returns given string as-is without env")
        void testReturnsGivenStringAsIsWithoutEnv() {
            var template = new TextTemplate("hgu75860u 807riohnihbhru b");
            var expected = "hgu75860u 807riohnihbhru b";
            var actual = template.evaluate();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Returns given string as-is with empty env")
        void testReturnsGivenStringAsIsWithEmptyEnv() {
            var template = new TextTemplate("hgu75860u 807riohnihbhru b");
            var expected = "hgu75860u 807riohnihbhru b";
            var actual = template.evaluate(new HashMap<String, String>());
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Returns interpolated string with env")
        void testReturnsInterpolatedStringAsIsWithEnv() {
            var env = new HashMap<String, String>();
            env.put("value", "6889hjknh iy8urhv");
            var template = new TextTemplate("hgu75860u ${value}807riohnihbhru b");
            var expected = "hgu75860u 6889hjknh iy8urhv807riohnihbhru b";
            var actual = template.evaluate(env);
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Returns empty string when key is not found")
        void testReturnsEmptyStringWhenKeyIsNotFound() {
            var env = new HashMap<String, String>();
            env.put("value", "6889hjknh iy8urhv");
            var template = new TextTemplate("hgu75860u ${notFound}807riohnihbhru b");
            var expected = "hgu75860u 807riohnihbhru b";
            var actual = template.evaluate(env);
            assertEquals(expected, actual);
        }
    }

    @Nested
    @DisplayName("Evaluate: Escape")
    class EvaluateEscape {
        @Test
        @DisplayName("Escape special charactor '\n'")
        void testEscapeSpecialCharactorN() {
            var template = new TextTemplate("hgu75860u \\n807riohnihbhru b");
            var expected = "hgu75860u \n807riohnihbhru b";
            var actual = template.evaluate();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Escape special charactor '\r'")
        void testEscapeSpecialCharactorR() {
            var template = new TextTemplate("hgu75860u \\r807riohnihbhru b");
            var expected = "hgu75860u \r807riohnihbhru b";
            var actual = template.evaluate();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Escape special charactor '\t'")
        void testEscapeSpecialCharactorT() {
            var template = new TextTemplate("hgu75860u \\t807riohnihbhru b");
            var expected = "hgu75860u \t807riohnihbhru b";
            var actual = template.evaluate();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Escape special charactor '\\'")
        void testEscapeSpecialCharactorBackslash() {
            var template = new TextTemplate("hgu75860u \\\\807riohnihbhru b");
            var expected = "hgu75860u \\807riohnihbhru b";
            var actual = template.evaluate();
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("Escape special charactor '$'")
        void testEscapeSpecialCharactorDollar() {
            var template = new TextTemplate("hgu75860u \\${807riohnihbhru b}");
            var expected = "hgu75860u ${807riohnihbhru b}";
            var actual = template.evaluate();
            assertEquals(expected, actual);
        }
    }
    @Nested
    @DisplayName("Evaluate: Comment")
    class EvaluateComment {
        @Test
        @DisplayName("Comments not rendered")
        void testCommentsNotRendered() {
            var template =
                    new TextTemplate("hgu75860u #{ this is a comment \\n \n }807riohnihbhru b");
            var expected = "hgu75860u 807riohnihbhru b";
            var actual = template.evaluate();
            assertEquals(expected, actual);
        }
    }
}

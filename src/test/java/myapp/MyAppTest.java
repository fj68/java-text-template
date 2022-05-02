package myapp;

// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import static org.junit.jupiter.api.Assertions.AssertEqual;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.fail;

class MyAppTest {
  /*
   * @BeforeAll static void beforeAll() { System.out.println("Start MyAppTest"); }
   */

  /*
   * @AfterAll static void afterAll() { System.out.println("Finish MyAppTest"); }
   */

  @Test
  @DisplayName("Test A Method")
  void testAMethod() {
    fail("Must fail.");
  }
}

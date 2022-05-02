# java-text-template

Very simple text template library in Java, which is intended to be used like a [Literal String Interpolation a.k.a. "f-string" in Python](https://peps.python.org/pep-0498/) and thus not a full-stack templating library.

## How it looks like

### TextTemplate.render(String fmt, String... values)

```java
System.out.println(TextTemplate.render("${0}, ${1}!", "Hello", "world"));
```

### TextTemplate#evaluate(Map<String, String> values)

```java
/* Renderable.java */
package main;

import java.util.Map;
import work.fj68.texttemplate.TextTemplate;

public abstract class Renderable {
  public abstract Map<String, String> toMap();
  public abstract String getHtmlTemplate();
  
  private TextTemplate template;
  
  protected Renderable() {
    this.template = new TextTemplate(this.getHtmlTemplate());
  }
  
  public String render() {
    return this.render(new HashMap<String, String>());
  }
  
  public String render(Map<String, String> m) {
    return this.template.evaluate(this.toMap().putAll(m));
  }
}
```

```java
/* User.java */
package main;

import java.util.Map;
import java.util.HashMap;

public class User {
  public String name;
  public String avatorUrl;
  
  public User(String name, String avatorUrl) {
    this.name = name;
    this.avatorUrl = avatorUrl;
  }
  
  public User(String name) {
    this(name, "");
  }
```  

```java
/* Post.java */
package main;

import java.util.Map;
import java.util.HashMap;

public class Post extends Renderable {
  public String title;
  public User author;
  public String body;
  
  public Post(String title, User author, String body) {
    this.title = title;
    this.author = author
    this.body = body;
  }
  
  public Map<String, String> toMap() {
    var m = new HashMap<String, String>();
    m.put('title', this.title);
    m.put('author.name', this.author.name);
    m.put('body', this.body);
    return m;
  }
  
  public String getHtmlTemplate() {
    return """
      #{TODO: add author and lastModified}
      <section class="post">
        <header>
          <h2 class="title">${title}</h2>
          <div class="author">
            ${author.avator}
            <span class="name">${author.name}</span>
          </div>
        </header>
        <p class="body">${body}</p>
      </section>
    """;
  }
  
  @Override
  public String render(Map<String, String> m) {
    var avator = TextTemplate.render(
      "<img class=\"avator\" src=\"${0}\" alt=\"${1}\">",
      this.author.avatorUrl,
      this.author.name
    );
    m.put("author.avator", avator);
    return super.render(m);
  }
}
```

```java
/* MyApp.java */
package main;

public class MyApp {
  public static void main(String[] args) {
    var post = new Post("This is a title.", "This is a body.");
    System.out.println(post.render());
  }
}
```

## Requirements

- JDK 11+

## Installation

TBA

## Documentation

TBA

## For Developpers

1. `git clone git@github.com/fj68/java-text-template.git`
2. `cd java-text-template`

### Compile

```sh
gradle complieJava
```

### Run tests

```sh
gradle test
```

### Generate documentation

```sh
gradle javadoc
```

### Clean

```sh
gradle clean
```

### Full build

```sh
gradle clean build
```

## License

MIT

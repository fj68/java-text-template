# java-vscode-starter
Starter template for Java with VSCode

## What's necessary

- [Visual Studio Code](https://azure.microsoft.com/ja-jp/products/visual-studio-code/?cdn=disable)
- JDK 11+
- [Gradle](https://gradle.org/)
- Git

## What's included

- VS Code extentions
  - [Japanese Language Pack for Visual Studio Code](https://marketplace.visualstudio.com/items?itemName=MS-CEINTL.vscode-language-pack-ja)
  - [GitLens](https://marketplace.visualstudio.com/items?itemName=eamodio.gitlens)
  - [Java Extention Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
  - [Gradle for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-gradle)
  - [SonarLint](https://marketplace.visualstudio.com/items?itemName=SonarSource.sonarlint-vscode)
  - [CheckStyle for Java](https://marketplace.visualstudio.com/items?itemName=shengchen.vscode-checkstyle) with [Google Java Style](https://google.github.io/styleguide/javaguide.html)
- Code format on save
- Lint & IntelliSence
- Run application with args
- Test & Debug with [JUnit 5](https://junit.org/junit5/)

## Installation

1. Click '[Use this template](https://github.com/fj68/java-vscode-starter/generate)' button on this page to create new repository
2. `git clone git@github.com/your-name/your-repository.git`
3. `cd your-repository`
4. Open `your-repository` in VS Code
5. Install recommended extentions

## How to

### Compile

```sh
gradle complieJava
```

### Run tests

```sh
gradle test
```

### Run application

```sh
gradle run -Pargs='arg1 arg2'
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

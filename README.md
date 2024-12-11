# Java-based Test Automation Framework for TODO Manager
This project is a Java-based test automation framework using Rest Assured for API testing of the most straightforward TODO manager with CRUD operations.

---

## Prerequisites
Before you begin, ensure you have the following installed:

- **Java 17** (or later)
- **Maven** (for dependency management and build)
- **JUnit 5.7.2** (for writing and executing tests)
- **Rest Assured 5.3.0** (for API testing)
- **Allure 2.25.0** (for test reporting)
- **Owner 1.0.11** (for configuration management)
- **Lombok 1.18.20** (for reducing boilerplate code in Java classes)
- **Tyrus Standalone Client 2.0.0** (for provides the WebSocket client implementation)
- **Jakarta WebSocket API 2.1.0** (for defines the WebSocket communication protocol for Java applications)

---
## Install dependencies:

Ensure that **Maven** is installed on your system. Then, run the following command in the project root directory to install all dependencies:

```bash
mvn clean install
```
---
## Usage

Once the project is set up, you can run the tests.

### To execute all tests use the command:

```bash
mvn test
```

### To run a specific test class in a specific environment, use the command:
```bash
mvn clean test -DenvName=environmentName -Dtest=YourTestClassName
```
---

## Test Execution and Reporting

### Test Reports with Allure

Once the tests are executed, you can generate and view the test reports using Allure.

To generate an Allure report run the following command:

```bash
mvn allure:serve
```
---
## Rules for Writing New Tests

**1. Tests Should Be Independent**
Each test must be executed in isolation, ensuring that no test depends on the result of another. This prevents false positives or negatives caused by shared states.

**2. Test Structure**
Tests should follow this hierarchy structure:

**Epic** → **Feature** → **Story** → **Test**

**3. Test Naming Convention**
The name of each test should clearly describe the **result** and **execution conditions**.
A recommended naming convention format is:
```text
Result_Condition
```

**4. Test Structure**
- Test Data Generation
- Empty Line
- Tested Query
- Empty Line
- Assertions

**5.Import Style**

Do not use wildcard imports like `import class.*`.

To configure this in IntelliJ IDEA:

1. Go to **Editor** → **Code Style** → **Java**.
2. Set the **Class count to use import with '\*'** to `99`.
3. Set the **Names count to use static import with '\*'** to `99`.

**6. Code Formatting** 

Before pushing your changes, make sure to format all the modified files to maintain consistent code style.

In **IntelliJ IDEA**, you can format the code by pressing:
- **macOS**: `Option + Command + L`
- **Windows/Linux**: `Ctrl + Alt + L`
---

## Contributing

If you'd like to contribute to the project, please follow these steps:

1. **Fork the repository**
2. **Create a new branch**
   ```bash
   git checkout -b feature/your-feature
   ```
3. **Make your changes**
4. **Commit your changes**
   ```bash
   git commit -m 'Add some feature'
   ```
5. **Push to the branch**
   ```bash
   git push origin feature/your-feature
   ```
6. **Open a pull request**
# Lab 1 - Unit Testing (with JUnit 5)

## Overview

Unit Testing is a software testing technique where individual units or components of a software application are tested in isolation to ensure that they work as expected. A unit is the smallest testable part of an application, such as a function or method. Unit tests are typically automated and are run every time a change is made to the codebase.

Unit testing helps to identify defects or bugs in the code early in the development process. This leads to better code quality, fewer defects in the final product, and shorter development cycles. Unit tests can also serve as documentation for the code, making it easier for developers to understand what the code does and how it works.

## What is JUnit?

JUnit is a popular open-source testing framework for Java applications. It provides a simple and easy-to-use API for writing unit tests. JUnit allows developers to write test cases that are executed automatically by a test runner.

JUnit provides a set of annotations that can be used to mark test methods and configure the behavior of the test runner. JUnit also provides a set of assertions that can be used to verify that the code being tested behaves as expected.

## Best Practices

-   Write small, focused tests that test a single unit of code.
-   Write tests that are easy to understand and maintain.
-   Write tests that are independent of each other.
-   Write tests that are repeatable and reliable.
-   Write tests that are fast to run.
-   Write tests that are easy to debug.


## Advantages of Unit Testing

There are several advantages to using Unit Testing, including:

**Early detection of defects**: Unit tests can catch defects early in the development process, making them easier and less expensive to fix.

**Improved code quality**: Unit tests help to ensure that the code being developed is of high quality and meets the requirements of the application.

**Faster development cycles**: Unit tests enable developers to catch and fix defects quickly, reducing the time it takes to develop and deploy software.

**Easier maintenance:** Unit tests serve as documentation for the code, making it easier for developers to understand and maintain the codebase.

**Increased confidence**: Unit tests provide a level of confidence that the code is working as expected, reducing the risk of defects in the final product.


## JUnit 5

JUnit 5 is the latest version of the JUnit testing framework. It is a major rewrite of the framework that provides a new set of APIs and features. JUnit 5 is the first version of JUnit to support Java 8 and above.


### JUnit 5 Example

```java
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    public void testAdd() {
        Calculator calculator = new Calculator();
        int result = calculator.add(1, 2);
        assertEquals(3, result);
    }
}
```
### JUnit 5 Annotations

| Annotation | Description |
| --- | --- |
| @Test | Marks a method as a test method. |
| @BeforeAll | Marks a method as a method that should be executed before all test methods. |
| @AfterAll | Marks a method as a method that should be executed after all test methods. |
| @BeforeEach | Marks a method as a method that should be executed before each test method. |
| @AfterEach | Marks a method as a method that should be executed after each test method. |
| @Disabled | Marks a test class or test method as disabled. |
| @DisplayName | Specifies a custom display name for a test class or test method. |


### Cheatsheet

![JUnit 5 Cheatsheet](https://www.jrebel.com/sites/rebel/files/image/2019-11/image-resources-junit-cheat-sheet.jpg)


## Useful Links

-   [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)

-   [JUnit 5 API Documentation](https://junit.org/junit5/docs/current/api/)

-  [JUnit 5 POM](https://raw.githubusercontent.com/bonigarcia/mastering-junit5/master/junit5-basic-tests/pom.xml)

-  [JUnit 5 Cheatsheet](https://www.jrebel.com/blog/junit-cheat-sheet)


---

## Maven Surefire Plugin

Maven Surefire Plugin is a plugin for the Apache Maven build system that provides support for executing unit tests. It is responsible for running JUnit tests during the test phase of the Maven build process.

The Surefire Plugin uses JUnit to execute tests and generates reports on the test results. By default, the plugin will execute all test classes in the src/test/java directory that match the pattern **/*Test.java.

The Surefire Plugin can be configured to execute tests in parallel, which can significantly reduce the time it takes to run the tests.

## Maven Failsafe Plugin

Maven Failsafe Plugin is another plugin for the Apache Maven build system that provides support for executing integration tests. It is responsible for running JUnit tests during the verify phase of the Maven build process.

The Failsafe Plugin uses JUnit to execute tests and generates reports on the test results. By default, the plugin will execute all test classes in the src/test/java directory that match the pattern **/*IT.java.
# ☕ Java Interview Notes
## AI Driven Selenium Automation Framework
Author : Ashutosh Kumar Sahu
---
# Progress
| Sprint | Topic | Status |
|---------|-------|--------|
| Sprint 1 | Maven Multi Module | ✅ |
| Sprint 2 | Git & GitHub | ✅ |
| Sprint 3 | Package Structure | ✅ |
| Sprint 4 | Configuration Manager Skeleton | ✅ |
| Sprint 5 | Singleton Pattern | ✅ |
--
# 1. Java Basics
## What is Java?
Java is an Object-Oriented Programming Language developed by Sun Microsystems.
### Features
- Platform Independent
- Object Oriented
- Secure
- Robust
- Multithreaded
- Portable
- Automatic Garbage Collection
---
## JVM
JVM (Java Virtual Machine) executes Java Bytecode.
### Responsibilities
- Execute bytecode
- Memory Management
- Garbage Collection
- Class Loading
### Interview Question
**Q:** Why is Java Platform Independent?
**Answer:**
Java source code is compiled into Bytecode (.class), and JVM converts Bytecode into machine code depending on the operating system.
---
## JDK vs JRE vs JVM
| JDK | JRE | JVM |
|------|------|------|
| Development Kit | Runtime Environment | Executes Bytecode |
| Contains Compiler | Contains JVM | Runs Java Program |
---
# 2. OOPS Concepts
## Class
### Definition
A class is a blueprint for creating objects.
### Example
```java
public class ConfigurationManager {
}
``
### Framework Example
``
ConfigurationManager
↓
Reads Configuration
```
---
## Object
### Definition
Object is an instance of a class.
### Example
```java
ConfigurationManager config =
ConfigurationManager.getInstance();
```
---
## Difference
| Class | Object |
|--------|---------|
| Blueprint | Instance |
| No Memory | Occupies Memory |
---
## Constructor
### Definition
Constructor initializes an object.
### Types
- Default Constructor
- Parameterized Constructor
- Private Constructor
Example
```java
private ConfigurationManager(){
}
```
### Why Private?
To prevent
```java
new ConfigurationManager();
```
This is required for Singleton.
---
# 3. Access Modifiers
| Modifier | Access |
|-----------|---------|
| public | Everywhere |
| protected | Package + Child |
| default | Package Only |
| private | Same Class |
Example
```java
private static ConfigurationManager instance;
```
Only ConfigurationManager can access it.
---
# 4. static Keyword
## Definition
Static belongs to the class, not to an object.
### Example
```java
private static ConfigurationManager instance;
```
### Why Static?
Because we need only ONE object.
Without static
```
Object1 → instance

Object2 → instance

Object3 → instance
```
With static
```
ConfigurationManager
↓
instance
↓
One Object
```
### Interview Question
Why is Singleton object static?
**Answer**
Because only one copy should exist for the entire application.
---
# 5. final Keyword
## Definition
final prevents modification.
### Final Variable
```java
final int age = 30;
```
Cannot change.
---
### Final Method
Cannot Override.
---
### Final Class
```java
public final class ConfigurationManager {
}
```
Cannot inherit.
### Why Final?
Nobody should extend ConfigurationManager because it is a Singleton.
---
# 6. Singleton Design Pattern
## Definition
Singleton ensures only one object exists in the application.
### Current Implementation
```java
private static ConfigurationManager instance;

private ConfigurationManager() {

}

public static ConfigurationManager getInstance() {

    if(instance == null){
        instance = new ConfigurationManager();
    }

    return instance;
}
```
---
## Why Singleton?
Suppose
1000 Tests
Without Singleton
```
1000 Objects
```
With Singleton
```
1000 Tests

↓

1 ConfigurationManager

↓

Shared Configuration
``
### Advantages
- Less Memory
- Better Performance
- Centralized Configuration
### Disadvantages
Current implementation is NOT Thread Safe.
Later we'll improve it.
---
### Framework Usage

- ConfigurationManager
- DriverManager
- Logger
- ReportManager
---
### Interview Questions
Q. Why Singleton?
A. To create only one object and provide a global access point.
---
Q. Why private constructor?
A. To stop object creation using new keyword.
---
Q. Why static object?

A. Because the object belongs to the class.
---
Q. What is Lazy Initialization?

A. Object is created only when required.
---
# 7. Properties Class
Package
```java
java.util.Properties
```
Purpose
Read configuration files.
Example
config.properties

```properties
browser=chrome
```
Java
```java
properties.getProperty("browser");
``
Output
```
chrome
```
---

# 8. Framework Concepts Learned
## Parent POM
Central place for
- Dependency Versions
- Plugin Versions
- Java Version
---
## Child POM

Contains module-specific dependencies.

Example

```
automation-common

automation-config

automation-driver
```

---
## Multi Module Project
```
AI Selenium Framework

│

├── automation-common

├── automation-config

├── automation-driver

├── automation-core

├── automation-web
```
Advantages
- Reusable
- Easy Maintenance
- Independent Modules
---

# 9. Git Commands

```bash
git init
git status
git add .
git commit -m "message"
git push
git pull
git branch
```

---

# 10. Best Practices Learned

✅ Never hardcode values.

❌ Wrong

```java
driver.get("https://google.com");
```

✅ Correct

```java
driver.get(config.getProperty("base.url"));
```

---

✅ Never create multiple ConfigurationManager objects.

---

✅ Keep one responsibility per class.

---

✅ Always use meaningful package names.

---

✅ Build should always pass before commit.

---

# 11. Classes Built Till Now

```
FrameworkConstants

BrowserType

FrameworkException

ConfigurationManager
```

---

# 12. Interview Questions Covered

- What is Java?
- JVM vs JDK vs JRE
- What is a Class?
- What is an Object?
- Difference between Class and Object
- What is Constructor?
- Why Private Constructor?
- What is static?
- What is final?
- Why final class?
- What is Singleton?
- Why Singleton?
- What is Lazy Initialization?
- What is Properties class?
- What is Parent POM?
- What is Child POM?
- Why Multi Module Framework?

---

# Remember

> **Good Automation Engineers write Selenium scripts.**

> **Automation Architects design frameworks that other engineers use.**

Our goal is to become an **Automation Architect**, not just an Automation Engineer.

# Sprint 06 - Configuration Manager (Property Loading)
## Java Concepts Learned
### 1. Private Method
Definition
A private method is accessible only within the same class.
Why use it?
- Hide implementation details.
- Prevent misuse by other classes.
- Improve encapsulation.
Framework Example
private void loadProperties() {
}
Interview Question
Q. Why should helper methods be private?
Answer
Helper methods are internal implementation details and should not be exposed outside the class.
-----------------------------------
### 2. ClassLoader
Definition
ClassLoader loads resources from the application's classpath.
Why use it?
- Platform independent
- No hardcoded file paths
- Works on Windows, Linux, Docker, Jenkins
Framework Example
getClass().getClassLoader().getResourceAsStream("config.properties");
Interview Question
Q. Why use ClassLoader instead of FileInputStream?
Answer
Because ClassLoader loads resources from the classpath, making the application portable across different environments.
-----------------------------------
### 3. InputStream
Definition
InputStream reads bytes from files or resources.
Framework Example
InputStream inputStream = ...
Interview Question
Q. What is InputStream?
Answer
InputStream is the superclass used to read bytes from different input sources like files, network streams, and classpath resources.
-----------------------------------
### Design Pattern Used
Singleton Pattern
Purpose
Ensure only one ConfigurationManager object exists.
-----------------------------------
### SOLID Principle
Single Responsibility Principle
ConfigurationManager only manages configuration.
-----------------------------------
### Best Practices
✔ Never hardcode file paths.
✔ Use ClassLoader for resources.
✔ Keep helper methods private.
✔ Constructor should initialize the object only.
-----------------------------------
### Common Mistakes
❌ Using FileInputStream with absolute paths.
❌ Making helper methods public.
❌ Hardcoding configuration values.
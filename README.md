> [![Built with Claude](https://img.shields.io/badge/Built%20with-Claude%20Code-CC785C?style=flat&logo=anthropic&logoColor=white)](https://claude.ai/code)
> _This project was built with the assistance of [Claude Code](https://claude.ai/code)._

# Automation Exercise — UI Test Automation Framework

A professional-grade Selenium 4 + TestNG + Allure test automation framework targeting [automationexercise.com](https://automationexercise.com), built following **Page Object Model (POM)**, **Component Object Pattern**, and industry best practices (**KISS**, **YAGNI**, **DRY**).

---

## Table of Contents

- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Architecture Overview](#architecture-overview)
- [Design Decisions](#design-decisions)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Running Tests](#running-tests)
- [Allure Report](#allure-report)
- [Configuration](#configuration)
- [Test Cases Covered](#test-cases-covered)
- [Framework Components](#framework-components)

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 11+ | Programming language |
| Selenium WebDriver | 4.41.0 | Browser automation |
| TestNG | 7.9.0 | Test framework & suite management |
| Allure | 2.25.0 | Test reporting |
| Datafaker | 2.5.4 | Test data generation |
| Logback | 1.5.32 | Structured logging |
| AspectJ | 1.9.21 | Allure `@Step` annotation processing |
| Maven | 3.x | Build & dependency management |

> **Note:** Selenium 4.6+ includes **Selenium Manager** built-in — no WebDriverManager dependency needed. Driver binaries are resolved automatically.

---

## Project Structure

```
ae-automation-framework/
│
├── config/
│   └── config.properties               # Browser, URL, timeout configuration
│
├── src/
│   ├── main/java/com/automationexercise/
│   │   ├── App.java                    # Application entry point
│   │   ├── config/
│   │   │   └── ConfigManager.java      # Centralized config with system property override
│   │   ├── driver/
│   │   │   └── DriverManager.java      # ThreadLocal WebDriver lifecycle management
│   │   └── pages/
│   │       ├── BasePage.java           # Base class for all Page Objects
│   │       ├── HomePage.java
│   │       ├── AuthPage.java
│   │       ├── RegisterPage.java
│   │       ├── AccountCreatedPage.java
│   │       ├── AccountDeletedPage.java
│   │       ├── ProductsPage.java
│   │       ├── ProductDetailPage.java
│   │       ├── ProductListPage.java    # Unified page for Category + Brand listings
│   │       ├── CartPage.java
│   │       ├── CheckoutPage.java
│   │       ├── PaymentPage.java
│   │       ├── ContactUsPage.java
│   │       ├── TestCasesPage.java
│   │       └── components/
│   │           ├── HeaderComponent.java
│   │           ├── FooterComponent.java
│   │           ├── SidebarComponent.java       # Category + Brand sidebar
│   │           ├── CartModalComponent.java
│   │           └── CheckoutModalComponent.java
│   │
│   └── test/java/com/automationexercise/
│       ├── base/
│       │   └── BaseTest.java           # TestNG lifecycle + Allure screenshot on failure
│       ├── steps/
│       │   └── AccountSteps.java       # Reusable business-level flows
│       └── tests/
│           ├── RegisterUserTest.java
│           ├── LoginUserTest.java
│           ├── LogoutUserTest.java
│           ├── ContactUsTest.java
│           ├── TestCasesTest.java
│           ├── ProductsTest.java
│           ├── SubscriptionTest.java
│           ├── CartTest.java
│           ├── PlaceOrderTest.java
│           ├── CategoryTest.java
│           ├── BrandTest.java
│           ├── SearchCartTest.java
│           ├── ProductReviewTest.java
│           ├── RecommendedItemsTest.java
│           ├── CheckoutAddressTest.java
│           ├── InvoiceTest.java
│           └── ScrollTest.java
│
├── src/test/resources/
│   ├── testng.xml                      # TestNG suite configuration
│   ├── allure.properties               # Allure results directory
│   ├── logback.xml                     # Logging configuration
│   └── testdata/
│       └── test-upload.txt             # File used for upload test (TC06)
│
└── pom.xml
```

---

## Architecture Overview

```
┌─────────────────────────────────────────────────┐
│                   Test Layer                    │
│  RegisterUserTest, LoginUserTest, CartTest...   │
│  (extends BaseTest — TestNG lifecycle)          │
└────────────────────┬────────────────────────────┘
                     │ uses
┌────────────────────▼────────────────────────────┐
│                  Steps Layer                    │
│  AccountSteps — reusable business flows         │
│  (preconditions for tests needing existing data)│
└────────────────────┬────────────────────────────┘
                     │ uses
┌────────────────────▼────────────────────────────┐
│                  Page Layer                     │
│  Page Objects + Component Objects               │
│  (extends BasePage — Selenium interactions)     │
└────────────────────┬────────────────────────────┘
                     │ uses
┌────────────────────▼────────────────────────────┐
│               Framework Layer                   │
│  App → DriverManager → ConfigManager            │
│  (entry point, WebDriver, configuration)        │
└─────────────────────────────────────────────────┘
```

### Navigation Flow

```
BaseTest.setUp()
    └── DriverManager.initDriver()     # Browser opens
    └── app = new App()                # Entry point ready

Test Method
    └── app.open()                     # Navigate to URL → return HomePage
    └── homePage.header().clickXxx()   # HeaderComponent handles navigation → returns Page
    └── page.someAction()              # Page Object interactions
    └── Assert.xxx(...)                # Assertions
```

---

## Design Decisions

### No PageFactory
Direct `By` locators used instead of `@FindBy` — eliminates `StaleElementReferenceException` from element caching and supports dynamic locators.

```java
// ❌ PageFactory — cached, stale-prone
@FindBy(css = ".product-name")
private WebElement productName;

// ✅ Direct By locator — always fresh
private static final By PRODUCT_NAME = By.cssSelector(".product-name");
```

### Component Object Pattern
Shared UI elements (`HeaderComponent`, `FooterComponent`, `SidebarComponent`) are extracted as reusable components — each Page exposes them via fresh getter methods:

```java
homePage.header().clickSignupLogin();   // HeaderComponent owns navigation
homePage.footer().subscribeWithEmail(); // FooterComponent owns footer interactions
homePage.sidebar().clickCategory();     // SidebarComponent owns sidebar navigation
```

### One-Way Dependency
`HeaderComponent` returns Page Objects after navigation — Pages do **not** import `HeaderComponent`, eliminating circular dependency:

```
HeaderComponent → HomePage, AuthPage, CartPage...
HomePage        → (does NOT import HeaderComponent)
```

### ThreadLocal WebDriver
`DriverManager` uses `ThreadLocal<WebDriver>` — each test thread gets an isolated driver instance, enabling safe parallel execution.

### Dynamic Locators with Varargs
`buildLocator()` supports any number of `%s` placeholders via varargs:

```java
// 1 placeholder
buildLocator("//a[@href='#%s']", "Women")

// 2 placeholders
buildLocator("//div[@id='%s']//a[contains(text(),'%s')]", "Women", "Dress")
```

---

## Prerequisites

- **Java 11+** — `java -version`
- **Maven 3.x** — `mvn -version`
- **Chrome browser** installed (Selenium Manager auto-downloads chromedriver)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/your-username/ae-automation-framework.git
cd ae-automation-framework
```

### 2. Install dependencies

```bash
mvn clean install -DskipTests
```

### 3. Verify setup

```bash
mvn clean test -Dtest=TestCasesTest
```

---

## Running Tests

### Run all tests

```bash
mvn clean test
```

### Run a specific test class

```bash
mvn clean test -Dtest=RegisterUserTest
mvn clean test -Dtest=CartTest
```

### Run a specific test method

```bash
mvn clean test -Dtest=LoginUserTest#testLoginWithCorrectCredentials
```

### Run with different browser

```bash
mvn clean test -Dbrowser=firefox
mvn clean test -Dbrowser=edge
```

### Run in headless mode (for CI/CD)

```bash
mvn clean test -Dheadless=true
```

### Run with multiple overrides

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true
```

---

## Allure Report

### Generate and open report

```bash
# Run tests first
mvn clean test

# Serve report in browser
mvn allure:serve
```

### Generate static report

```bash
mvn allure:report
# Report available at: target/site/allure-maven-plugin/index.html
```

### Report features

- **Epic / Feature / Story** grouping for all 26 test cases
- **Step-by-step** breakdown via `@Step` annotations on Page Object methods
- **Screenshot on failure** — automatically attached to failed tests
- **Severity levels** — BLOCKER, CRITICAL, NORMAL per test case

---

## Configuration

All configuration is managed in `config/config.properties`:

```properties
# Browser: chrome | firefox | edge
browser=chrome

# Headless mode: true for CI/CD pipelines
headless=false

# Application base URL
base.url=https://automationexercise.com

# Explicit wait timeout (seconds)
explicit.wait=10

# Page load timeout (seconds)
page.load.timeout=30

# Implicit wait — keep at 0 to avoid conflicts with explicit wait
implicit.wait=0
```

### System property override (CI/CD)

Any config value can be overridden via Maven system properties:

```bash
mvn clean test -Dbrowser=firefox -Dheadless=true -Dexplicit.wait=15
```

---

## Test Cases Covered

| TC | Test Case | Feature | Status |
|---|---|---|---|
| TC01 | Register User | Register | ✅ |
| TC02 | Login User with correct credentials | Login | ✅ |
| TC03 | Login User with incorrect credentials | Login | ✅ |
| TC04 | Logout User | Logout | ✅ |
| TC05 | Register User with existing email | Register | ✅ |
| TC06 | Contact Us Form | Contact | ✅ |
| TC07 | Verify Test Cases Page | Navigation | ✅ |
| TC08 | Verify All Products and product detail | Products | ✅ |
| TC09 | Search Product | Products | ✅ |
| TC10 | Verify Subscription in home page | Subscription | ✅ |
| TC11 | Verify Subscription in Cart page | Subscription | ✅ |
| TC12 | Add Products in Cart | Cart | ✅ |
| TC13 | Verify Product quantity in Cart | Cart | ✅ |
| TC14 | Place Order: Register while Checkout | Orders | ✅ |
| TC15 | Place Order: Register before Checkout | Orders | ✅ |
| TC16 | Place Order: Login before Checkout | Orders | ✅ |
| TC17 | Remove Products From Cart | Cart | ✅ |
| TC18 | View Category Products | Category | ✅ |
| TC19 | View & Cart Brand Products | Brand | ✅ |
| TC20 | Search Products and Verify Cart After Login | Cart | ✅ |
| TC21 | Add review on product | Review | ✅ |
| TC22 | Add to cart from Recommended items | Cart | ✅ |
| TC23 | Verify address details in checkout page | Checkout | ✅ |
| TC24 | Download Invoice after purchase order | Invoice | ✅ |
| TC25 | Scroll Up using Arrow button | UI | ✅ |
| TC26 | Scroll Up without Arrow button | UI | ✅ |

**Total: 26/26 PASSED ✅**

---

## Framework Components

### `BasePage`
Abstract base class for all Page Objects. Provides:
- Explicit wait helpers: `waitForVisible()`, `waitForClickable()`, `waitForPresence()`
- Core interactions: `click()`, `type()`, `getText()`, `isDisplayed()`, `isPresent()`
- Dynamic locator builder: `buildLocator(template, String... values)`, `buildLocator(template, int index)`
- JavaScript utilities: `scrollToBottom()`, `scrollToTop()`, `jsClick()`, `scrollToElement()`
- List utilities: `findElements()`, `findElementsNoWait()`, `countElements()`
- File upload: `uploadFile()`
- Alert handling: `acceptAlert()`
- Hover: `hoverOverElement()`

### `DriverManager`
Thread-safe WebDriver lifecycle management:
- `ThreadLocal<WebDriver>` — isolated driver per thread for parallel execution
- Supports Chrome, Firefox, Edge with headless mode
- Selenium Manager handles driver binary resolution automatically
- Configures download directory for file download tests

### `ConfigManager`
Centralized configuration with priority:
1. System properties (`-Dbrowser=firefox`) — highest priority
2. `config/config.properties` — default values

### `App`
Application entry point — ensures browser is open before any Page Object is created:
```java
HomePage homePage = app.open(); // navigate to base URL → return HomePage
```

### `BaseTest`
Abstract base for all test classes:
- `@BeforeMethod`: initializes driver + `app` instance
- `@AfterMethod`: captures screenshot on failure → quits driver
- `waitForFileDownload()`: polls download directory for file existence

### `AccountSteps`
Reusable business-level flows for test preconditions:
- `registerUser(...)`: full registration flow → returns logged-in `HomePage`
- `registerUser(name, email, password)`: minimal overload with default address data
- `loginUser(email, password)`: login flow → returns logged-in `HomePage`

---

## Logging

Logs are written to both console and rolling file:

```
target/logs/test-run.log          # Current run
target/logs/test-run.yyyy-MM-dd.log  # Historical (7 days retention)
```

Log levels:
- `DEBUG` — element interactions (click, type, hover)
- `INFO` — page actions and navigation
- `WARN` — test failures, download timeouts
- `ERROR` — driver initialization failures, screenshot errors
# REST Assured Enterprise API Test Automation Framework

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![REST Assured](https://img.shields.io/badge/REST%20Assured-5.5.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.12.0-red)
![Maven](https://img.shields.io/badge/Maven-Build-blue)

A modular, data-driven REST API test automation framework built using **Java 17, REST Assured, and TestNG**. Designed following enterprise API engineering patterns—including centralized `RequestSpecification`/`ResponseSpecification` builders, externalized secret management, multi-part file uploads, and end-to-end CRUD request chaining—to validate **Google Maps Place APIs, Library Management APIs, Nested JSON Course Calculations, and Jira Cloud REST APIs**.

---

## Key Architectural Highlights

* **Centralized `SpecBuilder` Pattern:** Uses `RequestSpecBuilder` and `ResponseSpecBuilder` (`base.SpecBuilder`) to standardize Base URIs, preemptive Basic Authentication, `Content-Type` headers, HTTP status assertions, and request/response logging across all test suites.
* **End-to-End Request Chaining:** Dynamically extracts runtime identifiers (`place_id`, Jira `id`) via `JsonPath` and chains dependent API calls (`POST` $\rightarrow$ `PUT` $\rightarrow$ `GET` and Jira Bug Creation $\rightarrow$ Multipart Screenshot Attachment).
* **Data-Driven & Self-Cleaning Tests:** Leverages TestNG `@DataProvider` (`LibraryApiTest`) to validate multiple book payloads dynamically, paired with automated `POST /Library/DeleteBook.php` teardown steps to keep test environments clean and repeatable.
* **Complex Nested JSON Parsing:** Validates dynamic array sizes, conditional element lookups, and calculated business logic totals across nested JSON structures (`CourseValidationTest`).
* **Enterprise Credential Security:** Separates environment URLs and API tokens into an ignored `config.properties` file with CLI/Environment variable override support (`Configreader`) so sensitive tokens are never exposed in source control.

---

## Tech Stack

| Layer | Technology |
| :--- | :--- |
| **Language** | Java 17 |
| **API Automation & JSON Parsing** | REST Assured 5.5.0 (`JsonPath` & Hamcrest Matchers) |
| **Test Runner & Data-Driven Engine** | TestNG 7.12.0 (`@Test`, `@DataProvider`, `dependsOnMethods`) |
| **Build & Execution** | Apache Maven (`maven-surefire-plugin`) |

---

## Project Directory Structure

```text
Demo_Api_Testing/
├── src/
│   ├── main/java/
│   │   ├── base/
│   │   │   └── SpecBuilder.java              # Centralized Request & Response Specifications (Place, Library, Jira)
│   │   ├── payloads/
│   │   │   └── Payloads.java                 # Parameterized JSON request payload builders
│   │   └── utilities/
│   │       ├── Configreader.java             # Environment properties & CLI/Env secret loader
│   │       └── ReusableMethods.java          # JsonPath parsers & dynamic test data helpers
│   └── test/
│       ├── java/
│       │   └── tests/
│       │       ├── PlaceApiTests.java        # E2E Place API CRUD chaining (Add -> Update -> Get)
│       │       ├── LibraryApiTest.java       # Data-driven Add & Delete Book API validation (@DataProvider)
│       │       ├── CourseValidationTest.java # Nested JSON array parsing & purchaseAmount verification
│       │       └── BugTestAndAttach.java     # Jira Cloud Issue creation & multipart screenshot attachment
│       └── resources/
│           ├── config/
│           │   └── config.properties         # Environment URIs & API credentials (git-ignored)
│           └── bug_screenshot.png            # Sample attachment payload for Jira multipart upload tests
├── testng.xml                                # Master TestNG regression suite configuration
├── pom.xml                                   # Maven dependencies & Surefire execution plugin
└── README.md# Demo_Api_Testing

Prerequisites
JDK 17 or higher configured in JAVA_HOME

Apache Maven 3.8+ configured in PATH

Valid Jira Cloud API Token (for running BugTestAndAttach)

Getting Started & Execution
1. Clone the Repository
Bash
git clone[https://github.com/bhairappa/Demo_Api_Testing](https://github.com/bhairappa/Demo_Api_Testing)
cd Demo_Api_Testing
2. Configure Environment (src/test/resources/config/config.properties)
Create src/test/resources/config/config.properties locally (excluded from Git via .gitignore) and populate your environment values:

Properties
place.baseUri=[https://rahulshettyacademy.com](https://rahulshettyacademy.com)
library.baseUri=[http://216.10.245.166](http://216.10.245.166)
jira.baseUri=https://<your-domain>.atlassian.net

place.apiKey=qaclick123
jira.email=<your-atlassian-email>
jira.apiToken=<your-jira-api-token>
jira.projectKey=<your-project-key>
3. Run API Suites via Maven CLI
Run the full TestNG API regression suite (testng.xml):

Bash
mvn clean test
Run a single test class:

Bash
mvn clean test -Dtest=LibraryApiTest
mvn clean test -Dtest=PlaceApiTests
Pass secrets dynamically in CI/CD (without config.properties):

Bash
mvn clean test -Djira.email="user@example.com" -Djira.apiToken="YOUR_TOKEN"
Test Reports
TestNG HTML Report: Generated after execution at test-output/index.html and target/surefire-reports/index.html.

Request/Response Logs: Full HTTP headers, payloads, and status assertions printed to console via LogDetail.ALL.

Future Roadmap
[ ] Migrate raw string JSON payloads to POJO Serialization/Deserialization using Jackson (ObjectMapper).

[ ] Add JSON Schema Validation (rest-assured-json-schema-validator) for contract testing.

[ ] Integrate Allure Reports (allure-rest-assured) for interactive HTTP request/response attachment reporting.


---
Here is a complete, professional README tailored exactly to the architecture and features you just built. It highlights the advanced multithreading and raw SQL work you did, which will look fantastic to anyone reviewing your GitHub profile!

You can copy and paste this directly into a `README.md` file in the root of your project.

---

# 🐕 WatchDog - Asynchronous Website Health Monitor

WatchDog is a high-performance Spring Boot REST API designed to monitor website uptime and latency. It steps beyond basic CRUD by utilizing Java's multithreading capabilities (`@Async`, `CompletableFuture`) for concurrent network requests, strict connection timeouts, and raw PostgreSQL queries for precise data management.

## 🚀 Key Features

* **Concurrent Bulk Pinging:** Ping multiple websites simultaneously. A list of 50 URLs takes only as long as the slowest website to respond (max 10 seconds), rather than waiting for each to finish sequentially.
* **Strict Timeouts & Resilience:** Custom `RestTemplate` configuration ensures no network request hangs for more than 10 seconds, returning graceful `504 DOWN (TIMEOUT)` responses for blackholed servers.
* **Automated Background Monitoring:** Built-in `@Scheduled` tasks wake up periodically to check the health of all saved websites in the database.
* **Smart Database Management:** Uses raw SQL via `JdbcTemplate` to bypass ORM overhead. It handles complex conditional inserts and automatically cleans up old health check logs to keep the database lightweight.
* **Dockerized Database:** Uses `docker-compose` for instant PostgreSQL database provisioning.

## 🛠️ Tech Stack

* **Backend:** Java, Spring Boot (Web, JDBC)
* **Database:** PostgreSQL
* **Infrastructure:** Docker & Docker Compose
* **Core Concepts:** Multithreading (`CompletableFuture`), Scheduled Tasks, Raw SQL querying.

## ⚙️ Getting Started

### Prerequisites

* Java 17 or higher
* Docker Desktop (for running the database)
* Maven

### Installation

1. **Clone the repository:**
```bash
git clone https://github.com/yourusername/watchDog.git
cd watchDog

```


2. **Start the PostgreSQL Database:**
Make sure Docker is running, then spin up the database using the provided compose file:
```bash
docker-compose up -d

```


3. **Run the Application:**
Start the Spring Boot server:
```bash
mvn spring-boot:run

```


*The application will start on `http://localhost:8080`.*

## 📡 API Reference

Here are the primary endpoints available in the application. All endpoints are prefixed with `/api`.

### 1. Ad-Hoc Ping & Save

Pings a single website, records the response time and status code (200, 500, 504) into the database, and automatically deletes outdated logs for that specific site.

* **URL:** `/api/pingAndSave`
* **Method:** `POST`
* **Body (JSON):**
```json
{
    "url": "https://www.google.com"
}

```



### 2. Concurrent Bulk Ping

Pings a list of websites entirely in parallel without saving to the database. Excellent for quick, massive health checks. Use a `+` sign to separate URLs in the parameter.

* **URL:** `/api/multiple?urls={url1}+{url2}+{url3}`
* **Method:** `GET`
* **Example:** `/api/multiple?urls=https://www.google.com+https://www.spring.io+http://10.255.255.1`

### 3. Single Ping Check

Pings a single website instantly without saving to the database.

* **URL:** `/api/single?url={url}`
* **Method:** `GET`

### 4. Fetch All Reports

Retrieves the full list of websites and their latest health check logs from the database.

* **URL:** `/api/reports`
* **Method:** `GET`

### 5. Trigger Instant Automated Check

Forces the background scheduler to run immediately, pinging all active websites in the database and updating their status logs.

* **URL:** `/api/instant`
* **Method:** `GET`

---

**Would you like me to help you write a quick `.gitignore` file so you don't accidentally push your compiled files or IDE settings to GitHub, or are you ready to commit and push this right now?**

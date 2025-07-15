
# ELK Stack Interview Questions and Answers

## ✅ Beginner-Level ELK Questions

### ❓ What is ELK Stack and what does each component do?

ELK stands for **Elasticsearch**, **Logstash**, and **Kibana** — open-source tools used for **centralized logging, search, and visualization**.

- **Elasticsearch**: A distributed search and analytics engine used to store, search, and analyze large volumes of data.
- **Logstash**: A data processing pipeline that ingests, transforms, and sends logs or events to Elasticsearch.
- **Kibana**: A visualization UI that connects to Elasticsearch to create dashboards and query logs.

---

### ❓ What is the difference between Elasticsearch and Logstash?

- **Elasticsearch** stores, indexes, and provides fast search capabilities on data.
- **Logstash** is responsible for collecting, parsing, and sending the data to Elasticsearch.

---

### ❓ What type of data can Elasticsearch store and search?

Elasticsearch natively stores data in **JSON format**. It can index and search:
- Structured data (key-value pairs)
- Semi-structured data (JSON, XML)
- Unstructured data (text, logs)

---

### ❓ How does Logstash process data? What are filters?

Logstash processes data through a **pipeline** consisting of three stages:

- **Input**: Receives data from sources (e.g., file, TCP, beats)
- **Filter**: Applies transformations using filters like `grok`, `mutate`, `date`, `json`
- **Output**: Sends data to destinations (e.g., Elasticsearch, file, stdout)

Filters are used to clean, transform, or parse logs before indexing.

---

### ❓ What is Kibana used for?

Kibana is a **UI tool** used to:
- Create dashboards and visualizations
- Search and explore log data stored in Elasticsearch
- Monitor and alert (via plugins or Stack features)

---

## ⚙️ Hands-On / Practical Questions

### ⚙️ How do you send logs from a Spring Boot or Node.js app to ELK?

**Spring Boot:**
- Add `logstash-logback-encoder` dependency.
- Configure `logback-spring.xml` to use a **Logstash TCP appender** with the Logstash host and port.

**Node.js:**
- Use libraries like `winston-logstash` or `bunyan` to forward logs via TCP or HTTP.

---

### ⚙️ How do you define a Logstash pipeline? What are input, filter, and output blocks?

A Logstash pipeline is defined in a `.conf` file using:

- **input**: Defines the data source (`file`, `tcp`, `beats`, etc.)
- **filter**: Parses and enriches logs using plugins like `grok`, `mutate`, `json`, etc.
- **output**: Sends the processed logs to Elasticsearch or another destination.

---

### ⚙️ What is a Logstash grok filter? Give an example.

A **grok** filter is used to extract fields from unstructured log data using regex-like patterns.

**Example:**
```logstash
filter {
  grok {
    match => { "message" => "%{TIMESTAMP_ISO8601:timestamp} \[%{LOGLEVEL:level}\] %{GREEDYDATA:msg}" }
  }
}
```

### ⚙️ How do you create a dashboard in Kibana?

1. Go to **Stack Management → Data Views** and create a view (e.g., `logstash-*`).
2. Go to **Dashboard**, click **Create**, and add visualizations using **Discover**, **Lens**, or **Visualize**.
3. Save and arrange the dashboard as needed.

---

### ⚙️ How can you filter logs in Kibana for a specific error level?

Use **KQL (Kibana Query Language)** or **Lucene syntax** in the search bar.

#### 🔹 KQL:
```kql
log_level: "ERROR"
```

---

## 🧠 Intermediate to Advanced ELK Questions

### 🧠 What is the difference between a full-text search and keyword search in Elasticsearch?

- **Full-text search** is performed on `text` fields. The content is **analyzed** (tokenized, lowercased, etc.).  
  Best for searching within large text blocks like log messages or descriptions.

- **Keyword search** is performed on `keyword` fields (non-analyzed).  
  It performs **exact matches**, useful for structured data like log levels, IDs, hostnames, etc.

---

### 🧠 How do you manage data retention in Elasticsearch (e.g., delete logs older than 30 days)?

Use **ILM (Index Lifecycle Management)** in Elasticsearch to:

- Automatically **delete old indices** (e.g., older than 30 days)
- **Rollover** to new indices after reaching a size/time threshold
- **Move** indices to colder tiers (e.g., cold/frozen) before deletion to save resources

---

### 🧠 What is an Elasticsearch index? What is a mapping?

- **Index**: A collection of documents, similar to a database table. Every log or event is stored as a document inside an index.

- **Mapping**: Defines how fields in the documents are stored and indexed in Elasticsearch. It includes:
  - Data types (`text`, `keyword`, `date`, `integer`, etc.)
  - Field analyzers
  - Format definitions

---

### 🧠 How do you secure Elasticsearch and Kibana using authentication (e.g., x-pack)?

1. **Enable security** in `elasticsearch.yml`:
   ```yaml
   xpack.security.enabled: true
   ```

2. **Set passwords for built-in users**:
   ```bash
   ./bin/elasticsearch-setup-passwords interactive
   ```

3. **Configure Kibana authentication** in `kibana.yml`:
   ```yaml
   elasticsearch.username: "kibana_system"
   elasticsearch.password: "your_password"
   ```

4. **Configure Logstash authentication** in the `output` block:
   ```logstash
   output {
     elasticsearch {
       hosts => ["http://localhost:9200"]
       user => "logstash_user"
       password => "your_password"
     }
   }
   ```

# ELK-Stack

# ELK Stack Setup with Spring Boot 

## **1. Install and Start Elasticsearch and Kibana**

### **Install Elasticsearch**
1. Download Elasticsearch from [Elastic's official website](https://www.elastic.co/downloads/elasticsearch)
2. Extract the downloaded archive:
   ```sh
   tar -xzf elasticsearch-8.5.3-linux-x86_64.tar.gz
   ```
3. Navigate to the Elasticsearch directory:
   ```sh
   cd elasticsearch-8.5.3
   ```
4. Start Elasticsearch:
   ```sh
   ./bin/elasticsearch
   ```

### **Install Kibana**
1. Download Kibana from [Elastic's official website](https://www.elastic.co/downloads/kibana)
2. Extract the downloaded archive:
   ```sh
   tar -xzf kibana-8.5.3-linux-x86_64.tar.gz
   ```
3. Navigate to the Kibana directory:
   ```sh
   cd kibana-8.5.3
   ```
4. Start Kibana:
   ```sh
   ./bin/kibana
   ```

Access **Kibana** at: [http://localhost:5601](http://localhost:5601)

## **2. Configure Logstash to Receive Logs**

Create a `logstash.conf` file:

```plaintext
input {
  tcp {
    port => 5000
    codec => json
  }
}

filter {
  mutate {
    remove_field => ["host"]
  }
}

output {
  elasticsearch {
    hosts => ["http://localhost:9200"]
    index => "spring-boot-logs"
  }
  stdout { codec => rubydebug }
}
```

Start **Logstash**:
```sh
./bin/logstash -f logstash.conf
```

## **3. Configure Spring Boot Logging to Send Logs to Logstash**

### **Update `logback-spring.xml`**

```xml
<configuration>
    <appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>localhost:5000</destination>
        <encoder class="net.logstash.logback.encoder.LogstashEncoder"/>
    </appender>

    <root level="info">
        <appender-ref ref="LOGSTASH"/>
    </root>
</configuration>
```

### **Add Logstash Dependency in `pom.xml`**
```xml
<dependency>
    <groupId>net.logstash.logback</groupId>
    <artifactId>logstash-logback-encoder</artifactId>
    <version>7.4</version>
</dependency>
```

## **4. Define API Endpoints in Spring Boot**

Create a `ProductController.java`:

```java
@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/name/{name}")
    public List<Product> searchByName(@PathVariable String name) {
        return productService.searchByName(name);
    }

    @GetMapping("/description/{keyword}")
    public List<Product> searchByDescription(@PathVariable String keyword) {
        return productService.searchByDescription(keyword);
    }

    @GetMapping
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
```

## **5. Test API Endpoints using cURL**

### **Add a Product**
```sh
curl -X POST "http://localhost:8080/products" \
     -H "Content-Type: application/json" \
     -d '{
           "name": "Laptop",
           "description": "Gaming Laptop",
           "price": 1200
         }'
```

### **Search Product by Name**
```sh
curl -X GET "http://localhost:8080/products/name/Laptop" \
     -H "Content-Type: application/json"
```

### **Search Product by Description**
```sh
curl -X GET "http://localhost:8080/products/description/Gaming" \
     -H "Content-Type: application/json"
```

### **Get All Products**
```sh
curl -X GET "http://localhost:8080/products" \
     -H "Content-Type: application/json"
```

### **Check Elasticsearch Index**
```sh
curl -X GET "http://localhost:9200/products/_search?pretty"
```

## **6. Configure Kibana to View Logs**

1. Open **Kibana** at [http://localhost:5601](http://localhost:5601)
2. Navigate to **Stack Management** → **Index Patterns**
3. Click **Create Index Pattern**
4. Enter `spring-boot-logs*` as the index pattern
5. Select `@timestamp` as the time field
6. Click **Create Index Pattern**

## **7. View API Calls in Kibana**

1. Go to **Discover**
2. Select the index **spring-boot-logs**
3. Use JSON queries in **Dev Tools** to filter API calls:

```json
GET spring-boot-logs/_search
{
  "query": {
    "match": {
      "message": "GET /products"
    }
  }
}
```

---

### ✅ **Now Your Spring Boot Application Logs Are Monitored in Kibana!** 🚀


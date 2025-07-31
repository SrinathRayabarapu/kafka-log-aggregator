# Kafka Log Aggregator

A multi-module Spring Boot project for Kafka-based log aggregation.

## Project Structure

```
kafka-log-aggregator/
├── pom.xml                 # Parent POM
├── docker-compose.yml      # Kafka & Zookeeper setup
├── log-producer/           # Log Producer Module
│   ├── pom.xml
│   └── src/
└── log-consumer/           # Log Consumer Module
    ├── pom.xml
    └── src/
```

## Modules

### 1. Log Producer (`log-producer`)
- **Purpose**: Produces log messages to Kafka topics
- **Dependencies**: Spring Boot Web, Spring Kafka
- **Port**: Typically runs on 8080

### 2. Log Consumer (`log-consumer`)
- **Purpose**: Consumes and processes log messages from Kafka topics
- **Dependencies**: Spring Boot, Spring Kafka, Kafka Streams
- **Processing**: Uses Kafka Streams for real-time log processing

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.1.0
- **Spring Kafka**: 3.0.7
- **Apache Kafka**: 3.6.1
- **Maven**: Multi-module project
- **Docker**: For Kafka and Zookeeper

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+
- Docker & Docker Compose

### 1. Start Kafka Infrastructure
```bash
docker compose up -d
```

### 2. Build All Modules
```bash
# Build all modules from the root
mvn clean compile

# Or build and package
mvn clean package
```

### 3. Run Individual Modules
```bash
# Run Producer
cd log-producer
mvn spring-boot:run

# Run Consumer (in another terminal)
cd log-consumer
mvn spring-boot:run
```

### 4. Build Specific Module
```bash
# Build only producer
mvn clean package -pl log-producer

# Build only consumer
mvn clean package -pl log-consumer
```

## Maven Commands

### Multi-module Commands
```bash
# Validate all modules
mvn validate

# Clean all modules
mvn clean

# Compile all modules
mvn compile

# Test all modules
mvn test

# Package all modules
mvn package

# Install all modules to local repository
mvn install
```

### Module-specific Commands
```bash
# Build specific modules
mvn clean package -pl log-producer,log-consumer

# Skip tests
mvn package -DskipTests

# Run with specific profile
mvn clean package -Pdev
mvn clean package -Pprod
```

## Configuration

### Profiles
- **dev**: Development profile (active by default)
- **prod**: Production profile

### Kafka Configuration
- **Zookeeper**: localhost:2181
- **Kafka Broker**: localhost:9092
- **Topics**: Configured in application.yml files

## Docker Services

The `docker-compose.yml` includes:
- **Zookeeper**: Port 2181
- **Kafka**: Port 9092

### Start Services
```bash
docker compose up -d
```

### Stop Services
```bash
docker compose down
```

### View Logs
```bash
docker compose logs kafka
docker compose logs zookeeper
```

## Development

### Adding New Dependencies
Add shared dependencies to the parent POM's `<dependencyManagement>` section, then reference them in child modules without versions.

### Creating New Modules
1. Create new module directory
2. Add module to parent POM's `<modules>` section
3. Create child POM with parent reference
4. Implement module functionality

## Troubleshooting

### Common Issues
1. **Port conflicts**: Ensure ports 2181, 8080, 9092 are available
2. **Kafka not starting**: Check Docker logs and ensure Zookeeper is running first
3. **Build failures**: Ensure Java 17+ and Maven are properly installed

### Logs
- Application logs: Check console output
- Kafka logs: `docker compose logs kafka`
- Zookeeper logs: `docker compose logs zookeeper`

### Accessing Kafka CLI
```bash
docker exec -it -w /opt/kafka/bin broker sh
./kafka-topics.sh --create --topic logs --bootstrap-server broker:29092
./kafka-topics.sh --list --bootstrap-server localhost:9092
```
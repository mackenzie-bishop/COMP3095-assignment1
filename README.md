# GBC Wellness Hub – Assignment 1 (Docker-first)

Three Spring Boot services + Redis + PostgreSQL + MongoDB, all containerized.

## Services & Ports
- wellness-resource-service (Spring Boot + JPA + Redis cache) → `:8081`
- event-service (Spring Boot + JPA) → `:8082`
- goal-tracking-service (Spring Boot + Spring Data Mongo) → `:8083`

## Quickstart
```bash
# 1) Build images (each service compiles inside its Dockerfile)
docker compose build

# 2) Run the entire stack
docker compose up -d

# 3) Verify health
curl http://localhost:8081/actuator/health
curl http://localhost:8082/actuator/health
curl http://localhost:8083/actuator/health

# 4) Try endpoints
# Wellness Resources
curl http://localhost:8081/resources
curl "http://localhost:8081/resources?category=mindfulness"
# create
curl -X POST http://localhost:8081/resources -H "Content-Type: application/json" -d '{"title":"Campus Counseling","description":"Free 1:1 sessions","category":"counseling","url":"https://example.edu"}'

# Goals
curl http://localhost:8083/goals
curl -X POST http://localhost:8083/goals -H "Content-Type: application/json" -d '{"title":"Meditate","description":"10 mins daily","category":"mindfulness"}'
curl -X PATCH http://localhost:8083/goals/ID/complete

# Events
curl http://localhost:8082/events
```

## Run tests locally (optional)
If you have JDK 17+ and Maven, you can run tests (Testcontainers will pull DB images):
```bash
cd services/wellness-resource-service && mvn -q -DskipITs=false test
cd ../event-service && mvn -q -DskipITs=false test
cd ../goal-tracking-service && mvn -q -DskipITs=false test
```

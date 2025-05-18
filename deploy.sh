#!/bin/bash

# 1. Build jar
./mvnw clean package -DskipTests

# 2. Build Docker image
docker build -t hospital -f Dockerfile .

# 3. Tag image
docker tag hospital nilendrapatel1317/hospital:latest

# 4. Push to Docker Hub
docker push nilendrapatel1317/hospital:latest

# 5. Trigger redeploy via Render Deploy Hook
curl -X POST https://api.render.com/deploy/srv-d0kepvbuibrs739fuu8g?key=6P_6Set2LI8

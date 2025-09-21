#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Create logs directory if it doesn't exist
mkdir -p logs

echo "Starting Node 1..."
mvn spring-boot:run -Dspring-boot.run.profiles=node1 > logs/node1.log 2>&1 &

echo "Starting Node 2..."
mvn spring-boot:run -Dspring-boot.run.profiles=node2 > logs/node2.log 2>&1 &

echo "Starting Node 3..."
mvn spring-boot:run -Dspring-boot.run.profiles=node3 > logs/node3.log 2>&1 &

echo "All nodes started in background. Logs available in logs/ directory."

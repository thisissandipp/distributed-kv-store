#!/bin/bash

echo "Stopping all running nodes..."

# Find all spring-boot:run processes and kill them
pkill -f 'spring-boot:run'

echo "All nodes stopped."

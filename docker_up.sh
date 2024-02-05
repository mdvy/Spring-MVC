#!/bin/bash

docker-compose down
docker  volume rm  spring-1_mysql-volume
docker-compose up -d
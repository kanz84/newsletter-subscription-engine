#!/bin/sh

project_name=newsletter-subscription
echo "$project_name is deploying..."

current_time=$(date "+%Y%m%d_%H%M%S")
backup_dir=$HOME/backups/$project_name/backup_$current_time/

mkdir -p "$HOME/artifacts/$project_name/app_files"
mkdir -p "$backup_dir"

docker-compose down && docker system prune -f && docker network prune -f
echo "Old docker containers removed"

tar -pczf "$backup_dir/project.tar.gz" --exclude .git --exclude target .
echo "Project backed up to $backup_dir"

./mvnw clean package -Dmaven.test.skip=true dockerfile:build -f newsletter-subscription
docker-compose up -d --build
echo "Docker image built"

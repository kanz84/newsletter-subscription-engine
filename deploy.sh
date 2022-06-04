#!/bin/sh

project_name=newsletter-subscription
echo "$project_name is deploying..."

mkdir -p "$HOME/tmp/jdk"
JDK="$HOME/tmp/jdk/jdk-11"
if [ ! -d "$JDK" ]; then
    wget -c -O $HOME/tmp/jdk/openjdk-11+28_linux-x64_bin.tar.gz https://download.java.net/openjdk/jdk11/ri/openjdk-11+28_linux-x64_bin.tar.gz
    echo "JDK downloaded"
    tar -xf $HOME/tmp/jdk/openjdk-11+28_linux-x64_bin.tar.gz -C $HOME/tmp/jdk/
    echo "JDK extracted"
else
    echo "JDK already existed in $JDK"
fi

current_time=$(date "+%Y%m%d_%H%M%S")
backup_dir=$HOME/backups/$project_name/backup_$current_time/

mkdir -p "$HOME/artifacts/$project_name/app_files"
mkdir -p "$HOME/artifacts/$project_name/postgres/pgdata"
mkdir -p "$backup_dir"

docker-compose down && docker system prune -f && docker network prune -f
echo "Old docker containers removed"

tar -pczf "$backup_dir/project.tar.gz" --exclude .git --exclude target .
echo "Project backed up to $backup_dir"

#The following line is required for first installation.
JAVA_HOME="$JDK"  ./mvnw clean package install -Dmaven.test.skip=true
JAVA_HOME="$JDK"  ./mvnw clean package install -Dmaven.test.skip=true dockerfile:build -f newsletter-subscription

docker-compose up -d --build
echo "Docker image built"

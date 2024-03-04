#!/bin/bash
set -e
set -u

echo "Creating operator database 'news'"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
CREATE DATABASE "news";
CREATE USER news_app WITH PASSWORD 'news_password' SUPERUSER;
ALTER DATABASE news OWNER TO news_app;
\connect news
SET ROLE news_app;
CREATE SCHEMA news_schema;
ALTER ROLE news_app SET search_path TO news_schema,public;

EOSQL

echo "Creating operator database 'users'"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
CREATE DATABASE "users";
CREATE USER users_app WITH PASSWORD 'users_password' SUPERUSER;
ALTER DATABASE users OWNER TO users_app;
\connect users
SET ROLE users_app;
CREATE SCHEMA users_schema;
ALTER ROLE users_app SET search_path TO users_schema,public;
EOSQL
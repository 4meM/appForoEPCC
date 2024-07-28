## Inicializacion de Base de datos POSTGRES en Docker

    docker run -d --name foroEPCC -e POSTGRES_USER=maurpz -e POSTGRES_PASSWORD=12345 -e POSTGRES_DB=foro_db_v1 -p 5432:5432 postgres:latest

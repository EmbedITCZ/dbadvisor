# For development purposes we need oracle database

## Run oracle database locally
For running oracle locally you will need docker installed.

```
docker run -d --shm-size=1G -p 8080:8080 -p 1521:1521 -v `pwd`/dbadvisor-core/src/test/resources/scripts:/docker-entrypoint-initdb.d sath89/oracle-xe-11g
```

You can override host nad port for integration tests via system variables:
```
test.database.url=localhost
```

For build you can use maven oracle profile:
```bash
mvn clean install
```

## Sample schema
http://o7planning.org/en/10233/sample-oracle-database-for-learning-sql

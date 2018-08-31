

**Do not** commit connection details in *application.properties*. Instead, create a file *application-dev.properties* 
and add them there. This file is included in *.gitignore*.

Then, when running the program for debugging purposes:
```sh
export SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run
```
or on Windows
```bat
set SPRING_PROFILES_ACTIVE=dev
mvn spring-boot:run
```
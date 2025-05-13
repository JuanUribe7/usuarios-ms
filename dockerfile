# Etapa 1: construir el proyecto
FROM gradle:8.5.0-jdk17 AS build
WORKDIR /app

# Copiar los archivos del proyecto
COPY . .

# Compilar el proyecto y generar el JAR
RUN gradle clean bootJar --no-daemon

# Etapa 2: imagen liviana para producción
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copiar el JAR desde la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto (ajústalo si tu app usa otro)
EXPOSE 8081

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]

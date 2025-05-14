@echo off
set REGION=us-east-2
set REPO=537124941881.dkr.ecr.%REGION%.amazonaws.com/repo/jdus/reto-aws:latest

echo 🧹 Limpiando y reconstruyendo el JAR...
call gradlew.bat clean bootJar

echo 🐳 Construyendo imagen Docker...
docker build -t %REPO% .

echo 🔐 Autenticando con ECR...
FOR /F "tokens=*" %%i IN ('aws ecr get-login-password --region %REGION%') DO docker login --username AWS --password %%i 537124941881.dkr.ecr.%REGION%.amazonaws.com

echo ☁️ Enviando imagen a ECR...
docker push %REPO%

echo 🧼 Eliminando imágenes sin etiqueta en ECR...
powershell -ExecutionPolicy Bypass -File clean-untagged.ps1

echo ✅ Listo. Ahora ve a ECS y haz "Force new deployment".
pause

$region = "us-east-2"
$repositoryName = "repo/jdus/reto-aws"

# Obtener imágenes untagged
$imagesToDelete = aws ecr list-images `
  --region $region `
  --repository-name $repositoryName `
  --filter tagStatus=UNTAGGED `
  --query 'imageIds[*]' `
  --output json

# Mostrar para depurar
Write-Host "Imágenes a eliminar:" $imagesToDelete

if ($imagesToDelete -eq "[]") {
    Write-Host "✅ No hay imágenes untagged para eliminar."
} else {
    Write-Host "🗑 Eliminando imágenes untagged..."

    # Guardar JSON en archivo temporal
    $tempFile = "images.json"
    $imagesToDelete | Out-File -Encoding ascii $tempFile

    # Ejecutar eliminación
    aws ecr batch-delete-image `
      --region $region `
      --repository-name $repositoryName `
      --image-ids file://$tempFile

    Remove-Item $tempFile
    Write-Host "✅ Imágenes eliminadas."
}

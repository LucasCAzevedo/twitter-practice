#!/bin/bash

# Script para gerar imagens dos diagramas PlantUML
# Requer plantuml instalado: sudo apt-get install plantuml

PLANTUML_DIR="./plantuml"
IMAGES_DIR="./images"

# Criar diretório de imagens se não existir
mkdir -p $IMAGES_DIR

# Gerar diagrama de classes
plantuml -tpng $PLANTUML_DIR/class-diagram.puml -o ../$IMAGES_DIR

# Gerar diagramas de sequência
for file in $PLANTUML_DIR/*-sequence.puml; do
 plantuml -tpng "$file" -o ../$IMAGES_DIR
done

echo "Diagramas gerados em $IMAGES_DIR/"
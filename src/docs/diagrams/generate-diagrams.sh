#!/bin/bash

# Script para gerar imagens dos diagramas PlantUML
# Versão com verificações e múltiplas opções de instalação

PLANTUML_DIR="./plantuml"
IMAGES_DIR="./images"

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Função para verificar se comando existe
command_exists() {
 command -v "$1" >/dev/null 2>&1
}

# Função para instalar PlantUML via Docker
install_plantuml_docker() {
 echo -e "${YELLOW}Instalando PlantUML via Docker...${NC}"
 if command_exists docker; then
     docker pull plantuml/plantuml
     echo 'alias plantuml="docker run --rm -v $(pwd):/work -w /work plantuml/plantuml"' >> ~/.bashrc
     source ~/.bashrc
     echo -e "${GREEN}PlantUML instalado via Docker!${NC}"
     echo -e "${YELLOW}Execute 'source ~/.bashrc' ou abra um novo terminal${NC}"
     return 0
 else
     echo -e "${RED}Docker não encontrado. Por favor, instale o Docker primeiro.${NC}"
     return 1
 fi
}

# Função para mostrar instruções de instalação
show_install_instructions() {
 echo -e "${RED}PlantUML não encontrado!${NC}"
 echo ""
 echo "Escolha uma opção de instalação:"
 echo ""
 echo "1. Ubuntu/Debian:"
 echo "   sudo apt update && sudo apt install plantuml"
 echo ""
 echo "2. macOS (com Homebrew):"
 echo "   brew install plantuml"
 echo ""
 echo "3. Windows (com Chocolatey):"
 echo "   choco install plantuml"
 echo ""
 echo "4. Via Docker (funciona em qualquer SO):"
 echo "   docker pull plantuml/plantuml"
 echo ""
 echo "5. Manual (requer Java):"
 echo "   wget http://sourceforge.net/projects/plantuml/files/plantuml.jar/download -O plantuml.jar"
 echo "   java -jar plantuml.jar [argumentos]"
 echo ""
}

# Verificar se PlantUML está instalado
if ! command_exists plantuml; then
 show_install_instructions
 
 read -p "Deseja instalar via Docker automaticamente? (y/n): " -n 1 -r
 echo
 if [[ $REPLY =~ ^[Yy]$ ]]; then
     if install_plantuml_docker; then
         echo -e "${YELLOW}Execute o script novamente após carregar o alias${NC}"
     fi
 fi
 exit 1
fi

# Verificar se Java está instalado (necessário para PlantUML)
if ! command_exists java; then
 echo -e "${RED}Java não encontrado! PlantUML requer Java para funcionar.${NC}"
 echo "Instale Java:"
 echo "Ubuntu/Debian: sudo apt install default-jre"
 echo "macOS: brew install openjdk"
 echo "Windows: Baixe do site da Oracle ou use 'choco install openjdk'"
 exit 1
fi

# Criar diretório de imagens se não existir
mkdir -p $IMAGES_DIR

echo -e "${GREEN}Gerando diagramas...${NC}"

# Verificar se existem arquivos .puml
if [ ! -d "$PLANTUML_DIR" ]; then
 echo -e "${RED}Diretório $PLANTUML_DIR não encontrado!${NC}"
 exit 1
fi

# Contar arquivos .puml
puml_count=$(find $PLANTUML_DIR -name "*.puml" | wc -l)
if [ $puml_count -eq 0 ]; then
 echo -e "${YELLOW}Nenhum arquivo .puml encontrado em $PLANTUML_DIR${NC}"
 exit 1
fi

echo "Encontrados $puml_count arquivo(s) .puml"

# Gerar todos os diagramas .puml
generated_count=0
for file in $PLANTUML_DIR/*.puml; do
 if [ -f "$file" ]; then
     filename=$(basename "$file" .puml)
     echo "Gerando: $filename.png"
     
     if plantuml -tpng "$file" -o "../$IMAGES_DIR"; then
         ((generated_count++))
         echo -e "${GREEN}✓ $filename.png gerado com sucesso${NC}"
     else
         echo -e "${RED}✗ Erro ao gerar $filename.png${NC}"
     fi
 fi
done

echo ""
echo -e "${GREEN}Concluído!${NC}"
echo "Diagramas gerados: $generated_count/$puml_count"
echo "Localização: $IMAGES_DIR/"

# Listar arquivos gerados
if [ $generated_count -gt 0 ]; then
 echo ""
 echo "Arquivos gerados:"
 ls -la $IMAGES_DIR/*.png 2>/dev/null || echo "Nenhuma imagem PNG encontrada"
fi
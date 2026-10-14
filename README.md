# Academic Race 🐙

Aplicativo mobile de gamificação de estudos e leitura, desenvolvido com Spring Boot e React Native.

---

## Tecnologias utilizadas

**Backend**
- Java 21+
- Spring Boot 4.1.1
- Spring Data JPA
- Spring Security + JWT
- MySQL 8.0

**Frontend**
- React Native com Expo SDK 54
- Expo Router
- Axios
- Expo Secure Store

---

## Pré-requisitos

Antes de começar, instale:

- [Java JDK 21+](https://www.oracle.com/java/technologies/downloads/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [MySQL 8.0](https://dev.mysql.com/downloads/installer/)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)

---

## Como rodar o projeto

### 1. Clonar o repositório

```bash
git clone URL_DO_REPOSITORIO
cd academic-race-main
```

### 2. Configurar o banco de dados

Abre o MySQL Workbench e executa o seguinte SQL para criar o banco e as tabelas:

```sql
CREATE DATABASE academic_race;
USE academic_race;

CREATE TABLE usuario (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    foto VARCHAR(255),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE streak (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    dias_consecutivos INT DEFAULT 0,
    ultima_data DATE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
CREATE TABLE metodo_estudo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);
CREATE TABLE registro_estudo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    descricao TEXT,
    metodo_id BIGINT,
    duracao_minutos INT NOT NULL,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (metodo_id) REFERENCES metodo_estudo(id)
);
CREATE TABLE meta (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    minutos_por_dia INT NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
CREATE TABLE grupo (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    tipo ENUM('ESTUDO', 'LEITURA') NOT NULL,
    meta_diaria INT,
    data_inicio DATE,
    data_fim DATE
);
CREATE TABLE grupo_membro (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grupo_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    FOREIGN KEY (grupo_id) REFERENCES grupo(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
CREATE TABLE progresso_corrida (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    grupo_id BIGINT NOT NULL,
    tempo_estudado INT,
    observacao TEXT,
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (grupo_id) REFERENCES grupo(id)
);
CREATE TABLE mensagem (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    grupo_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    conteudo TEXT NOT NULL,
    data_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (grupo_id) REFERENCES grupo(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
CREATE TABLE livro (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(100),
    ano INT,
    total_paginas INT
);
CREATE TABLE estante_livro (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    livro_id BIGINT NOT NULL,
    status ENUM('TBR', 'CR', 'DNF') NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (livro_id) REFERENCES livro(id)
);
CREATE TABLE progresso_leitura (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    livro_id BIGINT NOT NULL,
    paginas_lidas INT,
    percentual DECIMAL(5,2),
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (livro_id) REFERENCES livro(id)
);
CREATE TABLE avaliacao_livro (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    livro_id BIGINT NOT NULL,
    nota INT CHECK (nota BETWEEN 1 AND 5),
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (livro_id) REFERENCES livro(id)
);
```

### 3. Configurar o backend

Abre o arquivo `src/main/resources/application.properties` e ajusta a senha do MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/academic_race
spring.datasource.username=root
spring.datasource.password=SUA_SENHA_AQUI
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

### 4. Rodar o backend

Abre o projeto no IntelliJ IDEA e clica no botão **Run** (triângulo verde). O servidor vai subir na porta **8080**.


```

Descobre o seu IP local rodando `ipconfig` (Windows) ou `ifconfig` (Mac/Linux) e pega o **IPv4** do Wi-Fi.



## Estrutura do projeto
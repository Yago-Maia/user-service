# Micro serviço de usuário utilizando JWT
## Projeto Integrado PUC Minas

## Imagem docker
Caso queira rodar a aplicação, ela está disponível em meu perfil do docker. Segue comando para baixá-la e executar.
```bash
docker pull yagogmaia/user-service
docker run yagogmaia/user-service
```

# Clonar e rodar projeto via maven
## Clonar o projeto:
Acesse o diretório desejado para alocar o projeto pelo terminal e execute o comando:
```bash
git clone https://github.com/Yago-Maia/user-service.git
```

## Instalação do Maven:
Acesse o diretório pelo terminal e instale o Maven junto com as dependências necessárias para o projeto.
```bash
mvn install
```

## Executar a aplicação:
Ainda no terminal, executar o comando no diretório raiz do projeto.
```bash
mvn spring-boot:run
```

## Postman:
Segue na raiz do diretório uma coleção chamada 'postman_collection.json' para utilização das API's.
Segue nome dos Endpoint's, suas descrições e permissões:

### Token de acesso
Para acessar alguns endpoint's, é necessário criar um usuário e utilizar o token jwt e, em alguns endpoint's, é necessário possuir permissão de administrador, confome descrito abaixo.

## Users:

### Auth
Autentica o usuário e devolve um token jwt necessário para acessar alguns endpoint's e acessar a API de criação de Carteiras de ações.
```bash
curl --location --request POST 'http://ec2-52-23-254-85.compute-1.amazonaws.com:8080/auth' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "yago@gmail.com",
    "password": "senha"
}'
```
### Get all users
Recupar todos os usuários regitrados em nosso banco de dados. Necessário envio do token contendo role ADMINISTRATOR.
```bash
curl --location --request GET 'http://ec2-52-23-254-85.compute-1.amazonaws.com:8080/user' \
--header 'Authorization: {token}'
```

### Get user by id
Recupera um usuário de acordo com o id dele. Necessário enviar o token de autenticação e ser o usuário que quer alterar.
```bash
curl --location --request GET 'http://ec2-52-23-254-85.compute-1.amazonaws.com:8080/user/21' \
--header 'Authorization: {token}'
```

### Create user
Cria um usuário no banco. Acesso permitido à todos.
```bash
curl --location --request POST 'http://ec2-52-23-254-85.compute-1.amazonaws.com:8080/user' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Nome",
    "lastName": "UltimoNome",
    "email": "email@gmail.com",
    "password": "senha",
    "role": "SIMPLE" or "ADMINISTRATOR"
}'
```

### Edit user
Edita um usuário no banco. Necessário enviar o token de autenticação e ser o usuário que quer alterar.
```bash
curl --location --request PUT 'http://ec2-52-23-254-85.compute-1.amazonaws.com:8080/user' \
--header 'Authorization: {token}' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1,
    "firstName": "Yago",
    "lastName": "Guimaraes",
    "email": "yago@gmail.com",
    "password": "mimica",
    "role": "ADMINISTRATOR"
}'
```

### Delete user
Deleta um usuário no banco. Necessário enviar o token de autenticação e ser o usuário que quer deletar.
```bash
curl --location --request DELETE 'http://ec2-52-23-254-85.compute-1.amazonaws.com:8080/user/1' \
--header 'Authorization: {token}'
```

## Swagger:
Para acessar o Swagger é necessário acessar o link abaixo com o projeto em execução:
```bash
http://ec2-52-23-254-85.compute-1.amazonaws.com:8080/swagger-ui/
```

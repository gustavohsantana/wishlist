# Wishlist Application

## Descrição

Este projeto é uma aplicação de gerenciamento de listas de desejos (wishlist) desenvolvida em Java utilizando o framework Spring Boot. A aplicação permite adicionar, remover e listar produtos em uma wishlist, além de verificar se um produto está presente na lista.

## Tecnologias Utilizadas

- **Linguagem**: Java 21
- **Framework**: Spring Boot 3.4.1
- **Banco de Dados**: MongoDB
- **Gerenciamento de Dependências**: Gradle
- **Containerização**: Docker

## Arquitetura do Projeto

A arquitetura do projeto foi definida seguindo os princípios de **Clean Architecture** e **DDD (Domain-Driven Design)**. A aplicação está dividida em camadas:

- **Controller**: Responsável por receber as requisições HTTP e retornar as respostas.
- **Service**: Contém a lógica de negócio da aplicação.
- **Repository**: Responsável pela comunicação com o banco de dados.
- **DTO (Data Transfer Object)**: Utilizado para transferir dados entre as camadas da aplicação.
- **Mapper**: Utilizado para converter entidades em DTOs e vice-versa.

## Testes

A aplicação foi desenvolvida utilizando **TDD (Test-Driven Development)** e **BDD (Behavior-Driven Development)**. Os testes foram escritos utilizando JUnit e Cucumber.

Para executar os testes, utilize o seguinte comando:

```sh 
./gradlew test
```

### Exemplo de Teste TDD

```java
@Test
public void shouldNotAllowAddingMoreThan20Products() {
    String clientId = "cliente1";
    for (int i = 1; i <= 20; i++) {
        wishListService.addProductToWishList(clientId, new ProductDTO("Produto" + i));
    }

    assertThrows(RuntimeException.class, () -> {
        wishListService.addProductToWishList(clientId, new ProductDTO("Produto21"));
    });
}
```

O arquivo feature do Cucumber pode ser encontrado em `src/test/resources/features/wishlist.feature`.

Todos os testes podem ser executados com o comando:

```sh
./gradlew test 
```

### Exemplo de Teste BDD

```gherkin
  Scenario: Add a product to the wishlist
Given the client with ID "cliente1"
When the client adds a product with ID "1" and name "Produto Teste"
Then the product should be added to the wishlist
```

```java
@Given("the client with ID {string}")
public void theClientWithID(String clientId) {
   wishListRepository.deleteAll();
   this.clientId = clientId;
}

@When("the client adds a product with ID {string} and name {string}")
//...

@Then("the product should be added to the wishlist")
//...
```


## Executando a Aplicação

Para executar a aplicação utilizando Docker Compose, siga os passos abaixo:

1. Certifique-se de que o Docker está instalado na sua máquina.
2. Clone o repositório em sua máquina local:
    ```sh
    git clone https://github.com/gustavohsantana/wishlist.git
    ```
3. Navegue até o diretório raiz do projeto:
    ```sh
    cd wishlist
    ```
4. Execute o Docker Compose para iniciar a aplicação e o MongoDB:
    ```sh
    docker-compose up
    ```

A aplicação estará disponível em `http://localhost:8080`.

## Documentação da API

A documentação da API foi gerada automaticamente pelo Swagger e pode ser acessada em `http://localhost:8080/swagger-ui/index.html`.

# Implementação do Spring Security

O Spring Security foi implementado no projeto para proteger a aplicação. A autenticação básica é usada para proteger as rotas.

## Adicionar Produto à Wishlist

Substitua `{clientId}` e `{productId}` pelos valores apropriados.

Para adicionar um produto à wishlist de um cliente, use o seguinte comando:

```sh
curl -X POST "http://localhost:8080/api/wishlist/{clientId}/add" \
     -u user:password \
     -H "Content-Type: application/json" \
     -d '{"id":"1","name":"Produto Teste"}'
```

Saida:
```json
{
   "id": "676a4bbd748fe77d94cfe27d",
   "clientId": "clientIDXPTO",
   "products": [
      {
         "id": "1",
         "name": "Produto Teste"
      }
   ]
}
```

## Obter Todos os Produtos da Wishlist

Para obter todos os produtos da wishlist de um cliente, use o seguinte comando:

```sh
curl -X GET "http://localhost:8080/api/wishlist/{clientId}" \
     -u user:password
```

Saída:
```json
[
   {
      "id": "1",
      "name": "Produto Teste"
   },
   {
      "id": "2",
      "name": "Produto 2"
   }
]
```

## Remover Produto da Wishlist

Para remover um produto da wishlist de um cliente, use o seguinte comando:

```sh
curl -X DELETE "http://localhost:8080/api/wishlist/{clientId}/products" \
     -u user:password \
     -H "Content-Type: application/json" \
     -d '{"id":"1","name":"Produto Teste"}'
```
Saída: (Retorna a wishlist atualizada)

```json
{
   "id": "676a4bbd748fe77d94cfe27d",
   "clientId": "clientIDXPTO",
   "products": [
      {
         "id": "2",
         "name": "Produto 2"
      }
   ]
}
``` 

## Verificar se um Produto Está na Wishlist

Para verificar se um produto está na wishlist de um cliente, use o seguinte comando:

```sh
curl -X GET "http://localhost:8080/api/wishlist/{clientId}/product/{productId}" \
     -u user:password
```

Saída:
```json
true
```

## Licença
@2024 - Gustavo Santana

Este projeto está licenciado sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

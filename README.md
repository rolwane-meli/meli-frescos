# Desafio Integrador - MeLi Frescos

<br />

## Objetivo 🚀
O objetivo deste projeto final é implementar uma API REST no âmbito do slogan e aplicar
os conteúdos trabalhados durante o BOOTCAMP MELI. (Git, Java, Spring, Banco de Dados,
Qualidade e Segurança).
<br />


## Configurações ⚙️
* Java 11
* MySql
* Configurar váriaveis de ambiente no seguinte formato(com dados do seu banco de dados local):
  * <em>USER_DB=user;PASSWORD_DB=password</em>
<br />

## Funcionalidades 🔧
1. Cadastrar um lote com seu estoque de produtos.
2. Atualizar lote já cadastrado.
3. Veja lista completa de produtos.
4. Veja uma lista de produtos filtrada por categoria*.
5. Registre um pedido com sua lsita de produtos.
6. Mostrar pedidos do carrinho.
7. Altere o status do pedido entre ABERTO/ENCERRADO.
8. Veja uma lista de produtos com seus respectivos lotes.
9. Veja uma lista de produtos com seus respectivos lotes ordenados por:\
     L = ordenado por lote\
     Q = ordenado por quantidade atual\
     V = ordenado por data de vencimento
10. Obtenha quantidade total de determinado produto por armazém.
11. Obtenha todos os lotes armazenados em um setor de um armazém ordenados por sua data de vencimento.
12. Obtenha uma lista de lotes dentro do prazo de validade solicitado, que pertencem a uma determinada categoria*.

*<em>Categorias: FS = Fresco; RF = Refrigerado; FF = Congelado</em>

<br />
## End-Points ➡️

***URL BASE:*** ```/api/v1/fresh-products```

<br />

### GET

| Ação | End-Point |
|---------|---------|
| Listar todos os produtos dentro do prazo de validade estipulado e da categoria indicada  |  ```/batch?days=30&type=FS``` |a
| Listar produtos por setor dentro do prazo de vlidade estipiladp |  ```/batch/due-date?numberOfDays=30&sectorId=1``` |
| Listar todos os produtos |  ```/product``` |
| Listar todos lotes de um produto ordenados |  ```/product/1?orderByType=L``` |
| Listar todos produtos de uma categoria |  ```/product/type/FS``` |
| Lista produtos de um carrinho | ```/orders/1```|
| Lista armazens que um produto está alocado | ```/warehouse?productId=1``` |

<br />

### POST

 Para cadastrar um lote: ```/batch```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
  "currentTemperature": 0,
  "dueDate": "2022-11-17T18:45:27.672Z",
  "id": 0,
  "manufacturingDate": "string",
  "manufacturingTime": {
    "hour": 0,
    "minute": 0,
    "nano": 0,
    "second": 0
  },
  "price": 0,
  "productId": 0,
  "productQuantity": 0,
  "volume": 0
}
```
<br />

Para registrar um pedido de entrada no estoque: ```/inboundorder```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
  "batchStock": [
    {
      "currentTemperature": 0,
      "dueDate": "2022-11-17T18:57:13.125Z",
      "id": 0,
      "manufacturingDate": "string",
      "manufacturingTime": {
        "hour": 0,
        "minute": 0,
        "nano": 0,
        "second": 0
      },
      "price": 0,
      "productId": 0,
      "productQuantity": 0,
      "volume": 0
    }
  ],
  "orderDate": "2022-11-17T18:57:13.125Z",
  "sectionCode": 0
}
```
<br />

 Para cadastrar um produto: ```/product```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
  "name": "string",
  "sellerId": 0,
  "type": "string"
}
```
<br />

Para cadastrar um carrinho: ```/orders```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
  "batchId": 0,
  "buyerId": 0,
  "id": 0,
  "productDTO": {
    "price": 0,
    "productId": 0,
    "quantity": 0
  }
}
```
<br />

Para cadastrar um representante: ```/representative```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
  "cpf": "string",
  "email": "string",
  "id": 0,
  "name": "string",
  "warehouse": {
    "id": 0,
    "name": "string",
    "sectors": [
      {
        "capacity": 0,
        "id": 0,
        "inboundOrders": [
          {
            "batches": [
              {
                "currentTemperature": 0,
                "dueDate": "2022-11-17T18:59:38.801Z",
                "id": 0,
                "manufacturingDate": "dd/MM/yyyy",
                "manufacturingTime": {
                  "hour": 0,
                  "minute": 0,
                  "nano": 0,
                  "second": 0
                },
                "price": 0,
                "product": {
                  "batches": [
                    null
                  ],
                  "id": 0,
                  "name": "string",
                  "productPurchaseOrders": [
                    {
                      "batchId": 0,
                      "id": 0,
                      "productPrice": 0,
                      "productQuantity": 0,
                      "purchaseOrder": {
                        "buyer": {
                          "cpf": "string",
                          "email": "string",
                          "id": 0,
                          "name": "string",
                          "purchaseOrders": [
                            null
                          ]
                        },
                        "date": "2022-11-17T18:59:38.801Z",
                        "id": 0,
                        "productPurchaseOrders": [
                          null
                        ],
                        "status": "OPEN"
                      }
                    }
                  ],
                  "seller": {
                    "cnpj": "string",
                    "email": "string",
                    "id": 0,
                    "name": "string",
                    "phoneNumber": "string",
                    "products": [
                      null
                    ]
                  },
                  "type": "FROZEN"
                },
                "productQuantity": 0,
                "volume": 0
              }
            ],
            "id": 0,
            "orderDate": "dd/MM/yyyy"
          }
        ],
        "name": "string",
        "type": "FROZEN"
      }
    ]
  }
}
```
<br />

Para cadastrar um setor: ```/sector```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
  "capacity": 0,
  "name": "string",
  "type": "string",
  "warehouseId": 0
}
```
<br />

Para cadastrar um vendedor: ```/seller```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
  "cnpj": "string",
  "email": "string",
  "id": 0,
  "name": "string",
  "phoneNumber": "string",
  "products": [
    {
      "batches": [
        {
          "currentTemperature": 0,
          "dueDate": "2022-11-17T19:01:03.848Z",
          "id": 0,
          "inboundOrder": {
            "batches": [
              null
            ],
            "id": 0,
            "orderDate": "dd/MM/yyyy",
            "sector": {
              "capacity": 0,
              "id": 0,
              "inboundOrders": [
                null
              ],
              "name": "string",
              "type": "FROZEN",
              "warehouse": {
                "id": 0,
                "name": "string",
                "representative": {
                  "cpf": "string",
                  "email": "string",
                  "id": 0,
                  "name": "string"
                },
                "sectors": [
                  null
                ]
              }
            }
          },
          "manufacturingDate": "dd/MM/yyyy",
          "manufacturingTime": {
            "hour": 0,
            "minute": 0,
            "nano": 0,
            "second": 0
          },
          "price": 0,
          "productQuantity": 0,
          "volume": 0
        }
      ],
      "id": 0,
      "name": "string",
      "productPurchaseOrders": [
        {
          "batchId": 0,
          "id": 0,
          "productPrice": 0,
          "productQuantity": 0,
          "purchaseOrder": {
            "buyer": {
              "cpf": "string",
              "email": "string",
              "id": 0,
              "name": "string",
              "purchaseOrders": [
                null
              ]
            },
            "date": "2022-11-17T19:01:03.848Z",
            "id": 0,
            "productPurchaseOrders": [
              null
            ],
            "status": "OPEN"
          }
        }
      ],
      "type": "FROZEN"
    }
  ]
}
```
<br />

Para cadastrar um armazem: ```/warehouse```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
  "name": "string",
  "representativeId": 0
}
```
<br />

### PUT

Para atualizar um pedido de entrada: ```/inboundorder/{id}```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
  "batchStock": [
    {
      "currentTemperature": 0,
      "dueDate": "2022-11-17T19:02:41.388Z",
      "id": 0,
      "manufacturingDate": "string",
      "manufacturingTime": {
        "hour": 0,
        "minute": 0,
        "nano": 0,
        "second": 0
      },
      "price": 0,
      "productId": 0,
      "productQuantity": 0,
      "volume": 0
    }
  ],
  "orderDate": "2022-11-17T19:02:41.388Z",
  "sectionCode": 0
}
```
<br />

Para atualizar um carrinho: ```/orders/{id}```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
  "batchId": 0,
  "buyerId": 0,
  "id": 0,
  "productDTO": {
    "price": 0,
    "productId": 0,
    "quantity": 0
  }
}
```
<br />

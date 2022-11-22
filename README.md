# Desafio Integrador - MeLi Frescos

<br />

## Objetivo 🚀
O objetivo deste projeto final é implementar uma API REST os conteúdos trabalhados durante o BOOTCAMP MELI. (Git, Java, Spring, Banco de Dados, Qualidade e Segurança).
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

Para registrar um pedido de entrada no estoque: ```/inboundorder```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
    "sectionCode": 1,
    "orderDate": "11-11-2022",
    "batchStock": [
        {
            "productId": 6,
            "currentTemperature": -18,
            "productQuantity": 15,
            "manufacturingDate": "11-09-2022",
            "manufacturingTime": "08:21:22",
            "volume": 4,
            "dueDate": "11-01-2023",
            "price": 18.5
        }
    ]
}
```
<br />
<br />

 Para cadastrar um produto: ```/product```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
    "name": "Ovo de Codorna",
    "type": "REFRIGERATED",
    "sellerId": 1
}
```
<br />

Para cadastrar um carrinho: ```/orders```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
    "buyerId": 1,
    "batchId": 1,
    "product": {
        "productId": 7,
        "quantity": 3,
        "price": 6.0
    }
}
```
<br />

Para cadastrar um representante: ```/representative```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
    "name": "Fulano de Tal",
    "email": "fulano@gmail.com",
    "cpf": "605.447.840-06"
}
```
<br />

Para cadastrar um setor: ```/sector```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
    "name": "ST-035",
    "capacity": 45.0,
    "type": "FROZEN",
    "warehouseId": 1
}
```
<br />

Para cadastrar um vendedor: ```/seller```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
    "name": "Seller da Silva",
    "phoneNumber": "85991010101",
    "email": "seller@gmail.com",
    "cnpj": "53.350.268/0001-10"
}
```
<br />

Para cadastrar um armazem: ```/warehouse```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
    "name": "Warehouse Meli2",
    "representativeId": 1
}
```
<br />

### PUT

Para atualizar um pedido de entrada: ```/inboundorder/{id}```

<br />

*Deve ser enviado no corpo da requisição um payload de acordo com o exemplo abaixo:*

```
{
    "sectionCode": 1,
    "orderDate": "11-11-2022",
    "batchStock": [
        {
            "productId": 6,
            "currentTemperature": -18,
            "productQuantity": 10,
            "manufacturingDate": "11-09-2022",
            "manufacturingTime": "08:21:22",
            "volume": 4,
            "dueDate": "11-01-2023",
            "price": 18.5
        }
    ]
}
```
<br />

Para finalizar um carrinho de compra (purchaseOrder): ```/orders/{id}```

<br />

<br />

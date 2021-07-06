Feature: Product API - Sell Errors

  Background:
    Given the database is empty

  Scenario: Saving articles and then creating products out of them, and trying to sell a product that doesn't have enough stock
    Given these articles are added to the inventory:
      | id | name      | stock |
      | 1  | leg       | 12    |
      | 2  | screw     | 17    |
      | 3  | seat      | 2     |
      | 4  | table top | 1     |
    Given these products are created:
    """
      [
        {
          "id": 1,
          "name": "Dining Chair",
          "price": 19.90,
          "containArticles": [
            {
              "articleId": "1",
              "amount": "4"
            },
            {
              "articleId": "2",
              "amount": "8"
            },
            {
              "articleId": "3",
              "amount": "1"
            }
          ]
        },
        {
          "id": 2,
          "name": "Dinning Table",
          "price": 99.90,
          "containArticles": [
            {
              "articleId": "1",
              "amount": "4"
            },
            {
              "articleId": "2",
              "amount": "8"
            },
            {
              "articleId": "4",
              "amount": "1"
            }
          ]
        }
      ]
    """
    When a sale is requested with these details:
      | productId | quantityRequested |
      | 1         | 4                 |
    Then a client error should be returned with message: Not enough stock for article with id: 1 (16/12 needed/have) and status code 417
    Then calling /articles endpoint should return:
      | id | name      | stock |
      | 1  | leg       | 12    |
      | 2  | screw     | 17    |
      | 3  | seat      | 2     |
      | 4  | table top | 1     |

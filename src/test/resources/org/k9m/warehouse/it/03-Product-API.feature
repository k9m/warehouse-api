Feature: Product API

  Background:
    Given the inventory is empty

  Scenario: Saving articles and then creating products out of them
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
    Then calling /products endpoint should return:
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



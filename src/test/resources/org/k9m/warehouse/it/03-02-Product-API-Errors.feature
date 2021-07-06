Feature: Product API - Errors

  Background:
    Given the database is empty

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
              "articleId": "99",
              "amount": "4"
            }
          ]
        }
      ]
    """
    Then an error should be returned with message: Article not found with id: 99
    Then calling /products endpoint should return:
    """
      []
    """



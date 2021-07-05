Feature: Article API

  Background:
    Given the database is empty

  Scenario: Saving articles and then retrieving them
    Given these articles are added to the inventory:
      | id | name      | stock |
      | 1  | leg       | 12    |
      | 2  | screw     | 17    |
      | 3  | seat      | 2     |
      | 4  | table top | 1     |
    Then calling /articles endpoint should return:
      | id | name      | stock |
      | 1  | leg       | 12    |
      | 2  | screw     | 17    |
      | 3  | seat      | 2     |
      | 4  | table top | 1     |
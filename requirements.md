# Recipe-based Requirements #

## Finding Recipes ##

The use case is being able to find the best recipe that meets our client's specifications.
We've identified a few key filtering parameters to help clients find the best recipe:
* Ingredients/Nutritional Info/Dietary Restrictions
* Cuisine

## Getting Recipe Data ##

A common use case of our API is getting detailed information about a recipe. Below is a list of datapoints that are requirements:

* Preparation Instructions
	* A detailed breakdown of how to cook the recipe. This is important for anyone who wants to prepare the recipe.
* Prep Time
	* An estimate of the overall preparation time. This gives an idea on how much work the recipe takes.
* Ingredients
	* A list of ingredients as well as the amounts needed for the recipe. Our API should give more detailed information about any specific ingredient.
* Cuisine
	* The type of cuisine a recipe belongs to. Gives clients a general idea of how the recipe might taste.
* Dietary Restrictions
	* Different restrictions such as allergies, intolerances, diets, etc. Important information to see if recipe meets specifications.
* Servings
	* Amount of servings recipe can make. Gives general idea on how much food the recipe prepares.
* Price
	* Price of the recipe per serving. Gives an idea on how much the recipe costs.
* Recipe URL
	* URL to original recipe. URL leads to human-readable recipe format with additional information not in API.
* Image URL
	* URL to image of the recipe. Useful for any GUI built on top our API.
* Health Score
	* An approximate metric on how healthy a recipe is. Useful as a general guideline for people who don't think too much about nutrition.
* Popularity
	* An approximate metric on how popular a recipe is. Useful as a metric to sort recipes by.
* Spoonacular Score
	* A relative score compared to every other recipe on Spoonacular. Takes into account price, difficulty, popularity, and health. Useful metric of how good a recipe is overall.

# Meal-Plan-Based Requirements #

These requirements will not be included in v1 but will be explored in more detail in v2.
We determined that another common use-case for our API is to help plan a user's meals. There are 5 specifications that we want to be considered when creating a meal plan:
* budget
* time
* cuisine
* ingredients
* nutrition
We also want to be able to create an aggregate list of ingredients to make shopping easier and the ability to generate meal plans randomly.
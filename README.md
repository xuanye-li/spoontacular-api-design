# Spoontacular API

Find the best food recipes for your individual search criteria and get a detailed breakdown of all the data you would
want for the recipes. Spoontacular is a wrapper API over the Spoonacular REST API, that provides a fast, easy, and cheap
method to access the same information that you would get from Spoonacular. All the REST calls are abstracted away and
our classes are all quick and easy to use.

## Getting Started

First, install our package and include the following into your code:

`import cmu.edu.andrew.spoonacular.*;`

The most important classes are RecipeSearcher and Recipe. Use RecipeSearcher.Builder to specify the search criteria for
a search:

````
RecipeSearcher searcher = (new RecipeSearcher.Builder())
                .addIngredient("beef")
                .setDiet(Diet.KETOGENIC)
                .setMinimumPrice(0)
                .setMaximumPrice(10000)
                .build();
````

Then, call search to get a list of recipes that meet the search criteria:

`List<Recipe> myBeefRecipes = searcher.search();`

For a detailed look at how to construct a search criteria and the various methods of Recipe, see the Javadoc.

### Build instruction
1. Install libraries: In Intellij, File -> Project Structure -> Library -> New Project Library -> Search and Install the following libraries.
    1. com.google.code.gson:gson:2.8.6 for JSON parsing.
    2. com.squareup.okhttp3:okhttp:4.7.2 for API requests.
    3. testing: junit:junit:4.11 for unit testing
2. [Register a spoonacular account](https://spoonacular.com/food-api/console#Dashboard) and select the free account($1 plan)
3. Use your spoonacular api key for RestfulApiTests and enter them when prompted while running sample client code

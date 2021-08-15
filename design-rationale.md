## Class Structure

Overall, we wanted a better class structure for our API, focusing on making it intuitive, small, and easy to use. We wanted
to create simple abstractions that would reflect real world objects, so it would be simpler for clients to learn. Thus,
all of our classes are common names for anyone who is familiar with recipes. The classes all reflect their real-world
counterpart, making their usage very intuitive. For example, when we say two ingredients are the same, we often mean
the ingredients are the same type, not that they're the same instance. Thus, we overrode the isEqual method of ingredients
to reflect this. 

Our design also enables users to write quality code without having to worry about points efficiency. We've abstracted away
all spoonacular API calls from the user other than requiring an API key, so they don't need to worry about the APIs 
poor quality. By using our API, we guarantee that clients are using the least amount of points possible. 


## Amount

Amounts in real-life are often represented as a pair of quantity and unit. The amount class aims to ease the hassle
of dealing with this pair, by (u1) not making users do anything the library could do for them.Our amount class is used for 
volume units (like ml and fl oz), weight units (like mg and lb) as well as some miscellaneous ones (like cal and IU). We 
chose to include all three into a single class even though it complicated the conversion method because they were all 
essential quantity unit pairs, and we thought subclassing would be too complicated. So, all three belong to a single easy 
class. To make amounts easier to work with, we added a conversion method for amounts that represent the same underlying 
method.

## Enums (Cuisine, Diet, Nutrient, Intolerant)
We made these two enums because they had a small number of distinct elements so we didn't need to create a new class.
We also didn't need many methods other than converting to and from a string for simplicity. We wanted enums instead of
strings as it would make client code less error prone as we would have compile time type checking.

## Equipment
Like cuisines and diets, we wanted to make client code less error prone by avoiding strings all together. However, we
did not know all the types of equipment that spoonacular supports, thus we couldn't make it an enum.

## Ingredient
A big decision for Ingredients is whether or not an ingredient instance would carry an amount of that ingredient. Having
ingredients carry an amount means we can determine the price and nutritional information of that ingredient based on
the amount internally. However, it doesn't always make sense for ingredients to carry an amount, like if users want
the price for an arbitrary ingredient. We also didn't always have an amount from the spoonacular API for ingredients, 
for example the ingredients in an individual step of a recipe did not have an amount. Thus, the amount for an ingredient
is not in the instance of the ingredient, but rather in the recipe instead.

## Recipe
Despite having the most methods, recipe is one of the simplest classes. All methods are just getters to different instances
of objects mentioned above. Because our APIs functionality all come from Recipes, we didn't want to overcomplicate its
design, otherwise it would be difficult to learn. Instead, more complicated methods are tied to their respective classes
like amount conversion, daily nutritional value, etc. 

## RecipeSearcher
Being a main use case for our project, we wanted to make recipe searching simple but powerful. The best way to do this
was by adopting the builder pattern, which allows us to support complex search criteria without huge parameter lists.
This means that we could support all the search functionality that Spoonacular provides without overcomplicating simple
searches (as the parameters are all optional).

## Step
We wanted to keep the instructions, ingredients and equipment used for a step together. The best way to do this is to 
just create a class. 

package cmu.edu.andrew.spoonacular;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.*;
import okhttp3.HttpUrl;

//    protected Ingredient(String name, Amount amount, double price,  Map<Nutrient, Amount> nutrients, String imageURL);
class RestfulApiHelper {
    private static final String PROTOCOL = "https";
    private static final String SPOONACULAR_HOST = "api.spoonacular.com";
    private static final String GET_INGREDIENT_PATH = "food/ingredients/";
    private static final String GET_RECIPES_PATH = "recipes/complexSearch";
    private static final String GET_RECIPE_PATH = "recipes/";
    private static final String GET_INFORMATION_TRAILING_PATH = "/information";
    private static final String GET_INSTRUCTIONS_TRAILING_PATH = "/analyzedInstructions";

    /**
     * Get list of recipe by sending a GET recipes request
     * @param paramMap map of parameters to send in the Get request
     * @param apiKey client's api key
     * @return
     */
    static List<Recipe> getRecipes(Map<String, String> paramMap, String apiKey) {
        URL url = buildGetRecipesURL(paramMap, apiKey);
        try{
            JsonObject recipesJson = getRequest(url);
            JsonElement temp = recipesJson.get("results");
            if (temp == null) {
                return null;
            }
            JsonArray recipesJsonArray = temp.getAsJsonArray();
            List<Recipe> recipes = new ArrayList<Recipe>();
            for (JsonElement recipe : recipesJsonArray) {
                recipes.add(Parser.parseRecipe(recipe.getAsJsonObject()));
            }
            return recipes;
        }catch (IOException exception) {
            System.out.println("IO exception in getIngredient " + exception);
            return null;
        }
    }

    /**
     * Build the GET ingredient url
     * @param ingredientId
     * @param apiKey
     * @return
     */
    private static URL buildGetIngredientURL(int ingredientId, String apiKey) {
        URL url = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(SPOONACULAR_HOST)
                .addPathSegments(buildGetIngredientPath(ingredientId))
                .addQueryParameter("apiKey", apiKey)
                .addQueryParameter("amount", "1") // take this off when the ingredient class exposed id
                .build().url();
        return url;
    }

    /**
     * Build the GET url to get recipe list
     * @param paramMap
     * @param apiKey
     * @return
     */
    private static URL buildGetRecipesURL(Map<String, String> paramMap, String apiKey) {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(SPOONACULAR_HOST)
                .addPathSegments(GET_RECIPES_PATH)
                .addQueryParameter("apiKey", apiKey);
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            urlBuilder.addQueryParameter(entry.getKey(), entry.getValue());
        }
        return urlBuilder.build().url();
    }

    /**
     * Build the GET url to get one recipe
     * @param recipeId
     * @param apiKey
     * @return
     */
    private static URL buildGetRecipeURL(int recipeId, String apiKey) {
        URL url = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(SPOONACULAR_HOST)
                .addPathSegments(buildGetRecipePath(recipeId))
                .addQueryParameter("apiKey", apiKey)
                .build()
                .url();
        return url;
    }

    /**
     * Build the GET url to get list of step instructions
     * @param recipeId
     * @param apiKey
     * @return
     */
    private static URL buildGetStepURL(int recipeId, String apiKey) {
        URL url = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(SPOONACULAR_HOST)
                .addPathSegments(buildGetStepsPath(recipeId))
                .addQueryParameter("apiKey", apiKey)
                .build()
                .url();
        return url;
    }

    /**
     * Build the GET ingredient request url path
     * TODO: This can be optimized with StringBuilder
     * @param ingredientId
     * @return
     */
    private static String buildGetIngredientPath(int ingredientId) {
        return GET_INGREDIENT_PATH + ingredientId + GET_INFORMATION_TRAILING_PATH;
    }

    /**
     * Build the GET recipe request url path
     * TODO: This can be optimized with StringBuilder
     * @param recipeId
     * @return
     */
    private static String buildGetRecipePath(int recipeId) {
        return GET_RECIPE_PATH + recipeId + GET_INFORMATION_TRAILING_PATH;
    }

    /**
     * Build the GET recipe request url path
     * TODO: This can be optimized with StringBuilder
     * @param recipeId
     * @return
     */
    private static String buildGetStepsPath(int recipeId) {
        return GET_RECIPE_PATH + recipeId + GET_INSTRUCTIONS_TRAILING_PATH;
    }

    /**
     * Execute a GET request with a given url
     * @param url the GET url
     * @return json reponse
     * @throws IOException
     */
    private static JsonObject getRequest(URL url) throws IOException {
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) url.openConnection();
        conection.setRequestMethod("GET");
        int responseCode = conection.getResponseCode();


        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in .readLine()) != null) {
                response.append(readLine);
            }
            in .close();
            // print result
            return JsonParser.parseString(response.toString()).getAsJsonObject();
            //GetAndPost.POSTRequest(response.toString());
        } else {
            System.out.println("GET NOT WORKED");
            return null;
        }

    }
}

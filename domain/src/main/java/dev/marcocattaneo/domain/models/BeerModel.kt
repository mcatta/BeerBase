package dev.marcocattaneo.domain.models

import com.google.gson.annotations.SerializedName

data class BeerModel(
    @SerializedName("datasetid") val datasetid : String,
    @SerializedName("recordid") val recordid : String,
    @SerializedName("fields") val fields : Fields,
    @SerializedName("record_timestamp") val record_timestamp : String
)

data class Fields(
    @SerializedName("brewery_id") val brewery_id : Int,
    @SerializedName("city") val city : String,
    @SerializedName("name") val name : String,
    @SerializedName("cat_name") val cat_name : String,
    @SerializedName("country") val country : String,
    @SerializedName("cat_id") val cat_id : Int,
    @SerializedName("upc") val upc : Int,
    @SerializedName("coordinates") val coordinates : List<Double>,
    @SerializedName("srm") val srm : Int,
    @SerializedName("last_mod") val last_mod : String,
    @SerializedName("state") val state : String,
    @SerializedName("add_user") val add_user : Int,
    @SerializedName("abv") val abv : Int,
    @SerializedName("address1") val address1 : String,
    @SerializedName("name_breweries") val name_breweries : String,
    @SerializedName("style_name") val style_name : String,
    @SerializedName("id") val id : Int,
    @SerializedName("ibu") val ibu : Int,
    @SerializedName("style_id") val style_id : Int
)
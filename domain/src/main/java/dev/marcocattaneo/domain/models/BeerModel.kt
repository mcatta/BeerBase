package dev.marcocattaneo.domain.models

import Boil_volume
import Ingredients
import Method
import Volume
import com.google.gson.annotations.SerializedName

data class BeerModel(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("tagline") val tagline : String,
    @SerializedName("first_brewed") val first_brewed : String,
    @SerializedName("description") val description : String,
    @SerializedName("image_url") val image_url : String,
    @SerializedName("abv") val abv : Float,
    @SerializedName("ibu") val ibu : Int,
    @SerializedName("target_fg") val target_fg : Int,
    @SerializedName("target_og") val target_og : Int,
    @SerializedName("ebc") val ebc : Float,
    @SerializedName("srm") val srm : Float,
    @SerializedName("ph") val ph : Float,
    @SerializedName("attenuation_level") val attenuation_level : Float,
    @SerializedName("volume") val volume : Volume,
    @SerializedName("boil_volume") val boil_volume : Boil_volume,
    @SerializedName("method") val method : Method,
    @SerializedName("ingredients") val ingredients : Ingredients,
    @SerializedName("food_pairing") val food_pairing : List<String>,
    @SerializedName("brewers_tips") val brewers_tips : String,
    @SerializedName("contributed_by") val contributed_by : String
)
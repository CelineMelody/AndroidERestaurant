package fr.isen.paulin.androiderestaurant.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class DataResult (

  @SerializedName("data" ) var data : ArrayList<Data> = arrayListOf()

): Serializable
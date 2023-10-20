package com.example.retrofit.model

import java.io.Serializable

data class HitFav(
    val hit: Hit,
    var isFavorite: Boolean = false
):Serializable


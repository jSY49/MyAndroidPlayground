package com.example.apod.Navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Radar
import androidx.compose.material.icons.outlined.Satellite
import com.example.apod.R

sealed class NasaDestination(val route: String, val label: String, @DrawableRes val icon: Int) {
    object Apod : NasaDestination("apod", "사진", R.drawable.ic_photo)
    object Asteroids : NasaDestination("asteroids", "소행성", R.drawable.ic_asteroid)
    object Iss : NasaDestination("iss", "ISS", R.drawable.ic_iss)
}

val bottomNavItems = listOf(
    NasaDestination.Apod,
    NasaDestination.Asteroids,
    NasaDestination.Iss
)
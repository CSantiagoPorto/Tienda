package com.example.tienda.model

import java.io.Serializable

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val categoria: String,
    val thumbnail: String
) : Serializable

package com.example.lab15

data class Product(var name: String, var quantity: String)

val data: MutableList<Product> = mutableListOf<Product>(
    Product("Томаты", "1 килограмм"),
    Product("Персики", "1 килограмм"),
    Product("Виноград", "1 килограмм"),
    Product("Шоколад", "1 плитка"),
    Product("Батарейки AAA", "10 штук"),
    Product("Скумбрия", "1 банка")
)

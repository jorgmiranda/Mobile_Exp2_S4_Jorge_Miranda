package com.example.exp2_s3_jorge_miranda.classes

class Receta(
    val id: String? = null,
    val nombre: String,
    val ingredientes: String,
    val imagen: String
) {
    companion object{
        fun generarRecetasPreCargadas(): List<Receta>{
            return listOf(
                Receta(
                    nombre = "Spaghetti Carbonara",
                    ingredientes = "Spaghetti, Huevos, Queso Parmesano, Panceta, Sal, Pimienta",
                    imagen = "https://static01.nyt.com/images/2021/02/14/dining/carbonara-horizontal/carbonara-horizontal-threeByTwoMediumAt2X-v2.jpg"
                ),
                Receta(
                    nombre = "Ensalada César",
                    ingredientes = "Lechuga, Pollo, Crutones, Queso Parmesano, Salsa César",
                    imagen = "https://cdn7.kiwilimon.com/brightcove/6506/6506.jpg"
                ),
                Receta(
                    nombre = "Barquitos de calabacita",
                    ingredientes = " calabacitas medianas, salsa de tomate para pasta,  queso mozzarella rallado, queso parmesano",
                    imagen = "https://medlineplus.gov/images/recipe_zucchinipizzaboats.jpg"
                )
            )
        }
    }
}

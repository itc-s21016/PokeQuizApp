package jp.ac.it_college.std.s21016.pokemonquiz.Json

data class Pokemon(
    val id: Int,
    val name: String,
)
data class PokemonJson(
    val pokemon: List<Pokemon>
)
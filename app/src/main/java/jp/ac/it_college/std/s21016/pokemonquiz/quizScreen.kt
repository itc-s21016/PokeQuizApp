package jp.ac.it_college.std.s21016.pokemonquiz

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.picasso.Picasso
import jp.ac.it_college.std.s21016.pokemonquiz.Json.PokemonInfo
import jp.ac.it_college.std.s21016.pokemonquiz.Json.PokemonService
import jp.ac.it_college.std.s21016.pokemonquiz.databinding.FragmentQuizScreenBinding


class Quiz : Fragment() {
    private var _binding: FragmentQuizScreenBinding? = null
    private val binding get() = _binding!!
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val BASE_URL = "https://pokeapi.co/api/v2/"
    val args:QuizArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizScreenBinding.inflate(inflater, container, false)
        binding.imgPokemon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
        val num = args.pokemonid.random()
        showPokemonInfo(num)
        val qCount = args.qCount
        var collecto = args.collecto



        binding.tvQCount.text = getString(R.string.q_count, qCount)
        val list = listOf(
            binding.btDisplay,
            binding.button6,
            binding.button7,
            binding.button8
        ).shuffled()

        list[0].text = pokemonJson.pokemon.filter { p -> p.id == num } [0].name
        list[1].text = pokemonJson.pokemon.filter { p -> p.id != num }.random().name
        list[2].text = pokemonJson.pokemon.filter { p -> p.id != num }.random().name
        list[3].text = pokemonJson.pokemon.filter { p -> p.id != num }.random().name

        var clicked = false


        class ClickListener(val right: Boolean) : View.OnClickListener{
            override fun onClick(v: View) {
                clicked = true
                if(right) {
                    collecto++
                } else {
                    Toast.makeText(context,"不正解", Toast.LENGTH_SHORT).show()
                }

                if (qCount < 10) {
                    Navigation.findNavController(v).navigate(
                        QuizDirections.actionQuizScreenSelf(
                            args.pokemonid
                        ).apply {
                            setQCount(qCount + 1)
                            setCollecto(collecto)
                        }
                    )
                }else
                    Navigation.findNavController(v).navigate(
                        QuizDirections.actionQuizScreenToAnswerScreen(
                            args.collecto
                        )
                    )
            }
        }
        list[0].setOnClickListener(ClickListener(true))
        list[1].setOnClickListener(ClickListener(false))
        list[2].setOnClickListener(ClickListener(false))
        list[3].setOnClickListener(ClickListener(false))
        return binding.root


    }

    @UiThread
    private fun showPokemonInfo(id: Int) {
        lifecycleScope.launch {
            val info = getPokemonInfo(id)
            val url = info.sprites.other.officialArtwork.frontDefault
            Picasso.get().load(url).into(binding.imgPokemon)
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @WorkerThread
    private suspend fun getPokemonInfo(id: Int): PokemonInfo {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder().apply {
                baseUrl(BASE_URL)
                addConverterFactory(MoshiConverterFactory.create(moshi))
            }.build()
            val service: PokemonService = retrofit.create(PokemonService::class.java)
            service.fetchPokemon(id).execute().body() ?: throw IllegalStateException("ポケモンが見つかりません")
        }
    }


//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}
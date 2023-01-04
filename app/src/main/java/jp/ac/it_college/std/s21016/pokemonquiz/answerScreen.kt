package jp.ac.it_college.std.s21016.pokemonquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import jp.ac.it_college.std.s21016.pokemonquiz.databinding.FragmentAnswerScreenBinding

class Answer : Fragment() {
    private var _binding: FragmentAnswerScreenBinding? = null
    private val binding get() = _binding!!
    val args:AnswerArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnswerScreenBinding.inflate(inflater, container, false)

        binding.collectText.apply {
            text = "${args.collecto}/10 問正解！！"
        }
        binding.btGenSelect.setOnClickListener {
            Navigation.findNavController(it).navigate(
                AnswerDirections.actionAnswerScreenToGenSelectScreen()
            )
        }
        return binding.root
    }
}
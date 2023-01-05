package jp.ac.it_college.std.s21016.pokemonquiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import jp.ac.it_college.std.s21016.pokemonquiz.databinding.FragmentStartScreenBinding

class StartScreen : Fragment() {
    private var _binding: FragmentStartScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartScreenBinding.inflate(inflater, container, false)

        binding.imageButton.setOnClickListener {
            Navigation.findNavController(it).navigate(
                StartScreenDirections.actionStartScreenToGenSelectScreen()
            )
        }
        return binding.root
    }

}
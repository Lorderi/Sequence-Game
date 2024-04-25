package ru.lorderi.sequencegame.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.lorderi.sequencegame.R
import ru.lorderi.sequencegame.databinding.StartFragmentBinding
import ru.lorderi.sequencegame.fragment.PlayFragment.Companion.FREE_PLAY

class StartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = StartFragmentBinding.inflate(inflater, container, false)

        binding.newGame.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_playFragment)
        }

        binding.freePlay.setOnClickListener {
            findNavController().navigate(
                R.id.action_startFragment_to_freePlayFragment,
                bundleOf(FREE_PLAY to true)
            )
        }

        binding.about.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_aboutFragment)
        }

        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_settingsFragment)
        }

        return binding.root
    }
}

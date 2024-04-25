package ru.lorderi.sequencegame.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.lorderi.sequencegame.R
import ru.lorderi.sequencegame.databinding.SettingsFragmentBinding
import ru.lorderi.sequencegame.plate.SoundThemesTypes
import ru.lorderi.sequencegame.viewmodel.PlatesViewModel

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SettingsFragmentBinding.inflate(inflater, container, false)

        val platesViewModel by activityViewModels<PlatesViewModel>()

        changeSoundLabel(platesViewModel, binding)
        changeHighlightLabel(platesViewModel, binding)
        changeHardModeLabel(platesViewModel, binding)
        changeSliderSelection(platesViewModel, binding)
        binding.delaySlider.value = platesViewModel.uiState.value.delayMilliseconds.toFloat()

        bindState(binding, platesViewModel)

        return binding.root
    }

    private fun changeSliderSelection(
        platesViewModel: PlatesViewModel,
        binding: SettingsFragmentBinding
    ) {
        val soundThemesType = platesViewModel.uiState.value.soundThemes

        if (soundThemesType == SoundThemesTypes.Animals) {
            binding.voicesChooser.setSelection(0)
        } else if (soundThemesType == SoundThemesTypes.Piano) {
            binding.voicesChooser.setSelection(1)
        }
    }

    private fun bindState(
        binding: SettingsFragmentBinding,
        platesViewModel: PlatesViewModel
    ) {
        binding.sound.setOnClickListener {
            platesViewModel.changeSound()
            changeSoundLabel(platesViewModel, binding)
        }

        binding.highlight.setOnClickListener {
            platesViewModel.changeHighlight()
            changeHighlightLabel(platesViewModel, binding)
        }

        binding.hardMode.setOnClickListener {
            platesViewModel.changeHardMode()
            changeHardModeLabel(platesViewModel, binding)
        }

        binding.delaySlider.addOnChangeListener { _, value, _ ->
            platesViewModel.changeDelay(value.toLong())
        }

        binding.voicesChooser.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (id == 0L) {
                    platesViewModel.changeTheme(SoundThemesTypes.Animals)
                } else if (id == 1L) {
                    platesViewModel.changeTheme(SoundThemesTypes.Piano)
                }
                println(platesViewModel.uiState.value.soundThemes)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun changeHighlightLabel(
        platesViewModel: PlatesViewModel,
        binding: SettingsFragmentBinding
    ) {
        if (platesViewModel.uiState.value.highlight) {
            binding.highlight.setText(R.string.button_highlight_on)
        } else {
            binding.highlight.setText(R.string.button_highlight_off)
        }
    }

    private fun changeSoundLabel(
        platesViewModel: PlatesViewModel,
        binding: SettingsFragmentBinding
    ) {
        if (platesViewModel.uiState.value.sound) {
            binding.sound.setText(R.string.sound_on)
        } else {
            binding.sound.setText(R.string.sound_off)
        }
    }

    private fun changeHardModeLabel(
        platesViewModel: PlatesViewModel,
        binding: SettingsFragmentBinding
    ) {
        if (platesViewModel.uiState.value.hardMode) {
            binding.hardMode.setText(R.string.hardmode_text_on)
        } else {
            binding.hardMode.setText(R.string.hardmode_text_off)
        }
    }
}

package ru.lorderi.sequencegame.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.lorderi.sequencegame.R
import ru.lorderi.sequencegame.databinding.AboutFragmentBinding
import ru.lorderi.sequencegame.fragment.PlayFragment.Companion.RECORD
import ru.lorderi.sequencegame.fragment.PlayFragment.Companion.RECORD_VALUE
import ru.lorderi.sequencegame.plate.PlateUiState
import ru.lorderi.sequencegame.plate.SoundThemesTypes
import ru.lorderi.sequencegame.viewmodel.PlatesViewModel

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = AboutFragmentBinding.inflate(inflater, container, false)

        val preferences = requireContext().getSharedPreferences(RECORD, Context.MODE_PRIVATE)

        val platesViewModel by activityViewModels<PlatesViewModel>()

        readRecord(preferences, platesViewModel)

        bindAbout(platesViewModel.uiState.value, binding)

        return binding.root
    }

    private fun readRecord(
        preferences: SharedPreferences,
        platesViewModel: PlatesViewModel
    ) {
        val record = preferences.getInt(RECORD_VALUE, 0)
        platesViewModel.setRecord(record)
    }

    private fun bindAbout(
        plateUiState: PlateUiState,
        binding: AboutFragmentBinding
    ) {
        binding.record.text =
            getText(R.string.record_value_text).toString().format(plateUiState.record)
        if (plateUiState.hardMode) {
            binding.hardmode.setText(R.string.hardmode_text_on)
        } else {
            binding.hardmode.setText(R.string.hardmode_text_off)
        }
        if (plateUiState.highlight) {
            binding.highlight.setText(R.string.button_highlight_on)
        } else {
            binding.highlight.setText(R.string.button_highlight_off)
        }
        if (plateUiState.sound) {
            binding.sound.setText(R.string.sound_on)
        } else {
            binding.sound.setText(R.string.sound_off)
        }
        binding.delay.text =
            getText(R.string.delay_value_label).toString().format(plateUiState.delayMilliseconds)

        val voicesLabel = when (plateUiState.soundThemes) {
            SoundThemesTypes.Piano -> {
                "Piano"
            }

            SoundThemesTypes.Animals -> {
                "Animals"
            }
        }
        binding.voices.text =
            getText(R.string.voices_value_label).toString().format(voicesLabel)
    }
}

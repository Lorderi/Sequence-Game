package ru.lorderi.sequencegame.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.lorderi.sequencegame.theme.ComposeAppTheme
import ru.lorderi.sequencegame.ui.PlayScreen
import ru.lorderi.sequencegame.viewmodel.PlatesViewModel


class PlayFragment : Fragment() {
    companion object {
        const val RECORD = "RECORD"
        const val RECORD_VALUE = "RECORD_VALUE"
        const val GAME_IS_RUNNED = "GAME_IS_RUNNED"
        const val FREE_PLAY = "FREE_PLAY"

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(GAME_IS_RUNNED, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val binding = PlayFragmentBinding.inflate(inflater, container, false)

        val gameRunned = savedInstanceState?.getBoolean(GAME_IS_RUNNED) ?: false

        val preferences = requireContext().getSharedPreferences(RECORD, Context.MODE_PRIVATE)

        val freePlay = arguments?.getBoolean(FREE_PLAY) ?: false

        val platesViewModel by activityViewModels<PlatesViewModel>()

        readRecord(preferences, platesViewModel)

        val view = ComposeView(requireContext()).apply {
            setContent {
                ComposeAppTheme {
                    PlayScreen(freePlay = freePlay, platesViewModel = platesViewModel)
                }
            }
        }

        platesViewModel.clearValues()


        platesViewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
//                binding.levelText.text = it.level.toString()
//                binding.recordText.text = it.record.toString()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        if (!freePlay) {
            platesViewModel.sequences
                .flowWithLifecycle(viewLifecycleOwner.lifecycle)
                .onEach {
//                    compareBotAndPlayer(platesViewModel, binding, preferences, savedInstanceState)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }

        platesViewModel.menu
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .filter { it }
            .onEach {
                platesViewModel.setMenu(false)
                requireParentFragment().findNavController()
                    .navigateUp()
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        return view
    }

    private fun readRecord(
        preferences: SharedPreferences,
        platesViewModel: PlatesViewModel
    ) {
        val record = preferences.getInt(RECORD_VALUE, 0)
        platesViewModel.setRecord(record)
    }

//    private suspend fun compareBotAndPlayer(
//        platesViewModel: PlatesViewModel,
//        binding: PlayFragmentBinding,
//        preferences: SharedPreferences,
//        savedInstanceState: Bundle?,
//    ) {
//        val botSequence = platesViewModel.sequences.value.botSequence
//        val playerSequence = platesViewModel.sequences.value.playerSequence
//
//        var needDialog = false
//        if (playerSequence.size == botSequence.size) {
//            if (botSequence == playerSequence) {
//                updateRecord(playerSequence, platesViewModel, preferences)
//                runBot(platesViewModel, binding, savedInstanceState)
//            } else {
//                needDialog = true
//            }
//        }
//        if (needDialog || playerSequence.size > botSequence.size) {
//            requireParentFragment().findNavController()
//                .navigate(R.id.action_play_fragment_to_dialogFragment)
//        }
//    }
//
//    private fun updateRecord(
//        playerSequence: MutableList<Int>,
//        platesViewModel: PlatesViewModel,
//        preferences: SharedPreferences
//    ) {
//        if (playerSequence.size > 0) {
//            val record = platesViewModel.uiState.value.record
//            val level = platesViewModel.uiState.value.level
//            if (level > record) {
//                platesViewModel.setRecord(level)
//                preferences.edit {
//                    putInt(RECORD_VALUE, platesViewModel.uiState.value.record)
//                }
//            }
//        }
//    }
//
//    private suspend fun runBot(
//        platesViewModel: PlatesViewModel,
//        binding: PlayFragmentBinding,
//        savedInstanceState: Bundle?,
//    ) {
//        setClickablePlates(binding, false)
//        platesViewModel.setInputMode(InputMode.Bot)
//
//        val level = platesViewModel.uiState.value.level
//        if (level == 0) {
//            savedInstanceState?.putBoolean(GAME_IS_RUNNED, true)
//            showStartCounter(binding)
//            var c = 3
//            for (i in 0..3) {
//                binding.startGameText.text = "${c--}"
//                delay(1_000)
//            }
//            hideStartCounter(binding)
//        }
//
//        platesViewModel.setLevel(level + 1)
//
//        if (platesViewModel.uiState.value.hardMode) {
//            randomClick(platesViewModel, binding)
//        } else {
//            clickWithLastValues(platesViewModel, binding)
//        }
//
//        platesViewModel.setInputMode(InputMode.Player)
//        setClickablePlates(binding, true)
//    }
//
//    private suspend fun clickWithLastValues(
//        platesViewModel: PlatesViewModel,
//        binding: PlayFragmentBinding
//    ) {
//        val sequence = platesViewModel.sequences.value.botSequence.toList()
//        platesViewModel.clearSequences()
//        for (num in sequence) {
//            delay(platesViewModel.uiState.value.delayMilliseconds)
//            clickNum(num, binding)
//        }
//        delay(platesViewModel.uiState.value.delayMilliseconds)
//        val num = (0..3).random()
//        clickNum(num, binding)
//    }
//
//    private suspend fun randomClick(
//        platesViewModel: PlatesViewModel,
//        binding: PlayFragmentBinding
//    ) {
//        var count = 0
//        while (count < platesViewModel.uiState.value.level) {
//            delay(platesViewModel.uiState.value.delayMilliseconds)
//            val num = (0..3).random()
//            clickNum(num, binding)
//            count++
//        }
//    }
//
//
//    private fun clickNum(num: Int, binding: PlayFragmentBinding) {
//        when (num) {
//            0 -> {
//                clickPlate(binding.firstPlate)
//            }
//
//            1 -> {
//                clickPlate(binding.secondPlate)
//            }
//
//            2 -> {
//                clickPlate(binding.thirdPlate)
//            }
//
//            3 -> {
//                clickPlate(binding.fourthPlate)
//            }
//        }
//    }
//
//    private fun bindAllPlates(
//        binding: PlayFragmentBinding,
//        platesViewModel: PlatesViewModel,
//    ) {
//        val voices: List<Int> = when (platesViewModel.uiState.value.soundThemes) {
//            SoundThemesTypes.Animals -> {
//                ANIMALS_VOICES
//            }
//
//            SoundThemesTypes.Piano -> {
//                NOTE_VOICES
//            }
//        }
//
//        val firstSound = MediaPlayer.create(requireContext(), voices[FIRST_PLATE])
//        val secondSound = MediaPlayer.create(requireContext(), voices[SECOND_PLATE])
//        val thirdSound = MediaPlayer.create(requireContext(), voices[THIRD_PLATE])
//        val fourthSound = MediaPlayer.create(requireContext(), voices[FOURTH_PLATE])
//
//        binding.firstPlate.setOnClickListener {
//            bindPlateAndSound(platesViewModel, FIRST_PLATE, firstSound)
//        }
//
//        binding.secondPlate.setOnClickListener {
//            bindPlateAndSound(platesViewModel, SECOND_PLATE, secondSound)
//        }
//
//        binding.thirdPlate.setOnClickListener {
//            bindPlateAndSound(platesViewModel, THIRD_PLATE, thirdSound)
//        }
//
//        binding.fourthPlate.setOnClickListener {
//            bindPlateAndSound(platesViewModel, FOURTH_PLATE, fourthSound)
//        }
//    }
//
//    private fun bindPlateAndSound(
//        platesViewModel: PlatesViewModel,
//        key: Int,
//        firstSound: MediaPlayer,
//    ) {
//        val inputMode = platesViewModel.uiState.value.inputMode
//
//        if (inputMode == InputMode.Bot) {
//            platesViewModel.addNumToBot(key)
//        } else if (inputMode == InputMode.Player) {
//            platesViewModel.addNumToPlayer(key)
//        }
//
//        if (platesViewModel.uiState.value.sound) {
//            firstSound.seekTo(0)
//            firstSound.start()
//        }
//    }

}

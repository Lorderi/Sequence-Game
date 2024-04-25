package ru.lorderi.sequencegame.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.lorderi.sequencegame.plate.InputMode
import ru.lorderi.sequencegame.plate.PlateUiState
import ru.lorderi.sequencegame.plate.Sequences
import ru.lorderi.sequencegame.plate.SoundThemesTypes

class PlatesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PlateUiState())
    val uiState: StateFlow<PlateUiState> = _uiState.asStateFlow()

    private val _sequences = MutableStateFlow(Sequences())
    val sequences: StateFlow<Sequences> = _sequences.asStateFlow()

    private val _menu = MutableStateFlow(false)
    val menu: StateFlow<Boolean> = _menu.asStateFlow()

    fun changeSound() {
        val sound = _uiState.value.sound
        _uiState.update {
            it.copy(sound = !sound)
        }
    }

    fun changeHighlight() {
        val highlight = _uiState.value.highlight
        _uiState.update {
            it.copy(highlight = !highlight)
        }
    }

    fun changeHardMode() {
        val hardMode = _uiState.value.hardMode
        _uiState.update {
            it.copy(hardMode = !hardMode)
        }
    }

    fun changeDelay(delay: Long) {
        _uiState.update {
            it.copy(delayMilliseconds = delay)
        }
    }

    fun changeTheme(theme: SoundThemesTypes) {
        _uiState.update {
            it.copy(soundThemes = theme)
        }
    }

    fun setLevel(level: Int) {
        _uiState.update {
            it.copy(level = level)
        }
    }

    fun setRecord(record: Int) {
        _uiState.update {
            it.copy(record = record)
        }
    }

    fun setInputMode(inputMode: InputMode) {
        _uiState.update {
            it.copy(inputMode = inputMode)
        }
    }

    fun addNumToBot(num: Int) {
        val bot = _sequences.value.botSequence
        bot.add(num)
        val edited = _sequences.value.edited
        _sequences.update {
            it.copy(botSequence = bot, edited = !edited)
        }
    }

    fun addNumToPlayer(num: Int) {
        val player = _sequences.value.playerSequence
        player.add(num)
        val edited = _sequences.value.edited
        _sequences.update {
            it.copy(playerSequence = player, edited = !edited)
        }
    }

    fun clearSequences() {
        _sequences.value.botSequence.clear()
        _sequences.value.playerSequence.clear()
        val edited = _sequences.value.edited
        _sequences.update {
            it.copy(edited = !edited)
        }
    }

    fun clearValues() {
        val level = 0
        _uiState.update {
            it.copy(level = level)
        }

        clearSequences()
    }

    fun setMenu(menu: Boolean) {
        _menu.update {
            menu
        }
    }
}

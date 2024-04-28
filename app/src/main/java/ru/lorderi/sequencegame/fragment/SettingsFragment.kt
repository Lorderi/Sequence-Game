package ru.lorderi.sequencegame.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.lorderi.sequencegame.theme.ComposeAppTheme
import ru.lorderi.sequencegame.ui.SettingsScreen
import ru.lorderi.sequencegame.viewmodel.PlatesViewModel

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val platesViewModel by activityViewModels<PlatesViewModel>()

        val view = ComposeView(requireContext()).apply {
            setContent {
                ComposeAppTheme {
                    SettingsScreen(platesViewModel = platesViewModel)
                }
            }
        }

        return view
    }
}
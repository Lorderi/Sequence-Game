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
import ru.lorderi.sequencegame.fragment.PlayFragment.Companion.RECORD
import ru.lorderi.sequencegame.fragment.PlayFragment.Companion.RECORD_VALUE
import ru.lorderi.sequencegame.theme.ComposeAppTheme
import ru.lorderi.sequencegame.ui.AboutScreen
import ru.lorderi.sequencegame.viewmodel.PlatesViewModel

class AboutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val preferences = requireContext().getSharedPreferences(RECORD, Context.MODE_PRIVATE)

        val platesViewModel by activityViewModels<PlatesViewModel>()

        readRecord(preferences, platesViewModel)

        val view = ComposeView(requireContext()).apply {
            setContent {
                ComposeAppTheme {
                    AboutScreen(platesViewModel = platesViewModel)
                }
            }
        }

        return view
    }

    private fun readRecord(
        preferences: SharedPreferences,
        platesViewModel: PlatesViewModel
    ) {
        val record = preferences.getInt(RECORD_VALUE, 0)
        platesViewModel.setRecord(record)
    }
}

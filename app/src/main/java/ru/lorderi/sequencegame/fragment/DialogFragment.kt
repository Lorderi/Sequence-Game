package ru.lorderi.sequencegame.fragment

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.lorderi.sequencegame.R
import ru.lorderi.sequencegame.viewmodel.PlatesViewModel


class DialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val platesViewModel by activityViewModels<PlatesViewModel>()

        return activity?.let {
            val navController = requireNotNull(
                requireParentFragment().requireParentFragment().childFragmentManager.findFragmentById(
                    R.id.container
                )
            )
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Game over")
                .setMessage("You passed level: ${platesViewModel.uiState.value.level - 1}")
                .setNegativeButton("Menu") { _, _ ->
                    platesViewModel.setMenu(true)
                    navController.findNavController()
                        .navigateUp()
                }
                .setPositiveButton("Restart") { _, _ ->
                    platesViewModel.clearValues()
                    navController.findNavController()
                        .navigateUp()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

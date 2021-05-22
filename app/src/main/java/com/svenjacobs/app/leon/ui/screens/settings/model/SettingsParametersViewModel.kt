package com.svenjacobs.app.leon.ui.screens.settings.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.svenjacobs.app.leon.domain.model.Sanitizer
import com.svenjacobs.app.leon.repository.CleanerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsParametersViewModel @Inject constructor(
    private val cleanerRepository: CleanerRepository,
) : ViewModel() {

    val sanitizers
        get() = cleanerRepository.getSanitizers()

    fun setEnabled(
        sanitizer: Sanitizer,
        enabled: Boolean,
    ) {
        viewModelScope.launch {
            cleanerRepository.updateSanitizer(
                sanitizer.withEnabled(enabled)
            )
        }
    }
}

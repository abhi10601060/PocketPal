package com.app.pocketpal.presentation.screens.entry

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.app.pocketpal.domain.model.Label
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject


@HiltViewModel
class EntryScreenViewModel @Inject constructor() : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var amount by mutableStateOf(0)
    var selectedLabel by mutableStateOf<Label?>(null)
    var entryScreenState by mutableStateOf(EntryScreenState())


    fun saveData(){
        val validation = validateData()
        if (validation.isEmpty()){

        }
        else{
            entryScreenState = EntryScreenState(error = validation)
        }
    }

    fun validateData() : String{
        if(title.length == 0){
            return "Input field required title..."
        }
        else if (amount == 0){
            return "Amount can not be 0"
        }
        else if(selectedLabel == null){
            return "Please Select Label"
        }
        return ""
    }

}
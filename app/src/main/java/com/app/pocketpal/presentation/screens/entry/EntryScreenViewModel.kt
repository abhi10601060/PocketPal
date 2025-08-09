package com.app.pocketpal.presentation.screens.entry

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pocketpal.data.room.model.Expense
import com.app.pocketpal.domain.model.Label
import com.app.pocketpal.domain.use_case.upsert_expense.UpsertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@HiltViewModel
class EntryScreenViewModel @Inject constructor(val upsertExpenseUseCase: UpsertUseCase) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var amount by mutableStateOf(0)
    var selectedLabel by mutableStateOf<Label?>(null)
    var entryScreenState by mutableStateOf(EntryScreenState())


    fun saveData(){
        val validation = validateData()
        if (validation.isEmpty()){
            val expense = Expense("PP:" + System.currentTimeMillis(), title, description, amount, selectedLabel!!.name)
            viewModelScope.launch(Dispatchers.IO) {
                upsertExpenseUseCase.invoke(expense).collect {
                    if (it){
                        Log.d("EXPENSE", "saveData: success")
                    }
                    else{
                        Log.d("EXPENSE", "saveData: success")
                    }
                }
            }
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
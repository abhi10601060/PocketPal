package com.app.pocketpal.presentation.screens.entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.Bitmap
import com.app.pocketpal.constant.getColorForLable
import com.app.pocketpal.data.room.model.Expense
import com.app.pocketpal.domain.model.Label
import com.app.pocketpal.domain.use_case.get_expense_images.GetExpenseImagesUseCase
import com.app.pocketpal.domain.use_case.save_images.SaveImagesUseCase
import com.app.pocketpal.domain.use_case.upsert_expense.UpsertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@HiltViewModel
class EntryScreenViewModel @Inject constructor(val upsertExpenseUseCase: UpsertUseCase, val saveImagesUseCase: SaveImagesUseCase, val getExpenseImagesUseCase: GetExpenseImagesUseCase) : ViewModel() {

    var title by mutableStateOf("")
    var description by mutableStateOf("")
    var amount by mutableStateOf(0)
    var selectedLabel by mutableStateOf<Label?>(null)
    var images = mutableStateListOf<Bitmap>()


    var entryScreenState by mutableStateOf(EntryScreenState())


    fun saveData(){
        val validation = validateData()
        if (validation.isEmpty()){
            val expense = Expense("PP:" + System.currentTimeMillis(), title, description, amount, selectedLabel!!.name)
            viewModelScope.launch(Dispatchers.IO) {
                upsertExpenseUseCase.invoke(expense).collect {
                    if (it){
                        entryScreenState = EntryScreenState(success = true)
                        saveImagesUseCase.invoke(images.toList(), expense.id).collect {  }
                    }
                    else{
                        entryScreenState = EntryScreenState(error = "Something went wrong")
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

    fun loadDataForExpense(expense: Expense){
        title = expense.title
        description = expense.desc
        amount = expense.amount
        selectedLabel = Label(expense.label, getColorForLable(expense.label))
        getExpenseImages(expense.id)
    }

    fun getExpenseImages(expenseId : String){
        viewModelScope.launch(Dispatchers.IO) {
            getExpenseImagesUseCase.invoke(expenseId).collect {
                images.clear()
                images.addAll(it)
            }
        }
    }

}
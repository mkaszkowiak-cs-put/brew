package pl.put.brew

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class UserModel : ViewModel() {
    var name by mutableStateOf("")
}


package ovh.trustme.overdated.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Bienvenue ! Ouvrez le menu afin de profiter de l'application"
    }
    val text: LiveData<String> = _text
}
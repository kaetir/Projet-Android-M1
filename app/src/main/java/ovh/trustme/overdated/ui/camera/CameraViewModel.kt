package ovh.trustme.overdated.ui.camera


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "Lecteur de code barres"
    }
    val text: LiveData<String> = _text

    
}
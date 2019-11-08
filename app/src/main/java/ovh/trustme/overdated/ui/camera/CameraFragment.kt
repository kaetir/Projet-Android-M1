package ovh.trustme.overdated.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ovh.trustme.overdated.R

class CameraFragment : Fragment() {

    private lateinit var cameraViewModel: CameraViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cameraViewModel =
                ViewModelProviders.of(this).get(CameraViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_camera, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        cameraViewModel.text.observe(this, Observer {
            textView.text = it
        })
        val dlc_dluo: Switch = root.findViewById(R.id.dlc_dluo)
        dlc_dluo.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                dlc_dluo.text = "DLC"

            } else {
                // The switch is disabled
                dlc_dluo.text = "DLUO"
            }
        }

        val capartencuisine: Button = root.findViewById(R.id.capartencuisine)
        capartencuisine.setOnClickListener{l ->
            Toast.makeText(requireContext(), "caca", Toast.LENGTH_LONG).show()
        }


        return root
    }
}
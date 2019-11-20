package ovh.trustme.overdated.ui.camera

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import ovh.trustme.overdated.MainActivity
import ovh.trustme.overdated.R
import java.text.SimpleDateFormat
import java.util.*


class CameraFragment : Fragment() {

    private lateinit var cameraViewModel: CameraViewModel
    private var barcodeView: DecoratedBarcodeView? = null
    private var lastText: String? = null

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null || result.text == lastText) { // Prevent duplicate scans
                Log.d("RESULT", "NULL")
                return
            }
            Toast.makeText( activity, "Scan de : " + result.text, Toast.LENGTH_LONG).show()
            lastText = result.text

            val textView = view!!.findViewById<TextView>(R.id.camera_ean)
            textView.text = result.text
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

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

        barcodeView = root.findViewById(R.id.barcode_scanner) as DecoratedBarcodeView
        val formats: Collection<BarcodeFormat> = listOf(
            BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39
        )

        val intent = Intent(activity, MainActivity::class.java)
        barcodeView?.barcodeView?.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView?.initializeFromIntent(intent)
        barcodeView?.decodeContinuous(callback)

        val dlcDluo: Switch = root.findViewById(R.id.dlc_dluo)
        dlcDluo.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                dlcDluo.text = getString(R.string.DCL)

            } else {
                // The switch is disabled
                dlcDluo.text = getString(R.string.DLUO)
            }
        }

        val capartencuisine: Button = root.findViewById(R.id.capartencuisine)
        capartencuisine.setOnClickListener{
            Toast.makeText(requireContext(), "caca", Toast.LENGTH_LONG).show()
        }

        val lechoixdansladate: TextView  = root.findViewById(R.id.lechoixdansladate)
        lechoixdansladate.text = SimpleDateFormat("dd.MM.yyyy")
                                    .format(System.currentTimeMillis())

        val cal = Calendar.getInstance()


        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            lechoixdansladate.text = sdf.format(cal.time)

        }

        lechoixdansladate.setOnClickListener{
            DatePickerDialog(requireContext(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        barcodeView!!.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView!!.pause()
    }

    fun pause() {
        barcodeView!!.pause()
    }

    fun resume() {
        barcodeView!!.resume()
    }

    fun triggerScan() {
        barcodeView!!.decodeSingle(callback)
    }
}
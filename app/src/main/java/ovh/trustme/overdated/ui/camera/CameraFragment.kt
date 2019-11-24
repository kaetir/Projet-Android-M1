package ovh.trustme.overdated.ui.camera

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_camera.*
import ovh.trustme.overdated.MainActivity
import ovh.trustme.overdated.ProductsViewModel
import ovh.trustme.overdated.R
import ovh.trustme.overdated.database.Product
import ovh.trustme.overdated.pojo.ProductService
import ovh.trustme.overdated.pojo.RequeteOpenfood
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CameraFragment : Fragment() {

    private lateinit var cameraViewModel: CameraViewModel
    private lateinit var productViewModel: ProductsViewModel
    private lateinit var root: View

    private var barcodeView: DecoratedBarcodeView? = null
    private var lastText: String? = null

    // Callback function to read the ean13 code
    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            // Prevent duplicate scans
            if (result.text == null || result.text == lastText) {
                return
            }
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

        root = inflater.inflate(R.layout.fragment_camera, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get a new or existing ViewModel from the ViewModelProvider.
        cameraViewModel = ViewModelProviders.of(this).get(CameraViewModel::class.java)
        productViewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

        // Initialization of the scanner
        val formats: Collection<BarcodeFormat> = listOf(BarcodeFormat.EAN_13)
        val intent = Intent(activity, MainActivity::class.java)

        barcodeView = root.findViewById(R.id.barcode_scanner) as DecoratedBarcodeView
        barcodeView?.setStatusText("Placez un code-barres au niveau du scanner")
        barcodeView?.initializeFromIntent(intent)
        barcodeView?.barcodeView?.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView?.decodeContinuous(callback)

        // Declaration of retrofit builder
        val retrofit = Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()


        /*
            List of all the inputs
         */
        val textView: TextView = text_gallery
        cameraViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val dlcDluo: Switch = dlc_dluo
        dlcDluo.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                dlcDluo.text = getString(R.string.DLC)

            } else {
                // The switch is disabled
                dlcDluo.text = getString(R.string.DLUO)
            }
        }

        val lechoixdansladate: TextView  = lechoixdansladate
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

        val capartencuisine: Button = capartencuisine


        // When the user click on the submit button
        capartencuisine.setOnClickListener{
            val type = dlcDluo.text as String
            val date  = lechoixdansladate.text as String
            val ean13: String = camera_ean.text.toString()

            val service = retrofit.create(ProductService::class.java)
            val productRequest = service.productGot(ean13)

            // If the ean13 code has the correct pattern
            if(ean13.matches(Regex("^[0-9]{13}$"))) {
                // Then we perform the request with all the useful informations
                if (!productRequest.isExecuted) {
                    productRequest(productRequest, date, type)
                }
            }
            // Else we warn the user that the code is not well formed
            else {
                Toast.makeText(requireContext(), "Code inconnu, veuillez le vérifier",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeView!!.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView!!.pause()
    }

    // Function which performs the network call
    private fun productRequest(productRequest: Call<RequeteOpenfood>, date: String, type: String) {
        productRequest.enqueue(object : Callback<RequeteOpenfood> {
            // First case : the network call passed without trouble
            override fun onResponse(call: Call<RequeteOpenfood>,
                                    response: Response<RequeteOpenfood>
            ) {
                val prodInfo: RequeteOpenfood? = response.body()

                // If the API doesn't know the product, we inform the user
                if(prodInfo!!.status == 0) {
                    Toast.makeText(
                        requireContext(),
                        "Désolé mais ce produit n'est pas référencé",
                        Toast.LENGTH_LONG
                    ).show()
                }
                // Else, the API knows the product, we can now insert it into the database
                else {
                    val product = prodInfo.product

                    Toast.makeText(
                        requireContext(),"Le produit a été ajouté à la liste",
                        Toast.LENGTH_LONG
                    ).show()
                    val dbProduct =Product(0, date, product.product_name, type, product.image_url)
                    productViewModel.insert(dbProduct)
                }
            }

            // Second case : the network call failed
            override fun onFailure(call: Call<RequeteOpenfood>, t: Throwable) {
                if (t is IOException) {
                    Toast.makeText(
                        requireContext(),
                        "Erreur : Problème de connexion",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Erreur : La requête a échouée",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}
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
import kotlinx.android.synthetic.main.fragment_camera.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text == null || result.text == lastText) { // Prevent duplicate scans
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

        cameraViewModel = ViewModelProviders.of(this).get(CameraViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_camera, container, false)

        val textView: TextView = root.findViewById(R.id.text_gallery)
        cameraViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val formats: Collection<BarcodeFormat> = listOf(BarcodeFormat.EAN_13)

        val intent = Intent(activity, MainActivity::class.java)

        barcodeView = root.findViewById(R.id.barcode_scanner) as DecoratedBarcodeView
        barcodeView?.setStatusText("Placez un code-barres au niveau du scanner")
        barcodeView?.initializeFromIntent(intent)
        barcodeView?.barcodeView?.decoderFactory = DefaultDecoderFactory(formats)
        barcodeView?.decodeContinuous(callback)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get a new or existing ViewModel from the ViewModelProvider.
        productViewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

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

        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client : OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://world.openfoodfacts.org/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()

        val capartencuisine: Button = capartencuisine

        capartencuisine.setOnClickListener{
            val type = dlcDluo.text as String
            val date  = lechoixdansladate.text as String
            val ean13: String = camera_ean.text.toString()

            val service = retrofit.create(ProductService::class.java)
            val productRequest = service.productGot(ean13)

            if(ean13.matches(Regex("^[0-9]{13}$"))) {
                if (!productRequest.isExecuted) {
                    productRequest.enqueue(object : Callback<RequeteOpenfood> {
                        override fun onResponse(
                            call: Call<RequeteOpenfood>,
                            response: Response<RequeteOpenfood>
                        ) {
                            val prodInfo: RequeteOpenfood? = response.body()

                            if(prodInfo!!.status == 0) {
                                Toast.makeText(
                                    requireContext(),
                                    "Produit non disponible",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                val product = prodInfo?.product

                                Toast.makeText(
                                    requireContext(),
                                    "Produit ajouté !",
                                    Toast.LENGTH_LONG
                                ).show()
                                val dbProduct =
                                    Product(
                                        0,
                                        date,
                                        product!!.product_name,
                                        type,
                                        product.image_url
                                    )
                                productViewModel.insert(dbProduct)
                            }
                        }

                        override fun onFailure(call: Call<RequeteOpenfood>, t: Throwable) {
                            Log.d("badFood", t.message)
                            if (t is IOException) {
                                Toast.makeText(
                                    requireContext(),
                                    "Problème de connexion",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Fail to request",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    })
                }
            }
            else {
                Toast.makeText(requireContext(), "Produit inconnu", Toast.LENGTH_LONG).show()
            }
        }

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
    }

    override fun onResume() {
        super.onResume()
        barcodeView!!.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeView!!.pause()
    }
}
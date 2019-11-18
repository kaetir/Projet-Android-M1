package ovh.trustme.overdated

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator

class BarCodeReader : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getScanner()
    }

    private fun getScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Scanner un code-barre")
        integrator.setOrientationLocked(false)
        integrator.setCaptureActivity(CaptureActivityPortrait::class.java)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Annuler", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scan de : " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
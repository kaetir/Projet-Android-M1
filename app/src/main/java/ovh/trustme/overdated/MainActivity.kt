package ovh.trustme.overdated

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import ovh.trustme.overdated.adapters.ProductAdapter
import ovh.trustme.overdated.database.ProductDatabase
import ovh.trustme.overdated.ui.list_products.ListProductsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var REQUEST_PERMISSION_CAMERA = 1
    private lateinit var productionViewModel: ProductsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        val recyclerView = findViewById<RecyclerView>(R.id.liste_produits_recycler_view)
        val adapter = ProductAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        productionViewModel = ViewModelProviders.of(this).get(ProductsViewModel::class.java)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        productionViewModel.products.observe(this, Observer { products ->
            // Update the cached copy of the words in the adapter.
            products?.let { adapter.setProducts(it) }
        })
        */

        //var db = Room.databaseBuilder(applicationContext, ProductDatabase::class.java, "test").build()

        /*Thread {
            var t = Product(2, "2019-11-21", "test", "dlc", "google")

            db.productDao().insert(t)

            db.productDao().getAll().forEach {
                Log.i("loic", "id : ${it.id}")
                Log.i("loic", "id : ${it.date}")
                Log.i("loic", "id : ${it.name}")
                Log.i("loic", "id : ${it.dlc_dluo}")
                Log.i("loic", "id : ${it.urlImage}")
            }

        }.start()*/

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        this.appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_camera, R.id.nav_list_products,
            R.id.nav_share, R.id.nav_send), drawerLayout)
        setupActionBarWithNavController(navController, this.appBarConfiguration)
        navView.setupWithNavController(navController)
        checkPermission()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA),REQUEST_PERMISSION_CAMERA)
        }
    }
}

package za.co.howtogeek.shabbatreadingsapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import za.co.howtogeek.shabbatreadingsapplication.fragments.HomeFragment

/**
 * Author: Dylan Martin, South Africa
 *
 * Current update start date: 25 July 2025.
 *
 * I am busy with an @Compose version with a ViewModel, of Shabbat Readings, but it won't be ready in time for this release.
 *
 * I've just altered the text files to contain the readings relative to this time, as well as updating the Button on the fragment_home.xml file.
 *
 * I will generate the relative Dark theme with the new app.
 */

class MainActivity : AppCompatActivity() {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    //private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) { // Check if it's the initial creation
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val homeFragment = HomeFragment()
            fragmentTransaction.add(R.id.fragment_container, homeFragment)
            fragmentTransaction.commit()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

}
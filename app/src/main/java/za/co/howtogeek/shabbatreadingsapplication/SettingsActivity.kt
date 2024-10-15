package za.co.howtogeek.shabbatreadingsapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.replace
import androidx.preference.PreferenceFragmentCompat
import za.co.howtogeek.shabbatreadingsapplication.settings.BibleTranslationPreferenceFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.settings_activity)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, BibleTranslationPreferenceFragment())
            .commit()
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /*class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }

     */
}
package za.co.howtogeek.shabbatreadingsapplication.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import za.co.howtogeek.shabbatreadingsapplication.R

class BibleTranslationPreferenceFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val listPreference: ListPreference? = findPreference("bible_translation")
        listPreference?.setOnPreferenceChangeListener { _, newValue ->
            // Handle the preference change event
            true
        }

    }

}
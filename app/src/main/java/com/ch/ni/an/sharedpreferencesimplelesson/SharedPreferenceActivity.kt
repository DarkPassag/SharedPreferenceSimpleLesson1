package com.ch.ni.an.sharedpreferencesimplelesson


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.ch.ni.an.sharedpreferencesimplelesson.databinding.ActivitySharedPreferenceBinding


const val APP_PREFERENCE = "APP_PREFERENCE"
const val PREF_SOME_TEXT = "Key1"
class SharedPreferenceActivity : BaseActivity(){

    private lateinit var bind: ActivitySharedPreferenceBinding
    private lateinit var preferences: SharedPreferences
    private val preferenceListener = SharedPreferences.OnSharedPreferenceChangeListener{_, key ->
        if(key == PREF_SOME_TEXT){
            bind.getCurrentValue.text = preferences.getString(key, "")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        bind = ActivitySharedPreferenceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(bind.root)

        preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE)
        val currentValue = preferences.getString(PREF_SOME_TEXT, "")
        bind.getCurrentValue.text = currentValue


        bind.saveButton.setOnClickListener {
            val text = bind.inputText.text.toString()
            preferences.edit()
                .putString(PREF_SOME_TEXT, text)
                .apply()
        }

        preferences.registerOnSharedPreferenceChangeListener(preferenceListener)



    }

    override fun onDestroy() {
        super.onDestroy()
        preferences.unregisterOnSharedPreferenceChangeListener(preferenceListener)
    }
}
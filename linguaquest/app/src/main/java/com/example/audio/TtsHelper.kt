package com.example.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.Locale

class TtsHelper(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context.applicationContext, this)
    private var isInitialized = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.setLanguage(Locale.FRENCH)
            tts?.setPitch(1.05f)
            tts?.setSpeechRate(0.92f)
            isInitialized = true
        } else {
            Log.e("TtsHelper", "TTS Initialization failed")
        }
    }

    /**
     * Speaks text in the specified language code (e.g. "FR", "ES", "DE", "JA", "ZH", "KO", "IT", "PT", "RU", "AR", etc.)
     */
    fun speak(text: String, languageCode: String = "FR") {
        if (!isInitialized || tts == null) return

        val targetLocale = resolveLocale(languageCode)
        val result = tts?.setLanguage(targetLocale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.w("TtsHelper", "Language $languageCode not fully supported on this TTS engine, using default.")
            tts?.setLanguage(Locale.getDefault())
        }

        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "lingua_tts_${System.currentTimeMillis()}")
    }

    fun speakFrench(text: String) {
        speak(text, "FR")
    }

    private fun resolveLocale(languageCode: String): Locale {
        return when (languageCode.uppercase()) {
            "FR" -> Locale.FRENCH
            "ES" -> Locale("es", "ES")
            "DE" -> Locale.GERMAN
            "IT" -> Locale.ITALIAN
            "JA" -> Locale.JAPANESE
            "ZH", "CN" -> Locale.CHINESE
            "KO" -> Locale.KOREAN
            "PT", "BR" -> Locale("pt", "BR")
            "RU" -> Locale("ru", "RU")
            "AR" -> Locale("ar")
            "HI" -> Locale("hi", "IN")
            "NL" -> Locale("nl", "NL")
            "SV", "SE" -> Locale("sv", "SE")
            "PL" -> Locale("pl", "PL")
            "TR" -> Locale("tr", "TR")
            "EL", "GR" -> Locale("el", "GR")
            "VI" -> Locale("vi", "VN")
            "ID" -> Locale("id", "ID")
            "TH" -> Locale("th", "TH")
            "TL", "FIL" -> Locale("fil", "PH")
            "SW" -> Locale("sw")
            "HE", "IL" -> Locale("he", "IL")
            "UK", "UA" -> Locale("uk", "UA")
            "NO" -> Locale("no", "NO")
            "DA", "DK" -> Locale("da", "DK")
            "FI" -> Locale("fi", "FI")
            "HU" -> Locale("hu", "HU")
            "CS", "CZ" -> Locale("cs", "CZ")
            "RO" -> Locale("ro", "RO")
            "IE", "GA" -> Locale("ga", "IE")
            else -> runCatching { Locale(languageCode.lowercase()) }.getOrDefault(Locale.getDefault())
        }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
    }
}

package com.example.casttest

import android.content.Context
import com.google.android.gms.cast.CastMediaControlIntent
import com.google.android.gms.cast.framework.CastOptions
import com.google.android.gms.cast.framework.OptionsProvider
import com.google.android.gms.cast.framework.SessionProvider

class CastOptionsProvider : OptionsProvider {
    companion object {
        const val CUSTOM_NAMESPACE = "urn:x-cast:custom_namespace"
    }

    override fun getCastOptions(appContext: Context): CastOptions {
        val supportedNamespaces: MutableList<String> = ArrayList()
        supportedNamespaces.add(CUSTOM_NAMESPACE)

        return CastOptions.Builder()
            .setReceiverApplicationId(CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID)
//            .setReceiverApplicationId("C0868879")
//            .setSupportedNamespaces(supportedNamespaces)
            .build()
    }

    override fun getAdditionalSessionProviders(context: Context): List<SessionProvider>? {
        return null
    }
}
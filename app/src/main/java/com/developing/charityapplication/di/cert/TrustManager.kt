package com.developing.charityapplication.di.cert

import android.util.Log
import com.developing.charityapplication.HeartBellApplication
import okhttp3.OkHttpClient
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*
import com.developing.charityapplication.R

fun getOkHttpClientWithCertificate(): OkHttpClient {
    try {
        val cf = CertificateFactory.getInstance("X.509")

        // Đọc chứng chỉ từ tệp res/raw/certificate.pem
        val caInput = HeartBellApplication.getAppContext().resources.openRawResource(R.raw.serverhost)
        val ca: X509Certificate = cf.generateCertificate(caInput) as X509Certificate
        caInput.close()
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
            setCertificateEntry("ca", ca)
        }

        val tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
            init(keyStore)
        }
        val trustManagers = tmf.trustManagers

        val sslContext = SSLContext.getInstance("TLS").apply {
            init(null, trustManagers, null)
        }

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustManagers[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .build()
    } catch (e: Exception) {
        Log.e("SSL_ERROR", "Error loading certificate: ${e.message}")
        throw RuntimeException(e)
    }
}

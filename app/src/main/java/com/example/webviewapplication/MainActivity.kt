package com.example.webviewapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myWebView = findViewById<WebView>(R.id.testWebView)

        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.domStorageEnabled = true;
        myWebView.addJavascriptInterface(WebAppInterface(this), "Android")

        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                /* This call inject JavaScript into the page which just finished loading. */
                myWebView.loadUrl("javascript:window.Android.showMsg('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');")
                //myWebView.loadUrl("javascript:showAndroidToast()")
            }
        }
        myWebView.loadUrl("file:///android_asset/index.htm")
        //myWebView.loadUrl("https://fervent-neumann-7d6fbe.netlify.app/")
    }


    class WebAppInterface(private val mContext: Context) {
        @JavascriptInterface
        fun showMsg(toast: String) {
            val doc = Jsoup.parse(toast)
            val elements = doc.select("div.city")
            for (element in elements) {
                Log.d("javaScriptMessage", element.getElementsByTag("div").text())
            }
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }
    }

}
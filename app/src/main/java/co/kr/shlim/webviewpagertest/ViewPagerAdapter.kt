package co.kr.shlim.webviewpagertest

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.item_viewpager.view.*

class ViewPagerAdapter(val mContext : Context, val mData : List<String>, val viewPager2 : ViewPager2) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = mData.get(position)
        holder.mWebView.loadUrl(url)
    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val mWebView = view.webview
        val mProgress = view.progressbar
        val mWebViewClient by lazy {
            object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url : String): Boolean {
                    view?.loadUrl(url)
                    return true
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    mProgress.visibility = View.VISIBLE
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    mProgress.visibility = View.GONE
                    super.onPageFinished(view, url)
                }

            }
        }
        init {
            mWebView.settings.apply {
                javaScriptEnabled = true
                cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                defaultTextEncodingName = "utf-8"
                setRenderPriority(WebSettings.RenderPriority.HIGH)
                savePassword = false
                mediaPlaybackRequiresUserGesture = false
            }

            mWebView.apply {
                isHorizontalScrollBarEnabled = false
                isVerticalScrollBarEnabled = false
                webViewClient = mWebViewClient
				setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }


        }


    }

}
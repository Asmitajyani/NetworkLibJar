package com.tecocraft.networklib

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.net.NetworkInfo
import android.support.v4.content.ContextCompat.getSystemService
import android.net.ConnectivityManager




class DialogUtils(internal var mContext: Context) {


    fun showCustomDialog(dialog: Dialog?, context: Context) {
        if (dialog != null) {
            if (!dialog.isShowing)
                if (!(context as Activity).isFinishing) {
                    dialog.show()
                }
        }
    }

    fun dismissCustomDialog(dialog: Dialog?) {
        if (dialog != null) {
            if (dialog.isShowing)
                dialog.dismiss()
        }
    }

    // Showing dialogInternet if network dialogInternet
    fun createNetDialog(context: Context, customView: Int?): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.attributes.windowAnimations = R.style.PauseDialogAnimation
        dialog.setCancelable(false)

        if (customView != null) {
            dialog.setContentView(customView)
        } else {
            dialog.setContentView(R.layout.dialog_no_internet)
        }
        //Grab the window of the dialogInternet, and change the width
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        return dialog
    }

    //check internet connection
    fun isNetworkAvailable(): Boolean {
        val cm = mContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}


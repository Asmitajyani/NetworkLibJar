package com.tecocraft.networklib

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.pwittchen.networkevents.library.BusWrapper
import com.github.pwittchen.networkevents.library.NetworkEvents
import com.github.pwittchen.networkevents.library.event.ConnectivityChanged
import com.squareup.otto.Bus
import com.squareup.otto.Subscribe


open class NetActivity : AppCompatActivity() {

    var busWrapper: BusWrapper? = null // network listener
    var networkEvents: NetworkEvents? = null// network event
    var dialogUtils: DialogUtils? = null
    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun initView(context: Context, customView: Int?) {
        init_view(context, customView)
    }

    fun init_view(context: Context, customView: Int?) {
        dialogUtils = DialogUtils(context)
        dialog = dialogUtils!!.createNetDialog(context, customView)
        busWrapper = getOttoBusWrapper(Bus())
        networkEvents = NetworkEvents(context, busWrapper).enableInternetCheck().enableWifiScan()
    }

    private fun getOttoBusWrapper(bus: Bus): BusWrapper {
        return object : BusWrapper {
            override fun register(`object`: Any) {
                bus.register(`object`)
            }

            override fun unregister(`object`: Any) {
                bus.unregister(`object`)
            }

            override fun post(event: Any) {
                bus.post(event)
            }
        }
    }

    @Subscribe
    fun onEvent(event: ConnectivityChanged) {
        if (event.connectivityStatus.toString().equals("connected to WiFi (Internet available)") || event.connectivityStatus.toString().equals(
                "connected to mobile network"
            )
        ) {
            dialogUtils!!.dismissCustomDialog(dialog)
        } else {
            if (!isFinishing)
                dialogUtils!!.showCustomDialog(dialog, this)
        }
    }

    fun registerEvent() {
        busWrapper!!.register(this)
        networkEvents!!.register()
    }

    fun unregisterEvent() {
        busWrapper!!.unregister(this)
        networkEvents!!.unregister()
    }

    fun checkNetAvalibility(): Boolean {
        var isNetAvalible = dialogUtils!!.isNetworkAvailable()
        return isNetAvalible
    }

}
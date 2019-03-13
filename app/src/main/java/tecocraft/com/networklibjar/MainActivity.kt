package tecocraft.com.networklibjar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tecocraft.networklib.NetActivity

class MainActivity : AppCompatActivity() {

    var netEvent: NetActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        netEvent = NetActivity()
        netEvent!!.initView(this, null)
    }

    override fun onResume() {
        super.onResume()
        netEvent!!.registerEvent()
    }

    override fun onPause() {
        super.onPause()
        netEvent!!.unregisterEvent()
    }
}

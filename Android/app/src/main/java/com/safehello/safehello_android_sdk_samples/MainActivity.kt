package com.safehello.safehello_android_sdk_samples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.safehello.sdk.Router
import com.safehello.sdk.SafeHelloSdk

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SafeHelloSdk.token = "TODO"
        SafeHelloSdk.observeEvents()
        findViewById<Button>(R.id.createNewSafeHelloSessionButton).setOnClickListener {
            Router.showEventScreen(
                context = this,
                title = "Demo Meeting",
                subtitle = "08:00PM",
                eventId = "any-valid-event-id"
            )
        }
        findViewById<Button>(R.id.connectToExistingSafeHelloSessionButton).setOnClickListener {
            Router.showEventScreen(
                context = this,
                title = "Demo Meeting",
                subtitle = "08:00PM",
                eventId = "any-valid-event-id"
            )
        }
    }
}

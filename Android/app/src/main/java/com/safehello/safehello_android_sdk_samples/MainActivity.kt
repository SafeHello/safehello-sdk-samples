package com.safehello.safehello_android_sdk_samples

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.CheckResult
import androidx.appcompat.app.AppCompatActivity
import com.safehello.sdk.Router
import com.safehello.sdk.SafeHelloSdk
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val HOST_USER_ID = "host-id"
        private const val PARTICIPANT_USER_ID = "participant-id"
        private const val LOCALHOST_IP="10.0.2.2" //"10.0.2.2 android emulator localhost.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.createNewSafeHelloSessionButton).setOnClickListener { createNewSession() }
        findViewById<Button>(R.id.connectToExistingSafeHelloSessionButton).setOnClickListener { connectToExistingSession() }
    }

    private fun createNewSession() {
        val progressLayout = findViewById<View>(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE
        SafeHelloSdk.disconnect()
        SafeHelloSdk.myId = HOST_USER_ID

        getUserToken(HOST_USER_ID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { token ->
                    SafeHelloSdk.token = token
                    SafeHelloSdk.connect(this)

                    SafeHelloSdk.createEvent(HOST_USER_ID, PARTICIPANT_USER_ID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ event ->
                            progressLayout.visibility = View.GONE
                            val eventId = event.id
                            if (eventId.isNullOrBlank()) {
                                Toast.makeText(this, "Error creating event: id is null or blank", Toast.LENGTH_SHORT).show()
                            } else {
                                Router.showEventScreen(
                                    context = this,
                                    title = "Demo Event",
                                    subtitle = "08:00PM\nEvent id: $eventId",
                                    eventId = eventId
                                )
                            }
                        }, {
                            Log.e(TAG, it.message, it)
                            progressLayout.visibility = View.GONE
                            Toast.makeText(this, "Error creating event", Toast.LENGTH_SHORT).show()
                        })
                },
                {
                    Log.e(TAG, it.message, it)
                    progressLayout.visibility = View.GONE
                    Toast.makeText(this, "Error retrieving host token", Toast.LENGTH_SHORT).show()
                })
    }

    private fun connectToExistingSession() {
        val progressLayout = findViewById<View>(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        SafeHelloSdk.disconnect()
        SafeHelloSdk.myId = PARTICIPANT_USER_ID

        getUserToken(PARTICIPANT_USER_ID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { token ->
                    val eventId = findViewById<EditText>(R.id.connectToExistingSafeHelloSessionEditText).text.toString().uppercase()
                    progressLayout.visibility = View.GONE
                    SafeHelloSdk.token = token
                    SafeHelloSdk.connect(this)
                    Router.showEventScreen(
                        context = this,
                        title = "Demo Event",
                        subtitle = "08:00PM\nEvent id: $eventId",
                        eventId = eventId
                    )
                },
                {
                    Log.e(TAG, it.message, it)
                    progressLayout.visibility = View.GONE
                    Toast.makeText(this, "Error retrieving participant token", Toast.LENGTH_SHORT).show()
                })
    }

    @CheckResult
    private fun getUserToken(userid: String): Single<String> {
        return Single.create { emitter ->
            try {
                val client = OkHttpClient()
                val request = Request.Builder().url("http://$LOCALHOST_IP/tokens/$userid").build()
                val response = client.newCall(request).execute()
                val responseBody = response.body?.string().orEmpty()
                emitter.onSuccess(JSONObject(responseBody).optString("token"))
            } catch (exception: Exception) {
                emitter.onError(exception)
            }
        }
    }
}

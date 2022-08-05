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
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
        private const val HOST_USER_ID = "host-id"
        private const val PARTICIPANT_USER_ID = "participant-id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressLayout = findViewById<View>(R.id.progressLayout)
        progressLayout.visibility = View.VISIBLE

        connectToSafeHello(HOST_USER_ID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                progressLayout.visibility = View.GONE
            }, {
                Log.e(TAG, it.message, it)
                progressLayout.visibility = View.GONE
                Toast.makeText(this, "Error retrieving tokens", Toast.LENGTH_SHORT).show()
            })

        findViewById<Button>(R.id.createNewSafeHelloSessionButton).setOnClickListener {

            SafeHelloSdk.connect()
            SafeHelloSdk.createEvent(HOST_USER_ID, PARTICIPANT_USER_ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ event ->
                    SafeHelloSdk.myId = HOST_USER_ID

                    val eventId = event.id
                    if (eventId.isNullOrBlank()) {
                        Toast.makeText(this, "Error creating event: id is null or blank", Toast.LENGTH_SHORT).show()
                    } else {
                        Router.showEventScreen(
                            context = this,
                            title = "Demo Event",
                            subtitle = "08:00PM",
                            eventId = eventId
                        )
                    }
                }, {
                    Log.e(TAG, it.message, it)
                    Toast.makeText(this, "Error creating event", Toast.LENGTH_SHORT).show()
                })
        }
        findViewById<Button>(R.id.connectToExistingSafeHelloSessionButton).setOnClickListener {
            SafeHelloSdk.myId = PARTICIPANT_USER_ID

            connectToSafeHello(PARTICIPANT_USER_ID).subscribeOn(Schedulers.io()).subscribe()
            SafeHelloSdk.connect()

            Router.showEventScreen(
                context = this,
                title = "Demo Event",
                subtitle = "08:00PM",
                eventId = findViewById<EditText>(R.id.connectToExistingSafeHelloSessionEditText).text.toString()
            )
        }
    }

    @CheckResult
    private fun connectToSafeHello(userid:String): Completable {
        return Completable.create { emitter ->
            try {
                val token = getUserToken(userid)
                if (token.isNullOrBlank()) {
                    emitter.onError(IllegalStateException("token is null or blank"))
                } else {

                    SafeHelloSdk.environment=SafeHelloSdk.Environment.Dev
                    SafeHelloSdk.token = token

                    emitter.onComplete()
                }
            } catch (exception: Exception) {
                emitter.onError(exception)
            }
        }
    }

    private fun getUserToken(userid: String): String {
        val client = OkHttpClient()
        val request = Request.Builder().url("http://10.0.2.2/tokens/$userid").build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string().orEmpty()
        return JSONObject(responseBody).optString("token");
    }
}

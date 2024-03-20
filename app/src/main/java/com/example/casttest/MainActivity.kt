package com.example.casttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.example.casttest.databinding.ActivityMainBinding
import com.google.android.gms.cast.framework.CastButtonFactory
import com.google.android.gms.cast.framework.CastContext
import com.google.android.gms.cast.framework.CastSession
import com.google.android.gms.cast.framework.SessionManager
import com.google.android.gms.cast.framework.SessionManagerListener

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var mCastSession: CastSession? = null
    private lateinit var mCastContext: CastContext
    private lateinit var mSessionManager: SessionManager
    private val mSessionManagerListener: SessionManagerListener<CastSession> =
        SessionManagerListenerImpl()

    private inner class SessionManagerListenerImpl : SessionManagerListener<CastSession> {
        override fun onSessionStarting(session: CastSession) {
            Log.d("TTTT_CAST", "onSessionStarting: ${session}")
        }

        override fun onSessionStarted(session: CastSession, sessionId: String) {
            invalidateOptionsMenu()
            Log.d("TTTT_CAST", "onSessionStarted: ${session}, sessionId $sessionId")
        }

        override fun onSessionStartFailed(session: CastSession, error: Int) {
            val castReasonCode = mCastContext.getCastReasonCodeForCastStatusCode(error)
            // Handle error
            Log.d("TTTT_CAST", "onSessionStartFailed: ${session}, error $castReasonCode")
        }

        override fun onSessionSuspended(session: CastSession, reason: Int) {
            Log.d("TTTT_CAST", "onSessionSuspended: ${session}, reason $reason")
        }

        override fun onSessionResuming(session: CastSession, sessionId: String) {
            Log.d("TTTT_CAST", "onSessionResuming: ${session}, sessionId $sessionId")
        }

        override fun onSessionResumed(session: CastSession, wasSuspended: Boolean) {
            invalidateOptionsMenu()
            Log.d("TTTT_CAST", "onSessionResumed: ${session}, wasSuspended $wasSuspended")
        }

        override fun onSessionResumeFailed(session: CastSession, error: Int) {
            val castReasonCode = mCastContext.getCastReasonCodeForCastStatusCode(error)
            // Handle error
            Log.d("TTTT_CAST", "onSessionResumeFailed: ${session}, error $castReasonCode")
        }

        override fun onSessionEnding(session: CastSession) {
            Log.d("TTTT_CAST", "onSessionEnding: ${session}")
        }

        override fun onSessionEnded(session: CastSession, error: Int) {
            Log.d("TTTT_CAST", "onSessionEnded: ${session}, _CASTerror $error")
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mCastContext = CastContext.getSharedInstance(this)
        mSessionManager = mCastContext.sessionManager

        CastButtonFactory.setUpMediaRouteButton(applicationContext, binding.mediaRouteButton)
    }

    override fun onResume() {
        super.onResume()
        mCastSession = mSessionManager.currentCastSession
        mSessionManager.addSessionManagerListener(mSessionManagerListener, CastSession::class.java)
    }

    override fun onPause() {
        super.onPause()
        mSessionManager.removeSessionManagerListener(mSessionManagerListener, CastSession::class.java)
        mCastSession = null
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_cast, menu)
        CastButtonFactory.setUpMediaRouteButton(
            applicationContext,
            menu,
            R.id.media_route_menu_item
        )
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
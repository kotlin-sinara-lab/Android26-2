package ru.urfu.droidpractice1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide
import ru.urfu.droidpractice1.databinding.ActivitySecondBinding

class SecondActivity : ComponentActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var likes = 0
    private val KEY_LIKES = "likes_count"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Lifecycle", "SecondActivity onCreate")

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            likes = savedInstanceState.getInt(KEY_LIKES, 0)
        }
        updateLikes()

        // Retrieve initial state
        val isRead = intent.getBooleanExtra("IS_READ", false)
        binding.switchRead.isChecked = isRead

        // Set initial result just in case user goes back immediately without changing anything
        setResult(RESULT_OK, Intent().apply { putExtra("IS_READ", isRead) })

        // Toolbar setup
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Load image with Glide
        Glide.with(binding.root)
            .load("https://upload.wikimedia.org/wikipedia/commons/thumb/7/74/Kotlin_Icon.png/1200px-Kotlin_Icon.png")
            .into(binding.imageArticle)

        // Handle switch changes
        binding.switchRead.setOnCheckedChangeListener { _, isChecked ->
            val resultIntent = Intent().apply {
                putExtra("IS_READ", isChecked)
            }
            setResult(RESULT_OK, resultIntent)
        }

        // Handle likes
        binding.btnLike.setOnClickListener {
            likes++
            updateLikes()
        }

        binding.btnDislike.setOnClickListener {
            likes--
            updateLikes()
        }

        // Handle share
        binding.btnShare.setOnClickListener {
            val title = getString(R.string.article2_title)
            val body1 = getString(R.string.article2_body1)
            val shareText = "$title\n\n$body1"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun updateLikes() {
        binding.tvLikes.text = likes.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_LIKES, likes)
    }

    override fun onStart() {
        super.onStart()
        Log.d("Lifecycle", "SecondActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Lifecycle", "SecondActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Lifecycle", "SecondActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Lifecycle", "SecondActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Lifecycle", "SecondActivity onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Lifecycle", "SecondActivity onRestart")
    }
}
package com.d4rk.androidtutorials.java.ui.android.alerts.alertdialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.d4rk.androidtutorials.java.R
import com.d4rk.androidtutorials.java.databinding.ActivityAlertDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
class AlertDialogActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlertDialogBinding
    @Suppress("DEPRECATION")
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val alertDialog = createAlertDialog()
        binding.button.setOnClickListener {
            alertDialog.show()
        }
        binding.floatingButtonShowSyntax.setOnClickListener {
            startActivity(Intent(this, AlertDialogCodeActivity::class.java))
        }
        handler.postDelayed({
            binding.floatingButtonShowSyntax.shrink()
        }, 5000)
    }
    private fun createAlertDialog(): MaterialAlertDialogBuilder {
        return MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.title_alert_dialog)
            setMessage(R.string.alert_dialog_message)
            setIcon(R.drawable.ic_shop)
            setPositiveButton(android.R.string.ok, null)
            setNegativeButton(android.R.string.cancel, null)
        }
    }
}
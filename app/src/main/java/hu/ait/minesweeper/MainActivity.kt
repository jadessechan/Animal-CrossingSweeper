package hu.ait.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import hu.ait.minesweeper.databinding.ActivityMainBinding
import hu.ait.minesweeper.model.MinesweeperModel
import hu.ait.minesweeper.view.MinesweeperView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.minesweeperView.resetGame()

        binding.btnReset.setOnClickListener {
            binding.minesweeperView.resetGame()
        }
    }

    fun isFlagMode() : Boolean {
        return binding.toggleFlag.isChecked()
    }

    fun showWinnerMessage() {
        Snackbar.make(binding.root, getString(R.string.winner), Snackbar.LENGTH_LONG).show()
    }

    fun showLoserMessage() {
        Snackbar.make(binding.root, getString(R.string.loser), Snackbar.LENGTH_LONG).show()
    }


}
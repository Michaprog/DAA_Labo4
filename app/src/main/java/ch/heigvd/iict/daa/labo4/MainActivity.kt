package ch.heigvd.iict.daa.labo4

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // ViewModel disposant d’une Factory depuis un Activité
    private val viewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((application as MyApp).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // depuis android 15 (sdk 35), le mode edge2edge doit être activé
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // la barre d'action doit être définie dans le layout, on la lie à l'activité
        setSupportActionBar(findViewById(R.id.toolbar))

    }

    private fun hasRightPane(): Boolean =
        findViewById<View?>(R.id.main_fragment_right) != null

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)

        // On tablette, cacher les actions qui sont dans le ControlFragment
        if (hasRightPane()) {
            menu.findItem(R.id.actionGenerate)?.isVisible = false
            menu.findItem(R.id.actionDeleteAll)?.isVisible = false
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.triCreation -> { /* do something */ true }
            R.id.triETA -> { /* do something */ true }
            R.id.actionGenerate -> {
                /* Generate a note */
                viewModel.generateANote()
                true
            }
            R.id.actionDeleteAll -> { /* do something */ true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
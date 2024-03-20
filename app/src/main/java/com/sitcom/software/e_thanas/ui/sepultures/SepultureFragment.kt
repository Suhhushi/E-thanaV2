import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.sitcom.software.e_thanas.R


class SepultureFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sepulture, container, false)
    }









    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Récupérer les données transmises depuis le fragment précédent
        val sexe = arguments?.getString("sexe")
        val nom = arguments?.getString("nom")
        val nomJF = arguments?.getString("nomJF")
        val prenom = arguments?.getString("prenom")
        val ville = arguments?.getString("ville")
        val cimetiere = arguments?.getString("cimetiere")

        // Afficher les données dans les TextView
        view.findViewById<TextView>(R.id.textViewNom).text = nom
        view.findViewById<TextView>(R.id.textViewPrenom).text = prenom
        view.findViewById<TextView>(R.id.textViewVille).text = ville
        view.findViewById<TextView>(R.id.textViewCimetiere).text = cimetiere
        view.findViewById<TextView>(R.id.textViewNomJF).text = nomJF

    }
}

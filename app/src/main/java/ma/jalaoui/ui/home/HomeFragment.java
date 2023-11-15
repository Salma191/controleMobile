package ma.jalaoui.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import ma.jalaoui.R;
import ma.jalaoui.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private ListView listView;
    private Button button;
    private Spinner serviceSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        listView = root.findViewById(R.id.listView);
        button = root.findViewById(R.id.button);


        homeViewModel.getData().observe(getViewLifecycleOwner(), data -> {
            ArrayAdapter<Employe> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, data);
            listView.setAdapter(adapter);

        });


                homeViewModel.fetchDataFromAPI(requireContext());



        homeViewModel.getService().observe(getViewLifecycleOwner(), filieres -> {
            ArrayAdapter<Service> serviceAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, filieres);
            serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Service defaultService = new Service();
            defaultService.setNom("Informatique");  // Remplacez "Informatique" par la valeur souhaitée
            serviceAdapter.insert(defaultService, 0);  // Insérez la valeur par défaut à la première position de la liste

            // Mettez à jour le Spinner avec l'adaptateur
            serviceSpinner.setAdapter(serviceAdapter);
        });



        listView.setOnItemClickListener((adapterView, view, position, id) -> {
            // Récupérer l'élément sélectionné dans la liste
            Employe selectedStudent = (Employe) adapterView.getItemAtPosition(position);

            // Créer une boîte de dialogue pour afficher les détails de l'élément sélectionné
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Vous pouvez modifier ou supprimer cet etudiant");
//            builder.setMessage("Code: " + selectedStudent.getCode() + "\nName: " + selectedStudent.getName());

            // Ajouter un bouton "Modifier"
            builder.setPositiveButton("Modifier", (dialog, which) -> {
            });


            // Ajouter un bouton "Supprimer"
            builder.setNeutralButton("Supprimer", (dialog, which) -> {
            });


//            // Ajouter un bouton "Fermer" pour fermer la boîte de dialogue
//            builder.setPositiveButton("Fermer", (dialog, which) -> {
//                dialog.dismiss();
//            });

            // Afficher la boîte de dialogue
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });

        button.setOnClickListener(view -> {
            showAddDataPopup();
        });

        return root;
    }

    private void showAddDataPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Ajouter des données");

        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.home_add, null);
        builder.setView(dialogView);

        EditText dataInput1 = dialogView.findViewById(R.id.code);
        EditText dataInput2 = dialogView.findViewById(R.id.name);
        EditText dataInput3 = dialogView.findViewById(R.id.date);
        Spinner dataInput5 = dialogView.findViewById(R.id.serviceSpinner);

        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String firstName = dataInput1.getText().toString();
            String lastName = dataInput2.getText().toString();
            String telephone = dataInput3.getText().toString();
            Service service = (Service) dataInput5.getSelectedItem();

            try {
                homeViewModel.addNewStudent(firstName, lastName, telephone, service, requireContext());
            } catch (JSONException e) {
                e.printStackTrace();
                // Gérez les erreurs JSON ici si nécessaire
            }

            dialog.dismiss();
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package ma.jalaoui.ui.home;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import ma.jalaoui.R;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Employe>> data;
    private MutableLiveData<List<Service>> serviceData;


    public HomeViewModel() {
        data = new MutableLiveData<>();
        serviceData = new MutableLiveData<>();
    }


    public void fetchDataFromAPI(Context context) {
        String apiUrl = "http://192.168.62.43:8085/employes";

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Employe> fetchedData = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject employeObject = response.getJSONObject(i);



                                String nom = response.getJSONObject(i).getString("nom");
                                String prenom = response.getJSONObject(i).getString("prenom");
                                String dateNaissance = response.getJSONObject(i).getString("dateNaissance");

                                JSONObject serviceObject = employeObject.getJSONObject("service");
                                String serviceName = serviceObject.getString("nom");
                                Service service = new Service(serviceName);

                                Employe employe = new Employe(nom, prenom,dateNaissance, service);
                                fetchedData.add(employe);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("JSONException", "Error parsing JSON: " + e.getMessage());
                                // Vous pouvez gérer l'exception ici, par exemple, ignorer l'élément en cours ou afficher un message d'erreur.
                            }
                        }
                        data.setValue(fetchedData);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            Log.e("VolleyError", "Error fetching data: " + error.getMessage());
                        } else {
                            Log.e("VolleyError", "Error fetching data: Response is null");
                        }
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    public void addNewStudent(String nom, String prenom, String dateNaissance, Service service, Context context) throws JSONException {
        String apiUrl = "http://192.168.62.43:8085/employes";


        // Créez une demande POST pour ajouter une nouvelle filière
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                apiUrl,
                new JSONObject() {{
                    put("nom", nom);
                    put("prenom", prenom);
                    put("dateNaissance", dateNaissance);
                    put("service", service);
                }},
                response -> {
                },
                error -> {
                    // Gérez les erreurs de la demande
                }
        );



        Employe newStudent = new Employe(nom, prenom, dateNaissance, service);
        List<Employe> currentData = data.getValue();
        currentData.add(newStudent);
        data.setValue(currentData); // Notifie les observateurs du changement de données
        // Ajoutez la demande à la file d'attente de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }






    public LiveData<List<Employe>> getData() {
        return data;
    }

    public LiveData<List<Service>> getService() {
        return serviceData;
    }

}
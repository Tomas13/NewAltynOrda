package kz.growit.altynorda.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kz.growit.altynorda.AgenciesActivity;
import kz.growit.altynorda.R;
import kz.growit.altynorda.adapters.ListAgenciesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAgenciesFragment extends Fragment {

    private ProgressView progressView;
    private ListAgenciesAdapter listAgenciesAdapter;
//    private ArrayList<Agencies> agencies;

    public ListAgenciesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View listAgenciesFragment = inflater.inflate(R.layout.fragment_list_agencies, container, false);

        progressView = (ProgressView) listAgenciesFragment.findViewById(R.id.progressAgenciesView);


//        searchAgencies(listAgenciesFragment);

        return listAgenciesFragment;
    }


    //request for Agencies list
//    public void searchAgencies(final View listAgenciesFragment) {
//
//        progressView.start();
//
//        String url = "http://altynorda.kz/api/AgenciesAPI/GetAgencies";
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                Request.Method.GET,
//                url,
//                new JSONObject(),
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            ArrayList<Agencies> agenciesArrayList = new ArrayList<>();
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject agency = (JSONObject) response.get(i);
//                                agenciesArrayList.add(new Agencies(agency));
//                            }
//
//                            //AppController.getInstance().setAgencies(agenciesArrayList);
//
//                            RecyclerView recyclerView = (RecyclerView) listAgenciesFragment.findViewById(R.id.agenciesRV);
//                         //   agencies = AppController.getInstance().getAgencies();
//                            listAgenciesAdapter = new ListAgenciesAdapter(agencies, (AgenciesActivity) getActivity());
//                            recyclerView.setHasFixedSize(true);
//                            LinearLayoutManager llm = new LinearLayoutManager(listAgenciesFragment.getContext());
//                            recyclerView.setLayoutManager(llm);
//
//                            recyclerView.setAdapter(listAgenciesAdapter);
//
//                            progressView.stop();
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        Log.d("Agencies request error", error.getMessage());
//                        progressView.stop();
//                    }
//                });
//
//
//       // AppController.getInstance().addToRequestQueue(jsonArrayRequest, "Agencies request");
//    }

}

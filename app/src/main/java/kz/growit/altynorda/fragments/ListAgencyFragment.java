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

import kz.growit.altynorda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListAgencyFragment extends Fragment {

    private ProgressView progressView;


    public ListAgencyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View listAgencyFragment = inflater.inflate(R.layout.fragment_list_agency, container, false);
        progressView = (ProgressView) listAgencyFragment.findViewById(R.id.progressAgencyView);
//        searchAgent(listAgencyFragment);
        return listAgencyFragment;


    }


//    public void searchAgent(final View listAgencyFragment) {
//        //Request for getting list of agents
//
//        progressView.start();
//
////        final AppController myApp = AppController.getInstance();
//        String url1 = "http://altynorda.kz/api/AgentsAPI/GetPrivateAgents";
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                Request.Method.GET,
//                url1,
//                new JSONObject(),
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        ArrayList<PrivateAgents> arrayList = new ArrayList<>();
//
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject current = response.getJSONObject(i);
//                                arrayList.add(new PrivateAgents(current));
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        myApp.setPrivateAgents(arrayList);
//
//
//                        RecyclerView recyclerView = (RecyclerView) listAgencyFragment.findViewById(R.id.agentsRV);
//                        ArrayList<PrivateAgents> agents = AppController.getInstance().getPrivateAgents();
//                        ListPrivateAgentsAdapter listAgentAdapter = new ListPrivateAgentsAdapter(agents);
//                        recyclerView.setHasFixedSize(true);
//                        LinearLayoutManager llm = new LinearLayoutManager(listAgencyFragment.getContext());
//                        recyclerView.setLayoutManager(llm);
//
//                        recyclerView.setAdapter(listAgentAdapter);
//
//                        progressView.stop();
//
//                    }
//
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                            Log.e("Agents request error", error.getMessage());
//                        progressView.stop();
//                    }
//                });
//
//        myApp.addToRequestQueue(jsonArrayRequest, "Agents request");
//    }
}

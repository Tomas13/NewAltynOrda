package kz.growit.altynorda.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.ThemeManager;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import kz.growit.altynorda.AppController;
import kz.growit.altynorda.MainActivity;
import kz.growit.altynorda.R;
import kz.growit.altynorda.adapters.ListingsRVAdapter;
import kz.growit.altynorda.models.Listings;
import kz.growit.altynorda.models.ListringRow;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListingsListFragment extends Fragment {
    private boolean isHidden = true;

    private Button moreFiltersButton1;


    private LinearLayout selectArrivalDateLinear;
    private LinearLayout selectDepartueDateLinear;
    private TextView arrivalDate;
    private TextView departureDate;
    private Dialog.Builder builder = null;

    public ListingsListFragment() {
        // Required empty public constructor
    }


    private RecyclerView recyclerView;
    private ArrayList<Listings> listings = new ArrayList<>();
    private ProgressView progressView;
    private int CityId;



    public void onDatePick(final View v) {

        boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;

        builder = new DatePickerDialog.Builder(isLightTheme ?
                R.style.Material_App_Dialog_DatePicker_Light : R.style.Material_App_Dialog_DatePicker) {
            @Override
            public void onPositiveActionClicked(com.rey.material.app.DialogFragment fragment) {
                DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                if (v.getTag().equals(0)) {
                    arrivalDate.setText(date);
                } else {
                    departureDate.setText(date);
                }
                Toast.makeText(v.getContext(), "Date is " + date, Toast.LENGTH_SHORT).show();
                super.onPositiveActionClicked(fragment);
            }


            @Override
            public void onNegativeActionClicked(com.rey.material.app.DialogFragment fragment) {
                Toast.makeText(v.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                super.onNegativeActionClicked(fragment);
            }
        };

        builder.positiveAction("OK")
                .negativeAction("CANCEL");


        DialogFragment fragment1 = DialogFragment.newInstance(builder);
        fragment1.show(getChildFragmentManager(), null);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_listings_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.myListingsRV);
        progressView = (ProgressView) rootView.findViewById(R.id.progressTListingsFragment);


      /*  //More Filters button to show a fragment with additional filters
        moreFiltersButton1 = (Button) fView.findViewById(R.id.moreFiltersButton1);
        moreFiltersButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHidden) {
                    isHidden = true;
                    moreFiltersButton1.setText("More filters");
                    android.support.v4.app.FragmentTransaction ft = getChildFragmentManager().beginTransaction(); //.remove(fragment1).commit();
                    ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                    ft.remove(getChildFragmentManager().findFragmentByTag("SearchFiltersFragment"));
                    ft.commit();
                } else {
                    isHidden = false;
                    moreFiltersButton1.setText("Apply filters");
                    SearchFiltersFragment searchFiltersFragment = new SearchFiltersFragment();
                    android.support.v4.app.FragmentTransaction ft = getChildFragmentManager().beginTransaction(); //.remove(fragment1).commit();
                    ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                    ft.replace(R.id.containerCategory, searchFiltersFragment, "SearchFiltersFragment");
                    ft.commit();
                }
            }
        });
        Helper.getListViewSize(resultsLV);*/


        return rootView;
    }


    @Override
    public void onResume() {
        refreshList();


        super.onResume();

    }

    private ArrayList<Listings> myListings;

    public ArrayList<Listings> getMyListings() {
        return myListings;
    }

    public void setMyListings(ArrayList<Listings> myListings) {
        this.myListings = myListings;
    }


    private void refreshList() {
        progressView.start();


        CityId = 1; //need to change according to spinner selection

        String url = "http://altynorda.kz/ListingsAPI/GetCityListings?cityId=" + CityId ;
        JsonArrayRequest getListingsByCityReq = new JsonArrayRequest(
                Request.Method.GET,
                url,
                new JSONObject(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                Listings listing = new Listings(response.getJSONObject(i));
                                listings.add(listing);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setMyListings(listings);
                        ListingsRVAdapter myAdapter = new ListingsRVAdapter(listings, (MainActivity) getActivity());
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));


                        //set number of columns depending on orientation
                        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
                        }
                        else{
                            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        }

                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(myAdapter);


                        progressView.stop();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getListingsByCityReq, "get listings");
    }
}

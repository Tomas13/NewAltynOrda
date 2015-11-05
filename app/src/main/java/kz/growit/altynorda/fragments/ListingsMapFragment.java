package kz.growit.altynorda.fragments;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

import kz.growit.altynorda.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListingsMapFragment extends Fragment {

    private GoogleMap map;


    public ListingsMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFragment = inflater.inflate(R.layout.fragment_listings_map, container, false);
//        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
//        findFragmentById(R.id.mapLF);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapLF);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(new LatLng(51.12638435, 71.43703759), 14));

                map.getUiSettings().setZoomControlsEnabled(false);
                map.getUiSettings().setCompassEnabled(true);
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMapToolbarEnabled(true);
                map.getUiSettings().setAllGesturesEnabled(true);

                initMarkers();
            }
        });
        return viewFragment;
    }

    private void initMarkers() {
//        map.setInfoWindowAdapter(new MapInfoWindowAdapter(getActivity(), AppController.getInstance().getListings().get(0)));
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                map.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude - 1), 14));
                return false;
            }
        });
        map.addMarker(new MarkerOptions()
                .position(new LatLng(51.12382893, 71.42155588))
                .title("Nomad"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(51.12840098, 71.43467188))
                .title("Nursaya"));
        Drawable test = new IconicsDrawable(getActivity().getApplicationContext(), FontAwesome.Icon.faw_building)
                .sizeDp(24)
                .colorRes(R.color.colorAccent);
        map.addMarker(new MarkerOptions()
                .position(new LatLng(51.12802392, 71.42155051))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.apartment))
                .title("Northern"));
        map.addMarker(new MarkerOptions()
                .position(new LatLng(51.13280427, 71.40276432))
                .title("Han shatyr"));
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}

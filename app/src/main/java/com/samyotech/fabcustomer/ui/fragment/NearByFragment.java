package com.samyotech.fabcustomer.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.samyotech.fabcustomer.DTO.AllAtristListDTO;
import com.samyotech.fabcustomer.DTO.MarkerData;
import com.samyotech.fabcustomer.DTO.UserDTO;
import com.samyotech.fabcustomer.R;
import com.samyotech.fabcustomer.https.HttpsRequest;
import com.samyotech.fabcustomer.interfacess.Consts;
import com.samyotech.fabcustomer.interfacess.Helper;
import com.samyotech.fabcustomer.network.NetworkManager;
import com.samyotech.fabcustomer.preferences.SharedPrefrence;
import com.samyotech.fabcustomer.ui.activity.ArtistProfileView;
import com.samyotech.fabcustomer.utils.CustomTextView;
import com.samyotech.fabcustomer.utils.CustomTextViewBold;
import com.samyotech.fabcustomer.utils.ProjectUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;


public class NearByFragment extends Fragment  {
    private String TAG = NearByFragment.class.getSimpleName();
    View view;
    private MapView mMapView;
    private GoogleMap googleMap;
    private ArrayList<MarkerOptions> optionsList = new ArrayList<>();
    private UserDTO userDTO;
    private SharedPrefrence prefrence;
    HashMap<String, String> parms = new HashMap<>();
    private ArrayList<AllAtristListDTO> allAtristListDTOList;
    private Hashtable<String, AllAtristListDTO> markers;
    private Marker marker;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_near_by, container, false);
        prefrence = SharedPrefrence.getInstance(getActivity());
        userDTO = prefrence.getParentUser(Consts.USER_DTO);
        parms.put(Consts.USER_ID, userDTO.getUser_id());

        mMapView = (MapView) view.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);
        markers = new Hashtable<String, AllAtristListDTO>();


        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        if (NetworkManager.isConnectToInternet(getActivity())) {
            parms.put(Consts.LATITUDE, prefrence.getValue(Consts.LATITUDE));
            parms.put(Consts.LONGITUDE, prefrence.getValue(Consts.LONGITUDE));
            getArtist();

        } else {
            ProjectUtils.showToast(getActivity(), getResources().getString(R.string.internet_concation));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    public void getArtist() {
        ProjectUtils.showProgressDialog(getActivity(), true, getResources().getString(R.string.please_wait));
        new HttpsRequest(Consts.GET_ALL_ARTISTS_API, parms, getActivity()).stringPost(TAG, new Helper() {
            @Override
            public void backResponse(boolean flag, String msg, JSONObject response) {
                if (flag) {
                    try {
                        allAtristListDTOList = new ArrayList<>();
                        Type getpetDTO = new TypeToken<List<AllAtristListDTO>>() {
                        }.getType();
                        allAtristListDTOList = (ArrayList<AllAtristListDTO>) new Gson().fromJson(response.getJSONArray("data").toString(), getpetDTO);



                        for (int i = 0; i < allAtristListDTOList.size();i++){

                            optionsList.add( new MarkerOptions().position(
                                    new LatLng(allAtristListDTOList.get(i).getLatitude(), allAtristListDTOList.get(i).getLongitude())).title(allAtristListDTOList.get(i).getName()).snippet(allAtristListDTOList.get(i).getUser_id()));

                        }


                        mMapView.onResume(); // needed to get the map to display immediately

                        try {
                            MapsInitializer.initialize(getActivity().getApplicationContext());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mMapView.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(GoogleMap mMap) {
                                googleMap = mMap;

                                // For showing a move to my location button
                                googleMap.setMyLocationEnabled(true);

                                // For dropping a marker at a point on the Map

                            /*    for (LatLng point : latlngs) {
                                    options.position(point);
                                    options.title("SAMYOTECH");
                                    options.snippet("SAMYOTECH");
                                    googleMap.addMarker(options);
                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(point).zoom(12).build();
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                }*/
                                for (MarkerOptions options : optionsList) {
                                    options.position(options.getPosition());
                                    options.title(options.getTitle());
                                    options.snippet(options.getSnippet());
                                    final Marker hamburg = googleMap.addMarker(options);


                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(options.getPosition()).zoom(12).build();
                                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                                    for (int i = 0; i < allAtristListDTOList.size();i++){

                                        if (allAtristListDTOList.get(i).getUser_id().equalsIgnoreCase(options.getSnippet()))

                                        markers.put(hamburg.getId(), allAtristListDTOList.get(i));


                                    }

                                    googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());


                                }


                                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

                                    @Override
                                    public void onInfoWindowClick(Marker arg0) {
                                        Intent intent = new Intent(getActivity().getBaseContext(), ArtistProfileView.class);
                                        intent.putExtra(Consts.ARTIST_ID, arg0.getSnippet());
                                        // Starting the Place Details Activity
                                        startActivity(intent);
                                    }
                                });
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    ProjectUtils.showToast(getActivity(), msg);
                }
            }
        });
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View view;

        public CustomInfoWindowAdapter() {
            view = getLayoutInflater().inflate(R.layout.custom_info_window,
                    null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            if (NearByFragment.this.marker != null
                    && NearByFragment.this.marker.isInfoWindowShown()) {
                NearByFragment.this.marker.hideInfoWindow();
                NearByFragment.this.marker.showInfoWindow();
            }
            return null;
        }

        @Override
        public View getInfoWindow(final Marker marker) {
            NearByFragment.this.marker = marker;

            String url = null;
            String name = null;
            String id = null;
            String category = null;

            if (marker.getId() != null && markers != null && markers.size() > 0) {
                if ( markers.get(marker.getId()) != null &&
                        markers.get(marker.getId()) != null) {
                    url = markers.get(marker.getId()).getImage();
                    name = markers.get(marker.getId()).getName();
                    id = markers.get(marker.getId()).getUser_id();
                    category = markers.get(marker.getId()).getCategory_name();
                }
            }
            final ImageView image = ((ImageView) view.findViewById(R.id.badge));

            if (url != null && !url.equalsIgnoreCase("null")
                    && !url.equalsIgnoreCase("")) {
                Glide.with(getActivity()).
                        load(url)
                        .placeholder(R.drawable.dummyuser_image)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image);

            } else {
                image.setImageResource(R.mipmap.ic_launcher);
            }

            //final String title = marker.getTitle();
            final CustomTextViewBold titleUi = ((CustomTextViewBold) view.findViewById(R.id.title));
            if (name != null) {
                titleUi.setText(name);
            } else {
                titleUi.setText("");
            }

         //   final String snippet = marker.getSnippet();
            final CustomTextView snippetUi = ((CustomTextView) view
                    .findViewById(R.id.snippet));
            if (category != null) {
                snippetUi.setText(category);
            } else {
                snippetUi.setText("");
            }

            return view;
        }
    }
}

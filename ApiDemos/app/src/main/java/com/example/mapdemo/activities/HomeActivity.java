package com.example.mapdemo.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapdemo.R;
import com.example.mapdemo.adapter.EndPlaceArrayAdapter;
import com.example.mapdemo.adapter.PlaceArrayAdapter;
import com.example.mapdemo.utils.GMapV2GetRouteDirection;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Hashtable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * The {@link HomeActivity} used for display {@link MapFragment}
 * with {@link android.support.design.widget.TabLayout.Tab}.
 * <p/>
 *
 * @author karannassa44@gmail.com
 * @version 1.0
 * @since 23 May, 2016
 */
public class HomeActivity extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    /*************************************
     * DECLARATION OF CLASS INSTANCES
     ************************************/
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.btMap)
    ImageView btMap;
    // Google Map
    private GoogleMap map;
    private AutoCompleteTextView tvFromPlace, tvToPlace;

    //Custom TypeFaceb declaration.
    Typeface tfCaviarDreamsBold, tfCaviarDreams;


    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final String LOG_TAG = "GetDirection";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    //    public static String[] prgmNameList = {"My Rides", "My Messages", "My Friends", "Chats", "Favourite Destination", "Notifications", "Settings", "    \n"};
//    public static int[] prgmImages = {R.drawable.ic_menu_fav_destinations, R.drawable.ic_menu_my_messages, R.drawable.ic_menu_my_friends, R.drawable.ic_menu_menu_chats, R.drawable.ic_menu_fav_destinations, R.drawable.ic_menu_menu_notifications, R.drawable.ic_menu_menu_settings, R.drawable.ic_menu_menu_blank_icon};
//    public static Class[] classList = {MyRidesRecyclerView.class, ChatListScreen.class, MyFriends.class, ChatListScreen.class, FavouriteDesination.class, Notification.class, SettingsActivity.class, SettingsActivity.class};
    // public static String [] prgmNameList={"Riding Destinations","Meet 'N' Plan A Ride","Riding Events \n    ","Modifly Your Bikes","Healthy Riding","Get Directions","Notifications","Settings"};
    // public static int [] prgmImages={R.drawable.icon_menu_destination,R.drawable.icon_menu_meetplan,R.drawable.icon_menu_events,R.drawable.icon_modifybike,R.drawable.icon_menu_healthy_riding,R.drawable.icon_menu_get_direction,R.drawable.icon_menu_notification,R.drawable.icon_menu_settings};
    // public static Class [] classList={DestinationsListActivity.class,PlanRideActivity.class,EventsListActivity.class,ModifyBikeActivity.class,HealthyRidingActivity.class,HomeActivity.class,NotificationScreen.class,SettingsActivity.class};
    private final LatLng HAMBURG = new LatLng(53.558, 9.927);
    private final LatLng HAMBURG_ = new LatLng(53.500, 9.900);
    public ImageLoader imageLoader;
    public DisplayImageOptions options;
    GMapV2GetRouteDirection v2GetRouteDirection;
    LocationManager locManager;
    Drawable drawable;
    Document document;
    EndPlaceArrayAdapter _ArrayAdapter;
    public LatLng mString_end = new LatLng(28.6139391, 77.2090212);
    public LatLng mString_start = new LatLng(30.483996999999995, 76.5939516);
    double start, end;
//    @Bind(R.id.gridView1)
//    GridView gridView1;
    //    @Bind(R.id.drawer_layout)
//    DrawerLayout mDrawerLayout;
//    @Bind(R.id.lvSlidingMenu)
//    LinearLayout lvSlidingMenu;
//    @Bind(R.id.tvName)
//    TextView tvName;

    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private Activity activity;
    private Marker customMarker;
    private LatLng markerLatLng;
    private Marker marker;
    private Hashtable<String, String> markers;
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback_start;

    private AdapterView.OnItemClickListener mAutocompleteClickListener_start
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback_start);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback_end
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            CharSequence attributions = places.getAttributions();

            // mNameTextView.setText("NAME:"+ Html.fromHtml(place.getName() + ""));
            // mAddressTextView.setText("ADDRESS: "+Html.fromHtml(place.getAddress() + ""));
            //  mIdTextView.setText(Html.fromHtml("PLACEID:" + place.getId() + ""));
            String s = "" + Html.fromHtml("" + place.getLatLng().latitude);
            String s1 = "" + Html.fromHtml("" + place.getLatLng().longitude);
            double start = Double.valueOf(s.trim()).doubleValue();
            double end = Double.valueOf(s1.trim()).doubleValue();


            mString_end = new LatLng(start, end);
            hideKeyboard();
            //mString_end =Html.fromHtml(place.getLatLng() + "");

            Log.e("end:", "" + mString_end);
            //mPhoneTextView.setText(Html.fromHtml("Lat:" + place.getLatLng().latitude + "--long:" + latlong));
            //mWebTextView.setText(place.getWebsiteUri() + "");
            if (attributions != null) {
                // mAttTextView.setText(Html.fromHtml(attributions.toString()));
            }
        }

    };

    private AdapterView.OnItemClickListener mAutocompleteClickListener_end
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final EndPlaceArrayAdapter.PlaceAutocomplete item = _ArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback_end);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    {
        mUpdatePlaceDetailsCallback_start = new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                if (!places.getStatus().isSuccess()) {
                    Log.e(LOG_TAG, "Place query did not complete. Error: " +
                            places.getStatus().toString());
                    return;
                }
                // Selecting the first object buffer.
                final Place place = places.get(0);
                CharSequence attributions = places.getAttributions();

                // mNameTextView.setText("NAME:"+ Html.fromHtml(place.getName() + ""));
                // mAddressTextView.setText("ADDRESS: "+Html.fromHtml(place.getAddress() + ""));
                //  mIdTextView.setText(Html.fromHtml("PLACEID:" + place.getId() + ""));
                String s = "" + Html.fromHtml("" + place.getLatLng().latitude);
                String s1 = "" + Html.fromHtml("" + place.getLatLng().longitude);
                start = Double.valueOf(s.trim()).doubleValue();
                end = Double.valueOf(s1.trim()).doubleValue();
                mString_start = new LatLng(start, end);

                Log.e("start:", "" + mString_start);

                //  mPhoneTextView.setText(Html.fromHtml("Lat:" + place.getLatLng().latitude + "--long:" + latlong));
                //mWebTextView.setText(place.getWebsiteUri() + "");
                if (attributions != null) {
                    // mAttTextView.setText(Html.fromHtml(attributions.toString()));
                }
            }

        };
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(HomeActivity.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        v2GetRouteDirection = new GMapV2GetRouteDirection();
        activity = this;
        ButterKnife.bind(this);

        //  setupActionBar();

        markerLatLng = new LatLng(48.8567, 2.3508);

//        gridView1.setAdapter(new CustomGridAdapter(this, prgmNameList, prgmImages));
//        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (position == 7) {
//
//                } else {
//                    Log.e("positiuon:", "" + classList[position]);
//                    intentCalling(classList[position]);
//                }
//
//            }
//        });

        initImageLoader();
        markers = new Hashtable<String, String>();
        imageLoader = ImageLoader.getInstance();

        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.places_ic_clear)        //	Display Stub Image
                .showImageForEmptyUri(R.drawable.places_ic_clear)    //	If Empty image found
                .cacheInMemory()
                .cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();
        // map.setMyLocationEnabled(true);

        // mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(2));
        // initializeMap();

        // ATTENTION: This "addApi(AppIndex.API)"was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient = new GoogleApiClient.Builder(HomeActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, HomeActivity.this)
                .addConnectionCallbacks(this)
                .addApi(AppIndex.API).build();
        tvFromPlace = (AutoCompleteTextView) findViewById(R.id
                .tvFromPlace);
        tvToPlace = (AutoCompleteTextView) findViewById(R.id
                .tvToPlace);
        tvFromPlace.setThreshold(1);
        tvToPlace.setThreshold(1);
        tvFromPlace.setOnItemClickListener(mAutocompleteClickListener_start);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        tvFromPlace.setAdapter(mPlaceArrayAdapter);
        tvToPlace.setOnItemClickListener(mAutocompleteClickListener_end);
        _ArrayAdapter = new EndPlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        tvToPlace.setAdapter(_ArrayAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideKeyboard();
            }
        }, 300);

        //Set font style from .ttf file.
        setFontTypeFace();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Initialize the Google Map.
        initializeMap();
    }

    /**
     * Implements a method to initialize the
     * Google Map.
     */
    private void initializeMap() {
        if (map == null) {
            map = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (map == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            } else {

                // map.setMyLocationEnabled(true);
                map.getUiSettings().setZoomControlsEnabled(true);
                map.getUiSettings().setCompassEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
                map.getUiSettings().setAllGesturesEnabled(true);
                map.setTrafficEnabled(true);
                setUpMap();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mGoogleApiClient.disconnect();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.ivMenu, /*R.id.rlProfile,*/ R.id.btMap})
    void onclick(View view) {
        switch (view.getId()) {

            case R.id.ivMenu:
//                if (mDrawerLayout.isDrawerOpen(lvSlidingMenu))
//                    mDrawerLayout.closeDrawer(lvSlidingMenu);
//                else
//                    mDrawerLayout.openDrawer(lvSlidingMenu);
                break;
           // case R.id.rlProfile:
                //startActivity(new Intent(activity, ProfileActivity.class));
          //      break;
            case R.id.btMap:
                GetRouteTask getRoute = new GetRouteTask();
                getRoute.execute();
                break;

        }
    }

    private void initImageLoader() {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager)
                    getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024;
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }


        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(memoryCacheSize)
                .memoryCache(new FIFOLimitedMemoryCache(memoryCacheSize - 1000000))
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                /*.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging()*/
                .build();

        ImageLoader.getInstance().init(config);
    }

    private void setUpMap() {
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter());
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.my_location_demo, null);
        final Marker hamburg = map.addMarker(new MarkerOptions()
                .position(HAMBURG)
                .title("Hamburg")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)/*(createDrawableFromView(this, marker))*/));
        markers.put(hamburg.getId(), "http://img.india-forums.com/images/100x100/37525-a-still-image-of-akshay-kumar.jpg");


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);


        customMarker = map.addMarker(new MarkerOptions()
                .position(markerLatLng)
                .title("PLACE 2")
                .snippet("Description")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)/*fromBitmap(createDrawableFromView(this, marker))*/));
        markers.put(customMarker.getId(), "http://img.india-forums.com/images/100x100/37525-a-still-image-of-akshay-kumar.jpg");
        // map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

        final Marker custom_Marker = map.addMarker(new MarkerOptions()
                .position(HAMBURG_)
                .title("PLACE 1")
                .snippet("Description")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)/*fromBitmap(createDrawableFromView(this, marker))*/));
        markers.put(custom_Marker.getId(), "http://img.india-forums.com/images/100x100/37525-a-still-image-of-akshay-kumar.jpg");


        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                marker.showInfoWindow();
                return true;
            }
        });
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                Log.e("ID:", "" + marker.getId());


                // startActivity(new Intent(activity, PublicProfileScreen.class));
            }
        });

    }

    @Override
    public void onConnected(Bundle bundle) {
        _ArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mGoogleApiClient.connect();

    }


    /**
     * Implements a method to hide KEYBOARD.
     */
    private void hideKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        mGoogleApiClient.disconnect();
    }

    private class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View view;

        public CustomInfoWindowAdapter() {
            view = getLayoutInflater().inflate(R.layout.custom_info_window,
                    null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            if (HomeActivity.this.marker != null
                    && HomeActivity.this.marker.isInfoWindowShown()) {
                HomeActivity.this.marker.hideInfoWindow();
                HomeActivity.this.marker.showInfoWindow();
            }
            return null;
        }


        @Override
        public View getInfoWindow(final Marker marker) {
            HomeActivity.this.marker = marker;


            String url = null;


            if (marker.getId() != null && markers != null && markers.size() > 0) {
                if (markers.get(marker.getId()) != null &&
                        markers.get(marker.getId()) != null) {
                    url = markers.get(marker.getId());
                }
            }
            final ImageView image = ((ImageView) view.findViewById(R.id.badge));

            if (url != null && !url.equalsIgnoreCase("null")
                    && !url.equalsIgnoreCase("")) {
//                imageLoader.displayImage(url, image, options,
//                        new SimpleImageLoadingListener() {
//                            @Override
//                            public void onLoadingComplete(String imageUri,
//                                                          View view, Bitmap loadedImage) {
//                                super.onLoadingComplete(imageUri, view,
//                                        loadedImage);
//                                getInfoContents(marker);
//                            }
//                        });
            } else {
                image.setImageResource(R.mipmap.ic_launcher);
            }

            final String title = marker.getTitle();
            final TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                titleUi.setText(title);
            } else {
                titleUi.setText("");
            }

            final String snippet = marker.getSnippet();
            final TextView snippetUi = ((TextView) view
                    .findViewById(R.id.snippet));
            if (snippet != null) {
                snippetUi.setText(snippet);
            } else {
                snippetUi.setText("");
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("", "CLICKED");
                    //   startActivity(new Intent(activity, PublicProfileScreen.class));
                }
            });

            return view;
        }
    }

    /**
     * @author Amit Agnihotri
     *         This class Get Route on the map
     */
    private class GetRouteTask extends AsyncTask<String, Void, String> {

        String response = "";
        private ProgressDialog Dialog;

        @Override
        protected void onPreExecute() {
            Dialog = new ProgressDialog(HomeActivity.this);
            Dialog.setMessage("Loading route...");
            Dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {

            //Get All Route values
            document = v2GetRouteDirection.getDocument(mString_start, mString_end, GMapV2GetRouteDirection.MODE_DRIVING);
            response = "Success";
            return response;

        }

        @Override
        protected void onPostExecute(String result) {
            map.clear();
            if (response.equalsIgnoreCase("Success")) {
                ArrayList<LatLng> directionPoint = v2GetRouteDirection.getDirection(document);
                PolylineOptions rectLine = new PolylineOptions().width(10).color(
                        Color.parseColor("#D1622A"));

                for (int i = 0; i < directionPoint.size(); i++) {
                    rectLine.add(directionPoint.get(i));
                }
                LatLng latLng = new LatLng(start, end);

                // Adding route on the map
                map.addPolyline(rectLine);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 9);
                map.animateCamera(cameraUpdate);
                //  mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                //mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(6),1000, null);
                // markerOptions.position(toPosition);
                // markerOptions.draggable(true);
                // mGoogleMap.addMarker(markerOptions);

            }

            Dialog.dismiss();
        }
    }

    /**
     * Implements a method to set FONT style using .ttf by putting
     * in main\assets\fonts directory of current project.
     */
    private void setFontTypeFace() {
        tfCaviarDreamsBold = Typeface.createFromAsset(getAssets(), "fonts/Caviar_Dreams_Bold.ttf");
        tfCaviarDreams = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");

        tvFromPlace.setTypeface(tfCaviarDreams);
        tvToPlace.setTypeface(tfCaviarDreams);
    }

}

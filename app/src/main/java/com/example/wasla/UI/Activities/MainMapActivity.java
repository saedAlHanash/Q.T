package com.example.wasla.UI.Activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.wasla.AppConfig.SharedPreference;
import com.example.wasla.Helpers.View.FTH;
import com.example.wasla.Helpers.system.GetPermissions;
import com.example.wasla.Models.Request.DriverLocation;
import com.example.wasla.Models.Response.AvailableModel;
import com.example.wasla.Models.Response.SingleTrip;
import com.example.wasla.R;
import com.example.wasla.UI.Dialogs.CancelTripDialog;
import com.example.wasla.UI.Fragments.ProcessFragments.AcceptTripFragment;
import com.example.wasla.UI.Fragments.ProcessFragments.FindTripFragment;
import com.example.wasla.UI.Fragments.ProcessFragments.HaveOrderFragment;
import com.example.wasla.UI.Fragments.ProcessFragments.SafeTripFragment;
import com.example.wasla.UI.Fragments.ProcessFragments.ScheduledTripFragment;
import com.example.wasla.ViewModels.DriverViewModel;
import com.example.wasla.ViewModels.MapViewModel;
import com.example.wasla.ViewModels.UserViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.ButtCap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.ButterKnife;

import static com.example.wasla.AppConfig.Cods.CANCEL_TRIP;
import static com.example.wasla.AppConfig.Cods.LAT;
import static com.example.wasla.AppConfig.Cods.LNG;
import static com.example.wasla.AppConfig.Cods.MAIN_MAP_C;
import static com.example.wasla.AppConfig.Cods.REQUEST_CHECK_SETTINGS;
import static com.example.wasla.AppConfig.Cods.REQUEST_CODE_GPS;
import static com.example.wasla.AppConfig.SharedPreference.GET_MY_ID;
import static com.example.wasla.AppConfig.SharedPreference.IS_THERE_TRIP;
import static com.example.wasla.AppConfig.SharedPreference.REMOVE_CASH_TRIP;
import static com.example.wasla.Helpers.View.FTH.replaceFragment;
import static com.example.wasla.Helpers.View.NoteMessage.TOAST_ERROR;
import static com.example.wasla.Helpers.View.NoteMessage.TOAST_NO_INTERNET;
import static com.example.wasla.Helpers.system.ScreenHelper.hideStatusBar;

;

public class MainMapActivity extends AppCompatActivity {

    public GoogleMap googleMap;
    public PolylineOptions poly;

    public SupportMapFragment mapFragment;
    public DriverLocation location;
    Marker myCar;

    public boolean animateCameraLook = false;
    boolean thread = true;

    UserViewModel user = UserViewModel.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        hideStatusBar(this);

        // تهيئة الخريطة
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        location = new DriverLocation();

        if (GetPermissions.checkPermeationLocation(this))
            initMap();

        registerReceivers();

    }

// ___ from api  ____________________________________________________________________________

    /**
     * get trip info from api
     */
    private void getTripFromApi() {
        DriverViewModel.getInstance().getTripById(SharedPreference.GET_TRIP_ID());
        DriverViewModel.getInstance().tripByIdLiveData.observe(this, tripObserver);
    }

    /**
     * get point rout from google map api
     */
    private void getPointsGoogleApi(LatLng start, LatLng end) {

        MapViewModel.getINSTANCE().getRoadPoint(start, end);
        MapViewModel.getINSTANCE().routePointResultLiveData.observe(this, listPointsObserver);
    }

    /**
     * Check the trip if it has been accepted, or has already started,
     * or if it is still a order and has not yet been accepted
     * <p>
     * This method is called if there is a journey stored in the file
     */
    void checkStickyTrip() {

        if (IS_THERE_TRIP())
            getTripFromApi();
        else
            startFindTrip();
    }

    /**
     * تهيئة موقع السيارة الحقيقية من أثر طبعا الموقع بيوصلنا لما السائق بيصير متاح <br>
     * أنا عم أطلب الاتاحة بال سبلاش سكرين إذا كان في رحلةولما يصير متاح ببعت الموقع بالانتنت
     *
     * @param lat latitude
     * @param lng longitude
     */
    void initOnGetLocation(String lat, String lng) {

        //تحديث كائن الموقع
        location.setLatitud(Double.parseDouble(lat));
        location.setLongitud(Double.parseDouble(lng));

        //تحريك الكمرة للموقع الوارد
        animateCamera(location.getLatLing(), 11.0f);


        //اضافة ماركر السيارة
        myCar = addCustomMarkerWithTitle(location.getLatLing(), "", R.drawable.ic_car);

        //ال thread هنا من أجل كل فترة يطلب الموقع الجديد ويحرك الخريطة للموقع الجديد
        new Thread(() -> {
            while (thread) {

                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ignored) {
                }

                //طلب تحديث الموقع
                user.getMyLocation(user.locationLiveData == null, GET_MY_ID());

                runOnUiThread(() -> {
                    //تحديث موقع السيارة على الخريطة
                    user.locationLiveData.observe(com.example.wasla.UI.Activities.MainMapActivity.this, locationObserver);
                });
            }
        }).start();

    }

    private void tripCases(SingleTrip trip) {
        //اذا كانت الرحلة عادية
        if (trip.result.tripType == 0) {
            //حالة تقريبا مستحيلة لكن ممكنة الحدوث بشكل نادر جدا
            // في حالة كانت الرحلة ملغاة أو تم توصيلها
            if (trip.result.isDelved || trip.result.isCanceled) {
                REMOVE_CASH_TRIP();
                startFindTrip();
                return;
            }

            if (trip.result.isStarted) //الرحلة بدأت
                tripStarted(trip);

            else if (trip.result.isAccepted) // الرحلة تم قبولها ولم تبدأ
                tripAccepted(trip);

            else  //الرحلة لم تبدأ ولم يتم قبولها بعد اذا هي ما زالت طلب
                tripJustOrder(trip);
        }
        //الرحلة مجدولة
        else {
            animateCamera(trip.result.currentLocation.getLatLing(), 11.0f);
            FTH.replaceFragment(MAIN_MAP_C, this, new ScheduledTripFragment(trip));
        }
    }

    private void tripJustOrder(SingleTrip trip) {
        animateCamera(trip.result.currentLocation.getLatLing(), 11.0f);
        startFragment(new HaveOrderFragment(trip));
    }

    private void tripAccepted(SingleTrip trip) {
        if (trip.result.driverId == GET_MY_ID()) {
            //رسم طريق الزبون
            getPointRout(
                    trip.result.currentLocation.getLatLing(),
                    trip.result.destination.getLatLing(),
                    getResources().getColor(R.color.main_color),
                    getResources().getDimension(R.dimen._4sdp));

            startFragment(new AcceptTripFragment(trip, false));

        } else
            finishing();
    }

    private void tripStarted(SingleTrip trip) {
        if (trip.result.driverId == GET_MY_ID())
            startFragment(new SafeTripFragment(trip));
        else
            finishing();
    }

//____ observers ___________________________________________________________________

    //مراقب الرحلة
    private final Observer<Pair<SingleTrip, String>> tripObserver = pair -> {

        if (pair != null) {
            if (pair.first != null)
                tripCases(pair.first);
            else
                TOAST_ERROR(this, pair.second);
        } else
            TOAST_NO_INTERNET(this);

    };

    //مراقب الموقع الجديد
    private final Observer<Pair<AvailableModel, String>> locationObserver = pair -> {
        if (pair != null) {
            if (pair.first != null) {

                location.setLatitud(Double.parseDouble(pair.first.result.lat));
                location.setLongitud(Double.parseDouble(pair.first.result.lng));
                //مسح الماركر السابق
                myCar.remove();

                if (!animateCameraLook)
                    animateCamera(location.getLatLing(), googleMap.getCameraPosition().zoom);

                //اضافة ماركر جديد
                myCar = addCustomMarkerWithTitle(location.getLatLing(), "", R.drawable.ic_car);

                Log.d("SAEDD", "in observer: " + pair.first.toString());
            } else
                TOAST_ERROR(this, pair.second);
        } else
            TOAST_NO_INTERNET(this);

    };
    // مراقب نقاط الطريق
    private final Observer<List<LatLng>> listPointsObserver = point -> {
        if (point != null) {
            //اضافة النقاط للمستقيم
            for (int i = 0; i < point.size(); i++)
                poly.add(point.get(i));

            //رسم الخط
            googleMap.addPolyline(poly);
        }
    };

// ___ Receivers ____________________________________________________________________________

    //ريسيفر الغاء الرحلة
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            {
                if (intent.getAction().equals(CANCEL_TRIP)) {

                    //مسح الطريق من الخريطة
                    googleMap.clear();
                    //مسح الرحلة من الملف
                    REMOVE_CASH_TRIP();

                    Log.d("SAED_", "cancel receiver : ");

                    // new DoneEdit(MainMapActivity.this, "لقد تم الغاء الرحلة من قبل العميل ").show1();
                    new CancelTripDialog(com.example.wasla.UI.Activities.MainMapActivity.this).show();
                }
            }
        }
    };

// ___ Map ____________________________________________________________________________

    @SuppressLint("MissingPermission")
    public void initMap() {
        if (mapFragment != null)
            mapFragment.getMapAsync(googleMap -> {

                this.googleMap = googleMap;
                checkStickyTrip();

                if (getIntent().getExtras() != null) {

                    String lat = getIntent().getExtras().getString(LAT);
                    String lng = getIntent().getExtras().getString(LNG);

                    initOnGetLocation(lat, lng);

                }

            });
    }

    /**
     * to move map camera to any point you need
     *
     * @param latLng point in latitude and longitude
     */
    void animateCamera(LatLng latLng, float zoom) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    /**
     * تابع من أجل طلب نقاط الطريق من شان الرسم
     */
    public void getPointRout(LatLng start, LatLng end, int color, float width) {

        //تهيئة الطريق
        poly = new PolylineOptions();
        poly.color(color);
        poly.width(width);
        poly.startCap(new ButtCap());

//        //اضافة ماركر بالبداية والنهاية
//        googleMap.addMarker(new MarkerOptions().position(start));
//        googleMap.addMarker(new MarkerOptions().position(end));

        animateCamera(start, 14.0f);

        // FIXME: 11/04/2022  هون صار يرسم طريقين أنت شوف اذا فات للتطبيق ومعه رحلة مبلشة لازم ينرسم بس طريق واحد
        //طلب النقاط
        staticRout(start, end);

    }

    /**
     * تهيئة ماركر مخصص
     */
    public Marker addCustomMarkerWithTitle(LatLng latLng, String title, int drawableId) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title).icon(BitmapFromVector(drawableId));
        return googleMap.addMarker(markerOptions);
    }

    /**
     * من أجل أخذ أيقونة ك ماركر
     */
    public BitmapDescriptor BitmapFromVector(int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(this, vectorResId);

        // below line is use to set bounds to our vector drawable.
        assert vectorDrawable != null;
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /**
     * to draw rout in map with tow point
     *
     * @param c start point
     * @param d end point
     */
    void staticRout(LatLng c, LatLng d) {

        googleMap.addMarker(new MarkerOptions().position(c));
        googleMap.addMarker(new MarkerOptions().position(d));

        GoogleDirection.withServerKey(MapViewModel.google_map_api_key)
                .from(c)
                .and(location.getLatLing())
                .to(d)
                .transportMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(@Nullable Direction direction) {
                        List<Route> l = direction.getRouteList();
                        for (int i = 0; i < l.size(); i++) {
                            List<Leg> legs = l.get(i).getLegList();
                            if (legs.size() > 1) {
                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(
                                        getApplicationContext(),
                                        legs.get(0).getDirectionPoint(),
                                        2,
                                        Color.BLACK);

                                PolylineOptions polylineOptions1 = DirectionConverter.createPolyline(
                                        getApplicationContext(),
                                        legs.get(1).getDirectionPoint(),
                                        4,
                                        getResources().getColor(R.color.main_color));

                                googleMap.addPolyline(polylineOptions);
                                googleMap.addPolyline(polylineOptions1);

                            }
                        }

//                        ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
//                        PolylineOptions polylineOptions = DirectionConverter.createPolyline(getApplicationContext(), directionPositionList, 5, Color.YELLOW);
//                        googleMap.addPolyline(polylineOptions);
                    }

                    @Override
                    public void onDirectionFailure(@NonNull Throwable t) {

                    }
                });
    }

// ___ others ____________________________________________________________________________

    //تهيئة الريسفرات
    void registerReceivers() {

        // تسجيل متلقي الغاء الرحلة
        registerReceiver(receiver, new IntentFilter(CANCEL_TRIP));
    }

    //الغاء الريسيفرات
    void unregisterReceivers() {
        unregisterReceiver(this.receiver);
    }

    //الغاء المراقبات
    void removeObservers() {
        if (DriverViewModel.getInstance().tripByIdLiveData != null)
            DriverViewModel.getInstance().tripByIdLiveData.removeObserver(tripObserver);

        if (user.locationLiveData != null)
            user.locationLiveData.removeObserver(locationObserver);

        if (MapViewModel.getINSTANCE().routePointResultLiveData != null)
            MapViewModel.getINSTANCE().routePointResultLiveData.removeObserver(listPointsObserver);

    }

// ___ start or finishing ____________________________________________________________________________

    /**
     * starting  process fragment
     *
     * @param fragment @{@link SafeTripFragment} or @{@link AcceptTripFragment}  or @{@link HaveOrderFragment}
     */
    void startFragment(Fragment fragment) {
        //تبديل للواجهة المناسبة
        replaceFragment(MAIN_MAP_C, this, fragment);
    }

    /**
     * to start @{@link FindTripFragment} in this activity
     */
    private void startFindTrip() {
        replaceFragment(MAIN_MAP_C, this, new FindTripFragment());
    }

    /**
     * finishing this activity and remove trip from file
     */
    private void finishing() {
        REMOVE_CASH_TRIP();
        startActivity(new Intent(this, SecondActivity.class));
        this.finish();
    }

// ___ Base in activity ____________________________________________________________________________

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //اغلاق الثريد تبع تحريك الخريطة
        thread = false;

        removeObservers();

        try {
            unregisterReceivers();
        } catch (Exception ignored) {
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS)
            if (resultCode != -1) {
                if (resultCode == 0) {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (GetPermissions.checkPermeationLocation(this))
                    initMap();
            }
    }

    public void onRequestPermissionsResult(int paramInt, String @NotNull [] paramArrayOfString, int @NotNull [] paramArrayOfint) {
        super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
        if (paramInt == REQUEST_CODE_GPS) {
            if (paramArrayOfint.length <= 0 || paramArrayOfint[0] != 0) {
                Toast.makeText(this, "لا يمكن المتابعة بدون قبول صلاحيات الموقع", Toast.LENGTH_SHORT).show();
            } else
                initMap();
        }
    }
}
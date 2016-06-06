package footprint.footprint;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;

import static android.media.ExifInterface.TAG_DATETIME;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //변수는 여기에서(?)
    //private WebView webView = null;

    private View writeView = null;
    private View mainView = null;
    private View articleView = null;
    private View calendarView = null;
    private View buttonView = null;
    private View settingView = null;
    protected static View progressView = null;
    protected static View progressBar = null;

    private TextView articleDate = null;
    private TextView articleTime = null;
    private TextView articleText = null;
    private ImageView articleImageButton = null;

    private FloatingActionsMenu writeFab = null;
    private FloatingActionButton recordOnFab = null;
    private FloatingActionButton recordOffFab = null;
    private FloatingActionButton zoomFab = null;
    private FloatingActionButton goMainFabInWritePage = null;
    private FloatingActionButton goMainFabInArticlePage = null;
    private FloatingActionButton saveFab = null;
    private FloatingActionButton rcmdFab = null;


    private static GoogleMap map = null;
    private static SupportMapFragment mapFragment = null;
    private static LocationManager manager = null;
    private static GPSListener gpsListener = null;
    private static PolylineOptions pOptions = new PolylineOptions();
    private static PolylineOptions pastPolyOptions = new PolylineOptions();
    private static Double latitude = null;
    private static Double longitude = null;
    private static float gpsLocation[] = new float[2];


    protected static SupportMapFragment articleMapFragment;
    protected static GoogleMap articleMap;


    protected static HashMap<Object,String> markerIdHash = new HashMap<>();
    protected static LinkedList<LatLng> polylineHash = new LinkedList<>();
    protected static LinkedList<LBRS> lbrsList = null;
    protected static HashMap<Object,String> lbrsHash = new HashMap<>();

    protected static ArticleData articleData = null;


    private ImageView imageView = null;
    private File imageFile = null;
    private ExifInterface exif = null;
    private EditText inputText = null;

    private int polyColor;

    private final static int GPS_TIME_CYCLE = 1000;
    private final static int GPS_DISTANCE_CYCLE = 3;

    private final static int GO_CAMERA = 1;
    private final static int GO_GALLARY = 2;

    private final static int DB_ID = 0;
    private final static int DB_DATE = 1;
    private final static int DB_TIME = 2;
    private final static int DB_ARTICLE = 3;
    private final static int DB_FILENAME = 4;
    private final static int DB_LATITUDE = 5;
    private final static int DB_LONGITUDE = 6;

    private final static int POLYLINE_ID = 0;
    private final static int POLYLINE_DATE = 1;
    private final static int POLYLINE_LATITUDE = 2;
    private final static int POLYLINE_LONGITUDE = 3;


    private final static int MARKER_GREEN = 10;
    private final static int MARKER_RAINBOW = 20;

    private boolean recordOn = false;
    private boolean zoomOn = false;
    private static int zoomLevel = 16;


    private final static int RADIAN_100M = 0;
    private final static int RADIAN_500M = 1;
    private final static int RADIAN_1KM = 2;
    private final static int RADIAN_3KM = 3;
    private final static int RADIAN_5KM = 4;


    private final static int MIN_RCMD_0 = 0;
    private final static int MIN_RCMD_10 = 1;
    private final static int MIN_RCMD_20 = 2;
    private final static int MIN_RCMD_30 = 3;
    private final static int MIN_RCMD_40 = 4;


    private final static int TIMESPECTRUM_OFF = 0;
    private final static int TIMESPECTRUM_ON = 1;

    private static float radian = 0.0f;
    private static int radianSelector = RADIAN_100M;
    private static int minRcmd = 0;
    private static int minRcmdSelector = MIN_RCMD_0;
    private static int timeSpectrum = TIMESPECTRUM_OFF;
    private static Button resetButton = null;

    private static Spinner rcmdSpinner = null;
    private static Spinner radianSpinner = null;


    private static DBOpenHelper helper;


    private LatLng mainMarkerPosition = null;
    private static Date today = new Date();

    private NotificationManager notiManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startActivity(new Intent(this, PreLoadActivity.class));
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);




        //DB 설정
        helper = new DBOpenHelper(this, "footprint.db", null, 1);
        //helper.setWriteAheadLoggingEnabled(false);


        //메인 페이지 초기화
        mainView = findViewById(R.id.mainPage);
        mainView.setVisibility(View.VISIBLE);
        writeView = findViewById(R.id.writePage);
        writeView.setVisibility(View.GONE);
        articleView = findViewById(R.id.articlePage);
        articleView.setVisibility(View.GONE);
        calendarView = findViewById(R.id.calendar_view);
        calendarView.setVisibility(View.VISIBLE);
        buttonView = findViewById(R.id.buttonPage);
        buttonView.setVisibility(View.VISIBLE);
        settingView = findViewById(R.id.settingPage);
        settingView.setVisibility(View.GONE);
        progressView = findViewById(R.id.progressPage);
        progressView.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progressBar);


        //글 페이지 초기화
        articleDate = (TextView) findViewById(R.id.articleDate);
        articleTime = (TextView) findViewById(R.id.articleTime);
        articleText = (TextView) findViewById(R.id.articleText);
        articleImageButton = (ImageButton) findViewById(R.id.articleImageButton);



        //버튼 초기화
        //줌 팹 버튼 생성
        zoomFab = (FloatingActionButton) findViewById(R.id.mapZoom);
        zoomFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomOnOff();
            }
        });

        //메인페이지 가는 팹 버튼 생성
        goMainFabInWritePage = (FloatingActionButton) findViewById(R.id.goMainFabInWritePage);
        goMainFabInWritePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        goMainFabInArticlePage = (FloatingActionButton) findViewById(R.id.goMainFabInArticlePage);
        goMainFabInArticlePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //저장 팹 버튼 생성
        saveFab = (FloatingActionButton) findViewById(R.id.saveFab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSavePressed();
            }
        });

        //추천 팹 버튼 생성
        rcmdFab = (FloatingActionButton) findViewById(R.id.rcmdFab);
        rcmdFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRcmdPressed();
            }
        });

        //기록 팹 버튼 생성
        recordOnFab = (FloatingActionButton) findViewById(R.id.recordOnState);
        recordOffFab = (FloatingActionButton) findViewById(R.id.recordOffState);
        recordOnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordOnOff();
            }
        });
        recordOffFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordOnOff();
            }
        });

        //글쓰기 팹 메뉴 생성
        writeFab = (FloatingActionsMenu) findViewById(R.id.writeFab);
        FloatingActionButton goGallary = (FloatingActionButton) findViewById(R.id.goGallary);
        FloatingActionButton goCamera = (FloatingActionButton) findViewById(R.id.goCamera);

        goGallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeFab.collapse();
                //Toast.makeText(MainActivity.this ,"GO GALLARY!", Toast.LENGTH_SHORT).show();
                onNavGallaryPressed();
            }
        });
        goCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeFab.collapse();
                //Toast.makeText(MainActivity.this ,"GO CAMERA!", Toast.LENGTH_SHORT).show();
                onNavCameraPressed();
            }
        });


        //맵 초기화
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        map = mapFragment.getMap();
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setAllGesturesEnabled(false);

        //마커 클릭 이벤트 설정
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                dbSelectArticle(markerIdHash.get(marker));
                goArticlePage();
                return true;
            }
        });
        startLocationService();


        //글상에서 보이는 맵 초기화 및 마커 클릭 이벤트 설정
        articleMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.articleMap);
        articleMap = articleMapFragment.getMap();
        articleMap.getUiSettings().setMyLocationButtonEnabled(false);

        articleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if (marker.getPosition() != mainMarkerPosition) {
                    try {
                        Log.d("LBRS_HASH", lbrsHash.get(marker));
                        articleData = new HttpGetArticleTask().execute(lbrsHash.get(marker)).get();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    viewArticle(articleData);
                }
                return true;
            }

        });




        imageView = (ImageView) findViewById(R.id.imageView);
        inputText = (EditText) findViewById(R.id.inputText);


        //슬라이드 바

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //설정화면 설정
        rcmdSpinner = (Spinner)findViewById(R.id.recommendSpinner);
        radianSpinner = (Spinner)findViewById(R.id.radianSpinner);
        resetButton = (Button) findViewById(R.id.optionReset);
        getPreferences();

        displayMapSubInfo(CalendarView.selectedDateInfo);
    }


    /**
     * 쉐어드프리퍼런스를 이용해서 옵션 저장
     */
    // 값 불러오기
    private void getPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        timeSpectrum = Integer.parseInt(pref.getString("timeSwitch", "0"));
        radianSelector = Integer.parseInt(pref.getString("radianSelector", "0"));
        minRcmdSelector = Integer.parseInt(pref.getString("rcmdSelector", "0"));



    }

    // 값 저장하기
    private void savePreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("timeSwitch", String.valueOf(timeSpectrum));
        editor.putString("radianSelector", String.valueOf(radianSelector));
        editor.putString("rcmdSelector", String.valueOf(minRcmdSelector));
        editor.apply();
    }


    // 값(ALL Data) 삭제하기
    private void removeAllPreferences(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {

            Toast.makeText(this,"로그인 기능 미구현", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_setting) {
            //Toast.makeText(this,"설정 버튼", Toast.LENGTH_SHORT).show();

            setSettingView();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setSettingView() {

        radianSpinner.setSelection(radianSelector);
        rcmdSpinner.setSelection(minRcmdSelector);

        settingView.setVisibility(View.VISIBLE);
        settingView.setClickable(true);

        radianSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case RADIAN_100M:
                        radian = 0.1f;
                        radianSelector = RADIAN_100M;
                        break;
                    case RADIAN_500M:
                        radian = 0.5f;
                        radianSelector = RADIAN_500M;
                        break;
                    case RADIAN_1KM:
                        radian = 1.0f;
                        radianSelector = RADIAN_1KM;
                        break;
                    case RADIAN_3KM:
                        radian = 3.0f;
                        radianSelector = RADIAN_3KM;
                        break;
                    case RADIAN_5KM:
                        radian = 5.0f;
                        radianSelector = RADIAN_5KM;
                        break;
                    default:
                        radian = 0.1f;
                        radianSelector = RADIAN_100M;
                }
                savePreferences();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        rcmdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                switch (position) {
                    case MIN_RCMD_0:
                        minRcmd = 0;
                        minRcmdSelector = MIN_RCMD_0;
                        break;
                    case MIN_RCMD_10:
                        minRcmd = 10;
                        minRcmdSelector = MIN_RCMD_10;
                        break;
                    case MIN_RCMD_20:
                        minRcmd = 20;
                        minRcmdSelector = MIN_RCMD_20;
                        break;
                    case MIN_RCMD_30:
                        minRcmd = 30;
                        minRcmdSelector = MIN_RCMD_30;
                        break;
                    case MIN_RCMD_40:
                        minRcmd = 40;
                        minRcmdSelector = MIN_RCMD_40;
                        break;
                    default:
                        minRcmd = 0;
                        minRcmdSelector = MIN_RCMD_0;
                }
                savePreferences();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Switch timeSwitch = (Switch) findViewById(R.id.timeSwitch);

        if(timeSpectrum==1)
            timeSwitch.setChecked(true);
        else
            timeSwitch.setChecked(false);

        timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    timeSpectrum = TIMESPECTRUM_ON;
                } else {
                    timeSpectrum = TIMESPECTRUM_OFF;
                }
                savePreferences();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAllPreferences();
                getPreferences();
                setSettingView();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (settingView.getVisibility() == View.VISIBLE) {
            savePreferences();
            settingView.setVisibility(View.GONE);

        }else if(articleView.getVisibility()==View.VISIBLE) {
            mainView.setVisibility(View.VISIBLE);
            articleView.setVisibility(View.GONE);
            buttonView.setVisibility(View.VISIBLE);
            writeViewReset();
        } else if(writeView.getVisibility() == View.VISIBLE)  {
            mainView.setVisibility(View.VISIBLE);
            writeView.setVisibility(View.GONE);
            buttonView.setVisibility(View.VISIBLE);
            writeViewReset();
        }
        else if(calendarView.getVisibility()==View.GONE) {
            calendarView.setVisibility(View.VISIBLE);
        }
        else {
            exitApplication();
        }

    }
    public void exitApplication() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_app)
                .setTitle(this.getString(R.string.exit_title)) // 제목부분 텍스트
                .setMessage(this.getString(R.string.exit_message)) // 내용부분 텍스트
                .setPositiveButton(this.getString(android.R.string.yes), //승인버튼을 눌렀을때..
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick( DialogInterface dialog, int which )
                            {
                                moveTaskToBack(true);
                                finish(); //종료
                            }
                        }
                ).setNegativeButton(this.getString(android.R.string.no), null ).show(); //취소버튼을 눌렀을때..
        return ;
    }

    public void writeViewReset(){

        imageFile = null;
        imageView.setImageResource(0);
        gpsLocation[0] = gpsLocation[1] = 0.0f;
        inputText.setText(null);
    }


    public void goWritePage() {
        writeView.setVisibility(View.VISIBLE);
        mainView.setVisibility(View.GONE);
        buttonView.setVisibility(View.GONE);

    }


    public void goArticlePage() {
        if(writeView.getVisibility() == View.VISIBLE){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputText.getWindowToken(), 0);
            articleView.setVisibility(View.VISIBLE);
            writeView.setVisibility(View.GONE);

            //글쓰기 페이지에서는 팹버튼들이 이미 숨겨져있으므로 다시 숨길 필요가 없다

        }
        else {
            articleView.setVisibility(View.VISIBLE);
            mainView.setVisibility(View.GONE);
            buttonView.setVisibility(View.GONE);



        }
    }


    /**
     * GPS 관련 메소드 시작
     *
     */
    private void startLocationService() {
        // 위치 관리자 객체 참조
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        polyColor = colorSelector(Calendar.getInstance());
        boolean isGps = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!isGps) {
            Toast.makeText(this, "어플리케이션을 이용하기 위해서는 GPS를 활성화해야합니다.", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
        }
        //스타팅포인트는 학교 좌표
        // 위치 정보를 받을 리스너 생성
        gpsListener = new GPSListener();
        long minTime = GPS_TIME_CYCLE;
        float minDistance = GPS_DISTANCE_CYCLE;

        try {
            // GPS를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 네트워크를 이용한 위치 요청
            manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    gpsListener);

            // 위치 확인이 안되는 경우에도 최근에 확인된 위치 정보 먼저 확인
            Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();

                //Toast.makeText(getApplicationContext(), "Last Known Location : " + "Latitude : " + latitude + "\nLongitude:" + longitude, Toast.LENGTH_LONG).show();
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        if(latitude!=null && longitude!=null) {
            LatLng startPoint = new LatLng(latitude, longitude);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, zoomLevel));
        }
        //Toast.makeText(getApplicationContext(), "위치 확인이 시작되었습니다. 로그를 확인하세요.", Toast.LENGTH_SHORT).show();

    }

    /**
     * 리스너 클래스 정의
     */
    private class GPSListener implements LocationListener {
        /**
         * 위치 정보가 확인될 때 자동 호출되는 메소드
         */

        public void onLocationChanged(Location location) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();


            String msg = "Latitude : " + latitude + "\nLongitude:" + longitude;
            Log.i("GPSListener", msg);
            //String accuracy = "정확도 : " + location.getAccuracy();

            //지도에 내 위치 표시
            try {
                map.setMyLocationEnabled(true);
            } catch (SecurityException ex) {
                ex.printStackTrace();
            }

            if (location.getAccuracy() < 50.0) {

                pOptions.geodesic(true);
                LatLng latlng = new LatLng(latitude, longitude);
                pOptions.add(latlng).color(polyColor);
                if(recordOn) {
                    dbInsertPolyline(latlng);
                }
                if(dateFrame(CalendarView.selectedDateInfo).equals(dateFrame(today)) && recordOn) {
                    map.addPolyline(pOptions);
                }
            }
            //맵이 전체화면이 아니고, 달력에서 선택한 날짜가 오늘일 경우 동작
            if(dateFrame(CalendarView.selectedDateInfo).equals(dateFrame(today)) && !zoomOn) {
                showCurrentLocation(latitude, longitude);
            }
        }
        /**
         * 현 위치를 중심으로 화면에 지도를 띄워주는 메소드
         */
        private void showCurrentLocation(Double latitude, Double longitude) {
            LatLng curPoint = new LatLng(latitude, longitude);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint,zoomLevel));
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }


    /**
     *  카메라를 선택하면 동작하는 메소드
     */
    public void onNavCameraPressed() {
        String path = Environment.getExternalStorageDirectory()+"/DCIM/Camera/temp_image.jpg";
        imageFile = new File(path);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, GO_CAMERA);
        }
    }


    /**
     *  갤러리를 선택하면 동작하는 메소드
     */
    public void onNavGallaryPressed() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GO_GALLARY);
    }


    /**
     * Intent 후처리 메소드
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            if (requestCode == GO_CAMERA) {
                //카메라 촬영 선택했을 경우

                //Toast.makeText(this,"후처리 시작",Toast.LENGTH_SHORT).show();
                //임시로 생성한 이미지 이름을 다시 변경
                imageRename();

                //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                BitmapFactory.Options options = new BitmapFactory.Options();
                //Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();

                options.inSampleSize = 2;
                //Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();


                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(imageFile)));
                //Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();

                //이미지 데이터를 비트맵으로 받아온다.
                Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
                //Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();

                //화면에 뿌리기
                imageView.setImageBitmap(bitmap);

                goWritePage();
            }
            else if (requestCode == GO_GALLARY) {
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    imageFile = new File(getPath(data.getData()));

                    //이미지 데이터를 비트맵으로 받아온다.
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    //화면에 뿌리기
                    imageView.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                goWritePage();
            }
            else {
                Toast.makeText(this, "Activity is canceled",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    /**
     * 파일 이름 재설정 작업
     * 파일 생성 시분초로 이름을 변경시킨다 - 갤럭시 시리즈 기준
     */
    public void imageRename() {
        try {
            exif = new ExifInterface(imageFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show();
        }

        String fileRename;
        fileRename = exif.getAttribute(TAG_DATETIME);
        fileRename = fileRename.replace(":", "");
        fileRename = fileRename.replace(" ", "_");
        File imageNameOrigin =
                new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/DCIM/Camera/", "temp_image.jpg");
        File imageNameChanged =
                new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/DCIM/Camera/", fileRename + ".jpg");

        imageNameOrigin.renameTo(imageNameChanged);
        imageFile = imageNameChanged;
        //exif = null;
        //파일 이름 재설정 작업 완료
    }


    /**
     * 저장 (및 업로드 처리)
     */
    public void onSavePressed(){
        if(imageFile == null) {
            Toast.makeText(this,"이미지를 먼저 업로드 하세요", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                exif = new ExifInterface(imageFile.getAbsolutePath());
                //db_insert();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            }

            if(!exif.getLatLong(gpsLocation)) {
                Toast.makeText(this,"GPS 정보가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
            }
            else {
                //Toast.makeText(this, gpsLocation[0] + ", " + gpsLocation[1], Toast.LENGTH_SHORT).show();
                dbInsertArticle();
                Log.d("INSERT", "SUCCESS");
                dbSelectArticle(null);
                Log.d("SELECT", "SUCCESS");
                goArticlePage();
                writeViewReset();
            }
        }
    }

    public void onRcmdPressed() {

        final String IP_NONE = "N/A";
        final String WIFI_DEVICE_PREFIX = "eth";

        String LocalIP = IP_NONE;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        if( LocalIP.equals(IP_NONE) )
                            LocalIP = inetAddress.getHostAddress().toString();
                        else if( intf.getName().startsWith(WIFI_DEVICE_PREFIX) )
                            LocalIP = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            Log.e("IP_CHECK", "getLocalIpAddress Exception:" + e.toString());
        }
        Log.d("IP_CHECK",LocalIP);
        try {
            String checker = new HttpRcmdTask().execute(LocalIP,articleData.date,articleData.time,articleData.article).get();

            if(checker.equals("EXIST")) {
                Toast.makeText(this,"이미 추천한 글입니다.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"추천하였습니다.", Toast.LENGTH_SHORT).show();
            }

        }catch(InterruptedException e) {
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 요일마다 색깔을 다르게 구분
     * 일단은 어플을 실행했을때 한번만 실행되게 해놓았음
     */
    public static int colorSelector(Calendar cal){
        switch(cal.get(Calendar.DAY_OF_WEEK)){
            case 1:
                return Color.RED;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.CYAN;
            case 5:
                return Color.BLUE;
            case 6:
                return Color.MAGENTA;
            case 7:
                return Color.DKGRAY;
            default:
                return Color.BLACK;
        }
    }


    /**
     * CalendarView 클래스로부터 받은 날짜값으로 Marker 리스트 뽑아 출력하는 메소드
     * CalendarView 클래스로부터 받은 날짜값으로 Polyline 을 출력하는 메소드
     *
     */
    public static void displayMapSubInfo(Date date) {
        //날짜를 바꿀때마다 id리스트 초기화

        markerIdHash.clear();
        polylineHash.clear();
        pastPolyOptions =  new PolylineOptions();
        map.clear();
        dbSelectMarkerByDate(date);
        dbSelectPolylineByDate(date);
    }

    public void recordOnOff() {
        if(recordOn) {
            recordOffFab.setVisibility(View.VISIBLE);
            recordOnFab.setVisibility(View.GONE);
            recordOn = false;
        }
        else {
            recordOnFab.setVisibility(View.VISIBLE);
            recordOffFab.setVisibility(View.GONE);
            recordOn = true;
        }

    }
    public void zoomOnOff() {
        if(!zoomOn) {
            calendarView.setVisibility(View.GONE);
            findViewById(R.id.shadowBar).setVisibility(View.GONE);
            map.getUiSettings().setMyLocationButtonEnabled(true);
            map.getUiSettings().setAllGesturesEnabled(true);
            zoomOn = true;
        }
        else {
            calendarView.setVisibility(View.VISIBLE);
            findViewById(R.id.shadowBar).setVisibility(View.VISIBLE);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.getUiSettings().setAllGesturesEnabled(false);
            zoomOn = false;
        }
    }


    public void viewArticle(ArticleData values) {

        dbSelectMarker(values);
        //실제 필요한 작업 처리
        articleDate.setText(values.date);
        articleTime.setText(values.time);
        articleText.setText(values.article);

        BitmapFactory.Options options = new BitmapFactory.Options();
        //Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();


        final Double lati = values.latitude;
        final Double longi = values.longitude;

        articleImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lati, longi)));
            }
        });
        articleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        articleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(values.latitude, values.longitude), 15));
        mainMarkerPosition = new LatLng(values.latitude,values.longitude);

        options.inSampleSize = 4;

        if(values.filename != null) {
            String filePath = Environment.getExternalStorageDirectory()+"/DCIM/Camera/" + values.filename;
            imageFile = new File(filePath);
            Bitmap bitmap = null;
            if(imageFile.exists()) {
                //이미지 데이터를 비트맵으로 받아온다.
                bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
                //화면에 뿌리기
            }
            else {
                try {
                    bitmap = new HttpImageDownTask().execute(values.filename).get();

                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
            articleImageButton.setImageBitmap(bitmap);

        }

        displayMapSubInfo(CalendarView.selectedDateInfo);

    }


    /**
     * 데이터베이스 SQL 설정
     *
     * http://kuroikuma.tistory.com/75 참조
     *
     */
    public void dbInsertArticle() {

        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //db.insert의 매개변수인 values가 ContentValues 변수이므로 그에 맞춤
        //데이터 삽입은 put을 이용한다.

        String datetime = exif.getAttribute(TAG_DATETIME);
        datetime = datetime.replaceFirst(":","-");
        datetime = datetime.replaceFirst(":", "-");

        String txtChecker = inputText.getText().toString();
        if(txtChecker.replace(" ", "").equals("")) {
            inputText.setText("내용없음");
        }

        ArticleData values = new ArticleData();


        values.date = datetime.substring(0, 10);
        values.time = datetime.substring(11, 19);
        values.article = inputText.getText().toString();
        values.filename = imageFile.getName();
        values.latitude = gpsLocation[0];
        values.longitude = gpsLocation[1];

        /** 업로드 스레드
         *
         */
        new HttpUploadTask().execute(
                imageFile.getAbsolutePath(),
                String.valueOf(0),
                values.date,
                values.time,
                values.article,
                values.filename,
                String.valueOf(values.latitude),
                String.valueOf(values.longitude));


        //Toast.makeText(this,"DB_INSERT_SUCCESS",Toast.LENGTH_SHORT).show();
        contentValues.put("date", values.date);
        contentValues.put("time", values.time);
        contentValues.put("article", values.article);
        contentValues.put("filename", values.filename);
        contentValues.put("latitude", values.latitude);
        contentValues.put("longitude", values.longitude);
        db.insert("footprint", null, contentValues);

        db.close();

    }

    public void dbSelectArticle(String idCode) {

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor result;

        ArticleData values = new ArticleData();

        if(idCode == null) {
            result = db.rawQuery("SELECT footprint.* FROM footprint;", null);
        }
        else {
            result = db.rawQuery("SELECT footprint.* FROM footprint WHERE _id = " + Integer.parseInt(idCode) + ";",null);
        }
        result.moveToLast();
        Log.d("ARTICLE", "DB_SEARCH_SUCCESS!");
        Log.d("ID_MATCH", idCode + " " + result.getInt(DB_ID));


        values.id = result.getInt(DB_ID);
        values.date = result.getString(DB_DATE);
        values.time = result.getString(DB_TIME);
        values.article = result.getString(DB_ARTICLE);
        values.filename = result.getString(DB_FILENAME);
        values.latitude = result.getDouble(DB_LATITUDE);
        values.longitude = result.getDouble(DB_LONGITUDE);

        db.close();

        viewArticle(values);

    }

    /**
     * 메인 페이지에서의 마커 표시
     * @param date
     */
    public static void dbSelectMarkerByDate (Date date) {


       SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT footprint.* FROM footprint WHERE date = \'" +dateFrame(date) + "\';";

        Cursor result = db.rawQuery(sql, null);
        if(result.getCount() > 0) {

            result.moveToFirst();
            do {
                markerIdHash.put(map.addMarker(new MarkerOptions().position(
                                new LatLng(result.getDouble(DB_LATITUDE), result.getDouble(DB_LONGITUDE))).icon(BitmapDescriptorFactory.fromResource(R.drawable.main_marker_icon))),
                        String.valueOf(result.getInt(DB_ID)));
            } while (result.moveToNext());

            if(!dateFrame(CalendarView.selectedDateInfo).equals(dateFrame(today))) {
                Log.d("DATE", dateFrame(CalendarView.selectedDateInfo) + "=" + dateFrame(today));
                result.moveToFirst();
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(result.getDouble(DB_LATITUDE), result.getDouble(DB_LONGITUDE)), zoomLevel));
            }

        }
        result.close();
        db.close();

    }

    /**
     * Article 페이지에서 주변 마커 표시
     * @param values
     */
    public void dbSelectMarker (ArticleData values) {

        lbrsHash.clear();
        articleMap.clear();

        String minTime;
        String maxTime;

        int time  = Integer.parseInt(values.time.substring(0,1));
        if(time >= 18) {
            minTime = "18:00:00";
            maxTime = "23:59:59";
        }
        else if (time >= 12) {
            minTime = "12:00:00";
            maxTime = "17:59:59";
        }
        else if (time >= 6) {
            minTime = "06:00:00";
            maxTime = "11:59:59";
        }
        else {
            minTime = "00:00:00";
            maxTime = "05:59:59";
        }

        try {
            lbrsList = new HttpLBRSTask().execute(String.valueOf(values.latitude), String.valueOf(values.longitude), String.valueOf(radian), String.valueOf(minRcmd),String.valueOf(timeSpectrum),minTime,maxTime).get();

        }catch(Exception e) {
            e.printStackTrace();
        }
        BitmapDescriptor bitmapDescriptor;

        //Log.d("HASH_LBRS", String.valueOf(lbrsList.size()));
        if(lbrsList.size() > 0) {

            for (int i = 0; i < lbrsList.size(); i++) {
                if(lbrsList.get(i).cnt > MARKER_RAINBOW) {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.location_icon_rainbow_24dp);
                }
                else if(lbrsList.get(i).cnt > MARKER_GREEN) {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.location_icon_green_24dp);
                }
                else {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.location_icon_red_24dp);
                }

                lbrsHash.put(articleMap.addMarker(
                                new MarkerOptions().position(new LatLng(lbrsList.get(i).latitude, lbrsList.get(i).longitude)).icon(bitmapDescriptor)),
                        String.valueOf(lbrsList.get(i).id));

                Log.d("HASH_LBRS", lbrsList.get(i).latitude + " " + lbrsList.get(i).longitude + " " + lbrsList.get(i).id);
            }
        }

        return ;
    }

    public static void dbInsertPolyline(LatLng latlng) {
       SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //db.insert의 매개변수인 values가 ContentValues 변수이므로 그에 맞춤
        //데이터 삽입은 put을 이용한다.


        values.put("date",dateFrame(today));
        values.put("latitude", latlng.latitude);
        values.put("longitude", latlng.longitude);
        db.insert("polyline", null, values);
        db.close();
    }


    public static void dbSelectPolylineByDate(Date date) {

       SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT polyline.* FROM polyline WHERE date = \'" + dateFrame(date) + "\' ORDER BY _id ASC;";

        Cursor result = db.rawQuery(sql, null);
        LatLng latlng;

        if(result.getCount() > 0) {

            Calendar cal = Calendar.getInstance();
            cal.set(date.getYear() + 1900, date.getMonth(), date.getDate());


            result.moveToFirst();
            do {
                latlng = new LatLng(result.getDouble(POLYLINE_LATITUDE),result.getDouble(POLYLINE_LONGITUDE));
                polylineHash.add(latlng);
            } while (result.moveToNext());
            pastPolyOptions.addAll(polylineHash).color(colorSelector(cal));
            map.addPolyline(pastPolyOptions);
        }

        result.close();
        db.close();
    }


    public static void dbDelete(int id) {

       SQLiteDatabase db = helper.getWritableDatabase();
        db.rawQuery("DELETE FROM footprint WHERE _id = " + id + ";", null);


    }

    public void dbSelect() {
       SQLiteDatabase db = helper.getReadableDatabase();
        //Cursor c = db.query();

    }

    /**
     * date를 문자열로 출력해주는 메소드
     * @param date
     * @return String
     */
    public static String dateFrame(Date date) {

        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDate();
        return String.format("%04d-%02d-%02d", year + 1900, month+1,day);
    }



    /**
     * 기록중 노티바 띄우기
     */
    public void onBtnNotification() {
        //알림(Notification)을 관리하는 NotificationManager 얻어오기
        notiManager= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);


        //알림(Notification)을 만들어내는 Builder 객체 생성
        //API 11 버전 이하도 지원하기 위해 NotificationCampat 클래스 사용
        //만약 minimum SDK가 API 11 이상이면 Notification 클래스 사용 가능
        Notification.Builder builder = new Notification.Builder(this);

        //Notification.Builder에게 Notification 제목, 내용, 이미지 등을 설정//////////////////////////////////////

        builder.setSmallIcon(R.drawable.ic_app);//상태표시줄에 보이는 아이콘 모양
        builder.setTicker("FootPrint"); //알림이 발생될 때 잠시 보이는 글씨

        //상태바를 드래그하여 아래로 내리면 보이는 알림창(확장 상태바)의 아이콘 모양 지정
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_app));

        builder.setContentTitle("FootPrint");    //알림창에서의 제목
        builder.setContentText("경로 추적중입니다");   //알림창에서의 글씨

        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        Notification notification= builder.build();   //Notification 객체 생성

        notification.flags |= Notification.FLAG_NO_CLEAR;

        notiManager.notify(1, notification);             //NotificationManager가 알림(Notification)을 표시

    }


}

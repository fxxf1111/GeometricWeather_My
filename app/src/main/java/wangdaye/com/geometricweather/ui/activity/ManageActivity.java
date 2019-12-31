package wangdaye.com.geometricweather.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.View;

import java.util.List;

import wangdaye.com.geometricweather.R;
import wangdaye.com.geometricweather.basic.GeoActivity;
import wangdaye.com.geometricweather.basic.model.location.Location;
import wangdaye.com.geometricweather.db.DatabaseHelper;
import wangdaye.com.geometricweather.main.MainActivity;
import wangdaye.com.geometricweather.main.fragment.LocationManageFragment;
import wangdaye.com.geometricweather.utils.helpter.IntentHelper;

/**
 * Manage activity.
 * */

public class ManageActivity extends GeoActivity {

    private CoordinatorLayout container;
    private LocationManageFragment manageFragment;

    public static final int SEARCH_ACTIVITY = 1;
    public static final int SELECT_PROVIDER_ACTIVITY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //删除当前位置
        delCurLocal();
        setContentView(R.layout.activity_manage);

        container = findViewById(R.id.activity_manage_container);

        manageFragment = new LocationManageFragment();
        manageFragment.setRequestCodes(SEARCH_ACTIVITY, SELECT_PROVIDER_ACTIVITY);
        manageFragment.setOnLocationListChangedListener(new LocationManageFragment.LocationManageCallback() {
            @Override
            public void onSelectedLocation(@NonNull String formattedId) {
                setResult(
                        RESULT_OK,
                        new Intent().putExtra(MainActivity.KEY_MAIN_ACTIVITY_LOCATION_FORMATTED_ID, formattedId)
                );
                finish();
            }

            @Override
            public void onLocationListChanged() {
                setResult(RESULT_OK);
            }
        });

        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.activity_manage_container, manageFragment)
                .commit();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //删除当前位置
        delCurLocal();
        switch (requestCode) {
            case SEARCH_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    manageFragment.addLocation();
                }
                break;

            case SELECT_PROVIDER_ACTIVITY:
                manageFragment.resetLocationList();
                break;
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // do nothing.
    }

    @Override
    public View getSnackbarContainer() {
        return container;
    }
    //删除当前位置
    private void delCurLocal(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
        List<Location> locationList = databaseHelper.readLocationList();
        for (int i = 0; i < locationList.size(); i ++) {
            if (locationList.get(i).getCityId().equalsIgnoreCase("NULL_ID")){
                databaseHelper.deleteLocation(locationList.get(0));
                databaseHelper.deleteWeather(locationList.get(0));
            }
        }
    }
}

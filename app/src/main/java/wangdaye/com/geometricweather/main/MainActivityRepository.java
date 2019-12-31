package wangdaye.com.geometricweather.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

import wangdaye.com.geometricweather.basic.model.location.Location;
//import wangdaye.com.geometricweather.location.LocationHelper;
import wangdaye.com.geometricweather.main.model.LocationResource;
import wangdaye.com.geometricweather.weather.WeatherHelper;

public class MainActivityRepository {

//    private LocationHelper locationHelper;
    private WeatherHelper weatherHelper;

    private static final int INVALID_LOCATION_INDEX = -1;

    public MainActivityRepository(Context context) {
//        locationHelper = new LocationHelper(context);
        weatherHelper = new WeatherHelper();
    }

    public void getWeather(Context context,
                           @NonNull MutableLiveData<LocationResource> currentLocation,
                           @NonNull List<Location> totalLocationList,
                           @NonNull ReadWriteLock lock,
                           boolean locate,
                           @Nullable OnLocationCompletedListener l) {
        assert currentLocation.getValue() != null;
        Location data = currentLocation.getValue().data;
        boolean defaultLocation = currentLocation.getValue().isDefaultLocation();

        if (locate) {
//            locationHelper.requestLocation(context, currentLocation.getValue().data, false,
//                    new LocationHelper.OnRequestLocationListener() {
//                        @Override
//                        public void requestLocationSuccess(Location requestLocation) {
//                            if (!requestLocation.equals(data)) {
//                                return;
//                            }
//
//                            lock.writeLock().lock();
//                            currentLocation.setValue(LocationResource.loading(requestLocation, defaultLocation));
//                            updateLocationList(requestLocation, totalLocationList);
//                            if (l != null) {
//                                l.onCompleted(context);
//                            }
//                            lock.writeLock().unlock();
//
//                            getWeatherWithValidLocationInformation(
//                                    context, currentLocation, totalLocationList, lock);
//                        }
//
//                        @Override
//                        public void requestLocationFailed(Location requestLocation) {
//                            if (!requestLocation.equals(data)) {
//                                return;
//                            }
//
//                            lock.writeLock().lock();
//                            if (requestLocation.isUsable()) {
//                                currentLocation.setValue(
//                                        LocationResource.loading(requestLocation, defaultLocation, true));
//                                updateLocationList(requestLocation, totalLocationList);
//                                lock.writeLock().unlock();
//
//                                getWeatherWithValidLocationInformation(
//                                        context, currentLocation, totalLocationList, lock);
//                            } else {
//                                currentLocation.setValue(
//                                        LocationResource.error(requestLocation, defaultLocation, true));
//                                updateLocationList(requestLocation, totalLocationList);
//                                lock.writeLock().unlock();
//                            }
//                        }
//                    });
        } else {
            getWeatherWithValidLocationInformation(context, currentLocation, totalLocationList, lock);
        }
    }

    private void getWeatherWithValidLocationInformation(Context context,
                                                        @NonNull MutableLiveData<LocationResource> currentLocation,
                                                        @NonNull List<Location> totalLocationList,
                                                        @NonNull ReadWriteLock lock) {
        assert currentLocation.getValue() != null;
        Location data = currentLocation.getValue().data;
        boolean defaultLocation = currentLocation.getValue().isDefaultLocation();

        weatherHelper.requestWeather(context, data, new WeatherHelper.OnRequestWeatherListener() {
            @Override
            public void requestWeatherSuccess(@NonNull Location requestLocation) {
                if (!requestLocation.equals(data)) {
                    return;
                }

                lock.writeLock().lock();
                currentLocation.setValue(LocationResource.success(requestLocation, defaultLocation));
                updateLocationList(requestLocation, totalLocationList);
                lock.writeLock().unlock();
            }

            @Override
            public void requestWeatherFailed(@NonNull Location requestLocation) {
                if (!requestLocation.equals(data)) {
                    return;
                }

                lock.writeLock().lock();
                currentLocation.setValue(LocationResource.error(requestLocation, defaultLocation));
                updateLocationList(requestLocation, totalLocationList);
                lock.writeLock().unlock();
            }
        });
    }

    private void updateLocationList(@NonNull Location data, @NonNull List<Location> totalList) {
        int index = indexLocation(totalList, data);
        if (index != INVALID_LOCATION_INDEX) {
            totalList.set(index, data);
        }
    }

    private int indexLocation(@NonNull List<Location> locationList, @NonNull Location location) {
        for (int i = 0; i < locationList.size(); i ++) {
            if (locationList.get(i).equals(location)) {
                return i;
            }
        }
        return INVALID_LOCATION_INDEX;
    }

//    public List<String> getLocatePermissionList() {
////        return new ArrayList<>(
////                Arrays.asList(locationHelper.getPermissions())
////        );
////    }

    public void cancel() {
//        locationHelper.cancel();
        weatherHelper.cancel();
    }

    public interface OnLocationCompletedListener {
        /**
         * Work in write lock.
         * */
        void onCompleted(Context context);
    }
}

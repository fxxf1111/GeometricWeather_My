//package wangdaye.com.geometricweather.location.service;
//
//import android.Manifest;
//import android.content.Context;
//import android.os.Build;
//import androidx.annotation.NonNull;
//import androidx.core.app.NotificationManagerCompat;
//
////import com.baidu.location.BDAbstractLocationListener;
////import com.baidu.location.BDLocation;
////import com.baidu.location.LocationClient;
////import com.baidu.location.LocationClientOption;
//
//import wangdaye.com.geometricweather.GeometricWeather;
////import wangdaye.com.geometricweather.location.LocationException;
////import wangdaye.com.geometricweather.utils.helpter.BuglyHelper;
//
///**
// * Baidu location service.
// * */
//
//public class BaiduLocationService extends LocationService {
//
//    private LocationClient client;
//    private NotificationManagerCompat manager;
//    private LocationCallback callback;
//
//    private BDAbstractLocationListener listener = new BDAbstractLocationListener() {
//
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            cancel();
//            if (callback != null) {
//                switch (bdLocation.getLocType()) {
//                    case 61:
//                    case 161:
//                        Result result = new Result(
//                                (float) bdLocation.getLatitude(),
//                                (float) bdLocation.getLongitude()
//                        );
//                        result.setGeocodeInformation(
//                                bdLocation.getCountry(),
//                                bdLocation.getProvince(),
//                                bdLocation.getCity(),
//                                bdLocation.getDistrict()
//                        );
//                        result.inChina = bdLocation.getLocationWhere() == BDLocation.LOCATION_WHERE_IN_CN;
//                        callback.onCompleted(result);
//                        break;
//
//                    default:
////                        BuglyHelper.report(
////                                new LocationException(
////                                        bdLocation.getLocType(),
////                                        bdLocation.getLocTypeDescription()
////                                )
////                        );
////                        callback.onCompleted(null);
//                        break;
//                }
//            }
//        }
//    };
//
//    public BaiduLocationService(Context context) {
//        manager = NotificationManagerCompat.from(context);
//    }
//
//    @Override
//    public void requestLocation(Context context, @NonNull LocationCallback callback){
//        this.callback = callback;
//
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving); // 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType("wgs84"); // 可选，默认gcj02，设置返回的定位结果坐标系
//        option.setScanSpan(0); // 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true); // 可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(false); // 可选，默认false,设置是否使用gps
//        option.setLocationNotify(false); // 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(false); // 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(false); // 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false); // 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        option.SetIgnoreCacheException(true); // 可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false); // 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
//        option.setWifiCacheTimeOut(5 * 60 * 1000); // 可选，如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位
//        client = new LocationClient(context.getApplicationContext());
//        client.setLocOption(option);
//        client.registerLocationListener(listener);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            manager.createNotificationChannel(getLocationNotificationChannel(context));
//            client.enableLocInForeground(
//                    GeometricWeather.NOTIFICATION_ID_LOCATION,
//                    getLocationNotification(context));
//        }
//        client.start();
//    }
//
//    @Override
//    public void cancel() {
//        if (client != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                client.disableLocInForeground(true);
//            }
//            client.stop();
//            client = null;
//        }
//    }
//
//    @Override
//    public String[] getPermissions() {
//        return new String[] {
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        };
//    }
//}

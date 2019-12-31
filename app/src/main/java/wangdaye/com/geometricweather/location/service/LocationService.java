//package wangdaye.com.geometricweather.location.service;
//
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.content.pm.PackageManager;
//import android.os.Build;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import androidx.core.app.ActivityCompat;
//import androidx.core.app.NotificationCompat;
//import androidx.core.content.ContextCompat;
//
//import wangdaye.com.geometricweather.GeometricWeather;
//import wangdaye.com.geometricweather.R;
//
///**
// * Location service.
// * */
//
//public abstract class LocationService {
//
//    public class Result {
//
//        public float latitude;
//        public float longitude;
//
//        public String district;
//        public String city;
//        public String province;
//        public String country;
//
//        public boolean inChina;
//        public boolean hasGeocodeInformation;
//
//        public Result(float lat, float lon) {
//            latitude = lat;
//            longitude = lon;
//
//            district = "";
//            city = "";
//            province = "";
//            country = "";
//
//            inChina = false;
//            hasGeocodeInformation = false;
//        }
//
//        public void setGeocodeInformation(String country, String province, String city, String district) {
//            hasGeocodeInformation = true;
//            this.country = country;
//            this.province = province;
//            this.city = city;
//            this.district = district;
//        }
//    }
//
//    public abstract void requestLocation(Context context, @NonNull LocationCallback callback);
//
//    public abstract void cancel();
//
//    public boolean hasPermissions(Context context) {
//        String[] permissions = getPermissions();
//        for (String p : permissions) {
//            if (ActivityCompat.checkSelfPermission(context, p) != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public abstract String[] getPermissions();
//
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    NotificationChannel getLocationNotificationChannel(Context context) {
//        NotificationChannel channel = new NotificationChannel(
//                GeometricWeather.NOTIFICATION_CHANNEL_ID_LOCATION,
//                GeometricWeather.getNotificationChannelName(
//                        context, GeometricWeather.NOTIFICATION_CHANNEL_ID_LOCATION),
//                NotificationManager.IMPORTANCE_MIN);
//        channel.setShowBadge(false);
//        channel.setLightColor(ContextCompat.getColor(context, R.color.colorPrimary));
//        return channel;
//    }
//
//    Notification getLocationNotification(Context context) {
//        return new NotificationCompat.Builder(context, GeometricWeather.NOTIFICATION_CHANNEL_ID_LOCATION)
//                .setSmallIcon(R.drawable.ic_location)
//                .setContentTitle(context.getString(R.string.feedback_request_location))
//                .setContentText(context.getString(R.string.feedback_request_location_in_background))
//                .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)
//                .setPriority(NotificationCompat.PRIORITY_MIN)
//                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
//                .setAutoCancel(true)
//                .setProgress(0, 0, true)
//                .build();
//    }
//
//    // interface.
//
//    public interface LocationCallback {
//        void onCompleted(@Nullable Result result);
//    }
//}

package until;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Checkconnection {
    public static boolean HaveNetworkConnection(Context context){
        boolean haveconectedwifi=false;
        boolean haveconectedmobile=false;
        ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo= cm.getAllNetworkInfo();
        for(NetworkInfo ni: networkInfo) {
            if(ni.getTypeName().equalsIgnoreCase("WIFI"))
                if(ni.isConnected())
                    haveconectedwifi=true;
            if(ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if(ni.isConnected())
                    haveconectedmobile=true;
        }
        return haveconectedwifi || haveconectedmobile;
    }
    public static void Showtoast_short(Context context,String thongbao){
        Toast.makeText(context,thongbao , Toast.LENGTH_SHORT).show();
    }
}

package ovh.trustme.overdated;

import android.app.Application;
import android.content.Context;

public class ProduitApplication extends Application{
    private static Context sContext;

    public void onCreate(){
        super.onCreate();

        sContext = getApplicationContext();
    }

    public static Context getsContext() {return sContext;}
}

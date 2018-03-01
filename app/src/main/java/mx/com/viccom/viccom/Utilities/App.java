package mx.com.viccom.viccom.Utilities;

import android.app.Application;
import android.content.Context;

/**
 * Created by Admin on 28/02/2018.
 */

public class App extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static Context getContext(){
        return mContext;
    }
}


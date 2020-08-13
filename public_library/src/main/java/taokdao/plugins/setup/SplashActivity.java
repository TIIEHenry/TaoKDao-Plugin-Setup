package taokdao.plugins.setup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.Objects;

import taokdao.plugins.setup.io.Filej;
import taokdao.plugins.setup.io.Zipl;

public class SplashActivity extends AppCompatActivity {

    @SuppressLint({"ApplySharedPref"})
    private boolean checkInfo() {
        String str = "lastUpdateTime";
        try {
            long j = getPackageManager().getPackageInfo(getPackageName(), 0).lastUpdateTime;
            SharedPreferences sharedPreferences = getSharedPreferences("appInfo", 0);
            if (sharedPreferences.getLong(str, 0) != j) {
                Editor edit = sharedPreferences.edit();
                edit.putLong(str, j);
                edit.commit();
                return true;
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());//绕过provider
        setContentView(R.layout.activity_library_splash);
        Constant.extractedDir = Objects.requireNonNull(getExternalFilesDir("plugin")).getAbsolutePath();
        if (checkInfo()) {
            startTask();
        } else {
            startMain();
        }
    }

    public void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    @SuppressLint({"StaticFieldLeak"})
    public void startTask() {
        new AsyncTask<Object, Object, Integer>() {
            protected Integer doInBackground(Object... objArr) {
                try {
                    new Filej(Constant.extractedDir).deleteAll();
                    new Zipl(new Filej(getApplicationInfo().publicSourceDir)).unZipDir("assets/taokdao", new Filej(Constant.extractedDir));
//                    new Filej(SplashActivity.this.getApplicationInfo().publicSourceDir).copyTo(new Filej(SplashActivity.filesPath,getPackageName()));
                    return 0;
                } catch (IOException e) {
                    e.printStackTrace();
                    return -1;
                }
            }

            protected void onPostExecute(Integer num) {
                super.onPostExecute(num);
                if (num == 0) {
                    startMain();
                } else {
                    finish();
                }
            }
        }.execute();
    }
}

package taokdao.plugins.setup;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import io.noties.markwon.Markwon;
import taokdao.api.internal.InnerIdentifier;
import taokdao.plugins.setup.io.Filej;


public class MainActivity extends AppCompatActivity implements OnMenuItemClickListener {
    private TextView mdView;
    private long mTouchTime;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_library_main);
        mdView = findViewById(R.id.tv_md);
        File file = new File(Constant.extractedDir, Constant.readmeName);
        final Markwon markwon = Markwon.create(this);

        try {
            markwon.setMarkdown(mdView, new Filej(file).readString());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        if ((System.currentTimeMillis() - mTouchTime) > 2000) {
            mTouchTime = System.currentTimeMillis();
            Toast.makeText(this, R.string.pressagaintoexit, Toast.LENGTH_LONG).show();
        } else {
            mTouchTime = 0;
            finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, R.string.install, 0, R.string.install).setIcon(R.drawable.ic_package_down_white_48dp).setOnMenuItemClickListener(this).setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getItemId() == R.string.install) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(Constant.taokdaoPackage);
            if (intent == null) {
                Toast.makeText(this, R.string.taokdao_not_installed, Toast.LENGTH_LONG).show();
                return false;
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(InnerIdentifier.Intent.PARAMETER_ACTION, InnerIdentifier.Intent.ACTION_INSTALL_PLUGIN);
            intent.putExtra(InnerIdentifier.Intent.PARAMETER_PACKAGE, getPackageName());
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
        return false;
    }
}
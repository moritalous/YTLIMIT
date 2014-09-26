package forest.rice.field.k.matsuda1;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class ShortcutActivity extends Activity {

	private SharedPreferences sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		String packageName = getIntent().getStringExtra(
				SettingActivity.EXTRA_PACKAGE_NAME);
		if (packageName == null) {
			finish();
			return;
		}

		sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

		String key = String.valueOf(Calendar.getInstance().get(
				Calendar.HOUR_OF_DAY));

		int count = sharedPref.getInt(key, 10);
		count--;
		sharedPref.edit().putInt(key, count).commit();

		if (count <= 0) {
			// 10回みたよ
			// // Youtube
			Toast.makeText(this, "休憩しよう", Toast.LENGTH_LONG).show();

		} else {
			// // Youtube
			PackageManager manager = getPackageManager();
			Intent intent = manager.getLaunchIntentForPackage(packageName);
			startActivity(intent);

			Toast.makeText(this, "あと" + count + "回", Toast.LENGTH_SHORT).show();

		}

		finish();
	}

}

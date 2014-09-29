package forest.rice.field.k.matsuda1;

import java.util.List;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import forest.rice.field.k.matsuda1.entity.PackageItem;

public class SettingActivity extends ActionBarActivity implements
		OnClickListener {
	
	private final int REQUEST_CODE_PACKAGE_SELECT = 9000;  

	private Button button1;

	public static final String EXTRA_PACKAGE_NAME = "package_name";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1: {
			Intent intent = new Intent(this, PackageSelectActivity.class);
			startActivityForResult(intent, REQUEST_CODE_PACKAGE_SELECT);
		}
			break;

		case 999:

			PackageManager manager = getPackageManager();
			List<PackageInfo> packages = manager
					.getInstalledPackages(PackageManager.GET_ACTIVITIES);

			for (PackageInfo info : packages) {

				if ("com.google.android.youtube".equals(info.packageName)) {

					Intent intent = new Intent(getApplicationContext(),
							ShortcutActivity.class);
					intent.setAction(Intent.ACTION_MAIN);
					// intent.setClassName(this, intent.getClass().getName());
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.putExtra(EXTRA_PACKAGE_NAME, info.packageName);

					Intent createShortcut = new Intent();

					// ショートカットインテントを指定
					createShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
							intent);
					createShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME,
							info.applicationInfo.loadLabel(manager));
					try {
						createShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON,
								((BitmapDrawable) manager
										.getApplicationIcon(info.packageName))
										.getBitmap());
					} catch (Exception e) {
						System.out.println("Error");
					}
					createShortcut
							.setAction("com.android.launcher.action.INSTALL_SHORTCUT");

					sendBroadcast(createShortcut);

					break;
				}

			}

			finish();
			break;

		default:
			break;
		}

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE_PACKAGE_SELECT:
		{
			String packageName = data.getStringExtra(PackageItem.EXTRA_PACKAGE_NAME);
			String name =data.getStringExtra(PackageItem.EXTRA_PACKAGE_LABEL);
			Bitmap icon = (Bitmap)data.getParcelableExtra(PackageItem.EXTRA_PACKAGE_ICON);
			PackageItem item = new PackageItem(packageName, name, icon);
		}
			System.out.println();

			
			
			break;
		default:
			break;
		}
	}
}

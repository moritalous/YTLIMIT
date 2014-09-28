package forest.rice.field.k.matsuda1;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import forest.rice.field.k.matsuda1.dummy.PackageItem;

public class PackageLoadAsyncTask extends
		AsyncTask<Context, String, List<PackageItem>> {

	@Override
	protected List<PackageItem> doInBackground(Context... params) {

		List<PackageItem> result = new ArrayList<PackageItem>();

		PackageManager manager = params[0].getPackageManager();
		List<PackageInfo> packages = manager
				.getInstalledPackages(PackageManager.GET_ACTIVITIES);

		for (PackageInfo info : packages) {
			try {
				String packageName = info.packageName;
				CharSequence name = info.applicationInfo.loadLabel(manager);
				Bitmap icon = ((BitmapDrawable) manager
						.getApplicationIcon(info.packageName)).getBitmap();

				PackageItem item = new PackageItem(packageName, name, icon);
				result.add(item);
			} catch (Exception e) {
				continue;
			}
		}

		return result;
	}

	@Override
	protected void onPostExecute(List<PackageItem> result) {

	}

}

package forest.rice.field.k.matsuda1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import forest.rice.field.k.matsuda1.PackageSelectFragment.OnFragmentInteractionListener;
import forest.rice.field.k.matsuda1.entity.PackageItem;

public class PackageSelectActivity extends ActionBarActivity implements
		OnFragmentInteractionListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_package_select);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PackageSelectFragment()).commit();
		}

		// 戻るを有効にする
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		switch (id) {
		case android.R.id.home:
			finish();
			return true;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onFragmentInteraction(PackageItem packageItem) {

		Intent intent = new Intent();
		intent.putExtra(PackageItem.EXTRA_PACKAGE_NAME, packageItem.packageName);
		intent.putExtra(PackageItem.EXTRA_PACKAGE_LABEL, packageItem.packageLabel);
		intent.putExtra(PackageItem.EXTRA_PACKAGE_ICON, packageItem.icon);
		
		setResult(RESULT_OK, intent);
		finish();
	}
}

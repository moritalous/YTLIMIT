package forest.rice.field.k.matsuda1.dummy;

import android.graphics.Bitmap;

public class PackageItem {
	public CharSequence packageName;
	public CharSequence packageLabel;
	public Bitmap icon;

	public PackageItem(CharSequence packageName, CharSequence name, Bitmap icon) {
		this.packageName = packageName;
		this.packageLabel = name;
		this.icon = icon;
	}

}
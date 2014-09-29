package forest.rice.field.k.matsuda1.entity;

import android.graphics.Bitmap;

public class PackageItem {

	public static final String EXTRA_PACKAGE_NAME = "packageName";
	public static final String EXTRA_PACKAGE_LABEL = "label";
	public static final String EXTRA_PACKAGE_ICON = "icon";

	public CharSequence packageName;
	public CharSequence packageLabel;
	public Bitmap icon;
	

	public PackageItem(CharSequence packageName, CharSequence name, Bitmap icon) {
		this.packageName = packageName;
		this.packageLabel = name;
		this.icon = icon;
	}
	
}
package forest.rice.field.k.matsuda1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import forest.rice.field.k.matsuda1.entity.PackageItem;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class PackageSelectFragment extends ListFragment {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	List<PackageItem> packageItemList;

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	// TODO: Rename and change types of parameters
	public static PackageSelectFragment newInstance(String param1, String param2) {
		PackageSelectFragment fragment = new PackageSelectFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public PackageSelectFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}

		PackageLoadAsyncTask asyncTask = new PackageLoadAsyncTask();
		asyncTask.execute();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		if (null != mListener) {
			mListener.onFragmentInteraction(packageItemList.get(position));
		}
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {

		public void onFragmentInteraction(PackageItem packageItem);
	}

	class PackageLoadAsyncTask extends
			AsyncTask<String, String, List<PackageItem>> {

		@Override
		protected List<PackageItem> doInBackground(String... params) {

			List<PackageItem> result = new ArrayList<PackageItem>();

			PackageManager manager = getActivity().getPackageManager();
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
			packageItemList = result;

			setListAdapter(new PackageSelectAdapter(getActivity(), 0, result));
		}
	}

	class ViewHolder {
		public ImageView image;
		public TextView name;
	}

	class PackageSelectAdapter extends ArrayAdapter<PackageItem> {

		private LayoutInflater layoutInflater_;

		public PackageSelectAdapter(Context context, int resource,
				List<PackageItem> item) {
			super(context, resource, item);

			layoutInflater_ = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = layoutInflater_.inflate(
						R.layout.fragment_package_select_row, null);
				holder = new ViewHolder();

				holder.image = (ImageView) convertView
						.findViewById(R.id.fragment_package_select_image);
				holder.name = (TextView) convertView
						.findViewById(R.id.fragment_package_select_name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			PackageItem item = (PackageItem) getItem(position);

			holder.image.setImageBitmap(item.icon);
			holder.name.setText(item.packageLabel);

			return convertView;

		}

	}
}

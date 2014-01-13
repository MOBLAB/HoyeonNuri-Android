package proinfactory.com.hoyeonnuri.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import proinfactory.com.hoyeonnuri.R;

public class FragmentDevInfo extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_devinfo, container, false);
		return v;
	}
}

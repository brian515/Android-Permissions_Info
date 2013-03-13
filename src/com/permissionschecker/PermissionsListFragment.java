package com.permissionschecker;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: briancharous
 * Date: 3/7/13
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class PermissionsListFragment extends ListFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.apps_list_fragment, container, false);
	}

}

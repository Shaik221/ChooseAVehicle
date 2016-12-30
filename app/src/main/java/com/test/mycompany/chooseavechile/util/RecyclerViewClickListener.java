package com.test.mycompany.chooseavechile.util;

import android.view.View;

public class RecyclerViewClickListener {
	/** Interface for Item Click over Recycler View Items **/
	public interface OnClickListener {
		public void OnItemClick(View view, int position);
	}
}

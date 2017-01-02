package com.test.mycompany.chooseavechile.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.mycompany.chooseavechile.R;


public class ContainerFragment extends BaseContainerFragment {

        private boolean mIsViewInited;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.container_fragment, null);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            if (!mIsViewInited) {
                mIsViewInited = true;
                initView();
            }
        }

        private void initView() {
            replaceFragment(new MainFragment(), false);
        }

    }
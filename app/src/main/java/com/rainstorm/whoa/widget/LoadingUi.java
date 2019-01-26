package com.rainstorm.whoa.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.wang.avi.AVLoadingIndicatorView;
import com.rainstorm.whoa.R;

/**
 * @author liys
 *
 *  Usage：
 * 1.show：LoadingUi.getInstance().setDialog(context).show();
 * 2.hide：LoadingUi.getInstance().getDialog().hide();
 */

public class LoadingUi {
    private  Dialog mLoadingDialog;
    private  AVLoadingIndicatorView avi;

    private static LoadingUi instance = new LoadingUi();
    private LoadingUi(){}
    public static LoadingUi getInstance() {
        return instance;
    }

    public Loading setDialog (Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        avi = view.findViewById(R.id.avi);
        mLoadingDialog = new Dialog(context, R.style.loading_dialog_style);
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        return new Loading();
    }

    public Loading getDialog () {
        return new Loading();
    }

    public class Loading{

        public Loading() {
        }

        public Loading show() {
			if (null != mLoadingDialog && null != avi) {
				mLoadingDialog.show();
                avi.smoothToShow();
			}

            return this;
        }

        public Loading hide() {
			if (null != mLoadingDialog && null != avi) {
				avi.smoothToHide();
                mLoadingDialog.cancel();
			}

            return this;
        }

    }

}

package com.federicobarocci.wifiexplorer.ui.presenter;

import com.federicobarocci.wifiexplorer.R;

/**
 * Created by federico on 04/12/15.
 */
public enum DataBaseAction {
    IS_PRESENT {
        @Override
        public int getImage() {
            return R.drawable.ic_grade_black_24dp;
        }

        @Override
        public int getMessage() {
            return R.string.ask_remove_favourite;
        }

        @Override
        public int getResultMessage() {
            return R.string.saved_wifi_element;
        }
    },
    NOT_PRESENT {
        @Override
        public int getImage() {
            return R.drawable.ic_grade_white_24dp;
        }

        @Override
        public int getMessage() {
            return R.string.ask_add_favourite;
        }

        @Override
        public int getResultMessage() {
            return R.string.removed_wifi_element;
        }
    };

    abstract public int getImage();
    public abstract int getMessage();
    public abstract int getResultMessage();
}

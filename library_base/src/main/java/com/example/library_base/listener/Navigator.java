package com.example.library_base.listener;

public interface Navigator {
    /**
     * 收藏或取消收藏
     */
    interface OnCollectNavigator {
        void onSuccess();

        void onFailure();
    }
}

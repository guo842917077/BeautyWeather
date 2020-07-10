package com.crazyorange.beauty.comm.network.lifecycle;

import androidx.lifecycle.Lifecycle;

public interface LifeCycleProvider {
    void observe(Observer observer);

    void removeObserver(Observer observer);


    interface Observer {
        /**
         * Called when life state change
         *
         * @param event
         */
        void onChanged(Lifecycle.Event event);
    }
}

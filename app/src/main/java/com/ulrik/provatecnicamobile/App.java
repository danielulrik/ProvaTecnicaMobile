package com.ulrik.provatecnicamobile;

import android.app.Application;
import android.app.NotificationManager;
import android.content.SharedPreferences;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.ulrik.provatecnicamobile.database.AppDatabase;
import com.ulrik.provatecnicamobile.repository.ResourceRepository;
import com.ulrik.provatecnicamobile.view.NotificationUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class App extends Application {

    private static SharedPreferences preferences;
    private static ResourceRepository repository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(getApplicationContext());
        FlowManager.getDatabase(AppDatabase.class).reset();
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        preferences.edit().clear().apply();
        syncDb();
    }

    private void syncDb() {
        if (!preferences.getBoolean("synced", false)) {
            NotificationUtil.makeProgressNotification(getApplicationContext(), NOTIFICATION_ID);
            compositeDisposable.add(getRepository().syncDb().subscribeOn(Schedulers.io()).subscribe(aBoolean -> {
                if (aBoolean) {
                    preferences.edit().putBoolean("synced", true).apply();
                    preferences.edit().putBoolean("isSyncing", false).apply();
                    NotificationUtil.cancelNotification(getApplicationContext(), NOTIFICATION_ID);
                    NotificationUtil.makeFinishedNotification(getApplicationContext(), 3);
                    dispose();
                }
            }));
        }
    }

    private void dispose() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public static boolean isSynced() {
        return preferences.getBoolean("synced", false);
    }

    public static boolean isSyncing() {
        return preferences.getBoolean("isSyncing", false);
    }

    public static ResourceRepository getRepository() {
        if (repository == null) {
            repository = new ResourceRepository();
        }
        return repository;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }
}

package com.ulrik.provatecnicamobile.cache;

import android.util.SparseArray;

import com.ulrik.provatecnicamobile.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class Cache {

    private static SparseArray<User> users = new SparseArray<>();

    public static void setUsers(List<User> users) {
        Cache.users.clear();
        for (User user : users) {
            Cache.users.put(user.getId(), user);
        }
    }

    public static User getUser(int userId) {
        return users.get(userId);
    }

    public static SparseArray<User> getUsers() {
        return users;
    }
}

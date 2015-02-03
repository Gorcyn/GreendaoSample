package com.gorcyn.sample.greendao.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.gorcyn.sample.greendao.dao.migrate.Migrate1To2;

public class UpgradeOpenHelper extends DaoMaster.OpenHelper {

    public UpgradeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion) {
            case 2:
               new Migrate1To2().applyMigration(db, oldVersion);
                break;
            case 3:
                // new Migrate2To3().applyMigration(db, oldVersion);
                break;
        }
    }
}

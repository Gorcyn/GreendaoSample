package com.gorcyn.sample.greendao.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gorcyn.sample.greendao.R;
import com.gorcyn.sample.greendao.dao.DaoMaster;
import com.gorcyn.sample.greendao.dao.DaoSession;
import com.gorcyn.sample.greendao.dao.UpgradeOpenHelper;
import com.gorcyn.sample.greendao.model.User;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    DaoSession daoSession;

    ListView userListView;
    ArrayAdapter<User> userListAdapter;

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userListView = (ListView) findViewById(android.R.id.list);

        // Empty
        View userListEmpty = findViewById(R.id.list_empty);
        userListView.setEmptyView(userListEmpty);

        daoSession = new DaoMaster(new UpgradeOpenHelper(this, getString(R.string.app_name), null).getWritableDatabase())
            .newSession();

        userListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new LinkedList<User>());
        userListView.setAdapter(userListAdapter);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull AdapterView<?> parent, @NonNull View view, int position, long id) {
                final String toast = String.format(
                    "mail: %s; password: %s",
                    userListAdapter.getItem(position).getEmail(),
                    userListAdapter.getItem(position).getPassword()
                );
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //region Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void addUser(MenuItem menuItem) {
        int randomInt = random.nextInt(2015);

        User user = new User();
        user.setFirstName("Firstname " + randomInt);
        user.setLastName("Lastname " + randomInt);
        user.setBirthDate(new GregorianCalendar(randomInt, Calendar.JUNE, 20).getTime());
        user.setEmail(String.format("mail%d@mail.com", randomInt));
        user.setPassword(String.format("%d", randomInt));

        daoSession.getUserDao().insertInTx(user);
        onStart();
    }
    //endregion

    @Override
    protected void onStart() {
        super.onStart();

        userListAdapter.clear();
        List<User> users = daoSession.getUserDao().loadAll();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            userListAdapter.addAll(users);
        } else {
            for (User user : users) {
                userListAdapter.add(user);
            }
        }
        userListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();

        daoSession.clear();
    }
}

package com.team300.fridge;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.team300.fridge.POJOs.FoodItem;
import com.team300.fridge.POJOs.GroceryList;
import com.team300.fridge.POJOs.Model;
import com.team300.fridge.Tabs.FridgeFragment;
import com.team300.fridge.Tabs.UserFragment;
import com.team300.fridge.Tabs.ViewGroceryListsFragment;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {


    private MyPagerAdapter mPagerAdapter;
    private static Context context;
    private static final int SCHEMA_V_PREV = 4;// previous schema version
    private static final int SCHEMA_V_NOW = 5;// change schema version if any change happened in schema

    private static Realm uiThreadRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Realm.init(this);

        // Realm Sync code
        /*app = new App(new AppConfiguration.Builder("fridge-itlwp")
                .build());


        Credentials emailPasswordCredentials = Credentials.emailPassword("user1@demo.com", "password123");
//        AtomicReference<User> user = new AtomicReference<User>();
        app.loginAsync(emailPasswordCredentials, it -> {
            if (it.isSuccess()) {
                Log.v("AUTH", "Successfully authenticated using an email and password.");
                User user = app.currentUser();
                String partitionValue = "object_id";
                SyncConfiguration config = new SyncConfiguration.Builder(
                        user,
                        partitionValue)
                        .allowQueriesOnUiThread(true)
                        .allowWritesOnUiThread(true)
                        .build();
                uiThreadRealm = Realm.getInstance(config);

                Products product = uiThreadRealm.where(Products.class).equalTo("_id", 4).findFirst();
                if (product != null) {
                    Log.v("AUTH", product.toString());
                }
                Log.v("AUTH", config.toString());


                setContentView(R.layout.activity_main);

                //populate each view with the appropriate view from the activity_main layout file
                Toolbar toolbar = findViewById(R.id.toolbar);
                setSupportActionBar(toolbar);

                // Create the adapter that will return a fragment for each of the three
                // primary sections of the activity.
                mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
                ViewPager mViewPager = findViewById(R.id.container);
                mViewPager.setAdapter(mPagerAdapter);

                TabLayout tabLayout = findViewById(R.id.tabs);

                mViewPager.addOnPageChangeListener(new TabLayout
                        .TabLayoutOnPageChangeListener(tabLayout));
                tabLayout.addOnTabSelectedListener(new TabLayout
                        .ViewPagerOnTabSelectedListener(mViewPager));
                //start app on middle fridge tab
                tabLayout.getTabAt(1).select();
            } else {
                Log.e("AUTH", it.getError().toString());
            }
        });

 */

        RealmConfiguration config = new RealmConfiguration.Builder().name("Fridge.realm")
                .schemaVersion(SCHEMA_V_NOW)
                .deleteRealmIfMigrationNeeded()// if migration needed then this methoud will remove the existing database and will create new database
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .build();
        uiThreadRealm = Realm.getInstance(config);
        addChangeListenerToRealm(uiThreadRealm);


        setContentView(R.layout.activity_main);

        //populate each view with the appropriate view from the activity_main layout file

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout
                .TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout
                .ViewPagerOnTabSelectedListener(mViewPager));
        //start app on middle fridge tab
        tabLayout.getTabAt(1).select();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        uiThreadRealm.close();
    }

    public static Realm getUiThreadRealm() {
        return uiThreadRealm;
    }

    public static void setUiThreadRealm(Realm uiThreadRealm) {
        MainActivity.uiThreadRealm = uiThreadRealm;
    }

    public static int getSchemaVNow() {
        return SCHEMA_V_NOW;
    }

    public static int getSchemaVPrev() {
        return SCHEMA_V_PREV;
    }

    public static Context getAppContext() {
        return context;
    }

    public static void setContext(Context context) {
        MainActivity.context = context;
    }

    private void addChangeListenerToRealm(Realm realm) {
        // all Tasks in the realm
        RealmResults<FoodItem> foodItems = uiThreadRealm.where(FoodItem.class).findAllAsync();
        RealmResults<GroceryList> groceryLists = uiThreadRealm.where(GroceryList.class).findAllAsync();

        foodItems.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<FoodItem>>() {
            @Override
            public void onChange(RealmResults<FoodItem> collection, OrderedCollectionChangeSet changeSet) {
                // process deletions in reverse order if maintaining parallel data structures so indices don't change as you iterate
                OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
                for (OrderedCollectionChangeSet.Range range : deletions) {
                    Log.v("QUICKSTART", "Deleted range: " + range.startIndex + " to " + (range.startIndex + range.length - 1));
                }
                OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
                for (OrderedCollectionChangeSet.Range range : insertions) {
                    Log.v("QUICKSTART", "Inserted range: " + range.startIndex + " to " + (range.startIndex + range.length - 1));                            }
                OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
                for (OrderedCollectionChangeSet.Range range : modifications) {
                    Log.v("QUICKSTART", "Updated range: " + range.startIndex + " to " + (range.startIndex + range.length - 1));                            }
            }
        });

        groceryLists.addChangeListener(new OrderedRealmCollectionChangeListener<RealmResults<GroceryList>>() {
            @Override
            public void onChange(RealmResults<GroceryList> collection, OrderedCollectionChangeSet changeSet) {
                // process deletions in reverse order if maintaining parallel data structures so indices don't change as you iterate
                OrderedCollectionChangeSet.Range[] deletions = changeSet.getDeletionRanges();
                for (OrderedCollectionChangeSet.Range range : deletions) {
                    Log.v("QUICKSTART", "Deleted range: " + range.startIndex + " to " + (range.startIndex + range.length - 1));
                }
                OrderedCollectionChangeSet.Range[] insertions = changeSet.getInsertionRanges();
                for (OrderedCollectionChangeSet.Range range : insertions) {
                    Log.v("QUICKSTART", "Inserted range: " + range.startIndex + " to " + (range.startIndex + range.length - 1));                            }
                OrderedCollectionChangeSet.Range[] modifications = changeSet.getChangeRanges();
                for (OrderedCollectionChangeSet.Range range : modifications) {
                    Log.v("QUICKSTART", "Updated range: " + range.startIndex + " to " + (range.startIndex + range.length - 1));                            }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    class MyPagerAdapter extends SmartFragmentStatePagerAdapter {

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            switch (position) {
                case 0:
                    return ViewGroceryListsFragment.newInstance();
                case 1:
                    return FridgeFragment.newInstance();
                default:
                    //should be replaced with database call to the current user
                    Model model = Model.getInstance();
                    return UserFragment.newInstance(model.getCurrentUser());
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }

    public void switchOutUser() {
        //essentially refreshes the app to register the new user's information
        finish();
        startActivity(getIntent());
    }

    public void onCheckboxClicked(View view) {
        //pass of the on click of the checkbox to the fragment
        UserFragment fragment = (UserFragment) mPagerAdapter.getRegisteredFragment(2);
        fragment.onCheckboxClicked(view);
    }
}
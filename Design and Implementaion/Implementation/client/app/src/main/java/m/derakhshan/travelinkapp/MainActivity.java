package m.derakhshan.travelinkapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {


    private DrawerLayout mDrawerLayout;


    String[] titles;
    int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        ListView listView = findViewById(R.id.listView);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.nav_header_main, listView, false);
        listView.addHeaderView(header, null, false);

        images = new int[]{
                R.mipmap.circle_filled,
                R.mipmap.circle_filled,
                R.mipmap.circle_filled,
                R.mipmap.circle_filled,
                R.mipmap.circle_filled,
                R.mipmap.circle_filled,
                R.mipmap.circle_filled
        };

        titles = new String[]{
                "Profile",
                "E Wallet",
                "History",
                "Routing",
                "Help center",
                "About Us",
                "Exit"
        };

        MyAdapter myAdapter = new MyAdapter(getApplicationContext());
        listView.setAdapter(myAdapter);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ImageView menu=findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        });
        listView.setOnItemClickListener(new DrawerItemClickListener());



    }


    private class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflaer;

        public MyAdapter(Context context) {
            mInflaer = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;


            if (convertView == null) {
                view = mInflaer.inflate(R.layout.list_items, null);
            } else {
                view = convertView;
            }
            TextView textView = (TextView) view.findViewById(R.id.list_text);
            ImageView imageView = (ImageView) view.findViewById(R.id.list_image);
            textView.setText(titles[position]);
            imageView.setImageResource(images[position]);
            return view;
        }
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            selectitem(position);
        }

        private void selectitem(int position) {
            Log.i("Log",position+"");

            switch (position) {
                case 5:
                    //open another Activity :D -JFP
                    mDrawerLayout.closeDrawers();
                    Intent intent=new Intent(MainActivity.this,HelpCenter.class);
                    startActivity(intent);
                    break;
                case 7:
                    mDrawerLayout.closeDrawers();
                    SharedPreferences sh = getSharedPreferences("Information", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sh.edit();
                    editor.putBoolean("IsLoggedIn",false);
                    editor.apply();
                    Intent intent1=new Intent(MainActivity.this,Signup.class);
                    startActivity(intent1);
                    MainActivity.this.finish();
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            mDrawerLayout.openDrawer(GravityCompat.END);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        return true;
    }



}

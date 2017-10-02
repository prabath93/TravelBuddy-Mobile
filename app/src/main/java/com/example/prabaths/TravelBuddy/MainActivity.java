package com.example.prabaths.TravelBuddy;

import android.content.ContentResolver;
import android.content.Intent;
import android.location.Location;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prabaths.Adapter.NavListAdapter;
import com.example.prabaths.Data.DAO.UserDAO;
import com.example.prabaths.Data.DAO.VehicleDAO;
import com.example.prabaths.Fragments.Expenses_Fragment;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.Fragments.Personal_Fragment;
import com.example.prabaths.Fragments.Statistics_Fragment;
import com.example.prabaths.Fragments.Vehicle_Fragment;
import com.example.prabaths.GPS.GPSTracker;
import com.example.prabaths.inner.fragments.expenses.Add_Fuel_Refill_Fragment;
import com.example.prabaths.inner.fragments.expenses.Add_Other_Expense_Fragment;
import com.example.prabaths.inner.fragments.personal.Change_Password_Fragment;
import com.example.prabaths.inner.fragments.vehicle.Add_Vehicle_Fragment;
import com.example.prabaths.inner.fragments.statistics.Comparison_Fragment;
import com.example.prabaths.inner.fragments.personal.Edit_Profile_Fragment;
import com.example.prabaths.inner.fragments.vehicle.Edit_Vehicle_Details_Fragment;
import com.example.prabaths.inner.fragments.statistics.Patterns_Fragment;
import com.example.prabaths.inner.fragments.vehicle.Vehicle_Details_Fragment;
import com.example.prabaths.inner.fragments.personal.View_Profile_Fragment;
import com.example.prabaths.models.Item;
import com.example.prabaths.models.NavItem;
import com.example.prabaths.models.SectionItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    RelativeLayout profile_box;
    ListView nav_list;
    List<Item> navItemList;
    List<Fragment> fragmentList;
    private static ImageView iv;
    Home_Fragment home_fragment;
    Expenses_Fragment expenses_fragment;
    Add_Fuel_Refill_Fragment add_fuel_refill_fragment;
    Add_Other_Expense_Fragment add_other_expense_fragment;
    Vehicle_Fragment vehicle_fragment;
    Vehicle_Details_Fragment vehicle_details_fragment;
    Edit_Vehicle_Details_Fragment edit_vehicle_details_fragment;
    Add_Vehicle_Fragment add_vehicle_fragment;
    Personal_Fragment personal_fragment;
    View_Profile_Fragment view_profile_fragment;
    Edit_Profile_Fragment edit_profile_fragment;
    Change_Password_Fragment change_password_fragment;
    Statistics_Fragment statistics_fragment;
    Comparison_Fragment comparison_fragment;
    Patterns_Fragment patterns_fragment;

    Fragment old;
    Location location;
    GPSTracker gpsTracker;
    Spinner spinner;
    public static String userName;
    public static ContentResolver contentResolver;
    String regNo;
    public static boolean userProPicChanged;
    public static boolean vehiclePicChanged;

    ActionBarDrawerToggle actionBarDrawerToggle;
    public static int main_content_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_content_id=R.id.main_content;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayOptions(0);


        Intent intent=getIntent();
        contentResolver=getContentResolver();
        //Toast.makeText(this,Environment.getExternalStorageDirectory().toString(),Toast.LENGTH_LONG).show();
        File folder = new File(Environment.getExternalStorageDirectory() + "/TravelBuddy/Images");

        if(!folder.exists()){
            //Toast.makeText(this,"folder does not exist",Toast.LENGTH_LONG).show();
            folder.mkdirs();}
        if(folder.exists()){

            //Toast.makeText(this, "folder exist:" + folder.listFiles().length, Toast.LENGTH_LONG).show();
        }
        userName=intent.getStringExtra("username");
        //Toast.makeText(this,userName,Toast.LENGTH_LONG).show();
        //SQLiteHelper sqLiteHelper=SQLiteHelper.getInstance(this);



        gpsTracker=new GPSTracker(this);
        //location=gpsTracker.getLocation();
        //GPSTracker1 gpsTracker1=new GPSTracker1(this);

        //gpsTracker1.getLocation();
        //gpsTracker.turnGPSOn();

        /*Geocoder geocoder;
        List<Address> address=null;
        geocoder=new Geocoder(this, Locale.getDefault());
        try {
            address=geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Toast.makeText(MainActivity.this, address.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();*/
        navItemList=new ArrayList<Item>();
        drawerLayout =(DrawerLayout)findViewById(R.id.drawer_layout);
        drawerPane=(RelativeLayout)findViewById(R.id.drawer_pane);
        nav_list=(ListView)findViewById(R.id.nav_list);

        old=null;

        if(userName.equals("prabath")) {
            navItemList.add(new NavItem(R.drawable.home, "Home_Fragment page", "Home_Fragment"));
            navItemList.add(new SectionItem(R.drawable.ball, "", "----- Expenses ----"));
            navItemList.add(new NavItem(R.drawable.fuel, "", "Add Fuel Expense"));
            navItemList.add(new NavItem(R.drawable.money, "", "Other Expense"));
            navItemList.add(new SectionItem(R.drawable.ball, "", "----- Vehicle ----"));
            navItemList.add(new NavItem(R.drawable.view_car, "", "View Vehicle Detail"));
            navItemList.add(new NavItem(R.drawable.edit, "", "Edit Vehicle Details"));
            navItemList.add(new NavItem(R.drawable.add, "", "Add Vehicle"));
            navItemList.add(new SectionItem(R.drawable.ball, "", "----- Personal ----"));
            navItemList.add(new NavItem(R.drawable.profile, "", "View Profile"));
            navItemList.add(new NavItem(R.drawable.edit, "", "Edit Profile"));
            navItemList.add(new NavItem(R.drawable.password, "", "Change password"));
            navItemList.add(new SectionItem(R.drawable.ball, "", "----- Statistics ----"));
            navItemList.add(new NavItem(R.drawable.profile, "", "Comparison"));
            navItemList.add(new NavItem(R.drawable.edit, "", "Patterns"));
        }

        else if(userName.equals("admin")){
            navItemList.add(new NavItem(R.drawable.home, "Home_Fragment page", "Home_Fragment"));
            navItemList.add(new SectionItem(R.drawable.ball, "", "----- Expenses ----"));
            navItemList.add(new NavItem(R.drawable.fuel, "", "Add Fuel Expense"));
            navItemList.add(new NavItem(R.drawable.money, "", "Other Expense"));
            navItemList.add(new SectionItem(R.drawable.ball, "", "----- Vehicle ----"));
            navItemList.add(new NavItem(R.drawable.view_car, "", "View Vehicle Detail"));
            navItemList.add(new NavItem(R.drawable.edit, "", "Edit Vehicle Details"));
            navItemList.add(new NavItem(R.drawable.add, "", "Add Vehicle"));
            navItemList.add(new SectionItem(R.drawable.ball, "", "----- Personal ----"));
            navItemList.add(new NavItem(R.drawable.profile, "", "View Profile"));
            navItemList.add(new NavItem(R.drawable.edit, "", "Edit Profile"));
            navItemList.add(new NavItem(R.drawable.password, "", "Change password"));
        }


        NavListAdapter navListAdapter=new NavListAdapter(getApplicationContext(),R.layout.item_nav_list,navItemList);
        nav_list.setAdapter(navListAdapter);

        //home_fragment=newInstance(userName);
        Bundle bundle=new Bundle();
        bundle.putString("u",userName);
        home_fragment=new Home_Fragment();
        home_fragment.setArguments(bundle);
        home_fragment.setMainActivity(this);

        expenses_fragment=new Expenses_Fragment();
        expenses_fragment.setArguments(bundle);
        expenses_fragment.setMainActivity(this);


        add_fuel_refill_fragment=new Add_Fuel_Refill_Fragment();
        add_fuel_refill_fragment.setArguments(bundle);
        add_fuel_refill_fragment.setMainActivity(this);


        add_other_expense_fragment=new Add_Other_Expense_Fragment();
        add_other_expense_fragment.setArguments(bundle);
        add_other_expense_fragment.setMainActivity(this);

        vehicle_fragment=new Vehicle_Fragment();
        vehicle_fragment.setArguments(bundle);
        vehicle_fragment.setMainActivity(this);

        vehicle_details_fragment=new Vehicle_Details_Fragment();
        vehicle_details_fragment.setMainActivity(this);

        edit_vehicle_details_fragment=new Edit_Vehicle_Details_Fragment();
        edit_vehicle_details_fragment.setArguments(bundle);
        edit_vehicle_details_fragment.setMainActivity(this);

        add_vehicle_fragment=new Add_Vehicle_Fragment();
        add_vehicle_fragment.setArguments(bundle);
        add_vehicle_fragment.setMainActivity(this);

        personal_fragment=new Personal_Fragment();
        personal_fragment.setArguments(bundle);
        personal_fragment.setMainActivity(this);

        view_profile_fragment=new View_Profile_Fragment();
        view_profile_fragment.setArguments(bundle);
        view_profile_fragment.setMainActivity(this);

        edit_profile_fragment=new Edit_Profile_Fragment();
        edit_profile_fragment.setArguments(bundle);
        edit_profile_fragment.setMainActivity(this);

        change_password_fragment=new Change_Password_Fragment();

        statistics_fragment=new Statistics_Fragment();
        statistics_fragment.setMainActivity(this);

        comparison_fragment=new Comparison_Fragment();

        patterns_fragment=new Patterns_Fragment();
        patterns_fragment.setMainActivity(this);


        fragmentList=new ArrayList<>();
        fragmentList.add(home_fragment);
        fragmentList.add(expenses_fragment);
        fragmentList.add(add_fuel_refill_fragment);
        //fragmentList.add(new Change_Password_Fragment());
        /*fragmentList.add(new Add_Other_Expense_Fragment());
        fragmentList.add(new Vehicle_Fragment());
        fragmentList.add(new Vehicle_Details_Fragment());
        fragmentList.add(new Edit_Vehicle_Details_Fragment());
        fragmentList.add(new Add_Vehicle_Fragment());
        fragmentList.add(new Personal_Fragment());
        fragmentList.add(new View_Profile_Fragment());
        fragmentList.add(new Edit_Profile_Fragment());
        fragmentList.add(new Statistics_Fragment());
        fragmentList.add(new Patterns_Fragment());*/
        fragmentList.add(add_other_expense_fragment);
        fragmentList.add(vehicle_fragment);
        fragmentList.add(vehicle_details_fragment);
        fragmentList.add(edit_vehicle_details_fragment);
        fragmentList.add(add_vehicle_fragment);
        fragmentList.add(personal_fragment);
        fragmentList.add(view_profile_fragment);
        fragmentList.add(edit_profile_fragment);
        fragmentList.add(change_password_fragment);
        fragmentList.add(statistics_fragment);
        fragmentList.add(comparison_fragment);
        fragmentList.add(patterns_fragment);
        //fragmentList.add(new Vehicle_Fragment());
        FragmentManager fragmentManager= getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content,fragmentList.get(0)).commit();
        setTitle(navItemList.get(0).getTitle());
        nav_list.setItemChecked(0, true);
        drawerLayout.closeDrawer(drawerPane);


        nav_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fragmentManager = getSupportFragmentManager();

                //if(old!=null) {
                    //Toast.makeText(MainActivity.this, fragmentList.get(position).toString(), Toast.LENGTH_LONG).show();
                    //fragmentManager.beginTransaction().remove(fragmentList.get(position)).commit();
               // }


                fragmentManager.beginTransaction().replace(R.id.main_content, fragmentList.get(position)).addToBackStack(null).commit();
                setTitle(navItemList.get(position).getTitle());
                nav_list.setItemChecked(position, true);
                drawerLayout.closeDrawer(drawerPane);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_opened,R.string.drawer_closed){
            @Override
            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);


    }



    public Home_Fragment newInstance(String message)
    {
        Home_Fragment f = new Home_Fragment();
        Bundle bdl = new Bundle();
        bdl.putString("u", message);
        f.setArguments(bdl);

        return f;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Toast.makeText(this,requestCode,Toast.LENGTH_LONG).show();

        if(resultCode==RESULT_OK){



            if(requestCode==131073){
                ImageView iv=(ImageView)findViewById(R.id.profPicImView);
                ImageView iv1=(ImageView)findViewById(R.id.profPicImView1);
                //iv.setImageResource(0);
                if(iv!=null) {
                    iv.setImageURI(data.getData());
                }
                if(iv1!=null) {
                    iv1.setImageURI(data.getData());
                }
                UserDAO userDAO=new UserDAO(this);
                userDAO.setImageUri(data.getData(),userName);
            }
            if(requestCode==131074){
                ImageView iv=(ImageView)findViewById(R.id.vehiclePicImView2);
                ImageView iv1=(ImageView)findViewById(R.id.vehiclePicImView);
                //iv.setImageResource(0);
                if(iv!=null) {
                    iv.setImageURI(data.getData());
                }
                Add_Vehicle_Fragment.imageUri=data.getData().toString();
                if(iv1!=null) {
                    iv1.setImageURI(data.getData());
                }
            }

            if(requestCode==131075){
                ImageView iv=(ImageView)findViewById(R.id.vehiclePicImView2);
                ImageView iv1=(ImageView)findViewById(R.id.vehiclePicImView1);
                //iv.setImageResource(0);
                if(iv!=null) {
                    iv.setImageURI(data.getData());
                }
                if(iv1!=null) {
                    iv1.setImageURI(data.getData());
                }
                VehicleDAO vehicleDAO=new VehicleDAO(this);
                vehicleDAO.setImageUri(data.getData().toString());
            }
            if(requestCode==196611){
                ImageView iv=(ImageView)findViewById(R.id.vehiclePicImView2);
                ImageView iv1=(ImageView)findViewById(R.id.vehiclePicImView1);
                //iv.setImageResource(0);
                if(iv!=null) {
                    iv.setImageURI(data.getData());
                }
                if(iv1!=null) {
                    iv1.setImageURI(data.getData());
                }
                VehicleDAO vehicleDAO=new VehicleDAO(this);
                vehicleDAO.setImageUri(data.getData().toString());
            }
            if(requestCode==65566) {
                ImageView iv=(ImageView)findViewById(R.id.vehiclePicImView);
                //iv.setImageResource(0);
                if(iv!=null) {
                    iv.setImageURI(data.getData());
                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home_Fragment/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        //noinspection SimplifiableIfStatement
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Add_Fuel_Refill_Fragment getAdd_fuel_refill_fragment() {
        return add_fuel_refill_fragment;
    }

    public Add_Other_Expense_Fragment getAdd_other_expense_fragment() {
        return add_other_expense_fragment;
    }

    public Add_Vehicle_Fragment getAdd_vehicle_fragment() {
        return add_vehicle_fragment;
    }

    public Edit_Vehicle_Details_Fragment getEdit_vehicle_details_fragment() {
        return edit_vehicle_details_fragment;
    }

    public Edit_Profile_Fragment getEdit_profile_fragment() {
        return edit_profile_fragment;
    }

    public Expenses_Fragment getExpenses_fragment() {
        return expenses_fragment;
    }

    public Home_Fragment getHome_fragment() {
        return home_fragment;
    }

    public Patterns_Fragment getPatterns_fragment() {
        return patterns_fragment;
    }

    public Personal_Fragment getPersonal_fragment() {
        return personal_fragment;
    }

    public Statistics_Fragment getStatistics_fragment() {
        return statistics_fragment;
    }

    public Vehicle_Details_Fragment getVehicle_details_fragment() {
        return vehicle_details_fragment;
    }

    public Vehicle_Fragment getVehicle_fragment() {
        return vehicle_fragment;
    }

    public View_Profile_Fragment getView_profile_fragment() {
        return view_profile_fragment;
    }


}

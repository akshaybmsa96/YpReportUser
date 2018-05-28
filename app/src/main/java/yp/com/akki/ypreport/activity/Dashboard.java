package yp.com.akki.ypreport.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yp.com.akki.ypreport.R;
import yp.com.akki.ypreport.adapter.CustomAdapterExpandableList;
import yp.com.akki.ypreport.fragment.AccountAddFragment;
import yp.com.akki.ypreport.fragment.AccountEditFragment;
import yp.com.akki.ypreport.fragment.AccountLogFragment;
import yp.com.akki.ypreport.fragment.AccountStatusFragment;
import yp.com.akki.ypreport.fragment.AccountUpdateFragment;
import yp.com.akki.ypreport.fragment.AttendanceEntryFragment;
import yp.com.akki.ypreport.fragment.ChooseDateFragment;
import yp.com.akki.ypreport.fragment.DashboardFragment;
import yp.com.akki.ypreport.fragment.EmployeeAddFragment;
import yp.com.akki.ypreport.fragment.EmployeeAttendanceFragment;
import yp.com.akki.ypreport.fragment.EmployeeEditFragment;
import yp.com.akki.ypreport.fragment.EmployeeStatusFragment;
import yp.com.akki.ypreport.fragment.ExpenseEntryFragment;
import yp.com.akki.ypreport.fragment.ExpenseReportFragment;
import yp.com.akki.ypreport.fragment.FranchisePaymentFragment;
import yp.com.akki.ypreport.fragment.ItemAddFragment;
import yp.com.akki.ypreport.fragment.ItemEditFragment;
import yp.com.akki.ypreport.fragment.MaterialReceivedFragment;
import yp.com.akki.ypreport.fragment.PurchaseEntryFragment;
import yp.com.akki.ypreport.fragment.PurchaseReportFragment;
import yp.com.akki.ypreport.fragment.VendorAddFragment;
import yp.com.akki.ypreport.fragment.VendorEditFragment;
import yp.com.akki.ypreport.fragment.VendorStatusFragment;
import yp.com.akki.ypreport.global.PreferencedData;

public class Dashboard extends AppCompatActivity {

    private Toolbar tb;
    private ActionBarDrawerToggle toggle;
    private MenuItem prevItem;
    private DrawerLayout drawer;
    private FragmentTransaction fragmentTransaction;
    private boolean doubleBackToExitPressedOnce = false;
    private ExpandableListView expandableListView;
    private CustomAdapterExpandableList customAdapterExpandableList;

    private List<String> mExpandableListTitle=new ArrayList<>();
    private Map<String, List<String>> mExpandableListData = new HashMap<String, List<String>>();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_dashboard);


        tb=(Toolbar)findViewById(R.id.toolbar);
        tb.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(tb);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        //expandable list


        expandableListView=(ExpandableListView)findViewById(R.id.navList);

        addItemsToExpandableList();

        customAdapterExpandableList = new CustomAdapterExpandableList(this, mExpandableListTitle, mExpandableListData);
        expandableListView.setAdapter(customAdapterExpandableList);


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousGroup = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousGroup)
                    expandableListView.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                //   Toast.makeText(Dashboard.this,"Group : " + groupPosition + "Child : "+ childPosition,Toast.LENGTH_SHORT).show();

                if(groupPosition == 0)
                {

                    if(childPosition == 0)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new DashboardFragment(),"Dashboard");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(PreferencedData.getPrefDeliveryCentre(Dashboard.this));

                    }

                    else if(childPosition == 1)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new ChooseDateFragment(),"Centre Report");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Select Date");

                    }


                    else if(childPosition == 2)
                    {

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new PurchaseReportFragment(),"Purchase Report");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Purchase Report");
                    }

                    else if(childPosition == 3)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new ExpenseReportFragment(),"Expense Report");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Expense Report");
                    }

                    else if(childPosition == 4)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new MaterialReceivedFragment(),"Material Received");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Material Received");

                    }

                    else if(childPosition == 5)
                    {

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AccountLogFragment(),"Account Log");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Account Log");
                    }

                    else if(childPosition == 6)
                    {

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AccountStatusFragment(),"Account Status");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Account Status");
                    }


                    else if(childPosition == 7)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new VendorStatusFragment(),"Vendor Status");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Vendor Status");
                    }

                    else if(childPosition == 8)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new EmployeeStatusFragment(),"Employee Status");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Employee Status");
                    }

                    else if(childPosition == 9)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new FranchisePaymentFragment(),"Franchise Payment");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Franchise Payment");
                    }

                }

                else if(groupPosition == 1)
                {
                    if(childPosition == 0)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AccountUpdateFragment(),"Account Update");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Account Update");

                    }

                    else if(childPosition == 1)
                    {

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AccountAddFragment(),"Account Add");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Account");

                    }

                    else if(childPosition == 2)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AccountEditFragment(),"Account Edit");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Account Edit");

                    }

                }

                else if(groupPosition == 2)
                {
                    if(childPosition == 0)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new ItemAddFragment(),"Item Add");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Item");

                    }

                    else if(childPosition == 1)
                    {

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new ItemEditFragment(),"Item Edit");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Select Item");
                    }

                }


                else if(groupPosition == 3)
                {

                    if(childPosition == 0)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new VendorAddFragment(),"Vendor Add");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Vendor");

                    }

                    else if(childPosition == 1)
                    {

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new VendorEditFragment(),"Vendor Edit");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Select Vendor");
                    }
                }


                else if(groupPosition == 4)
                {

                    if(childPosition == 0)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new EmployeeAttendanceFragment(),"Employee Attendance");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Employee Attendance");
                    }

                    else if(childPosition == 1)
                    {

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new EmployeeAddFragment(),"Add Employee");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Employee");
                    }

                    else if(childPosition == 2)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new EmployeeEditFragment(),"Remove Employee");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Select Employee");

                    }
                }


                else if(groupPosition == 5)
                {

                    if(childPosition == 0)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new PurchaseEntryFragment(),"Purchase Entry");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Purchase Entry");

                    }

                    else if(childPosition == 1)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new ExpenseEntryFragment(),"Expense Entry");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Expense Entry");
                    }

                    else if(childPosition == 2)
                    {
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AttendanceEntryFragment(),"Attendance Entry");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Attendance Entry");

                    }

                }

                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });


        //expandable list


/*
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigator);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (prevItem != null)
                    prevItem.setChecked(false);

                item.setCheckable(true);
                item.setChecked(true);

                prevItem = item;


                switch (item.getItemId()) {

                    case R.id.menuDashBoard:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new DashboardFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle(PreferencedData.getPrefDeliveryCentre(Dashboard.this));

                        drawer.closeDrawers();
                        break;

                    case R.id.menuCentreReport:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new ChooseDateFragment(),"DateTag");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Select Date");


                        drawer.closeDrawers();
                        break;

                    case R.id.menuPurchaseReport:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new PurchaseReportFragment(),"Purchase Report");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Purchase Report");

                        drawer.closeDrawers();
                        break;


                    case R.id.menuExpenseReport:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new ExpenseReportFragment(),"Expense Report");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Expense Report");

                        drawer.closeDrawers();
                        break;

                    case R.id.menuMaterialReceived:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new MaterialReceivedFragment(),"Material Received");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Material Received");

                        drawer.closeDrawers();

                        break;


                    case R.id.menuAccountAdd:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AccountAddFragment(),"Account Add");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Account");

                        drawer.closeDrawers();
                        break;


                    case R.id.menuAccountBalance:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AccountStatusFragment(),"Account Status");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Account Status");

                        drawer.closeDrawers();
                        break;

                    case R.id.menuAccountUpdate:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AccountUpdateFragment(),"Account Update");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Account Update");

                        drawer.closeDrawers();
                        break;

                    case R.id.menuAccountEdit:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AccountEditFragment(),"Account Edit");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Account Edit");

                        drawer.closeDrawers();
                        break;

                    case R.id.menuAccountLog:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AccountLogFragment(),"Account Log");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Account Log");

                        drawer.closeDrawers();
                        break;

                    case R.id.menuItemAdd:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new ItemAddFragment(),"Item Add");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Item");

                        drawer.closeDrawers();
                        break;



                    case R.id.menuItemEdit:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new ItemEditFragment(),"Item Edit");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Select Item");

                        drawer.closeDrawers();
                        break;





                    case R.id.menuVendorStatus:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new VendorStatusFragment(),"Vendor Status");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Vendor Status");

                        drawer.closeDrawers();
                        break;



                    case R.id.menuVendorAdd:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new VendorAddFragment(),"Vendor Add");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Vendor");

                        drawer.closeDrawers();
                        break;



                    case R.id.menuVendorEdit:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new VendorEditFragment(),"Vendor Edit");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Select Vendor");

                        drawer.closeDrawers();
                        break;


                    case R.id.menuEmployeeStatus:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new EmployeeStatusFragment(),"Employee Status");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Employee Status");

                        drawer.closeDrawers();
                        break;

                    case R.id.menuEmployeeAttendance:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new EmployeeAttendanceFragment(),"Employee Attendance");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Employee Attendance");

                        drawer.closeDrawers();
                        break;


                    case R.id.menuEmployeeAdd:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new EmployeeAddFragment(),"Add Employee");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Add Employee");

                        drawer.closeDrawers();
                        break;

                    case R.id.menuEmployeeEdit:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new EmployeeEditFragment(),"Remove Employee");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Select Employee");

                        drawer.closeDrawers();
                        break;

                    case R.id.menuPurchaseEntry:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new PurchaseEntryFragment(),"Purchase Entry");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Purchase Entry");

                        drawer.closeDrawers();
                        break;

                    case R.id.menuExpenseEntry:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new ExpenseEntryFragment(),"Expense Entry");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Expense Entry");

                        drawer.closeDrawers();
                        break;

                    case R.id.menuAttendanceEntry:

                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.home_layout_id, new AttendanceEntryFragment(),"Attendance Entry");
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Attendance Entry");

                        drawer.closeDrawers();
                        break;

                }



                return false;


            }

        });




        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        MenuItem item = navigationView.getMenu().findItem(R.id.menuDashBoard);
        item.setCheckable(true);
        item.setChecked(true);

        prevItem=item;

        */

        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_layout_id, new DashboardFragment(),"Dashboard");
        fragmentTransaction.commit();
        toggle = new ActionBarDrawerToggle(
                this, drawer,tb,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        getSupportActionBar().setTitle(PreferencedData.getPrefDeliveryCentre(this));

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawer.closeDrawers();


        getCallPermission();
    }


    private void addItemsToExpandableList() {

        mExpandableListTitle.add("Report");
        mExpandableListTitle.add("Accounts");
        mExpandableListTitle.add("Item");
        mExpandableListTitle.add("Vendor");
        mExpandableListTitle.add("Employee");
        mExpandableListTitle.add("Entries");


        // Adding child data
        List<String> Report = new ArrayList<String>();
        Report.add("Home");
        Report.add("Centre");
        Report.add("Purchase");
        Report.add("Expense");
        Report.add("Material Received");
        Report.add("Accounts Log");
        Report.add("Account Status");
        Report.add("Vendor Status");
        Report.add("Employee Status");
        Report.add("Franchise Payment");



        List<String> Accounts = new ArrayList<String>();
        Accounts.add("Amount Update");
        Accounts.add("Add");
        Accounts.add("Edit");;


        List<String> Item = new ArrayList<String>();
        Item.add("Add");
        Item.add("Edit");


        List<String> Vendor = new ArrayList<String>();
        Vendor.add("Add");
        Vendor.add("Edit");


        List<String> Employee = new ArrayList<String>();
        Employee.add("Attendance and Salary");
        Employee.add("Add");
        Employee.add("Edit");



        List<String> Entries = new ArrayList<String>();
        Entries.add("Purchase");
        Entries.add("Expense");
        Entries.add("Attendance");


        mExpandableListData.put(mExpandableListTitle.get(0), Report); // Header, Child data
        mExpandableListData.put(mExpandableListTitle.get(1), Accounts);
        mExpandableListData.put(mExpandableListTitle.get(2), Item);
        mExpandableListData.put(mExpandableListTitle.get(3), Vendor);
        mExpandableListData.put(mExpandableListTitle.get(4), Employee);
        mExpandableListData.put(mExpandableListTitle.get(5), Entries);


    }

    private void getCallPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user'textViewBuyer response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        101);


            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

         if(id==R.id.logOut)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Dashboard.this);
            builder.setTitle("LOGOUT ?");
            builder.setMessage("Are You Sure?");
            builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent i=new Intent(Dashboard.this,LoginActivity.class);
                    PreferencedData.clearPref(Dashboard.this);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}

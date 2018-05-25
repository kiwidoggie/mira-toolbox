package org.mira.companion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.crossfader.Crossfader;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileSettingDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialize.util.UIUtils;

import org.mira.companion.Activities.DebuggerActivity;
import org.mira.companion.Activities.PluginsFragment;
import org.mira.companion.Activities.RPCToolActivity;
import org.mira.companion.Activities.RemoteControllerActivity;
import org.mira.companion.Activities.SettingsActivity;
import org.mira.companion.Fragments.DevicesFragment;
import org.mira.companion.Fragments.DownloadsFragment;
import org.mira.companion.Fragments.FileManager;
import org.mira.companion.Fragments.HomeFragment;
import org.mira.companion.Fragments.LogsFragment;
import org.mira.companion.Fragments.StoreFragment;
import org.mira.companion.MiraAPIs.MiraApManager;
import org.mira.companion.MiraAPIs.MiraNetwork;
import org.mira.companion.Utils.CrossfadeWrapper;
import org.mira.companion.Utils.Helper;
import org.mira.companion.Utils.Prefs;

public class MainActivity extends AppCompatActivity {


    //TODO : Clean this code... too messed up :/

    private static final int PROFILE_SETTING = 1;
    private static final int CODE_WRITE_SETTINGS_PERMISSION =1 ;

    //save our header or result
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private MiniDrawer miniResult = null;
    private Crossfader crossFader;
    Fragment defaultFragment;
    Typeface tf;
    private static final int PROFILE_LOGOUT = 1;
    private static int IN_STAFF = 0;

    Toolbar toolbar;
    TextView mTitle, mSubtitle;

    private OnCheckedChangeListener onCheckedChangeListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beta);

        tf = Typeface.createFromAsset(this.getAssets(), "SF/SF-Pro-Display-Regular.otf");
   /*     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mira Companion");
*/

        setTitle(getString(R.string.app_name));
        toolbar = findViewById(R.id.tabanim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mTitle = toolbar.findViewById(R.id.toolbar_title);
        mSubtitle = toolbar.findViewById(R.id.toolbar_subtitle);

        setCustomTitle("Mira Companion", "Welcome buddy");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


       // setupMenuDrawer(savedInstanceState, toolbar);
        setMenu(savedInstanceState, toolbar);
    }


    public void changeFragment(Fragment f) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, f);
        //  ft.addToBackStack(null);
        ft.commit();
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


        switch (id)
        {



            case R.id.action_info:

                new AlertDialog.Builder(this)
                        .setTitle("Infos")
                        .setMessage(R.string.devs_name)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();

               break;

            case R.id.action_hotspot:

                if(MiraNetwork.isUserConnectedOnWifi(this))
                {
                    Toast.makeText(this, "You are already connected to a Wifi Network", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Helper
                            .showDialog(this, "You are currently not connected on a wifi network. Would you like to create an access point on this device ?",
                                    getString(R.string.no),
                                    getString(R.string.yes),
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        // NO

                                        }
                                    }, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        // YES
                                            checkForSystemSettingsPermision(MainActivity.this);
                                            // TODO : Rewrite this part
                                            MiraApManager miraApManager = new MiraApManager(MainActivity.this);
                                            miraApManager.turnWifiApOn();
                                            miraApManager.createNewNetwork("MIRA_5DF54", "0123456789");
                                        }
                                    }
                            );
                }

                break;

            case R.id.action_settings:
                Toast.makeText(this, "WIP :/", Toast.LENGTH_SHORT).show();

                        Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(settingsIntent);

                break;

            case R.id.action_remote:
                Toast.makeText(this, "WIP :/", Toast.LENGTH_SHORT).show();

                Intent remoteControllerIntent = new Intent(MainActivity.this, RemoteControllerActivity.class);
                startActivity(remoteControllerIntent);

                break;


            case R.id.action_debug:
                Toast.makeText(this, "WIP :/", Toast.LENGTH_SHORT).show();

                Intent debugActivity = new Intent(MainActivity.this, DebuggerActivity.class);
                startActivity(debugActivity);
                break;

            case R.id.action_plugins:
                Toast.makeText(this, "WIP :/", Toast.LENGTH_SHORT).show();

            /*    Intent debugActivity = new Intent(MainActivity.this, DebuggerActivity.class);
                startActivity(debugActivity);*/

                changeFragment(new PluginsFragment());


                break;

            case R.id.action_rpc_tool:
                Toast.makeText(this, "WIP :/", Toast.LENGTH_SHORT).show();

                Intent rpcActivity = new Intent(MainActivity.this, RPCToolActivity.class);
                startActivity(rpcActivity);
         //       setCustomTitle("Mira Companion", "RPC Tool");
            //    changeFragment(new RPCFragment());


                break;


            case R.id.action_power:
                setCustomTitle("Mira Companion", "Kernel Logs");
                Toast.makeText(this, "WIP :/", Toast.LENGTH_SHORT).show();
                break;


            default:
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void Launch_Logs_Activity(View view)
    {
        Toast.makeText(this, "WIP :/", Toast.LENGTH_SHORT).show();
        changeFragment(new LogsFragment());
    }

    public void setMenu(Bundle savedInstanceState, Toolbar toolbar)
    {
        defaultFragment = new HomeFragment();
        final IProfile profile = new ProfileDrawerItem().withName("Welcome, ").withEmail("Mira User").withIcon(getResources().getDrawable(R.mipmap.ic_launcher_foreground)).withTypeface(tf);

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimary)
                .addProfiles(
                        profile,
                        //don't ask but google uses 14dp for the add account icon in gmail but 20dp for the normal icons (like manage account)
                        new ProfileSettingDrawerItem().withName("Logout").withDescription("Leave Mira!").withIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_sign_out_alt).colorRes(R.color.colorPrimary)).withTypeface(tf).withIdentifier(PROFILE_LOGOUT)
                        //               new ProfileSettingDrawerItem().withName("Réglages").withIcon(new IconicsDrawable(this, FontAwesome.Icon.faw_cogs).colorRes(R.color.colorPrimary)).withIdentifier(PROFILE_SETTING)
                )
                .withSelectionListEnabledForSingleProfile(true) // pour avoir un seul compte
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_LOGOUT) {


                          //  FirebaseAuth.getInstance().signOut();
                            Prefs.edit().clear().commit();

                            Toast.makeText(getApplicationContext(), "You have successfully been logged off!", Toast.LENGTH_SHORT).show();

                            // DokitaApplication.clearApplicationData();
                            finish();

                          /*  IProfile newProfile = new ProfileDrawerItem().withNameShown(true).withName("Batman").withEmail("batman@gmail.com").withIcon(getResources().getDrawable(R.mipmap.ic_launcher));
                            if (headerResult.getProfiles() != null) {
                                //we know that there are 2 setting elements. set the new profile above them ;)
                                headerResult.addProfile(newProfile, headerResult.getProfiles().size() - 2);
                            } else {
                                headerResult.addProfiles(newProfile);
                            }*/
                        }
                        else   if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();




        //Create the drawer
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header

                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.action_menu_main).withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1).withTypeface(tf),
                        new PrimaryDrawerItem().withName(R.string.action_menu_store).withIcon(GoogleMaterial.Icon.gmd_burst_mode).withIdentifier(2).withTypeface(tf) /*.withBadgeStyle(new BadgeStyle(Color.BLUE, Color.WHITE)).withIdentifier(2).withSelectable(false) */,
                        new PrimaryDrawerItem().withName(R.string.action_menu_devices).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3).withTypeface(tf),
                        new PrimaryDrawerItem().withName(R.string.action_menu_downloads).withIcon(GoogleMaterial.Icon.gmd_cloud_download).withIdentifier(4).withTypeface(tf),
                        new PrimaryDrawerItem().withName(R.string.action_menu_fmanager).withIcon(GoogleMaterial.Icon.gmd_file_upload).withIdentifier(5).withTypeface(tf),
                        new DividerDrawerItem(),
                        new SwitchDrawerItem().withName(R.string.action_menu_night_mode).withIcon(GoogleMaterial.Icon.gmd_settings_brightness).withTypeface(tf).withChecked(false).withOnCheckedChangeListener(onCheckedChangeListener)
                        //         new ToggleDrawerItem().withName("Toggle").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)
                )

                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //check if the drawerItem is set.
                        //there are different reasons for the drawerItem to be null
                        //--> click on the header
                        //--> click on the footer
                        //those items don't contain a drawerItem

                        if (drawerItem != null) {
                            Intent intent = null;
                            //  Toast.makeText(getApplicationContext(), "Identifier : "+drawerItem.getIdentifier(), Toast.LENGTH_SHORT).show();

                            if (drawerItem.getIdentifier() == 1) {
                                setCustomTitle("Mira Companion", "Welcome buddy");
                                changeFragment(new HomeFragment());

                            } else if (drawerItem.getIdentifier() == 2) {

                                setCustomTitle("Mira Companion", "Mira Store - Latest Homebrews, Payloads, and Plugins");
                                changeFragment(new StoreFragment());

                            }
                            else if (drawerItem.getIdentifier() == 3) {
                                setCustomTitle("Mira Companion", "My Devices");
                                changeFragment(new DevicesFragment());
                            }
                            else if (drawerItem.getIdentifier() == 4) {

                                setCustomTitle("Mira Companion", "My Downloads");
                                changeFragment(new DownloadsFragment());
                            }
                            else if (drawerItem.getIdentifier() == 5) {

                                setCustomTitle("Mira Companion", "File Manager");
                                changeFragment(new FileManager());
                            }


                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();

        if (savedInstanceState == null) {
            // set the selection to the item with the identifier 11
            result.setSelection(1, false);

            //set the active profile
            headerResult.setActiveProfile(profile);
        //    result.openDrawer();
            //   checkSyncs();
        }


        changeFragment(new HomeFragment());
    }


    public void setupMenuDrawer(Bundle savedInstanceState, Toolbar toolbar)
    {

        onCheckedChangeListener = new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(IDrawerItem drawerItem, CompoundButton buttonView, boolean isChecked) {
                if (drawerItem instanceof Nameable) {
                    Log.i("material-drawer", "DrawerItem: " + ((Nameable) drawerItem).getName() + " - toggleChecked: " + isChecked);
                } else {
                    Log.i("material-drawer", "toggleChecked: " + isChecked);
                }
            }
        };


        // Create a few sample profile
        // NOTE you have to define the loader logic too. See the CustomApplication for more details
        final IProfile profile = new ProfileDrawerItem().withName("Demo Profile").withEmail("demo@mira.org").withIcon("https://avatars3.githubusercontent.com/u/1476232?v=3&s=460");

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorPrimaryDark)
                .withTranslucentStatusBar(false)
                .addProfiles(
                        profile,

               new ProfileSettingDrawerItem().withName("Add Account").withDescription("Add new PSN Account").withIcon(GoogleMaterial.Icon.gmd_add).withIdentifier(PROFILE_SETTING),
                        new ProfileSettingDrawerItem().withName("Manage Account").withIcon(GoogleMaterial.Icon.gmd_settings)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        //sample usage of the onProfileChanged listener
                        //if the clicked item has the identifier 1 add a new profile ;)
                        if (profile instanceof IDrawerItem && ((IDrawerItem) profile).getIdentifier() == PROFILE_SETTING) {

                        }

                        //false if you have not consumed the event and it should close the drawer
                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withTranslucentStatusBar(false)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.action_menu_main).withIcon(GoogleMaterial.Icon.gmd_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.action_menu_store).withIcon(FontAwesome.Icon.faw_app_store).withBadge("22").withBadgeStyle(new BadgeStyle(Color.RED, Color.RED)).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName(R.string.action_menu_devices).withIcon(FontAwesome.Icon.faw_gamepad).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.action_menu_downloads).withIcon(GoogleMaterial.Icon.gmd_cloud_download).withIdentifier(4),
                          new DividerDrawerItem(),
                        new SwitchDrawerItem().withName(R.string.action_menu_night_mode).withIcon(GoogleMaterial.Icon.gmd_settings_brightness).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)
               //         new ToggleDrawerItem().withName("Toggle").withIcon(Octicons.Icon.oct_tools).withChecked(true).withOnCheckedChangeListener(onCheckedChangeListener)
                ) // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(MainActivity.this, ((Nameable) drawerItem).getName().getText(MainActivity.this), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .withGenerateMiniDrawer(false)
               // .withGenerateMiniDrawer(true)
                .withSavedInstance(savedInstanceState)
                // build only the view of the Drawer (don't inflate it automatically in our layout which is done with .build())
                .buildView();

        //the MiniDrawer is managed by the Drawer and we just get it to hook it into the Crossfader
        miniResult = result.getMiniDrawer();

        //get the widths in px for the first and second panel
        int firstWidth = (int) UIUtils.convertDpToPixel(300, this);
        int secondWidth = (int) UIUtils.convertDpToPixel(72, this);

        //create and build our crossfader (see the MiniDrawer is also builded in here, as the build method returns the view to be used in the crossfader)
        //the crossfader library can be found here: https://github.com/mikepenz/Crossfader
        //
             crossFader = new Crossfader()
                .withContent(findViewById(R.id.crossfade_content))
                .withFirst(result.getSlider(), firstWidth)
                .withSecond(miniResult.build(this), secondWidth)
                .withSavedInstance(savedInstanceState)
                .build();

        //define the crossfader to be used with the miniDrawer. This is required to be able to automatically toggle open / close
        miniResult.withCrossFader(new CrossfadeWrapper(crossFader));

        //define a shadow (this is only for normal LTR layouts if you have a RTL app you need to define the other one
       // crossFader.getCrossFadeSlidingPaneLayout().setShadowResourceLeft(R.drawable.material_drawer_shadow_left);



    }



    public void setCustomTitle(String title, String subtitle) {
        mTitle.setText(title);
        mSubtitle.setText(subtitle);
        mTitle.setTypeface(tf);
        mSubtitle.setTypeface(tf);
    }


    public static void checkForSystemSettingsPermision(Activity context){
        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context);
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
        if (permission) {
            //do your code
        }  else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivityForResult(intent, MainActivity.CODE_WRITE_SETTINGS_PERMISSION);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_SETTINGS}, MainActivity.CODE_WRITE_SETTINGS_PERMISSION);
            }
        }
    }


    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_WRITE_SETTINGS_PERMISSION && Settings.System.canWrite(this)){
            Log.d("TAG", "CODE_WRITE_SETTINGS_PERMISSION success");
            //do your code
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_WRITE_SETTINGS_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do your code
        }
    }

}

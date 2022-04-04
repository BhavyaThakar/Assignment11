package com.example.contacts_music;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.contacts_music.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    CFragment cFragment = new CFragment();
    MFragment mFragment = new MFragment();
    public static int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());

        adapter.AddPage(cFragment,"Contacts");
        adapter.AddPage(mFragment,"Music Files");

        binding.viewPager.setAdapter(adapter);
        binding.tabLayout.setupWithViewPager(binding.viewPager);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            ArrayList<String > permissions = new ArrayList<>();
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            permissions.add(Manifest.permission.READ_CONTACTS);

            ArrayList<String> notGrantedPermission = new ArrayList<>();

            for (String i:permissions){
                if (ActivityCompat.checkSelfPermission(MainActivity.this,i)!= PackageManager.PERMISSION_GRANTED){
                    notGrantedPermission.add(i);
                }

            }
            if(!notGrantedPermission.isEmpty()){
                ActivityCompat.requestPermissions(this,notGrantedPermission.toArray(new String[notGrantedPermission.size()]), REQUEST_CODE);
            }
        }else {
            cFragment.showContacts();
            mFragment.showMusicList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                mFragment.showMusicList();
            }
            if (grantResults.length>0 && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                cFragment.showContacts();
            }
        }
    }
}
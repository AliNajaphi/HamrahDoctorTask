package ir.adrianet.uploaddownloadimage;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import ir.adrianet.uploaddownloadimage.Core.Presenter;
import ir.adrianet.uploaddownloadimage.General.TypeConvertor;
import ir.adrianet.uploaddownloadimage.General.mFragment;
import ir.adrianet.uploaddownloadimage.Views.FragMain;
import ir.adrianet.uploaddownloadimage.Views.PickerImage;

public class MainActivity extends AppCompatActivity {

    private static MainActivity global;
    public static MainActivity getGlobal()
    {
        return global;
    }
    public MainActivity()
    {
        global = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Presenter.get_global().OnCreate(this);
        FinishFragStartFrag(new FragMain());


    }


    private mFragment currentFragment;
    public void FinishFragStartFrag(mFragment newFragment)
    {
        currentFragment = newFragment;
        FragmentManager fm = MainActivity.getGlobal().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_show,R.anim.fade_hide,R.anim.fade_show,R.anim.fade_hide);
        ft.addToBackStack(newFragment.getClass().getSimpleName());
        ft.replace(R.id.RelMaster, newFragment,newFragment.getClass().getSimpleName());
        ft.commitAllowingStateLoss();
    }

    public void AddFrag(mFragment newFragment)
    {
        currentFragment = newFragment;
        FragmentManager fm = MainActivity.getGlobal().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_show,R.anim.fade_hide,R.anim.fade_show,R.anim.fade_hide);
        ft.add(R.id.RelMaster, newFragment);
        ft.commitAllowingStateLoss();
    }

    public void RemoveFrag(mFragment removeFragment,mFragment backFragment)
    {
        currentFragment = backFragment;
        FragmentManager fm = MainActivity.getGlobal().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.fade_show,R.anim.fade_hide,R.anim.fade_show,R.anim.fade_hide);
        ft.remove(removeFragment);
        ft.commitAllowingStateLoss();
    }

    private static final int PICK_FROM_GALLERY = 1001;

    PickerImage pickerFrag;
    public void PickFromGallery(PickerImage pickerFrag)
    {
        this.pickerFrag = pickerFrag;
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
            } else {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                resultFileLauncher.launch(photoPickerIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ActivityResultLauncher<Intent> resultFileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        pickerFrag.OnSelect();
                        Uri uri = result.getData().getData();
                        CreateBitmapBytes(uri);
                    }
                }
            });


    private void CreateBitmapBytes(Uri uri)
    {
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   Bitmap bitmap = TypeConvertor.getBitmapUri(uri);
                   byte[] dataByte = TypeConvertor.getBytesBitmap(bitmap);
                   MainActivity.getGlobal().runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           pickerFrag.onDone(dataByte,bitmap);
                       }
                   });

               } catch (Exception e) {
                   e.printStackTrace();
                   pickerFrag.onError(getResources().getString(R.string.pick_file_error));
               }
           }
       }).start();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PICK_FROM_GALLERY)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PickFromGallery(pickerFrag);
            } else {
                pickerFrag.onError(getResources().getString(R.string.permission_denied));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        currentFragment.onBackPress();
    }
}
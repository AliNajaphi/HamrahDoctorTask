package ir.adrianet.uploaddownloadimage.Views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ir.adrianet.uploaddownloadimage.Core.IService;
import ir.adrianet.uploaddownloadimage.Core.Request.ReqRegister;
import ir.adrianet.uploaddownloadimage.Core.Request.ReqUpload;
import ir.adrianet.uploaddownloadimage.Core.Service.UploadDownloadService;
import ir.adrianet.uploaddownloadimage.General.HashData;
import ir.adrianet.uploaddownloadimage.General.Setting;
import ir.adrianet.uploaddownloadimage.General.TypeConvertor;
import ir.adrianet.uploaddownloadimage.General.mAnimation;
import ir.adrianet.uploaddownloadimage.General.mFragment;
import ir.adrianet.uploaddownloadimage.MainActivity;
import ir.adrianet.uploaddownloadimage.R;
import ir.adrianet.uploaddownloadimage.Views.Model.UploadModel;

public class FragMain extends mFragment implements PickerImage {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View parent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View parent = inflater.inflate(R.layout.frag_main, container, false);
        this.parent = parent;

        return parent;
    }

    List<UploadModel> uploadModelList;
    AdapterUpload adapterUpload;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uploadModelList = new ArrayList<>();
        adapterUpload = new AdapterUpload(uploadModelList,this);
        Setting.SetSplitRecyclerAdapter(parent.findViewById(R.id.RVUploadItem),adapterUpload,3);

        parent.findViewById(R.id.btnUpload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getGlobal().PickFromGallery(FragMain.this);
            }
        });

    }

    @Override
    public void OnSelect() {
        OnProgressRegister();
    }

    @Override
    public void onDone(byte[] bytesFile,Bitmap bitmap) {
        RegisterFile(bytesFile,bitmap);
    }

    @Override
    public void onError(String error) {
        OnNormal();
        TextView txtError = parent.findViewById(R.id.txtErrorRegister);
        txtError.setVisibility(View.VISIBLE);
        txtError.setText(error);
        mAnimation.tremor(txtError);
    }

    private void OnNormal()
    {
        parent.findViewById(R.id.btnUpload).setClickable(true);
        parent.findViewById(R.id.progressRegisterAV).setVisibility(View.GONE);
        parent.findViewById(R.id.imgUpload).setVisibility(View.VISIBLE);
        parent.findViewById(R.id.txtUpload).setVisibility(View.VISIBLE);
    }

    private void OnProgressRegister()
    {
        parent.findViewById(R.id.btnUpload).setClickable(false);
        parent.findViewById(R.id.imgUpload).setVisibility(View.GONE);
        parent.findViewById(R.id.txtUpload).setVisibility(View.GONE);
        parent.findViewById(R.id.txtErrorRegister).setVisibility(View.GONE);
        parent.findViewById(R.id.progressRegisterAV).setVisibility(View.VISIBLE);
    }

    private void RegisterFile(byte[] bytesFile,Bitmap bitmap)
    {
        String data = TypeConvertor.getStringBytes(bytesFile);
        String sha256 = HashData.SHA256(data);

        IService<ReqRegister,String> service = new IService<ReqRegister, String>() {
            @Override
            public void SendRequest(ReqRegister req) {
                UploadDownloadService.RegisterImage(req,this);
            }

            @Override
            public void OnSucceed(String result) {
                BeginUpload(data,sha256,bitmap);
            }

            @Override
            public void OnError(String error, int statusCode) {
                FragMain.this.onError(error);
            }
        };
        service.SendRequest(new ReqRegister(sha256,data.length()));

    }


    private void BeginUpload(String fullData,String sha256,Bitmap bitmap)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int counter = fullData.length()/ Setting.getChunkSize();
                int mod = fullData.length() % Setting.getChunkSize();
                int i;

                final List<ReqUpload> uploadList = new ArrayList<>();
                for (i=0;i<counter;i++)
                {
                    int start = i * Setting.getChunkSize();
                    int end = (i+1) * Setting.getChunkSize();
                    String chunkData = fullData.substring(start,end);
                    uploadList.add(new ReqUpload(i,Setting.getChunkSize(),chunkData));
                }

                if (mod > 0)
                {
                    int start = i * Setting.getChunkSize();
                    int end = fullData.length();
                    String chunkData = fullData.substring(start,end);
                    uploadList.add(new ReqUpload(i,mod,chunkData));
                }

                MainActivity.getGlobal().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        OnNormal();
                        AddToRecycler(new UploadModel(uploadList,sha256,0,bitmap));
                    }
                });
            }
        }).start();
    }

    private void AddToRecycler(UploadModel uploadModel)
    {
        uploadModelList.add(uploadModel);
        adapterUpload.notifyItemChanged(uploadModelList.size()-1);
    }


    @Override
    public void onBackPress() {
        super.onBackPress();
        MainActivity.getGlobal().finish();
    }
}

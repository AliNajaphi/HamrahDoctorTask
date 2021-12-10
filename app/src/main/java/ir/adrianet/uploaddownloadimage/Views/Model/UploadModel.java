package ir.adrianet.uploaddownloadimage.Views.Model;

import android.graphics.Bitmap;

import java.util.List;

import ir.adrianet.uploaddownloadimage.Core.Request.ReqUpload;

public class UploadModel {

    private List<ReqUpload> uploadList;
    private String sha256;
    private int indexNewUpload;
    private Bitmap bitmap;
    private boolean isFinish = false;

    public UploadModel(List<ReqUpload> uploadList, String sha256, int indexNewUpload, Bitmap bitmap) {
        this.uploadList = uploadList;
        this.sha256 = sha256;
        this.indexNewUpload = indexNewUpload;
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getIndexNewUpload() {
        return indexNewUpload;
    }

    public void setIndexNewUpload(int indexNewUpload) {
        this.indexNewUpload = indexNewUpload;
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public List<ReqUpload> getUploadList() {
        return uploadList;
    }

    public void setUploadList(List<ReqUpload> uploadList) {
        this.uploadList = uploadList;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }
}

package ir.adrianet.uploaddownloadimage.Views.Model;

import android.graphics.Bitmap;

import java.util.List;

import ir.adrianet.uploaddownloadimage.Core.Request.ReqUpload;

public class UploadModel {

    private int idPosition;
    private List<ReqUpload> uploadList;
    private String sha256;
    private int indexNewUpload;
    private Bitmap bitmap;
    private int state = 0;//0=progress , 1=pause , 2=finish

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

    public int getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

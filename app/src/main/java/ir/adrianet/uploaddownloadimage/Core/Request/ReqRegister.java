package ir.adrianet.uploaddownloadimage.Core.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

import ir.adrianet.uploaddownloadimage.General.Setting;

public class ReqRegister {

    @JsonProperty("sha256")
    private String sha256;
    @JsonProperty("size")
    private int size;
    @JsonProperty("chunk_size")
    private int chunk_size;


    public ReqRegister(String sha256, int size) {
        this.sha256 = sha256;
        this.size = size;
        this.chunk_size = Setting.getChunkSize();
    }

    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getChunk_size() {
        return chunk_size;
    }

    public void setChunk_size(int chunk_size) {
        this.chunk_size = chunk_size;
    }
}

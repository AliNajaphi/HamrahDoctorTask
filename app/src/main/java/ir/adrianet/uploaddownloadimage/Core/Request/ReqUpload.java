package ir.adrianet.uploaddownloadimage.Core.Request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReqUpload {

    @JsonProperty("id")
    private int id;
    @JsonProperty("size")
    private int size;
    @JsonProperty("data")
    private String data;


    public ReqUpload(int id, int size, String data) {
        this.id = id;
        this.size = size;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}

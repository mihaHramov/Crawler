package aaa.bbb.ccc.crawler.domain.model;

import java.io.Serializable;
import java.util.List;

public class PageOfSite implements Serializable {
    private String mAddress;
    private Long mTime;
    private List<String> mImages;
    private List<String> mUrls;

    public List<String> getImages() {
        return mImages;
    }

    public void setImages(List<String> mImages) {
        this.mImages = mImages;
    }

    public List<String> getUrls() {
        return mUrls;
    }

    public void setUrls(List<String> mUrls) {
        this.mUrls = mUrls;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAdres) {
        this.mAddress = mAdres;
    }

    public Long getTime() {
        return mTime;
    }

    public void setTime(Long l) {
        mTime = l;
    }
}

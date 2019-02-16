package aaa.bbb.ccc.crawler.domain.model;

import java.io.Serializable;
import java.util.List;

public class Site implements Serializable {
    private List<PageOfSite> mPages;
    private String mUrl;


    public Site() {
    }

    public Site(List<PageOfSite> mPages) {
        this.mPages = mPages;
    }

    public List<PageOfSite> getPages() {
        return mPages;
    }

    public void setPages(List<PageOfSite> mPages) {
        this.mPages = mPages;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}

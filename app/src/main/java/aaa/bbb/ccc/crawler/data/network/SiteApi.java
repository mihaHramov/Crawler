package aaa.bbb.ccc.crawler.data.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import aaa.bbb.ccc.crawler.domain.model.PageOfSite;
import aaa.bbb.ccc.crawler.domain.model.Site;
import aaa.bbb.ccc.crawler.domain.utils.UrlConverter;
import rx.Observable;
import rx.functions.Func1;

public class SiteApi implements ISiteApi {

    @Override
    public Observable<Site> getSite(String query, Integer countOfNode, Integer countOfPage) {
        return Observable.just(query)
                .map(UrlConverter::trimUrlAddress)
                .flatMap((Func1<String, Observable<Site>>) site -> getSitePager(site, countOfNode, countOfPage));
    }

    private Observable<Site> getSitePager(String query, Integer countOfNode, Integer countOfPage) {
        return Observable.fromCallable(() -> {
            PageOfSite rootPage = getPage(query, countOfNode, query);
            List<String> urls = rootPage.getUrls();
            List<PageOfSite> pages = new ArrayList<>();
            pages.add(rootPage);
            for (int i = 1; i < countOfPage; i++) {//перебераю все доступные страницы
                if (urls.size() >= i) {
                    PageOfSite page1 = getPage(urls.get(i), countOfNode, query);
                    pages.add(page1);
                    urls.addAll(page1.getUrls());
                } else break;
            }
            Site site = new Site(pages);
            site.setUrl(query);
            if (site.getPages().size() > 0) {
                return site;
            } else {
                throw new Exception();
            }
        });
    }


    private PageOfSite getPage(String url, Integer level, String domen) throws IOException {
        Date oldDate = new Date(); //старое время в миллисекундах
        PageOfSite page1 = new PageOfSite();
        page1.setUrls(new ArrayList<>());
        page1.setImages(new ArrayList<>());
        page1.setAddress(url);
        Document document = Jsoup.connect(url).get();
        getChild(document.body(), level, page1, domen);
        Date newDate = new Date(); //текущее время
        page1.setTime((newDate.getTime() - oldDate.getTime()) / 1000);
        return page1;
    }

    private void getChild(Element element, Integer level, PageOfSite page, String domen) {
        if (level == 0) return;
        for (Element tempElement : element.children()) {
            page.setUrls(updateList(page.getUrls(), tempElement, "a", "href", domen));
            page.setImages(updateList(page.getImages(), tempElement, "img", "src", domen));
            getChild(tempElement, level - 1, page, domen);
        }
    }

    private List<String> updateList(List<String> list, Element tempElement, String tag, String atr, String domen) {
        if (tempElement.tagName().equals(tag)) {
            String imageResult = UrlConverter.getRealPathOfLink(tempElement.attributes().get(atr), domen);
            if (!imageResult.isEmpty()) {
                list.add(imageResult);
            }
        }
        return list;
    }
}

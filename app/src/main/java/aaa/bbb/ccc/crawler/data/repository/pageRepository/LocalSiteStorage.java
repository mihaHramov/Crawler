package aaa.bbb.ccc.crawler.data.repository.pageRepository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import aaa.bbb.ccc.crawler.domain.model.PageOfSite;
import aaa.bbb.ccc.crawler.domain.model.Site;
import aaa.bbb.ccc.crawler.domain.utils.UrlConverter;
import rx.Observable;
import rx.functions.Func1;

public class LocalSiteStorage extends SQLiteOpenHelper implements ILocalStorage {
    private static final int DATABASE_VERSION = 13;
    private static final String IMAGE_TABLE = "image";
    private static final String IMAGE_ID = "id";
    private static final String IMAGE_URL = "url";
    private static final String IMAGE_ID_PAGE = "id_age";

    private static final String DATABASE_NAME = "localStorage";
    private static final String PAGE_TABLE = "page";
    private static final String PAGE_NAME = "name";
    private static final String PAGE_ID = "id";
    private static final String PAGE_ID_SITE = "site_id";
    private static final String PAGE_TIME = "time_id";

    private static final String SITE_TABLE = "site";
    private static final String SITE_ID = "id";
    private static final String SITE_NAME = "name";


    public LocalSiteStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public Observable<Site> getSiteFromLocalStorage(String url) {
        return Observable.just(url).map(UrlConverter::trimUrlAddress)
                .flatMap((Func1<String, Observable<Site>>) this::getSite);
    }

    private Observable<Site> getSite(String url) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + SITE_ID + " FROM " + SITE_TABLE + " WHERE " + SITE_NAME + " = '" + url + "'";
        String queryOfPage = "SELECT * FROM " + PAGE_TABLE + " WHERE " + PAGE_ID_SITE + " IN (" + query + ")";
        Cursor cursorPager = db.rawQuery(queryOfPage, null);
        Map<Integer, PageOfSite> mapOfPager = new Hashtable<>();
        if (cursorPager.moveToFirst()) {
            Integer indexCollId = cursorPager.getColumnIndex(PAGE_ID);
            Integer indexCollName = cursorPager.getColumnIndex(PAGE_NAME);
            Integer indexCollTime = cursorPager.getColumnIndex(PAGE_TIME);
            String imageQeryParametr = "";
            do {
                Integer id = cursorPager.getInt(indexCollId);
                String name = cursorPager.getString(indexCollName);
                Integer time = cursorPager.getInt(indexCollTime);
                PageOfSite page = new PageOfSite();

                page.setAddress(UrlConverter.trimUrlAddress(name));
                page.setTime(time.longValue());
                page.setImages(new ArrayList<>());
                mapOfPager.put(id, page);
                imageQeryParametr = imageQeryParametr.concat(id + ",");
            } while (cursorPager.moveToNext());

            if (!imageQeryParametr.isEmpty()) {
                String idOfPager = imageQeryParametr.substring(0, imageQeryParametr.length() - 1);
                String queryOfImage = "SELECT * FROM " + IMAGE_TABLE + " WHERE " + IMAGE_ID_PAGE + " IN (" + idOfPager + ")";
                Cursor imageCursor = db.rawQuery(queryOfImage, null);
                if (imageCursor.moveToFirst()) {
                    Integer indexOfIdPage = imageCursor.getColumnIndex(IMAGE_ID_PAGE);
                    Integer indexOfName = imageCursor.getColumnIndex(IMAGE_URL);
                    do {
                        Integer idPager = imageCursor.getInt(indexOfIdPage);
                        String name = imageCursor.getString(indexOfName);
                        PageOfSite curentPage = mapOfPager.get(idPager);
                        List<String> tempListImage = curentPage.getImages();
                        tempListImage.add(name);
                        curentPage.setImages(tempListImage);
                    } while (imageCursor.moveToNext());
                }
                imageCursor.close();
            }
            cursorPager.close();
        }
        db.close();
        Site site = new Site();
        List<PageOfSite> pageOfSites = new ArrayList<>();
        for (Map.Entry<Integer, PageOfSite> entry : mapOfPager.entrySet()) {
            pageOfSites.add(entry.getValue());
        }

        site.setPages(pageOfSites);
        return site.getPages().size() > 0 ? Observable.just(site) : Observable.error(new Throwable());
    }

    @Override
    public void setSite(Site site) {
        SQLiteDatabase db = getWritableDatabase();
        //удалил старые даные
        String query = "SELECT " + SITE_ID + " FROM " + SITE_TABLE + " WHERE " + SITE_NAME + " = '" + site.getUrl() + "'";


        String queryOfPage = "SELECT " + PAGE_ID + " FROM " + PAGE_TABLE + " WHERE " + PAGE_ID_SITE + " IN (" + query + ")";

        String deleteImage = "DELETE FROM " + IMAGE_TABLE + " WHERE " + IMAGE_ID_PAGE + " IN (" + queryOfPage + ")";
        String deletePage = "DELETE  FROM " + PAGE_TABLE + " WHERE " + PAGE_ID_SITE + " IN (" + query + ")";

        String siteDelete = "DELETE  FROM " + SITE_TABLE + " WHERE " + SITE_NAME + " = '" + site.getUrl() + "'";

        db.rawQuery(deleteImage, null).close();//delete images
        db.rawQuery(deletePage, null).close();//delete pages
        db.rawQuery(siteDelete, null).close();//delete site
        //добавил новые даные
        Integer idSite = createSite(site, db);
        for (PageOfSite pager : site.getPages()) {
            Integer idPager = this.createPager(pager, idSite, db);
            for (String image : pager.getImages()) {
                this.createImage(image, idPager, db);
            }
        }
        db.close();
    }

    private Integer createImage(String image, Integer idPager, SQLiteDatabase db) {
        ContentValues siteCv = new ContentValues();
        siteCv.put(IMAGE_URL, image);
        siteCv.put(IMAGE_ID_PAGE, idPager);
        return (int) db.insert(IMAGE_TABLE, null, siteCv);
    }

    private Integer createPager(PageOfSite pageOfSite, Integer idSite, SQLiteDatabase db) {
        ContentValues siteCv = new ContentValues();
        siteCv.put(PAGE_NAME, pageOfSite.getAddress());
        siteCv.put(PAGE_ID_SITE, idSite);
        return (int) db.insert(PAGE_TABLE, null, siteCv);
    }

    private Integer createSite(Site site, SQLiteDatabase db) {
        ContentValues siteCv = new ContentValues();
        siteCv.put(SITE_NAME, UrlConverter.trimUrlAddress(site.getUrl()));
        return (int) db.insert(SITE_TABLE, null, siteCv);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_IMAGE_TABLE = "CREATE TABLE " + IMAGE_TABLE + "("
                + IMAGE_ID + " INTEGER PRIMARY KEY,"
                + IMAGE_ID_PAGE + " INTEGER,"
                + IMAGE_URL + " TEXT)";

        String CREATE_PAGE_TABLE = "CREATE TABLE " + PAGE_TABLE + "("
                + PAGE_ID + " INTEGER PRIMARY KEY,"
                + PAGE_ID_SITE + " INTEGER,"
                + PAGE_TIME + " INTEGER,"
                + PAGE_NAME + " TEXT)";

        String CREATE_SITE_TABLE = "CREATE TABLE " + SITE_TABLE + "("
                + SITE_ID + " INTEGER PRIMARY KEY,"
                + SITE_NAME + " TEXT)";


        db.execSQL(CREATE_IMAGE_TABLE);
        db.execSQL(CREATE_PAGE_TABLE);
        db.execSQL(CREATE_SITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + IMAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SITE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PAGE_TABLE);
        onCreate(db);
    }
}

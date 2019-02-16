package aaa.bbb.ccc.crawler.data.repository.settingsRepository;

import android.content.Context;
import android.content.SharedPreferences;

import rx.Observable;

public class SettingsRepository implements ISettingsRepository {
    private SharedPreferences sp;
    private static final String MY_SETTINGS = SettingsRepository.class.getCanonicalName();
    private static final String COUNT_OF_NODE = "COUNT_OF_NODE";
    private static final String COUNT_OF_PAGE = "COUNT_OF_PAGE";
    private Integer DEFAULT_PAGE = 1;
    private Integer DEFAULT_NODE = 1;

    public SettingsRepository(Context context) {
        sp = context.getSharedPreferences(MY_SETTINGS, Context.MODE_PRIVATE);
    }

    @Override
    public void setCountOfPage(Integer countOfPage) {
        this.setIntVal(COUNT_OF_PAGE, countOfPage);
    }

    private void setIntVal(String key, Integer val) {
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt(key, val);
        ed.apply();
    }

    @Override
    public void setCountOfNode(Integer countOfNode) {
        this.setIntVal(COUNT_OF_NODE, countOfNode);
    }

    @Override
    public Observable<Integer> getCountOfPage() {
        return Observable.fromCallable(() -> sp.getInt(COUNT_OF_PAGE, DEFAULT_PAGE));
    }

    @Override
    public Observable<Integer> getCountOfNode() {
        return Observable.fromCallable(() -> sp.getInt(COUNT_OF_NODE, DEFAULT_NODE));
    }


}

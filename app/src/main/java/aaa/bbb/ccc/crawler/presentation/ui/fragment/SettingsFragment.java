package aaa.bbb.ccc.crawler.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import aaa.bbb.ccc.crawler.R;
import aaa.bbb.ccc.crawler.data.repository.settingsRepository.SettingsRepository;
import aaa.bbb.ccc.crawler.presentation.mvp.settingsFragment.ISettingsView;
import aaa.bbb.ccc.crawler.presentation.mvp.settingsFragment.SettingsPresenter;

public class SettingsFragment extends MvpAppCompatDialogFragment implements ISettingsView {

    @InjectPresenter
    SettingsPresenter presenter;

    @ProvidePresenter
    SettingsPresenter provideSettingsPresenter() {
        presenter = new SettingsPresenter(new SettingsRepository(getActivity()));
        presenter.init();
        return presenter;
    }

    private TextView mNodeTextView;
    private TextView mPageTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.settings_layout, container, false);
        v.findViewById(R.id.plusNode).setOnClickListener(view -> presenter.plusCountNode());
        v.findViewById(R.id.plusPage).setOnClickListener(view -> presenter.plusCountPage());
        v.findViewById(R.id.minusNode).setOnClickListener(view -> presenter.minusCountNode());
        v.findViewById(R.id.minusPage).setOnClickListener(view -> presenter.minusCountPage());
        mNodeTextView = v.findViewById(R.id.nodeText);
        mPageTextView = v.findViewById(R.id.pageText);
        return v;
    }

    @Override
    public void showCountOfNode(Integer integer) {
        mNodeTextView.setText(String.valueOf(integer));
    }

    @Override
    public void showCountOfPage(Integer integer) {
        mPageTextView.setText(String.valueOf(integer));
    }
}

package aaa.bbb.ccc.crawler.presentation.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatDialogFragment;

import aaa.bbb.ccc.crawler.R;

public class ErrorInfoFragment extends MvpAppCompatDialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private String mErrorMessage;


    public static ErrorInfoFragment newInstance(String error) {
        ErrorInfoFragment fragment = new ErrorInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, error);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mErrorMessage = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_error_info, container, false);
        ((TextView) v.findViewById(R.id.error_field)).setText(mErrorMessage);
        return v;
    }

}

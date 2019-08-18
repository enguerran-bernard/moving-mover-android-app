package com.movingmover.oem.movingmover.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.movingmover.oem.movingmover.R;

public class ForgottenPasswordFragment extends LoginFragment {

    private EditText mEMailEditText;
    private Button mConfirmButton;
    private Button mBackButton;

    private View.OnClickListener mConfirmButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((LoginActivity) getActivity()).switchToFragment(LoginActivity.FRAGMENT_CONNECTION);
        }
    };

    private View.OnClickListener mBackButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((LoginActivity) getActivity()).switchToFragment(LoginActivity.FRAGMENT_CONNECTION);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_forgotten_password,
                container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEMailEditText = view.findViewById(R.id.email_edit);
        mConfirmButton = view.findViewById(R.id.confirm_button);
        mBackButton = view.findViewById(R.id.back_button);
        mConfirmButton.setOnClickListener(mConfirmButtonOnClickListener);
        mBackButton.setOnClickListener(mBackButtonOnClickListener);
    }
}

package com.movingmover.oem.movingmover.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.movingmover.oem.movingmover.R;

public class CreateAccountFragment extends LoginFragment {
    private EditText mEMailEditText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mPasswordEditText;
    private Button mValidateAccountButton;
    private Button mBackButton;

    private View.OnClickListener mValidateAccountButtonOnClickListener = new View.OnClickListener() {
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
        View view = inflater.inflate(R.layout.fragment_create_account,
                container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEMailEditText = view.findViewById(R.id.email_edit);
        mFirstNameEditText = view.findViewById(R.id.first_name_edit);
        mLastNameEditText = view.findViewById(R.id.last_name_edit);
        mPasswordEditText = view.findViewById(R.id.password_edit);
        mValidateAccountButton = view.findViewById(R.id.validate_account_button);
        mBackButton = view.findViewById(R.id.back_button);
        mValidateAccountButton.setOnClickListener(mValidateAccountButtonOnClickListener);
        mBackButton.setOnClickListener(mBackButtonOnClickListener);
    }

}

package com.movingmover.oem.movingmover.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.movingmover.oem.movingmover.R;
import com.movingmover.oem.movingmover.webservice.ServiceControllerListener;

public class ConnectionFragment extends LoginFragment {
    private EditText mNameEditText;
    private EditText mPasswordEditText;
    private Button mConnectionButton;
    private TextView mForgottenPasswordTextView;
    private TextView mErrorTextView;
    private Button mCreateAccountButton;
    private EditText mIPValueEditText;

    private View.OnClickListener mConnectionButtonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            mErrorTextView.setText("");
            ((LoginActivity) getActivity()).getController().connect(mNameEditText.getText().toString().trim(),
                    mPasswordEditText.getText().toString().trim());
        }
    };

    private View.OnClickListener mCreateAccountButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((LoginActivity) getActivity()).switchToFragment(LoginActivity.FRAGMENT_CREATE_ACCOUNT);
        }
    };

    private View.OnClickListener mForgottenPasswordOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((LoginActivity) getActivity()).switchToFragment(LoginActivity.FRAGMENT_FORGOTTEN_PASSWORD);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_connection,
                container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNameEditText = view.findViewById(R.id.login_edit);
        mPasswordEditText = view.findViewById(R.id.password_edit);
        mConnectionButton = view.findViewById(R.id.connection_button);
        mForgottenPasswordTextView = view.findViewById(R.id.forgotten_password);
        mErrorTextView = view.findViewById(R.id.error_text);
        mCreateAccountButton = view.findViewById(R.id.create_account_button);
        mIPValueEditText = view.findViewById(R.id.ip_value_edit_text);
        mConnectionButton.setOnClickListener(mConnectionButtonOnClickListener);
        mForgottenPasswordTextView.setOnClickListener(mForgottenPasswordOnClickListener);
        mCreateAccountButton.setOnClickListener(mCreateAccountButtonOnClickListener);
    }

    @Override
    public void onConnectionResult(boolean res, int resCode) {
        if (!res) {
            Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake_error);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mConnectionButton.setBackground(getActivity().getResources().getDrawable(R.drawable.button_selector_red));
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mConnectionButton.setBackground(getActivity().getResources().getDrawable(R.drawable.button_selector));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mConnectionButton.startAnimation(animation);
            if (resCode == ServiceControllerListener.SERVER_NOT_FOUND) {
                mErrorTextView.setText(R.string.verify_internet_connection);
            } else {
                mErrorTextView.setText(R.string.wrong_credentials);
            }
        }
    }
}

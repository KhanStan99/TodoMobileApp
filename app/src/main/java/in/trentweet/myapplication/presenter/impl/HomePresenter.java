package in.trentweet.myapplication.presenter.impl;

import android.content.Context;
import android.widget.EditText;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import in.trentweet.myapplication.R;
import in.trentweet.myapplication.presenter.base.IMainActivityPresenter;
import in.trentweet.myapplication.service.INetworkInterface;
import in.trentweet.myapplication.service.NetworkNetwork;
import in.trentweet.myapplication.utilities.AppConstant;
import in.trentweet.myapplication.utilities.AppSharedPreferences;
import in.trentweet.myapplication.utilities.Widget;
import in.trentweet.myapplication.view.Home;

public class HomePresenter implements IMainActivityPresenter {

    private Home _view;
    private Context _context;
    private Widget widget;
    private NetworkNetwork networkNetworkService;
    private INetworkInterface.cbJsonObjectRequest cbSignUp;
    private INetworkInterface.cbJsonObjectRequest cbLogin;
    private RelativeLayout _myLayout;
    private AppSharedPreferences sharedPreferences;

    public HomePresenter(Home view, Context context, RelativeLayout myLayout) {
        _view = view;
        _context = context;
        _myLayout = myLayout;
        widget = new Widget(_context);
        networkNetworkService = new NetworkNetwork(_context);
        sharedPreferences = new AppSharedPreferences(_context);
    }

    @Override
    public void SubscribeCallBacks() {
        cbSignUp = new INetworkInterface.cbJsonObjectRequest() {
            @Override
            public void onResponse(JSONObject response) {
                widget.showSnackBar(_myLayout, _view.getString(R.string.txt_signup_success),
                        AppConstant.SNACK_BAR_LONG, AppConstant.SNACK_BAR_SUCCESS);
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                widget.showSnackBar(_myLayout, "Error: " + errorCode + "\nMessage:" +
                        errorMsg, AppConstant.SNACK_BAR_LONG, AppConstant.SNACK_BAR_ERROR);
            }
        };

        cbLogin = new INetworkInterface.cbJsonObjectRequest() {
            @Override
            public void onResponse(JSONObject response) {
                widget.showSnackBar(_myLayout, response.toString(), AppConstant.SNACK_BAR_LONG,
                        AppConstant.SNACK_BAR_SUCCESS);
                sharedPreferences.saveId(response.optInt("id"));
                sharedPreferences.saveToken(response.optString("uniqueId"));
                sharedPreferences.saveUserName(response.optString("lastName"));
                _view.loginSuccess();
            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                widget.showSnackBar(_myLayout, "Error: " + errorCode + "\nMessage:" +
                        errorMsg, AppConstant.SNACK_BAR_LONG, AppConstant.SNACK_BAR_ERROR);
            }
        };
    }

    @Override
    public void UnSubscribeCallBacks() {
        cbSignUp = null;
        cbLogin = null;
    }


    @Override
    public void doSignUp(EditText etFName, EditText etLName, EditText etEmail,
                         EditText etPassword) {
        widget.hideKeyBoard(etFName);

        if (isSignUpFormVerified(etFName, etLName, etEmail, etPassword)) {

            JSONObject input = new JSONObject();

            try {
                input.put("firstName", widget.getTextFromEditText(etFName));
                input.put("lastName", widget.getTextFromEditText(etLName));
                input.put("email", widget.getTextFromEditText(etEmail));
                input.put("password", widget.getTextFromEditText(etPassword));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            networkNetworkService.JsonObjectRequest(AppConstant.POST, "MyUsers/Signup", input, cbSignUp);
        }
    }

    public void loginUser(EditText etEmailLogin, EditText etPasswordLogin) {
        widget.hideKeyBoard(etEmailLogin);

        if (isLoginFormVerified(etEmailLogin, etPasswordLogin)) {
            JSONObject input = new JSONObject();

            try {
                input.put("email", widget.getTextFromEditText(etEmailLogin));
                input.put("password", widget.getTextFromEditText(etPasswordLogin));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            networkNetworkService.JsonObjectRequest(AppConstant.POST, "MyUsers/login", input, cbLogin);
        }
    }

    private Boolean isLoginFormVerified(EditText etEmail, EditText etPassword) {
        if (!widget.isValidEmail(etEmail) || widget.isLessThan(etPassword, 5)) {

            if (!widget.isValidEmail(etEmail))
                etEmail.setError("Enter Correct Email Address");

            if (widget.isLessThan(etEmail, 5))
                etPassword.setError("Minimum 6 characters");

            return false;
        } else {
            return true;
        }
    }

    private Boolean isSignUpFormVerified(EditText etFName, EditText etLName, EditText etEmail,
                                         EditText etPassword) {

        if (widget.isLessThan(etFName, 2) || widget.isLessThan(etLName, 2) ||
                !widget.isValidEmail(etEmail) || widget.isLessThan(etPassword, 5)) {

            if (widget.isLessThan(etFName, 2))
                etFName.setError("Minimum 3 characters");

            if (widget.isLessThan(etLName, 2))
                etLName.setError("Minimum 3 characters");

            if (!widget.isValidEmail(etEmail))
                etEmail.setError("Enter Correct Email Address");

            if (widget.isLessThan(etPassword, 5))
                etPassword.setError("Minimum 6 characters");

            return false;

        } else {
            return true;
        }

    }
}

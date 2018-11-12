package in.trentweet.myapplication.presenter.base;

import android.app.Activity;
import android.widget.EditText;
import android.widget.RelativeLayout;

public interface IMainActivityPresenter extends IBasePresenter  {


    void doSignUp(EditText etFName, EditText etLName, EditText etEmail, EditText etPassword);
}

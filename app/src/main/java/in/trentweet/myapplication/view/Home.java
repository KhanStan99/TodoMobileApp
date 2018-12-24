package in.trentweet.myapplication.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.trentweet.myapplication.R;
import in.trentweet.myapplication.presenter.impl.HomePresenter;

public class Home extends AppCompatActivity {

    @BindView(R.id.etFName)
    EditText etFName;
    @BindView(R.id.etLName)
    EditText etLName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.myLayout)
    RelativeLayout myLayout;
    @BindView(R.id.flipView)
    EasyFlipView flipView;

    @BindView(R.id.etEmailLogin)
    EditText etEmailLogin;
    @BindView(R.id.etPasswordLogin)
    EditText etPasswordLogin;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    private HomePresenter _presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        _presenter = new HomePresenter(Home.this, getApplicationContext(), myLayout);
    }

    @OnClick(R.id.btnSignUp)
    void submit() {
        _presenter.doSignUp(etFName, etLName, etEmail, etPassword);
    }

    @OnClick(R.id.txtToSignup)
    void flipView1() {
        flipView.flipTheView();
    }

    @OnClick(R.id.txtToLogin)
    void flipView() {
        flipView.flipTheView();
    }

    @OnClick(R.id.btnLogin)
    void loginUser() {
        _presenter.loginUser(etEmailLogin, etPasswordLogin);
    }

    @Override
    protected void onStart() {
        super.onStart();
        _presenter.SubscribeCallBacks();
    }

    @Override
    protected void onStop() {
        super.onStop();
        _presenter.UnSubscribeCallBacks();
    }

    public void loginSuccess() {
        Intent intent = new Intent(Home.this, TodoTask.class);
        startActivity(intent);
        finish();
    }
}

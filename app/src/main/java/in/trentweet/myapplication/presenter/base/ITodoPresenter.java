package in.trentweet.myapplication.presenter.base;

import android.widget.EditText;

public interface ITodoPresenter extends IBasePresenter {


    void getTasks();

    void createTask(EditText etTaskTitle, EditText etTaskDesc);
}

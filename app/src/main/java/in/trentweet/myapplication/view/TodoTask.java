package in.trentweet.myapplication.view;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AlertDialog;


import java.util.List;
import java.util.Objects;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.trentweet.myapplication.R;
import in.trentweet.myapplication.adapter.RecyclerItemTouchHelper;
import in.trentweet.myapplication.adapter.TodoTaskAdapter;
import in.trentweet.myapplication.model.TodoList;
import in.trentweet.myapplication.presenter.impl.TodoPresenter;

import in.trentweet.myapplication.utilities.AppConstant;
import in.trentweet.myapplication.utilities.Widget;

public class TodoTask extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private TodoPresenter _presenter;

    @BindView(R.id.btnAddTask)
    ImageButton btnAddTask;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private Widget widget;
    AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        ButterKnife.bind(this);
        widget = new Widget(getApplicationContext());

        _presenter = new TodoPresenter(getApplicationContext(), TodoTask.this);
        _presenter.SubscribeCallBacks();
        _presenter.getTasks();


    }

    @OnClick(R.id.btnAddTask)
    void btnClicked() {
        showDialog();
    }


    @Override
    protected void onStop() {
        super.onStop();
        _presenter.UnSubscribeCallBacks();
    }

    private void showDialog() {
        alert = new AlertDialog.Builder(this).create();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            Objects.requireNonNull(alert.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View alertBoxView = inflater.inflate(R.layout.layout_add_task, null);
        alert.setView(alertBoxView);

        Button yesBtn = alertBoxView.findViewById(R.id.btnCreateTask);
        final EditText etTaskTitle = alertBoxView.findViewById(R.id.etTaskTitle);
        final EditText etTaskDesc = alertBoxView.findViewById(R.id.etTaskDesc);


        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _presenter.createTask(etTaskTitle, etTaskDesc);
            }
        });

        alert.show();
    }


    public void taskAdded() {
        _presenter.getTasks();
        alert.hide();
    }

    public void setTask(List<TodoList> model) {

        TodoTaskAdapter adapter = new TodoTaskAdapter(getApplicationContext(), model);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        widget.showSnackBar(findViewById(R.id.app_bar), "Position: " + position, AppConstant.SNACK_BAR_LONG, AppConstant.SNACK_BAR_SUCCESS);
    }
}

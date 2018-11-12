package in.trentweet.myapplication.presenter.impl;

import android.content.Context;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.trentweet.myapplication.model.TodoList;
import in.trentweet.myapplication.presenter.base.ITodoPresenter;
import in.trentweet.myapplication.service.INetworkInterface;
import in.trentweet.myapplication.service.NetworkNetwork;
import in.trentweet.myapplication.utilities.AppConstant;
import in.trentweet.myapplication.utilities.AppSharedPreferences;
import in.trentweet.myapplication.utilities.Widget;
import in.trentweet.myapplication.view.TodoTask;

public class TodoPresenter implements ITodoPresenter {


    private Context _context;
    private TodoTask _view;
    private NetworkNetwork networkService;
    private AppSharedPreferences sharedPreferences;
    private Widget widget;
    private INetworkInterface.cbJsonArrayRequest cbGetTasks;
    private INetworkInterface.cbJsonObjectRequest cbPostTasks;

    public TodoPresenter(Context context, TodoTask view) {
        _context = context;
        _view = view;
        widget = new Widget(_context);
        networkService = new NetworkNetwork(_context);
        sharedPreferences = new AppSharedPreferences(_context);
    }

    @Override
    public void SubscribeCallBacks() {
        cbGetTasks = new INetworkInterface.cbJsonArrayRequest() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    List<TodoList> model = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++)
                        model.add(new TodoList(response.optJSONObject(i).optInt("todoId"),
                                response.optJSONObject(i).optString("title"),
                                response.optJSONObject(i).optString("description")));

                    _view.setTask(model);
                }

            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                widget.showToast("Error: " + errorCode + "\nMessage:" + errorMsg);

            }
        };

        cbPostTasks = new INetworkInterface.cbJsonObjectRequest() {
            @Override
            public void onResponse(JSONObject response) {

                _view.taskAdded();

            }

            @Override
            public void onError(int errorCode, String errorMsg) {
                widget.showToast("Whoops! Error Occured\n" + "Error Code: " + errorCode + "\nError Message: " + errorMsg);
            }
        };
    }

    @Override
    public void UnSubscribeCallBacks() {
        cbGetTasks = null;
        cbPostTasks = null;
    }


    @Override
    public void getTasks() {
        networkService.JsonArrayRequest(AppConstant.GET, "TodoLists/getTask/?id=" + sharedPreferences.getId(), null, cbGetTasks);
    }

    @Override
    public void createTask(EditText etTaskTitle, EditText etTaskDesc) {
        if (widget.getTextFromEditText(etTaskTitle).isEmpty())
            etTaskTitle.setError("This is required");
        else {

            JSONObject input = new JSONObject();
            try {
                input.put("id", sharedPreferences.getId());
                input.put("title", widget.getTextFromEditText(etTaskTitle));
                input.put("description", widget.getTextFromEditText(etTaskDesc));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            networkService.JsonObjectRequest(AppConstant.POST, "TodoLists/postTask", input, cbPostTasks);
        }

    }


}

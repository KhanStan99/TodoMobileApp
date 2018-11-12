package in.trentweet.myapplication.model;

public class TodoList {

    private int todoTaskId;
    private String todoTaskTitle, todoTaskDesc;

    public TodoList(int todoTaskId, String todoTaskTitle, String todoTaskDesc) {
        this.todoTaskId = todoTaskId;
        this.todoTaskTitle = todoTaskTitle;
        this.todoTaskDesc = todoTaskDesc;
    }

    public int getTodoTaskId() {
        return todoTaskId;
    }

    public String getTodoTaskTitle() {
        return todoTaskTitle;
    }

    public String getTodoTaskDesc() {
        return todoTaskDesc;
    }
}

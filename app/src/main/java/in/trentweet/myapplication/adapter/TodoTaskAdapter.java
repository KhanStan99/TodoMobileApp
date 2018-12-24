package in.trentweet.myapplication.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import in.trentweet.myapplication.R;
import in.trentweet.myapplication.model.TodoList;

public class TodoTaskAdapter extends RecyclerView.Adapter<TodoTaskAdapter.MyViewHolder> {
    private Context context;
    private List<TodoList> taskList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTodoTitle, txtTodoDesc;
        public RelativeLayout viewBackground, viewForeground, view_left_background;

        public MyViewHolder(View view) {
            super(view);
            txtTodoTitle = view.findViewById(R.id.txtTodoTitle);
            txtTodoDesc = view.findViewById(R.id.txtTodoDesc);

            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            view_left_background = view.findViewById(R.id.view_left_background);
        }
    }


    public TodoTaskAdapter(Context context, List<TodoList> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_todo_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final TodoList item = taskList.get(position);
        holder.txtTodoTitle.setText(item.getTodoTaskTitle());
        holder.txtTodoDesc.setText(item.getTodoTaskDesc());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void removeItem(int position) {
        taskList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(TodoList item, int position) {
        taskList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}
// TaskItem.js
import React from "react";

function TaskItem({ task, toggleTask, deleteTask }) {
  return (
    <li className={`task-item ${task.completed ? "completed" : ""}`}>
      <span>{task.title}</span>

      <div>
        <button onClick={() => toggleTask(task.id)}>
          {task.completed ? "Undo" : "Complete"}
        </button>
        <button onClick={() => deleteTask(task.id)} className="delete-btn">
          Delete
        </button>
      </div>
    </li>
  );
}

export default TaskItem;

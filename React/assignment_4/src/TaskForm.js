// TaskForm.js
import React, { useState } from "react";

function TaskForm({ addTask }) {
  const [taskInput, setTaskInput] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();

    if (taskInput.trim() === "") return;

    addTask(taskInput);
    setTaskInput(""); // Clear input after adding
  };

  return (
    <form className="task-form" onSubmit={handleSubmit}>
      <input
        type="text"
        value={taskInput}
        onChange={(e) => setTaskInput(e.target.value)}
        placeholder="Enter new task..."
      />
      <button type="submit">Add Task</button>
    </form>
  );
}

export default TaskForm;

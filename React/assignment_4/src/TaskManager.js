// TaskManager.js
import React, { Component } from "react";
import TaskForm from "./TaskForm"; // ✅ Corrected path
import TaskList from "./TaskList"; // ✅ Corrected path
import "./index.css"; // optional styling

class TaskManager extends Component {
  constructor(props) {
    super(props);

    // Step 1: Initialize State
    this.state = {
      tasks: [
        { id: 1, title: "Learn React", completed: false },
        { id: 2, title: "Build a project", completed: false },
      ],
    };
  }

  // Add a new task
  addTask = (title) =>
    this.setState((prevState) => ({
      tasks: [...prevState.tasks, { id: Date.now(), title, completed: false }],
    }));

  // Toggle task completion
  toggleTask = (id) =>
    this.setState((prevState) => ({
      tasks: prevState.tasks.map((t) =>
        t.id === id ? { ...t, completed: !t.completed } : t
      ),
    }));

  // Delete a task
  deleteTask = (id) =>
    this.setState((prevState) => ({
      tasks: prevState.tasks.filter((t) => t.id !== id),
    }));

  render() {
    return (
      <div className="task-manager">
        <h1>📝 Task Management App</h1>
        <TaskForm addTask={this.addTask} />
        <TaskList
          tasks={this.state.tasks}
          toggleTask={this.toggleTask}
          deleteTask={this.deleteTask}
        />
      </div>
    );
  }
}

export default TaskManager;

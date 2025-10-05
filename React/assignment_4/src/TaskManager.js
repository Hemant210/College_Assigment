// TaskManager.js
import React, { Component } from "react";
import TaskForm from "./TaskForm";   // ✅ Corrected path
import TaskList from "./TaskList";   // ✅ Corrected path
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

  // Step 2: Add a new task
  addTask = (newTaskTitle) => {
    const newTask = {
      id: Date.now(),
      title: newTaskTitle,
      completed: false,
    };

    this.setState({
      tasks: [...this.state.tasks, newTask],
    });
  };

  // Step 3: Toggle task completion
  toggleTask = (id) => {
    const updatedTasks = this.state.tasks.map((task) =>
      task.id === id ? { ...task, completed: !task.completed } : task
    );
    this.setState({ tasks: updatedTasks });
  };

  // Step 4: Delete a task
  deleteTask = (id) => {
    const filteredTasks = this.state.tasks.filter((task) => task.id !== id);
    this.setState({ tasks: filteredTasks });
  };

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

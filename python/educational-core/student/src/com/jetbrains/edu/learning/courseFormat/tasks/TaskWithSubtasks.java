package com.jetbrains.edu.learning.courseFormat.tasks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jetbrains.edu.learning.courseFormat.AnswerPlaceholder;
import com.jetbrains.edu.learning.courseFormat.StudyStatus;
import com.jetbrains.edu.learning.courseFormat.TaskFile;
import org.jetbrains.annotations.NotNull;

public class TaskWithSubtasks extends Task {
  private int myActiveSubtaskIndex = 0;
  @SerializedName("last_subtask_index")
  @Expose private int myLastSubtaskIndex = 0;

  public TaskWithSubtasks() {}

  public TaskWithSubtasks(@NotNull final String name) {
    super(name);
  }

  public TaskWithSubtasks(Task task) {
    copyTaskParameters(task);
  }

  public int getActiveSubtaskIndex() {
    return myActiveSubtaskIndex;
  }

  public void setActiveSubtaskIndex(int activeSubtaskIndex) {
    myActiveSubtaskIndex = activeSubtaskIndex;
  }

  public int getLastSubtaskIndex() {
    return myLastSubtaskIndex;
  }

  public void setLastSubtaskIndex(int lastSubtaskIndex) {
    myLastSubtaskIndex = lastSubtaskIndex;
  }

  public void setStatus(StudyStatus status) {
    for (TaskFile taskFile : taskFiles.values()) {
      for (AnswerPlaceholder placeholder : taskFile.getActivePlaceholders()) {
        placeholder.setStatus(status);
      }
    }
    if (status == StudyStatus.Solved && activeSubtaskNotLast()) {
      if (myStatus == StudyStatus.Failed) {
        myStatus = StudyStatus.Unchecked;
      }
    }
  }

  public boolean activeSubtaskNotLast() {
    return getActiveSubtaskIndex() != getLastSubtaskIndex();
  }

  public String getTaskType() {
    return "subtasks";
  }
}

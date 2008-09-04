package com.intellij.xdebugger.impl.ui.tree.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.xdebugger.frame.XValue;
import com.intellij.xdebugger.impl.ui.tree.XDebuggerTree;
import com.intellij.xdebugger.impl.ui.tree.nodes.XValueNodeImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.tree.TreePath;

/**
 * @author nik
 */
public abstract class XDebuggerTreeActionBase extends AnAction {
  public void actionPerformed(final AnActionEvent e) {
    XValueNodeImpl node = getSelectedNode(e.getDataContext());
    if (node == null) return;

    String nodeName = node.getName();
    if (nodeName == null) return;

    perform(node, nodeName, e);
  }

  protected abstract void perform(final XValueNodeImpl node, @NotNull String nodeName, final AnActionEvent e);


  public void update(final AnActionEvent e) {
    XValueNodeImpl node = getSelectedNode(e.getDataContext());
    e.getPresentation().setEnabled(node != null && isEnabled(node));
  }

  protected boolean isEnabled(final XValueNodeImpl node) {
    return node.getName() != null;
  }

  @Nullable
  private static XValueNodeImpl getSelectedNode(final DataContext dataContext) {
    XDebuggerTree tree = XDebuggerTree.getTree(dataContext);
    if (tree == null) return null;

    TreePath path = tree.getSelectionPath();
    if (path == null) return null;

    Object node = path.getLastPathComponent();
    if (node instanceof XValueNodeImpl) {
      return (XValueNodeImpl)node;
    }
    return null;
  }

  @Nullable
  public static XValue getSelectedValue(@NotNull DataContext dataContext) {
    XValueNodeImpl node = getSelectedNode(dataContext);
    return node != null ? node.getValueContainer() : null;
  }
}

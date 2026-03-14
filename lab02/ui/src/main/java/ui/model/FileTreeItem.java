package ui.model;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileTreeItem extends TreeItem<File> {
    private boolean isLeaf;
    private boolean isFirstTimeChildren = true;
    private boolean isFirstTimeLeaf = true;

    public FileTreeItem(File file) {
        super(file);
    }

    // updateExpandedDescendentCount
    @Override
    public boolean isLeaf() {
        if (isFirstTimeLeaf) {
            isFirstTimeLeaf = false;
            File f = getValue();
            isLeaf = f.isFile();
        }

        return isLeaf;
    }

    @Override
    public ObservableList<TreeItem<File>> getChildren() {
        if (isFirstTimeChildren) {
            isFirstTimeChildren = false;

            super.getChildren().setAll(buildChildren(this));
        }

        return super.getChildren();
    }

    private List<TreeItem<File>> buildChildren(TreeItem<File> treeItem) {
        File f = treeItem.getValue();

        if (f != null && f.isDirectory()) {
            File[] files = f.listFiles();

            if (files != null) {
                List<TreeItem<File>> children = new ArrayList<>();

                for (File file : files) {
                    children.add(new FileTreeItem(file));
                }

                return children;
            }
        }

        return new ArrayList<>();
    }
}

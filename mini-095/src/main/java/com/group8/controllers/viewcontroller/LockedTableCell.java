package com.group8.controllers.viewcontroller;

import javafx.scene.control.TreeTableCell;

public abstract class LockedTableCell<T, S> extends TreeTableCell<T, S> {

    {

        // Platform.runLater(() -> {

        // ScrollBar sc = (ScrollBar) getTreeTableView()
        // .queryAccessibleAttribute(AccessibleAttribute.HORIZONTAL_SCROLLBAR);

        // TableHeaderRow thr = (TableHeaderRow) getTreeTableView()
        // .queryAccessibleAttribute(AccessibleAttribute.HEADER);

        // Region headerNode = thr.getColumnHeaderFor(this.getTableColumn());

        // sc.valueProperty().addListener((ob, o, n) -> {

        // double doubleValue = n.doubleValue();

        // headerNode.setTranslateX(doubleValue);

        // headerNode.toFront();

        // this.setTranslateX(doubleValue);
        // this.toFront();

        // });

        // });

    }

}

package com.group8.controllers.viewcontroller;

import javafx.application.Platform;
import javafx.scene.AccessibleAttribute;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.skin.TableColumnHeader;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.Region;

public abstract class LockedTableCell<T, S> extends TreeTableCell<T, S> {

    {

        Platform.runLater(() -> {

            ScrollBar sc = (ScrollBar) getTreeTableView()
                    .queryAccessibleAttribute(AccessibleAttribute.HORIZONTAL_SCROLLBAR);

            TableColumnHeader chr = ((TableHeaderRow) getTreeTableView()
                    .queryAccessibleAttribute(AccessibleAttribute.HEADER)).getColumnHeaderFor(this.getTableColumn());
            Region headerNode = chr;

            sc.valueProperty().addListener((ob, o, n) -> {

                double doubleValue = n.doubleValue();

                headerNode.setTranslateX(doubleValue);

                headerNode.toFront();

                this.setTranslateX(doubleValue);
                this.toFront();

            });

        });

    }

}

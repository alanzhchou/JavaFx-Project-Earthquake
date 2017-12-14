package controller;

import bean.Earthquake;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/**
 * Author ZH-AlanChou
 * Date: 2017/12/9.
 * Version 1.0
 */
public class TableController<T> implements ViewLikeController {
    private TableView<T> tableView;
    ArrayList<TableColumn> tableColumns = new ArrayList<TableColumn>();

    public TableController(TableView<T> tableView){
        this.tableView = tableView;
        this.tableView.getColumns().stream().forEach(tableColumns::add);
        this.tableColumns.stream().forEach(q->q.setCellValueFactory(new PropertyValueFactory<Earthquake, Object>(q.getText())));
    }

    @Override
    public void refresh() {
    }

    public void refresh(ObservableList<T> list){
        this.tableView.setItems(list);
    }
}

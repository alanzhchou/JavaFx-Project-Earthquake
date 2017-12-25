package controller;

import bean.Earthquake;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/**
 * @Author: Alan
 * @since : Java_8_151
 * @version: 1.0
 */
public class TableController<T> implements ViewLikeController {
    /**
     * the aim tableView for controlling
     */
    private TableView<T> tableView;

    /**
     * the TableColumns of the tableView
     */
    ArrayList<TableColumn> tableColumns = new ArrayList<TableColumn>();

    public TableController(TableView<T> tableView){
        this.tableView = tableView;
        this.tableView.getColumns().stream().forEach(tableColumns::add);
        this.tableColumns.stream().forEach(q->q.setCellValueFactory(new PropertyValueFactory<Earthquake, Object>(q.getText())));
    }

    /**
     * no use, please overload this function
     * @return
     */
    @Override
    public void refresh() {
    }

    /**
     * refresh the tableview using the "ObservableList<T> list"
     * @param list the Observablelist of earthquakes for refresh table
     */
    public void refresh(ObservableList<T> list){
        this.tableView.setItems(list);
    }
}

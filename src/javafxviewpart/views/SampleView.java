package javafxviewpart.views;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenuBuilder;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import org.csstudio.logbook.Attachment;
import org.csstudio.logbook.AttachmentBuilder;
import org.csstudio.logbook.LogEntry;
import org.csstudio.logbook.LogEntryBuilder;
import org.csstudio.logbook.LogbookBuilder;
import org.csstudio.logbook.PropertyBuilder;
import org.csstudio.logbook.TagBuilder;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;

import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;





public class SampleView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "javafxviewpart.views.SampleView";
	private FXCanvas canvas;
	private Text text = new Text();
	private TreeTableView table;
	private final GridPane grid = new GridPane();
	private Scene scene;
	private List<LogEntry> logList = new ArrayList<>();

	@Override
	public void createPartControl(Composite parent) {
		canvas = new FXCanvas(parent, SWT.NONE);
		canvas.setScene(createFxScene());
	}

	private Scene createFxScene() {
        BorderPane pane = new BorderPane();
        makeTable();
        pane.setCenter(table);
        return new Scene(pane);
	}
	
	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}
	
	private void makeTable() {
		makeData();
        int columnCount = 10;
        int rowCount = 5;
        

        //set the name and cell factory for each column
        
        TreeTableColumn<LogEntry, String> idColumn;
        idColumn = new TreeTableColumn<LogEntry, String>("ID");
        idColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<LogEntry, String> p) {
            	try {
            		return new SimpleStringProperty(p.getValue().getValue().getId().toString());
            	}
            	catch (Throwable e) {
            		return new SimpleStringProperty("");
            	}
            }
        });
        idColumn.setCellFactory(TextFieldTreeTableCell.<LogEntry, String>forTreeTableColumn(new StringConverter<String>() {
            @Override
            public String toString(String t) {
                return t.toString();
            }
            @Override
            public String fromString(String string) {
                return string;
            }                                    
        }));

        TreeTableColumn<LogEntry, String> levelColumn;
        levelColumn = new TreeTableColumn<LogEntry, String>("Level");
        levelColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<LogEntry, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().getLevel());
            }
        });
        levelColumn.setCellFactory(TextFieldTreeTableCell.<LogEntry>forTreeTableColumn());
        
        TreeTableColumn<LogEntry, String> textColumn;
        textColumn = new TreeTableColumn<LogEntry, String>("Text");
        textColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<LogEntry, String> p) {
            	System.out.println(p.getValue().getValue().getText());
                return new SimpleStringProperty(p.getValue().getValue().getText());  
            }
        });
        textColumn.setCellFactory(TextFieldTreeTableCell.<LogEntry>forTreeTableColumn());
        
        TreeTableColumn<LogEntry, String> ownerColumn;
        ownerColumn = new TreeTableColumn<LogEntry, String>("Owner");
        ownerColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<LogEntry, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().getOwner());
            }
        });
        ownerColumn.setCellFactory(TextFieldTreeTableCell.<LogEntry>forTreeTableColumn());
        
        TreeTableColumn<LogEntry, String> dateCreatedColumn;
        dateCreatedColumn = new TreeTableColumn<LogEntry, String>("Date Created");
        dateCreatedColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<LogEntry, String> p) {
            	try {
            		return new SimpleStringProperty(p.getValue().getValue().getCreateDate().toString());
            	}
            	catch (Throwable e) {
            		return new SimpleStringProperty("");
            	}
            }
        });
        dateCreatedColumn.setCellFactory(TextFieldTreeTableCell.<LogEntry>forTreeTableColumn());

        TreeTableColumn<LogEntry, String> dateModifiedColumn;
        dateModifiedColumn = new TreeTableColumn<LogEntry, String>("Date Modified");
        dateModifiedColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<LogEntry, String> p) {
            	try {
            		return new SimpleStringProperty(p.getValue().getValue().getModifiedDate().toString());
            	}
            	catch (Throwable e) {
            		return new SimpleStringProperty("");
            	}
            }
        });
        dateModifiedColumn.setCellFactory(TextFieldTreeTableCell.<LogEntry>forTreeTableColumn());
        
        TreeTableColumn<LogEntry, String> attachmentColumn;
        attachmentColumn = new TreeTableColumn<LogEntry, String>("Attachment");
        attachmentColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<LogEntry, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().getAttachment().iterator().next().toString());
            }
        });
        attachmentColumn.setCellFactory(TextFieldTreeTableCell.<LogEntry>forTreeTableColumn());

        TreeTableColumn<LogEntry, String> tagColumn;
        tagColumn = new TreeTableColumn<LogEntry, String>("Tags");
        tagColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<LogEntry, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().getTags().iterator().next().toString());
            }
        });
        tagColumn.setCellFactory(TextFieldTreeTableCell.<LogEntry>forTreeTableColumn());

        TreeTableColumn<LogEntry, String> logbookColumn;
        logbookColumn = new TreeTableColumn<LogEntry, String>("Logbooks");
        logbookColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<LogEntry, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().getLogbooks().iterator().next().toString());
            }
        });
        logbookColumn.setCellFactory(TextFieldTreeTableCell.<LogEntry>forTreeTableColumn());

        TreeTableColumn<LogEntry, String> propertyColumn;
        propertyColumn = new TreeTableColumn<LogEntry, String>("Properties");
        propertyColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LogEntry, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<LogEntry, String> p) {
                return new SimpleStringProperty(p.getValue().getValue().getProperties().iterator().next().toString());
            }
        });
        propertyColumn.setCellFactory(TextFieldTreeTableCell.<LogEntry>forTreeTableColumn());

        TreeItem<LogEntry> root = new TreeItem<LogEntry>(logList.get(0));
        for(int i = 1; i < 5; i++) {
        	root.getChildren().add(new TreeItem<LogEntry>(logList.get(i)));
        }
        //create table
        table = new TreeTableView<>(root);

        table.getSelectionModel().setCellSelectionEnabled(true);
        table.setEditable(true);
        //safely add all columns to table
        table.getColumns().clear();
        table.getColumns().addAll(idColumn, levelColumn, textColumn, ownerColumn,
        		dateCreatedColumn, dateModifiedColumn, attachmentColumn, tagColumn,
        		logbookColumn, propertyColumn);

        //set columns to fit to the current width of the table unless
        //the user changes them.
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        
        //adds "+" button that allows user to choose which columns are shown.
        table.setTableMenuButtonVisible(true);
        
        //make it so table grows when user stretches screen.
        table.maxWidth(Double.MAX_VALUE);
        table.maxHeight(Double.MAX_VALUE);
        grid.setMaxHeight(Double.MAX_VALUE);
        grid.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(table, Priority.ALWAYS);
        VBox.setVgrow(table, Priority.ALWAYS);
        GridPane.setHgrow(grid, Priority.ALWAYS);
        GridPane.setVgrow(grid, Priority.ALWAYS);
    }
	
	private ObservableList<Map> generateDataInMap(int columnCount, int rowCount){
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for(int i = 0; i < rowCount; i++) {
            Map<String, Node> row = new HashMap<>();
            for(int j = 0; j < columnCount; j++) {
                row.put(Double.toString(j) , makeTextBox(j, i, columnCount)); 
            }
            
            allData.add(row); // add each row to allData
        }
        return allData;
    }
	
	private void setComparators() {
        for(int i = 0; i < table.getColumns().size() - 2; i++) {
            ((TableColumn<Map, Node>)table.getColumns().get(i)).setComparator((node1, node2)->{
                if((Double.parseDouble(((Text)((HBox)node1).getChildren().get(0)).getText()) -
                    Double.parseDouble(((Text)((HBox)node2).getChildren().get(0)).getText())) > 0) {
                    return 1;
                }
                if((Double.parseDouble(((Text)((HBox)node1).getChildren().get(0)).getText()) -
                    Double.parseDouble(((Text)((HBox)node2).getChildren().get(0)).getText())) == 0) {
                    return 0;
                }
                if((Double.parseDouble(((Text)((HBox)node1).getChildren().get(0)).getText()) -
                    Double.parseDouble(((Text)((HBox)node2).getChildren().get(0)).getText())) == 0) {
                    return -1;
                }
                return 0;
            });
        }
    }
	
	private HBox makeTextBox(int column, int row, int columnCount) {
        HBox hbox = new HBox();
        hbox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(hbox, Priority.ALWAYS);
        hbox.getChildren().add(new Text(Double.toString(column + row*columnCount)));
        
        return hbox;
    }
	
	private void makeData() {
		try {
			LogEntryBuilder builder = LogEntryBuilder.withText("Log").owner("Sam")
					.attach(AttachmentBuilder.attachment("attachement.giraffe"))
					.addLogbook(LogbookBuilder.logbook("logbook"))
					.addProperty(PropertyBuilder.property("property"))
					.addTag(TagBuilder.tag("tag"));

			for(int i = 0; i < 5; i++) {
				logList.add(builder.setLevel(i + "").build());
			}

		}
		catch (Throwable e){
			
		}
	}
}
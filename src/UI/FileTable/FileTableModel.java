package UI.FileTable;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")

public class FileTableModel extends DefaultTableModel{
	public FileTableModel(Object[][] objects, String[] tableHeader){
        super(objects, tableHeader);
    }
	
	@Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}

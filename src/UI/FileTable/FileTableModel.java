package UI.FileTable;

import java.io.File;

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
	
//	@Override
//    public Object getValueAt(int row, int column) {
//        File file = (File) super.getValueAt(row, column);
//        if (column == 0){
//            return file;
//        } else if (column == 1){
//            return file.lastModified();
//        } else if (column == 2) {
//        	return file.length();
//        } 
//        return super.getValueAt(row, column);
//    }
}

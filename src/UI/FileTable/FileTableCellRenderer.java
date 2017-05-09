package UI.FileTable;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableCellRenderer;

import UI.GlobalDef;

@SuppressWarnings("serial")

public class FileTableCellRenderer extends JLabel implements TableCellRenderer {
	
	FileSystemView fileSystemView = FileSystemView.getFileSystemView();
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.setFont(new java.awt.Font(GlobalDef.TableFontName, 0, 13));
        setOpaque(true);
        setEnabled(table.isEnabled());
        if (isSelected) {
            this.setBackground(GlobalDef.selecetdGray);
            this.setForeground(table.getForeground());
        }
        else {
            this.setBackground(table.getBackground());
            this.setForeground(table.getForeground());
        }
        this.setText((String)value);
        return this;
    }
}
